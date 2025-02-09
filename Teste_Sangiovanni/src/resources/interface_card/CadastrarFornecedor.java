package resources.interface_card;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.cdimascio.dotenv.Dotenv;
import resources.interfaces.ProductType;
import resources.interfaces.Supplier;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class CadastrarFornecedor extends JPanel {
    private JPanel mainPanel;
    private String id;
    private Dotenv dotenv;
    private JRootPane rootPane;
    private JanelaPrincipal frame;

    public CadastrarFornecedor(JPanel mainPanel,JRootPane rootPane) {
        this.mainPanel = mainPanel;
        this.rootPane = rootPane;
        this.frame = (JanelaPrincipal) SwingUtilities.getWindowAncestor(rootPane);
        this.dotenv = Dotenv.load();
        this.initComponentes();
    }

    private void initComponentes(){
        // inicialização de variáveis
        jLabelTelefone = new JLabel();
        jLabelCNPJ = new JLabel();
        jLabelEmail = new JLabel();
        jLabelObservacoes = new JLabel();
        jLabelCadastro = new JLabel();
        jLabelNome = new JLabel();
        jLabelEndereco = new JLabel();

        jTextFieldNome = new JTextField();
        jTextFieldObservacoes = new JTextField();
        jTextFieldEmail = new JTextField();
        try{
            jTextFieldTelefone = new javax.swing.JFormattedTextField(new MaskFormatter("(##) #####-####"));
            jTextFieldCPNJ = new javax.swing.JFormattedTextField(new MaskFormatter("##.###.###/####-##"));
            jTextFieldEndereco = new javax.swing.JFormattedTextField();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        jButtonCadastrar = new JButton();
        jButtonCancelar = new JButton();

        jPanelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        jPanelContent = new JPanel(new GridBagLayout());
        jPanelHeader = new JPanel();
        jPanel = new JPanel();

        gbc = new GridBagConstraints();

        jSeparator = new JSeparator();

        // configuração do card
        setBackground(new Color(243, 243, 223));
        setPreferredSize(new Dimension(1366, 650)); // tamanho do painel que tem tudo
        setLayout(new FlowLayout(FlowLayout.CENTER,30,30));

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
        jLabelCadastro.setText("Cadastrar Fornecedor");
        jLabelCadastro.setAlignmentX(Component.CENTER_ALIGNMENT); // centralizar

        jSeparator.setBackground(new Color(128, 0, 32));
        jSeparator.setForeground(new Color(128, 0, 32));
        jSeparator.setPreferredSize(new Dimension(200, 8));
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
        jTextFieldNome.setText("Nome do Fornecedor");
        jTextFieldNome.addFocusListener(new FocusAdapter() { // adicionar um evento ao clicar no campo
            @Override
            public void focusGained(FocusEvent e) { // clicou
                if (jTextFieldNome.getText().equals("Nome do Fornecedor")) {
                    jTextFieldNome.setText("");
                    jTextFieldNome.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) { // clicou em outra coisa
                if (jTextFieldNome.getText().isEmpty()) {
                    jTextFieldNome.setText("Nome do Fornecedor");
                    jTextFieldNome.setForeground(Color.BLACK);
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 100);
        jPanelContent.add(jTextFieldNome, gbc);

        jLabelTelefone.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelTelefone.setText("Telefone:");
        jLabelTelefone.setForeground(Color.BLACK);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        jPanelContent.add(jLabelTelefone, gbc);

        jTextFieldTelefone.setBackground(new Color(255, 255, 255));
        jTextFieldTelefone.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jTextFieldTelefone.setFont(new Font("Cormorant Infant", 1, 18));
        jTextFieldTelefone.setForeground(Color.BLACK);
        jTextFieldTelefone.setPreferredSize(fieldSize);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 0);
        jPanelContent.add(jTextFieldTelefone, gbc);

        jLabelCNPJ.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelCNPJ.setForeground(Color.BLACK);
        jLabelCNPJ.setText("CNPJ:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 0, 100);
        jPanelContent.add(jLabelCNPJ, gbc);

        jTextFieldCPNJ.setBackground(new Color(255, 255, 255));
        jTextFieldCPNJ.setBackground(Color.WHITE);
        jTextFieldCPNJ.setForeground(Color.BLACK);
        jTextFieldCPNJ.setFont(new Font("Cormorant Infant", Font.BOLD, 18));
        jTextFieldCPNJ.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jTextFieldCPNJ.setPreferredSize(fieldSize);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 20, 100);
        jPanelContent.add(jTextFieldCPNJ, gbc);

        jLabelEmail.setText("E-mail:");
        jLabelEmail.setForeground(Color.BLACK);
        jLabelEmail.setFont(new Font("Cormorant Garamond", 1, 18));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        jPanelContent.add(jLabelEmail, gbc);

        jTextFieldEmail.setBackground(Color.WHITE);
        jTextFieldEmail.setFont(new Font("Cormorant Garamond", 1, 18));
        jTextFieldEmail.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jTextFieldEmail.setPreferredSize(fieldSize);
        jTextFieldEmail.setForeground(Color.BLACK);
        jTextFieldEmail.setText("email@gmail.com");
        jTextFieldEmail.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (jTextFieldEmail.getText().equals("email@gmail.com")) {
                    jTextFieldEmail.setText("");
                    jTextFieldEmail.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (jTextFieldEmail.getText().isEmpty()) {
                    jTextFieldEmail.setText("email@gmail.com");
                    jTextFieldEmail.setForeground(Color.BLACK);
                }
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 20, 0);
        jPanelContent.add(jTextFieldEmail, gbc);

        jLabelEndereco.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelEndereco.setText("Endereço:");
        jLabelEndereco.setForeground(Color.BLACK);
        jLabelEndereco.setBackground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 0, 100);
        jPanelContent.add(jLabelEndereco, gbc);

        jTextFieldEndereco.setBackground(Color.WHITE);
        jTextFieldEndereco.setFont(new Font("Cormorant Infant", 1, 18));
        jTextFieldEndereco.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jTextFieldEndereco.setPreferredSize(fieldSize);
        jTextFieldEndereco.setForeground(Color.BLACK);
        jTextFieldEndereco.setText("Rua, Bairro, Número, Cidade-UF");
        jTextFieldEndereco.addFocusListener(new java.awt.event.FocusListener() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (jTextFieldEndereco.getText().equals("Rua, Bairro, Número, Cidade-UF")) {
                    jTextFieldEndereco.setText("");
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (jTextFieldEndereco.getText().isEmpty()) {
                    jTextFieldEndereco.setText("Rua, Bairro, Número, Cidade-UF");
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 20, 100);
        jPanelContent.add(jTextFieldEndereco, gbc);

        jLabelObservacoes.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelObservacoes.setText("Observações:");
        jLabelObservacoes.setForeground(Color.BLACK);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        jPanelContent.add(jLabelObservacoes, gbc);

        jTextFieldObservacoes.setBackground(new Color(255, 255, 255));
        jTextFieldObservacoes.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jTextFieldObservacoes.setPreferredSize(fieldSize);
        jTextFieldObservacoes.setForeground(Color.BLACK);
        jTextFieldObservacoes.setFont(new Font("Cormorant Garamond", 1, 18));
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 20, 0);
        jPanelContent.add(jTextFieldObservacoes, gbc);


        jButtonCancelar.setBackground(new Color(225, 225, 200));
        jButtonCancelar.setFont(new Font("Cormorant Garamond Bold", 1, 18));
        jButtonCancelar.setText("Cancelar");
        jButtonCancelar.setFocusPainted(false);
        jButtonCancelar.setBorder(new EmptyBorder(5,20,5,20));
        jButtonCancelar.addActionListener(e -> {
            this.reset();
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            cl.show(mainPanel, "listar_fonecedores");
        });

        jButtonCadastrar.setBackground(new Color(0, 128, 17));
        jButtonCadastrar.setFont(new Font("Cormorant Garamond Bold", 1
                , 18));
        jButtonCadastrar.setForeground(new Color(255, 255, 200));
        jButtonCadastrar.setText("Cadastrar");
        jButtonCadastrar.setFocusPainted(false);
        jButtonCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (id == null) {
                    cadastrarFornecedor();
                } else  {
                    editarFornecedor();
                }
            }
        });


        jPanel.add(jPanelContent);

        jPanelButtons.setBackground(new Color(255, 255, 255));
        jPanelButtons.setPreferredSize(new Dimension(1200, 50));
        jPanelButtons.add(jButtonCancelar);
        jPanelButtons.add(jButtonCadastrar);
        jPanelButtons.setLayout(new FlowLayout(FlowLayout.RIGHT,10,10));
        jPanelButtons.setBorder(new EmptyBorder(5,10,5,140));

        jPanel.add(jPanelButtons);

        add(jPanel);
    }

    private void getSupplier(){
        try {
            String urlAPI = this.dotenv.get("API_HOST");
            String encodedId = URLEncoder.encode(this.id, StandardCharsets.UTF_8.toString());
            URL url = new URL(urlAPI + "/supplier/"+this.id);
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
                JsonObject suppliers = JsonParser.parseString(response.toString()).getAsJsonObject();

                jTextFieldNome.setText(suppliers.get("name").getAsString());
                jTextFieldTelefone.setText(suppliers.get("phone_number").getAsString());
                jTextFieldEmail.setText(suppliers.get("email").getAsString());
                jTextFieldCPNJ.setText(suppliers.get("cnpj").getAsString());
                jTextFieldEndereco.setText(suppliers.get("address").getAsString());
                if(suppliers.has("observation") && !suppliers.get("observation").isJsonNull()){
                    jTextFieldObservacoes.setText(suppliers.get("observation").getAsString());
                }

                connection.disconnect();
            }
        }
        catch (Exception e) {
            JOptionPane.showOptionDialog(rootPane,
                    "Não foi possivel carregar os dados do fornecedor!\n"+e.getMessage(),
                    "Fornecedor Não Encontrado",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.ERROR_MESSAGE,
                    null,null,null);
        }
    }

    public void setId(String id) {
        this.id = id;
        this.getSupplier();
        this.jButtonCadastrar.setText("Atualizar");
        this.jLabelCadastro.setText("Editar Fornecedor");
    }

    public void reset(){
        jTextFieldNome.setText("Nome do Fornecedor");
        jTextFieldTelefone.setText(null);
        jTextFieldEmail.setText("email@gmail.com");
        jTextFieldCPNJ.setText(null);
        jTextFieldEndereco.setText("Rua - Bairro - Número - UF");
        jTextFieldObservacoes.setText(null);
        this.jButtonCadastrar.setText("Cadastrar");
        this.jLabelCadastro.setText("Cadastrar Fornecedor");
    }

    private void cadastrarFornecedor(){
        try {
            String name = jTextFieldNome.getText();
            String cnpj = jTextFieldCPNJ.getText();
            String email = jTextFieldEmail.getText();
            String observation = jTextFieldObservacoes.getText();
            String address = jTextFieldEndereco.getText();
            String phone_number = jTextFieldTelefone.getText();


            JsonObject jsonData = new JsonObject();
            jsonData.addProperty("name", name);
            jsonData.addProperty("cnpj", cnpj);
            jsonData.addProperty("email", email);
            jsonData.addProperty("observation", observation);
            jsonData.addProperty("address", address);
            jsonData.addProperty("phone_number", phone_number);

            // making the request
            String urlAPI = this.dotenv.get("API_HOST");
            URL url = new URL(urlAPI + "/supplier");
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
                this.frame.showCard("listar_fonecedores");
                JOptionPane.showOptionDialog(this.rootPane,
                        "O Fornecedor foi cadastrado com sucesso!",
                        "Fornecedor Cadastrado",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,null,null);
                connection.disconnect();


            } else {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream(), "utf-8"))) {
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                }
                connection.disconnect();
                JOptionPane.showOptionDialog(this.rootPane,
                        "Não foi possível cadastrar o fornecedor, verifique as informações dos campos e tente novamente!\n" + response.toString(),
                        "Fornecedor Não Cadastrado",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,null,null);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.rootPane, e.getMessage());
        }
    }

    private void editarFornecedor(){
        try {
            String name = jTextFieldNome.getText();
            String cnpj = jTextFieldCPNJ.getText();
            String email = jTextFieldEmail.getText();
            String observation = jTextFieldObservacoes.getText();
            String address = jTextFieldEndereco.getText();
            String phone_number = jTextFieldTelefone.getText();


            JsonObject jsonData = new JsonObject();
            jsonData.addProperty("name", name);
            jsonData.addProperty("cnpj", cnpj);
            jsonData.addProperty("email", email);
            jsonData.addProperty("observation", observation);
            jsonData.addProperty("address", address);
            jsonData.addProperty("phone_number", phone_number);

            // making the request
            String urlAPI = this.dotenv.get("API_HOST");
            URL url = new URL(urlAPI + "/supplier/"+this.id);
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
                this.id = null;
                this.frame.showCard("listar_fonecedores");
                JOptionPane.showOptionDialog(this.rootPane,
                        "O Fornecedor foi atualizado com sucesso!",
                        "Fornecedor Atualizado",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,null,null);
                connection.disconnect();


            } else {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream(), "utf-8"))) {
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                }
                connection.disconnect();
                JOptionPane.showOptionDialog(this.rootPane,
                        "Não foi possível atualizar o fornecedor, verifique as informações dos campos e tente novamente!\n" + response.toString(),
                        "Fornecedor Não Atualizado",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,null,null);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.rootPane, e.getMessage());
        }
    }

    // componentes que vão ser usados na tela, só o essencial e com os nomes certinhos
    private JLabel jLabelCadastro;
    private JLabel jLabelNome;
    private JLabel jLabelTelefone;
    private JLabel jLabelCNPJ;
    private JLabel jLabelEmail;
    private JLabel jLabelEndereco;
    private JLabel jLabelObservacoes;

    private JTextField jTextFieldNome;
    private JTextField jTextFieldTelefone;
    private JTextField jTextFieldCPNJ;
    private JTextField jTextFieldEmail;
    private JTextField jTextFieldEndereco;
    private JTextField jTextFieldObservacoes;

    private JButton jButtonCancelar;
    private JButton jButtonCadastrar;

    private JPanel jPanelHeader;
    private JPanel jPanelButtons;
    private JPanel jPanelContent;
    private JPanel jPanel;

    private GridBagConstraints gbc;

    private JSeparator jSeparator;
}
