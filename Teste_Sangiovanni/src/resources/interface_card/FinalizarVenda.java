package resources.interface_card;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.cdimascio.dotenv.Dotenv;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class FinalizarVenda extends JPanel {
    private Client client;
    private ArrayList<Product> products = new ArrayList<>();
    private JFrame rootPane;
    private Dotenv dotenv;
    private JanelaPrincipal frame;

    public void setClient(Client client) {
        this.client = client;
        loadClient();
    }

    public double getValorTotal() {
        return Double.parseDouble(this.jLabelValorTotal.getText().replace("R$", "").trim());
    }

    public void makeSale() {
        try {
            JsonObject jsonData = new JsonObject();
            jsonData.addProperty("clientId", this.client.getId());

            String discount = this.jLabelDescontoTabela.getText().replace("-R$", "").trim().replace(",", ".");
            jsonData.addProperty("discount", Double.parseDouble(discount));

            String totalValue = this.jLabelValorTotal.getText().replace("R$", "").trim().replace(",", ".");
            jsonData.addProperty("totalPrice", Double.parseDouble(totalValue));

            jsonData.addProperty("paymentMethod", client.getPayment());

            LocalDate hojeData = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            jsonData.addProperty("saleDate", hojeData.format(formatter));

            JsonArray jsonProducts = new JsonArray();
            for (Product product : products) {
                JsonObject jsonProduct = new JsonObject();
                jsonProduct.addProperty("productId", product.getId());
                jsonProduct.addProperty("quantity", product.getQuantity());
                jsonProducts.add(jsonProduct);
            }
            jsonData.add("products", jsonProducts);

            String urlAPI = this.dotenv.get("API_HOST");
            URL url = new URL(urlAPI + "/sale");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Enviar a requisição com JSON
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonData.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int statusCode = connection.getResponseCode();
            StringBuilder response = new StringBuilder();

            if (statusCode >= 200 && statusCode < 300) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                }

                JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
                String saleId = jsonResponse.has("id") ? jsonResponse.get("id").getAsString() : null;

                if (saleId != null) {
                    // Chamar API para gerar cupom fiscal
                    if (generateTaxReceipt(urlAPI, saleId)) {
                        JOptionPane.showMessageDialog(this.rootPane,
                                "A venda foi realizada com sucesso!\nID da Venda: " + saleId + "\nCupom fiscal gerado!",
                                "Venda Realizada",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this.rootPane,
                                "Venda realizada, mas houve um erro ao gerar o cupom fiscal.",
                                "Aviso",
                                JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this.rootPane,
                            "Venda realizada, mas não foi possível obter o ID da venda.",
                            "Aviso",
                            JOptionPane.WARNING_MESSAGE);
                }

                frame.showCard("listar_vendas");

            } else {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8))) {
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                }

                JsonObject err = JsonParser.parseString(response.toString()).getAsJsonObject();
                String errorMessage = err.has("message") && !err.get("message").isJsonNull()
                        ? err.get("message").getAsString()
                        : response.toString();

                JOptionPane.showMessageDialog(this.rootPane,
                        "Não foi possível finalizar a venda. Verifique os dados e tente novamente!\n" + errorMessage,
                        "Erro ao Finalizar a Venda",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.rootPane, "Erro inesperado: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Gera o cupom fiscal para uma venda específica.
     *
     * @param urlAPI  URL base da API
     * @param saleId  ID da venda
     * @return true se o cupom fiscal for gerado com sucesso, false caso contrário
     */
    private boolean generateTaxReceipt(String urlAPI, String saleId) {
        try {
            URL urlTax = new URL(urlAPI + "/sale/tax-receipt/" + saleId);
            HttpURLConnection connectionTax = (HttpURLConnection) urlTax.openConnection();
            connectionTax.setRequestMethod("POST");
            connectionTax.setRequestProperty("Content-Type", "application/json");
            connectionTax.setDoOutput(true);

            int statusCodeTax = connectionTax.getResponseCode();

            return statusCodeTax >= 200 && statusCodeTax < 300;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.rootPane, "Erro ao gerar o cupom fiscal: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }


    public void setProducts(ArrayList<Product> prods) {
        this.products = prods;
        jTable.getColumn("Remover").setCellRenderer(new ButtonRendererEndSale());
        jTable.getColumn("Remover").setCellEditor(new ButtonEditorEndSale(jTable, this.rootPane, this.products, this));
        loadProducts();
    }

    public FinalizarVenda(JFrame frame, JanelaPrincipal RootFrame) {
        this.rootPane = frame;
        this.frame = RootFrame;
        this.dotenv = Dotenv.load();
        initComponents();
    }

    private void loadClient() {
        jLabelIconCliente.setText(client.getName());
        jLabelPagamento.setText(client.getPayment());
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate aniversario = LocalDate.parse(client.getData().replace("/", "-"), inputFormatter);

        MonthDay hoje = MonthDay.from(LocalDate.now());
        MonthDay aniversarioMd = MonthDay.from(aniversario);

        if (hoje.equals(aniversarioMd)) {
            jLabelDesconto.setText("Aplicado!");
        } else {
            jLabelDesconto.setText("Não Aplicado");
        }

        LocalDate hojeData = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String dataFormatada = hojeData.format(formatter);
        jTextFieldData.setText(dataFormatada);

    }

    public void setDicount(double valorTotal, double desconto) {
        jLabelValorTotal.setText("R$ " + valorTotal);
        jLabelDescontoTabela.setText("-R$ " + desconto);
    }

    public void loadProducts() {
        DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
        tableModel.setRowCount(0);
        double valorTotal = 0;

        for (int i = 0; i < products.size(); i++) {
            Product prod = products.get(i);
            String id = String.valueOf(prod.getId());
            String name = prod.getProductName();
            String qtdXvalor = prod.getQuantity() + "x" + prod.getValorUnitario();
            double subtotal = prod.getQuantity() * prod.getValorUnitario();
            valorTotal += subtotal;
            tableModel.addRow(new Object[]{id, name, qtdXvalor, subtotal});
        }
        jLabelValorTotal.setText("R$ " + valorTotal);

        if (jLabelDesconto.getText().equals("Aplicado!")) {
            double desconto = valorTotal * 0.10;
            jLabelDescontoTabela.setText("-R$ " + desconto);
        } else {
            jLabelDescontoTabela.setText("-R$ 00,00");
        }
    }

    private void initComponents() {
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

        try {
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

        jPanelTabelaContentLeft.add(jLabelProdutos, BorderLayout.WEST);

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
        jTable.setPreferredSize(new Dimension(600, 150));

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

        jTable.getColumn("Remover").setCellRenderer(new ButtonRendererEndSale());
        jTable.getColumn("Remover").setCellEditor(new ButtonEditorEndSale(jTable, this.rootPane, this.products, this));

        jScrollPaneTable.setViewportView(jTable);
        jScrollPaneTable.setBackground(Color.WHITE);
        jScrollPaneTable.setPreferredSize(new Dimension(650, 150));
        jScrollPaneTable.setBorder(null);

        jPanelTabelaContentRight.add(jScrollPaneTable);

        jLabelDescontoTabela.setText("-Desconto");
        jLabelDescontoTabela.setForeground(Color.RED);
        jLabelDescontoTabela.setFont(new Font("Cormorant Infant Bold", Font.BOLD, 20));
        jLabelDescontoTabela.setBorder(new EmptyBorder(0, 0, 0, 400));

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
        jTextFieldData.setBackground(new Color(217, 217, 217));
        jTextFieldData.setPreferredSize(new Dimension(40, 25));
        jTextFieldData.setOpaque(true);
        jTextFieldData.setBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY));
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

class ButtonRendererEndSale extends JPanel implements TableCellRenderer {
    private final JButton deleteButton = new JButton();

    public ButtonRendererEndSale() {
        setOpaque(true);
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));

        this.deleteButton.setIcon(new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/images/excluir.png"))));
        this.deleteButton.setBackground(Color.WHITE);
        this.deleteButton.setBorder(null);
        this.deleteButton.setContentAreaFilled(false);
        this.deleteButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        add(deleteButton);

        deleteButton.setFocusable(false);
    }


    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(128, 0, 32));
        return this;
    }
}

class ButtonEditorEndSale extends AbstractCellEditor implements TableCellEditor {
    private final JPanel panel = new JPanel();
    private final JButton editButton = new JButton();
    private final JButton deleteButton = new JButton();
    private final JTable table;
    private int currentRow;
    private final JFrame frame;

    public ButtonEditorEndSale(JTable table, JFrame rootPane, ArrayList<Product> products, FinalizarVenda mainPanel) {
        this.table = table;
        this.frame = rootPane;

        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));

        this.deleteButton.setIcon(new javax.swing.ImageIcon(Objects.requireNonNull(
                getClass().getResource("/resources/images/excluir.png"))));
        this.deleteButton.setBackground(new java.awt.Color(255, 255, 255));
        this.deleteButton.setForeground(new java.awt.Color(128, 0, 32));
        this.deleteButton.setFocusPainted(false);
        this.deleteButton.setBorder(null);
        this.deleteButton.setContentAreaFilled(false);
        this.deleteButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        panel.add(deleteButton);


        // Action for delete button
        deleteButton.addActionListener(e -> {
            String[] options = {"Cancelar", "Excluir"};
            int confirmation = JOptionPane.showOptionDialog(table,
                    "Deseja excluir o produto? Essa ação não poderá ser desfeita. ",
                    "Deletar Produto",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
            if (confirmation == 1) {
                try {
                    int cellIndex = table.getSelectedRow();
                    if (table.isEditing()) {
                        table.getCellEditor().stopCellEditing();
                    }
                    ((DefaultTableModel) table.getModel()).removeRow(currentRow);
                    products.remove(cellIndex);
                    mainPanel.loadProducts();


                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this.frame, "Ocorreu um erro ao remover o Produto " + ex.getMessage());
                }
            }

        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        currentRow = row;
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }
}