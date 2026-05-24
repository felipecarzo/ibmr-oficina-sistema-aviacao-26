package com.aviacao.model;

import java.time.LocalDate;

public class AeronaveCia {
    private int idAeronave;
    private int idCia;
    private LocalDate dataAquisicao;

    public AeronaveCia() {}

    public AeronaveCia(int idAeronave, int idCia, LocalDate dataAquisicao) {
        this.idAeronave = idAeronave;
        this.idCia = idCia;
        this.dataAquisicao = dataAquisicao;
    }

    public int getIdAeronave() { return idAeronave; }
    public void setIdAeronave(int idAeronave) { this.idAeronave = idAeronave; }

    public int getIdCia() { return idCia; }
    public void setIdCia(int idCia) { this.idCia = idCia; }

    public LocalDate getDataAquisicao() { return dataAquisicao; }
    public void setDataAquisicao(LocalDate dataAquisicao) { this.dataAquisicao = dataAquisicao; }

    @Override
    public String toString() {
        return "AeronaveCia{idAeronave=" + idAeronave + ", idCia=" + idCia
                + ", aquisicao=" + dataAquisicao + '}';
    }
}
