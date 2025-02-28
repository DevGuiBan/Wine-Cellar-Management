package resources.interface_card;

import com.google.gson.*;
import resources.interfaces.ProductType;
import resources.interfaces.Supplier;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Objects;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import io.github.cdimascio.dotenv.Dotenv;

public class CadastrarProduto extends JPanel {
    private Dotenv dotenv;
    private JRootPane framePrincipal;
    private JPanel mainPanel;
    private String id;
    private JanelaPrincipal janelaPrincipal;

    public CadastrarProduto(JPanel mainPanel, JRootPane framePrincipal) {
        this.mainPanel = mainPanel;
        this.framePrincipal = framePrincipal;
        this.dotenv = Dotenv.load();
        this.janelaPrincipal = (JanelaPrincipal) SwingUtilities.getWindowAncestor(framePrincipal);
        this.initComponentes();
        getSupplier();
        getProductType();
    }

    public void setId(String id) {
        this.id = id;
        this.getProduct();
        this.jButtonCadastrar.setText("Atualizar");
        jLabelCadastro.setText("Editar Produto");
    }

    public void reset() {
        this.jComboBoxSupplier.setSelectedIndex(0);
        this.jComboBoxProductType.setSelectedIndex(0);
        this.jSpinnerQuantidade.setValue(0);
        this.jSpinnerPrecoVenda.setValue(0);
        this.jTextFieldDescricao.setText(null);
        this.jTextFieldNome.setText(null);
        this.jButtonCadastrar.setText("Cadastrar");
        jLabelCadastro.setText("Cadastrar Produto");
        this.id = null;
    }

    private void initComponentes() {
        // inicialização de variáveis
        frame = (JFrame) SwingUtilities.getWindowAncestor(framePrincipal);

        jLabelTipoProduto = new JLabel();
        jLabelQuantidade = new JLabel();
        jLabelDescricao = new JLabel();
        jLabelPrecoVenda = new JLabel();
        jLabelCadastro = new JLabel();
        jLabelNome = new JLabel();
        jLabelFornecedor = new JLabel();

        jSpinnerPrecoVenda = new JSpinner(new SpinnerNumberModel(0.0, 0.0, Double.MAX_VALUE, 0.10));
        jSpinnerQuantidade = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));

        jTextFieldNome = new JTextField();
        jTextFieldDescricao = new JTextField();

        jComboBoxProductType = new JComboBox<ProductType>();
        jComboBoxSupplier = new JComboBox<Supplier>();

        jButtonCadastrar = new JButton();
        jButtonCancelar = new JButton();
        jButtonVisualizarProduto = new JButton();

        jPanelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        jPanelContent = new JPanel(new GridBagLayout());
        jPanelHeader = new JPanel();
        jPanel = new JPanel();

        gbc = new GridBagConstraints();

        jSeparator = new JSeparator();

        // configuração do card
        setBackground(new Color(243, 243, 223));
        setPreferredSize(new Dimension(1366, 650)); // tamanho do painel que tem tudo
        setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));

        gbc.fill = GridBagConstraints.HORIZONTAL; // Permitir redimensionamento horizontal
        gbc.anchor = GridBagConstraints.WEST; // Alinhamento à esquerda
        Dimension fieldSize = new Dimension(400, 40); // tamanho padrão dos campos

        // painel branco que vai conter tudo
        jPanel.setPreferredSize(new Dimension(1200, 600));
        jPanel.setBackground(Color.WHITE);
        jPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // painel do texto de cadastro de produto(header)
        jPanelHeader.setLayout(new BoxLayout(jPanelHeader, BoxLayout.Y_AXIS));
        jPanelHeader.setBackground(new Color(255, 255, 255));
        jPanelHeader.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanelHeader.setPreferredSize(new Dimension(1200, 50));

        jLabelCadastro.setFont(new Font("Cormorant Garamond SemiBold", 0, 30));
        jLabelCadastro.setText("Cadastrar Produto");
        jLabelCadastro.setAlignmentX(Component.CENTER_ALIGNMENT); // centralizar

        jSeparator.setBackground(new Color(128, 0, 32));
        jSeparator.setForeground(new Color(128, 0, 32));
        jSeparator.setPreferredSize(new Dimension(35, 8));
        jSeparator.setMaximumSize(new Dimension(230, 8));
        jSeparator.setAlignmentX(Component.CENTER_ALIGNMENT); // centralizar

        jPanelHeader.setOpaque(false);
        jPanelHeader.add(jLabelCadastro);
        jPanelHeader.add(jSeparator); // adiciona no painel

        // adiciona o painelHeader no principal
        jPanel.add(jPanelHeader);

        // painel dos campos
        jPanelContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanelContent.setPreferredSize(new Dimension(1200, 450));
        jPanelContent.setBackground(new Color(255, 255, 255));

        jLabelNome.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelNome.setText("Nome:");
        jLabelNome.setForeground(Color.BLACK);
        gbc.gridx = 0; // coluna
        gbc.gridy = 0; // linha
        gbc.insets = new Insets(0, 0, 0, 100); // adicionar espaçamento por componente
        jPanelContent.add(jLabelNome, gbc); // sempre adicionar neste formato por conta do grid

        jTextFieldNome.setBackground(new Color(255, 255, 255));
        jTextFieldNome.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jTextFieldNome.setFont(new Font("Cormorant Garamond", 1, 18));
        jTextFieldNome.setPreferredSize(fieldSize);
        jTextFieldNome.setForeground(Color.BLACK);
        jTextFieldNome.setText("Nome do Produto");

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 100);
        jPanelContent.add(jTextFieldNome, gbc);

        jLabelDescricao.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelDescricao.setText("Descrição:");
        jLabelDescricao.setForeground(Color.BLACK);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        jPanelContent.add(jLabelDescricao, gbc);

        jTextFieldDescricao.setBackground(new Color(255, 255, 255));
        jTextFieldDescricao.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jTextFieldDescricao.setFont(new Font("Cormorant Garamond", 1, 18));
        jTextFieldDescricao.setPreferredSize(fieldSize);
        jTextFieldDescricao.setForeground(Color.BLACK);
        jTextFieldDescricao.setText("Descrição do Produto");
        jTextFieldDescricao.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (jTextFieldDescricao.getText().equals("Descrição do Produto")) {
                    jTextFieldDescricao.setText("");
                    jTextFieldDescricao.setForeground(Color.GRAY);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (jTextFieldDescricao.getText().isEmpty()) {
                    jTextFieldDescricao.setText("Descrição do Produto");
                    jTextFieldDescricao.setForeground(Color.GRAY);
                }
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 0);
        jPanelContent.add(jTextFieldDescricao, gbc);

        jLabelFornecedor.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelFornecedor.setText("Fornecedor:");
        jLabelFornecedor.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 0, 100);
        jPanelContent.add(jLabelFornecedor, gbc);

        jComboBoxSupplier.setBackground(new Color(255, 255, 255));
        jComboBoxSupplier.setBackground(Color.WHITE);
        jComboBoxSupplier.setForeground(Color.BLACK);
        jComboBoxSupplier.setFont(new Font("Cormorant Garamond", Font.BOLD, 18));
        jComboBoxSupplier.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jComboBoxSupplier.setPreferredSize(fieldSize);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 20, 100);
        jPanelContent.add(jComboBoxSupplier, gbc);

        jLabelQuantidade.setBackground(Color.WHITE);
        jLabelQuantidade.setText("Quantidade:");
        jLabelQuantidade.setForeground(Color.BLACK);
        jLabelQuantidade.setFont(new Font("Cormorant Garamond", 1, 18));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        jPanelContent.add(jLabelQuantidade, gbc);

        jSpinnerQuantidade.setBackground(Color.WHITE);
        jSpinnerQuantidade.setFont(new Font("Cormorant Infant", 1, 18));
        jSpinnerQuantidade.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jSpinnerQuantidade.setForeground(Color.BLACK);
        jSpinnerQuantidade.setPreferredSize(fieldSize);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 20, 0);
        jPanelContent.add(jSpinnerQuantidade, gbc);

        jLabelPrecoVenda.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelPrecoVenda.setText("Preço de Venda:");
        jLabelPrecoVenda.setForeground(Color.BLACK);
        jLabelPrecoVenda.setBackground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 0, 100);
        jPanelContent.add(jLabelPrecoVenda, gbc);

        jSpinnerPrecoVenda.setBackground(Color.WHITE);
        jSpinnerPrecoVenda.setFont(new Font("Cormorant Infant", 1, 18));
        jSpinnerPrecoVenda.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jSpinnerPrecoVenda.setPreferredSize(fieldSize);
        jSpinnerPrecoVenda.setForeground(Color.BLACK);
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(jSpinnerPrecoVenda, "0.00"); // máscara de preço pro spinner
        jSpinnerPrecoVenda.setEditor(editor);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 0, 100);
        jPanelContent.add(jSpinnerPrecoVenda, gbc);

        jLabelTipoProduto.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelTipoProduto.setText("Tipo:");
        jLabelTipoProduto.setForeground(Color.BLACK);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        jPanelContent.add(jLabelTipoProduto, gbc);

        jComboBoxProductType.setBackground(new Color(255, 255, 255));
        jComboBoxProductType.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jComboBoxProductType.setPreferredSize(fieldSize);
        jComboBoxProductType.setForeground(Color.BLACK);
        jComboBoxProductType.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedItem = e.getItem().toString();

                    if ("Outros".equalsIgnoreCase(selectedItem)) {
                        String novoNome = JOptionPane.showInputDialog(framePrincipal, "Digite o novo tipo de produto:");
                        if (novoNome != null && !novoNome.trim().isEmpty()) {
                            adicionarTipoProdutoComboBox(novoNome);
                        }
                    }
                }
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 0, 0);
        jPanelContent.add(jComboBoxProductType, gbc);


        frame.add(jButtonVisualizarProduto);

        jButtonVisualizarProduto.setFont(new java.awt.Font("Cormorant Garamond Bold", 1, 18));
        jButtonVisualizarProduto.setForeground(new java.awt.Color(0, 28, 128));
        jButtonVisualizarProduto.setBackground(Color.WHITE);
        jButtonVisualizarProduto.setText("Visualizar Tipos de Produtos");
        jButtonVisualizarProduto.setBorder(null);
        jButtonVisualizarProduto.setOpaque(false);
        jButtonVisualizarProduto.setFocusPainted(false);
        jButtonVisualizarProduto.setFocusable(false);
        jButtonVisualizarProduto.setBorder(new EmptyBorder(0, 0, 0, 460));
        jButtonVisualizarProduto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                abrirModalTiposProdutos(frame);
            }
        });

        jButtonCancelar.setBackground(new Color(225, 225, 200));
        jButtonCancelar.setFont(new Font("Cormorant Garamond SemiBold", 0, 18));
        jButtonCancelar.setText("Cancelar");
        jButtonCancelar.setFocusPainted(false);
        jButtonCancelar.setBorder(new EmptyBorder(5, 20, 5, 20));
        jButtonCancelar.addActionListener(e -> {
            this.reset();
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            cl.show(mainPanel, "listar_produtos");
        });

        jButtonCadastrar.setBackground(new Color(0, 128, 17));
        jButtonCadastrar.setFont(new Font("Cormorant Garamond SemiBold", 0, 18));
        jButtonCadastrar.setForeground(new Color(255, 255, 200));
        jButtonCadastrar.setText("Cadastrar");
        jButtonCadastrar.setFocusPainted(false);
        jButtonCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (id == null) {
                    cadastrarProduto();
                } else {
                    editarProduto();
                }
            }
        });

        jPanel.add(jPanelContent);

        jPanelButtons.setBackground(new Color(255, 255, 255));
        jPanelButtons.setPreferredSize(new Dimension(1200, 50));
        jPanelButtons.add(jButtonVisualizarProduto);
        jPanelButtons.add(jButtonCancelar);
        jPanelButtons.add(jButtonCadastrar);
        jPanelButtons.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        jPanelButtons.setBorder(new EmptyBorder(5, 10, 5, 140));

        jPanel.add(jPanelButtons);

        add(jPanel);

    }

    private void abrirModalTiposProdutos(JFrame parent) {
        JDialog modal = new JDialog(parent, "Tipos de Produtos", true);
        modal.setSize(400, 300);
        modal.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Tipos de Produtos", SwingConstants.LEFT);
        titleLabel.setFont(new Font("Cormorant Garamond SemiBold", Font.BOLD, 18));
        titleLabel.setForeground(Color.BLACK);

        topPanel.add(titleLabel, BorderLayout.WEST);

        DefaultListModel<ProductType> model = new DefaultListModel<>();
        JList<ProductType> list = new JList<>(model);
        list.setCellRenderer((jList, value, index, isSelected, cellHasFocus) -> {
            JLabel label = new JLabel(value.getName());
            label.setFont(new Font("Cormorant Garamond SemiBold", Font.BOLD, 14));
            label.setForeground(Color.BLACK);
            label.setOpaque(true);
            label.setBackground(isSelected ? new Color(200, 200, 255) : Color.WHITE);
            return label;
        });

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(new EmptyBorder(5, 10, 5, 10));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(Color.WHITE);

        JButton novoButton = new JButton();
        novoButton.setBackground(Color.WHITE);
        novoButton.setIcon(new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/images/add.png"))));
        novoButton.setFocusPainted(false);
        novoButton.setBorderPainted(false);
        novoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton editarButton = new JButton();
        editarButton.setBackground(Color.WHITE);
        editarButton.setIcon(new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/images/editar.png"))));
        editarButton.setFocusPainted(false);
        editarButton.setBorderPainted(false);
        editarButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton removerButton = new JButton();
        removerButton.setBackground(Color.WHITE);
        removerButton.setIcon(new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/images/excluir.png"))));
        removerButton.setFocusPainted(false);
        removerButton.setBorderPainted(false);
        removerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        bottomPanel.add(novoButton);
        bottomPanel.add(editarButton);
        bottomPanel.add(removerButton);

        carregarTiposProduto(model);

        novoButton.addActionListener(e -> {
            String novoNome = JOptionPane.showInputDialog(modal, "Digite o novo tipo de produto:");
            if (novoNome != null && !novoNome.trim().isEmpty()) {
                adicionarTipoProduto(novoNome, model);
            }
        });

        editarButton.addActionListener(e -> {
            int selectedIndex = list.getSelectedIndex();
            if (selectedIndex != -1) {
                ProductType selectedType = model.get(selectedIndex);
                String novoNome = JOptionPane.showInputDialog(modal, "Editar:", selectedType.getName());
                if (novoNome != null && !novoNome.trim().isEmpty()) {
                    editarTipoProduto(selectedType.getId(), novoNome, model, selectedIndex);
                }
            }
        });

        removerButton.addActionListener(e -> {
            int selectedIndex = list.getSelectedIndex();
            if (selectedIndex != -1) {
                ProductType selectedType = model.get(selectedIndex);
                removerTipoProduto(selectedType.getId(), model, selectedIndex);
            }
        });

        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedIndex = list.getSelectedIndex();
                    if (selectedIndex != -1) {
                        ProductType selectedType = model.get(selectedIndex);
                        String novoNome = JOptionPane.showInputDialog(modal, "Editar:", selectedType.getName());
                        if (novoNome != null && !novoNome.trim().isEmpty()) {
                            editarTipoProduto(selectedType.getId(), novoNome, model, selectedIndex);
                        }
                    }
                }
            }
        });

        modal.add(topPanel, BorderLayout.NORTH);
        modal.add(scrollPane, BorderLayout.CENTER);
        modal.add(bottomPanel, BorderLayout.SOUTH);
        modal.setLocationRelativeTo(parent);
        modal.setVisible(true);
    }

    private void carregarTiposProduto(DefaultListModel<ProductType> model) {
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

                Gson gson = new Gson();
                JsonArray productType = JsonParser.parseString(response.toString()).getAsJsonArray();

                for (JsonElement element : productType) {
                    JsonObject type = element.getAsJsonObject();
                    String id = type.get("id").getAsString();
                    String name = type.get("name").getAsString();

                    model.addElement(new ProductType(id, name));
                }

            } else {
                JOptionPane.showMessageDialog(framePrincipal, "Erro ao carregar os Tipos de Produto: " + responseCode);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(framePrincipal, e.getMessage());
        }
    }

    private void adicionarTipoProduto(String nome, DefaultListModel<ProductType> model) {
        try {
            // creating the json to send
            JsonObject jsonData = new JsonObject();
            jsonData.addProperty("name", nome);

            // making the request
            String urlAPI = this.dotenv.get("API_HOST");
            URL url = new URL(urlAPI + "/product_type");
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
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                }

                // Convertendo a resposta JSON para obter o ID
                JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
                String id = jsonResponse.get("id").getAsString(); // Supondo que a API retorna { "id": "123", "name": "Novo Produto" }

                // Criando o objeto ProductType e adicionando à lista
                ProductType novoTipo = new ProductType(id, nome);
                model.addElement(novoTipo);

                this.getProductType();

                JOptionPane.showOptionDialog(this.framePrincipal,
                        "O tipo de Produto foi cadastrado com sucesso!",
                        "Tipo de Produto Cadastrado",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null, null, null);

                connection.disconnect();
            } else {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream(), "utf-8"))) {
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                }
                connection.disconnect();
                JsonObject err = JsonParser.parseString(response.toString()).getAsJsonObject();
                JOptionPane.showOptionDialog(this.framePrincipal,
                        "Não foi possível cadastrar o tipo de produto, verifique as informações dos campos!\n" + err.get("message").toString(),
                        "Tipo de Produto Não Cadastrado",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null, null, null);
            }


        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.framePrincipal, e.getMessage());
        }
    }

    private void adicionarTipoProdutoComboBox(String nome){
            try {
                JsonObject jsonData = new JsonObject();
                jsonData.addProperty("name", nome);

                String urlAPI = this.dotenv.get("API_HOST");
                URL url = new URL(urlAPI + "/product_type");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

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

                    this.getProductType();

                    JOptionPane.showOptionDialog(this.framePrincipal,
                            "O tipo de Produto foi cadastrado com sucesso!",
                            "Tipo de Produto Cadastrado",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.INFORMATION_MESSAGE,
                            null, null, null);

                    connection.disconnect();
                } else {
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream(), "utf-8"))) {
                        String responseLine;
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                    }
                    connection.disconnect();
                    JsonObject err = JsonParser.parseString(response.toString()).getAsJsonObject();
                    JOptionPane.showOptionDialog(this.framePrincipal,
                            "Não foi possível cadastrar o tipo de produto, verifique as informações dos campos!\n" + err.get("message").toString(),
                            "Tipo de Produto Não Cadastrado",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.ERROR_MESSAGE,
                            null, null, null);
                }


            } catch (Exception e) {
                JOptionPane.showMessageDialog(this.framePrincipal, e.getMessage());
            }
    }

    private void editarTipoProduto(String id, String novoNome, DefaultListModel<ProductType> model, int index) {
        try {
            // creating the json to send
            JsonObject jsonData = new JsonObject();
            jsonData.addProperty("name", novoNome);

            // making the request
            String urlAPI = this.dotenv.get("API_HOST");
            URL url = new URL(urlAPI + "/product_type/" + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // send the request with json
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonData.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int statusCode = connection.getResponseCode();
            StringBuilder response = new StringBuilder();

            if (statusCode >= 200 && statusCode <= 300) {

                ProductType prod = model.getElementAt(index);
                prod.setName(novoNome);
                model.setElementAt(prod, index);

                this.getProductType();

                JOptionPane.showOptionDialog(this.framePrincipal,
                        "O tipo de produto foi atualizado com sucesso!",
                        "Tipo de Produto Atualizado",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null, null, null);

                connection.disconnect();

            } else {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream(), "utf-8"))) {
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                }
                JsonObject err = JsonParser.parseString(response.toString()).getAsJsonObject();
                JOptionPane.showOptionDialog(this.framePrincipal,
                        "Não foi possível atualizar o tipo de produto, verifique as informações e tente novamente!\n" + err.get("message").toString(),
                        "Tipo de Produto Não Atualizado",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null, null, null);
                connection.disconnect();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.framePrincipal, e.getMessage());
        }
    }

    private void removerTipoProduto(String id, DefaultListModel<ProductType> model, int index) {
        try {
            URL url = new URL(this.dotenv.get("API_HOST") + "/product_type/" + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("DELETE");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_NO_CONTENT || responseCode == HttpURLConnection.HTTP_OK) {
                model.removeElementAt(index);
                this.getProductType();
                JOptionPane.showOptionDialog(this.frame,
                        "O tipo de produto foi deletado com sucesso",
                        "Tipo de Produto Deletado",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        null,
                        null);
            } else {
                JOptionPane.showOptionDialog(this.frame,
                        "Ocorreu um erro no servidor ao deletar o tipo de produto",
                        "Erro ao deletar o tipo de produto produto",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,
                        null,
                        null);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.frame, e.getMessage());
        }

    }

    private void getSupplier() {
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

                Gson gson = new Gson();
                JsonArray suppliers = JsonParser.parseString(response.toString()).getAsJsonArray();

                DefaultComboBoxModel<Supplier> model = new DefaultComboBoxModel<>();

                for (JsonElement element : suppliers) {
                    JsonObject supplier = element.getAsJsonObject();
                    String id = supplier.get("id").getAsString();
                    String name = supplier.get("name").getAsString();

                    model.addElement(new Supplier(id, name));
                }

                jComboBoxSupplier.setModel(model);

            } else {
                JOptionPane.showMessageDialog(framePrincipal, "Erro ao carregar fornecedores: " + responseCode);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(framePrincipal, e.getMessage());
        }
    }

    private void getProductType() {
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

                Gson gson = new Gson();
                JsonArray productType = JsonParser.parseString(response.toString()).getAsJsonArray();

                DefaultComboBoxModel<ProductType> model = new DefaultComboBoxModel<>();

                for (JsonElement element : productType) {
                    JsonObject type = element.getAsJsonObject();
                    String id = type.get("id").getAsString();
                    String name = type.get("name").getAsString();

                    model.addElement(new ProductType(id, name));
                }
                ProductType outros = new ProductType("0", "Outros");
                model.addElement(outros);

                this.jComboBoxProductType.setModel(model);

            } else {
                JOptionPane.showMessageDialog(framePrincipal, "Erro ao carregar os Tipos de Produto: " + responseCode);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(framePrincipal, e.getMessage());
        }

    }

    private void getProduct() {
        try {
            // making the request
            String urlAPI = this.dotenv.get("API_HOST");
            URL url = new URL(urlAPI + "/product/" + new BigInteger(this.id));
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
                JsonObject prod = JsonParser.parseString(response.toString()).getAsJsonObject();

                String name = prod.get("name").getAsString();
                int quantity = prod.get("quantity").getAsInt();
                Double price = prod.get("price").getAsDouble();
                String description = prod.get("description").getAsString();

                JsonObject supplier = prod.get("supplier").getAsJsonObject();
                Supplier sup = new Supplier(
                        supplier.get("id").getAsString(),
                        supplier.get("name").getAsString()
                );

                JsonObject prodType = prod.get("productType").getAsJsonObject();
                ProductType productType = new ProductType(
                        prodType.get("id").getAsString(),
                        prodType.get("name").getAsString()
                );

                jTextFieldNome.setText(name);
                jTextFieldDescricao.setText(description);
                jSpinnerPrecoVenda.setValue(price);
                jSpinnerQuantidade.setValue(quantity);

                for (int i = 0; i < jComboBoxSupplier.getItemCount(); i++) {
                    Supplier sup_in = jComboBoxSupplier.getItemAt(i);
                    if (sup_in.getId().equals(sup.getId())) {
                        jComboBoxSupplier.setSelectedIndex(i);
                    }
                }

                for (int i = 0; i < jComboBoxProductType.getItemCount(); i++) {
                    ProductType prodType_in = jComboBoxProductType.getItemAt(i);
                    if (prodType_in.getId().contains(productType.getId())) {
                        jComboBoxProductType.setSelectedIndex(i);
                    }
                }

                connection.disconnect();

            } else {
                JOptionPane.showMessageDialog(framePrincipal, "Erro ao carregar o produto: " + responseCode);
                connection.disconnect();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(framePrincipal, e.getMessage());
        }
    }

    private void cadastrarProduto() {
        try {
            String name = jTextFieldNome.getText();
            Integer quantity = (Integer) jSpinnerQuantidade.getValue();
            Double price = (Double) jSpinnerPrecoVenda.getValue();
            String description = jTextFieldDescricao.getText();
            Supplier id_supplier = (Supplier) Objects.requireNonNull(jComboBoxSupplier.getSelectedItem());
            ProductType id_product_type = (ProductType) Objects.requireNonNull(jComboBoxProductType.getSelectedItem());


            JsonObject jsonData = new JsonObject();
            jsonData.addProperty("name", name);
            jsonData.addProperty("quantity", quantity);
            jsonData.addProperty("price", price);
            jsonData.addProperty("description", description);
            jsonData.addProperty("id_supplier", id_supplier.getId());
            jsonData.addProperty("id_product_type", id_product_type.getId());

            // making the request
            String urlAPI = this.dotenv.get("API_HOST");
            URL url = new URL(urlAPI + "/product");
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
                this.reset();
                this.janelaPrincipal.showCard("listar_produtos");
                JOptionPane.showMessageDialog(this.framePrincipal,
                        "O Produto foi cadastrado com sucesso!",
                        "Produto Cadastrado",
                        JOptionPane.INFORMATION_MESSAGE);
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

                    JOptionPane.showMessageDialog(this.framePrincipal,
                            "Não foi possível cadastrar o produto. Verifique os dados e tente novamente!\n" + errorMessage,
                            "Erro no Cadastro",
                            JOptionPane.ERROR_MESSAGE);
                } catch (Exception jsonException) {
                    JOptionPane.showMessageDialog(this.framePrincipal,
                            "Erro inesperado ao processar a resposta da API.\nCódigo: " + statusCode + "\nResposta: " + response,
                            "Erro no Cadastro",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.framePrincipal, e.getMessage());
        }
    }

    private void editarProduto() {
        try {
            String name = jTextFieldNome.getText();
            Integer quantity = (Integer) jSpinnerQuantidade.getValue();
            Double price = (Double) jSpinnerPrecoVenda.getValue();
            String description = jTextFieldDescricao.getText();
            Supplier id_supplier = (Supplier) Objects.requireNonNull(jComboBoxSupplier.getSelectedItem());
            ProductType id_product_type = (ProductType) Objects.requireNonNull(jComboBoxProductType.getSelectedItem());

            JsonObject jsonData = new JsonObject();
            jsonData.addProperty("name", name);
            jsonData.addProperty("quantity", quantity);
            jsonData.addProperty("price", price);
            jsonData.addProperty("description", description);
            jsonData.addProperty("id_supplier", id_supplier.getId());
            jsonData.addProperty("id_product_type", id_product_type.getId());

            // making the request
            String urlAPI = this.dotenv.get("API_HOST");
            URL url = new URL(urlAPI + "/product/" + this.id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
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
                this.reset();
                this.janelaPrincipal.showCard("listar_produtos");
                JOptionPane.showMessageDialog(this.framePrincipal,
                        "O Produto foi atualizado com sucesso!",
                        "Produto Atualizado",
                        JOptionPane.INFORMATION_MESSAGE);
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

                    JOptionPane.showMessageDialog(this.framePrincipal,
                            "Não foi possível cadastrar o produto. Verifique os dados e tente novamente!\n" + errorMessage,
                            "Erro na Atualização",
                            JOptionPane.ERROR_MESSAGE);
                } catch (Exception jsonException) {
                    JOptionPane.showMessageDialog(this.framePrincipal,
                            "Erro inesperado ao processar a resposta da API.\nCódigo: " + statusCode + "\nResposta: " + response,
                            "Erro na Atualização",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.framePrincipal, e.getMessage());
        }
    }

    public void atualizarDados(){
        this.getSupplier();
        this.getProductType();
    }

    // componentes que vão ser usados na tela, só o essencial e com os nomes certinhos
    private JLabel jLabelCadastro;
    private JLabel jLabelNome;
    private JLabel jLabelFornecedor;
    private JLabel jLabelPrecoVenda;
    private JLabel jLabelDescricao;
    private JLabel jLabelQuantidade;
    private JLabel jLabelTipoProduto;

    private JComboBox<Supplier> jComboBoxSupplier;
    private JComboBox<ProductType> jComboBoxProductType;

    private JSpinner jSpinnerQuantidade;
    private JSpinner jSpinnerPrecoVenda;

    private JTextField jTextFieldNome;
    private JTextField jTextFieldDescricao;

    private JButton jButtonCancelar;
    private JButton jButtonCadastrar;
    private JButton jButtonVisualizarProduto;

    private JPanel jPanelHeader;
    private JPanel jPanelButtons;
    private JPanel jPanelContent;
    private JPanel jPanel;

    private JFrame frame;

    private GridBagConstraints gbc;

    private JSeparator jSeparator;
}

