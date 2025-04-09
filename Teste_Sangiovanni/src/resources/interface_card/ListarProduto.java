package resources.interface_card;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Objects;

import com.google.gson.JsonObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import io.github.cdimascio.dotenv.Dotenv;

public class ListarProduto extends JPanel {
    private final Dotenv dotenv;
    private final JRootPane rootPane;
    private final JPanel mainPanel;
    private final CadastrarProduto cadastrarProduto;
    private javax.swing.JButton jButtonAtualizarEstoque;


    public ListarProduto(JRootPane rootPane, JPanel mainPanel, CadastrarProduto cardProduto) {
        this.dotenv = Dotenv.load();
        this.rootPane = rootPane;
        this.mainPanel = mainPanel;
        this.cadastrarProduto = cardProduto;

        initComponents();
        getProduct();
    }

    private void initComponents() {

        // Declarando os componentes
        jPanelTopoTabela = new javax.swing.JPanel();
        jPanelTabela = new javax.swing.JPanel();
        jPanelButtonsStock = new javax.swing.JPanel();
        jButtonCadastrar = new javax.swing.JButton();
        jButtonFiltrar = new javax.swing.JButton();
        jButtonAtualizarEstoque = new javax.swing.JButton();
        pesquisaProduto = new javax.swing.JTextField();
        jtable = new javax.swing.JTable();
        jScrollPane = new javax.swing.JScrollPane();
        jPanel4 = new JPanel();

        setBackground(new java.awt.Color(243, 243, 223));
        setPreferredSize(new Dimension(1366, 650));
        add(jPanel4, "painel_principal");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new Dimension(1200, 600));

        // Configuração do painel superior
        jPanelTopoTabela.setPreferredSize(new Dimension(1200, 100));
        jPanelTopoTabela.setBackground(new java.awt.Color(255, 255, 255));
        jPanelTopoTabela.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 30));

        pesquisaProduto.setBackground(new java.awt.Color(240, 240, 240));
        pesquisaProduto.setFont(new Font("Cormorant Garamond", Font.BOLD, 14));
        pesquisaProduto.setText("Pesquisar produto");
        pesquisaProduto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pesquisaProduto.setBorder(null);
        pesquisaProduto.setToolTipText("");
        pesquisaProduto.setPreferredSize(new java.awt.Dimension(200, 40));
        pesquisaProduto.addFocusListener(new java.awt.event.FocusListener() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (pesquisaProduto.getText().equals("Pesquisar produto")) {
                    pesquisaProduto.setText("");
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (pesquisaProduto.getText().isEmpty()) {
                    pesquisaProduto.setText("Pesquisar produto");
                }
            }
        });

        timer.setRepeats(false);
        pesquisaProduto.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                timer.restart();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                timer.restart();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                timer.restart();
            }
        });

        jPanelTopoTabela.add(pesquisaProduto);

        jPanelTopoTabela.add(Box.createHorizontalStrut(300));

        jButtonAtualizarEstoque.setFont(new java.awt.Font("Cormorant Garamond", Font.BOLD, 20));
        jButtonAtualizarEstoque.setIcon(new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/images/bulk_update.png"))));
        jButtonAtualizarEstoque.setText("Atualizar Estoque");
        jButtonAtualizarEstoque.setBorder(null);
        jButtonAtualizarEstoque.setContentAreaFilled(false);
        jButtonAtualizarEstoque.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonAtualizarEstoque.setPreferredSize(new java.awt.Dimension(200, 40));

        jPanelTopoTabela.add(jButtonAtualizarEstoque);

        jButtonCadastrar.setFont(new java.awt.Font("Cormorant Garamond", Font.BOLD, 20));
        jButtonCadastrar.setIcon(new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/images/criar.png"))));
        jButtonCadastrar.setText("Cadastrar Produto");
        jButtonCadastrar.setBorder(null);
        jButtonCadastrar.setContentAreaFilled(false);
        jButtonCadastrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonCadastrar.setPreferredSize(new java.awt.Dimension(200, 40));
        jButtonCadastrar.addActionListener(e -> {
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            cadastrarProduto.atualizarDados();
            cl.show(mainPanel, "cadastrar_produto");
        });

        jPanelTopoTabela.add(jButtonCadastrar);

        jButtonFiltrar.setFont(new java.awt.Font("Cormorant Garamond", Font.BOLD, 20));
        jButtonFiltrar.setForeground(new java.awt.Color(6, 6, 10));
        jButtonFiltrar.setIcon(new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/images/filter.png"))));
        jButtonFiltrar.setText("Filtrar");
        jButtonFiltrar.setBorder(null);
        jButtonFiltrar.setContentAreaFilled(false);
        jButtonFiltrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonFiltrar.setPreferredSize(new java.awt.Dimension(90, 40));
        jButtonFiltrar.addActionListener(evt -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(rootPane);
            abrirDialogoFiltro(frame);
        });

        jPanelTopoTabela.add(jButtonFiltrar);

        jPanelTabela.setPreferredSize(new Dimension(1145, 430));
        jPanelTabela.setBackground(new java.awt.Color(255, 255, 255));
        jPanelTabela.setLayout(new BorderLayout());

        jtable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                        "Código", "Nome", "Fornecedor", "Tipo de Produto", "Preço Unitário", "Qtd. em Estoque", "Ações"
                }
        ));

        jtable.setIntercellSpacing(new Dimension(0, 0));
        jtable.setShowHorizontalLines(false);
        jtable.setShowVerticalLines(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        jtable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

        jtable.setFont(new java.awt.Font("Cormorant Infant", Font.BOLD, 14));
        jtable.setShowGrid(false);
        jtable.setIntercellSpacing(new Dimension(0, 0));
        jtable.setRowHeight(30);
        jtable.setFocusable(false);

        JTableHeader header = jtable.getTableHeader();
        header.setFont(new java.awt.Font("Cormorant Garamond", Font.BOLD, 18));
        header.setBackground(Color.WHITE);
        header.setForeground(new java.awt.Color(128, 0, 32));
        header.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(128, 0, 32)));

        jtable.setBackground(new java.awt.Color(255, 255, 255));
        jtable.setSelectionBackground(new java.awt.Color(228, 236, 242));
        jtable.setSelectionForeground(new java.awt.Color(0, 0, 0));

        for (int i = 0; i < jtable.getColumnCount(); i++) {
            if (!jtable.getColumnName(i).equals("Ações")) {
                jtable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }

        jtable.getColumn("Ações").setCellRenderer(new ButtonRendererProduct_());
        jtable.getColumn("Ações").setCellEditor(new ButtonEditorProduct_(jtable, rootPane, this.dotenv.get("API_HOST"), cadastrarProduto, mainPanel));

        jScrollPane.setViewportView(jtable);
        jScrollPane.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(128, 0, 32)));
        jScrollPane.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(128, 0, 32)));

        jPanelTabela.add(jScrollPane, BorderLayout.CENTER);

        jPanelButtonsStock.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        jPanelButtonsStock.setPreferredSize(new Dimension(1145, 50));
        jPanelButtonsStock.setBackground(Color.WHITE);

        JButton jButtonProx = new javax.swing.JButton("Próximo");
        JButton jButtonCancel = new javax.swing.JButton("Cancelar");

        jButtonProx.setFont(new Font("Cormorant Garamond", Font.BOLD, 18));
        jButtonProx.setPreferredSize(new Dimension(120, 30));
        jButtonProx.setBackground((new Color(0, 128, 17)));
        jButtonProx.setForeground(Color.WHITE);
        jButtonProx.setFocusPainted(false);
        jButtonProx.setBorder(null);
        jButtonProx.addActionListener(evt -> {
            DefaultTableModel model = (DefaultTableModel) jtable.getModel();
            ArrayList<BigInteger> codigosSelecionados = new ArrayList<>();

            for (int i = 0; i < model.getRowCount(); i++) {
                Boolean isSelected = (Boolean) model.getValueAt(i, 6);
                if (isSelected != null && isSelected) {
                    BigInteger codigo = (BigInteger) model.getValueAt(i, 0);
                    codigosSelecionados.add(codigo);
                }
            }

            abrirModal(codigosSelecionados);
        });
        jButtonProx.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jButtonProx.setVisible(false);

        jButtonCancel.setFont(new Font("Cormorant Garamond", Font.BOLD, 18));
        jButtonCancel.setPreferredSize(new Dimension(120, 30));
        jButtonCancel.setBackground(new Color(225, 225, 200));
        jButtonCancel.setFocusPainted(false);
        jButtonCancel.setBorder(null);
        jButtonCancel.setVisible(false);
        jButtonCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jButtonCancel.addActionListener(e -> {
            restaurarTabelaOriginal();
            jButtonProx.setVisible(false);
            jButtonCancel.setVisible(false);
        });

        jButtonAtualizarEstoque.addActionListener(e -> {
            atualizarTabelaParaSelecao();
            jButtonProx.setVisible(true);
            jButtonCancel.setVisible(true);
        });

        jPanelButtonsStock.add(jButtonCancel);
        jPanelButtonsStock.add(Box.createHorizontalStrut(865));
        jPanelButtonsStock.add(jButtonProx);

        jPanel4.add(jPanelTopoTabela);
        jPanel4.add(jPanelTabela);
        jPanel4.add(jPanelButtonsStock);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        getProduct();
    }

    private void abrirModal(ArrayList<BigInteger> ids) {
        JDialog modal = new JDialog();
        modal.setFont(new java.awt.Font("Cormorant Garamond", Font.BOLD, 20));
        modal.setTitle("Atualizar estoque");
        modal.setSize(450, 230);
        modal.setBackground(Color.WHITE);
        modal.setLayout(new GridLayout(3, 1));
        modal.setModal(true);

        JLabel lblInfo = new JLabel("Informe a nova quantidade em estoque para os produtos selecionados.", SwingConstants.CENTER);
        lblInfo.setFont(new java.awt.Font("Cormorant Garamond", Font.BOLD, 14));
        lblInfo.setForeground(Color.BLACK);
        modal.add(lblInfo);

        JSpinner spinnerQuantidade = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
        spinnerQuantidade.setFont(new java.awt.Font("Cormorant Infant", Font.BOLD, 14));
        spinnerQuantidade.setPreferredSize(new Dimension(150, 30));
        spinnerQuantidade.setBorder(new LineBorder(new Color(128, 0, 32), 1));
        modal.add(spinnerQuantidade);

        JPanel panelBotoes = new JPanel();
        panelBotoes.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelBotoes.setBackground(Color.WHITE);
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new java.awt.Font("Cormorant Garamond", Font.BOLD, 14));
        btnCancelar.setBackground(Color.WHITE);
        btnCancelar.setForeground(Color.RED);
        btnCancelar.setBorder(new LineBorder(Color.RED, 1));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancelar.setPreferredSize(new Dimension(80, 30));

        JButton btnAplicar = new JButton("Aplicar novo estoque");
        btnAplicar.setBackground(new Color(0, 128, 17));
        btnAplicar.setForeground(Color.WHITE);
        btnAplicar.setFocusPainted(false);
        btnAplicar.setPreferredSize(new Dimension(150, 30));
        btnAplicar.setBorder(null);
        btnAplicar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAplicar.setFont(new java.awt.Font("Cormorant Garamond", Font.BOLD, 14));

        btnCancelar.addActionListener(e -> modal.dispose());
        btnAplicar.addActionListener(e -> {
            int quantidade = (int) spinnerQuantidade.getValue();
            updateMassiveStock(quantidade, ids);
            modal.dispose();
        });

        panelBotoes.add(btnCancelar);
        panelBotoes.add(btnAplicar);
        modal.add(panelBotoes);

        modal.setLocationRelativeTo(null);
        modal.setVisible(true);
    }

    private void updateMassiveStock(int quantity, ArrayList<BigInteger> prodIds) {
        try {
            JsonObject jsonData = new JsonObject();
            jsonData.addProperty("quantity", quantity);
            JsonArray jsonProducts = new JsonArray();

            for (BigInteger id : prodIds) {
                jsonProducts.add(id);
            }

            jsonData.add("productsIds", jsonProducts);

            // making the request
            String urlAPI = this.dotenv.get("API_HOST");
            URL url = new URL(urlAPI + "/product/stock");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // send the request with json
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonData.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int statusCode = connection.getResponseCode();
            StringBuilder response = new StringBuilder();

            if (statusCode >= 200 && statusCode < 300) {
                JOptionPane.showMessageDialog(this,
                        "O Estoque foi atualizado com sucesso!",
                        "Estoque Atualizado",
                        JOptionPane.INFORMATION_MESSAGE);
                CardLayout cl = (CardLayout) mainPanel.getLayout();
                restaurarTabelaOriginal();
                getProduct();
            } else {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8))) {
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                }

                try {
                    JsonObject err = JsonParser.parseString(response.toString()).getAsJsonObject();

                    String errorMessage = (err.has("message") && !err.get("message").isJsonNull())
                            ? err.get("message").getAsString()
                            : response.toString();

                    JOptionPane.showMessageDialog(this,
                            "Não foi possível atualizar o estoque. Verifique os dados e tente novamente!\n" + errorMessage,
                            "Erro no Cadastro",
                            JOptionPane.ERROR_MESSAGE);
                } catch (Exception jsonException) {
                    JOptionPane.showMessageDialog(this,
                            "Erro inesperado ao processar a resposta da API.\nCódigo: " + statusCode + "\nResposta: " + response,
                            "Erro no Cadastro",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void atualizarTabelaParaSelecao() {
        DefaultTableModel modelo = (DefaultTableModel) jtable.getModel();

        int colunaAcoes = jtable.getColumn("Ações").getModelIndex();

        TableColumnModel columnModel = jtable.getColumnModel();
        columnModel.getColumn(colunaAcoes).setHeaderValue("Selecionar");

        columnModel.getColumn(colunaAcoes).setCellRenderer(new CheckBoxRenderer());
        columnModel.getColumn(colunaAcoes).setCellEditor(new CheckBoxEditor());

        jtable.getTableHeader().repaint();
        jtable.repaint();
    }

    private void searchProduct(String prodId, String name) {
        try {
            String urlAPI = this.dotenv.get("API_HOST");
            // Construindo a URL com os parâmetros de busca
            StringBuilder urlBuilder = new StringBuilder(urlAPI + "/product/search");
            urlBuilder.append("?");
            boolean hasParam = false;

            if (prodId != null && !prodId.isEmpty()) {
                if (hasParam) urlBuilder.append("&");
                urlBuilder.append("productId=").append(URLEncoder.encode(prodId, "UTF-8"));
                hasParam = true;
            }

            if (name != null && !name.isEmpty()) {
                if (hasParam) urlBuilder.append("&");
                urlBuilder.append("productName=").append(URLEncoder.encode(name, "UTF-8"));
                hasParam = true;
            }

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    JsonArray products = JsonParser.parseString(response.toString()).getAsJsonArray();

                    DefaultTableModel tableModel = (DefaultTableModel) jtable.getModel();
                    tableModel.setRowCount(0); // Limpa a tabela antes de adicionar novos dados

                    for (int i = 0; i < products.size(); i++) {
                        JsonObject product = products.get(i).getAsJsonObject();
                        BigInteger id = product.get("id").getAsBigInteger();
                        String nameP = product.get("name").getAsString();
                        JsonObject supplier = product.get("supplier").getAsJsonObject();
                        JsonObject prodT = product.get("productType").getAsJsonObject();
                        String product_type = prodT.get("name").getAsString();
                        String supplierName = supplier.get("name").getAsString();
                        String quantity = product.get("quantity").getAsString();
                        String price = product.get("price").getAsString();

                        tableModel.addRow(new Object[]{id, nameP, supplierName, product_type, price, quantity});
                    }
                    connection.disconnect();
                }
            } else {
                JOptionPane.showMessageDialog(rootPane,
                        "Erro ao buscar produtos: Código " + responseCode,
                        "Problema na Busca",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(rootPane, "Erro na busca: " + e.getMessage());
        }
    }

    private void filterSales() {
        String searchText = pesquisaProduto.getText().trim();
        if (searchText.equals("Pesquisar produto") || searchText.isEmpty()) {
            getProduct();
        } else {
            if (searchText.matches(".*\\d.*")) {
                searchProduct(searchText, null);
            } else {
                searchProduct(null, searchText);
            }


        }
    }

    class CheckBoxRenderer extends JCheckBox implements TableCellRenderer {
        public CheckBoxRenderer() {
            setHorizontalAlignment(SwingConstants.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setSelected(value != null && (boolean) value);
            return this;
        }
    }

    class CheckBoxEditor extends DefaultCellEditor {
        private final JCheckBox checkBox;

        public CheckBoxEditor() {
            super(new JCheckBox());
            checkBox = (JCheckBox) getComponent();
            checkBox.setHorizontalAlignment(SwingConstants.CENTER);
            checkBox.setOpaque(true);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            checkBox.setSelected(value != null && (boolean) value);
            return checkBox;
        }

        @Override
        public Object getCellEditorValue() {
            return checkBox.isSelected();
        }
    }

    private void restaurarTabelaOriginal() {
        DefaultTableModel modelo = (DefaultTableModel) jtable.getModel();

        int colunaSelecionar = jtable.getColumn("Selecionar").getModelIndex();

        for (int row = 0; row < modelo.getRowCount(); row++) {
            modelo.setValueAt(false, row, colunaSelecionar);
        }

        TableColumnModel columnModel = jtable.getColumnModel();
        columnModel.getColumn(colunaSelecionar).setHeaderValue("Ações");

        columnModel.getColumn(colunaSelecionar).setCellRenderer(new ButtonRendererProduct_());
        columnModel.getColumn(colunaSelecionar).setCellEditor(new ButtonEditorProduct_(jtable, rootPane, dotenv.get("API_HOST"), cadastrarProduto, mainPanel));

        jtable.getTableHeader().repaint();
        jtable.repaint();
    }

    private void loadSupplier(JComboBox<Supplier> cbSupplier) {
        try {
            String urlAPI = this.dotenv.get("API_HOST");
            URL url = new URL(urlAPI + "/supplier");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JsonArray products = JsonParser.parseString(response.toString()).getAsJsonArray();

                DefaultComboBoxModel<Supplier> cb = (DefaultComboBoxModel<Supplier>) cbSupplier.getModel();

                for (int i = 0; i < products.size(); i++) {
                    JsonObject product = products.get(i).getAsJsonObject();
                    String id = product.get("id").getAsString();
                    String name = product.get("name").getAsString();

                    cb.addElement(new Supplier(id, name));

                }
                connection.disconnect();
                cbSupplier.setModel(cb);
            } else {
                JOptionPane.showOptionDialog(rootPane,
                        "Ocorreu um erro ao carregar os Fornecedores no filtro",
                        "Problema no Servidor",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null, null, null);
                connection.disconnect();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }

    private void loadProductType(JComboBox<ProductType> cbProductType) {
        try {
            String urlAPI = this.dotenv.get("API_HOST");
            URL url = new URL(urlAPI + "/product_type");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JsonArray products = JsonParser.parseString(response.toString()).getAsJsonArray();

                DefaultComboBoxModel<ProductType> cb = (DefaultComboBoxModel<ProductType>) cbProductType.getModel();

                for (int i = 0; i < products.size(); i++) {
                    JsonObject product = products.get(i).getAsJsonObject();
                    String id = product.get("id").getAsString();
                    String name = product.get("name").getAsString();

                    cb.addElement(new ProductType(id, name));

                }
                connection.disconnect();
                cbProductType.setModel(cb);
            } else {
                JOptionPane.showOptionDialog(rootPane,
                        "Ocorreu um erro ao carregar os Tipos de Produtos no Filtro",
                        "Problema no Servidor",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null, null, null);
                connection.disconnect();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }

    private void getProduct() {
        try {
            String urlAPI = this.dotenv.get("API_HOST");
            URL url = new URL(urlAPI + "/product");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                Gson gson = new Gson();
                JsonArray products = JsonParser.parseString(response.toString()).getAsJsonArray();

                DefaultTableModel tableModel = (DefaultTableModel) jtable.getModel();
                tableModel.setRowCount(0);

                for (int i = 0; i < products.size(); i++) {
                    JsonObject product = products.get(i).getAsJsonObject();
                    BigInteger id = product.get("id").getAsBigInteger();
                    String name = product.get("name").getAsString();
                    JsonObject supplier = product.get("supplier").getAsJsonObject();
                    JsonObject prodT = product.get("productType").getAsJsonObject();
                    String product_type = prodT.get("name").getAsString();
                    String supplierName = supplier.get("name").getAsString();
                    String quantity = product.get("quantity").getAsString();
                    String price = product.get("price").getAsString();

                    tableModel.addRow(new Object[]{id, name, supplierName, product_type, price, quantity});
                    connection.disconnect();
                }
            } else {
                JOptionPane.showOptionDialog(rootPane,
                        "Ocorreu um erro ao carregar os produtos",
                        "Problema no Servidor",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null, null, null);
                connection.disconnect();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }

    public void atualizarDados() {
        this.getProduct();
    }

    private void getSupplier(String supplierName) {
        try {
            String urlAPI = this.dotenv.get("API_HOST");
            String encodedSupplier = URLEncoder.encode(supplierName, StandardCharsets.UTF_8);
            URL url = new URL(urlAPI + "/product/supplier/?s_name=" + encodedSupplier);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JsonArray products = JsonParser.parseString(response.toString()).getAsJsonArray();
                DefaultTableModel tableModel = (DefaultTableModel) jtable.getModel();
                tableModel.setRowCount(0);

                for (int i = 0; i < products.size(); i++) {
                    JsonObject product = products.get(i).getAsJsonObject();
                    BigInteger id = product.get("id").getAsBigInteger();
                    String name = product.get("name").getAsString();
                    JsonObject supplier = product.get("supplier").getAsJsonObject();
                    JsonObject prodT = product.get("productType").getAsJsonObject();
                    String product_type = prodT.get("name").getAsString();
                    String supplierN = supplier.get("name").getAsString();
                    String quantity = product.get("quantity").getAsString();
                    String price = product.get("price").getAsString();

                    tableModel.addRow(new Object[]{id, name, supplierN, product_type, price, quantity});

                }
                connection.disconnect();
            } else {
                JOptionPane.showOptionDialog(rootPane,
                        "Ocorreu um erro ao carregar os Fornecedores no filtro",
                        "Problema no Servidor",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null, null, null);
                connection.disconnect();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }

    private void getStock(int quantity) {
        try {
            String urlAPI = this.dotenv.get("API_HOST");
            URL url = new URL(urlAPI + "/product/quantity/" + quantity);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JsonArray products = JsonParser.parseString(response.toString()).getAsJsonArray();
                DefaultTableModel tableModel = (DefaultTableModel) jtable.getModel();
                tableModel.setRowCount(0);

                for (int i = 0; i < products.size(); i++) {
                    JsonObject product = products.get(i).getAsJsonObject();
                    BigInteger id = product.get("id").getAsBigInteger();
                    String name = product.get("name").getAsString();
                    JsonObject supplier = product.get("supplier").getAsJsonObject();
                    JsonObject prodT = product.get("productType").getAsJsonObject();
                    String product_type = prodT.get("name").getAsString();
                    String supplierN = supplier.get("name").getAsString();
                    String quantit = product.get("quantity").getAsString();
                    String price = product.get("price").getAsString();

                    tableModel.addRow(new Object[]{id, name, supplierN, product_type, price, quantit});

                }
                connection.disconnect();
            } else {
                JOptionPane.showOptionDialog(rootPane,
                        "Ocorreu um erro ao carregar os Fornecedores no filtro",
                        "Problema no Servidor",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null, null, null);
                connection.disconnect();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }

    private void getLowStock() {
        try {
            String urlAPI = this.dotenv.get("API_HOST");
            URL url = new URL(urlAPI + "/product/quantity");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JsonArray products = JsonParser.parseString(response.toString()).getAsJsonArray();
                DefaultTableModel tableModel = (DefaultTableModel) jtable.getModel();
                tableModel.setRowCount(0);

                for (int i = 0; i < products.size(); i++) {
                    JsonObject product = products.get(i).getAsJsonObject();
                    BigInteger id = product.get("id").getAsBigInteger();
                    String name = product.get("name").getAsString();
                    JsonObject supplier = product.get("supplier").getAsJsonObject();
                    JsonObject prodT = product.get("productType").getAsJsonObject();
                    String product_type = prodT.get("name").getAsString();
                    String supplierN = supplier.get("name").getAsString();
                    String quantit = product.get("quantity").getAsString();
                    String price = product.get("price").getAsString();

                    tableModel.addRow(new Object[]{id, name, supplierN, product_type, price, quantit});

                }
                connection.disconnect();
            } else {
                JOptionPane.showOptionDialog(rootPane,
                        "Ocorreu um erro ao carregar os Fornecedores no filtro",
                        "Problema no Servidor",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null, null, null);
                connection.disconnect();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }

    private void getProductByProdT(String prodT) {
        try {
            String urlAPI = this.dotenv.get("API_HOST");
            URL url = new URL(urlAPI + "/product/prodType/?t_name=" + URLEncoder.encode(prodT, StandardCharsets.UTF_8));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JsonArray products = JsonParser.parseString(response.toString()).getAsJsonArray();
                DefaultTableModel tableModel = (DefaultTableModel) jtable.getModel();
                tableModel.setRowCount(0);

                for (int i = 0; i < products.size(); i++) {
                    JsonObject product = products.get(i).getAsJsonObject();
                    BigInteger id = product.get("id").getAsBigInteger();
                    String name = product.get("name").getAsString();
                    JsonObject supplier = product.get("supplier").getAsJsonObject();
                    JsonObject prodType = product.get("productType").getAsJsonObject();
                    String product_type = prodType.get("name").getAsString();
                    String supplierN = supplier.get("name").getAsString();
                    String quantit = product.get("quantity").getAsString();
                    String price = product.get("price").getAsString();

                    tableModel.addRow(new Object[]{id, name, supplierN, product_type, price, quantit});

                }
                connection.disconnect();
            } else {
                JOptionPane.showOptionDialog(rootPane,
                        "Ocorreu um erro ao carregar os Fornecedores no filtro",
                        "Problema no Servidor",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null, null, null);
                connection.disconnect();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }

    private void getSupplierQuantity(String supplierName, int quantity) {
        try {
            String urlAPI = this.dotenv.get("API_HOST");
            String urlString = urlAPI + "/product/get/SupQua?quantity=" + URLEncoder.encode(String.valueOf(quantity), StandardCharsets.UTF_8) +
                    "&supplier=" + URLEncoder.encode(supplierName, StandardCharsets.UTF_8);
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JsonArray products = JsonParser.parseString(response.toString()).getAsJsonArray();
                DefaultTableModel tableModel = (DefaultTableModel) jtable.getModel();
                tableModel.setRowCount(0);

                for (int i = 0; i < products.size(); i++) {
                    JsonObject product = products.get(i).getAsJsonObject();
                    BigInteger id = product.get("id").getAsBigInteger();
                    String name = product.get("name").getAsString();
                    JsonObject supplier = product.get("supplier").getAsJsonObject();
                    JsonObject prodType = product.get("productType").getAsJsonObject();
                    String product_type = prodType.get("name").getAsString();
                    String supplierN = supplier.get("name").getAsString();
                    String quantit = product.get("quantity").getAsString();
                    String price = product.get("price").getAsString();

                    tableModel.addRow(new Object[]{id, name, supplierN, product_type, price, quantit});

                }
                connection.disconnect();
            } else {
                JOptionPane.showOptionDialog(rootPane,
                        "Ocorreu um erro ao carregar os Fornecedores no filtro",
                        "Problema no Servidor",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null, null, null);
                connection.disconnect();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }

    private void getSupplierLowQuantity(String supplierName) {
        try {
            String urlAPI = this.dotenv.get("API_HOST");
            String urlString = urlAPI + "/product/get/SupLQ?supplier=" + URLEncoder.encode(supplierName, StandardCharsets.UTF_8);
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JsonArray products = JsonParser.parseString(response.toString()).getAsJsonArray();
                DefaultTableModel tableModel = (DefaultTableModel) jtable.getModel();
                tableModel.setRowCount(0);

                for (int i = 0; i < products.size(); i++) {
                    JsonObject product = products.get(i).getAsJsonObject();
                    BigInteger id = product.get("id").getAsBigInteger();
                    String name = product.get("name").getAsString();
                    JsonObject supplier = product.get("supplier").getAsJsonObject();
                    JsonObject prodType = product.get("productType").getAsJsonObject();
                    String product_type = prodType.get("name").getAsString();
                    String supplierN = supplier.get("name").getAsString();
                    String quantit = product.get("quantity").getAsString();
                    String price = product.get("price").getAsString();

                    tableModel.addRow(new Object[]{id, name, supplierN, product_type, price, quantit});

                }
                connection.disconnect();
            } else {
                JOptionPane.showOptionDialog(rootPane,
                        "Ocorreu um erro ao carregar os Fornecedores no filtro",
                        "Problema no Servidor",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null, null, null);
                connection.disconnect();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }

    private void getSupplierProdT(String supplierName, String prodTName) {
        try {
            String urlAPI = this.dotenv.get("API_HOST");
            String urlString = urlAPI + "/product/get/SupProdT?supplier=" + URLEncoder.encode(supplierName, StandardCharsets.UTF_8)
                    + "&prod_t=" + URLEncoder.encode(prodTName, StandardCharsets.UTF_8);
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JsonArray products = JsonParser.parseString(response.toString()).getAsJsonArray();
                DefaultTableModel tableModel = (DefaultTableModel) jtable.getModel();
                tableModel.setRowCount(0);

                for (int i = 0; i < products.size(); i++) {
                    JsonObject product = products.get(i).getAsJsonObject();
                    BigInteger id = product.get("id").getAsBigInteger();
                    String name = product.get("name").getAsString();
                    JsonObject supplier = product.get("supplier").getAsJsonObject();
                    JsonObject prodType = product.get("productType").getAsJsonObject();
                    String product_type = prodType.get("name").getAsString();
                    String supplierN = supplier.get("name").getAsString();
                    String quantit = product.get("quantity").getAsString();
                    String price = product.get("price").getAsString();

                    tableModel.addRow(new Object[]{id, name, supplierN, product_type, price, quantit});

                }
                connection.disconnect();
            } else {
                JOptionPane.showOptionDialog(rootPane,
                        "Ocorreu um erro ao carregar os Fornecedores no filtro",
                        "Problema no Servidor",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null, null, null);
                connection.disconnect();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }

    private void getQuantityByProdType(int quantity, String prodTName) {
        try {
            String urlAPI = this.dotenv.get("API_HOST");
            String urlString = urlAPI + "/product/get/QuaProdT?quantity=" + URLEncoder.encode(String.valueOf(quantity), StandardCharsets.UTF_8)
                    + "&prod_t=" + URLEncoder.encode(prodTName, StandardCharsets.UTF_8);
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JsonArray products = JsonParser.parseString(response.toString()).getAsJsonArray();
                DefaultTableModel tableModel = (DefaultTableModel) jtable.getModel();
                tableModel.setRowCount(0);

                for (int i = 0; i < products.size(); i++) {
                    JsonObject product = products.get(i).getAsJsonObject();
                    BigInteger id = product.get("id").getAsBigInteger();
                    String name = product.get("name").getAsString();
                    JsonObject supplier = product.get("supplier").getAsJsonObject();
                    JsonObject prodType = product.get("productType").getAsJsonObject();
                    String product_type = prodType.get("name").getAsString();
                    String supplierN = supplier.get("name").getAsString();
                    String quantit = product.get("quantity").getAsString();
                    String price = product.get("price").getAsString();

                    tableModel.addRow(new Object[]{id, name, supplierN, product_type, price, quantit});

                }
                connection.disconnect();
            } else {
                JOptionPane.showOptionDialog(rootPane,
                        "Ocorreu um erro ao carregar os Fornecedores no filtro",
                        "Problema no Servidor",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null, null, null);
                connection.disconnect();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }

    private void getLowStockAndProdType(String prodTName) {
        try {
            String urlAPI = this.dotenv.get("API_HOST");
            String urlString = urlAPI + "/product/get/LQProdT?prod_t=" + URLEncoder.encode(prodTName, StandardCharsets.UTF_8);
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JsonArray products = JsonParser.parseString(response.toString()).getAsJsonArray();
                DefaultTableModel tableModel = (DefaultTableModel) jtable.getModel();
                tableModel.setRowCount(0);

                for (int i = 0; i < products.size(); i++) {
                    JsonObject product = products.get(i).getAsJsonObject();
                    BigInteger id = product.get("id").getAsBigInteger();
                    String name = product.get("name").getAsString();
                    JsonObject supplier = product.get("supplier").getAsJsonObject();
                    JsonObject prodType = product.get("productType").getAsJsonObject();
                    String product_type = prodType.get("name").getAsString();
                    String supplierN = supplier.get("name").getAsString();
                    String quantit = product.get("quantity").getAsString();
                    String price = product.get("price").getAsString();

                    tableModel.addRow(new Object[]{id, name, supplierN, product_type, price, quantit});

                }
                connection.disconnect();
            } else {
                JOptionPane.showOptionDialog(rootPane,
                        "Ocorreu um erro ao carregar os Fornecedores no filtro",
                        "Problema no Servidor",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null, null, null);
                connection.disconnect();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }

    private void getSupplierQuantityAndProdType(String supplierName, String quantity, String prodTName) {
        try {
            String urlAPI = this.dotenv.get("API_HOST");
            String urlString = urlAPI + "/product/get/SupQuaProdT?supplier=" + URLEncoder.encode(supplierName, StandardCharsets.UTF_8)
                    + "&quantity=" + URLEncoder.encode(quantity, StandardCharsets.UTF_8)
                    + "&prod_t=" + URLEncoder.encode(prodTName, StandardCharsets.UTF_8);
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JsonArray products = JsonParser.parseString(response.toString()).getAsJsonArray();
                DefaultTableModel tableModel = (DefaultTableModel) jtable.getModel();
                tableModel.setRowCount(0);

                for (int i = 0; i < products.size(); i++) {
                    JsonObject product = products.get(i).getAsJsonObject();
                    BigInteger id = product.get("id").getAsBigInteger();
                    String name = product.get("name").getAsString();
                    JsonObject supplier = product.get("supplier").getAsJsonObject();
                    JsonObject prodType = product.get("productType").getAsJsonObject();
                    String product_type = prodType.get("name").getAsString();
                    String supplierN = supplier.get("name").getAsString();
                    String quantit = product.get("quantity").getAsString();
                    String price = product.get("price").getAsString();

                    tableModel.addRow(new Object[]{id, name, supplierN, product_type, price, quantit});

                }
                connection.disconnect();
            } else {
                JOptionPane.showOptionDialog(rootPane,
                        "Ocorreu um erro ao carregar os Fornecedores no filtro",
                        "Problema no Servidor",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null, null, null);
                connection.disconnect();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }

    private void getSupplierLowStockAndProdType(String supplierName, String prodTName) {
        try {
            String urlAPI = this.dotenv.get("API_HOST");
            String urlString = urlAPI + "/product/get/SupLQProdT?supplier=" + URLEncoder.encode(supplierName, StandardCharsets.UTF_8)
                    + "&prod_t=" + URLEncoder.encode(prodTName, StandardCharsets.UTF_8);
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JsonArray products = JsonParser.parseString(response.toString()).getAsJsonArray();
                DefaultTableModel tableModel = (DefaultTableModel) jtable.getModel();
                tableModel.setRowCount(0);

                for (int i = 0; i < products.size(); i++) {
                    JsonObject product = products.get(i).getAsJsonObject();
                    BigInteger id = product.get("id").getAsBigInteger();
                    String name = product.get("name").getAsString();
                    JsonObject supplier = product.get("supplier").getAsJsonObject();
                    JsonObject prodType = product.get("productType").getAsJsonObject();
                    String product_type = prodType.get("name").getAsString();
                    String supplierN = supplier.get("name").getAsString();
                    String quantit = product.get("quantity").getAsString();
                    String price = product.get("price").getAsString();

                    tableModel.addRow(new Object[]{id, name, supplierN, product_type, price, quantit});

                }
                connection.disconnect();
            } else {
                JOptionPane.showOptionDialog(rootPane,
                        "Ocorreu um erro ao carregar os Fornecedores no filtro",
                        "Problema no Servidor",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null, null, null);
                connection.disconnect();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }

    private void abrirDialogoFiltro(JFrame parent) {
        JDialog dialog = new JDialog(parent, "Filtros", true);
        dialog.setSize(250, 320);
        dialog.setLayout(new GridBagLayout());
        dialog.setLocationRelativeTo(jButtonFiltrar);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JCheckBox chkFornecedor = new JCheckBox("Fornecedor");
        chkFornecedor.setFont(new Font("Cormorant Garamond", Font.BOLD, 14));
        chkFornecedor.setForeground(Color.BLACK);
        dialog.add(chkFornecedor, gbc);

        gbc.gridy = 1;
        JComboBox<Supplier> cbFornecedor = new JComboBox<Supplier>();
        cbFornecedor.setFont(new Font("Cormorant Garamond", Font.BOLD, 14));
        cbFornecedor.setForeground(Color.BLACK);
        this.loadSupplier(cbFornecedor);
        dialog.add(cbFornecedor, gbc);

        gbc.gridy = 2;
        JCheckBox chkEstoque = new JCheckBox("Quant. em estoque");
        chkEstoque.setFont(new Font("Cormorant Garamond", Font.BOLD, 14));
        chkEstoque.setForeground(Color.BLACK);
        dialog.add(chkEstoque, gbc);

        gbc.gridy = 3;
        JSpinner spnQuantidade = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        spnQuantidade.setFont(new Font("Cormorant Infant", Font.BOLD, 14));
        spnQuantidade.setForeground(Color.BLACK);
        dialog.add(spnQuantidade, gbc);

        gbc.gridy = 4;
        JCheckBox chkBaixoEstoque = new JCheckBox("Com baixo estoque");
        chkBaixoEstoque.setFont(new Font("Cormorant Garamond", Font.BOLD, 14));
        chkBaixoEstoque.setForeground(Color.BLACK);
        dialog.add(chkBaixoEstoque, gbc);

        gbc.gridy = 5;
        JCheckBox chkTipo = new JCheckBox("Tipo");
        chkTipo.setFont(new Font("Cormorant Garamond", Font.BOLD, 14));
        chkTipo.setForeground(Color.BLACK);
        dialog.add(chkTipo, gbc);

        gbc.gridy = 6;
        JComboBox<ProductType> cbTipo = new JComboBox<ProductType>();
        cbTipo.setFont(new Font("Cormorant Garamond", Font.BOLD, 14));
        cbTipo.setForeground(Color.BLACK);
        loadProductType(cbTipo);
        dialog.add(cbTipo, gbc);

        gbc.gridy = 7;
        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.setBackground(new Color(0, 0, 139));
        btnFiltrar.setFont(new Font("Cormorant Garamond", Font.BOLD, 16));
        btnFiltrar.setForeground(Color.BLACK);
        btnFiltrar.setForeground(Color.WHITE);
        btnFiltrar.setFocusPainted(false);
        btnFiltrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        dialog.add(btnFiltrar, gbc);

        btnFiltrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chkFornecedor.isSelected()) {
                    if (chkTipo.isSelected()) {
                        Supplier sup = (Supplier) cbFornecedor.getSelectedItem();
                        ProductType prod = (ProductType) cbTipo.getSelectedItem();
                        getSupplierProdT(sup.getName(), prod.getName());
                        dialog.setVisible(false);
                    } else if (chkEstoque.isSelected()) {
                        Supplier sup = (Supplier) cbFornecedor.getSelectedItem();
                        getSupplierQuantity(sup.getName(), Integer.parseInt(spnQuantidade.getValue().toString()));
                        dialog.setVisible(false);
                    } else if (chkBaixoEstoque.isSelected()) {
                        Supplier sup = (Supplier) cbFornecedor.getSelectedItem();
                        getSupplierLowQuantity(sup.getName());
                        dialog.setVisible(false);
                    } else {
                        Supplier sup = (Supplier) cbFornecedor.getSelectedItem();
                        getSupplier(sup.getName());
                        dialog.setVisible(false);
                    }


                } else if (chkEstoque.isSelected()) {
                    if (chkTipo.isSelected()) {
                        ProductType prod = (ProductType) cbTipo.getSelectedItem();
                        getQuantityByProdType(Integer.parseInt(spnQuantidade.getValue().toString()), prod.getName());
                        dialog.setVisible(false);
                    } else if (chkBaixoEstoque.isSelected()) {
                        JOptionPane.showMessageDialog(rootPane, "Não é possivel filtrar por quantidade especifica de estoque e por baixo estoque!", "Erro na seleção de filtragem", JOptionPane.ERROR_MESSAGE);
                    } else {
                        getStock(Integer.parseInt(spnQuantidade.getValue().toString()));
                        dialog.setVisible(false);
                    }


                } else if (chkBaixoEstoque.isSelected()) {
                    if (chkTipo.isSelected()) {
                        ProductType prod = (ProductType) cbTipo.getSelectedItem();
                        getLowStockAndProdType(prod.getName());
                        dialog.setVisible(false);
                    } else {
                        getLowStock();
                        dialog.setVisible(false);
                    }

                } else if (chkTipo.isSelected()) {
                    ProductType prod = (ProductType) cbTipo.getSelectedItem();
                    getProductByProdT(prod.getName());
                    dialog.setVisible(false);
                } else if (chkFornecedor.isSelected() && chkTipo.isSelected() && chkBaixoEstoque.isSelected()) {
                    ProductType prod = (ProductType) cbTipo.getSelectedItem();
                    Supplier sup = (Supplier) cbFornecedor.getSelectedItem();
                    getSupplierQuantityAndProdType(sup.getName(), spnQuantidade.getValue().toString(), prod.getName());
                    dialog.setVisible(false);
                } else if (chkFornecedor.isSelected() && chkEstoque.isSelected() && chkTipo.isSelected()) {
                    ProductType prod = (ProductType) cbTipo.getSelectedItem();
                    Supplier sup = (Supplier) cbFornecedor.getSelectedItem();
                    getSupplierLowStockAndProdType(sup.getName(), prod.getName());
                    dialog.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(parent, "Selecione uma opção de Filtro!", "Erro na seleção de filtro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        dialog.setVisible(true);
    }

    private javax.swing.JTextField pesquisaProduto;
    private javax.swing.JTable jtable;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JPanel jPanelTopoTabela;
    private javax.swing.JPanel jPanelTabela;
    private javax.swing.JPanel jPanelButtonsStock;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JButton jButtonCadastrar;
    private javax.swing.JButton jButtonFiltrar;
    private final Timer timer = new Timer(300, e -> filterSales());
}

class ButtonRendererProduct_ extends JPanel implements TableCellRenderer {
    private final JButton editButton = new JButton();
    private final JButton deleteButton = new JButton();

    public ButtonRendererProduct_() {
        setOpaque(true);
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));

        this.editButton.setIcon(new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/images/editar.png"))));
        this.editButton.setBackground(Color.WHITE);
        this.editButton.setBorder(null);
        this.editButton.setContentAreaFilled(false);
        this.editButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        this.deleteButton.setIcon(new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/images/excluir.png"))));
        this.deleteButton.setBackground(Color.WHITE);
        this.deleteButton.setBorder(null);
        this.deleteButton.setContentAreaFilled(false);
        this.deleteButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        add(editButton);
        add(deleteButton);

        editButton.setFocusable(false);
        deleteButton.setFocusable(false);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(128, 0, 32));
        return this;
    }
}

class ButtonEditorProduct_ extends AbstractCellEditor implements TableCellEditor {
    private final JPanel panel = new JPanel();
    private final JButton editButton = new JButton();
    private final JButton deleteButton = new JButton();
    private final JTable table;
    private int currentRow;
    private final JFrame frame;
    private final String APIURL;

    public ButtonEditorProduct_(JTable table, JRootPane rootPane, String APIURL, CadastrarProduto card, JPanel mainPanel) {
        this.table = table;
        this.frame = (JFrame) SwingUtilities.getWindowAncestor(rootPane);
        this.APIURL = APIURL;

        panel.setLayout(new

                FlowLayout(FlowLayout.CENTER, 5, 0));

        this.editButton.setIcon(new javax.swing.ImageIcon(Objects.requireNonNull(
                getClass().getResource("/resources/images/editar.png"))));
        this.editButton.setOpaque(true);
        this.editButton.setBackground(new java.awt.Color(255, 255, 255));
        this.editButton.setForeground(new java.awt.Color(128, 0, 32));
        this.editButton.setFocusPainted(false);
        this.editButton.setBorder(null);
        this.editButton.setContentAreaFilled(false);
        this.editButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        this.deleteButton.setIcon(new javax.swing.ImageIcon(Objects.requireNonNull(
                getClass().getResource("/resources/images/excluir.png"))));
        this.deleteButton.setBackground(new java.awt.Color(255, 255, 255));
        this.deleteButton.setForeground(new java.awt.Color(128, 0, 32));
        this.deleteButton.setFocusPainted(false);
        this.deleteButton.setBorder(null);
        this.deleteButton.setContentAreaFilled(false);
        this.deleteButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        panel.add(editButton);
        panel.add(deleteButton);

        editButton.addActionListener(evt -> {
            int cellIndex = table.getSelectedRow();
            Object cellValue = table.getValueAt(cellIndex, 0);
            card.setId(cellValue.toString());
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            cl.show(mainPanel, "cadastrar_produto");
            stopCellEditing();
        });

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
                    Object cellValue = table.getValueAt(cellIndex, 0);
                    BigInteger id_product = new BigInteger(cellValue.toString());
                    URL url = new URL(this.APIURL + "/product/" + id_product);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    connection.setRequestMethod("DELETE");

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_NO_CONTENT || responseCode == HttpURLConnection.HTTP_OK) {
                        ((DefaultTableModel) table.getModel()).removeRow(currentRow);
                        JOptionPane.showOptionDialog(this.frame,
                                "O produto foi deletado com sucesso",
                                "Produto Deletado",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.INFORMATION_MESSAGE,
                                null,
                                null,
                                null);
                    } else {
                        JOptionPane.showOptionDialog(this.frame,
                                "Ocorreu um erro no servidor ao deletar o produto",
                                "Erro ao deletar produto",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.ERROR_MESSAGE,
                                null,
                                null,
                                null);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this.frame, ex.getMessage());
                }
            } else {
                stopCellEditing();
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
