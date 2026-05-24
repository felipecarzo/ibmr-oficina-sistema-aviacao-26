package com.aviacao.gui;

import com.aviacao.dao.*;
import com.aviacao.model.*;
import com.aviacao.service.Validador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    private final AeronaveDAO aeronaveDAO = new AeronaveDAO();
    private final CiaAereaDAO ciaDAO = new CiaAereaDAO();
    private final AeroportoDAO aeroportoDAO = new AeroportoDAO();
    private final PassageiroDAO passageiroDAO = new PassageiroDAO();
    private final VooDAO vooDAO = new VooDAO();
    private final AeronaveCiaDAO acDAO = new AeronaveCiaDAO();
    private final ReservaDAO reservaDAO = new ReservaDAO();
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public MainFrame() {
        setTitle("Sistema de Aviação");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Aeronaves", painelAeronaves());
        abas.addTab("Companhias", painelCompanhias());
        abas.addTab("Aeroportos", painelAeroportos());
        abas.addTab("Passageiros", painelPassageiros());
        abas.addTab("Voos", painelVoos());
        abas.addTab("Vínculo Aero-Cia", painelVinculo());
        abas.addTab("Reservas", painelReservas());

        add(abas);
    }

    private void mostrarErro(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarSucesso(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    // ==================== AERONAVES ====================

    private CrudPanel painelAeronaves() {
        String[] colunas = {"ID", "Modelo", "Capacidade", "Envergadura", "Fabricante", "Ativo"};
        return new CrudPanel(colunas, () -> {
            try {
                List<Object[]> dados = new ArrayList<>();
                for (Aeronave a : aeronaveDAO.findAll())
                    dados.add(new Object[]{a.getIdAeronave(), a.getModelo(), a.getCapacidade(),
                            a.getEnvergadura(), a.getFabricante(), a.getStatusAtivo()});
                return dados;
            } catch (Exception e) {
                mostrarErro("Erro ao carregar aeronaves: " + e.getMessage());
                return List.of();
            }
        }, () -> {
            try {
                CampoFormulario[] campos = {
                    new CampoFormulario("Modelo", CampoFormulario.Tipo.TEXTO),
                    new CampoFormulario("Capacidade", CampoFormulario.Tipo.INTEIRO,
                        s -> s.matches("\\d+") && Validador.validarCapacidade(Integer.parseInt(s)), "Capacidade deve ser 1-999"),
                    new CampoFormulario("Envergadura", CampoFormulario.Tipo.INTEIRO,
                        s -> s.matches("\\d+"), "Envergadura inválida"),
                    new CampoFormulario("Fabricante", CampoFormulario.Tipo.TEXTO),
                    new CampoFormulario("Ativo", CampoFormulario.Tipo.CHAR_SN),
                };
                FormDialog d = new FormDialog(this, "Cadastrar Aeronave", campos);
                d.setVisible(true);
                if (d.foiConfirmado()) {
                    Aeronave a = new Aeronave();
                    a.setModelo(d.getValor(campos[0]));
                    a.setCapacidade(d.getValorInt(campos[1]));
                    a.setEnvergadura(d.getValorInt(campos[2]));
                    a.setFabricante(d.getValor(campos[3]));
                    a.setStatusAtivo(d.getValorChar(campos[4]));
                    if (aeronaveDAO.insert(a))
                        mostrarSucesso("Aeronave cadastrada! ID: " + a.getIdAeronave());
                    else
                        mostrarErro("Erro ao cadastrar aeronave.");
                }
            } catch (Exception e) { mostrarErro("Erro: " + e.getMessage()); }
        }, id -> {
            try {
                Aeronave a = aeronaveDAO.findById((Integer) id);
                if (a == null) { mostrarErro("Aeronave não encontrada."); return; }
                CampoFormulario[] campos = {
                    new CampoFormulario("Modelo", CampoFormulario.Tipo.TEXTO),
                    new CampoFormulario("Capacidade", CampoFormulario.Tipo.INTEIRO,
                        s -> s.matches("\\d+") && Validador.validarCapacidade(Integer.parseInt(s)), "Capacidade deve ser 1-999"),
                    new CampoFormulario("Envergadura", CampoFormulario.Tipo.INTEIRO,
                        s -> s.matches("\\d+"), "Envergadura inválida"),
                    new CampoFormulario("Fabricante", CampoFormulario.Tipo.TEXTO),
                    new CampoFormulario("Ativo", CampoFormulario.Tipo.CHAR_SN),
                };
                campos[0].setValor(a.getModelo());
                campos[1].setValor(String.valueOf(a.getCapacidade()));
                campos[2].setValor(String.valueOf(a.getEnvergadura()));
                campos[3].setValor(a.getFabricante());
                campos[4].setValor(String.valueOf(a.getStatusAtivo()));
                FormDialog d = new FormDialog(this, "Editar Aeronave", campos);
                d.setVisible(true);
                if (d.foiConfirmado()) {
                    a.setModelo(d.getValor(campos[0]));
                    a.setCapacidade(d.getValorInt(campos[1]));
                    a.setEnvergadura(d.getValorInt(campos[2]));
                    a.setFabricante(d.getValor(campos[3]));
                    a.setStatusAtivo(d.getValorChar(campos[4]));
                    if (aeronaveDAO.update(a)) mostrarSucesso("Aeronave atualizada.");
                    else mostrarErro("Erro ao atualizar aeronave.");
                }
            } catch (Exception e) { mostrarErro("Erro: " + e.getMessage()); }
        }, id -> {
            try {
                if (aeronaveDAO.delete((Integer) id))
                    mostrarSucesso("Aeronave excluída.");
                else
                    mostrarErro("Não foi possível excluir. Pode haver vínculos ativos.");
            } catch (Exception e) { mostrarErro("Erro: " + e.getMessage()); }
        });
    }

    // ==================== COMPANHIAS ====================

    private CrudPanel painelCompanhias() {
        String[] colunas = {"ID", "Nome", "CNPJ", "Email", "Ativo"};
        return new CrudPanel(colunas, () -> {
            try {
                List<Object[]> dados = new ArrayList<>();
                for (CiaAerea c : ciaDAO.findAll())
                    dados.add(new Object[]{c.getIdCia(), c.getNome(), c.getCnpj(), c.getEmail(), c.getStatusAtivo()});
                return dados;
            } catch (Exception e) { mostrarErro("Erro ao carregar companhias."); return List.of(); }
        }, () -> {
            try {
                CampoFormulario[] campos = {
                    new CampoFormulario("Nome", CampoFormulario.Tipo.TEXTO),
                    new CampoFormulario("CNPJ", CampoFormulario.Tipo.TEXTO,
                        Validador::validarCnpj, "CNPJ deve ter 14 dígitos"),
                    new CampoFormulario("Email", CampoFormulario.Tipo.TEXTO,
                        Validador::validarEmail, "Email inválido"),
                    new CampoFormulario("Ativo", CampoFormulario.Tipo.CHAR_SN),
                };
                FormDialog d = new FormDialog(this, "Cadastrar Companhia", campos);
                d.setVisible(true);
                if (d.foiConfirmado()) {
                    CiaAerea c = new CiaAerea();
                    c.setNome(d.getValor(campos[0]));
                    c.setCnpj(d.getValor(campos[1]));
                    c.setEmail(d.getValor(campos[2]));
                    c.setStatusAtivo(d.getValorChar(campos[3]));
                    if (ciaDAO.insert(c)) mostrarSucesso("Companhia cadastrada! ID: " + c.getIdCia());
                    else mostrarErro("Erro ao cadastrar companhia.");
                }
            } catch (Exception e) { mostrarErro("Erro: " + e.getMessage()); }
        }, id -> {
            try {
                CiaAerea c = ciaDAO.findById((Integer) id);
                if (c == null) { mostrarErro("Companhia não encontrada."); return; }
                CampoFormulario[] campos = {
                    new CampoFormulario("Nome", CampoFormulario.Tipo.TEXTO),
                    new CampoFormulario("CNPJ", CampoFormulario.Tipo.TEXTO,
                        Validador::validarCnpj, "CNPJ deve ter 14 dígitos"),
                    new CampoFormulario("Email", CampoFormulario.Tipo.TEXTO,
                        Validador::validarEmail, "Email inválido"),
                    new CampoFormulario("Ativo", CampoFormulario.Tipo.CHAR_SN),
                };
                campos[0].setValor(c.getNome());
                campos[1].setValor(c.getCnpj());
                campos[2].setValor(c.getEmail());
                campos[3].setValor(String.valueOf(c.getStatusAtivo()));
                FormDialog d = new FormDialog(this, "Editar Companhia", campos);
                d.setVisible(true);
                if (d.foiConfirmado()) {
                    c.setNome(d.getValor(campos[0]));
                    c.setCnpj(d.getValor(campos[1]));
                    c.setEmail(d.getValor(campos[2]));
                    c.setStatusAtivo(d.getValorChar(campos[3]));
                    if (ciaDAO.update(c)) mostrarSucesso("Companhia atualizada.");
                    else mostrarErro("Erro ao atualizar companhia.");
                }
            } catch (Exception e) { mostrarErro("Erro: " + e.getMessage()); }
        }, id -> {
            try {
                if (ciaDAO.delete((Integer) id)) mostrarSucesso("Companhia excluída.");
                else mostrarErro("Não foi possível excluir. Pode haver vínculos ativos.");
            } catch (Exception e) { mostrarErro("Erro: " + e.getMessage()); }
        });
    }

    // ==================== AEROPORTOS ====================

    private CrudPanel painelAeroportos() {
        String[] colunas = {"Código", "Nome", "Sigla", "Cidade"};
        return new CrudPanel(colunas, () -> {
            try {
                List<Object[]> dados = new ArrayList<>();
                for (Aeroporto a : aeroportoDAO.findAll())
                    dados.add(new Object[]{a.getCodAeroporto(), a.getNome(), a.getSigla(), a.getCidade()});
                return dados;
            } catch (Exception e) { mostrarErro("Erro ao carregar aeroportos."); return List.of(); }
        }, () -> {
            try {
                CampoFormulario[] campos = {
                    new CampoFormulario("Nome", CampoFormulario.Tipo.TEXTO),
                    new CampoFormulario("Sigla", CampoFormulario.Tipo.TEXTO,
                        s -> Validador.validarSigla(s), "Sigla deve ter 3 letras"),
                    new CampoFormulario("Cidade", CampoFormulario.Tipo.TEXTO),
                };
                FormDialog d = new FormDialog(this, "Cadastrar Aeroporto", campos);
                d.setVisible(true);
                if (d.foiConfirmado()) {
                    Aeroporto a = new Aeroporto();
                    a.setNome(d.getValor(campos[0]));
                    a.setSigla(d.getValor(campos[1]).toUpperCase());
                    a.setCidade(d.getValor(campos[2]));
                    if (aeroportoDAO.insert(a)) mostrarSucesso("Aeroporto cadastrado! Código: " + a.getCodAeroporto());
                    else mostrarErro("Erro ao cadastrar aeroporto.");
                }
            } catch (Exception e) { mostrarErro("Erro: " + e.getMessage()); }
        }, id -> {
            try {
                Aeroporto a = aeroportoDAO.findById((Integer) id);
                if (a == null) { mostrarErro("Aeroporto não encontrado."); return; }
                CampoFormulario[] campos = {
                    new CampoFormulario("Nome", CampoFormulario.Tipo.TEXTO),
                    new CampoFormulario("Sigla", CampoFormulario.Tipo.TEXTO,
                        s -> Validador.validarSigla(s), "Sigla deve ter 3 letras"),
                    new CampoFormulario("Cidade", CampoFormulario.Tipo.TEXTO),
                };
                campos[0].setValor(a.getNome());
                campos[1].setValor(a.getSigla());
                campos[2].setValor(a.getCidade());
                FormDialog d = new FormDialog(this, "Editar Aeroporto", campos);
                d.setVisible(true);
                if (d.foiConfirmado()) {
                    a.setNome(d.getValor(campos[0]));
                    a.setSigla(d.getValor(campos[1]).toUpperCase());
                    a.setCidade(d.getValor(campos[2]));
                    if (aeroportoDAO.update(a)) mostrarSucesso("Aeroporto atualizado.");
                    else mostrarErro("Erro ao atualizar aeroporto.");
                }
            } catch (Exception e) { mostrarErro("Erro: " + e.getMessage()); }
        }, id -> {
            try {
                if (aeroportoDAO.delete((Integer) id)) mostrarSucesso("Aeroporto excluído.");
                else mostrarErro("Não foi possível excluir. Pode haver vínculos ativos.");
            } catch (Exception e) { mostrarErro("Erro: " + e.getMessage()); }
        });
    }

    // ==================== PASSAGEIROS ====================

    private CrudPanel painelPassageiros() {
        String[] colunas = {"ID", "Nome", "Email", "Telefone", "Nascimento"};
        return new CrudPanel(colunas, () -> {
            try {
                List<Object[]> dados = new ArrayList<>();
                for (Passageiro p : passageiroDAO.findAll())
                    dados.add(new Object[]{p.getIdPassageiro(), p.getNome(), p.getEmail(),
                            p.getTel(), p.getDtNasc() != null ? p.getDtNasc().format(dtf) : ""});
                return dados;
            } catch (Exception e) { mostrarErro("Erro ao carregar passageiros."); return List.of(); }
        }, () -> {
            try {
                CampoFormulario[] campos = {
                    new CampoFormulario("Nome", CampoFormulario.Tipo.TEXTO),
                    new CampoFormulario("Email", CampoFormulario.Tipo.TEXTO,
                        Validador::validarEmail, "Email inválido"),
                    new CampoFormulario("Telefone", CampoFormulario.Tipo.TEXTO,
                        Validador::validarTelefone, "Telefone inválido (10-11 dígitos)"),
                    new CampoFormulario("Nascimento (dd/MM/yyyy)", CampoFormulario.Tipo.TEXTO),
                };
                FormDialog d = new FormDialog(this, "Cadastrar Passageiro", campos);
                d.setVisible(true);
                if (d.foiConfirmado()) {
                    LocalDate dt = d.getValorData(campos[3]);
                    if (dt == null || !Validador.validarDataNascimento(dt)) {
                        mostrarErro("Data inválida ou futura.");
                        return;
                    }
                    Passageiro p = new Passageiro();
                    p.setNome(d.getValor(campos[0]));
                    p.setEmail(d.getValor(campos[1]));
                    p.setTel(d.getValor(campos[2]));
                    p.setDtNasc(dt);
                    if (passageiroDAO.insert(p)) mostrarSucesso("Passageiro cadastrado! ID: " + p.getIdPassageiro());
                    else mostrarErro("Erro ao cadastrar passageiro.");
                }
            } catch (Exception e) { mostrarErro("Erro: " + e.getMessage()); }
        }, id -> {
            try {
                Passageiro p = passageiroDAO.findById((Integer) id);
                if (p == null) { mostrarErro("Passageiro não encontrado."); return; }
                CampoFormulario[] campos = {
                    new CampoFormulario("Nome", CampoFormulario.Tipo.TEXTO),
                    new CampoFormulario("Email", CampoFormulario.Tipo.TEXTO,
                        Validador::validarEmail, "Email inválido"),
                    new CampoFormulario("Telefone", CampoFormulario.Tipo.TEXTO,
                        Validador::validarTelefone, "Telefone inválido (10-11 dígitos)"),
                    new CampoFormulario("Nascimento (dd/MM/yyyy)", CampoFormulario.Tipo.TEXTO),
                };
                campos[0].setValor(p.getNome());
                campos[1].setValor(p.getEmail());
                campos[2].setValor(p.getTel());
                campos[3].setValor(p.getDtNasc() != null ? p.getDtNasc().format(dtf) : "");
                FormDialog d = new FormDialog(this, "Editar Passageiro", campos);
                d.setVisible(true);
                if (d.foiConfirmado()) {
                    LocalDate dt = d.getValorData(campos[3]);
                    p.setNome(d.getValor(campos[0]));
                    p.setEmail(d.getValor(campos[1]));
                    p.setTel(d.getValor(campos[2]));
                    p.setDtNasc(dt);
                    if (passageiroDAO.update(p)) mostrarSucesso("Passageiro atualizado.");
                    else mostrarErro("Erro ao atualizar passageiro.");
                }
            } catch (Exception e) { mostrarErro("Erro: " + e.getMessage()); }
        }, id -> {
            try {
                if (passageiroDAO.delete((Integer) id)) mostrarSucesso("Passageiro excluído.");
                else mostrarErro("Não foi possível excluir. Pode haver vínculos ativos.");
            } catch (Exception e) { mostrarErro("Erro: " + e.getMessage()); }
        });
    }

    // ==================== VOOS ====================

    private CrudPanel painelVoos() {
        String[] colunas = {"Código", "Partida", "Chegada", "Aeroporto", "Origem", "Destino"};
        return new CrudPanel(colunas, () -> {
            try {
                List<Object[]> dados = new ArrayList<>();
                for (Voo v : vooDAO.findAll())
                    dados.add(new Object[]{v.getCodVoo(), v.getHoraPartida(), v.getHoraChegada(),
                            v.getCodAeroporto(), v.getCidadeOrigem(), v.getCidadeDestino()});
                return dados;
            } catch (Exception e) { mostrarErro("Erro ao carregar voos."); return List.of(); }
        }, () -> {
            try {
                CampoFormulario[] campos = {
                    new CampoFormulario("Código", CampoFormulario.Tipo.TEXTO),
                    new CampoFormulario("Partida (HHMM)", CampoFormulario.Tipo.INTEIRO,
                        s -> s.matches("\\d+") && Validador.validarHorario(Integer.parseInt(s)), "Horário inválido"),
                    new CampoFormulario("Chegada (HHMM)", CampoFormulario.Tipo.INTEIRO,
                        s -> s.matches("\\d+") && Validador.validarHorario(Integer.parseInt(s)), "Horário inválido"),
                    new CampoFormulario("Código Aeroporto", CampoFormulario.Tipo.INTEIRO,
                        s -> s.matches("\\d+"), "Código inválido"),
                    new CampoFormulario("Cidade Origem", CampoFormulario.Tipo.TEXTO),
                    new CampoFormulario("Cidade Destino", CampoFormulario.Tipo.TEXTO),
                };
                FormDialog d = new FormDialog(this, "Cadastrar Voo", campos);
                d.setVisible(true);
                if (d.foiConfirmado()) {
                    Voo v = new Voo();
                    v.setCodVoo(d.getValor(campos[0]).toUpperCase());
                    v.setHoraPartida(d.getValorInt(campos[1]));
                    v.setHoraChegada(d.getValorInt(campos[2]));
                    v.setCodAeroporto(d.getValorInt(campos[3]));
                    v.setCidadeOrigem(d.getValor(campos[4]));
                    v.setCidadeDestino(d.getValor(campos[5]));
                    if (vooDAO.insert(v)) mostrarSucesso("Voo cadastrado!");
                    else mostrarErro("Erro ao cadastrar voo.");
                }
            } catch (Exception e) { mostrarErro("Erro: " + e.getMessage()); }
        }, id -> {
            try {
                String cod = (String) id;
                Voo v = vooDAO.findById(cod);
                if (v == null) { mostrarErro("Voo não encontrado."); return; }
                CampoFormulario[] campos = {
                    new CampoFormulario("Partida (HHMM)", CampoFormulario.Tipo.INTEIRO,
                        s -> s.matches("\\d+") && Validador.validarHorario(Integer.parseInt(s)), "Horário inválido"),
                    new CampoFormulario("Chegada (HHMM)", CampoFormulario.Tipo.INTEIRO,
                        s -> s.matches("\\d+") && Validador.validarHorario(Integer.parseInt(s)), "Horário inválido"),
                    new CampoFormulario("Código Aeroporto", CampoFormulario.Tipo.INTEIRO,
                        s -> s.matches("\\d+"), "Código inválido"),
                    new CampoFormulario("Cidade Origem", CampoFormulario.Tipo.TEXTO),
                    new CampoFormulario("Cidade Destino", CampoFormulario.Tipo.TEXTO),
                };
                campos[0].setValor(String.valueOf(v.getHoraPartida()));
                campos[1].setValor(String.valueOf(v.getHoraChegada()));
                campos[2].setValor(String.valueOf(v.getCodAeroporto()));
                campos[3].setValor(v.getCidadeOrigem());
                campos[4].setValor(v.getCidadeDestino());
                FormDialog d = new FormDialog(this, "Editar Voo", campos);
                d.setVisible(true);
                if (d.foiConfirmado()) {
                    v.setHoraPartida(d.getValorInt(campos[0]));
                    v.setHoraChegada(d.getValorInt(campos[1]));
                    v.setCodAeroporto(d.getValorInt(campos[2]));
                    v.setCidadeOrigem(d.getValor(campos[3]));
                    v.setCidadeDestino(d.getValor(campos[4]));
                    if (vooDAO.update(v)) mostrarSucesso("Voo atualizado.");
                    else mostrarErro("Erro ao atualizar voo.");
                }
            } catch (Exception e) { mostrarErro("Erro: " + e.getMessage()); }
        }, id -> {
            try {
                if (vooDAO.delete((String) id)) mostrarSucesso("Voo excluído.");
                else mostrarErro("Não foi possível excluir. Pode haver vínculos ativos.");
            } catch (Exception e) { mostrarErro("Erro: " + e.getMessage()); }
        });
    }

    // ==================== VÍNCULO AERONAVE-CIA ====================

    private JPanel painelVinculo() {
        JPanel p = new JPanel(new BorderLayout(8, 8));

        String[] colunas = {"ID Aeronave", "ID Cia", "Data Aquisição"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabela = new JTable(modelo);
        tabela.setFillsViewportHeight(true);

        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnVincular = new JButton("Vincular");
        JButton btnDesvincular = new JButton("Desvincular");
        JTextField txtAero = new JTextField(6);
        JTextField txtCia = new JTextField(6);

        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topo.add(new JLabel("Aero ID:"));
        topo.add(txtAero);
        topo.add(new JLabel("Cia ID:"));
        topo.add(txtCia);
        topo.add(btnVincular);
        topo.add(btnDesvincular);
        topo.add(btnAtualizar);

        Runnable carregar = () -> {
            try {
                modelo.setRowCount(0);
                for (AeronaveCia ac : acDAO.findAll()) {
                    modelo.addRow(new Object[]{ac.getIdAeronave(), ac.getIdCia(),
                            ac.getDataAquisicao() != null ? ac.getDataAquisicao().format(dtf) : ""});
                }
            } catch (Exception e) { mostrarErro("Erro ao carregar vínculos."); }
        };

        btnAtualizar.addActionListener(e -> carregar.run());
        btnVincular.addActionListener(e -> {
            try {
                int idA = Integer.parseInt(txtAero.getText().trim());
                int idC = Integer.parseInt(txtCia.getText().trim());
                if (aeronaveDAO.findById(idA) == null) { mostrarErro("Aeronave não encontrada."); return; }
                if (ciaDAO.findById(idC) == null) { mostrarErro("Companhia não encontrada."); return; }
                String dataStr = JOptionPane.showInputDialog(p, "Data aquisição (dd/MM/yyyy):");
                if (dataStr == null) return;
                LocalDate data = LocalDate.parse(dataStr, dtf);
                if (acDAO.insert(new AeronaveCia(idA, idC, data))) {
                    mostrarSucesso("Vínculo criado!");
                    carregar.run();
                } else {
                    mostrarErro("Erro ao criar vínculo.");
                }
            } catch (NumberFormatException ex) { mostrarErro("IDs inválidos."); }
            catch (Exception ex) { mostrarErro("Erro: " + ex.getMessage()); }
        });
        btnDesvincular.addActionListener(e -> {
            try {
                int row = tabela.getSelectedRow();
                if (row < 0) { mostrarErro("Selecione um vínculo."); return; }
                int idA = (Integer) modelo.getValueAt(row, 0);
                int idC = (Integer) modelo.getValueAt(row, 1);
                if (JOptionPane.showConfirmDialog(p, "Desvincular?", "Confirma",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    if (acDAO.delete(idA, idC)) { mostrarSucesso("Vínculo removido."); carregar.run(); }
                    else mostrarErro("Erro ao remover vínculo.");
                }
            } catch (Exception ex) { mostrarErro("Erro: " + ex.getMessage()); }
        });

        p.add(topo, BorderLayout.NORTH);
        p.add(new JScrollPane(tabela), BorderLayout.CENTER);
        SwingUtilities.invokeLater(carregar);
        return p;
    }

    // ==================== RESERVAS ====================

    private CrudPanel painelReservas() {
        String[] colunas = {"Código", "Passageiro", "Voo", "Assento", "Data"};
        return new CrudPanel(colunas, () -> {
            try {
                List<Object[]> dados = new ArrayList<>();
                for (Reserva r : reservaDAO.findAll())
                    dados.add(new Object[]{r.getCodReserva(), r.getIdPassageiro(), r.getCodVoo(),
                            r.getAssento(), r.getDtReserva() != null ? r.getDtReserva().format(dtf) : ""});
                return dados;
            } catch (Exception e) { mostrarErro("Erro ao carregar reservas."); return List.of(); }
        }, () -> {
            try {
                CampoFormulario[] campos = {
                    new CampoFormulario("Código", CampoFormulario.Tipo.TEXTO),
                    new CampoFormulario("ID Passageiro", CampoFormulario.Tipo.INTEIRO,
                        s -> s.matches("\\d+"), "ID inválido"),
                    new CampoFormulario("Código Voo", CampoFormulario.Tipo.TEXTO),
                    new CampoFormulario("Assento (ex: 12A)", CampoFormulario.Tipo.TEXTO,
                        Validador::validarAssento, "Assento inválido (ex: 12A)"),
                };
                FormDialog d = new FormDialog(this, "Efetuar Reserva", campos);
                d.setVisible(true);
                if (d.foiConfirmado()) {
                    String cod = d.getValor(campos[0]).toUpperCase();
                    if (reservaDAO.findById(cod) != null) { mostrarErro("Código já existe."); return; }
                    int idP = d.getValorInt(campos[1]);
                    if (passageiroDAO.findById(idP) == null) { mostrarErro("Passageiro não encontrado."); return; }
                    String cVoo = d.getValor(campos[2]).toUpperCase();
                    if (vooDAO.findById(cVoo) == null) { mostrarErro("Voo não encontrado."); return; }
                    String assento = d.getValor(campos[3]).toUpperCase();
                    if (reservaDAO.assentoOcupado(cVoo, assento)) { mostrarErro("Assento já ocupado."); return; }
                    if (reservaDAO.insert(new Reserva(cod, idP, cVoo, assento, LocalDate.now())))
                        mostrarSucesso("Reserva efetuada!");
                    else mostrarErro("Erro ao efetuar reserva.");
                }
            } catch (Exception e) { mostrarErro("Erro: " + e.getMessage()); }
        }, id -> {
            try {
                String cod = (String) id;
                if (reservaDAO.findById(cod) == null) { mostrarErro("Reserva não encontrada."); return; }
                CampoFormulario[] campos = {
                    new CampoFormulario("Assento (ex: 12A)", CampoFormulario.Tipo.TEXTO,
                        Validador::validarAssento, "Assento inválido"),
                };
                FormDialog d = new FormDialog(this, "Editar Reserva - Novo Assento", campos);
                d.setVisible(true);
                if (d.foiConfirmado()) {
                    Reserva r = reservaDAO.findById(cod);
                    if (r != null) {
                        String novoAssento = d.getValor(campos[0]).toUpperCase();
                        if (reservaDAO.assentoOcupado(r.getCodVoo(), novoAssento)) { mostrarErro("Assento já ocupado."); return; }
                        r.setAssento(novoAssento);
                        mostrarSucesso("Assento alterado.");
                    }
                }
            } catch (Exception e) { mostrarErro("Erro: " + e.getMessage()); }
        }, id -> {
            try {
                String cod = (String) id;
                if (JOptionPane.showConfirmDialog(this, "Cancelar reserva " + cod + "?",
                        "Confirma", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    if (reservaDAO.delete(cod)) mostrarSucesso("Reserva cancelada.");
                    else mostrarErro("Erro ao cancelar reserva.");
                }
            } catch (Exception e) { mostrarErro("Erro: " + e.getMessage()); }
        });
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
