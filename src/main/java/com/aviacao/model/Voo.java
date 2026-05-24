package com.aviacao.model;

public class Voo {
    private String codVoo;
    private int horaPartida;
    private int horaChegada;
    private int codAeroporto;
    private String cidadeOrigem;
    private String cidadeDestino;

    public Voo() {}

    public Voo(String codVoo, int horaPartida, int horaChegada, int codAeroporto,
               String cidadeOrigem, String cidadeDestino) {
        this.codVoo = codVoo;
        this.horaPartida = horaPartida;
        this.horaChegada = horaChegada;
        this.codAeroporto = codAeroporto;
        this.cidadeOrigem = cidadeOrigem;
        this.cidadeDestino = cidadeDestino;
    }

    public String getCodVoo() { return codVoo; }
    public void setCodVoo(String codVoo) { this.codVoo = codVoo; }

    public int getHoraPartida() { return horaPartida; }
    public void setHoraPartida(int horaPartida) { this.horaPartida = horaPartida; }

    public int getHoraChegada() { return horaChegada; }
    public void setHoraChegada(int horaChegada) { this.horaChegada = horaChegada; }

    public int getCodAeroporto() { return codAeroporto; }
    public void setCodAeroporto(int codAeroporto) { this.codAeroporto = codAeroporto; }

    public String getCidadeOrigem() { return cidadeOrigem; }
    public void setCidadeOrigem(String cidadeOrigem) { this.cidadeOrigem = cidadeOrigem; }

    public String getCidadeDestino() { return cidadeDestino; }
    public void setCidadeDestino(String cidadeDestino) { this.cidadeDestino = cidadeDestino; }

    @Override
    public String toString() {
        return "Voo{cod='" + codVoo + '\'' + ", " + cidadeOrigem + "->" + cidadeDestino
                + ", partida=" + horaPartida + ", chegada=" + horaChegada + '}';
    }
}
