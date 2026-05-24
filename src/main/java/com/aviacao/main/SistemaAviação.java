package com.aviacao.main;

import com.aviacao.dao.*;
import com.aviacao.model.*;
import com.aviacao.service.Validador;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class SistemaAviação {
    private final Scanner sc = new Scanner(System.in);
    private final AeronaveDAO aeronaveDAO = new AeronaveDAO();
    private final CiaAereaDAO ciaDAO = new CiaAereaDAO();
    private final AeroportoDAO aeroportoDAO = new AeroportoDAO();
    private final PassageiroDAO passageiroDAO = new PassageiroDAO();
    private final VooDAO vooDAO = new VooDAO();
    private final AeronaveCiaDAO acDAO = new AeronaveCiaDAO();
    private final ReservaDAO reservaDAO = new ReservaDAO();
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        new SistemaAviação().iniciar();
    }

    public void iniciar() {
        int op;
        do {
            exibirMenu();
            op = lerInt("Opção");
            switch (op) {
                case 1 -> gerenciarAeronaves();
                case 2 -> gerenciarCompanhias();
                case 3 -> gerenciarAeroportos();
                case 4 -> gerenciarPassageiros();
                case 5 -> gerenciarVoos();
                case 6 -> vincularAeronaveCia();
                case 7 -> efetuarReserva();
                case 8 -> cancelarReserva();
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (op != 0);
    }

    private void exibirMenu() {
        System.out.println("\n=== SISTEMA DE AVIAÇÃO ===");
        System.out.println("1. Gerenciar Aeronaves");
        System.out.println("2. Gerenciar Companhias");
        System.out.println("3. Gerenciar Aeroportos");
        System.out.println("4. Gerenciar Passageiros");
        System.out.println("5. Gerenciar Voos");
        System.out.println("6. Vincular Aeronave a Companhia");
        System.out.println("7. Efetuar Reserva");
        System.out.println("8. Cancelar Reserva");
        System.out.println("0. Sair");
    }

    // --- UTILITÁRIOS ---

    private int lerInt(String campo) {
        while (true) {
            try {
                System.out.print(campo + ": ");
                return sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Digite um número.");
                sc.next();
            }
        }
    }

    private String lerString(String campo) {
        System.out.print(campo + ": ");
        return sc.next().trim();
    }

    private String lerLinha(String campo) {
        System.out.print(campo + ": ");
        sc.nextLine();
        return sc.nextLine().trim();
    }

    private LocalDate lerData(String campo) {
        while (true) {
            try {
                System.out.print(campo + " (dd/MM/yyyy): ");
                return LocalDate.parse(sc.next().trim(), dtf);
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida. Use dd/MM/yyyy.");
            }
        }
    }

    private char lerStatus(String campo) {
        while (true) {
            System.out.print(campo + " (S/N): ");
            String s = sc.next().trim().toUpperCase();
            if (s.length() == 1 && Validador.validarStatusAtivo(s.charAt(0))) {
                return s.charAt(0);
            }
            System.out.println("Valor inválido. Digite S ou N.");
        }
    }

    private void aguardar() {
        System.out.println("Pressione ENTER para continuar...");
        sc.nextLine();
        sc.nextLine();
    }

    private void listar(List<?> list) {
        if (list.isEmpty()) {
            System.out.println("Nenhum registro encontrado.");
            return;
        }
        list.forEach(System.out::println);
    }

    // --- AERONAVES ---

    private void gerenciarAeronaves() {
        int op;
        do {
            System.out.println("\n--- GERENCIAR AERONAVES ---");
            System.out.println("1. Cadastrar  2. Listar  3. Buscar ID  4. Atualizar  5. Excluir  0. Voltar");
            op = lerInt("Opção");
            switch (op) {
                case 1 -> cadastrarAeronave();
                case 2 -> listar(aeronaveDAO.findAll());
                case 3 -> buscarAeronave();
                case 4 -> atualizarAeronave();
                case 5 -> excluirAeronave();
            }
        } while (op != 0);
    }

    private void cadastrarAeronave() {
        System.out.println("--- Cadastrar Aeronave ---");
        Aeronave a = new Aeronave();
        a.setModelo(lerLinha("Modelo"));
        a.setCapacidade(lerInt("Capacidade"));
        a.setEnvergadura(lerInt("Envergadura"));
        a.setFabricante(lerLinha("Fabricante"));
        a.setStatusAtivo(lerStatus("Ativo"));
        aeronaveDAO.insert(a);
        System.out.println("Aeronave cadastrada! ID: " + a.getIdAeronave());
    }

    private void buscarAeronave() {
        int id = lerInt("ID da aeronave");
        Aeronave a = aeronaveDAO.findById(id);
        if (a != null) System.out.println(a);
        else System.out.println("Aeronave não encontrada.");
    }

    private void atualizarAeronave() {
        int id = lerInt("ID da aeronave");
        Aeronave a = aeronaveDAO.findById(id);
        if (a == null) { System.out.println("Aeronave não encontrada."); return; }
        System.out.println("Deixe em branco para manter o valor atual.");
        a.setModelo(lerLinha("Modelo (" + a.getModelo() + ")"));
        a.setCapacidade(lerInt("Capacidade (" + a.getCapacidade() + ")"));
        a.setEnvergadura(lerInt("Envergadura (" + a.getEnvergadura() + ")"));
        a.setFabricante(lerLinha("Fabricante (" + a.getFabricante() + ")"));
        a.setStatusAtivo(lerStatus("Ativo (" + a.getStatusAtivo() + ")"));
        aeronaveDAO.update(a);
        System.out.println("Aeronave atualizada.");
    }

    private void excluirAeronave() {
        int id = lerInt("ID da aeronave");
        aeronaveDAO.delete(id);
        System.out.println("Aeronave excluída (se não houver vínculos).");
    }

    // --- COMPANHIAS ---

    private void gerenciarCompanhias() {
        int op;
        do {
            System.out.println("\n--- GERENCIAR COMPANHIAS ---");
            System.out.println("1. Cadastrar  2. Listar  3. Buscar ID  4. Atualizar  5. Excluir  0. Voltar");
            op = lerInt("Opção");
            switch (op) {
                case 1 -> cadastrarCia();
                case 2 -> listar(ciaDAO.findAll());
                case 3 -> buscarCia();
                case 4 -> atualizarCia();
                case 5 -> excluirCia();
            }
        } while (op != 0);
    }

    private void cadastrarCia() {
        System.out.println("--- Cadastrar Companhia ---");
        CiaAerea c = new CiaAerea();
        c.setNome(lerLinha("Nome"));
        String cnpj = lerString("CNPJ (14 dígitos)");
        while (!Validador.validarCnpj(cnpj)) {
            System.out.println("CNPJ inválido. Devem ser 14 dígitos.");
            cnpj = lerString("CNPJ (14 dígitos)");
        }
        c.setCnpj(cnpj);
        String email = lerString("Email");
        while (!Validador.validarEmail(email)) {
            System.out.println("Email inválido.");
            email = lerString("Email");
        }
        c.setEmail(email);
        c.setStatusAtivo(lerStatus("Ativo"));
        ciaDAO.insert(c);
        System.out.println("Companhia cadastrada! ID: " + c.getIdCia());
    }

    private void buscarCia() {
        int id = lerInt("ID da companhia");
        CiaAerea c = ciaDAO.findById(id);
        if (c != null) System.out.println(c);
        else System.out.println("Companhia não encontrada.");
    }

    private void atualizarCia() {
        int id = lerInt("ID da companhia");
        CiaAerea c = ciaDAO.findById(id);
        if (c == null) { System.out.println("Companhia não encontrada."); return; }
        c.setNome(lerLinha("Nome (" + c.getNome() + ")"));
        c.setCnpj(lerString("CNPJ (" + c.getCnpj() + ")"));
        c.setEmail(lerString("Email (" + c.getEmail() + ")"));
        c.setStatusAtivo(lerStatus("Ativo (" + c.getStatusAtivo() + ")"));
        ciaDAO.update(c);
        System.out.println("Companhia atualizada.");
    }

    private void excluirCia() {
        int id = lerInt("ID da companhia");
        ciaDAO.delete(id);
        System.out.println("Companhia excluída (se não houver vínculos).");
    }

    // --- AEROPORTOS ---

    private void gerenciarAeroportos() {
        int op;
        do {
            System.out.println("\n--- GERENCIAR AEROPORTOS ---");
            System.out.println("1. Cadastrar  2. Listar  3. Buscar ID  4. Atualizar  5. Excluir  0. Voltar");
            op = lerInt("Opção");
            switch (op) {
                case 1 -> cadastrarAeroporto();
                case 2 -> listar(aeroportoDAO.findAll());
                case 3 -> buscarAeroporto();
                case 4 -> atualizarAeroporto();
                case 5 -> excluirAeroporto();
            }
        } while (op != 0);
    }

    private void cadastrarAeroporto() {
        System.out.println("--- Cadastrar Aeroporto ---");
        Aeroporto a = new Aeroporto();
        a.setNome(lerLinha("Nome"));
        String sigla = lerString("Sigla (3 letras)").toUpperCase();
        while (!Validador.validarSigla(sigla)) {
            System.out.println("Sigla inválida. Devem ser 3 letras.");
            sigla = lerString("Sigla (3 letras)").toUpperCase();
        }
        a.setSigla(sigla);
        a.setCidade(lerLinha("Cidade"));
        aeroportoDAO.insert(a);
        System.out.println("Aeroporto cadastrado! Código: " + a.getCodAeroporto());
    }

    private void buscarAeroporto() {
        int id = lerInt("Código do aeroporto");
        Aeroporto a = aeroportoDAO.findById(id);
        if (a != null) System.out.println(a);
        else System.out.println("Aeroporto não encontrado.");
    }

    private void atualizarAeroporto() {
        int id = lerInt("Código do aeroporto");
        Aeroporto a = aeroportoDAO.findById(id);
        if (a == null) { System.out.println("Aeroporto não encontrado."); return; }
        a.setNome(lerLinha("Nome (" + a.getNome() + ")"));
        a.setSigla(lerString("Sigla (" + a.getSigla() + ")"));
        a.setCidade(lerLinha("Cidade (" + a.getCidade() + ")"));
        aeroportoDAO.update(a);
        System.out.println("Aeroporto atualizado.");
    }

    private void excluirAeroporto() {
        int id = lerInt("Código do aeroporto");
        aeroportoDAO.delete(id);
        System.out.println("Aeroporto excluído (se não houver vínculos).");
    }

    // --- PASSAGEIROS ---

    private void gerenciarPassageiros() {
        int op;
        do {
            System.out.println("\n--- GERENCIAR PASSAGEIROS ---");
            System.out.println("1. Cadastrar  2. Listar  3. Buscar ID  4. Atualizar  5. Excluir  0. Voltar");
            op = lerInt("Opção");
            switch (op) {
                case 1 -> cadastrarPassageiro();
                case 2 -> listar(passageiroDAO.findAll());
                case 3 -> buscarPassageiro();
                case 4 -> atualizarPassageiro();
                case 5 -> excluirPassageiro();
            }
        } while (op != 0);
    }

    private void cadastrarPassageiro() {
        System.out.println("--- Cadastrar Passageiro ---");
        Passageiro p = new Passageiro();
        p.setNome(lerLinha("Nome"));
        String email = lerString("Email");
        while (!Validador.validarEmail(email)) {
            System.out.println("Email inválido.");
            email = lerString("Email");
        }
        p.setEmail(email);
        String tel = lerString("Telefone");
        while (!Validador.validarTelefone(tel)) {
            System.out.println("Telefone inválido. 10-11 dígitos.");
            tel = lerString("Telefone");
        }
        p.setTel(tel);
        LocalDate dt = lerData("Data de nascimento");
        while (!Validador.validarDataNascimento(dt)) {
            System.out.println("Data não pode ser futura.");
            dt = lerData("Data de nascimento");
        }
        p.setDtNasc(dt);
        passageiroDAO.insert(p);
        System.out.println("Passageiro cadastrado! ID: " + p.getIdPassageiro());
    }

    private void buscarPassageiro() {
        int id = lerInt("ID do passageiro");
        Passageiro p = passageiroDAO.findById(id);
        if (p != null) System.out.println(p);
        else System.out.println("Passageiro não encontrado.");
    }

    private void atualizarPassageiro() {
        int id = lerInt("ID do passageiro");
        Passageiro p = passageiroDAO.findById(id);
        if (p == null) { System.out.println("Passageiro não encontrado."); return; }
        p.setNome(lerLinha("Nome (" + p.getNome() + ")"));
        p.setEmail(lerString("Email (" + p.getEmail() + ")"));
        p.setTel(lerString("Telefone (" + p.getTel() + ")"));
        p.setDtNasc(lerData("Nascimento (" + p.getDtNasc() + ")"));
        passageiroDAO.update(p);
        System.out.println("Passageiro atualizado.");
    }

    private void excluirPassageiro() {
        int id = lerInt("ID do passageiro");
        passageiroDAO.delete(id);
        System.out.println("Passageiro excluído (se não houver vínculos).");
    }

    // --- VOOS ---

    private void gerenciarVoos() {
        int op;
        do {
            System.out.println("\n--- GERENCIAR VOOS ---");
            System.out.println("1. Cadastrar  2. Listar  3. Buscar ID  4. Buscar rota  5. Excluir  0. Voltar");
            op = lerInt("Opção");
            switch (op) {
                case 1 -> cadastrarVoo();
                case 2 -> listar(vooDAO.findAll());
                case 3 -> buscarVoo();
                case 4 -> buscarRota();
                case 5 -> excluirVoo();
            }
        } while (op != 0);
    }

    private void cadastrarVoo() {
        System.out.println("--- Cadastrar Voo ---");
        Voo v = new Voo();
        v.setCodVoo(lerString("Código do voo").toUpperCase());
        int partida = lerInt("Hora partida (HHMM)");
        while (!Validador.validarHorario(partida)) {
            System.out.println("Horário inválido.");
            partida = lerInt("Hora partida (HHMM)");
        }
        v.setHoraPartida(partida);
        int chegada = lerInt("Hora chegada (HHMM)");
        while (!Validador.validarHorario(chegada)) {
            System.out.println("Horário inválido.");
            chegada = lerInt("Hora chegada (HHMM)");
        }
        v.setHoraChegada(chegada);
        v.setCodAeroporto(lerInt("Código do aeroporto"));
        v.setCidadeOrigem(lerLinha("Cidade origem"));
        v.setCidadeDestino(lerLinha("Cidade destino"));
        vooDAO.insert(v);
        System.out.println("Voo cadastrado!");
    }

    private void buscarVoo() {
        Voo v = vooDAO.findById(lerString("Código do voo").toUpperCase());
        if (v != null) System.out.println(v);
        else System.out.println("Voo não encontrado.");
    }

    private void buscarRota() {
        String origem = lerLinha("Cidade origem");
        String destino = lerLinha("Cidade destino");
        List<Voo> voos = vooDAO.findByOrigemDestino(origem, destino);
        listar(voos);
    }

    private void excluirVoo() {
        vooDAO.delete(lerString("Código do voo").toUpperCase());
        System.out.println("Voo excluído (se não houver vínculos).");
    }

    // --- VINCULAR AERONAVE A COMPANHIA ---

    private void vincularAeronaveCia() {
        System.out.println("--- Vincular Aeronave a Companhia ---");
        int idAer = lerInt("ID da aeronave");
        Aeronave a = aeronaveDAO.findById(idAer);
        if (a == null) { System.out.println("Aeronave não encontrada."); return; }
        int idCia = lerInt("ID da companhia");
        CiaAerea c = ciaDAO.findById(idCia);
        if (c == null) { System.out.println("Companhia não encontrada."); return; }
        LocalDate data = lerData("Data de aquisição");
        acDAO.insert(new AeronaveCia(idAer, idCia, data));
        System.out.println("Vínculo criado!");
    }

    // --- RESERVAS ---

    private void efetuarReserva() {
        System.out.println("--- Efetuar Reserva ---");
        String cod = lerString("Código da reserva").toUpperCase();
        if (reservaDAO.findById(cod) != null) {
            System.out.println("Código já existe.");
            return;
        }
        int idPass = lerInt("ID do passageiro");
        if (passageiroDAO.findById(idPass) == null) {
            System.out.println("Passageiro não encontrado.");
            return;
        }
        String codVoo = lerString("Código do voo").toUpperCase();
        if (vooDAO.findById(codVoo) == null) {
            System.out.println("Voo não encontrado.");
            return;
        }
        String assento = lerString("Assento (ex: 12A)").toUpperCase();
        while (!Validador.validarAssento(assento)) {
            System.out.println("Assento inválido. Ex: 12A");
            assento = lerString("Assento (ex: 12A)").toUpperCase();
        }
        if (reservaDAO.assentoOcupado(codVoo, assento)) {
            System.out.println("Assento já ocupado neste voo.");
            return;
        }
        reservaDAO.insert(new Reserva(cod, idPass, codVoo, assento, LocalDate.now()));
        System.out.println("Reserva efetuada!");
    }

    private void cancelarReserva() {
        System.out.println("--- Cancelar Reserva ---");
        String cod = lerString("Código da reserva").toUpperCase();
        if (reservaDAO.findById(cod) == null) {
            System.out.println("Reserva não encontrada.");
            return;
        }
        System.out.print("Confirmar cancelamento? (S/N): ");
        if (sc.next().trim().equalsIgnoreCase("S")) {
            reservaDAO.delete(cod);
            System.out.println("Reserva cancelada.");
        }
    }
}
