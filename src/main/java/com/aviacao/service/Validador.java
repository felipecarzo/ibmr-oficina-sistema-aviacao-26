package com.aviacao.service;

import java.time.LocalDate;

public final class Validador {

    private Validador() {}

    public static boolean validarCnpj(String cnpj) {
        return cnpj != null && cnpj.replaceAll("\\D", "").length() == 14;
    }

    public static boolean validarEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }

    public static boolean validarSigla(String sigla) {
        return sigla != null && sigla.matches("[A-Za-z]{3}");
    }

    public static boolean validarTelefone(String tel) {
        if (tel == null) return false;
        String digits = tel.replaceAll("\\D", "");
        return digits.length() >= 10 && digits.length() <= 11;
    }

    public static boolean validarDataNascimento(LocalDate dt) {
        return dt != null && !dt.isAfter(LocalDate.now());
    }

    public static boolean validarCapacidade(int cap) {
        return cap >= 1 && cap <= 999;
    }

    public static boolean validarHorario(int hora) {
        return hora >= 0 && hora <= 2359 && (hora % 100) < 60;
    }

    public static boolean validarStatusAtivo(char status) {
        return status == 'S' || status == 'N';
    }

    public static boolean validarAssento(String assento) {
        return assento != null && assento.matches("\\d{1,3}[A-Za-z]");
    }
}
