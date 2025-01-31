package resources.interface_card;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import resources.interfaces.EditarProduto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import io.github.cdimascio.dotenv.Dotenv;

public class ListarFornecedor extends JPanel {
    private final Dotenv dotenv;

    public ListarFornecedor(JRootPane rootPane,JPanel mainPanel,CadastrarFornecedor cardCadastroEdicao){
        this.dotenv = Dotenv.load();

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
        jPanelTopoTabela.add(jButtonFiltrar);

        jPanelTabela.setPreferredSize(new Dimension(1145, 450));
        jPanelTabela.setBackground(new java.awt.Color(255, 255, 255));
        jPanelTabela.setLayout(new BorderLayout());

        jtable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                        "Código", "Nome", "E-mail", "CPNJ", "Observações", "Endereço","Telefone","Ações"
                }
        ));

        jtable.setIntercellSpacing(new Dimension(0, 0));
        jtable.setShowHorizontalLines(false);
        jtable.setShowVerticalLines(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        jtable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

        jtable.setFont(new java.awt.Font("Cormorant Garamond",Font.BOLD,14));
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
        jtable.getColumn("Ações").setCellEditor(new ButtonEditorSupplier(jtable, rootPane,this.dotenv.get("API_HOST"),mainPanel,cardCadastroEdicao));

        jScrollPane.setViewportView(jtable);
        jScrollPane.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(128, 0, 32)));
        jScrollPane.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(128, 0, 32)));

        jPanelTabela.add(jScrollPane, BorderLayout.CENTER);

        jPanel4.add(jPanelTopoTabela);
        jPanel4.add(jPanelTabela);
        setBorder(new EmptyBorder(20, 20, 20, 20));
        add(jPanel4);

        getSupplier(rootPane);
    }

    private void getSupplier(JRootPane rootPane) {
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
                    String observation = supplier.get("observation").getAsString();

                    tableModel.addRow(new Object[]{id, name, email, cnpj,observation, address, phone});
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

    // Declaração de variáveis
    private javax.swing.JButton jButtonCadastrar;
    private javax.swing.JButton jButtonFiltrar;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanelTopoTabela;
    private javax.swing.JPanel jPanelTabela;
    private javax.swing.JTextField pesquisaProduto;
    private javax.swing.JTable jtable;
    private javax.swing.JScrollPane jScrollPane;
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
            JFrame tela = new EditarProduto(cellValue.toString());
            cardF.setId(cellValue.toString());
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            cl.show(mainPanel, "cadastrar_fornecedor");
            stopCellEditing();
        });

        // Action for delete button
        deleteButton.addActionListener(e -> {
            String[] options = {"Cancelar","Excluir"};
            int confirmation = JOptionPane.showOptionDialog(table,
                    "Deseja excluir o produto? Essa ação não poderá ser desfeita. ",
                    "Deletar Produto",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);

            stopCellEditing();
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

