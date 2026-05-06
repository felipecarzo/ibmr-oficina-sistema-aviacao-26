# HANDOFF — Decupador AI

## Meta
- Data: 2026-05-06
- Branch: master
- Último commit: 91dcd7e (docs: adicione comentário no início do index.html)
- Máquina: Windows + WSL2 (venv é Windows — rodar do PowerShell)

---

## O que foi feito nesta sessão

### Beat-Sync Mode implementado
Novo modo independente do pipeline AI que:
1. Recebe arquivo de música (`--music`)
2. Detecta beats/onsets com librosa
3. Gera `data/output/beat_markers.csv` compatível com DaVinci Resolve
4. Usuário importa CSV no DaVinci: **Arquivo → Importar → Marcadores de Timeline**

**Arquivos criados/modificados:**
- `src/beat_sync.py` — módulo novo (não commitado)
- `main.py` — adicionado modo beat-sync (não commitado)

---

## Task em andamento: TESTAR BEAT-SYNC

**O código está pronto mas não foi testado.** Precisa rodar do PowerShell (venv Windows).

### Próximo passo exato

```powershell
cd D:\Documentos\Ti\projetos\app_videoeditor
.\venv\Scripts\activate

# Teste mínimo (usa vídeo existente como música)
python main.py --music "data\input\b0197.MP4" --fps 60

# Teste completo (com música real)
python main.py --music "CAMINHO\trilha.mp3" --takes "PASTA_TAKES_DAVINCI" --fps 60
```

**Output esperado:** `data/output/beat_markers.csv` com linhas tipo:
```
1,Red,Beat,00:00:00:15,00:00:00:01,Beat 1 — 120.0 BPM
```

**Se der erro de import:** verificar se `imageio_ffmpeg` está no venv:
```powershell
pip install imageio-ffmpeg
```

---

## Pendências de commit

Arquivos não commitados:
- `src/beat_sync.py` (novo)
- `main.py` (modificado)
- `docs/ROADMAP.md` (novo)
- `docs/HANDOFF.md` (novo)
- `docs/daily/2026-05-06.md` (novo)

**Commitar após teste bem-sucedido:**
```powershell
git add src/beat_sync.py main.py docs/
git commit -m "feat: adiciona modo beat-sync com export de markers para DaVinci"
```

---

## Arquivos a ler para contexto

- `src/beat_sync.py` — implementação completa do beat-sync
- `main.py` — ver como os modos se integram (linha 38-48)
- `docs/ROADMAP.md` — próxima sprint é IQA
- `src/audio_analyzer.py` — já usa librosa (ver como beat_sync reutiliza padrão)

---

## Estado do ROADMAP

| Sprint | Status |
|---|---|
| Pipeline Base AI | Completo |
| Beat-Sync Mode | Implementado, aguardando teste |
| IQA Fotográfica | Próxima |
| UI + DaVinci Lua | Backlog |
