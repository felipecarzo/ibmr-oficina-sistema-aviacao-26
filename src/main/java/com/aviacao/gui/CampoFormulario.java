package com.aviacao.gui;

import java.util.function.Predicate;

public class CampoFormulario {
    public enum Tipo { TEXTO, INTEIRO, DATA, CHAR_SN }

    private final String rotulo;
    private final Tipo tipo;
    private final Predicate<String> validador;
    private final String mensagemErro;
    private String valor;

    public CampoFormulario(String rotulo, Tipo tipo) {
        this(rotulo, tipo, null, null);
    }

    public CampoFormulario(String rotulo, Tipo tipo, Predicate<String> validador, String mensagemErro) {
        this.rotulo = rotulo;
        this.tipo = tipo;
        this.validador = validador;
        this.mensagemErro = mensagemErro;
        this.valor = "";
    }

    public String getRotulo() { return rotulo; }
    public Tipo getTipo() { return tipo; }
    public Predicate<String> getValidador() { return validador; }
    public String getMensagemErro() { return mensagemErro; }
    public String getValor() { return valor; }
    public void setValor(String valor) { this.valor = valor; }
}
