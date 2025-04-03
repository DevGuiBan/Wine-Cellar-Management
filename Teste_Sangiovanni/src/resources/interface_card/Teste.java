package resources.interface_card;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class Teste extends JFrame {

    // Componentes da interface
    private JPanel painelPrincipal;
    private JTable tabelaVendas;
    private JTextField txtTotalVendas;
    private JTextField txtTotalReais;
    private JTextField txtMediaVendas;

    public Teste() {
        // Configurações básicas da janela
        setTitle("Relatório de Vendas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null); // Centraliza a janela

        // Inicializa o painel principal
        painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Inicializa os componentes no painel
        inicializarComponentes(painelPrincipal);

        // Adiciona o painel principal à janela
        add(painelPrincipal);
    }

    private void inicializarComponentes(JPanel painel) {
        JPanel painelTitulo = new JPanel();
        painelTitulo.setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel("RELATÓRIO DE FUNCIONÁRIOS - de 08/01/2025 até 08/02/2025");
        lblTitulo.setFont(new Font("Cormorant Infant Bold", Font.BOLD, 18));
        lblTitulo.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel lblInfo = new JLabel("Ordenado por maior número de vendas");
        lblInfo.setFont(new Font("Cormorant Garamond", Font.BOLD, 16));
        lblInfo.setHorizontalAlignment(SwingConstants.LEFT);

        painelTitulo.add(lblTitulo, BorderLayout.NORTH);
        painelTitulo.add(lblInfo, BorderLayout.SOUTH);

        painel.add(painelTitulo, BorderLayout.NORTH);

        String[] colunas = {"Produto", "Quantidade", "TOTAL (R$)"};
        Object[][] dados = {
                {"Anna Duarte", 500, "10.000,00"},
                {"João Mario", 490, "11.000,00"},
                {"Antony V.", 399, "5.000,00"}
        };

        DefaultTableModel modeloTabela = new DefaultTableModel(dados, colunas);
        tabelaVendas = new JTable(modeloTabela);
        tabelaVendas.setFillsViewportHeight(true);
        tabelaVendas.setEnabled(false);
        tabelaVendas.setShowHorizontalLines(false);
        tabelaVendas.setShowVerticalLines(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);


        tabelaVendas.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tabelaVendas.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tabelaVendas.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tabelaVendas.setFont(new java.awt.Font("Cormorant Infant", Font.BOLD, 14));
        tabelaVendas.setShowGrid(false);
        tabelaVendas.setIntercellSpacing(new Dimension(0, 0));
        tabelaVendas.setRowHeight(30);
        tabelaVendas.setFocusable(false);

        JTableHeader header = tabelaVendas.getTableHeader();
        header.setFont(new Font("Courier New", Font.BOLD, 30));
        header.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        header.setResizingAllowed(false);
        header.setReorderingAllowed(false);

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        headerRenderer.setBackground(new Color(217, 217, 217));
        headerRenderer.setForeground(Color.BLACK);
        for (int i = 0; i < tabelaVendas.getColumnModel().getColumnCount(); i++) {
            tabelaVendas.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }


        JScrollPane scrollPane = new JScrollPane(tabelaVendas);
        painel.add(scrollPane, BorderLayout.CENTER);


        JPanel painelInferior = new JPanel(new GridLayout(1, 3, 10, 10));

        JPanel painelTotalVendas = new JPanel(new BorderLayout());
        JLabel lbl = new JLabel("Total de Vendas Realizadas:");
        lbl.setFont(new Font("Cormorant Garamond Bold",Font.BOLD,16));
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        painelTotalVendas.add(lbl, BorderLayout.NORTH);
        txtTotalVendas = new JTextField("1.389");
        txtTotalVendas.setFont(new Font("Cormorant Infant",Font.BOLD,18));
        txtTotalVendas.setEditable(false);
        txtTotalVendas.setBorder(new LineBorder(Color.DARK_GRAY,1));
        txtTotalVendas.setFocusable(false);
        txtTotalVendas.setPreferredSize(new Dimension(250,100));
        txtTotalVendas.setBackground(Color.WHITE);
        txtTotalVendas.setHorizontalAlignment(JTextField.CENTER);
        painelTotalVendas.add(txtTotalVendas, BorderLayout.CENTER);
        painelInferior.add(painelTotalVendas);

        JPanel painelTotalReais = new JPanel(new BorderLayout());
        JLabel lbl2 = new JLabel("Total de Vendas em Reais:");
        lbl2.setFont(new Font("Cormorant Garamond Bold",Font.BOLD,16));
        lbl2.setHorizontalAlignment(SwingConstants.CENTER);
        painelTotalReais.add(lbl2, BorderLayout.NORTH);
        txtTotalReais = new JTextField("R$ 26.000,00");
        txtTotalReais.setFont(new Font("Cormorant Infant",Font.BOLD,18));
        txtTotalReais.setEditable(false);
        txtTotalReais.setBorder(new LineBorder(Color.DARK_GRAY,1));
        txtTotalReais.setFocusable(false);
        txtTotalReais.setPreferredSize(new Dimension(250,100));
        txtTotalReais.setBackground(Color.WHITE);
        txtTotalReais.setHorizontalAlignment(JTextField.CENTER);
        painelTotalReais.add(txtTotalReais, BorderLayout.CENTER);
        painelInferior.add(painelTotalReais);

        JPanel painelMediaVendas = new JPanel(new BorderLayout());
        JLabel lbl3 = new JLabel("Média de Vendas:");
        lbl3.setFont(new Font("Cormorant Garamond Bold",Font.BOLD,16));
        lbl3.setHorizontalAlignment(SwingConstants.CENTER);
        painelMediaVendas.add(lbl3, BorderLayout.NORTH);
        txtMediaVendas = new JTextField("46,6");
        txtMediaVendas.setFont(new Font("Cormorant Infant",Font.BOLD,18));
        txtMediaVendas.setBorder(new LineBorder(Color.DARK_GRAY,1));
        txtMediaVendas.setEditable(false);
        txtMediaVendas.setFocusable(false);
        txtMediaVendas.setPreferredSize(new Dimension(250,100));
        txtMediaVendas.setBackground(Color.WHITE);
        txtMediaVendas.setHorizontalAlignment(JTextField.CENTER);
        painelMediaVendas.add(txtMediaVendas, BorderLayout.CENTER);
        painelInferior.add(painelMediaVendas);

        painel.add(painelInferior, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        // Executa a interface na thread de despacho de eventos
        SwingUtilities.invokeLater(() -> {
            Teste frame = new Teste();
            frame.setVisible(true);
        });
    }
}