package resources.interface_card;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;


public class ProdutoVenda extends JPanel {
    public ProdutoVenda() {
        // inicialização de variáveis
        jLabelValorUnitario = new JLabel();
        jLabelQuantidade = new JLabel();
        jLabelNome = new JLabel();

        jPanelContent = new JPanel(new GridBagLayout());

        jSpinnerQuantidade = new JSpinner((new SpinnerNumberModel(0.0, 0.0, Integer.MAX_VALUE, 1)));
        jSpinnerValorUnitario = new JSpinner((new SpinnerNumberModel(0.0, 0.0, Long.MAX_VALUE, 0.1)));

        jComboBoxProduct = new JComboBox<Product>();

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
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 100);
        jPanelContent.add(jLabelNome, gbc);

        jComboBoxProduct.setBackground(new Color(255, 255, 255));
        jComboBoxProduct.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jComboBoxProduct.setFont(new Font("Cormorant Garamond", 1, 18));
        jComboBoxProduct.setPreferredSize(fieldSize);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 100);
        jPanelContent.add(jComboBoxProduct, gbc);

        jLabelQuantidade.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelQuantidade.setText("Quantidade:");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        jPanelContent.add(jLabelQuantidade, gbc);

        jSpinnerQuantidade.setBackground(new Color(255, 255, 255));
        jSpinnerQuantidade.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jSpinnerQuantidade.setFont(new Font("Cormorant Garamond", 1, 18));
        jSpinnerQuantidade.setPreferredSize(fieldSize);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 0);
        jPanelContent.add(jSpinnerQuantidade, gbc);

        jLabelValorUnitario.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelValorUnitario.setText("Valor Unitário:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 0, 100);
        jPanelContent.add(jLabelValorUnitario, gbc);

        jSpinnerValorUnitario.setBackground(new Color(255, 255, 255));
        jSpinnerValorUnitario.setBackground(Color.WHITE);
        jSpinnerValorUnitario.setFont(new Font("Cormorant Garamond", Font.BOLD, 18));
        jSpinnerValorUnitario.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jSpinnerValorUnitario.setPreferredSize(fieldSize);
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(jSpinnerValorUnitario, "R$ #,##0.00"); // máscara de preço pro spinner
        jSpinnerValorUnitario.setEditor(editor);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 20, 100);
        jPanelContent.add(jSpinnerValorUnitario, gbc);

        add(jPanelContent);
    }

    private JLabel jLabelQuantidade;
    private JLabel jLabelNome;
    private JLabel jLabelValorUnitario;

    private JPanel jPanelContent;

    private JSpinner jSpinnerValorUnitario;
    private JSpinner jSpinnerQuantidade;

    private JComboBox<Product> jComboBoxProduct;

    private GridBagConstraints gbc;
}
