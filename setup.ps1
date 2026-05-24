<#
.SYNOPSIS
    Instala dependências e executa o Sistema de Aviação no Windows.
.DESCRIPTION
    1. Verifica/instala JDK 17, Maven e XAMPP (via winget — requer Admin)
    2. Sobe o MySQL e importa o banco
    3. Compila e executa o projeto
.NOTES
    Executar como Administrador: botão direito → "Run with PowerShell"
#>

$ErrorActionPreference = "Stop"
$script:etapasPuladas = 0

function Write-Step {
    param([string]$Msg)
    Write-Host "`n>>> $Msg" -ForegroundColor Cyan
}

function Write-Success {
    param([string]$Msg)
    Write-Host "  OK  $Msg" -ForegroundColor Green
}

function Write-Skip {
    param([string]$Msg)
    Write-Host "  ~   $Msg" -ForegroundColor Yellow
    $script:etapasPuladas++
}

function Write-Error {
    param([string]$Msg)
    Write-Host "  FAIL $Msg" -ForegroundColor Red
}

function Check-Command {
    param([string]$Cmd)
    return [bool](Get-Command $Cmd -ErrorAction SilentlyContinue)
}

function Test-Java17 {
    if (-not (Check-Command "java")) { return $false }
    $v = (java -version 2>&1) -join " "
    return $v -match "version\s+\"17"
}

# ============================================================
# PASSO 0 — Verificação de Administrador
# ============================================================
Write-Step "[0/8] Verificando permissoes"
$isAdmin = ([Security.Principal.WindowsPrincipal][Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole]"Administrator")
if (-not $isAdmin) {
    Write-Error "Execute como Administrador (botao direito -> Run as Administrator)"
    Write-Host "Dica: clique com o botao direito no setup.ps1 e selecione 'Run as Administrator'"
    pause
    exit 1
}
Write-Success "Permissao de administrador OK"

# ============================================================
# PASSO 1 — Verificar winget
# ============================================================
Write-Step "[1/8] Verificando winget"
if (-not (Check-Command "winget")) {
    Write-Error "winget nao encontrado. Baixe o App Installer da Microsoft Store."
    pause
    exit 1
}
Write-Success "winget disponivel"

# ============================================================
# PASSO 2 — JDK 17
# ============================================================
Write-Step "[2/8] Verificando JDK 17"
$jdkOk = Test-Java17
if (-not $jdkOk) {
    $resp = Read-Host "  JDK 17 nao encontrado. Instalar agora? (S/N)"
    if ($resp -eq "S" -or $resp -eq "s") {
        Write-Host "  Instalando Eclipse Temurin 17 JDK (pode demorar)..."
        try {
            winget install "Eclipse Temurin 17 JDK" --accept-package-agreements --disable-interactivity
            Write-Success "JDK 17 instalado"
        } catch {
            Write-Error "Falha ao instalar JDK. Baixe manualmente de: https://adoptium.net/"
            pause; exit 1
        }
        # Refresh PATH
        $env:Path = [Environment]::GetEnvironmentVariable("Path", "Machine") + ";" + [Environment]::GetEnvironmentVariable("Path", "User")
    } else {
        Write-Skip "JDK 17 — pulando instalacao (necessario para rodar)"
    }
} else {
    Write-Success "JDK 17 ja instalado"
}

# ============================================================
# PASSO 3 — Maven
# ============================================================
Write-Step "[3/8] Verificando Maven"
$mvnOk = Check-Command "mvn"
if (-not $mvnOk) {
    $resp = Read-Host "  Maven nao encontrado. Instalar agora? (S/N)"
    if ($resp -eq "S" -or $resp -eq "s") {
        Write-Host "  Instalando Apache Maven..."
        try {
            winget install Apache.Maven --accept-package-agreements --disable-interactivity
            $env:Path = [Environment]::GetEnvironmentVariable("Path", "Machine") + ";" + [Environment]::GetEnvironmentVariable("Path", "User")
            Write-Success "Maven instalado"
        } catch {
            Write-Error "Falha ao instalar Maven. Baixe de: https://maven.apache.org/download.cgi"
            pause; exit 1
        }
    } else {
        Write-Skip "Maven — pulando instalacao (necessario para compilar)"
    }
} else {
    Write-Success "Maven ja instalado"
}

# ============================================================
# PASSO 4 — XAMPP (opcional, só se quiser rodar o banco local)
# ============================================================
Write-Step "[4/8] Verificando XAMPP / MySQL"
$mysqlPaths = @(
    "${env:ProgramFiles}\XAMPP\mysql\bin\mysql.exe",
    "C:\xampp\mysql\bin\mysql.exe",
    "${env:LOCALAPPDATA}\XAMPP\mysql\bin\mysql.exe"
)
$mysqlExe = $null
foreach ($p in $mysqlPaths) {
    if (Test-Path $p) { $mysqlExe = $p; break }
}

$precisaXampp = $mysqlExe -eq $null

if ($precisaXampp) {
    $resp = Read-Host "  MySQL nao encontrado. Instalar XAMPP? (S/N) — Se ja tiver MySQL de outro jeito, responda N"
    if ($resp -eq "S" -or $resp -eq "s") {
        Write-Host "  Instalando XAMPP..."
        try {
            winget install Apache.XAMPP --accept-package-agreements --disable-interactivity
            $mysqlExe = "${env:ProgramFiles}\XAMPP\mysql\bin\mysql.exe"
            if (-not (Test-Path $mysqlExe)) { $mysqlExe = "C:\xampp\mysql\bin\mysql.exe" }
            Write-Success "XAMPP instalado"
        } catch {
            Write-Error "Falha ao instalar XAMPP. Baixe de: https://www.apachefriends.org/"
            pause; exit 1
        }
    } else {
        Write-Skip "XAMPP — pulando (banco nao estara disponivel)"
    }
} else {
    Write-Success "MySQL encontrado em: $mysqlExe"
}

# ============================================================
# PASSO 5 — Iniciar MySQL
# ============================================================
Write-Step "[5/8] Iniciando MySQL"
if ($mysqlExe) {
    $mysqldPath = Join-Path (Split-Path (Split-Path $mysqlExe -Parent) -Parent) "mysqld.exe"
    if (Test-Path $mysqldPath) {
        $proc = Get-Process "mysqld" -ErrorAction SilentlyContinue
        if (-not $proc) {
            Write-Host "  Iniciando mysqld..."
            Start-Process -FilePath $mysqldPath -WindowStyle Hidden
            Start-Sleep -Seconds 4
        } else {
            Write-Success "MySQL ja rodando"
        }

        # Aguardar MySQL ficar pronto
        $ready = $false
        for ($i = 0; $i -lt 15; $i++) {
            try {
                $test = Start-Process -FilePath $mysqlExe -ArgumentList "-u root -e `"SELECT 1`"" -NoNewWindow -Wait -PassThru
                if ($test.ExitCode -eq 0) { $ready = $true; break }
            } catch {}
            Start-Sleep -Seconds 2
        }
        if (-not $ready) {
            Write-Error "MySQL nao respondeu apos 30s. Verifique se a porta 3306 esta livre."
            pause; exit 1
        }
        Write-Success "MySQL pronto para conexao"
    } else {
        Write-Error "mysqld.exe nao encontrado em: $mysqldPath"
        pause; exit 1
    }
} else {
    Write-Skip "MySQL nao configurado — pulando inicializacao"
}

# ============================================================
# PASSO 6 — Criar database + importar dados
# ============================================================
Write-Step "[6/8] Configurando banco de dados"
if ($mysqlExe) {
    $dumpFile = Join-Path $PSScriptRoot "docs\sistema_aviacao.sql"
    if (Test-Path $dumpFile) {
        try {
            & $mysqlExe -u root -e "CREATE DATABASE IF NOT EXISTS sistema_aviacao CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci"
            & $mysqlExe -u root sistema_aviacao < $dumpFile
            Write-Success "Database 'sistema_aviacao' criada e dados importados"

            # AUTO_INCREMENT nas PKs (se o dump antigo nao tinha)
            & $mysqlExe -u root sistema_aviacao -e "
                SET FOREIGN_KEY_CHECKS=0;
                ALTER TABLE passageiro MODIFY id_passageiro INT(5) NOT NULL AUTO_INCREMENT;
                ALTER TABLE aeroporto MODIFY cod_aeroporto INT(4) NOT NULL AUTO_INCREMENT;
                ALTER TABLE cia_aerea MODIFY id_cia INT(5) NOT NULL AUTO_INCREMENT;
                ALTER TABLE aeronave MODIFY id_aeronave INT(5) NOT NULL AUTO_INCREMENT;
                SET FOREIGN_KEY_CHECKS=1;
            " 2>$null
            Write-Success "AUTO_INCREMENT configurado nas PKs"
        } catch {
            Write-Error "Falha ao importar dump: $_"
            pause; exit 1
        }
    } else {
        Write-Error "Arquivo docs/sistema_aviacao.sql nao encontrado"
        pause; exit 1
    }
} else {
    Write-Skip "MySQL nao disponivel — pulando configuracao do banco"
}

# ============================================================
# PASSO 7 — Compilar
# ============================================================
Write-Step "[7/8] Compilando o projeto (mvn clean compile)"
try {
    $compile = Start-Process -FilePath "mvn" -ArgumentList "clean compile" -NoNewWindow -Wait -PassThru
    if ($compile.ExitCode -eq 0) {
        Write-Success "Compilacao OK"
    } else {
        Write-Error "Erro na compilacao. Verifique os logs acima."
        pause; exit 1
    }
} catch {
    Write-Error "Falha ao executar Maven: $_"
    pause; exit 1
}

# ============================================================
# PASSO 8 — Executar
# ============================================================
Write-Step "[8/8] Iniciando Sistema de Aviação"
Write-Host ""
Write-Host "=====================================" -ForegroundColor Green
Write-Host "  Tudo pronto!" -ForegroundColor Green
Write-Host "=====================================" -ForegroundColor Green
Write-Host ""

$resp = Read-Host "Iniciar o sistema agora? (S/N)"
if ($resp -eq "S" -or $resp -eq "s") {
    mvn exec:java
} else {
    Write-Host "Pra rodar depois: mvn exec:java" -ForegroundColor Yellow
    Write-Host "Ou abra no Eclipse: gui/MainFrame.java -> Run As -> Java Application"
}

Write-Host "`nConcluido! ($script:etapasPuladas etapas foram puladas)" -ForegroundColor Cyan
pause
