# Sprint Backlog

## Sprint F — Interface Gráfica (Swing) ✅

**Status:** ✅ Concluída

**Arquivos:** `src/main/java/com/aviacao/gui/`

| Componente | Arquivo | Função |
| :--- | :--- | :--- |
| CampoFormulario | `gui/CampoFormulario.java` | Descritor de campo (rótulo, tipo, validador) |
| FormDialog | `gui/FormDialog.java` | Formulário modal dinâmico |
| CrudPanel | `gui/CrudPanel.java` | JPanel reutilizável com JTable + CRUD |
| MainFrame | `gui/MainFrame.java` | JFrame principal com 7 abas |

**Abas implementadas:**

| Aba | Operações |
| :--- | :--- |
| Aeronaves | Cadastrar, Editar, Excluir, Buscar ID, Listar |
| Companhias | Cadastrar (c/ validação CNPJ/email), Editar, Excluir |
| Aeroportos | Cadastrar (c/ validação sigla), Editar, Excluir |
| Passageiros | Cadastrar (c/ validação email/tel/data), Editar, Excluir |
| Voos | Cadastrar (c/ validação horário), Editar, Excluir, Buscar rota |
| Vínculo Aero-Cia | Vincular/Desvincular aeronave a companhia |
| Reservas | Efetuar (c/ verificação assento), Editar assento, Cancelar |

**DoD:** ✅ `mvn clean compile` — BUILD SUCCESS

---

## Histórico de Sprints

| Sprint | Foco | Status |
| :--- | :--- | :---: |
| Sprint 0 | Inception (docs + diagramas) | ✅ |
| Sprint A | Infraestrutura (Maven + ConnectionFactory) | ✅ |
| Sprint B | POJOs + DAOs (Passageiro, Aeroporto, CiaAerea) | ✅ |
| Sprint C | POJOs + DAOs (Aeronave, AeronaveCia, Voo, Reserva) | ✅ |
| Fase 5.4 | Service (Validador) | ✅ |
| Sprint D | Interface (Menu console) | ✅ |
| Sprint E | Portabilidade (Windows/Eclipse) | ✅ |
| **Sprint F** | **Interface Gráfica (Swing)** | **✅** |
