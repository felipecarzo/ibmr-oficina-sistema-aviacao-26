# ROADMAP — Decupador AI para DaVinci Resolve

Última atualização: 2026-05-06 — Beat-Sync implementado

---

## Sprint Concluída — Pipeline Base AI

- [x] Processamento Base (OpenCV LK Optical Flow para estabilidade)
- [x] Smart Thresholds dinâmicos (lente/situação)
- [x] XML Metadata Extractor (câmeras Sony)
- [x] EDL CMX3600 multifiles (DaVinci Resolve)
- [x] CLIP — Inteligência Visual Semântica
- [x] YOLOv8 + ByteTrack — Focus Subject Tracker
- [x] EasyOCR — B-Rolls e textos decorativos
- [x] MediaPipe — Facial Emotion / Sorrisos
- [x] Whisper — Transcrição + Imunidade de Direção
- [x] Librosa — Party Mode Acústico
- [x] Batch Processing — lote completo via CLI

---

## Sprint Concluída — Beat-Sync Mode (2026-05-06)

- [x] `src/beat_sync.py` — módulo independente de beat detection
- [x] `librosa.beat.beat_track` + `onset_detect` para timestamps
- [x] Export CSV de markers compatível com DaVinci Resolve
- [x] CLI: `--music`, `--takes`, `--fps`, `--onsets`
- [ ] **PENDENTE: Testar beat-sync com arquivo de música real no PowerShell**

---

## Sprint Final — IQA: Inteligência Fotográfica Sênior

> Bloqueador: falsos positivos em Bokeh Cinemático (f/1.4, f/2.8)

- [ ] BRISQUE / Laplacian para Image Quality Assessment
- [ ] Lógica: foco central alto + borda borrada = "Ouro Cinemático"
- [ ] Integrar score IQA no `video_processor.py`
- [ ] Ajustar threshold de aceitação baseado em IQA

---

## Backlog Futuro

- [ ] UI visual de timeline (substituir CLI puro)
- [ ] Integração nativa DaVinci via API Lua
- [ ] Speed ramps automáticos nos beats (Pulse Edit Pro feature)
