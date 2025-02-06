package resources.interface_card;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;

public class ClienteVenda extends JPanel {
    public ClienteVenda() {
        // inicialização de variáveis
        jLabelCPF = new JLabel();
        jLabelDataNascimento = new JLabel();
        jLabelNome = new JLabel();
        jLabelFormaPagamento = new JLabel();

        try{
            jTextFieldDataNascimento = new javax.swing.JFormattedTextField(new MaskFormatter("##/##/####"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        jPanelContent = new JPanel(new GridBagLayout());

        jComboBoxName = new JComboBox<String>();
        jComboBoxCPF = new JComboBox<String>();
        jComboBoxMetodoPagamento = new JComboBox<String>();

        gbc = new GridBagConstraints();

        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(1200, 300));
        setLayout(new FlowLayout(FlowLayout.CENTER,30,30));

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        Dimension fieldSize = new Dimension(400, 40);

        jPanelContent.setPreferredSize(new Dimension(1200, 450));
        jPanelContent.setBackground(Color.WHITE);
        jPanelContent.setBorder(BorderFactory.createEmptyBorder(-150, 10, 10, 10));

        jLabelNome.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelNome.setText("Nome:");
        jLabelNome.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 100);
        jPanelContent.add(jLabelNome, gbc);

        jComboBoxName.setBackground(new Color(255, 255, 255));
        jComboBoxName.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jComboBoxName.setFont(new Font("Cormorant Garamond", 1, 18));
        jComboBoxName.setPreferredSize(fieldSize);
        jComboBoxName.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 100);
        jPanelContent.add(jComboBoxName, gbc);

        jLabelDataNascimento.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelDataNascimento.setText("Data de Nascimento:");
        jLabelDataNascimento.setForeground(Color.BLACK);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        jPanelContent.add(jLabelDataNascimento, gbc);

        jTextFieldDataNascimento.setBackground(new Color(255, 255, 255));
        jTextFieldDataNascimento.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jTextFieldDataNascimento.setFont(new Font("Cormorant Infant", 1, 18));
        jTextFieldDataNascimento.setPreferredSize(fieldSize);
        jTextFieldDataNascimento.setForeground(Color.BLACK);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 0);
        jPanelContent.add(jTextFieldDataNascimento, gbc);

        jLabelCPF.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelCPF.setText("CPF:");
        jLabelCPF.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 0, 100);
        jPanelContent.add(jLabelCPF, gbc);

        jComboBoxCPF.setBackground(Color.WHITE);
        jComboBoxCPF.setFont(new Font("Cormorant Infant", Font.BOLD, 18));
        jComboBoxCPF.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jComboBoxCPF.setPreferredSize(fieldSize);
        jComboBoxCPF.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 20, 100);
        jPanelContent.add(jComboBoxCPF, gbc);

        jLabelFormaPagamento.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelFormaPagamento.setText("Forma de Pagamento:");
        jLabelFormaPagamento.setForeground(Color.BLACK);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 0, 100);
        jPanelContent.add(jLabelFormaPagamento, gbc);

        jComboBoxMetodoPagamento.setBackground(Color.WHITE);
        jComboBoxMetodoPagamento.setForeground(Color.BLACK);
        jComboBoxMetodoPagamento.setFont(new Font("Cormorant Garamond", Font.BOLD, 18));
        jComboBoxMetodoPagamento.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jComboBoxMetodoPagamento.setPreferredSize(fieldSize);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 20, 0);
        jPanelContent.add(jComboBoxMetodoPagamento, gbc);

        add(jPanelContent);
    }

    private JLabel jLabelCPF;
    private JLabel jLabelNome;
    private JLabel jLabelDataNascimento;
    private JLabel jLabelFormaPagamento;

    private JComboBox<String> jComboBoxName;
    private JComboBox<String> jComboBoxCPF;
    private JComboBox<String> jComboBoxMetodoPagamento;

    private JTextField jTextFieldDataNascimento;

    private JPanel jPanelContent;

    private GridBagConstraints gbc;
}
