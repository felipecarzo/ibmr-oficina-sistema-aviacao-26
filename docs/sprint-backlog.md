# Sprint Backlog — Sprint B

## Objetivo
Implementar POJOs e DAOs das entidades independentes (Passageiro, Aeroporto, CiaAerea).

## Status: ✅ Concluída

| Item | Entregue |
| :--- | :---: |
| POJO Passageiro | `model/Passageiro.java` |
| PassageiroDAO (CRUD) | `dao/PassageiroDAO.java` |
| POJO Aeroporto | `model/Aeroporto.java` |
| AeroportoDAO (CRUD) | `dao/AeroportoDAO.java` |
| POJO CiaAerea | `model/CiaAerea.java` |
| CiaAereaDAO (CRUD) | `dao/CiaAereaDAO.java` |
| Teste via console | OK |
| `mvn clean compile` | BUILD SUCCESS |

## Impedimentos
- Banco sem `AUTO_INCREMENT` nas PKs de passageiro, aeroporto, cia_aerea → corrigido via ALTER TABLE.
- Passageiro com registro `id=0` impedia ALTER → migrado para `id=100`.
