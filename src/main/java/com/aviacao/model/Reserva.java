package com.aviacao.model;

import java.time.LocalDate;

public class Reserva {
    private String codReserva;
    private int idPassageiro;
    private String codVoo;
    private String assento;
    private LocalDate dtReserva;

    public Reserva() {}

    public Reserva(String codReserva, int idPassageiro, String codVoo,
                   String assento, LocalDate dtReserva) {
        this.codReserva = codReserva;
        this.idPassageiro = idPassageiro;
        this.codVoo = codVoo;
        this.assento = assento;
        this.dtReserva = dtReserva;
    }

    public String getCodReserva() { return codReserva; }
    public void setCodReserva(String codReserva) { this.codReserva = codReserva; }

    public int getIdPassageiro() { return idPassageiro; }
    public void setIdPassageiro(int idPassageiro) { this.idPassageiro = idPassageiro; }

    public String getCodVoo() { return codVoo; }
    public void setCodVoo(String codVoo) { this.codVoo = codVoo; }

    public String getAssento() { return assento; }
    public void setAssento(String assento) { this.assento = assento; }

    public LocalDate getDtReserva() { return dtReserva; }
    public void setDtReserva(LocalDate dtReserva) { this.dtReserva = dtReserva; }

    @Override
    public String toString() {
        return "Reserva{cod='" + codReserva + '\'' + ", passageiro=" + idPassageiro
                + ", voo='" + codVoo + '\'' + ", assento='" + assento + '\'' + '}';
    }
}
