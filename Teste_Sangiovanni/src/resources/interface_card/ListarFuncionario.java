package resources.interface_card;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.toedter.calendar.JDateChooser;
import io.github.cdimascio.dotenv.Dotenv;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class ListarFuncionario extends JPanel {
    private JRootPane rootPane;
    private JPanel mainPanel;
    private Dotenv dotenv;
    private CadastrarFuncionario card;

    public ListarFuncionario(JRootPane rootPane,JPanel mainPanel,CadastrarFuncionario cardFuncionario){
      this.rootPane = rootPane;
      this.mainPanel = mainPanel;
      this.dotenv = Dotenv.load();
      this.card = cardFuncionario;
      this.initComponents();
      this.getFuncionarios();
    }

    private void initComponents(){
        // iniciar Componentes
        jPanelTopoTabela = new javax.swing.JPanel();
        jPanelTabela = new javax.swing.JPanel();
        jButtonCadastrar = new javax.swing.JButton();
        jButtonFiltrar = new javax.swing.JButton();
        pesquisaProduto = new javax.swing.JTextField();
        jtable = new javax.swing.JTable();
        jScrollPane = new javax.swing.JScrollPane();
        jPanel4 = new JPanel();

        setBackground(new java.awt.Color(243, 243, 223));
        setPreferredSize(new Dimension(1366, 650));


        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new Dimension(1200, 600));

        jPanelTopoTabela.setPreferredSize(new Dimension(1200, 100));
        jPanelTopoTabela.setBackground(new java.awt.Color(255, 255, 255));
        jPanelTopoTabela.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 30));

        pesquisaProduto.setBackground(new java.awt.Color(240, 240, 240));
        pesquisaProduto.setFont(new Font("Cormorant Garamond", Font.BOLD, 14));
        pesquisaProduto.setText("Pesquisar Funcionário");
        pesquisaProduto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pesquisaProduto.setBorder(null);
        pesquisaProduto.setToolTipText("");
        pesquisaProduto.setPreferredSize(new java.awt.Dimension(200, 40));
        pesquisaProduto.addFocusListener(new java.awt.event.FocusListener() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (pesquisaProduto.getText().equals("Pesquisar Funcionário")) {
                    pesquisaProduto.setText("");
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (pesquisaProduto.getText().isEmpty()) {
                    pesquisaProduto.setText("Pesquisar Funcionário");
                }
            }
        });
        jPanelTopoTabela.add(pesquisaProduto);

        jPanelTopoTabela.add(Box.createHorizontalStrut(500));

        jButtonCadastrar.setFont(new java.awt.Font("Cormorant Garamond", Font.BOLD, 20));
        jButtonCadastrar.setIcon(new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/images/criar.png"))));
        jButtonCadastrar.setText("Cadastrar Funcionário");
        jButtonCadastrar.setBorder(null);
        jButtonCadastrar.setContentAreaFilled(false);
        jButtonCadastrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonCadastrar.setPreferredSize(new java.awt.Dimension(250, 40));
        jButtonCadastrar.addActionListener(e -> {
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            cl.show(mainPanel, "cadastrar_funcionario");
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
            this.filtro();
        });
        jPanelTopoTabela.add(jButtonFiltrar);

        jPanelTabela.setPreferredSize(new Dimension(1145, 450));
        jPanelTabela.setBackground(new java.awt.Color(255, 255, 255));
        jPanelTabela.setLayout(new BorderLayout());

        jtable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                        "Código", "Nome", "CPF", "Endereço", "Telefone", "E-mail", "Data de Nascimento", "Ações"
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

        jtable.getColumn("Ações").setCellRenderer(new ButtonRendererEmployee());
        jtable.getColumn("Ações").setCellEditor(new ButtonEditorEmployee(jtable, rootPane,this.dotenv.get("API_HOST"),card,this.mainPanel));

        jScrollPane.setViewportView(jtable);
        jScrollPane.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(128, 0, 32)));
        jScrollPane.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(128, 0, 32)));

        jPanelTabela.add(jScrollPane, BorderLayout.CENTER);

        jPanel4.add(jPanelTopoTabela);
        jPanel4.add(jPanelTabela);
        setBorder(new EmptyBorder(20, 20, 20, 20));
        add(jPanel4);
    }

    private void getFuncionarios(){
        try {
            String urlAPI = this.dotenv.get("API_HOST");
            URL url = new URL(urlAPI + "/employee");
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
                    String id = product.get("id").getAsString();
                    String name = product.get("name").getAsString();
                    String cpf = product.get("cpf").getAsString();
                    String address = product.get("address").getAsString();
                    String phone_number = product.get("phoneNumber").getAsString();
                    String email = product.get("email").getAsString();
                    String data = product.get("dateBirth").getAsString();
                    tableModel.addRow(new Object[]{id, name, cpf, address, phone_number, email, data});
                    connection.disconnect();
                }
            } else {
                JOptionPane.showOptionDialog(rootPane,
                        "Ocorreu um erro ao carregar os funcionários.",
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

    private void getFuncionarioByAddress(String endereco){
        try {
            String urlAPI = this.dotenv.get("API_HOST");
            URL url = new URL(urlAPI + "/employee/address/"+endereco);
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
                    String id = product.get("id").getAsString();
                    String name = product.get("name").getAsString();
                    String cpf = product.get("cpf").getAsString();
                    String address = product.get("address").getAsString();
                    String phone_number = product.get("phoneNumber").getAsString();
                    String email = product.get("email").getAsString();
                    String data = product.get("dateBirth").getAsString();
                    tableModel.addRow(new Object[]{id, name, cpf, address, phone_number, email, data});
                    connection.disconnect();
                }
            } else {
                JOptionPane.showOptionDialog(rootPane,
                        "Ocorreu um erro ao carregar os funcionários.",
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

    private void getFuncionariosByDate(String dateIn,String dateOut){
        try {
            String urlAPI = this.dotenv.get("API_HOST");
            String urlString = urlAPI + "/employee/date?start_date=" + URLEncoder.encode(dateIn, StandardCharsets.UTF_8) +
                    "&end_date=" + URLEncoder.encode(dateOut, StandardCharsets.UTF_8);
            URL url = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            int statusCode = connection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {
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
                    String id = product.get("id").getAsString();
                    String name = product.get("name").getAsString();
                    String cpf = product.get("cpf").getAsString();
                    String address = product.get("address").getAsString();
                    String phone_number = product.get("phoneNumber").getAsString();
                    String email = product.get("email").getAsString();
                    String data = product.get("dateBirth").getAsString();
                    tableModel.addRow(new Object[]{id, name, cpf, address, phone_number, email, data});
                }
                connection.disconnect();
            } else {
                StringBuilder response = new StringBuilder();
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

                    JOptionPane.showMessageDialog(this.rootPane,
                            "Não foi possível encontrar o funcionário. Verifique os dados e tente novamente!\n" + errorMessage,
                            "Erro na Pesquisa",
                            JOptionPane.ERROR_MESSAGE);
                } catch (Exception jsonException) {
                    JOptionPane.showMessageDialog(this.rootPane,
                            "Erro inesperado ao processar a resposta da API.\nCódigo: " + statusCode + "\nResposta: " + response,
                            "Erro na Pesquisa",
                            JOptionPane.ERROR_MESSAGE);
                }
                JOptionPane.showOptionDialog(rootPane,
                        "Ocorreu um erro ao carregar os funcionários.",
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

    private void getFuncionariosByDateAddress(String endereco, String dateIn, String dateOut){
        try {
            String urlAPI = this.dotenv.get("API_HOST");
            String urlString = urlAPI + "/employee/searchByAeD?start_date=" + URLEncoder.encode(dateIn, StandardCharsets.UTF_8) +
                    "&end_date=" + URLEncoder.encode(dateOut, StandardCharsets.UTF_8)+"&addressTerm="+URLEncoder.encode(endereco, StandardCharsets.UTF_8);
            URL url = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            int statusCode = connection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {
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
                    String id = product.get("id").getAsString();
                    String name = product.get("name").getAsString();
                    String cpf = product.get("cpf").getAsString();
                    String address = product.get("address").getAsString();
                    String phone_number = product.get("phoneNumber").getAsString();
                    String email = product.get("email").getAsString();
                    String data = product.get("dateBirth").getAsString();
                    tableModel.addRow(new Object[]{id, name, cpf, address, phone_number, email, data});
                }
                connection.disconnect();
            } else {
                StringBuilder response = new StringBuilder();
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

                    JOptionPane.showMessageDialog(this.rootPane,
                            "Não foi possível encontrar o funcionário. Verifique os dados e tente novamente!\n" + errorMessage,
                            "Erro na Pesquisa",
                            JOptionPane.ERROR_MESSAGE);
                } catch (Exception jsonException) {
                    JOptionPane.showMessageDialog(this.rootPane,
                            "Erro inesperado ao processar a resposta da API.\nCódigo: " + statusCode + "\nResposta: " + response,
                            "Erro na Pesquisa",
                            JOptionPane.ERROR_MESSAGE);
                }
                JOptionPane.showOptionDialog(rootPane,
                        "Ocorreu um erro ao carregar os funcionários.",
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

    public void atualizarDados(){
        this.getFuncionarios();
    }

    private void filtro(){
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(rootPane);

        JDialog dialog = new JDialog(frame, "Filtros", true);
        dialog.setSize(250, 300);
        dialog.setLayout(new GridBagLayout());
        dialog.setLocationRelativeTo(jButtonFiltrar);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JCheckBox chkDataNascimento = new JCheckBox("Data de Nascimento:");
        chkDataNascimento.setForeground(Color.BLACK);
        chkDataNascimento.setFont(new Font("Cormorant Garamond",Font.BOLD,14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        dialog.add(chkDataNascimento, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        JLabel de = new JLabel("de");
        de.setFont(new Font("Cormorant Garamond",Font.BOLD,14));
        de.setForeground(Color.BLACK);
        dialog.add(de, gbc);
        gbc.gridx = 1;
        JDateChooser dateChooserDe = new JDateChooser();
        dateChooserDe.setDateFormatString("dd/MM/yyyy");
        dateChooserDe.setFont(new Font("Cormorant Infant",Font.BOLD,14));
        dateChooserDe.setForeground(Color.BLACK);
        dialog.add(dateChooserDe, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel ate = new JLabel("de");
        ate.setFont(new Font("Cormorant Garamond",Font.BOLD,14));
        ate.setForeground(Color.BLACK);
        dialog.add(ate, gbc);
        gbc.gridx = 1;
        JDateChooser dateChooserAte = new JDateChooser();
        dateChooserAte.setDateFormatString("dd/MM/yyyy");
        dateChooserAte.setFont(new Font("Cormorant Infant",Font.BOLD,14));
        dateChooserAte.setForeground(Color.BLACK);
        dialog.add(dateChooserAte, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JCheckBox chkEndereco = new JCheckBox("Endereço");
        chkEndereco.setFont(new Font("Cormorant Garamond",Font.BOLD,14));
        chkEndereco.setForeground(Color.BLACK);
        dialog.add(chkEndereco, gbc);

        gbc.gridy = 4;
        JTextField txtEndereco = new JTextField(15);
        txtEndereco.setFont(new Font("Cormorant Infant",Font.BOLD,14));
        txtEndereco.setForeground(Color.BLACK);
        dialog.add(txtEndereco, gbc);

        gbc.gridy = 5;
        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.setBackground(new Color(0, 0, 139));
        btnFiltrar.setFont(new Font("Cormorant Garamond",Font.BOLD,16));
        btnFiltrar.setForeground(Color.WHITE);
        btnFiltrar.setFocusPainted(false);
        btnFiltrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        dialog.add(btnFiltrar, gbc);

        btnFiltrar.addActionListener(e -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String dataDe = dateChooserDe.getDate() != null ? sdf.format(dateChooserDe.getDate()) : "Não selecionado";
            String dataAte = dateChooserAte.getDate() != null ? sdf.format(dateChooserAte.getDate()) : "Não selecionado";
            String endereco = txtEndereco.getText().trim().isEmpty() ? "Não informado" : txtEndereco.getText();

            if(chkEndereco.isSelected()){
                getFuncionarioByAddress(endereco);
                dialog.setVisible(false);
            } else if (chkDataNascimento.isSelected()) {
                getFuncionariosByDate(dataDe, dataAte);
                dialog.setVisible(false);
            } else if (chkDataNascimento.isSelected() && chkEndereco.isSelected()) {
                getFuncionariosByDateAddress(endereco,dataDe,dataAte);
                dialog.setVisible(false);
            }else {
                JOptionPane.showMessageDialog(frame,"Selecione uma opção de Filtro!","Erro na pesquisa",JOptionPane.ERROR_MESSAGE);
            }

        });

        dialog.setVisible(true);
        frame.setVisible(true);
    }

    private javax.swing.JTextField pesquisaProduto;
    private javax.swing.JTable jtable;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JPanel jPanelTopoTabela;
    private javax.swing.JPanel jPanelTabela;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JButton jButtonCadastrar;
    private javax.swing.JButton jButtonFiltrar;
}

class ButtonRendererEmployee extends JPanel implements TableCellRenderer {
    private final JButton editButton = new JButton();
    private final JButton deleteButton = new JButton();

    public ButtonRendererEmployee() {
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

class ButtonEditorEmployee extends AbstractCellEditor implements TableCellEditor {
    private final JPanel panel = new JPanel();
    private final JButton editButton = new JButton();
    private final JButton deleteButton = new JButton();
    private final JTable table;
    private int currentRow;
    private final JFrame frame;
    private final String APIURL;

    public ButtonEditorEmployee(JTable table,JRootPane rootPane,String APIURL,CadastrarFuncionario cardFuncionario,JPanel mainPanel) {
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

        // Action for edit button
        editButton.addActionListener(evt -> {
            int cellIndex = table.getSelectedRow();
            Object cellValue = table.getValueAt(cellIndex,0);
            cardFuncionario.setId(cellValue.toString());
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            cl.show(mainPanel, "cadastrar_funcionario");
            stopCellEditing();
        });

        // Action for delete button
        deleteButton.addActionListener(e -> {
            String[] options = {"Cancelar","Excluir"};
            int confirmation = JOptionPane.showOptionDialog(table,
                    "Deseja excluir o funcionário? Essa ação não poderá ser desfeita. ",
                    "Deletar Funcionário",
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
                    String id_funcionario = cellValue.toString();
                    URL url = new URL(this.APIURL + "/employee/" + id_funcionario);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    connection.setRequestMethod("DELETE");

                    int responseCode = connection.getResponseCode();
                    if(responseCode==HttpURLConnection.HTTP_NO_CONTENT || responseCode == HttpURLConnection.HTTP_OK){
                        ((DefaultTableModel) table.getModel()).removeRow(currentRow);
                        JOptionPane.showOptionDialog(this.frame,
                                "O funcionário foi deletado com sucesso",
                                "Funcionário Deletado",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.INFORMATION_MESSAGE,
                                null,
                                null,
                                null);
                    }
                    else{
                        JOptionPane.showOptionDialog(this.frame,
                                "Ocorreu um erro no servidor ao deletar o funcionário",
                                "Erro ao deletar funcionário",
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