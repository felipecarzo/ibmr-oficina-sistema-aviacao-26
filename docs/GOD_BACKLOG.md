# ☰ GOD BACKLOG — Sistema de Aviação Comercial

> A existência completa do software em micro-esforços.
> Cada linha que você riscar é um passo real da sua jornada.
> **Riscou, fez. Sem abstração.**

| Dono | Felipe |
| :--- | :--- |
| **Início** | 20-Maio-2026 |
| **Término** | — |
| **Stack** | Java + Maven + MariaDB + JDBC |
| **Contexto** | Oficina de Integração de Software — Faculdade |

---

## ⚙ PREÂMBULO — Como usar este documento

Este guia divide a **existência completa do software** em 9 PARTES.

```
PARTE 0  → Gênese         (a ideia)
PARTE 1  → Descoberta     (o que construir)
PARTE 2  → Planejamento    (como e quando)
PARTE 3  → Product Backlog (o estoque)
PARTE 4  → Sprints         (a execução ágil)
PARTE 5  → Implementação   (o código)
PARTE 6  → Testes          (a validação)
PARTE 7  → Produção Acadêmica (diagramas + docs)
PARTE 8  → Entrega         (apresentação)
```

Cada item tem um `[ ]` — risque quando concluir.
Use como seu diário de bordo pessoal.

---

## PARTE 0 — GÊNESE (Concepção do Projeto)

*O momento zero. O projeto ainda não existe, só a necessidade.*

### 0.1 Identificar o problema de negócio

- [x] **P0.1.1** — Entender a demanda: sistema para gerenciar operações de aviação comercial
- [x] **P0.1.2** — Identificar as dores: dados espalhados, sem centralização, sem interface
- [x] **P0.1.3** — Definir o público-alvo: administradores e atendentes de companhias aéreas
- [x] **P0.1.4** — Definir o contexto acadêmico: oficina de integração de software

### 0.2 Pesquisar o domínio (Aviação Comercial)

- [x] **P0.2.1** — Estudar entidades do domínio: aeronave, companhia aérea, aeroporto, passageiro, voo, reserva
- [x] **P0.2.2** — Compreender relacionamentos: cada aeronave pertence a uma cia, cada voo parte de um aeroporto, cada reserva vincula passageiro a voo
- [x] **P0.2.3** — Identificar regras de negócio elementares: assento único por voo, CNPJ válido, etc.

### 0.3 Definir o propósito do sistema

- [x] **P0.3.1** — Redigir a visão do produto (1 parágrafo)
- [x] **P0.3.2** — Definir objetivos mensuráveis: cadastrar X entidades, consultar Y dados, gerar Z relatórios
- [x] **P0.3.3** — Documentar em `docs/vision.md`

### 0.4 Registrar lições da Gênese

- [x] **P0.4.1** — O que você aprendeu sobre o domínio?
- [x] **P0.4.2** — Quais dúvidas levar para o professor?

---

## PARTE 1 — DESCOBERTA (PO / BSA)

*O trabalho de Product Owner. Levantar, modelar, validar.*

### 1.1 Levantar requisitos

- [x] **P1.1.1** — Entrevistar o "cliente" (professor / orientador)
- [x] **P1.1.2** — Coletar requisitos funcionais (o que o sistema FAZ)
- [x] **P1.1.3** — Coletar requisitos não-funcionais (performance, portabilidade, usabilidade)
- [x] **P1.1.4** — Validar com o dump SQL existente (`docs/sistema_aviacao.sql`)

### 1.2 Mapear entidades do domínio

- [x] **P1.2.1** — Listar entidades: Aeronave, CiaAerea, AeronaveCia, Aeroporto, Passageiro, Voo, Reserva
- [x] **P1.2.2** — Identificar atributos de cada entidade (consultar SQL dump)
- [x] **P1.2.3** — Identificar chaves primárias e estrangeiras
- [x] **P1.2.4** — Classificar entidades: independentes vs dependentes

**Entidades independentes:** Passageiro, Aeroporto, CiaAerea
**Entidades dependentes:** Aeronave, AeronaveCia, Voo, Reserva

### 1.3 Modelar banco de dados (DER)

- [x] **P1.3.1** — Desenhar DER conceitual (no `docs/technical-design.md`)
- [x] **P1.3.2** — Desenhar DER lógico (tabelas, colunas, FKs)
- [x] **P1.3.3** — Verificar normalização (3FN)
- [x] **P1.3.4** — Comparar com o dump SQL existente

### 1.4 Criar / validar o script SQL

- [x] **P1.4.1** — Importar `sistema_aviacao.sql` no phpMyAdmin
- [x] **P1.4.2** — Verificar estrutura de todas as tabelas
- [x] **P1.4.3** — Verificar constraints (PK, FK, NOT NULL)
- [x] **P1.4.4** — Verificar dados seed (10 registros por tabela)

### 1.5 Registrar lições da Descoberta

- [x] **P1.5.1** — Quais requisitos ficaram claros?
- [x] **P1.5.2** — Quais ainda são dúvidas?
- [x] **P1.5.3** — O que muda no planejamento original?

---

## PARTE 2 — PLANEJAMENTO ESTRATÉGICO

*Roadmap, Product Backlog, MVP, DoD. O mapa do tesouro.*

### 2.1 Definir Roadmap

- [x] **P2.1.1** — Criar visão macro do projeto em sprints
- [x] **P2.1.2** — Definir marcos (milestones) de entrega
- [x] **P2.1.3** — Estimar duração de cada sprint
- [x] **P2.1.4** — Documentar em `docs/product-backlog.md` e `docs/vision.md`

### 2.2 Criar o Product Backlog

- [x] **P2.2.1** — Listar todas as funcionalidades desejadas (brain dump)
- [x] **P2.2.2** — Escrever User Stories (padrão: "Como um... eu quero... para que...")
- [x] **P2.2.3** — Definir critérios de aceite para cada US
- [x] **P2.2.4** — Estimar esforço relativo (Story Points ou T-shirt sizing: P/M/G)

### 2.3 Priorizar (Técnica MoSCoW)

- [x] **P2.3.1** — Must have: CRUD Aeronave, CRUD Cia Aérea, CRUD Aeroporto, CRUD Passageiro, CRUD Voo, Reserva
- [x] **P2.3.2** — Should have: busca por ID, filtros, validação de dados
- [x] **P2.3.3** — Could have: relatórios, histórico de reservas por passageiro
- [x] **P2.3.4** — Won't have: interface gráfica complexa, autenticação de usuário

### 2.4 Definir o MVP (Mínimo Produto Viável)

- [x] **P2.4.1** — Escopo mínimo para validação do professor
- [x] **P2.4.2** — Funcionalidades obrigatórias: menu funcional + CRUD Aeronave + Consulta Voos + Reserva
- [x] **P2.4.3** — Funcionalidades de suporte: cadastros de Cia, Aeroporto, Passageiro
- [x] **P2.4.4** — Documentar escopo do MVP no `docs/vision.md`

### 2.5 Criar Casos de Uso

- [x] **P2.5.1** — Escrever UC-01 a UC-08 (em `docs/use-cases.md`)
- [x] **P2.5.2** — Validar fluxos principais e alternativos
- [x] **P2.5.3** — Validar regras de negócio (RN-01 a RN-21)

### 2.6 Definir DoD (Definition of Done)

- [x] **P2.6.1** — Persistência real no banco (sem dados mockados)
- [x] **P2.6.2** — Tratamento de exceções (sistema não quebra)
- [x] **P2.6.3** — Fechamento de recursos (try-with-resources)
- [x] **P2.6.4** — Compilação limpa com Maven
- [x] **P2.6.5** — Portabilidade Windows

### 2.7 Registrar lições do Planejamento

- [x] **P2.7.1** — O plano está factível no prazo da faculdade?
- [x] **P2.7.2** — Quais riscos você identificou?

---

## PARTE 3 — PRODUCT BACKLOG (Estoque de Funcionalidades)

> ✅ Concluído. 13 user stories documentadas em `docs/product-backlog.md`.

*Todas as histórias que o sistema precisa contar.*

### 3.1 User Stories

#### Épico 1: Gerenciamento de Entidades Base

| ID | Como um... | Eu quero... | Para que... | Prioridade | Estimativa |
| :--- | :--- | :--- | :--- | :--- | :--- |
| US-01 | Administrador | cadastrar aeronaves com modelo, capacidade e fabricante | o sistema tenha registro de todas as aeronaves disponíveis | Must | M |
| US-02 | Administrador | consultar a frota de aeronaves cadastradas | eu possa visualizar rapidamente os dados de cada aeronave | Must | P |
| US-03 | Administrador | atualizar dados de uma aeronave existente | corrigir informações desatualizadas | Should | P |
| US-04 | Administrador | desativar uma aeronave sem excluí-la | manter o histórico sem permitir novos uso | Should | P |
| US-05 | Administrador | cadastrar companhias aéreas | o sistema reconheça as empresas operantes | Must | M |
| US-06 | Administrador | cadastrar aeroportos com nome, sigla e cidade | os voos tenham origens e destinos válidos | Must | M |
| US-07 | Administrador | cadastrar passageiros | o sistema tenha a base de clientes | Must | M |

#### Épico 2: Operações de Voo e Reserva

| ID | Como um... | Eu quero... | Para que... | Prioridade | Estimativa |
| :--- | :--- | :--- | :--- | :--- | :--- |
| US-08 | Administrador | cadastrar voos com horários e aeroporto de partida | a grade de voos esteja disponível no sistema | Must | G |
| US-09 | Administrador | consultar voos por origem ou destino | eu possa filtrar rotas rapidamente | Should | M |
| US-10 | Atendente | vincular uma aeronave a uma companhia | cada aeronave tenha uma operadora responsável | Must | M |
| US-11 | Atendente | efetuar uma reserva para um passageiro em um voo | o passageiro tenha seu assento garantido | Must | G |
| US-12 | Atendente | cancelar uma reserva | liberar o assento caso o passageiro desista | Should | M |
| US-13 | Atendente | visualizar assentos disponíveis em um voo | oferecer opções ao passageiro durante a reserva | Could | P |

### 3.2 Mapeamento UC x US x Classes x DAO

| Caso de Uso | User Stories | Modelo | DAO |
| :--- | :--- | :--- | :--- |
| UC-01 — Gerenciar Aeronaves | US-01, US-02, US-03, US-04 | `Aeronave` | `AeronaveDAO` |
| UC-02 — Gerenciar Cias Aéreas | US-05 | `CiaAerea` | `CiaAereaDAO` |
| UC-03 — Gerenciar Aeroportos | US-06 | `Aeroporto` | `AeroportoDAO` |
| UC-04 — Gerenciar Passageiros | US-07 | `Passageiro` | `PassageiroDAO` |
| UC-05 — Gerenciar Voos | US-08, US-09 | `Voo` | `VooDAO` |
| UC-06 — Vincular Aeronave a Cia | US-10 | `AeronaveCia` | `AeronaveCiaDAO` |
| UC-07 — Efetuar Reserva | US-11, US-13 | `Reserva` | `ReservaDAO` |
| UC-08 — Cancelar Reserva | US-12 | `Reserva` | `ReservaDAO` |

---

## PARTE 4 — SPRINTS (A Execução Ágil)

*Cada sprint é um ciclo completo: planejar → codificar → testar → revisar.*

### SPRINT A — Fundação e Conexão

**Duração:** — | **Início:** — | **Fim:** —

**Objetivo:** Estabelecer a infraestrutura do projeto Java com Maven e a camada de conexão com o banco MariaDB.

**Backlog da Sprint:**
- [x] **SA-01** — Criar projeto Maven (archetype)
- [x] **SA-02** — Configurar `pom.xml` com dependência `mysql-connector-j`
- [x] **SA-03** — Criar estrutura de pacotes (model, dao, service, main)
- [x] **SA-04** — Criar `db.properties` com credenciais
- [x] **SA-05** — Implementar `ConnectionFactory` (Singleton)
- [x] **SA-06** — Testar conexão com o banco
- [x] **SA-07** — `mvn clean compile` sem erros
- [x] **SA-08** — Registrar impedimentos e aprendizados

**DoD da Sprint:** Projeto compila, conexão com banco funciona.

---

### SPRINT B — Modelos e DAOs das Entidades Independentes

**Duração:** — | **Início:** — | **Fim:** —

**Objetivo:** Implementar POJOs e DAOs de Passageiro, Aeroporto e CiaAerea.

**Backlog da Sprint:**
- [x] **SB-01** — Implementar POJO `Passageiro`
- [x] **SB-02** — Implementar `PassageiroDAO` (CRUD)
- [x] **SB-03** — Implementar POJO `Aeroporto`
- [x] **SB-04** — Implementar `AeroportoDAO` (CRUD)
- [x] **SB-05** — Implementar POJO `CiaAerea`
- [x] **SB-06** — Implementar `CiaAereaDAO` (CRUD)
- [x] **SB-07** — Testar cada DAO via console (main temporária)
- [x] **SB-08** — Verificar dados no phpMyAdmin após inserts
- [x] **SB-09** — Registrar impedimentos e aprendizados

**DoD da Sprint:** 3 entidades com CRUD funcionando e testado.

---

### SPRINT C — Modelos e DAOs das Entidades Dependentes

**Duração:** — | **Início:** — | **Fim:** —

**Objetivo:** Implementar POJOs e DAOs de Aeronave, AeronaveCia, Voo e Reserva.

**Backlog da Sprint:**
- [ ] **SC-01** — Implementar POJO `Aeronave`
- [ ] **SC-02** — Implementar `AeronaveDAO` (CRUD + buscar por fabricante)
- [ ] **SC-03** — Implementar POJO `AeronaveCia`
- [ ] **SC-04** — Implementar `AeronaveCiaDAO` (vínculo + buscas por aeronave/cia)
- [ ] **SC-05** — Implementar POJO `Voo`
- [ ] **SC-06** — Implementar `VooDAO` (CRUD + buscar por origem/destino)
- [ ] **SC-07** — Implementar POJO `Reserva`
- [ ] **SC-08** — Implementar `ReservaDAO` (insert, delete, findByVoo, findByPassageiro, verificação de assento)
- [ ] **SC-09** — Testar integridade referencial (FK violada → exceção tratada)
- [ ] **SC-10** — Testar cada DAO via console
- [ ] **SC-11** — Registrar impedimentos e aprendizados

**DoD da Sprint:** 4 entidades com CRUD + regras de integridade referencial testadas.

---

### SPRINT D — Interface do Usuário

**Duração:** — | **Início:** — | **Fim:** —

**Objetivo:** Construir o menu interativo (console) integrando todos os DAOs.

**Backlog da Sprint:**
- [ ] **SD-01** — Criar `SistemaAviação.java` com menu principal
- [ ] **SD-02** — Implementar submenu Gerenciar Aeronaves
- [ ] **SD-03** — Implementar submenu Gerenciar Companhias
- [ ] **SD-04** — Implementar submenu Gerenciar Aeroportos
- [ ] **SD-05** — Implementar submenu Gerenciar Passageiros
- [ ] **SD-06** — Implementar submenu Gerenciar Voos
- [ ] **SD-07** — Implementar Vincular Aeronave a Companhia
- [ ] **SD-08** — Implementar Efetuar Reserva
- [ ] **SD-09** — Implementar Cancelar Reserva
- [ ] **SD-10** — Implementar `Validador.java` com todas as validações
- [ ] **SD-11** — Integrar validações nas entradas do usuário
- [ ] **SD-12** — Tratar todas as exceções com mensagens amigáveis
- [ ] **SD-13** — Testar fluxo completo (menu → operação → banco → feedback)
- [ ] **SD-14** — Registrar impedimentos e aprendizados

**DoD da Sprint:** MVP funcional de ponta a ponta no ambiente Linux.

---

### SPRINT E — Portabilidade e Polimento

**Duração:** — | **Início:** — | **Fim:** —

**Objetivo:** Garantir que o sistema rode no Windows da faculdade sem adaptações.

**Backlog da Sprint:**
- [ ] **SE-01** — Verificar encoding (acentos no console Windows)
- [ ] **SE-02** — Testar `mvn clean compile` no Eclipse
- [ ] **SE-03** — Verificar caminhos de recursos (`db.properties` via classpath)
- [ ] **SE-04** — Exportar projeto como ZIP para importação no Eclipse
- [ ] **SE-05** — Registrar instruções de setup para Windows
- [ ] **SE-06** — Registrar impedimentos e aprendizados

**DoD da Sprint:** Projeto importável e executável no Eclipse Windows.

---

## PARTE 5 — IMPLEMENTAÇÃO TÉCNICA (O Código)

*Passo a passo real da construção do software. Mão no teclado.*

### Fase 5.0 — Setup do Projeto

- [x] **5.0.1** — Criar projeto Maven
- [x] **5.0.2** — Adicionar `mysql-connector-j` no `pom.xml`
- [x] **5.0.3** — Alterar `maven.compiler.source` e `target` para 17
- [x] **5.0.4** — Criar pacotes: `model`, `dao`, `service`, `main`
- [x] **5.0.5** — Criar `src/main/resources/db.properties`
- [x] **5.0.6** — Verificar MariaDB rodando e banco criado
- [x] **5.0.7** — `mvn clean compile` → 0 erros

### Fase 5.1 — ConnectionFactory

**Arquivo:** `src/main/java/com/aviacao/dao/ConnectionFactory.java`

- [x] **5.1.1** — Criar atributo `private static ConnectionFactory instance`
- [x] **5.1.2** — Criar atributos `url`, `user`, `password` (carregar de `db.properties`)
- [x] **5.1.3** — Construtor `private` (carrega properties)
- [x] **5.1.4** — `public static ConnectionFactory getInstance()`
- [x] **5.1.5** — `public Connection getConnection()` → `DriverManager.getConnection(url, user, password)`
- [x] **5.1.6** — `public void close(Connection conn)` e `close(Connection, PreparedStatement, ResultSet)`
- [x] **5.1.7** — Criar `TesteConexao.java` temporário → imprimir "Conexão OK"

### Fase 5.2 — POJOs (Model)

**Pacote:** `src/main/java/com/aviacao/model/`

#### 5.2.1 — Aeronave.java
- [ ] Atributos: `int idAeronave`, `String modelo`, `int capacidade`, `int envergadura`, `String fabricante`, `char statusAtivo`
- [ ] Construtores (vazio + completo)
- [ ] Getters e setters
- [ ] `toString()`

#### 5.2.2 — CiaAerea.java
- [ ] Atributos: `int idCia`, `String nome`, `String cnpj`, `String email`, `char statusAtivo`
- [ ] Construtores, getters/setters, toString

#### 5.2.3 — AeronaveCia.java
- [ ] Atributos: `int idAeronave`, `int idCia`, `LocalDate dataAquisicao`
- [ ] Construtores, getters/setters, toString

#### 5.2.4 — Aeroporto.java
- [ ] Atributos: `int codAeroporto`, `String nome`, `String sigla`, `String cidade`
- [ ] Construtores, getters/setters, toString

#### 5.2.5 — Passageiro.java
- [ ] Atributos: `int idPassageiro`, `String nome`, `String email`, `String tel`, `LocalDate dtNasc`
- [ ] Construtores, getters/setters, toString

#### 5.2.6 — Voo.java
- [ ] Atributos: `String codVoo`, `int horaPartida`, `int horaChegada`, `int codAeroporto`, `String cidadeOrigem`, `String cidadeDestino`
- [ ] Construtores, getters/setters, toString

#### 5.2.7 — Reserva.java
- [ ] Atributos: `String codReserva`, `int idPassageiro`, `String codVoo`, `String assento`, `LocalDate dtReserva`
- [ ] Construtores, getters/setters, toString

### Fase 5.3 — DAOs (Data Access Object)

**Pacote:** `src/main/java/com/aviacao/dao/`

Template de implementação para cada DAO:
```java
public class XxxDAO {
    private ConnectionFactory cf = ConnectionFactory.getInstance();

    public Xxx findById(int id) {
        String sql = "SELECT * FROM tabela WHERE pk = ?";
        // try-with-resources → PreparedStatement → ResultSet → montar objeto
    }

    public List<Xxx> findAll() { /* ... */ }
    public void insert(Xxx obj) { /* PreparedStatement com RETURN_GENERATED_KEYS */ }
    public void update(Xxx obj) { /* ... */ }
    public void delete(int id)  { /* ... */ }
}
```

#### 5.3.1 — AeronaveDAO.java
- [ ] `findById(int id)` → `SELECT * FROM aeronave WHERE id_aeronave = ?`
- [ ] `findAll()` → `SELECT * FROM aeronave ORDER BY id_aeronave`
- [ ] `insert(Aeronave a)` → `INSERT INTO aeronave (modelo, capacidade, envergadura, fabricante, status_ativo) VALUES (?, ?, ?, ?, ?)`
- [ ] `update(Aeronave a)` → `UPDATE aeronave SET modelo=?, capacidade=?, envergadura=?, fabricante=?, status_ativo=? WHERE id_aeronave=?`
- [ ] `delete(int id)` → `DELETE FROM aeronave WHERE id_aeronave=?`

#### 5.3.2 — CiaAereaDAO.java
- [ ] `findById(int id)`, `findAll()`, `insert(CiaAerea)`, `update(CiaAerea)`, `delete(int)`

#### 5.3.3 — AeroportoDAO.java
- [ ] `findById(int id)`, `findAll()`, `insert(Aeroporto)`, `update(Aeroporto)`, `delete(int)`

#### 5.3.4 — PassageiroDAO.java
- [ ] `findById(int id)`, `findAll()`, `insert(Passageiro)`, `update(Passageiro)`, `delete(int)`

#### 5.3.5 — AeronaveCiaDAO.java
- [ ] `findByIds(int idAeronave, int idCia)` → consulta por chave composta
- [ ] `findAll()`
- [ ] `findByAeronave(int idAeronave)` → lista vínculos de uma aeronave
- [ ] `findByCia(int idCia)` → lista vínculos de uma cia
- [ ] `insert(AeronaveCia ac)` → INSERT com verificação de FK existente
- [ ] `delete(int idAeronave, int idCia)` → DELETE por chave composta

#### 5.3.6 — VooDAO.java
- [ ] `findById(String codVoo)`
- [ ] `findAll()`
- [ ] `insert(Voo v)`, `update(Voo v)`, `delete(String codVoo)`
- [ ] `findByOrigem(String cidade)` → LIKE
- [ ] `findByDestino(String cidade)` → LIKE

#### 5.3.7 — ReservaDAO.java
- [ ] `findById(String codReserva)`
- [ ] `findAll()`
- [ ] `insert(Reserva r)`
- [ ] `delete(String codReserva)`
- [ ] `findByVoo(String codVoo)`
- [ ] `findByPassageiro(int idPassageiro)`
- [ ] `assentoOcupado(String codVoo, String assento)` → `SELECT COUNT(*) ...` retorna boolean

### Fase 5.4 — Validador (Service)

**Arquivo:** `src/main/java/com/aviacao/service/Validador.java`

- [ ] `static boolean validarCnpj(String cnpj)` — verifica 14 dígitos
- [ ] `static boolean validarEmail(String email)` — contém '@' e domínio
- [ ] `static boolean validarSigla(String sigla)` — exatamente 3 letras
- [ ] `static boolean validarTelefone(String tel)` — 10-11 dígitos
- [ ] `static boolean validarDataNascimento(LocalDate dt)` — não futura
- [ ] `static boolean validarCapacidade(int cap)` — 1 a 999
- [ ] `static boolean validarHorario(int hora)` — 0 a 2359
- [ ] `static boolean validarStatusAtivo(char status)` — 'S' ou 'N'
- [ ] `static boolean validarAssento(String assento)` — padrão: número + letra (ex: 12A)

### Fase 5.5 — Interface (Menu)

**Arquivo:** `src/main/java/com/aviacao/main/SistemaAviação.java`

- [ ] **5.5.1** — Criar classe com `Scanner`, instâncias dos DAOs, método `main()`
- [ ] **5.5.2** — `iniciar()` — loop infinito até opção 0
- [ ] **5.5.3** — `exibirMenu()` — imprime o menu principal
- [ ] **5.5.4** — `gerenciarAeronaves()` → submenu com Cadastrar / Listar / Buscar ID / Atualizar / Excluir
- [ ] **5.5.5** — `gerenciarCompanhias()` → submenu análogo
- [ ] **5.5.6** — `gerenciarAeroportos()` → submenu análogo
- [ ] **5.5.7** — `gerenciarPassageiros()` → submenu análogo
- [ ] **5.5.8** — `gerenciarVoos()` → submenu análogo + filtros origem/destino
- [ ] **5.5.9** — `vincularAeronaveCia()` → ler IDs + data, validar, inserir
- [ ] **5.5.10** — `efetuarReserva()` → ler dados, verificar assento, inserir
- [ ] **5.5.11** — `cancelarReserva()` → ler código, confirmar, deletar
- [ ] **5.5.12** — Tratar `InputMismatchException` (entrada inválida do usuário)
- [ ] **5.5.13** — Tratar `SQLIntegrityConstraintViolationException` (FK violada)
- [ ] **5.5.14** — Tratar `SQLException` genérica com mensagem amigável

### Fase 5.6 — Compilação Final

- [ ] **5.6.1** — `mvn clean compile` → 0 erros
- [ ] **5.6.2** — Executar `mvn exec:java` ou rodar pelo Eclipse
- [ ] **5.6.3** — Navegar por todas as opções do menu
- [ ] **5.6.4** — Verificar dados no phpMyAdmin

---

## PARTE 6 — TESTES E VALIDAÇÃO

*Verificar se tudo funciona. Caçar bugs.*

### 6.1 Teste Integrado (Checklist)

**Aeronave**
- [ ] Cadastrar aeronave → aparece no banco
- [ ] Listar aeronaves → exibe todas ordenadas
- [ ] Buscar por ID → exibe dados corretos
- [ ] Atualizar capacidade → reflete na listagem
- [ ] Tentar cadastrar com capacidade = 0 → sistema rejeita
- [ ] Desativar aeronave (status_ativo = 'N') → não aparece em consultas de ativas

**Companhia Aérea**
- [ ] Cadastrar com CNPJ de 14 dígitos → sucesso
- [ ] Cadastrar com CNPJ inválido → rejeita
- [ ] Excluir companhia com aeronave vinculada → bloqueia com mensagem

**Aeroporto**
- [ ] Cadastrar com sigla de 3 letras → sucesso
- [ ] Cadastrar com sigla de 2 letras → rejeita

**Passageiro**
- [ ] Cadastrar com data futura → rejeita
- [ ] Cadastrar com e-mail sem @ → rejeita

**Voo**
- [ ] Cadastrar voo com aeroporto inexistente → erro tratado
- [ ] Cadastrar voo com origem = destino → rejeita

**Reserva**
- [ ] Reservar assento → aparece na tabela
- [ ] Reservar mesmo assento no mesmo voo → bloqueia
- [ ] Cancelar reserva → assento disponível novamente

**Geral**
- [ ] Sair do sistema → fecha scanner e encerra
- [ ] Menu aceita apenas números nas opções
- [ ] Strings com acentos são exibidas corretamente

### 6.2 Teste de Ambiente Windows

- [ ] Importar projeto no Eclipse
- [ ] `mvn clean compile` sem erros
- [ ] Executar sistema
- [ ] Verificar encoding de acentos

---

## PARTE 7 — PRODUÇÃO ACADÊMICA

*O que a faculdade vai avaliar além do código.*

### 7.1 Diagrama de Casos de Uso

- [x] Criar diagrama com ator "Administrador" e "Atendente"
- [x] Incluir UC-01 a UC-08
- [x] Relacionar atores aos casos de uso (include/extend)
- [x] Ferramenta: draw.io → exportado PNG em `docs/images/diagrama-casos-de-uso.png`

### 7.2 Diagrama de Classes UML

- [x] Incluir todas as 7 entidades (model)
- [x] Incluir ConnectionFactory, Validador, SistemaAviação
- [x] Mostrar atributos e métodos
- [x] Mostrar relacionamentos (associação, dependência)
- [x] Versões: PNG (`docs/images/`) + Mermaid (`docs/class-diagram.md`) + draw.io

### 7.3 Diagrama de Sequência

- [x] Criar 9 diagramas (todos os UCs):
  - [x] UC-01 — Consulta Geral de Aeronaves
  - [x] UC-01 — Cadastrar Aeronave
  - [x] UC-02 — Cadastrar Companhia
  - [x] UC-03 — Cadastrar Aeroporto
  - [x] UC-04 — Cadastrar Passageiro
  - [x] UC-05 — Cadastrar Voo
  - [x] UC-06 — Vincular Aeronave a Companhia
  - [x] UC-07 — Efetuar Reserva
  - [x] UC-08 — Cancelar Reserva
- [x] Mostrar: Ator → Menu → DAO → Banco → resposta
- [x] Formatos: PNG em `docs/images/` + draw.io em `docs/legacy/`

### 7.4 Diagrama Entidade-Relacionamento (DER)

- [ ] DER conceitual (entidades + relacionamentos)
- [ ] DER lógico (tabelas com colunas e FKs)
- [ ] Baseado no dump SQL existente

### 7.5 Relatório Técnico

- [ ] **Introdução:** contexto, objetivo, escopo
- [ ] **Tecnologias:** Java, Maven, MariaDB, JDBC — por que cada uma
- [ ] **Arquitetura:** camadas (model, dao, service, main)
- [ ] **Decisões técnicas:** por que Singleton no ConnectionFactory, por que DAO pattern
- [ ] **Dificuldades encontradas:** encoding, integridade referencial, portabilidade
- [ ] **Conclusão:** o que aprendeu, o que faria diferente

### 7.6 Slides de Apresentação

- [ ] Slide 1 — Capa (nome do projeto, integrante, professor, disciplina)
- [ ] Slide 2 — Problema e Motivação
- [ ] Slide 3 — Tecnologias Utilizadas
- [ ] Slide 4 — Arquitetura do Sistema (camadas)
- [ ] Slide 5 — Diagrama de Casos de Uso
- [ ] Slide 6 — Diagrama de Classes
- [ ] Slide 7 — Demonstração (prints ou ao vivo)
- [ ] Slide 8 — Dificuldades e Aprendizados
- [ ] Slide 9 — Conclusão e Perguntas

---

## PARTE 8 — ENTREGA

*O checkout. O software existe, foi validado, está documentado.*

### 8.1 Verificação Final

- [ ] **Código:** `mvn clean compile` sem erros
- [ ] **Banco:** dump SQL exportado (`sistema_aviacao.sql` atualizado)
- [ ] **Docs:** guia_conceitual.md, roadmap.md, GOD_BACKLOG.md
- [ ] **Diagramas:** casos de uso, classes, sequência, DER
- [ ] **Relatório:** documento técnico completo
- [ ] **Slides:** apresentação pronta

### 8.2 Exportação do Projeto

- [ ] `mvn clean package` → gerar JAR (opcional)
- [ ] Compactar pasta `sistema-aviacao/` em ZIP
- [ ] Incluir `docs/` no ZIP
- [ ] Incluir `db.properties` de exemplo
- [ ] Testar importação do ZIP no Eclipse Windows

### 8.3 Apresentação

- [ ] Projeto rodando (ao vivo ou por prints)
- [ ] Explicar arquitetura
- [ ] Mostrar casos de uso
- [ ] Mostrar código relevante (ConnectionFactory, DAO, Menu)
- [ ] Responder perguntas

### 8.4 Pós-entrega

- [ ] Registrar o que aprendeu
- [ ] O que faria diferente
- [ ] Próximos passos (se houver versão 2.0)

---

## APÊNDICE A — Diagrama de Classes UML (Completo)

```
┌──────────────────────────────────────────────────────────────────────────┐
│                                                                          │
│  ┌──────────────────────┐    ┌──────────────────────┐                   │
│  │      Aeronave        │    │      CiaAerea        │                   │
│  ├──────────────────────┤    ├──────────────────────┤                   │
│  │ - idAeronave: int    │    │ - idCia: int         │                   │
│  │ - modelo: String     │    │ - nome: String       │                   │
│  │ - capacidade: int    │    │ - cnpj: String       │                   │
│  │ - envergadura: int   │    │ - email: String      │                   │
│  │ - fabricante: String │    │ - statusAtivo: char  │                   │
│  │ - statusAtivo: char  │    ├──────────────────────┤                   │
│  ├──────────────────────┤    │ + getters/setters    │                   │
│  │ + getters/setters    │    │ + toString()         │                   │
│  │ + toString()         │    └──────────┬───────────┘                   │
│  └──────────┬───────────┘               │                               │
│             │          ┌────────────────┼───────────────┐               │
│             │          │                │               │               │
│             │          │ ┌──────────────▼─────────────┐ │               │
│             │          │ │        AeronaveCia         │ │               │
│             │          │ ├────────────────────────────┤ │               │
│             │          │ │ - idAeronave: int          │ │               │
│             │          │ │ - idCia: int               │ │               │
│             │          │ │ - dataAquisicao: LocalDate │ │               │
│             │          │ ├────────────────────────────┤ │               │
│             │          │ │ + getters/setters          │ │               │
│             │          │ │ + toString()               │ │               │
│             │          │ └────────────────────────────┘ │               │
│             │          │                                │               │
│  ┌──────────▼───────────┐    ┌──────────────────────┐   │               │
│  │      Aeroporto       │    │     Passageiro       │   │               │
│  ├──────────────────────┤    ├──────────────────────┤   │               │
│  │ - codAeroporto: int  │    │ - idPassageiro: int  │   │               │
│  │ - nome: String       │    │ - nome: String       │   │               │
│  │ - sigla: String      │    │ - email: String      │   │               │
│  │ - cidade: String     │    │ - tel: String        │   │               │
│  ├──────────────────────┤    │ - dtNasc: LocalDate  │   │               │
│  │ + getters/setters    │    ├──────────────────────┤   │               │
│  │ + toString()         │    │ + getters/setters    │   │               │
│  └──────────┬───────────┘    │ + toString()         │   │               │
│             │                └──────────┬───────────┘   │               │
│             │              ┌────────────┼───────────┐   │               │
│             │              │            │           │   │               │
│             │              │ ┌──────────▼───────────▼─┐ │               │
│             │              │ │        Reserva         │ │               │
│             │              │ ├────────────────────────┤ │               │
│             │              │ │ - codReserva: String   │ │               │
│             │              │ │ - idPassageiro: int    │ │               │
│             │              │ │ - codVoo: String       │ │               │
│             │              │ │ - assento: String      │ │               │
│             │              │ │ - dtReserva: LocalDate │ │               │
│             │              │ ├────────────────────────┤ │               │
│             │              │ │ + getters/setters      │ │               │
│             │              │ │ + toString()           │ │               │
│             │              │ └────────────────────────┘ │               │
│             │              │                            │               │
│  ┌──────────▼──────────────┴──┐                         │               │
│  │           Voo              │                         │               │
│  ├───────────────────────────┤                         │               │
│  │ - codVoo: String          │                         │               │
│  │ - horaPartida: int        │                         │               │
│  │ - horaChegada: int        │                         │               │
│  │ - codAeroporto: int       │                         │               │
│  │ - cidadeOrigem: String    │                         │               │
│  │ - cidadeDestino: String   │                         │               │
│  ├───────────────────────────┤                         │               │
│  │ + getters/setters         │                         │               │
│  │ + toString()              │                         │               │
│  └───────────────────────────┘                         │               │
│                                                        │               │
│  ┌────────────────────────────────────────────────┐    │               │
│  │           ConnectionFactory                    │    │               │
│  ├────────────────────────────────────────────────┤    │               │
│  │ - instance: ConnectionFactory                  │    │               │
│  │ - url: String                                  │    │               │
│  │ - user: String                                 │    │               │
│  │ - password: String                             │    │               │
│  ├────────────────────────────────────────────────┤    │               │
│  │ + getInstance(): ConnectionFactory             │    │               │
│  │ + getConnection(): Connection                  │    │               │
│  │ + close(Connection): void                      │    │               │
│  │ + close(Connection, Pstmt, RS): void           │    │               │
│  └────────────────────────────────────────────────┘    │               │
│                                                        │               │
│  ┌────────────────────────────────────────────────┐    │               │
│  │              Validador                         │    │               │
│  ├────────────────────────────────────────────────┤    │               │
│  │ + validarCnpj(String): boolean                 │    │               │
│  │ + validarEmail(String): boolean                │    │               │
│  │ + validarSigla(String): boolean                │    │               │
│  │ + validarTelefone(String): boolean             │    │               │
│  │ + validarDataNascimento(LocalDate): boolean    │    │               │
│  │ + validarCapacidade(int): boolean              │    │               │
│  │ + validarHorario(int): boolean                 │    │               │
│  │ + validarStatusAtivo(char): boolean            │    │               │
│  │ + validarAssento(String): boolean              │    │               │
│  └────────────────────────────────────────────────┘    │               │
│                                                        │               │
└────────────────────────────────────────────────────────┘               │
                                                                          │
┌────────────────────────────────────────────────────────────────────────┐
│                     SistemaAviação                                    │
├────────────────────────────────────────────────────────────────────────┤
│ - scanner: Scanner                                                     │
│ - aeronaveDAO: AeronaveDAO                                             │
│ - ciaAereaDAO: CiaAereaDAO                                             │
│ - aeroportoDAO: AeroportoDAO                                           │
│ - passageiroDAO: PassageiroDAO                                          │
│ - vooDAO: VooDAO                                                       │
│ - reservaDAO: ReservaDAO                                               │
├────────────────────────────────────────────────────────────────────────┤
│ + main(String[]): void                                                 │
│ - iniciar(): void                                                      │
│ - exibirMenu(): void                                                   │
│ - gerenciarAeronaves(): void                                           │
│ - gerenciarCompanhias(): void                                          │
│ - gerenciarAeroportos(): void                                          │
│ - gerenciarPassageiros(): void                                         │
│ - gerenciarVoos(): void                                                │
│ - vincularAeronaveCia(): void                                          │
│ - efetuarReserva(): void                                               │
│ - cancelarReserva(): void                                              │
└────────────────────────────────────────────────────────────────────────┘
```

---

## APÊNDICE B — Mapa de Armadilhas Comuns

| # | Problema | Sintoma | Solução |
| :--- | :--- | :--- | :--- |
| 1 | ConnectionFactory não acha `db.properties` | NullPointerException | Carregar com `getClass().getClassLoader().getResourceAsStream()` |
| 2 | FK violada em AeronaveCia | SQLException | Verificar existência da aeronave e cia antes de inserir |
| 3 | Auto-increment não retorna ID | id = 0 após insert | Usar `PreparedStatement.RETURN_GENERATED_KEYS` + `getGeneratedKeys()` |
| 4 | LocalDate vs java.sql.Date | ClassCastException | Usar `Date.valueOf(localDate)` e `resultSet.getDate().toLocalDate()` |
| 5 | Acentos no Windows | caracteres `???` | `-Dfile.encoding=UTF-8` no launch do Eclipse |
| 6 | Driver MariaDB vs MySQL | ClassNotFoundException | Usar `org.mariadb.jdbc.Driver` (se for MariaDB) |
| 7 | Scanner nextInt + nextLine | pula linha | Usar `nextLine()` sempre e converter com `Integer.parseInt()` |

---

## APÊNDICE C — Glossário

| Termo | Definição |
| :--- | :--- |
| **POJO** | Plain Old Java Object — classe com atributos, getters e setters |
| **DAO** | Data Access Object — padrão que encapsula operações SQL |
| **JDBC** | Java Database Connectivity — API de conexão com bancos |
| **CRUD** | Create, Read, Update, Delete |
| **Integridade Referencial** | FK sempre aponta para registro existente |
| **Personal Scrum** | Scrum adaptado para 1 desenvolvedor |
| **MVP** | Minimum Viable Product — versão validável |
| **MoSCoW** | Técnica de priorização: Must/Should/Could/Won't |
| **DoD** | Definition of Done — critérios de conclusão |
| **UC** | Use Case — caso de uso |
| **US** | User Story — história de usuário |
| **DER** | Diagrama Entidade-Relacionamento |
| **UML** | Unified Modeling Language |

---

*Este documento é vivo. Atualize as datas conforme avança.*
*Boa jornada, camarada Felipe.*
