package resources.interface_card;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;
import io.github.cdimascio.dotenv.Dotenv;

public class ListarFornecedor extends JPanel {
    private final Dotenv dotenv;
    private final JRootPane rootPane;
    private final JPanel mainPanel;
    private final CadastrarFornecedor cadastrarFornecedor;

    public ListarFornecedor(JRootPane rootPane,JPanel mainPanel,CadastrarFornecedor cardCadastroEdicao){
       this.rootPane = rootPane;
       this.mainPanel = mainPanel;
       this.cadastrarFornecedor = cardCadastroEdicao;
       this.dotenv = Dotenv.load();

       initComponents();
       getSupplier();
    }

    private void getSupplier() {
        try {
            String urlAPI = this.dotenv.get("API_HOST");
            URL url = new URL(urlAPI + "/supplier");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            ArrayList<SupplierCount> counts = getSupplierCount();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JsonArray suppliers = JsonParser.parseString(response.toString()).getAsJsonArray();

                DefaultTableModel tableModel = (DefaultTableModel) jtable.getModel();
                tableModel.setRowCount(0);

                for (int i = 0; i < suppliers.size(); i++) {
                    JsonObject supplier = suppliers.get(i).getAsJsonObject();
                    String id = supplier.get("id").getAsString();
                    String name = supplier.get("name").getAsString();
                    String email = supplier.get("email").getAsString();
                    String cnpj = supplier.get("cnpj").getAsString();
                    String address = supplier.get("address").getAsString();
                    String phone = supplier.get("phoneNumber").getAsString();

                    Long count = 0L;
                    for (SupplierCount sc : counts) {
                        if (sc.getSupplierName().equals(name)) {
                            count = sc.getProductCount();
                            break;
                        }
                    }

                    tableModel.addRow(new Object[]{id, name, email, cnpj, address, phone, count});
                    connection.disconnect();
                }
            } else {
                JOptionPane.showOptionDialog(rootPane,
                        "Ocorreu um erro ao carregar os fornecedores",
                        "Problema no Servidor",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,null,null);
                connection.disconnect();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }

    private void initComponents() {

        // iniciando componentes
        jPanel4 = new javax.swing.JPanel();
        jPanelTopoTabela = new javax.swing.JPanel();
        jPanelTabela = new javax.swing.JPanel();
        jButtonCadastrar = new javax.swing.JButton();
        jButtonFiltrar = new javax.swing.JButton();
        pesquisaProduto = new javax.swing.JTextField();
        jtable = new javax.swing.JTable();
        jScrollPane = new javax.swing.JScrollPane();


        // configurações do card
        setBackground(new java.awt.Color(243, 243, 223));
        setPreferredSize(new Dimension(1366, 650));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new Dimension(1200, 600));

        jPanelTopoTabela.setPreferredSize(new Dimension(1200, 100));
        jPanelTopoTabela.setBackground(new java.awt.Color(255, 255, 255));
        jPanelTopoTabela.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 30));

        pesquisaProduto.setBackground(new java.awt.Color(240, 240, 240));
        pesquisaProduto.setFont(new Font("Cormorant Garamond", Font.BOLD, 14));
        pesquisaProduto.setText("Pesquisar Fornecedor");
        pesquisaProduto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pesquisaProduto.setBorder(null);
        pesquisaProduto.setToolTipText("");
        pesquisaProduto.setPreferredSize(new java.awt.Dimension(200, 40));
        pesquisaProduto.addFocusListener(new java.awt.event.FocusListener() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (pesquisaProduto.getText().equals("Pesquisar Fornecedor")) {
                    pesquisaProduto.setText("");
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (pesquisaProduto.getText().isEmpty()) {
                    pesquisaProduto.setText("Pesquisar Fornecedor");
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

        jPanelTopoTabela.add(Box.createHorizontalStrut(500));

        jButtonCadastrar.setFont(new java.awt.Font("Cormorant Garamond", Font.BOLD, 20));
        jButtonCadastrar.setIcon(new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/images/criar.png"))));
        jButtonCadastrar.setText("Cadastrar Fornecedor");
        jButtonCadastrar.setBorder(null);
        jButtonCadastrar.setContentAreaFilled(false);
        jButtonCadastrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonCadastrar.setPreferredSize(new java.awt.Dimension(230, 40));
        jButtonCadastrar.addActionListener(e -> {
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            cl.show(mainPanel, "cadastrar_fornecedor");
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
        jButtonFiltrar.addActionListener(evt->{
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(rootPane);
            abrirDialogoFiltro(frame);
        });
        jPanelTopoTabela.add(jButtonFiltrar);

        jPanelTabela.setPreferredSize(new Dimension(1145, 450));
        jPanelTabela.setBackground(new java.awt.Color(255, 255, 255));
        jPanelTabela.setLayout(new BorderLayout());

        jtable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                        "Código", "Nome", "E-mail", "CPNJ", "Endereço","Telefone","N° Produtos","Ações"
                }
        ));

        jtable.setIntercellSpacing(new Dimension(0, 0));
        jtable.setShowHorizontalLines(false);
        jtable.setShowVerticalLines(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        jtable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

        jtable.setFont(new java.awt.Font("Cormorant Infant",Font.BOLD,14));
        jtable.setShowGrid(false);
        jtable.setIntercellSpacing(new Dimension(0, 0));
        jtable.setRowHeight(30);
        jtable.setFocusable(false);

        JTableHeader header = jtable.getTableHeader();
        header.setFont(new java.awt.Font("Cormorant Garamond",Font.BOLD,18));
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

        jtable.getColumn("Ações").setCellRenderer(new ButtonRendererSupplier());
        jtable.getColumn("Ações").setCellEditor(new ButtonEditorSupplier(jtable, rootPane,this.dotenv.get("API_HOST"),mainPanel,cadastrarFornecedor));

        jScrollPane.setViewportView(jtable);
        jScrollPane.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(128, 0, 32)));
        jScrollPane.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(128, 0, 32)));

        jPanelTabela.add(jScrollPane, BorderLayout.CENTER);

        jPanel4.add(jPanelTopoTabela);
        jPanel4.add(jPanelTabela);
        setBorder(new EmptyBorder(20, 20, 20, 20));
        add(jPanel4);

    }

    public void atualizarDados(){
        this.getSupplier();
    }

    private ArrayList<SupplierCount> getSupplierCount(){
        ArrayList<SupplierCount> list = new ArrayList<SupplierCount>();
        try {
            String urlAPI = this.dotenv.get("API_HOST");
            String urlString = urlAPI + "/supplier/count";
            URL url = new URL(urlString);
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

                JsonArray suppliers = JsonParser.parseString(response.toString()).getAsJsonArray();

                for (int i = 0; i < suppliers.size(); i++) {
                    JsonObject supplier = suppliers.get(i).getAsJsonObject();
                    Long count = supplier.get("productCount").getAsLong();
                    String name = supplier.get("supplierName").getAsString();

                    list.add(new SupplierCount(name, count));

                }
                connection.disconnect();
                return list;
            } else {
                JOptionPane.showOptionDialog(rootPane,
                        "Ocorreu um erro ao carregar a quantidade de produto para cada fornecedor!",
                        "Problema no Servidor",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,null,null);
                connection.disconnect();
                return null;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
            return null;
        }
    }

    private void getSupplierByAddress(String adress){
        try {
            String urlAPI = this.dotenv.get("API_HOST");
            String urlString = urlAPI + "/supplier/address/?address=" + URLEncoder.encode(adress, StandardCharsets.UTF_8);
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            ArrayList<SupplierCount> counts = getSupplierCount();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JsonArray suppliers = JsonParser.parseString(response.toString()).getAsJsonArray();

                DefaultTableModel tableModel = (DefaultTableModel) jtable.getModel();
                tableModel.setRowCount(0);

                for (int i = 0; i < suppliers.size(); i++) {
                    JsonObject supplier = suppliers.get(i).getAsJsonObject();
                    String id = supplier.get("id").getAsString();
                    String name = supplier.get("name").getAsString();
                    String email = supplier.get("email").getAsString();
                    String cnpj = supplier.get("cnpj").getAsString();
                    String address = supplier.get("address").getAsString();
                    String phone = supplier.get("phone_number").getAsString();

                    Long count = 0L;
                    for (SupplierCount sc : counts) {
                        if (sc.getSupplierName().equals(name)) {
                            count = sc.getProductCount();
                            break;
                        }
                    }

                    tableModel.addRow(new Object[]{id, name, email, cnpj, address, phone,count});
                    connection.disconnect();
                }
            } else {
                JOptionPane.showOptionDialog(rootPane,
                        "Ocorreu um erro ao carregar os fornecedores",
                        "Problema no Servidor",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,null,null);
                connection.disconnect();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }

    private void getSupplierByQtdProd(String quantity){
        try {
            String urlAPI = this.dotenv.get("API_HOST");
            String urlString = urlAPI + "/supplier/countBigThan/?quantity=" + URLEncoder.encode(quantity, StandardCharsets.UTF_8);
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            ArrayList<SupplierCount> counts = getSupplierCount();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JsonArray suppliers = JsonParser.parseString(response.toString()).getAsJsonArray();

                DefaultTableModel tableModel = (DefaultTableModel) jtable.getModel();
                tableModel.setRowCount(0);

                for (int i = 0; i < suppliers.size(); i++) {
                    JsonObject supplier = suppliers.get(i).getAsJsonObject();
                    String id = supplier.get("supplierId").getAsString();
                    String name = supplier.get("supplierName").getAsString();
                    String email = supplier.get("email").getAsString();
                    String cnpj = supplier.get("cnpj").getAsString();
                    String address = supplier.get("address").getAsString();
                    String phone = supplier.get("phoneNumber").getAsString();
                    Long count = supplier.get("productCount").getAsLong();


                    tableModel.addRow(new Object[]{id, name, email, cnpj, address, phone,count});
                    connection.disconnect();
                }
            } else {
                JOptionPane.showOptionDialog(rootPane,
                        "Ocorreu um erro ao carregar os fornecedores",
                        "Problema no Servidor",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,null,null);
                connection.disconnect();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }

    private void abrirDialogoFiltro(JFrame parent) {
        JDialog dialog = new JDialog(parent, "Filtros", true);
        dialog.setSize(200, 250);
        dialog.setLayout(new GridBagLayout());
        dialog.setLocationRelativeTo(jButtonFiltrar);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JCheckBox chkAddress = new JCheckBox("Endereço");
        chkAddress.setFont(new Font("Cormorant Garamond",Font.BOLD,14));
        chkAddress.setForeground(Color.BLACK);
        dialog.add(chkAddress, gbc);

        gbc.gridy = 1;
        JTextField jTextFieldAdress = new JTextField();
        jTextFieldAdress.setFont(new Font("Cormorant Garamond",Font.BOLD,14));
        jTextFieldAdress.setForeground(Color.BLACK);
        dialog.add(jTextFieldAdress, gbc);

        gbc.gridy = 2;
        JCheckBox chkNProd = new JCheckBox("N° Produtos");
        chkNProd.setFont(new Font("Cormorant Garamond",Font.BOLD,14));
        chkNProd.setForeground(Color.BLACK);
        dialog.add(chkNProd, gbc);

        gbc.gridy = 3;
        JSpinner spnQuantidade = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
        spnQuantidade.setFont(new Font("Cormorant Infant",Font.BOLD,14));
        spnQuantidade.setForeground(Color.BLACK);
        dialog.add(spnQuantidade, gbc);

        gbc.gridy = 7;
        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.setBackground(new Color(0, 0, 139));
        btnFiltrar.setFont(new Font("Cormorant Garamond",Font.BOLD,16));
        btnFiltrar.setForeground(Color.WHITE);
        btnFiltrar.setFocusPainted(false);
        btnFiltrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        dialog.add(btnFiltrar, gbc);

        btnFiltrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(chkAddress.isSelected()) {
                    getSupplierByAddress(jTextFieldAdress.getText());
                    dialog.setVisible(false);
                } else if (chkNProd.isSelected()) {
                    getSupplierByQtdProd(spnQuantidade.getValue().toString());
                    dialog.setVisible(false);
                }
                else{
                    JOptionPane.showMessageDialog(parent,"Selecione uma opção de Filtro!","Erro na seleção de filtro",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        dialog.setVisible(true);
    }

    private void searchSupplier(String supId, String name,String email, String cnpj) {
        try {
            String urlAPI = this.dotenv.get("API_HOST");
            // Construindo a URL com os parâmetros de busca
            StringBuilder urlBuilder = new StringBuilder(urlAPI + "/supplier/search");
            urlBuilder.append("?");
            boolean hasParam = false;

            if (supId != null && !supId.isEmpty()) {
                if (hasParam) urlBuilder.append("&");
                urlBuilder.append("supplierId=").append(URLEncoder.encode(supId, "UTF-8"));
                hasParam = true;
            }

            if (name != null && !name.isEmpty()) {
                if (hasParam) urlBuilder.append("&");
                urlBuilder.append("name=").append(URLEncoder.encode(name, "UTF-8"));
                hasParam = true;
            }

            if (email != null && !email.isEmpty()) {
                if (hasParam) urlBuilder.append("&");
                urlBuilder.append("email=").append(URLEncoder.encode(email, "UTF-8"));
                hasParam = true;
            }

            if (cnpj != null && !cnpj.isEmpty()) {
                if (hasParam) urlBuilder.append("&");
                urlBuilder.append("cnpj=").append(URLEncoder.encode(cnpj, "UTF-8"));
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

                    JsonArray suppliers = JsonParser.parseString(response.toString()).getAsJsonArray();

                    DefaultTableModel tableModel = (DefaultTableModel) jtable.getModel();
                    tableModel.setRowCount(0);
                    ArrayList<SupplierCount> counts = getSupplierCount();

                    for (int i = 0; i < suppliers.size(); i++) {
                        JsonObject supplier = suppliers.get(i).getAsJsonObject();
                        String id = supplier.get("id").getAsString();
                        String nameS = supplier.get("name").getAsString();
                        String emailS = supplier.get("email").getAsString();
                        String cnpjS = supplier.get("cnpj").getAsString();
                        String address = supplier.get("address").getAsString();
                        String phone = supplier.get("phoneNumber").getAsString();

                        Long count = 0L;
                        for (SupplierCount sc : counts) {
                            if (sc.getSupplierName().equals(nameS)) {
                                count = sc.getProductCount();
                                break;
                            }
                        }

                        tableModel.addRow(new Object[]{id, nameS, emailS, cnpjS, address, phone, count});
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
        if (searchText.equals("Pesquisar Fornecedor") || searchText.isEmpty()) {
            getSupplier();
        } else {
            if(searchText.contains(".")){
                searchSupplier(null, null, null,searchText);
            }else if(searchText.contains("@")){
                searchSupplier(null, null, searchText,null);
            }else if(searchText.matches(".*\\d.*")){
                searchSupplier(searchText, null, null,null);
            }else{
                searchSupplier(null, searchText, null,null);
            }

        }
    }

    // Declaração de variáveis
    private javax.swing.JButton jButtonCadastrar;
    private javax.swing.JButton jButtonFiltrar;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanelTopoTabela;
    private javax.swing.JPanel jPanelTabela;
    private javax.swing.JTextField pesquisaProduto;
    private javax.swing.JTable jtable;
    private javax.swing.JScrollPane jScrollPane;
    private final Timer timer = new Timer(300, e -> filterSales());
}

class ButtonRendererSupplier extends JPanel implements TableCellRenderer {
    private final JButton editButton = new JButton();
    private final JButton deleteButton = new JButton();

    public ButtonRendererSupplier() {
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

class ButtonEditorSupplier extends AbstractCellEditor implements TableCellEditor {
    private final JPanel panel = new JPanel();
    private final JButton editButton = new JButton();
    private final JButton deleteButton = new JButton();
    private final JTable table;
    private int currentRow;
    private final JFrame frame;
    private final String APIURL;

    public ButtonEditorSupplier(JTable table,JRootPane rootPane,String APIURL,JPanel mainPanel,CadastrarFornecedor cardF) {
        this.table = table;
        this.frame = (JFrame) SwingUtilities.getWindowAncestor(rootPane);
        this.APIURL = APIURL;

        panel.setLayout(new

                FlowLayout(FlowLayout.CENTER, 5, 0));

        this.editButton.setIcon(new ImageIcon(Objects.requireNonNull(
                getClass().getResource("/resources/images/editar.png"))));
        this.editButton.setOpaque(true);
        this.editButton.setBackground(new Color(255, 255, 255));
        this.editButton.setForeground(new Color(128, 0, 32));
        this.editButton.setFocusPainted(false);
        this.editButton.setBorder(null);
        this.editButton.setContentAreaFilled(false);
        this.editButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        this.deleteButton.setIcon(new ImageIcon(Objects.requireNonNull(
                getClass().getResource("/resources/images/excluir.png"))));
        this.deleteButton.setBackground(new Color(255, 255, 255));
        this.deleteButton.setForeground(new Color(128, 0, 32));
        this.deleteButton.setFocusPainted(false);
        this.deleteButton.setBorder(null);
        this.deleteButton.setContentAreaFilled(false);
        this.deleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panel.add(editButton);
        panel.add(deleteButton);

        // Action for edit button
        editButton.addActionListener(evt -> {
            int cellIndex = table.getSelectedRow();
            Object cellValue = table.getValueAt(cellIndex,0);
            cardF.setId(cellValue.toString());
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            cl.show(mainPanel, "cadastrar_fornecedor");
            stopCellEditing();
        });

        // Action for delete button
        deleteButton.addActionListener(e -> {
            String[] options = {"Cancelar","Excluir"};
            int confirmation = JOptionPane.showOptionDialog(table,
                    "Deseja excluir o fornecedor? Essa ação não poderá ser desfeita. ",
                    "Deletar Fornecedor",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
            if (confirmation == 1) {
                try{
                    int cellIndex = table.getSelectedRow();
                    if (table.isEditing()) {
                        table.getCellEditor().stopCellEditing();
                    }
                    Object cellValue = table.getValueAt(cellIndex,0);
                    String id_product = cellValue.toString();
                    URL url = new URL(this.APIURL + "/supplier/" + id_product);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    connection.setRequestMethod("DELETE");

                    int responseCode = connection.getResponseCode();
                    if(responseCode==HttpURLConnection.HTTP_NO_CONTENT || responseCode == HttpURLConnection.HTTP_OK){
                        ((DefaultTableModel) table.getModel()).removeRow(currentRow);
                        JOptionPane.showOptionDialog(this.frame,
                                "O fornecedor foi deletado com sucesso",
                                "Fornecedor Deletado",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.INFORMATION_MESSAGE,
                                null,
                                null,
                                null);
                    }
                    else{
                        JOptionPane.showOptionDialog(this.frame,
                                "Ocorreu um erro no servidor ao deletar o fornecedor",
                                "Erro ao deletar fornecedor",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.ERROR_MESSAGE,
                                null,
                                null,
                                null);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this.frame,ex.getMessage());
                }
            }
            else{
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

