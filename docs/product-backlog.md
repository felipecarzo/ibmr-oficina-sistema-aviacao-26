# Product Backlog — Sistema de Gerenciamento de Aviação Comercial

| Documento | Product Backlog |
| :--- | :--- |
| **Versão** | 1.0 |
| **Priorização** | MoSCoW (Must / Should / Could / Won't) |

---

## 1. Épicos

### Épico 1 — Gerenciamento de Entidades Base
Cadastro e manutenção das entidades fundamentais do sistema: aeronaves, companhias aéreas, aeroportos e passageiros.

### Épico 2 — Operações de Voo e Reserva
Gestão de voos, vínculo aeronave-companhia e reserva de assentos.

---

## 2. Casos de Uso (Visão Geral)

| ID | Nome | Ator | Épico |
| :--- | :--- | :--- | :--- |
| UC-01 | Gerenciar Aeronaves | Administrador | 1 |
| UC-02 | Gerenciar Companhias Aéreas | Administrador | 1 |
| UC-03 | Gerenciar Aeroportos | Administrador | 1 |
| UC-04 | Gerenciar Passageiros | Administrador / Atendente | 1 |
| UC-05 | Gerenciar Voos | Administrador | 2 |
| UC-06 | Vincular Aeronave a Companhia | Administrador | 2 |
| UC-07 | Efetuar Reserva | Atendente | 2 |
| UC-08 | Cancelar Reserva | Atendente | 2 |

---

## 3. User Stories

### Épico 1 — Gerenciamento de Entidades Base

| ID | Como um... | Eu quero... | Para que... | Prioridade | Est. |
| :--- | :--- | :--- | :--- | :--- | :--- |
| US-01 | Administrador | usar uma interface gráfica para gerenciar o sistema | a operação seja intuitiva e visual | Must | M |
| US-02 | Administrador | cadastrar aeronaves com modelo, capacidade e fabricante | o sistema tenha registro de todas as aeronaves disponíveis | Must | M |
| US-03 | Administrador | consultar a frota de aeronaves cadastradas | eu possa visualizar rapidamente os dados de cada aeronave | Must | P |
| US-04 | Administrador | atualizar dados de uma aeronave existente | corrigir informações desatualizadas | Should | P |
| US-05 | Administrador | desativar uma aeronave sem excluí-la | manter o histórico sem permitir novos uso | Should | P |
| US-06 | Administrador | cadastrar companhias aéreas | o sistema reconheça as empresas operantes | Must | M |
| US-07 | Administrador | cadastrar aeroportos com nome, sigla e cidade | os voos tenham origens e destinos válidos | Must | M |
| US-08 | Administrador | cadastrar passageiros | o sistema tenha a base de clientes | Must | M |

### Épico 2 — Operações de Voo e Reserva

| ID | Como um... | Eu quero... | Para que... | Prioridade | Est. |
| :--- | :--- | :--- | :--- | :--- | :--- |
| US-09 | Administrador | cadastrar voos com horários e aeroporto de partida | a grade de voos esteja disponível no sistema | Must | G |
| US-10 | Administrador | consultar voos por origem ou destino | eu possa filtrar rotas rapidamente | Should | M |
| US-11 | Atendente | vincular uma aeronave a uma companhia | cada aeronave tenha uma operadora responsável | Must | M |
| US-12 | Atendente | efetuar uma reserva para um passageiro em um voo | o passageiro tenha seu assento garantido | Must | G |
| US-13 | Atendente | cancelar uma reserva | liberar o assento caso o passageiro desista | Should | M |
| US-14 | Atendente | visualizar assentos disponíveis em um voo | oferecer opções ao passageiro durante a reserva | Could | P |

**Estimativa:** P = Pequena (≤2h), M = Média (2-4h), G = Grande (4-8h)

---

## 4. Mapeamento UC × US × Classes × DAO

| Caso de Uso | User Stories | Modelo | DAO |
| :--- | :--- | :--- | :--- |
| UC-01 — Gerenciar Aeronaves | US-01, US-02, US-03, US-04, US-05 | `Aeronave` | `AeronaveDAO` |
| UC-02 — Gerenciar Cias Aéreas | US-06 | `CiaAerea` | `CiaAereaDAO` |
| UC-03 — Gerenciar Aeroportos | US-07 | `Aeroporto` | `AeroportoDAO` |
| UC-04 — Gerenciar Passageiros | US-08 | `Passageiro` | `PassageiroDAO` |
| UC-05 — Gerenciar Voos | US-09, US-10 | `Voo` | `VooDAO` |
| UC-06 — Vincular Aeronave a Cia | US-11 | `AeronaveCia` | `AeronaveCiaDAO` |
| UC-07 — Efetuar Reserva | US-12, US-14 | `Reserva` | `ReservaDAO` |
| UC-08 — Cancelar Reserva | US-13 | `Reserva` | `ReservaDAO` |

---

## 5. Regras de Negócio (Consolidadas)

| RN | Descrição | UC |
| :--- | :--- | :--- |
| RN-01 | Capacidade da aeronave: inteiro positivo entre 1 e 999 | UC-01 |
| RN-02 | Envergadura da aeronave: inteiro positivo | UC-01 |
| RN-03 | Status ativo: 'S' ou 'N' | UC-01 |
| RN-04 | Modelo único por fabricante (não obrigatório no MVP) | UC-01 |
| RN-05 | CNPJ: exatamente 14 dígitos numéricos | UC-02 |
| RN-06 | E-mail: deve conter '@' e domínio válido | UC-02 |
| RN-07 | Cia com aeronaves vinculadas não pode ser excluída fisicamente | UC-02 |
| RN-08 | Sigla do aeroporto: exatamente 3 caracteres alfabéticos | UC-03 |
| RN-09 | Sigla do aeroporto: única no sistema | UC-03 |
| RN-10 | Aeroporto com voos vinculados não pode ser excluído | UC-03 |
| RN-11 | Data de nascimento não pode ser futura | UC-04 |
| RN-12 | E-mail do passageiro: único no sistema | UC-04 |
| RN-13 | Telefone: 10-11 dígitos com DDD | UC-04 |
| RN-14 | Horário de chegada deve ser diferente e posterior ao de partida | UC-05 |
| RN-15 | Código do voo: 2 letras + 4 dígitos (ex: G31001) | UC-05 |
| RN-16 | Origem e destino do voo não podem ser iguais | UC-05 |
| RN-17 | Aeronave: apenas 1 vínculo ativo por vez com companhia | UC-06 |
| RN-18 | Data de aquisição não pode ser futura | UC-06 |
| RN-19 | Assento: apenas 1 reserva ativa por voo | UC-07 |
| RN-20 | Código da reserva: único no sistema | UC-07 |
| RN-21 | Data da reserva não pode ser anterior à data atual | UC-07 |
