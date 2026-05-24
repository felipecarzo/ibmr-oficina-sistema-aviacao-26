package com.aviacao.model;

public class Aeronave {
    private int idAeronave;
    private String modelo;
    private int capacidade;
    private int envergadura;
    private String fabricante;
    private char statusAtivo;

    public Aeronave() {}

    public Aeronave(int idAeronave, String modelo, int capacidade, int envergadura,
                    String fabricante, char statusAtivo) {
        this.idAeronave = idAeronave;
        this.modelo = modelo;
        this.capacidade = capacidade;
        this.envergadura = envergadura;
        this.fabricante = fabricante;
        this.statusAtivo = statusAtivo;
    }

    public int getIdAeronave() { return idAeronave; }
    public void setIdAeronave(int idAeronave) { this.idAeronave = idAeronave; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public int getCapacidade() { return capacidade; }
    public void setCapacidade(int capacidade) { this.capacidade = capacidade; }

    public int getEnvergadura() { return envergadura; }
    public void setEnvergadura(int envergadura) { this.envergadura = envergadura; }

    public String getFabricante() { return fabricante; }
    public void setFabricante(String fabricante) { this.fabricante = fabricante; }

    public char getStatusAtivo() { return statusAtivo; }
    public void setStatusAtivo(char statusAtivo) { this.statusAtivo = statusAtivo; }

    @Override
    public String toString() {
        return "Aeronave{id=" + idAeronave + ", modelo='" + modelo + '\''
                + ", capacidade=" + capacidade + ", fabricante='" + fabricante + '\''
                + ", ativo=" + statusAtivo + '}';
    }
}
