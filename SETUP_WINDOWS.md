# Setup Windows (Eclipse + XAMPP)

## Pré-requisitos

1. **XAMPP** (https://www.apachefriends.org/)
   - Instalar com Apache + MariaDB (MySQL)
   - Iniciar Apache e MySQL pelo XAMPP Control Panel

2. **Eclipse IDE for Java Developers** (https://eclipseide.org/)
   - JDK 17+ instalado e configurado no Eclipse (Window → Preferences → Java → Installed JREs)

3. **MySQL Connector/J** — já incluso via Maven (`pom.xml`), não precisa baixar manualmente.

## Importar o Projeto no Eclipse

1. **Extrair o ZIP** em qualquer pasta (ex: `C:\projetos\sistema-aviacao`)
2. **Eclipse:** File → Import → Maven → Existing Maven Projects
3. **Root Directory:** selecionar a pasta extraída
4. Clicar **Finish**

## Configurar o Banco

1. Abrir **phpMyAdmin** (http://localhost/phpmyadmin)
2. Criar database: `sistema_aviacao` (utf8mb4)
3. Importar o arquivo `docs/sistema_aviacao.sql`

## Executar

### Pelo Eclipse
- Abrir `src/main/java/com/aviacao/main/SistemaAviação.java`
- Botão direito → **Run As → Java Application**

### Pelo Terminal (CMD/PowerShell)
```bash
mvn clean compile exec:java -Dexec.mainClass="com.aviacao.main.SistemaAviação"
```

## Observações

- O arquivo `db.properties` (em `src/main/resources/`) já vem configurado para `localhost:3306`
- Acentos e caracteres especiais funcionam com UTF-8 (configurado no `pom.xml`)
- Em caso de erro de encoding no console Windows, execute:
  ```bash
  chcp 65001
  ```
  No prompt antes de rodar o Maven.
