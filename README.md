# ✈️ Sistema de Gerenciamento de Aviação Comercial

**Projeto acadêmico** — Oficina de Integração de Software  
Aplicação desktop em Java com persistência MariaDB para gerenciar aeronaves, companhias aéreas, aeroportos, passageiros, voos e reservas.

---

## Stack

| Camada | Tecnologia |
| :--- | :--- |
| Linguagem | Java 17+ |
| Interface | Java Swing (GUI) |
| Build | Apache Maven |
| Banco | MariaDB 10.4+ (XAMPP) |
| Conectividade | JDBC (`mysql-connector-j`) |

## Funcionalidades

- **CRUD completo** de aeronaves, companhias, aeroportos, passageiros, voos
- **Vincular** aeronaves a companhias aéreas
- **Reservas** com verificação de assento duplicado
- Validações de domínio (CNPJ, email, sigla, telefone, horários)
- Interface gráfica com abas (Swing)
- Menu textual alternativo (console)

## Estrutura do Projeto

```
src/main/java/com/aviacao/
├── gui/            → Interface gráfica (Swing)
│   ├── MainFrame.java
│   ├── CrudPanel.java
│   ├── FormDialog.java
│   └── CampoFormulario.java
├── service/        → Validações
│   └── Validador.java
├── model/          → POJOs (7 entidades)
├── dao/            → Data Access Objects + ConnectionFactory
└── main/           → Entry point console (legado)
```

## Pré-requisitos

- **JDK 17+**
- **Apache Maven**
- **XAMPP** (ou MariaDB/MySQL na porta 3306)

## Como Executar

### Opção 1 — Setup automático (Windows)

Execute como **Administrador**:

```powershell
.\setup.ps1
```

O script instala JDK, Maven e XAMPP (se necessário), cria o banco, importa os dados e inicia o sistema.

### Opção 2 — Manual

```bash
# 1. Criar o banco
mysql -u root -e "CREATE DATABASE sistema_aviacao"
mysql -u root sistema_aviacao < docs/sistema_aviacao.sql

# 2. Compilar e executar
mvn clean compile exec:java
```

### Opção 3 — Eclipse

1. File → Import → Maven → Existing Maven Projects
2. Selecionar a pasta do projeto
3. Abrir `gui/MainFrame.java` → Run As → Java Application

## Banco de Dados

### Modelo Relacional

![Diagrama de Classes](docs/images/diagrama-classes-uml.png)

### Script SQL

O arquivo `docs/sistema_aviacao.sql` contém a estrutura completa com dados de exemplo (10 registros por tabela).

## Documentação

Toda a documentação do projeto segue a metodologia **Personal Scrum**:

| Documento | Conteúdo |
| :--- | :--- |
| `docs/vision.md` | Propósito, stakeholders, definição de pronto |
| `docs/product-backlog.md` | User stories, casos de uso, regras de negócio |
| `docs/use-cases.md` | Especificação formal dos 8 casos de uso |
| `docs/technical-design.md` | Arquitetura, diagramas, armadilhas |
| `docs/class-diagram.md` | Diagrama de classes UML (Mermaid) |
| `docs/sprint-backlog.md` | Status das sprints |
| `docs/GOD_BACKLOG.md` | Checklist completo do início ao fim |

## Licença

Projeto acadêmico — sem fins comerciais.
