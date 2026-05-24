package com.aviacao.gui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;
import java.util.Map;

public class FormDialog extends JDialog {
    private final Map<CampoFormulario, JComponent> campos = new LinkedHashMap<>();
    private boolean confirmou = false;

    public FormDialog(JFrame dono, String titulo, CampoFormulario... camposList) {
        super(dono, titulo, true);
        setLayout(new BorderLayout(10, 10));
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 8, 4, 8);

        int linha = 0;
        for (CampoFormulario cf : camposList) {
            gbc.gridx = 0; gbc.gridy = linha;
            gbc.weightx = 0;
            painel.add(new JLabel(cf.getRotulo() + ":"), gbc);

            gbc.gridx = 1;
            gbc.weightx = 1;
            JComponent comp;
            if (cf.getTipo() == CampoFormulario.Tipo.CHAR_SN) {
                JComboBox<String> cb = new JComboBox<>(new String[]{"S", "N"});
                comp = cb;
            } else {
                comp = new JTextField(20);
            }
            painel.add(comp, gbc);
            campos.put(cf, comp);
            linha++;
        }

        JPanel botoes = new JPanel();
        JButton btnOk = new JButton("OK");
        JButton btnCancel = new JButton("Cancelar");
        botoes.add(btnOk);
        botoes.add(btnCancel);

        btnOk.addActionListener(e -> {
            for (CampoFormulario cf : campos.keySet()) {
                JComponent c = campos.get(cf);
                String txt;
                if (c instanceof JTextField) {
                    txt = ((JTextField) c).getText().trim();
                } else {
                    txt = (String) ((JComboBox<?>) c).getSelectedItem();
                }
                cf.setValor(txt);

                if (cf.getValidador() != null && !cf.getValidador().test(txt)) {
                    JOptionPane.showMessageDialog(this, cf.getMensagemErro(), "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            confirmou = true;
            dispose();
        });
        btnCancel.addActionListener(e -> dispose());

        add(painel, BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(dono);
        setResizable(false);
    }

    public boolean foiConfirmado() { return confirmou; }

    public String getValor(CampoFormulario cf) { return cf.getValor(); }

    public LocalDate getValorData(CampoFormulario cf) {
        try {
            return LocalDate.parse(cf.getValor(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public int getValorInt(CampoFormulario cf) {
        try { return Integer.parseInt(cf.getValor()); }
        catch (NumberFormatException e) { return 0; }
    }

    public char getValorChar(CampoFormulario cf) {
        String s = cf.getValor();
        return s.isEmpty() ? 'S' : s.charAt(0);
    }
}
