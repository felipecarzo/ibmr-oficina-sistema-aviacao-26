package com.aviacao.model;

public class Aeroporto {
    private int codAeroporto;
    private String nome;
    private String sigla;
    private String cidade;

    public Aeroporto() {}

    public Aeroporto(int codAeroporto, String nome, String sigla, String cidade) {
        this.codAeroporto = codAeroporto;
        this.nome = nome;
        this.sigla = sigla;
        this.cidade = cidade;
    }

    public int getCodAeroporto() { return codAeroporto; }
    public void setCodAeroporto(int codAeroporto) { this.codAeroporto = codAeroporto; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getSigla() { return sigla; }
    public void setSigla(String sigla) { this.sigla = sigla; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    @Override
    public String toString() {
        return "Aeroporto{" +
                "cod=" + codAeroporto +
                ", nome='" + nome + '\'' +
                ", sigla='" + sigla + '\'' +
                ", cidade='" + cidade + '\'' +
                '}';
    }
}
