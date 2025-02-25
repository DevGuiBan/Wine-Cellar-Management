package resources.interface_card;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Teste {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Teste::criarGUI);
    }

    private static void criarGUI() {
        JFrame frame = new JFrame("Filtro com JDialog");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setLayout(new FlowLayout());

        JButton btnFiltro = new JButton("Filtros");

        // Evento para abrir o JDialog ao clicar no botão
        btnFiltro.addActionListener(e -> abrirDialogoFiltro(frame));

        frame.add(btnFiltro);
        frame.setVisible(true);
    }

    private static void abrirDialogoFiltro(JFrame parent) {
        // Criando o JDialog como uma janela modal
        JDialog dialog = new JDialog(parent, "Filtros", true);
        dialog.setSize(250, 300);
        dialog.setLayout(new GridBagLayout());
        dialog.setLocationRelativeTo(parent);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Checkbox "Fornecedor"
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JCheckBox chkFornecedor = new JCheckBox("Fornecedor");
        dialog.add(chkFornecedor, gbc);

        // ComboBox Fornecedor
        gbc.gridy = 1;
        JComboBox<String> cbFornecedor = new JComboBox<>(new String[]{"Fornecedor A", "Fornecedor B", "Fornecedor C"});
        dialog.add(cbFornecedor, gbc);

        // Checkbox "Quant. em estoque"
        gbc.gridy = 2;
        JCheckBox chkEstoque = new JCheckBox("Quant. em estoque");
        dialog.add(chkEstoque, gbc);

        // Spinner para quantidade
        gbc.gridy = 3;
        JSpinner spnQuantidade = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
        dialog.add(spnQuantidade, gbc);

        // Checkbox "Com baixo estoque"
        gbc.gridy = 4;
        JCheckBox chkBaixoEstoque = new JCheckBox("Com baixo estoque");
        dialog.add(chkBaixoEstoque, gbc);

        // Checkbox "Tipo"
        gbc.gridy = 5;
        JCheckBox chkTipo = new JCheckBox("Tipo");
        dialog.add(chkTipo, gbc);

        // ComboBox Tipo
        gbc.gridy = 6;
        JComboBox<String> cbTipo = new JComboBox<>(new String[]{"Selecione", "Tipo 1", "Tipo 2", "Tipo 3"});
        dialog.add(cbTipo, gbc);

        // Botão Filtrar
        gbc.gridy = 7;
        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.setBackground(new Color(0, 0, 139)); // Azul escuro
        btnFiltrar.setForeground(Color.WHITE);
        dialog.add(btnFiltrar, gbc);

        // Evento do botão "Filtrar"
        btnFiltrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Fecha o diálogo ao filtrar
                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }
}
