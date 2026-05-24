package com.aviacao.model;

public class CiaAerea {
    private int idCia;
    private String nome;
    private String cnpj;
    private String email;
    private char statusAtivo;

    public CiaAerea() {}

    public CiaAerea(int idCia, String nome, String cnpj, String email, char statusAtivo) {
        this.idCia = idCia;
        this.nome = nome;
        this.cnpj = cnpj;
        this.email = email;
        this.statusAtivo = statusAtivo;
    }

    public int getIdCia() { return idCia; }
    public void setIdCia(int idCia) { this.idCia = idCia; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public char getStatusAtivo() { return statusAtivo; }
    public void setStatusAtivo(char statusAtivo) { this.statusAtivo = statusAtivo; }

    @Override
    public String toString() {
        return "CiaAerea{" +
                "id=" + idCia +
                ", nome='" + nome + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", email='" + email + '\'' +
                ", ativo=" + statusAtivo +
                '}';
    }
}
