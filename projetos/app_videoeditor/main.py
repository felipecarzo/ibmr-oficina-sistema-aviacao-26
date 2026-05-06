import argparse
import os
import cv2
import glob
from src.video_processor import VideoProcessor
from src.timeline_logic import build_usable_blocks
from src.export_edl import export_edl
from src.xml_parser import XMLParser
from src.audio_analyzer import AudioAnalyzer
from src.beat_sync import run_beat_sync

def get_xml_path(video_path):
    # Regra Sony: C001.MP4 -> C001M01.XML
    base_name, ext = os.path.splitext(video_path)
    xml_path = base_name + "M01.XML"
    if os.path.exists(xml_path):
        return xml_path
    
    # Alternativa genérica
    xml_path = base_name + ".XML"
    if os.path.exists(xml_path):
        return xml_path
    return None

def main():
    parser = argparse.ArgumentParser(description="Automatiza decupagem de vídeos usando IA.")
    parser.add_argument("--input", "-i", type=str, help="Caminho para o vídeo bruto ou pasta")
    parser.add_argument("--output", "-o", type=str, help="Caminho para salvar o EDL", default="decupagem_master.edl")

    # Modo Beat-Sync (independente do pipeline de qualidade)
    parser.add_argument("--music", "-m", type=str, help="Arquivo de música para beat-sync (mp3/wav/aac)")
    parser.add_argument("--takes", "-t", type=str, help="Pasta com takes do DaVinci para contagem")
    parser.add_argument("--fps", type=float, default=60.0, help="FPS da timeline do DaVinci (padrão: 60)")
    parser.add_argument("--onsets", action="store_true", help="Usar onsets (mais cortes) ao invés de beats")

    args = parser.parse_args()

    # --- MODO BEAT-SYNC ---
    if args.music:
        output_csv = os.path.join("data", "output", "beat_markers.csv")
        run_beat_sync(
            music_file=args.music,
            takes_folder=args.takes,
            output_path=output_csv,
            fps=args.fps,
            use_onsets=args.onsets,
        )
        return
    
    # --- MODO DECUPAGEM AI ---
    if not args.input:
        print("Informe --input para decupagem ou --music para beat-sync.")
        return

    # 1. Determina a lista de arquivos de vídeo
    files_to_process = []
    if os.path.isfile(args.input):
        files_to_process.append(args.input)
    elif os.path.isdir(args.input):
        for ext in ("*.mp4", "*.mov", "*.mxf", "*.MP4", "*.MOV"):
            files_to_process.extend(glob.glob(os.path.join(args.input, ext)))
        files_to_process.sort() # Ordenar alfabeticamente ajuda na linha do tempo contínua
    else:
        print("Por favor, forneça um caminho válido (arquivo ou pasta).")
        return
        
    if not files_to_process:
        print(f"Nenhum arquivo de vídeo encontrado em: {args.input}")
        return

    print(f"\n[Batch Processor] Iniciando pipeline de decupagem para {len(files_to_process)} vídeo(s)...")

    # 2. Inicializa os Modelos Pesados de IA apenas UMA VEZ
    print("\n----- ALOCANDO MODELOS NA MEMÓRIA -----")
    audio_analyzer = AudioAnalyzer(model_size="tiny")
    processor = VideoProcessor(step_ms=500)
    
    global_usable_blocks = []

    # 3. Loop em Lote (Batch)
    for video_file in files_to_process:
        print(f"\n=============================================")
        print(f"🎬 PROCESSANDO: {os.path.basename(video_file)}")
        print(f"=============================================")
        
        # Obter FPS
        cap = cv2.VideoCapture(video_file)
        fps = cap.get(cv2.CAP_PROP_FPS) or 60.0
        cap.release()
        
        # Extrator de Metadados XML (Lente e Modelos)
        xml_path = get_xml_path(video_file)
        metadata = None
        if xml_path:
            parser_xml = XMLParser()
            metadata = parser_xml.parse_metadata(xml_path)
            print(f"[XML Metadados] Câmera: {metadata.get('camera_model')} | Lente: {metadata.get('lens_model')}")
            
        # Analisador Acústico e Semântico (Whisper e Librosa)
        audio_data = audio_analyzer.analyze_audio(video_file)
        directed_moments = audio_data.get("directed_moments", [])
        party_moments = audio_data.get("party_moments", [])
        
        if directed_moments:
            print(f"[Áudio] Palavras de direção encontradas nas marcações de {len(directed_moments)} falas.")
        if party_moments:
            print(f"[Áudio] Encontrado Som Estourado/Festa/Música em {len(party_moments)} fragmentos (Modo Festa Ativado!).")
        
        # Visão Computacional Principal (Processador Global)
        timeline_raw = processor.process_video(
             video_file, 
             metadata=metadata, 
             directed_moments=directed_moments, 
             party_moments=party_moments
        )
        
        # Agrupar a linha temporal desse arquivo específico em Múltiplos Fragmentos Úteis (Takes 1.1, 1.2, 1.3)
        if timeline_raw:
             blocks = build_usable_blocks(timeline_raw, min_duration_sec=1.5, source_file=video_file, fps=fps)
             global_usable_blocks.extend(blocks)
             print(f"✅ {len(blocks)} fragmentos úteis gerados para {os.path.basename(video_file)}.")
        else:
             print("❌ O arquivo não teve frames úteis lidos ou possuía erros.")
             
    # 4. Geração do EDL Mestre Compilado
    print(f"\n⏳ Processamento da Pasta Completo. Total de {len(global_usable_blocks)} takes úteis agrupados.")
    if global_usable_blocks:
         export_path = os.path.join("data", "output", args.output)
         os.makedirs(os.path.dirname(export_path), exist_ok=True)
         export_edl(global_usable_blocks, output_path=export_path)
         print(f"🔥 SUCESSO! Importe o arquivo {export_path} no seu DaVinci Resolve!")
    else:
         print("Nenhum take útil encontrado no lote.")
         
    # Fechar recursos
    processor.close()

if __name__ == "__main__":
    main()
