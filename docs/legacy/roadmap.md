# Sprint 0 — Inception
## Roadmap e Especificação do Sistema de Gerenciamento de Aviação Comercial

| Documento | Product Owner / BSA |
| :--- | :--- |
| **Versão** | 1.0 |
| **Metodologia** | Personal Scrum (Solo Dev) |
| **Stack** | Java Desktop + MariaDB + Maven + JDBC |

---

## 1. Visão do Produto

Sistema desktop em Java para gerenciamento de operações de aviação comercial, permitindo o cadastro e consulta de aeronaves, companhias aéreas, aeroportos, passageiros, voos e reservas. O sistema opera exclusivamente sobre um banco de dados relacional MariaDB, garantindo persistência real e integridade referencial.

---

## 2. Arquitetura do Sistema (Camadas)

```
 ┌─────────────────────────────────────┐
 │         Interface do Usuário        │  ← Console (Menu textual) ou Swing
 ├─────────────────────────────────────┤
 │           Camada de Serviço         │  ← Regras de negócio / validações
 ├─────────────────────────────────────┤
 │     Camada DAO (Data Access Object)  │  ← SQL + JDBC
 ├─────────────────────────────────────┤
 │         Banco de Dados MariaDB      │  ← sistema_aviacao
 └─────────────────────────────────────┘
```

### Modelo Relacional

```
 ┌──────────────┐     ┌──────────────────┐     ┌──────────────┐
 │   aeronave   │────<│   aeronave_cia   │>────│   cia_aerea  │
 └──────────────┘     └──────────────────┘     └──────────────┘
                                                      │
 ┌──────────────┐     ┌──────────────┐               │
 │  passageiro  │────<│   reserva    │>────┐          │
 └──────────────┘     └──────────────┘     │          │
                                           │          │
 ┌──────────────┐     ┌──────────────┐     │          │
 │  aeroporto   │────<│     voo      │─────┘          │
 └──────────────┘     └──────────────┘                │
                                                      │
                         ┌────────────────────────────┘
                         │
                    (FK voo → cia_aerea implícita
                     via aeronave_cia → aeronave)
```

---

## 3. Atores do Sistema

| Ator | Descrição |
| :--- | :--- |
| **Administrador** | Usuário com acesso total ao sistema. Gerencia aeronaves, companhias, aeroportos, passageiros, voos e reservas. |
| **Atendente** | Usuário operacional que consulta voos e efetua reservas para passageiros. |

*(No escopo do MVP, ambos os papéis podem ser unificados em um único ator "Operador".)*

---

## 4. Catálogo de Casos de Uso

### UC-01 — Gerenciar Aeronaves

**Ator:** Administrador

**Descrição:** Permite cadastrar, consultar, atualizar e desativar aeronaves no sistema.

**Pré-condições:** Banco de dados acessível.

**Fluxo Principal (Cadastro):**
1. Ator seleciona a opção "Cadastrar Aeronave".
2. Sistema solicita os dados: modelo, capacidade, envergadura, fabricante, status ativo.
3. Ator informa os dados e confirma.
4. Sistema valida os campos obrigatórios e tipos.
5. Sistema executa `INSERT INTO aeronave`.
6. Sistema exibe mensagem de sucesso com o ID gerado.

**Fluxo Principal (Consulta):**
1. Ator seleciona a opção "Consultar Aeronaves".
2. Sistema exibe listagem com todas as aeronaves (id, modelo, fabricante, capacidade, status).
3. Ator pode optar por filtrar por ID — sistema exibe detalhes de uma aeronave específica.

**Fluxo Alternativo (Atualização):**
1. Ator informa o ID da aeronave.
2. Sistema exibe os dados atuais e solicita os novos valores.
3. Ator altera os campos desejados.
4. Sistema valida e executa `UPDATE aeronave SET ... WHERE id_aeronave = ?`.

**Fluxo Alternativo (Exclusão / Desativação):**
1. Ator informa o ID da aeronave.
2. Sistema verifica se há vínculos em `aeronave_cia`.
3. Se houver vínculos, sistema alerta e sugere desativação lógica (status_ativo = 'N') em vez de exclusão física.
4. Sistema executa `DELETE` ou `UPDATE` conforme decisão.

**Pós-condição:** Dados persistidos no banco.

**Regras de Negócio:**
- RN-01: Capacidade deve ser um número inteiro positivo entre 1 e 999.
- RN-02: Envergadura deve ser um número inteiro positivo.
- RN-03: Status ativo aceita apenas 'S' (Sim) ou 'N' (Não).
- RN-04: Modelo é único por fabricante (não obrigatório no MVP, mas desejável).

---

### UC-02 — Gerenciar Companhias Aéreas

**Ator:** Administrador

**Descrição:** Permite cadastrar, consultar, atualizar e desativar companhias aéreas.

**Fluxo Principal (Cadastro):**
1. Ator seleciona "Cadastrar Companhia Aérea".
2. Sistema solicita: nome, CNPJ, e-mail, status ativo.
3. Ator informa os dados e confirma.
4. Sistema valida CNPJ (tamanho e caracteres), e-mail e campos obrigatórios.
5. Sistema executa `INSERT INTO cia_aerea`.
6. Sistema exibe mensagem de sucesso.

**Fluxo Principal (Consulta):**
1. Ator seleciona "Consultar Companhias Aéreas".
2. Sistema exibe listagem completa com nome, CNPJ e status.

**Fluxo Alternativo (Exclusão):**
1. Sistema verifica se existem aeronaves vinculadas via `aeronave_cia`.
2. Se houver, bloqueia exclusão física e sugere desativação lógica.

**Regras de Negócio:**
- RN-05: CNPJ deve conter exatamente 14 dígitos numéricos (sem formatação).
- RN-06: E-mail deve conter '@' e domínio válido.
- RN-07: Uma companhia com aeronaves vinculadas não pode ser excluída fisicamente.

---

### UC-03 — Gerenciar Aeroportos

**Ator:** Administrador

**Descrição:** Permite cadastrar, consultar, atualizar e excluir aeroportos.

**Fluxo Principal (Cadastro):**
1. Ator seleciona "Cadastrar Aeroporto".
2. Sistema solicita: nome, sigla (3 letras), cidade.
3. Ator informa os dados e confirma.
4. Sistema valida sigla (exatamente 3 caracteres alfabéticos maiúsculos).
5. Sistema executa `INSERT INTO aeroporto`.
6. Sistema exibe mensagem de sucesso.

**Regras de Negócio:**
- RN-08: Sigla do aeroporto deve conter exatamente 3 caracteres alfabéticos.
- RN-09: Sigla deve ser única no sistema.
- RN-10: Um aeroporto com voos vinculados não pode ser excluído fisicamente.

---

### UC-04 — Gerenciar Passageiros

**Ator:** Administrador / Atendente

**Descrição:** Permite cadastrar, consultar, atualizar e excluir passageiros.

**Fluxo Principal (Cadastro):**
1. Ator seleciona "Cadastrar Passageiro".
2. Sistema solicita: nome, e-mail, telefone, data de nascimento.
3. Ator informa os dados e confirma.
4. Sistema valida formato dos dados.
5. Sistema executa `INSERT INTO passageiro`.
6. Sistema exibe mensagem de sucesso.

**Regras de Negócio:**
- RN-11: Data de nascimento não pode ser futura.
- RN-12: E-mail deve ser único no sistema.
- RN-13: Telefone deve conter apenas dígitos com DDD (mínimo 10, máximo 11 caracteres).

---

### UC-05 — Gerenciar Voos

**Ator:** Administrador

**Descrição:** Permite cadastrar, consultar e cancelar voos. Cada voo é associado a um aeroporto de partida e possui cidade de origem e destino.

**Fluxo Principal (Cadastro):**
1. Ator seleciona "Cadastrar Voo".
2. Sistema solicita: código do voo, horário de partida (HHMM), horário de chegada (HHMM), aeroporto (código), cidade de origem, cidade de destino.
3. Ator informa os dados.
4. Sistema valida:
   - Aeroporto informado existe na tabela `aeroporto`.
   - Horário de chegada é posterior ao horário de partida.
   - Código do voo não duplicado.
5. Sistema executa `INSERT INTO voo`.
6. Sistema exibe mensagem de sucesso.

**Fluxo Principal (Consulta):**
1. Ator seleciona "Consultar Voos".
2. Sistema exibe listagem de todos os voos com dados completos (código, horários, origem, destino).

**Fluxo Alternativo (Consulta por Origem/Destino):**
1. Ator informa cidade de origem ou destino.
2. Sistema filtra e exibe apenas os voos correspondentes.

**Regras de Negócio:**
- RN-14: Horário de chegada deve ser diferente do horário de partida.
- RN-15: Código do voo deve seguir o padrão: 2 letras (sigla da CIA) + 4 dígitos numéricos (ex: G31001).
- RN-16: Cidade de origem e destino não podem ser iguais.

---

### UC-06 — Vincular Aeronave a Companhia

**Ator:** Administrador

**Descrição:** Permite associar uma aeronave a uma companhia aérea com data de aquisição. Uma aeronave pode pertencer a apenas uma companhia por vez (chave composta em `aeronave_cia`).

**Fluxo Principal:**
1. Ator seleciona "Vincular Aeronave a Companhia".
2. Sistema exibe listagem de aeronaves e companhias disponíveis.
3. Ator informa ID da aeronave, ID da companhia e data de aquisição.
4. Sistema valida:
   - Aeronave e companhia existem.
   - Aeronave não possui vínculo ativo com outra companhia.
5. Sistema executa `INSERT INTO aeronave_cia`.
6. Sistema exibe mensagem de sucesso.

**Regras de Negócio:**
- RN-17: Uma aeronave pode ter apenas um vínculo ativo por vez com uma companhia.
- RN-18: Data de aquisição não pode ser futura.

---

### UC-07 — Efetuar Reserva

**Ator:** Atendente

**Descrição:** Permite reservar um assento em um voo para um passageiro.

**Fluxo Principal:**
1. Ator seleciona "Efetuar Reserva".
2. Sistema solicita: código da reserva, ID do passageiro, código do voo, assento, data da reserva.
3. Ator informa os dados.
4. Sistema valida:
   - Passageiro existe.
   - Voo existe.
   - Assento não está ocupado no mesmo voo.
   - Data da reserva é válida.
5. Sistema executa `INSERT INTO reserva`.
6. Sistema exibe mensagem de sucesso.

**Fluxo Alternativo (Assento Ocupado):**
1. Sistema detecta que o assento já está reservado.
2. Sistema exibe os assentos disponíveis no voo.
3. Ator escolhe um novo assento.
4. Retorna ao passo 5 do fluxo principal.

**Regras de Negócio:**
- RN-19: Um assento em um voo só pode ter uma reserva ativa.
- RN-20: Código da reserva deve ser único.
- RN-21: A data da reserva não pode ser anterior à data atual.

---

### UC-08 — Cancelar Reserva

**Ator:** Atendente

**Descrição:** Permite cancelar uma reserva existente.

**Fluxo Principal:**
1. Ator seleciona "Cancelar Reserva".
2. Sistema solicita o código da reserva.
3. Ator informa o código.
4. Sistema localiza a reserva e exibe os dados para confirmação.
5. Ator confirma o cancelamento.
6. Sistema executa `DELETE FROM reserva WHERE cod_reserva = ?`.
7. Sistema exibe mensagem de sucesso.

**Fluxo Alternativo (Reserva não encontrada):**
1. Sistema não localiza o código informado.
2. Sistema exibe mensagem de erro e retorna ao menu.

---

## 5. User Stories (Visão PO)

### Épico: Gerenciamento de Entidades Base

| ID | Como um... | Eu quero... | Para que... | Prioridade |
| :--- | :--- | :--- | :--- | :--- |
| US-01 | Administrador | cadastrar aeronaves com modelo, capacidade e fabricante | o sistema tenha registro de todas as aeronaves disponíveis | Alta |
| US-02 | Administrador | consultar a frota de aeronaves cadastradas | eu possa visualizar rapidamente os dados de cada aeronave | Alta |
| US-03 | Administrador | atualizar dados de uma aeronave existente | corrigir informações desatualizadas | Média |
| US-04 | Administrador | desativar uma aeronave sem excluí-la | manter o histórico sem permitir novos uso | Média |
| US-05 | Administrador | cadastrar companhias aéreas | o sistema reconheça as empresas operantes | Alta |
| US-06 | Administrador | cadastrar aeroportos com nome, sigla e cidade | os voos tenham origens e destinos válidos | Alta |
| US-07 | Administrador | cadastrar passageiros | o sistema tenha a base de clientes | Alta |

### Épico: Operações de Voo e Reserva

| ID | Como um... | Eu quero... | Para que... | Prioridade |
| :--- | :--- | :--- | :--- | :--- |
| US-08 | Administrador | cadastrar voos com horários e aeroporto de partida | a grade de voos esteja disponível no sistema | Alta |
| US-09 | Administrador | consultar voos por origem ou destino | eu possa filtrar rotas rapidamente | Média |
| US-10 | Atendente | vincular uma aeronave a uma companhia | cada aeronave tenha uma operadora responsável | Alta |
| US-11 | Atendente | efetuar uma reserva para um passageiro em um voo | o passageiro tenha seu assento garantido | Alta |
| US-12 | Atendente | cancelar uma reserva | liberar o assento caso o passageiro desista | Média |
| US-13 | Atendente | visualizar os assentos disponíveis em um voo | oferecer opções ao passageiro durante a reserva | Baixa |

---

## 6. Roadmap de Sprints (Conceitual)

### Sprint A — Fundação e Conexão

**Objetivo:** Estabelecer a infraestrutura do projeto Java com Maven e a camada de conexão com o banco MariaDB.

**Funcionalidades:**
- Configuração do `pom.xml` com dependência `mysql-connector-j`
- Classe `ConnectionFactory` (JDBC) — Singleton para gerenciamento de conexões
- Arquivo de propriedades `db.properties` para credenciais externalizadas
- Teste de conectividade via console

**Entregáveis:** Projeto Maven compilável + conexão com o banco estabelecida.

---

### Sprint B — Modelos e DAOs das Entidades Independentes

**Objetivo:** Implementar a camada de modelo (POJOs) e acesso a dados (DAOs) das entidades que não dependem de chaves estrangeiras para existir.

**Entidades:** `Passageiro`, `Aeroporto`, `CiaAerea`

**Funcionalidades:**
- CRUD completo de Passageiro (UC-04)
- CRUD completo de Aeroporto (UC-03)
- CRUD completo de Companhia Aérea (UC-02)

**Casos de Uso Contemplados:** UC-02, UC-03, UC-04

---

### Sprint C — Modelos e DAOs das Entidades Dependentes

**Objetivo:** Implementar POJOs e DAOs das entidades com chaves estrangeiras e lógica de integridade referencial.

**Entidades:** `Aeronave`, `AeronaveCia`, `Voo`, `Reserva`

**Funcionalidades:**
- CRUD de Aeronave com validações (UC-01)
- Vínculo Aeronave-Companhia com verificação de existência (UC-06)
- CRUD de Voo com validação de aeroporto existente (UC-05)
- Reserva com verificação de disponibilidade de assento (UC-07)
- Cancelamento de reserva (UC-08)

**Casos de Uso Contemplados:** UC-01, UC-05, UC-06, UC-07, UC-08

---

### Sprint D — Interface do Usuário e Integração

**Objetivo:** Construir a interface (console ou Swing) que integra todos os DAOs em um menu navegável.

**Funcionalidades:**
- Menu principal com todas as opções do sistema
- Integração de cada opção do menu com o DAO correspondente
- Tratamento de exceções com mensagens amigáveis
- Validação de entrada do usuário
- Teste completo de ponta a ponta

**Casos de Uso Contemplados:** Todos (UC-01 a UC-08)

---

### Sprint E — Polimento e Portabilidade (Opcional / Pós-MVP)

**Objetivo:** Garantir que o sistema esteja pronto para execução no ambiente Windows da faculdade.

**Funcionalidades:**
- Verificação de compatibilidade com Eclipse Windows
- Empacotamento do projeto para importação
- Documentação de instalação (README)
- Testes em ambiente Windows (faculdade)

---

## 7. Dicionário de Dados (Referência Rápida)

| Tabela | Descrição | Chave Primária | FK |
| :--- | :--- | :--- | :--- |
| `aeronave` | Aeronaves cadastradas | `id_aeronave` | — |
| `cia_aerea` | Companhias aéreas | `id_cia` | — |
| `aeronave_cia` | Vínculo aeronave ↔ companhia | `id_aeronave` + `id_cia` | `id_aeronave`, `id_cia` |
| `aeroporto` | Aeroportos | `cod_aeroporto` | — |
| `passageiro` | Passageiros | `id_passageiro` | — |
| `voo` | Voos | `cod_voo` | `cod_aeroporto` |
| `reserva` | Reservas | `cod_reserva` | `id_passageiro`, `cod_voo` |

---

## 8. Glossário

| Termo | Definição |
| :--- | :--- |
| **POJO** | Plain Old Java Object — classe simples com atributos, getters e setters |
| **DAO** | Data Access Object — padrão que encapsula operações SQL |
| **JDBC** | Java Database Connectivity — API para conexão com bancos relacionais |
| **CRUD** | Create, Read, Update, Delete — operações básicas de persistência |
| **Integridade Referencial** | Garantia de que chaves estrangeiras apontam para registros existentes |
| **Personal Scrum** | Framework ágil adaptado para equipe de um único desenvolvedor |
| **MVP** | Minimum Viable Product — versão mínima funcional para validação |
