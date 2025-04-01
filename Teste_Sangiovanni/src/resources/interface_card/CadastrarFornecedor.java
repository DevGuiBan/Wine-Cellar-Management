package resources.interface_card;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.cdimascio.dotenv.Dotenv;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
        jLabelRua = new JLabel();
        jLabelBairro = new JLabel();
        jLabelNumero = new JLabel();
        jLabelCidade = new JLabel();
        jLabelUF = new JLabel();

        jTextFieldNome = new JTextField();
        jTextFieldObservacoes = new JTextField();
        jTextFieldEmail = new JTextField();
        jTextFieldRua = new JTextField();
        jTextFieldBairro = new JTextField();
        jTextFieldNumero = new JTextField();
        jTextFieldCidade = new JTextField();
        jComboBoxUF = new JComboBox<UF>();

        try{
            jTextFieldTelefone = new javax.swing.JFormattedTextField(new MaskFormatter("(##) #####-####"));
            jTextFieldCPNJ = new javax.swing.JFormattedTextField(new MaskFormatter("##.###.###/####-##"));
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

        this.jLabelNome.setFont(new Font("Cormorant Garamond", 1, 18));
        this.jLabelNome.setText("Nome:");
        this.jLabelNome.setForeground(Color.BLACK);
        this.gbc.gridx = 0; // coluna
        this.gbc.gridy = 0; // linha
        this.gbc.insets = new Insets(0, 0, 0, 100); // adicionar espaçamento por componente
        this.jPanelContent.add(this.jLabelNome, this.gbc); // sempre adicionar neste formato por conta do grid

        this.jTextFieldNome.setBackground(new Color(255, 255, 255));
        this.jTextFieldNome.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        this.jTextFieldNome.setFont(new Font("Cormorant Garamond", 1, 18));
        this.jTextFieldNome.setPreferredSize(fieldSize);
        this.jTextFieldNome.setForeground(Color.BLACK);
        this.jTextFieldNome.setText("");

        this.gbc.gridx = 0;
        this.gbc.gridy = 1;
        this.gbc.insets = new Insets(0, 0, 20, 100);
        this.jPanelContent.add(this.jTextFieldNome, this.gbc);

        this.jLabelTelefone.setFont(new Font("Cormorant Garamond", 1, 18));
        this.jLabelTelefone.setText("Telefone:");
        this.jLabelTelefone.setForeground(Color.BLACK);
        this.gbc.gridx = 0;
        this.gbc.gridy = 2;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        this.jPanelContent.add(this.jLabelTelefone, this.gbc);

        this.jTextFieldTelefone.setBackground(new Color(255, 255, 255));
        this.jTextFieldTelefone.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        this.jTextFieldTelefone.setFont(new Font("Cormorant Infant", 1, 18));
        this.jTextFieldTelefone.setForeground(Color.BLACK);
        this.jTextFieldTelefone.setPreferredSize(fieldSize);
        this.gbc.gridx = 0;
        this.gbc.gridy = 3;
        this.gbc.insets = new Insets(0, 0, 20, 100);
        this.jPanelContent.add(this.jTextFieldTelefone, this.gbc);

        this.jLabelCNPJ.setFont(new Font("Cormorant Garamond", 1, 18));
        this.jLabelCNPJ.setText("CNPJ:");
        this.jLabelCNPJ.setForeground(Color.BLACK);
        this.gbc.gridx = 0;
        this.gbc.gridy = 4;
        this.gbc.insets = new Insets(0, 0, 0, 100);
        this.jPanelContent.add(this.jLabelCNPJ, this.gbc);

        this.jTextFieldCPNJ.setBackground(new Color(255, 255, 255));
        this.jTextFieldCPNJ.setBackground(Color.WHITE);
        this.jTextFieldCPNJ.setForeground(Color.BLACK);
        this.jTextFieldCPNJ.setFont(new Font("Cormorant Infant", Font.BOLD, 18));
        this.jTextFieldCPNJ.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        this.jTextFieldCPNJ.setPreferredSize(fieldSize);
        this.gbc.gridx = 0;
        this.gbc.gridy = 5;
        this.gbc.insets = new Insets(0, 0, 20, 100);
        this.jPanelContent.add(this.jTextFieldCPNJ, this.gbc);

        this.jLabelEmail.setText("E-mail:");
        this.jLabelEmail.setFont(new Font("Cormorant Garamond", 1, 18));
        this.jLabelEmail.setForeground(Color.BLACK);
        this.gbc.gridx = 0;
        this.gbc.gridy = 6;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        this.jPanelContent.add(this.jLabelEmail, this.gbc);

        this.jTextFieldEmail.setBackground(Color.WHITE);
        this.jTextFieldEmail.setFont(new Font("Cormorant Garamond", 1, 18));
        this.jTextFieldEmail.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        this.jTextFieldEmail.setPreferredSize(fieldSize);
        this.jTextFieldEmail.setText("");
        this.jTextFieldEmail.setForeground(Color.BLACK);

        this.gbc.gridx = 0;
        this.gbc.gridy = 7;
        this.gbc.insets = new Insets(0, 0, 20, 100);
        this.jPanelContent.add(this.jTextFieldEmail, this.gbc);

        this.jLabelObservacoes.setFont(new Font("Cormorant Garamond", 1, 18));
        this.jLabelObservacoes.setText("Observações:");
        this.jLabelObservacoes.setForeground(Color.BLACK);
        this.gbc.gridx = 0;
        this.gbc.gridy = 8;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        this.jPanelContent.add(this.jLabelObservacoes, this.gbc);

        this.jTextFieldObservacoes.setBackground(new Color(255, 255, 255));
        this.jTextFieldObservacoes.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        this.jTextFieldObservacoes.setPreferredSize(fieldSize);
        this.jTextFieldObservacoes.setForeground(Color.BLACK);
        this.jTextFieldObservacoes.setFont(new Font("Cormorant Infant", 1, 18));
        this.gbc.gridx = 0;
        this.gbc.gridy = 9;
        this.gbc.insets = new Insets(0, 0, 20, 100);
        this.jPanelContent.add(this.jTextFieldObservacoes, this.gbc);

        jLabelRua.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelRua.setText("Rua:");
        jLabelRua.setForeground(Color.BLACK);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 100);
        jPanelContent.add(jLabelRua, gbc);

        jTextFieldRua.setBackground(Color.WHITE);
        jTextFieldRua.setFont(new Font("Cormorant Infant", 1, 18));
        jTextFieldRua.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jTextFieldRua.setPreferredSize(fieldSize);
        jTextFieldRua.setForeground(Color.BLACK);
        jTextFieldRua.setText("");
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 100);
        jPanelContent.add(jTextFieldRua, gbc);

        jLabelBairro.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelBairro.setText("Bairro:");
        jLabelBairro.setForeground(Color.BLACK);
        jLabelBairro.setBackground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 0, 100);
        jPanelContent.add(jLabelBairro, gbc);

        jTextFieldBairro.setBackground(Color.WHITE);
        jTextFieldBairro.setFont(new Font("Cormorant Infant", 1, 18));
        jTextFieldBairro.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jTextFieldBairro.setPreferredSize(fieldSize);
        jTextFieldBairro.setForeground(Color.BLACK);
        jTextFieldBairro.setText("");
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 20, 100);
        jPanelContent.add(jTextFieldBairro, gbc);

        jLabelNumero.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelNumero.setText("Número:");
        jLabelNumero.setForeground(Color.BLACK);
        jLabelNumero.setBackground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 0, 100);
        jPanelContent.add(jLabelNumero, gbc);

        jTextFieldNumero.setBackground(Color.WHITE);
        jTextFieldNumero.setFont(new Font("Cormorant Infant", 1, 18));
        jTextFieldNumero.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jTextFieldNumero.setPreferredSize(fieldSize);
        jTextFieldNumero.setForeground(Color.BLACK);
        jTextFieldNumero.setText("");
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 20, 100);
        jPanelContent.add(jTextFieldNumero, gbc);

        jLabelCidade.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelCidade.setText("Cidade:");
        jLabelCidade.setForeground(Color.BLACK);
        jLabelCidade.setBackground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 0, 0, 100);
        jPanelContent.add(jLabelCidade, gbc);

        jTextFieldCidade.setBackground(Color.WHITE);
        jTextFieldCidade.setFont(new Font("Cormorant Infant", 1, 18));
        jTextFieldCidade.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jTextFieldCidade.setPreferredSize(fieldSize);
        jTextFieldCidade.setForeground(Color.BLACK);
        jTextFieldCidade.setText("");
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.insets = new Insets(0, 0, 0, 100);
        jPanelContent.add(jTextFieldCidade, gbc);

        jLabelUF.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelUF.setText("UF:");
        jLabelUF.setForeground(Color.BLACK);
        jLabelUF.setBackground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.insets = new Insets(0, 0, 0, 100);
        jPanelContent.add(jLabelUF, gbc);

        jComboBoxUF.setBackground(Color.WHITE);
        jComboBoxUF.setFont(new Font("Cormorant Infant", 1, 18));
        jComboBoxUF.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jComboBoxUF.setPreferredSize(fieldSize);
        jComboBoxUF.setForeground(Color.BLACK);
        for (UF uf : UF.values()) {
            jComboBoxUF.addItem(uf);
        }
        gbc.gridx = 1;
        gbc.gridy = 9;
        gbc.insets = new Insets(0, 0, 20, 100);
        jPanelContent.add(jComboBoxUF, gbc);

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

                JsonObject suppliers = JsonParser.parseString(response.toString()).getAsJsonObject();

                jTextFieldNome.setText(suppliers.get("name").getAsString());
                jTextFieldTelefone.setText(suppliers.get("phoneNumber").getAsString());
                jTextFieldEmail.setText(suppliers.get("email").getAsString());
                jTextFieldCPNJ.setText(suppliers.get("cnpj").getAsString());
                String[] address = suppliers.get("address").getAsString().split(",");
                jTextFieldRua.setText(address[0]);
                jTextFieldBairro.setText(address[1]);
                jTextFieldNumero.setText(address[2]);
                String[] cityUF = address[3].split("-");
                jTextFieldCidade.setText(cityUF[0]);

                try {
                    UF ufSelecionado = UF.valueOf(cityUF[1]);
                    jComboBoxUF.setSelectedItem(ufSelecionado);
                } catch (IllegalArgumentException e) {
                    throw new Exception("O UF não foi encontrado!");
                }
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
        jTextFieldNome.setText(null);
        jTextFieldTelefone.setText(null);
        jTextFieldEmail.setText(null);
        jTextFieldCPNJ.setText(null);
        this.jTextFieldRua.setText(null);
        this.jTextFieldBairro.setText(null);
        this.jTextFieldNumero.setText(null);
        this.jTextFieldCidade.setText(null);
        this.jComboBoxUF.setSelectedItem(0);
        jTextFieldObservacoes.setText(null);
        this.jButtonCadastrar.setText("Cadastrar");
        this.jLabelCadastro.setText("Cadastrar Fornecedor");
        this.id = null;
    }

    private void cadastrarFornecedor(){
        try {
            String name = jTextFieldNome.getText();
            String cnpj = jTextFieldCPNJ.getText();
            String email = jTextFieldEmail.getText();
            String observation = jTextFieldObservacoes.getText();
            String address = String.format("%s, %s, %s, %s-%s",
                    jTextFieldRua.getText().trim(),
                    jTextFieldBairro.getText().trim(),
                    jTextFieldNumero.getText().trim(),
                    jTextFieldCidade.getText().trim(),
                    jComboBoxUF.getSelectedItem()
            );
            String phone_number = jTextFieldTelefone.getText();


            JsonObject jsonData = new JsonObject();
            jsonData.addProperty("name", name);
            jsonData.addProperty("cnpj", cnpj);
            jsonData.addProperty("email", email);
            jsonData.addProperty("observation", observation);
            jsonData.addProperty("address", address);
            jsonData.addProperty("phoneNumber", phone_number);

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
                frame.showCard("listar_fonecedores");
                JOptionPane.showMessageDialog(this.rootPane,
                        "O Fornecedor foi cadstrado com sucesso!",
                        "Fornecedor Cadasatrado",
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

                    JOptionPane.showMessageDialog(this.rootPane,
                            "Não foi possível cadastrar o fornecedor. Verifique os dados e tente novamente!\n" + errorMessage,
                            "Erro no Cadastro",
                            JOptionPane.ERROR_MESSAGE);
                } catch (Exception jsonException) {
                    JOptionPane.showMessageDialog(this.rootPane,
                            "Erro inesperado ao processar a resposta da API.\nCódigo: " + statusCode + "\nResposta: " + response,
                            "Erro no Cadastro",
                            JOptionPane.ERROR_MESSAGE);
                }
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
            String address = String.format("%s, %s, %s, %s-%s",
                    jTextFieldRua.getText().trim(),
                    jTextFieldBairro.getText().trim(),
                    jTextFieldNumero.getText().trim(),
                    jTextFieldCidade.getText().trim(),
                    jComboBoxUF.getSelectedItem()
            );
            String phone_number = jTextFieldTelefone.getText();


            JsonObject jsonData = new JsonObject();
            jsonData.addProperty("name", name);
            jsonData.addProperty("cnpj", cnpj);
            jsonData.addProperty("email", email);
            jsonData.addProperty("observation", observation);
            jsonData.addProperty("address", address);
            jsonData.addProperty("phoneNumber", phone_number);

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
                frame.showCard("listar_fonecedores");
                JOptionPane.showMessageDialog(this.rootPane,
                        "O Fornecedor foi atualizado com sucesso!",
                        "Fornecedor Atualizado",
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

                    JOptionPane.showMessageDialog(this.rootPane,
                            "Não foi possível atualizar o fornecedor. Verifique os dados e tente novamente!\n" + errorMessage,
                            "Erro na Atualização",
                            JOptionPane.ERROR_MESSAGE);
                } catch (Exception jsonException) {
                    JOptionPane.showMessageDialog(this.rootPane,
                            "Erro inesperado ao processar a resposta da API.\nCódigo: " + statusCode + "\nResposta: " + response,
                            "Erro na Atualização",
                            JOptionPane.ERROR_MESSAGE);
                }
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
    private JLabel jLabelObservacoes;
    private JLabel jLabelRua;
    private JLabel jLabelBairro;
    private JLabel jLabelNumero;
    private JLabel jLabelCidade;
    private JLabel jLabelUF;

    private JTextField jTextFieldNome;
    private JTextField jTextFieldTelefone;
    private JTextField jTextFieldCPNJ;
    private JTextField jTextFieldEmail;
    private JTextField jTextFieldObservacoes;
    private JTextField jTextFieldRua;
    private JTextField jTextFieldBairro;
    private JTextField jTextFieldNumero;
    private JTextField jTextFieldCidade;
    private JComboBox<UF> jComboBoxUF;

    private JButton jButtonCancelar;
    private JButton jButtonCadastrar;

    private JPanel jPanelHeader;
    private JPanel jPanelButtons;
    private JPanel jPanelContent;
    private JPanel jPanel;

    private GridBagConstraints gbc;

    private JSeparator jSeparator;
}
