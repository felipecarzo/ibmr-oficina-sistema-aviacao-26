package com.aviacao.model;

import java.time.LocalDate;

public class Passageiro {
    private int idPassageiro;
    private String nome;
    private String email;
    private String tel;
    private LocalDate dtNasc;

    public Passageiro() {}

    public Passageiro(int idPassageiro, String nome, String email, String tel, LocalDate dtNasc) {
        this.idPassageiro = idPassageiro;
        this.nome = nome;
        this.email = email;
        this.tel = tel;
        this.dtNasc = dtNasc;
    }

    public int getIdPassageiro() { return idPassageiro; }
    public void setIdPassageiro(int idPassageiro) { this.idPassageiro = idPassageiro; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTel() { return tel; }
    public void setTel(String tel) { this.tel = tel; }

    public LocalDate getDtNasc() { return dtNasc; }
    public void setDtNasc(LocalDate dtNasc) { this.dtNasc = dtNasc; }

    @Override
    public String toString() {
        return "Passageiro{" +
                "id=" + idPassageiro +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", tel='" + tel + '\'' +
                ", dtNasc=" + dtNasc +
                '}';
    }
}
