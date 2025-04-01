package resources.interface_card;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.cdimascio.dotenv.Dotenv;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CadastrarCliente extends JPanel {
    private JPanel mainPanel;
    private String id;
    private Dotenv dotenv;
    private JRootPane framePrincipal;
    private JanelaPrincipal frame;

    public CadastrarCliente(JPanel mainPanel,JRootPane framePrincipal) {
        this.mainPanel = mainPanel;
        this.framePrincipal = framePrincipal;
        this.frame = (JanelaPrincipal) SwingUtilities.getWindowAncestor(framePrincipal);
        this.dotenv = Dotenv.load();
        this.initComponentes();
    }

    private void initComponentes(){
        // inicialização de variáveis
        jLabelTelefone = new JLabel();
        jLabelCPF = new JLabel();
        jLabelEmail = new JLabel();
        jLabelDataNascimento = new JLabel();
        jLabelCadastro = new JLabel();
        jLabelNome = new JLabel();
        jLabelRua = new JLabel();
        jLabelBairro = new JLabel();
        jLabelNumero = new JLabel();
        jLabelCidade = new JLabel();
        jLabelUF = new JLabel();

        jTextFieldNome = new JTextField();
        jTextFieldEmail = new JTextField();
        jTextFieldRua = new JTextField();
        jTextFieldBairro = new JTextField();
        jTextFieldNumero = new JTextField();
        jTextFieldCidade = new JTextField();
        jComboBoxUF = new JComboBox<UF>();

        try{
            jTextFieldTelefone = new JFormattedTextField(new MaskFormatter("(##) #####-####"));
            jTextFieldCPF = new JFormattedTextField(new MaskFormatter("###.###.###-##"));
            jTextFieldDataNascimento = new JFormattedTextField(new MaskFormatter("##/##/####"));
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
        this.setBackground(new Color(243, 243, 223));
        this.setPreferredSize(new Dimension(1366, 650)); // tamanho do painel que tem tudo
        this.setLayout(new FlowLayout(FlowLayout.CENTER,30,30));

        this.gbc.fill = GridBagConstraints.HORIZONTAL; // Permitir redimensionamento horizontal
        this.gbc.anchor = GridBagConstraints.WEST; // Alinhamento à esquerda
        Dimension fieldSize = new Dimension(400, 40); // tamanho padrão dos campos

        // painel branco que vai conter tudo
        this.jPanel.setPreferredSize(new Dimension(1200, 600));
        this.jPanel.setBackground(Color.WHITE);
        this.jPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // painel do texto de cadastro de produto(header)
        this.jPanelHeader.setLayout(new BoxLayout(this.jPanelHeader, BoxLayout.Y_AXIS));
        this.jPanelHeader.setBackground(new Color(255, 255, 255));
        this.jPanelHeader.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.jPanelHeader.setPreferredSize(new Dimension(1200, 50));

        this.jLabelCadastro.setFont(new Font("Cormorant Garamond SemiBold", 0, 30));
        this.jLabelCadastro.setText("Cadastrar Cliente");
        this.jLabelCadastro.setAlignmentX(Component.CENTER_ALIGNMENT); // centralizar

        this.jSeparator.setBackground(new Color(128, 0, 32));
        this.jSeparator.setForeground(new Color(128, 0, 32));
        this.jSeparator.setPreferredSize(new Dimension(200, 8));
        this.jSeparator.setMaximumSize(new Dimension(230, 8));
        this.jSeparator.setAlignmentX(Component.CENTER_ALIGNMENT); // centralizar

        this.jPanelHeader.setOpaque(false);
        this.jPanelHeader.add(this.jLabelCadastro);
        this.jPanelHeader.add(this.jSeparator); // adiciona no painel

        // adiciona o painelHeader no principal
        this.jPanel.add(this.jPanelHeader);

        // painel dos campos
        this.jPanelContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.jPanelContent.setPreferredSize(new Dimension(1200, 450));
        this.jPanelContent.setBackground(new Color(255, 255, 255));

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

        this.jLabelCPF.setFont(new Font("Cormorant Garamond", 1, 18));
        this.jLabelCPF.setText("CPF:");
        this.jLabelCPF.setForeground(Color.BLACK);
        this.gbc.gridx = 0;
        this.gbc.gridy = 4;
        this.gbc.insets = new Insets(0, 0, 0, 100);
        this.jPanelContent.add(this.jLabelCPF, this.gbc);

        this.jTextFieldCPF.setBackground(new Color(255, 255, 255));
        this.jTextFieldCPF.setBackground(Color.WHITE);
        this.jTextFieldCPF.setForeground(Color.BLACK);
        this.jTextFieldCPF.setFont(new Font("Cormorant Infant", Font.BOLD, 18));
        this.jTextFieldCPF.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        this.jTextFieldCPF.setPreferredSize(fieldSize);
        this.gbc.gridx = 0;
        this.gbc.gridy = 5;
        this.gbc.insets = new Insets(0, 0, 20, 100);
        this.jPanelContent.add(this.jTextFieldCPF, this.gbc);

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

        this.jLabelDataNascimento.setFont(new Font("Cormorant Garamond", 1, 18));
        this.jLabelDataNascimento.setText("Data de Nascimento:");
        this.jLabelDataNascimento.setForeground(Color.BLACK);
        this.gbc.gridx = 0;
        this.gbc.gridy = 8;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        this.jPanelContent.add(this.jLabelDataNascimento, this.gbc);

        this.jTextFieldDataNascimento.setBackground(new Color(255, 255, 255));
        this.jTextFieldDataNascimento.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        this.jTextFieldDataNascimento.setPreferredSize(fieldSize);
        this.jTextFieldDataNascimento.setForeground(Color.BLACK);
        this.jTextFieldDataNascimento.setFont(new Font("Cormorant Infant", 1, 18));
        this.gbc.gridx = 0;
        this.gbc.gridy = 9;
        this.gbc.insets = new Insets(0, 0, 20, 100);
        this.jPanelContent.add(this.jTextFieldDataNascimento, this.gbc);

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


        this.jButtonCancelar.setBackground(new Color(225, 225, 200));
        this.jButtonCancelar.setFont(new Font("Cormorant Garamond Bold", 1, 18));
        this.jButtonCancelar.setText("Cancelar");
        this.jButtonCancelar.setFocusPainted(false);
        this.jButtonCancelar.setBorder(new EmptyBorder(5,20,5,20));
        this.jButtonCancelar.addActionListener(e -> {
            reset();
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            cl.show(mainPanel, "listar_clientes");
        });

        this.jButtonCadastrar.setBackground(new Color(0, 128, 17));
        this.jButtonCadastrar.setFont(new Font("Cormorant Garamond Bold", 1, 18));
        this.jButtonCadastrar.setForeground(new Color(255, 255, 200));
        this.jButtonCadastrar.setText("Cadastrar");
        this.jButtonCadastrar.setFocusPainted(false);
        this.jButtonCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (id == null) {
                    cadastrarCliente();
                } else  {
                    editarCliente();
                }
            }
        });



        this.jPanel.add(this.jPanelContent);

        this.jPanelButtons.setBackground(new Color(255, 255, 255));
        this.jPanelButtons.setPreferredSize(new Dimension(1200, 50));
        this.jPanelButtons.add(this.jButtonCancelar);
        this.jPanelButtons.add(this.jButtonCadastrar);
        this.jPanelButtons.setLayout(new FlowLayout(FlowLayout.RIGHT,10,10));
        this.jPanelButtons.setBorder(new EmptyBorder(5,10,5,140));

        this.jPanel.add(this.jPanelButtons);

        add(this.jPanel);
    }

    private void cadastrarCliente(){
        try {
            String name = jTextFieldNome.getText();
            if(name.equals("Nome Completo")){
                throw new IllegalArgumentException("Insira um nome válido!");
            }
            String phone_number = jTextFieldTelefone.getText();
            String email = jTextFieldEmail.getText();
            String cpf = jTextFieldCPF.getText();
            String dataString = jTextFieldDataNascimento.getText();

            String address = String.format("%s, %s, %s, %s-%s",
                    jTextFieldRua.getText().trim(),
                    jTextFieldBairro.getText().trim(),
                    jTextFieldNumero.getText().trim(),
                    jTextFieldCidade.getText().trim(),
                    jComboBoxUF.getSelectedItem()
            );

            JsonObject jsonData = new JsonObject();
            jsonData.addProperty("name", name);
            jsonData.addProperty("email", email);
            jsonData.addProperty("phoneNumber", phone_number);
            jsonData.addProperty("address", address);
            jsonData.addProperty("cpf", cpf);
            jsonData.addProperty("dateBirth", dataString);

            // making the request
            String urlAPI = this.dotenv.get("API_HOST");
            URL url = new URL(urlAPI + "/client");
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
                frame.showCard("listar_clientes");
                JOptionPane.showMessageDialog(this.framePrincipal,
                        "O Cliente foi cadastrado com sucesso!",
                        "Cliente Cadastrado",
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
                            "Não foi possível cadastrar o cliente. Verifique os dados e tente novamente!\n" + errorMessage,
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

    private void editarCliente(){
        try {
            String name = jTextFieldNome.getText();
            if(name.equals("Nome Completo")){
                throw new IllegalArgumentException("Insira um nome válido!");
            }

            String address = String.format("%s, %s, %s, %s-%s",
                jTextFieldRua.getText().trim(),
                jTextFieldBairro.getText().trim(),
                jTextFieldNumero.getText().trim(),
                jTextFieldCidade.getText().trim(),
                jComboBoxUF.getSelectedItem()
            );

            String phone_number = jTextFieldTelefone.getText();
            String email = jTextFieldEmail.getText();

            String cpf = jTextFieldCPF.getText();
            String dataString = jTextFieldDataNascimento.getText();

            JsonObject jsonData = new JsonObject();
            jsonData.addProperty("name", name);
            jsonData.addProperty("email", email);
            jsonData.addProperty("phoneNumber", phone_number);
            jsonData.addProperty("address", address);
            jsonData.addProperty("cpf", cpf);
            jsonData.addProperty("dateBirth", dataString);

            // making the request
            String urlAPI = this.dotenv.get("API_HOST");
            URL url = new URL(urlAPI + "/client/"+this.id);
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
                frame.showCard("listar_clientes");
                JOptionPane.showMessageDialog(this.framePrincipal,
                        "O Cliente foi atualizado com sucesso!",
                        "Cliente Atualizado",
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
                            "Não foi possível atualizar o cliente. Verifique os dados e tente novamente!\n" + errorMessage,
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

    public void setId(String id){
        this.id = id;
        this.getCliente();
        this.jButtonCadastrar.setText("Atualizar");
        this.jLabelCadastro.setText("Editar Cliente");
    }

    private void getCliente(){
            try {
                // making the request
                String urlAPI = this.dotenv.get("API_HOST");
                URL url = new URL(urlAPI + "/client/" + this.id);
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
                    String cpf = prod.get("cpf").getAsString();
                    String email = prod.get("email").getAsString();
                    String phone_number = prod.get("phoneNumber").getAsString();
                    String data = prod.get("dateBirth").getAsString();

                    DateTimeFormatter formatoEntrada = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    DateTimeFormatter formatoSaida = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String dataFormatada = LocalDate.parse(data, formatoEntrada).format(formatoSaida);

                    jTextFieldNome.setText(name);
                    jTextFieldTelefone.setText(phone_number);
                    jTextFieldEmail.setText(email);

                    jTextFieldCPF.setText(cpf);
                    jTextFieldDataNascimento.setText(dataFormatada.replace("/",""));
                    String[] address = prod.get("address").getAsString().split(",");
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

                    connection.disconnect();

                } else {
                    JOptionPane.showMessageDialog(framePrincipal, "Erro ao carregar o cliente: " + responseCode);
                    connection.disconnect();
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(framePrincipal, e.getMessage());
            }
    }

    public void reset(){
        this.jTextFieldDataNascimento.setText(null);
        this.jButtonCadastrar.setText("Cadastrar");
        this.jTextFieldCPF.setText(null);
        this.jTextFieldRua.setText(null);
        this.jTextFieldBairro.setText(null);
        this.jTextFieldNumero.setText(null);
        this.jTextFieldCidade.setText(null);
        this.jComboBoxUF.setSelectedItem(0);
        this.jTextFieldEmail.setText(null);
        this.jTextFieldTelefone.setText(null);
        this.jTextFieldNome.setText(null);
        this.jLabelCadastro.setText("Cadastrar Cliente");
        this.id = null;
    }

    // componentes que vão ser usados na tela, só o essencial e com os nomes certinhos
    private JLabel jLabelCadastro;
    private JLabel jLabelNome;
    private JLabel jLabelTelefone;
    private JLabel jLabelCPF;
    private JLabel jLabelEmail;
    private JLabel jLabelDataNascimento;
    private JLabel jLabelRua;
    private JLabel jLabelBairro;
    private JLabel jLabelNumero;
    private JLabel jLabelCidade;
    private JLabel jLabelUF;

    private JTextField jTextFieldNome;
    private JTextField jTextFieldTelefone;
    private JTextField jTextFieldCPF;
    private JTextField jTextFieldEmail;
    private JTextField jTextFieldDataNascimento;
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
