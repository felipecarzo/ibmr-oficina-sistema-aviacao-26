# Sprint Backlog

## Sprint C — Entidades Dependentes

**Objetivo:** Implementar POJOs e DAOs de Aeronave, AeronaveCia, Voo e Reserva.

**Status:** ✅ Concluída

| Item | Entregue |
| :--- | :---: |
| POJO Aeronave | `model/Aeronave.java` |
| AeronaveDAO | `dao/AeronaveDAO.java` |
| POJO AeronaveCia | `model/AeronaveCia.java` |
| AeronaveCiaDAO | `dao/AeronaveCiaDAO.java` |
| POJO Voo | `model/Voo.java` |
| VooDAO | `dao/VooDAO.java` |
| POJO Reserva | `model/Reserva.java` |
| ReservaDAO | `dao/ReservaDAO.java` |
| Teste integrado | OK (insert/busca/delete em todos) |
| `mvn clean compile` | BUILD SUCCESS |

**Impedimentos:**
- `aeronave` sem `AUTO_INCREMENT` e com `id=0` → migrado para `id=100`, PK alterada.
