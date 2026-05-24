# Product Vision — Sistema de Gerenciamento de Aviação Comercial

| Documento | Product Vision |
| :--- | :--- |
| **Versão** | 1.0 |
| **Contexto** | Oficina de Integração de Software — Faculdade |
| **Stack** | Java + Maven + MariaDB + JDBC |

---

## 1. Propósito do Produto

**Para** administradores e atendentes de companhias aéreas que precisam gerenciar operações de aviação comercial,
**o** Sistema de Gerenciamento de Aviação Comercial **é um** aplicativo desktop em Java
**que** permite cadastrar e consultar aeronaves, companhias aéreas, aeroportos, passageiros, voos e reservas,
**diferente de** planilhas ou sistemas genéricos,
**o nosso produto** oferece persistência real em banco relacional com integridade referencial garantida.

---

## 2. Contexto Acadêmico

Projeto desenvolvido no âmbito da **Oficina de Integração de Software** da faculdade. O grande desafio é o ingresso tardio — enquanto a equipe principal já desenvolve em Python, este projeto adota **Personal Scrum** (Scrum adaptado para 1 desenvolvedor) para entregar um MVP validável pelo professor no prazo.

### 2.1 Stack Tecnológica

| Camada | Tecnologia |
| :--- | :--- |
| Linguagem | Java (versão 17+) |
| Build | Apache Maven (`pom.xml`) |
| Interface | Java Swing (GUI) |
| Conectividade | JDBC com `mysql-connector-j` |
| SGBD | MariaDB 10.4.22 (XAMPP) |
| Admin DB | phpMyAdmin |
| IDE | Eclipse IDE (Windows) / VS Code ou terminal (Linux) |

### 2.2 Ambiente Alvo (Laboratório)

- **SO:** Microsoft Windows
- **IDE:** Eclipse IDE com suporte nativo a Maven
- **Banco:** MariaDB/MySQL via XAMPP + phpMyAdmin
- **Importação:** Projeto Maven importado via `pom.xml` (sem `.jar` manuais)

### 2.3 Portabilidade Linux → Windows

O desenvolvimento doméstico usa Linux (XAMPP/MariaDB) e a apresentação será no Windows da faculdade. A string de conexão JDBC usa `localhost:3306` — idêntica em ambos os SOs. Nenhum caminho absoluto é usado.

---

## 3. Definição de Pronto (Definition of Done)

Nenhuma funcionalidade é considerada concluída sem cumprir **todos** os critérios abaixo:

- [x] **Persistência Real:** Toda operação reflete imediatamente no banco (sem dados em memória)
- [x] **Fail-Safe:** Exceções SQL (FK violada, duplicidade) são capturadas com mensagens amigáveis — o sistema nunca "quebra"
- [x] **Recursos Fechados:** Toda conexão JDBC usa `try-with-resources` ou blocos `finally`
- [x] **Compilação Limpa:** `mvn clean compile` sem erros
- [x] **Portabilidade Windows:** Roda ao importar no Eclipse sem adaptações

---

## 4. Stakeholders

| Papel | Descrição |
| :--- | :--- |
| **Professor** | Cliente / Avaliador — valida o MVP |
| **Felipe (Dev)** | Único desenvolvedor, Product Owner, Scrum Master |
| **Colegas (equipe Python)** | Referência de ritmo e progresso |
