# Especificação de Casos de Uso

| Documento | Use Case Specification |
| :--- | :--- |
| **Versão** | 1.0 |
| **Ator unificado no MVP** | Operador (Administrador + Atendente) |

---

## Atores do Sistema

| Ator | Descrição |
| :--- | :--- |
| **Administrador** | Acesso total — gerencia aeronaves, companhias, aeroportos, passageiros, voos e reservas |
| **Atendente** | Operacional — consulta voos e efetua/cancela reservas |

> No MVP ambos os papéis são unificados sob o ator **Operador**.

---

## Diagrama de Casos de Uso

![Diagrama de Casos de Uso](images/diagrama-casos-de-uso.png)

---

## UC-01 — Gerenciar Aeronaves

**Ator Principal:** Operador
**Objetivo:** Permitir o cadastro, consulta, atualização e desativação lógica de aeronaves.
**Pré-condições:** Banco de dados MariaDB ativo.

**Fluxo Principal (Cadastro):**
1. Operador seleciona "Gerenciar Aeronaves" → "Cadastrar Aeronave"
2. Sistema solicita: modelo, capacidade, envergadura, fabricante, status ativo ('S'/'N')
3. Operador informa os dados e confirma
4. Sistema valida capacidade (1-999), envergadura positiva, status válido (RN-01 a RN-03)
5. `AeronaveDAO` executa `INSERT INTO aeronave`
6. Sistema exibe sucesso com o ID gerado

**Fluxo Principal (Consulta Geral):**
1. Operador escolhe "Consultar Frota"
2. `AeronaveDAO` executa `SELECT * FROM aeronave`
3. Sistema exibe listagem formatada com id, modelo, fabricante, capacidade, status

**Fluxo Alternativo (Atualização):**
1. Operador informa o ID da aeronave
2. Sistema exibe dados atuais e solicita novos valores
3. Operador altera os campos desejados
4. Sistema valida e executa `UPDATE aeronave SET ... WHERE id_aeronave = ?`

**Fluxo Alternativo (Exclusão / Desativação):**
1. Operador informa o ID da aeronave
2. Sistema verifica vínculos em `aeronave_cia`
3. Se houver vínculos, sugere desativação lógica (`status_ativo = 'N'`)
4. Sistema executa DELETE ou UPDATE conforme decisão

**Regras de Negócio:** RN-01 a RN-04

**Diagrama de Sequência — Consulta Geral de Aeronaves:**

![Seq - Consulta Geral de Aeronaves](images/seq-consulta-geral-aeronaves.png)

**Diagrama de Sequência — Cadastro de Aeronave:**

![Seq - Cadastro de Aeronave](images/seq-cadastro-aeronave.png)

---

## UC-02 — Gerenciar Companhias Aéreas

**Ator Principal:** Operador
**Objetivo:** Cadastrar, consultar, atualizar e desativar companhias aéreas.
**Pré-condições:** Banco de dados ativo.

**Fluxo Principal (Cadastro):**
1. Operador seleciona "Cadastrar Companhia Aérea"
2. Sistema solicita: nome, CNPJ, e-mail, status ativo
3. Operador informa os dados e confirma
4. Sistema valida CNPJ (14 dígitos — RN-05) e e-mail (RN-06)
5. `CiaAereaDAO` executa `INSERT INTO cia_aerea`
6. Sistema exibe confirmação com o ID gerado

**Fluxo Alternativo (Exclusão):**
1. Sistema verifica vínculos em `aeronave_cia`
2. Se houver, bloqueia exclusão física e sugere desativação lógica (RN-07)

**Regras de Negócio:** RN-05 a RN-07

**Diagrama de Sequência — Cadastro de Companhia:**

![Seq - Cadastro de Companhia](images/seq-cadastro-companhia.png)

---

## UC-03 — Gerenciar Aeroportos

**Ator Principal:** Operador
**Objetivo:** Cadastrar, consultar, atualizar e excluir aeroportos.
**Pré-condições:** Banco de dados ativo.

**Fluxo Principal (Cadastro):**
1. Operador escolhe "Cadastrar Aeroporto"
2. Sistema solicita: nome, sigla (3 letras), cidade
3. Operador informa e confirma
4. Sistema valida sigla (3 caracteres alfabéticos — RN-08) e unicidade (RN-09)
5. `AeroportoDAO` executa `INSERT INTO aeroporto`
6. Sistema exibe sucesso

**Regras de Negócio:** RN-08 a RN-10

**Diagrama de Sequência — Cadastro de Aeroporto:**

![Seq - Cadastro de Aeroporto](images/seq-cadastro-aeroporto.png)

---

## UC-04 — Gerenciar Passageiros

**Ator Principal:** Operador
**Objetivo:** Cadastrar, consultar, atualizar e excluir passageiros.
**Pré-condições:** Banco de dados ativo.

**Fluxo Principal (Cadastro):**
1. Operador seleciona "Cadastrar Passageiro"
2. Sistema solicita: nome, e-mail, telefone, data de nascimento
3. Operador informa e confirma
4. Sistema valida: data não futura (RN-11), e-mail único (RN-12), telefone com DDD (RN-13)
5. `PassageiroDAO` executa `INSERT INTO passageiro`
6. Sistema exibe confirmação com ID

**Regras de Negócio:** RN-11 a RN-13

**Diagrama de Sequência — Cadastro de Passageiro:**

![Seq - Cadastro de Passageiro](images/seq-cadastro-passageiro.png)

---

## UC-05 — Gerenciar Voos

**Ator Principal:** Operador
**Objetivo:** Registrar voos associados a um aeroporto de partida.
**Pré-condições:** Aeroporto de partida deve estar cadastrado.

**Fluxo Principal (Cadastro):**
1. Operador seleciona "Cadastrar Voo"
2. Sistema solicita: código do voo, origem, destino, horário de partida (HHMM), horário de chegada (HHMM), código do aeroporto
3. Operador informa os dados
4. Sistema valida: chegada > partida (RN-14), padrão de código (RN-15), origem ≠ destino (RN-16), aeroporto existe
5. `VooDAO` executa `INSERT INTO voo`
6. Sistema exibe sucesso

**Fluxo Alternativo (Consulta por Origem/Destino):**
1. Operador informa cidade de origem ou destino
2. Sistema filtra e exibe apenas os voos correspondentes

**Regras de Negócio:** RN-14 a RN-16

**Diagrama de Sequência — Cadastro de Voo:**

![Seq - Cadastro de Voo](images/seq-cadastro-voo.png)

---

## UC-06 — Vincular Aeronave a Companhia

**Ator Principal:** Operador
**Objetivo:** Associar uma aeronave a uma companhia aérea responsável.
**Pré-condições:** Aeronave e companhia existem. Aeronave sem vínculo ativo.

**Fluxo Principal:**
1. Operador seleciona "Vincular Aeronave a Companhia"
2. Sistema solicita: ID da aeronave, ID da companhia, data de aquisição
3. Sistema valida: existência da aeronave, existência da cia, vínculo único (RN-17), data válida (RN-18)
4. `AeronaveCiaDAO` executa `INSERT INTO aeronave_cia`
5. Sistema exibe confirmação

**Regras de Negócio:** RN-17 a RN-18

**Diagrama de Sequência — Vincular Aeronave a Companhia:**

![Seq - Vincular Aeronave a Companhia](images/seq-vincular-aeronave-cia.png)

---

## UC-07 — Efetuar Reserva

**Ator Principal:** Operador
**Objetivo:** Reservar um assento em um voo para um passageiro.
**Pré-condições:** Passageiro e voo devem estar cadastrados.

**Fluxo Principal:**
1. Operador escolhe "Efetuar Reserva"
2. Sistema solicita: código da reserva, ID do passageiro, código do voo, assento, data da reserva
3. Sistema valida: passageiro existe, voo existe, assento disponível (RN-19), data válida (RN-21)
4. `ReservaDAO` executa `INSERT INTO reserva`
5. Sistema exibe sucesso

**Fluxo Alternativo (Assento Ocupado):**
1. Sistema detecta assento ocupado
2. Exibe assentos disponíveis no voo
3. Operador escolhe novo assento
4. Retorna ao passo 4 do fluxo principal

**Regras de Negócio:** RN-19 a RN-21

**Diagrama de Sequência:**

![Seq - Efetuar Reserva](images/seq-efetuar-reserva.png)

---

## UC-08 — Cancelar Reserva

**Ator Principal:** Operador
**Objetivo:** Cancelar uma reserva, liberando o assento.
**Pré-condições:** A reserva informada deve existir.

**Fluxo Principal:**
1. Operador escolhe "Cancelar Reserva"
2. Sistema solicita o código da reserva
3. Sistema localiza a reserva e exibe os dados para confirmação
4. Operador confirma o cancelamento
5. `ReservaDAO` executa `DELETE FROM reserva WHERE cod_reserva = ?`
6. Sistema exibe sucesso

**Fluxo Alternativo (Reserva não encontrada):**
1. Sistema não localiza o código
2. Exibe mensagem de erro e retorna ao menu

**Diagrama de Sequência:**

![Seq - Cancelar Reserva](images/seq-cancelar-reserva.png)
