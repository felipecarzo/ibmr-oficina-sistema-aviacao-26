import os
import glob
import numpy as np
import librosa
import subprocess
import imageio_ffmpeg


def _load_audio(file_path: str, sr: int = 22050) -> np.ndarray:
    ffmpeg_exe = imageio_ffmpeg.get_ffmpeg_exe()
    cmd = [
        ffmpeg_exe, "-nostdin", "-threads", "0",
        "-i", file_path,
        "-f", "s16le", "-ac", "1",
        "-acodec", "pcm_s16le",
        "-ar", str(sr), "-"
    ]
    try:
        out = subprocess.run(cmd, capture_output=True, check=True).stdout
    except subprocess.CalledProcessError as e:
        raise RuntimeError(f"Falha ao extrair áudio: {e.stderr.decode()}") from e
    return np.frombuffer(out, np.int16).flatten().astype(np.float32) / 32768.0


def _sec_to_timecode(seconds: float, fps: float) -> str:
    total_frames = int(round(seconds * fps))
    h = total_frames // (int(fps) * 3600)
    total_frames %= int(fps) * 3600
    m = total_frames // (int(fps) * 60)
    total_frames %= int(fps) * 60
    s = total_frames // int(fps)
    f = total_frames % int(fps)
    return f"{h:02d}:{m:02d}:{s:02d}:{f:02d}"


def detect_beats(music_file: str, sr: int = 22050) -> dict:
    print(f"[BeatSync] Carregando: {music_file}")
    audio = _load_audio(music_file, sr=sr)

    print("[BeatSync] Analisando beats e onsets...")
    tempo, beat_frames = librosa.beat.beat_track(y=audio, sr=sr, units="frames")
    beat_times = librosa.frames_to_time(beat_frames, sr=sr).tolist()

    onset_frames = librosa.onset.onset_detect(
        y=audio, sr=sr,
        hop_length=512,
        backtrack=True
    )
    onset_times = librosa.frames_to_time(onset_frames, sr=sr).tolist()

    tempo_val = float(tempo[0]) if isinstance(tempo, np.ndarray) else float(tempo)
    print(f"[BeatSync] {tempo_val:.1f} BPM | {len(beat_times)} beats | {len(onset_times)} onsets")

    return {
        "tempo": tempo_val,
        "beat_times": beat_times,
        "onset_times": onset_times,
    }


def scan_takes(takes_folder: str) -> list[str]:
    takes = []
    for ext in ("*.mp4", "*.mov", "*.mxf", "*.MP4", "*.MOV", "*.MXF"):
        takes.extend(glob.glob(os.path.join(takes_folder, ext)))
    takes.sort()
    return takes


def export_beat_markers(
    beat_data: dict,
    output_path: str,
    fps: float = 60.0,
    use_onsets: bool = False,
) -> str:
    times = beat_data["onset_times"] if use_onsets else beat_data["beat_times"]
    tempo = beat_data["tempo"]
    label = "Onset" if use_onsets else "Beat"
    one_frame = _sec_to_timecode(1.0 / fps, fps)

    lines = ["#,Color,Name,Start,Duration,Notes"]
    for i, t in enumerate(times, 1):
        tc = _sec_to_timecode(t, fps)
        lines.append(f"{i},Red,{label},{tc},{one_frame},{label} {i} — {tempo:.1f} BPM")

    os.makedirs(os.path.dirname(output_path) or ".", exist_ok=True)
    with open(output_path, "w", encoding="utf-8") as f:
        f.write("\n".join(lines))

    print(f"[BeatSync] {len(times)} markers → {output_path}")
    return output_path


def run_beat_sync(
    music_file: str,
    takes_folder: str | None,
    output_path: str,
    fps: float = 60.0,
    use_onsets: bool = False,
) -> None:
    if not os.path.isfile(music_file):
        print(f"[BeatSync] Arquivo de música não encontrado: {music_file}")
        return

    beat_data = detect_beats(music_file)

    if takes_folder:
        takes = scan_takes(takes_folder)
        beats = beat_data["beat_times"]
        print(f"[BeatSync] {len(takes)} take(s) na pasta | {len(beats)} beats na música")
        if takes:
            print(f"[BeatSync] Primeiro take: {os.path.basename(takes[0])}")
            print(f"[BeatSync] Último take:   {os.path.basename(takes[-1])}")

    export_beat_markers(beat_data, output_path, fps=fps, use_onsets=use_onsets)
    print(f"\n[BeatSync] Importe '{output_path}' no DaVinci: Arquivo → Importar → Marcadores de Timeline")
