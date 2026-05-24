# Technical Design — Sistema de Aviação Comercial

| Documento | Technical Design |
| :--- | :--- |
| **Versão** | 1.0 |
| **Padrão Arquitetural** | DAO / Service / Console |

---

## 1. Arquitetura em Camadas

```
 ┌─────────────────────────────────────┐
 │         Interface do Usuário        │  ← Console (Menu textual)
 ├─────────────────────────────────────┤
 │           Camada de Serviço         │  ← Validador.java (regras de negócio)
 ├─────────────────────────────────────┤
 │     Camada DAO (Data Access Object)  │  ← SQL + JDBC + ConnectionFactory
 ├─────────────────────────────────────┤
 │         Banco de Dados MariaDB      │  ← sistema_aviacao
 └─────────────────────────────────────┘
```

**Fluxo de chamada:**

```
[Menu] → [Service/Validador] → [DAO] → [ConnectionFactory] → [MariaDB]
```

---

## 2. Estrutura de Diretórios

```
sistema-aviacao/
├── pom.xml
├── db.properties
└── src/main/java/
    └── com/aviacao/
        ├── main/
        │   └── SistemaAviação.java
        ├── model/
        │   ├── Aeronave.java
        │   ├── CiaAerea.java
        │   ├── AeronaveCia.java
        │   ├── Aeroporto.java
        │   ├── Passageiro.java
        │   ├── Voo.java
        │   └── Reserva.java
        ├── dao/
        │   ├── ConnectionFactory.java
        │   ├── AeronaveDAO.java
        │   ├── CiaAereaDAO.java
        │   ├── AeronaveCiaDAO.java
        │   ├── AeroportoDAO.java
        │   ├── PassageiroDAO.java
        │   ├── VooDAO.java
        │   └── ReservaDAO.java
        └── service/
            └── Validador.java
```

---

## 3. Modelo Relacional

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
 └──────────────┘     └──────────────┘
```

### Dicionário de Dados

| Tabela | Descrição | PK | FK |
| :--- | :--- | :--- | :--- |
| `aeronave` | Aeronaves cadastradas | `id_aeronave` | — |
| `cia_aerea` | Companhias aéreas | `id_cia` | — |
| `aeronave_cia` | Vínculo aeronave ↔ companhia | `id_aeronave` + `id_cia` | `id_aeronave`, `id_cia` |
| `aeroporto` | Aeroportos | `cod_aeroporto` | — |
| `passageiro` | Passageiros | `id_passageiro` | — |
| `voo` | Voos | `cod_voo` | `cod_aeroporto` |
| `reserva` | Reservas | `cod_reserva` | `id_passageiro`, `cod_voo` |

---

## 4. Diagrama de Casos de Uso

![Diagrama de Casos de Uso](images/diagrama-casos-de-uso.png)

---

## 5. Diagrama de Classes UML

![Diagrama de Classes UML](images/diagrama-classes-uml.png)

---

## 6. Mapa de Armadilhas Comuns

| # | Problema | Sintoma | Solução |
| :--- | :--- | :--- | :--- |
| 1 | ConnectionFactory não acha `db.properties` | NullPointerException | Carregar com `getClass().getClassLoader().getResourceAsStream()` |
| 2 | FK violada em AeronaveCia | SQLException | Verificar existência antes de inserir |
| 3 | Auto-increment não retorna ID | id = 0 após insert | Usar `PreparedStatement.RETURN_GENERATED_KEYS` + `getGeneratedKeys()` |
| 4 | LocalDate vs java.sql.Date | ClassCastException | Usar `Date.valueOf(localDate)` e `resultSet.getDate().toLocalDate()` |
| 5 | Acentos no Windows | caracteres `???` | `-Dfile.encoding=UTF-8` no Eclipse |
| 6 | Driver MariaDB vs MySQL | ClassNotFoundException | Usar `org.mariadb.jdbc.Driver` |
| 7 | Scanner nextInt + nextLine | pula linha | Usar `nextLine()` sempre e converter com `Integer.parseInt()` |

---

## 7. Glossário Técnico

| Termo | Definição |
| :--- | :--- |
| **POJO** | Plain Old Java Object — classe com atributos, getters e setters |
| **DAO** | Data Access Object — encapsula operações SQL |
| **JDBC** | Java Database Connectivity — API de conexão com bancos |
| **CRUD** | Create, Read, Update, Delete |
| **Integridade Referencial** | FK sempre aponta para registro existente |
| **Singleton** | Padrão de projeto com instância única (ConnectionFactory) |
| **Try-with-resources** | Bloco Java que fecha recursos automaticamente |
