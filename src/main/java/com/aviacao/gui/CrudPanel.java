package com.aviacao.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CrudPanel extends JPanel {
    protected final JTable tabela;
    protected final DefaultTableModel modelo;
    private final JTextField txtBusca = new JTextField(10);
    private final JButton btnCadastrar = new JButton("Cadastrar");
    private final JButton btnEditar = new JButton("Editar");
    private final JButton btnExcluir = new JButton("Excluir");
    private final JButton btnAtualizar = new JButton("Atualizar");
    private final JButton btnBuscar = new JButton("Buscar");

    private final Supplier<List<Object[]>> fornecedorDados;
    private final Runnable acaoCadastrar;
    private final Consumer<Object> acaoEditar;
    private final Consumer<Object> acaoExcluir;

    public CrudPanel(String[] colunas, Supplier<List<Object[]>> fornecedorDados,
                     Runnable acaoCadastrar, Consumer<Object> acaoEditar,
                     Consumer<Object> acaoExcluir) {
        this.fornecedorDados = fornecedorDados;
        this.acaoCadastrar = acaoCadastrar;
        this.acaoEditar = acaoEditar;
        this.acaoExcluir = acaoExcluir;

        setLayout(new BorderLayout(8, 8));
        modelo = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabela = new JTable(modelo);
        tabela.setFillsViewportHeight(true);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topo.add(new JLabel("Busca ID:"));
        topo.add(txtBusca);
        topo.add(btnBuscar);
        topo.add(btnAtualizar);
        topo.add(btnCadastrar);
        topo.add(btnEditar);
        topo.add(btnExcluir);

        add(topo, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        btnAtualizar.addActionListener(e -> carregarDados());
        btnCadastrar.addActionListener(e -> { acaoCadastrar.run(); carregarDados(); });
        btnBuscar.addActionListener(e -> buscarPorId());
        btnEditar.addActionListener(e -> editarSelecionado());
        btnExcluir.addActionListener(e -> excluirSelecionado());
    }

    public void carregarDados() {
        modelo.setRowCount(0);
        List<Object[]> dados = fornecedorDados.get();
        if (dados != null) {
            for (Object[] linha : dados) modelo.addRow(linha);
        }
    }

    private void buscarPorId() {
        String texto = txtBusca.getText().trim();
        if (texto.isEmpty()) { carregarDados(); return; }
        modelo.setRowCount(0);
        List<Object[]> dados = fornecedorDados.get();
        if (dados != null) {
            for (Object[] linha : dados) {
                if (linha.length > 0 && linha[0].toString().contains(texto))
                    modelo.addRow(linha);
            }
        }
    }

    public int getLinhaSelecionada() {
        int row = tabela.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um registro.");
            return -1;
        }
        return row;
    }

    public Object getValorCelula(int linha, int coluna) {
        return modelo.getValueAt(linha, coluna);
    }

    private void editarSelecionado() {
        int row = getLinhaSelecionada();
        if (row < 0) return;
        acaoEditar.accept(modelo.getValueAt(row, 0));
        carregarDados();
    }

    private void excluirSelecionado() {
        int row = getLinhaSelecionada();
        if (row < 0) return;
        int conf = JOptionPane.showConfirmDialog(this, "Excluir registro?",
                "Confirma", JOptionPane.YES_NO_OPTION);
        if (conf == JOptionPane.YES_OPTION) {
            acaoExcluir.accept(modelo.getValueAt(row, 0));
            carregarDados();
        }
    }
}
