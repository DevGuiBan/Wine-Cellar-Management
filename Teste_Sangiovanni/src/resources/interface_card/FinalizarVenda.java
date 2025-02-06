package resources.interface_card;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.util.Objects;

public class FinalizarVenda extends JPanel {

    public FinalizarVenda() {
        // iniciando variáveis
        jLabelCliente = new JLabel();
        jLabelData = new JLabel();
        jLabelDescontoAniversario = new JLabel();
        jLabelFormaPagamento = new JLabel();
        jLabelPagamento = new JLabel();
        jLabelTotal = new JLabel();
        jLabelDesconto = new JLabel();
        jLabelProdutos = new JLabel();
        jLabelIconCliente = new JLabel();
        jLabelValorTotal = new JLabel();
        jLabelDescontoTabela = new JLabel();

        jPanelTabela = new JPanel();
        jPanelInformacoes = new JPanel();
        jPanelPrincipal = new JPanel();
        jPanelTopTabela = new JPanel();
        jPanelTabelaContent = new JPanel();
        jPanelTabelaContentRight = new JPanel();
        jPanelTabelaContentLeft = new JPanel();

        try{
            jTextFieldData = new JFormattedTextField(new MaskFormatter("##/##/####"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        gbc = new GridBagConstraints();

        jTable = new JTable();
        jScrollPaneTable = new JScrollPane();

        // configurações cardPrincipal
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(1200, 450));
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));

        jPanelPrincipal.setBackground(Color.WHITE);
        jPanelPrincipal.setPreferredSize(new Dimension(1200, 400));
        jPanelPrincipal.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));

        jPanelTabela.setBackground(Color.WHITE);
        jPanelTabela.setPreferredSize(new Dimension(1030, 280));
        jPanelTabela.setBorder(new MatteBorder(1, 1, 0, 1, Color.GRAY));
        jPanelTabela.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));

        jPanelTopTabela.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 10));
        jPanelTopTabela.setBackground(Color.WHITE);

        jLabelCliente.setText("Cliente: ");
        jLabelCliente.setForeground(Color.BLACK);
        jLabelCliente.setFont(new Font("Cormorant Garamond", 0, 24));

        jPanelTopTabela.add(jLabelCliente);

        jLabelIconCliente.setText("Nome Completo");
        jLabelIconCliente.setForeground(Color.BLACK);
        jLabelIconCliente.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelIconCliente.setIcon(new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/images/icon_cliente.png"))));
        jLabelIconCliente.setBackground(new Color(200, 200, 200));
        jLabelIconCliente.setPreferredSize(new Dimension(650, 40));
        jLabelIconCliente.setOpaque(true);
        jLabelIconCliente.setIconTextGap(250);

        jPanelTopTabela.add(jLabelIconCliente);

        jPanelTabela.add(jPanelTopTabela);

        jPanelTabelaContent.setBackground(Color.WHITE);
        jPanelTabelaContent.setPreferredSize(new Dimension(900, 220));
        jPanelTabelaContent.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));

        jPanelTabelaContentLeft.setBackground(Color.WHITE);
        jPanelTabelaContentLeft.setPreferredSize(new Dimension(150, 200));

        jLabelProdutos.setText("Produtos:");
        jLabelProdutos.setForeground(Color.BLACK);
        jLabelProdutos.setFont(new Font("Cormorant Garamond", 0, 24));

        jPanelTabelaContentLeft.add(jLabelProdutos,BorderLayout.WEST);

        jPanelTabelaContentRight.setPreferredSize(new Dimension(700, 200));
        jPanelTabelaContentRight.setBackground(Color.WHITE);
        jPanelTabelaContentRight.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));

        jTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                        "Código", "Nome", "Qtde. X Unitário", "Subtotal", "Remover"
                }
        ));

        jTable.setIntercellSpacing(new Dimension(0, 0));
        jTable.setShowHorizontalLines(true);
        jTable.setShowVerticalLines(false);
        jTable.setGridColor(Color.BLACK); // Cor da linha horizontal
        jTable.setBackground(Color.WHITE);
        jTable.setRowHeight(30);
        jTable.setFocusable(false);
        jTable.setPreferredSize(new Dimension(600,150));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        jTable.setFont(new Font("Cormorant Infant", Font.BOLD, 16));

        JTableHeader header = jTable.getTableHeader();
        header.setFont(new Font("Cormorant Garamond", Font.BOLD, 16));
        header.setBackground(Color.WHITE);
        header.setForeground(Color.BLACK);
        header.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));

        jTable.setBackground(Color.WHITE);
        jTable.setSelectionBackground(new Color(240, 240, 240));
        jTable.setSelectionForeground(Color.BLACK);

        for (int i = 0; i < jTable.getColumnCount(); i++) {
            if (!jTable.getColumnName(i).equals("Remover")) {
                jTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }

        jScrollPaneTable.setViewportView(jTable);
        jScrollPaneTable.setBackground(Color.WHITE);
        jScrollPaneTable.setPreferredSize(new Dimension(650, 150));
        jScrollPaneTable.setBorder(null);

        jPanelTabelaContentRight.add(jScrollPaneTable);

        jLabelDescontoTabela.setText("-Desconto");
        jLabelDescontoTabela.setForeground(Color.RED);
        jLabelDescontoTabela.setFont(new Font("Cormorant Infant Bold", Font.BOLD, 20));
        jLabelDescontoTabela.setBorder(new EmptyBorder(0,0,0,400));

        jPanelTabelaContentRight.add(jLabelDescontoTabela);

        jLabelTotal.setText("Total: ");
        jLabelTotal.setForeground(Color.BLACK);
        jLabelTotal.setFont(new Font("Cormorant Garamond", Font.PLAIN, 24));

        jLabelValorTotal.setText("R$ 00,00");
        jLabelValorTotal.setForeground(Color.BLACK);
        jLabelValorTotal.setFont(new Font("Cormorant Infant Bold", Font.BOLD, 24));

        jPanelTabelaContentRight.add(jLabelTotal);
        jPanelTabelaContentRight.add(jLabelValorTotal);

        jPanelTabelaContent.add(jPanelTabelaContentLeft, BorderLayout.WEST);
        jPanelTabelaContent.add(jPanelTabelaContentRight, BorderLayout.EAST);
        jPanelTabela.add(jPanelTabelaContent);

        jPanelInformacoes.setBackground(Color.WHITE);
        jPanelInformacoes.setPreferredSize(new Dimension(1030, 80));
        jPanelInformacoes.setBorder(new MatteBorder(0, 1, 1, 1, Color.GRAY));
        jPanelInformacoes.setLayout(new GridBagLayout());
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.EAST;

        jLabelFormaPagamento.setText("Forma de Pagamento: ");
        jLabelFormaPagamento.setForeground(Color.BLACK);
        jLabelFormaPagamento.setFont(new Font("Cormorant Garamond", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 0);
        jPanelInformacoes.add(jLabelFormaPagamento, gbc);

        jLabelPagamento.setText("Forma de Pagamento");
        jLabelPagamento.setForeground(Color.BLACK);
        jLabelPagamento.setFont(new Font("Cormorant Infant Bold", Font.BOLD, 20));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 40);
        jPanelInformacoes.add(jLabelPagamento, gbc);

        jLabelDescontoAniversario.setText("Desconto de Aniversário: ");
        jLabelDescontoAniversario.setForeground(Color.BLACK);
        jLabelDescontoAniversario.setFont(new Font("Cormorant Garamond", Font.PLAIN, 20));
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 0);
        jPanelInformacoes.add(jLabelDescontoAniversario, gbc);

        jLabelDesconto.setText("Não aplicado");
        jLabelDesconto.setForeground(Color.BLACK);
        jLabelDesconto.setFont(new Font("Cormorant Infant Bold", Font.BOLD, 20));
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 0);
        jPanelInformacoes.add(jLabelDesconto, gbc);

        jLabelData.setText("Data:");
        jLabelData.setForeground(Color.BLACK);
        jLabelData.setFont(new Font("Cormorant Garamond", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 0, 0);
        jPanelInformacoes.add(jLabelData, gbc);

        jTextFieldData.setForeground(Color.BLACK);
        jTextFieldData.setFont(new Font("Cormorant Infant Bold", Font.BOLD, 20));
        jTextFieldData.setBackground(new Color(217,217,217));
        jTextFieldData.setPreferredSize(new Dimension(40,25));
        jTextFieldData.setOpaque(true);
        jTextFieldData.setBorder(new MatteBorder(1,1,1,1,Color.GRAY));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, -100, 0, 230);
        jPanelInformacoes.add(jTextFieldData, gbc);

        jPanelPrincipal.add(jPanelTabela, BorderLayout.NORTH);
        jPanelPrincipal.add(jPanelInformacoes, BorderLayout.SOUTH);

        add(jPanelPrincipal);
    }

    private JLabel jLabelCliente;
    private JLabel jLabelProdutos;
    private JLabel jLabelFormaPagamento;
    private JLabel jLabelDescontoAniversario;
    private JLabel jLabelData;
    private JLabel jLabelPagamento;
    private JLabel jLabelDesconto;
    private JLabel jLabelTotal;
    private JLabel jLabelIconCliente;
    private JLabel jLabelValorTotal;
    private JLabel jLabelDescontoTabela;

    private JButton jButtonExcluirProduto;

    private JTextField jTextFieldData;

    private JPanel jPanelPrincipal;
    private JPanel jPanelTabela;
    private JPanel jPanelInformacoes;
    private JPanel jPanelTopTabela;
    private JPanel jPanelTabelaContent;
    private JPanel jPanelTabelaContentRight;
    private JPanel jPanelTabelaContentLeft;

    private GridBagConstraints gbc;

    private JScrollPane jScrollPaneTable;
    private JTable jTable;
}
