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
        jLabelEndereco = new JLabel();

        jTextFieldNome = new JTextField();
        jTextFieldEmail = new JTextField();
        try{
            jTextFieldTelefone = new JFormattedTextField(new MaskFormatter("(##) #####-####"));
            jTextFieldCPF = new JFormattedTextField(new MaskFormatter("###.###.###-##"));
            jTextFieldDataNascimento = new JFormattedTextField(new MaskFormatter("##/##/####"));
            jTextFieldEndereco = new JFormattedTextField();
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
        this.jTextFieldNome.setText("Nome Completo");
        this.jTextFieldNome.addFocusListener(new FocusAdapter() { // adicionar um evento ao clicar no campo
            @Override
            public void focusGained(FocusEvent e) { // clicou
                if (jTextFieldNome.getText().equals("Nome Completo")) {
                    jTextFieldNome.setText("");
                    jTextFieldNome.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) { // clicou em outra coisa
                if (jTextFieldNome.getText().isEmpty()) {
                    jTextFieldNome.setText("Nome Completo");
                    jTextFieldNome.setForeground(Color.BLACK);
                }
            }
        });
        this.gbc.gridx = 0;
        this.gbc.gridy = 1;
        this.gbc.insets = new Insets(0, 0, 20, 100);
        this.jPanelContent.add(this.jTextFieldNome, this.gbc);

        this.jLabelTelefone.setFont(new Font("Cormorant Garamond", 1, 18));
        this.jLabelTelefone.setText("Telefone:");
        this.jLabelTelefone.setForeground(Color.BLACK);
        this.gbc.gridx = 1;
        this.gbc.gridy = 0;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        this.jPanelContent.add(this.jLabelTelefone, this.gbc);

        this.jTextFieldTelefone.setBackground(new Color(255, 255, 255));
        this.jTextFieldTelefone.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        this.jTextFieldTelefone.setFont(new Font("Cormorant Infant", 1, 18));
        this.jTextFieldTelefone.setForeground(Color.BLACK);
        this.jTextFieldTelefone.setPreferredSize(fieldSize);
        this.gbc.gridx = 1;
        this.gbc.gridy = 1;
        this.gbc.insets = new Insets(0, 0, 20, 0);
        this.jPanelContent.add(this.jTextFieldTelefone, this.gbc);

        this.jLabelCPF.setFont(new Font("Cormorant Garamond", 1, 18));
        this.jLabelCPF.setText("CPF:");
        this.jLabelCPF.setForeground(Color.BLACK);
        this.gbc.gridx = 0;
        this.gbc.gridy = 2;
        this.gbc.insets = new Insets(0, 0, 0, 100);
        this.jPanelContent.add(this.jLabelCPF, this.gbc);

        this.jTextFieldCPF.setBackground(new Color(255, 255, 255));
        this.jTextFieldCPF.setBackground(Color.WHITE);
        this.jTextFieldCPF.setForeground(Color.BLACK);
        this.jTextFieldCPF.setFont(new Font("Cormorant Infant", Font.BOLD, 18));
        this.jTextFieldCPF.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        this.jTextFieldCPF.setPreferredSize(fieldSize);
        this.gbc.gridx = 0;
        this.gbc.gridy = 3;
        this.gbc.insets = new Insets(0, 0, 20, 100);
        this.jPanelContent.add(this.jTextFieldCPF, this.gbc);

        this.jLabelEmail.setText("E-mail:");
        this.jLabelEmail.setFont(new Font("Cormorant Garamond", 1, 18));
        this.jLabelEmail.setForeground(Color.BLACK);
        this.gbc.gridx = 1;
        this.gbc.gridy = 2;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        this.jPanelContent.add(this.jLabelEmail, this.gbc);

        this.jTextFieldEmail.setBackground(Color.WHITE);
        this.jTextFieldEmail.setFont(new Font("Cormorant Garamond", 1, 18));
        this.jTextFieldEmail.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        this.jTextFieldEmail.setPreferredSize(fieldSize);
        this.jTextFieldEmail.setText("email@gmail.com");
        this.jTextFieldEmail.setForeground(Color.BLACK);
        this.jTextFieldEmail.addFocusListener(new FocusAdapter() {
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
        this.gbc.gridx = 1;
        this.gbc.gridy = 3;
        this.gbc.insets = new Insets(0, 0, 20, 0);
        this.jPanelContent.add(this.jTextFieldEmail, this.gbc);

        this.jLabelEndereco.setFont(new Font("Cormorant Garamond", 1, 18));
        this.jLabelEndereco.setText("Endereço:");
        this.jLabelEndereco.setForeground(Color.BLACK);
        this.jLabelEndereco.setBackground(Color.WHITE);
        this.gbc.gridx = 0;
        this.gbc.gridy = 4;
        this.gbc.insets = new Insets(0, 0, 0, 100);
        this.jPanelContent.add(this.jLabelEndereco, this.gbc);

        this.jTextFieldEndereco.setBackground(Color.WHITE);
        this.jTextFieldEndereco.setFont(new Font("Cormorant Infant", 1, 18));
        this.jTextFieldEndereco.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        this.jTextFieldEndereco.setPreferredSize(fieldSize);
        this.jTextFieldEndereco.setForeground(Color.BLACK);
        this.jTextFieldEndereco.setText("Rua, Bairro, Número, Cidade-UF");
        this.jTextFieldEndereco.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent evt) {
                if (jTextFieldEndereco.getText().equals("Rua, Bairro, Número, Cidade-UF")) {
                    jTextFieldEndereco.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent evt) {
                if (jTextFieldEndereco.getText().isEmpty()) {
                    jTextFieldEndereco.setText("Rua, Bairro, Número, Cidade-UF");
                }
            }
        });
        this.gbc.gridx = 0;
        this.gbc.gridy = 5;
        this.gbc.insets = new Insets(0, 0, 20, 100);
        this.jPanelContent.add(this.jTextFieldEndereco, this.gbc);

        this.jLabelDataNascimento.setFont(new Font("Cormorant Garamond", 1, 18));
        this.jLabelDataNascimento.setText("Data de Nascimento:");
        this.jLabelDataNascimento.setForeground(Color.BLACK);
        this.gbc.gridx = 1;
        this.gbc.gridy = 4;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        this.jPanelContent.add(this.jLabelDataNascimento, this.gbc);

        this.jTextFieldDataNascimento.setBackground(new Color(255, 255, 255));
        this.jTextFieldDataNascimento.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        this.jTextFieldDataNascimento.setPreferredSize(fieldSize);
        this.jTextFieldDataNascimento.setForeground(Color.BLACK);
        this.jTextFieldDataNascimento.setFont(new Font("Cormorant Infant", 1, 18));
        this.gbc.gridx = 1;
        this.gbc.gridy = 5;
        this.gbc.insets = new Insets(0, 0, 20, 0);
        this.jPanelContent.add(this.jTextFieldDataNascimento, this.gbc);


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
            String phone_number = jTextFieldTelefone.getText();
            String email = jTextFieldEmail.getText();
            String address = jTextFieldEndereco.getText();
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
                JOptionPane.showOptionDialog(this.framePrincipal,
                        "O Cliente foi cadastrado com sucesso!",
                        "Cleinte Cadastrado",
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
                JsonObject err = JsonParser.parseString(response.toString()).getAsJsonObject();
                JOptionPane.showOptionDialog(this.framePrincipal,
                        "Não foi possível cadastrar o cliente, verifique as informações dos campos e tente novamente!\n" + err.get("message").toString(),
                        "Cliente Não Cadastrado",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,null,null);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.framePrincipal, e.getMessage());
        }
    }

    private void editarCliente(){
        try {
            String name = jTextFieldNome.getText();
            String phone_number = jTextFieldTelefone.getText();
            String email = jTextFieldEmail.getText();
            String address = jTextFieldEndereco.getText();
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
                this.id = null;
                frame.showCard("listar_clientes");
                JOptionPane.showOptionDialog(this.framePrincipal,
                        "O Cliente foi atualizado com sucesso!",
                        "Cliente Atualizado",
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
                JsonObject err = JsonParser.parseString(response.toString()).getAsJsonObject();
                JOptionPane.showOptionDialog(this.framePrincipal,
                        "Não foi possível atualizar o cliente, verifique as informações dos campos e tente novamente!\n" + err.get("message").toString(),
                        "Cliente Não atualizado",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,null,null);
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
                    String address = prod.get("address").getAsString();
                    String phone_number = prod.get("phoneNumber").getAsString();
                    String data = prod.get("dateBirth").getAsString();

                    DateTimeFormatter formatoEntrada = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    DateTimeFormatter formatoSaida = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String dataFormatada = LocalDate.parse(data, formatoEntrada).format(formatoSaida);

                    jTextFieldNome.setText(name);
                    jTextFieldTelefone.setText(phone_number);
                    jTextFieldEmail.setText(email);
                    jTextFieldEndereco.setText(address);
                    jTextFieldCPF.setText(cpf);
                    jTextFieldDataNascimento.setText(dataFormatada.replace("/",""));

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
        this.jTextFieldEndereco.setText(null);
        this.jTextFieldEmail.setText("email@gmail.com");
        this.jTextFieldTelefone.setText(null);
        this.jTextFieldNome.setText("Nome do cliente");
        this.jLabelCadastro.setText("Cadastrar Cliente");
    }

    // componentes que vão ser usados na tela, só o essencial e com os nomes certinhos
    private JLabel jLabelCadastro;
    private JLabel jLabelNome;
    private JLabel jLabelTelefone;
    private JLabel jLabelCPF;
    private JLabel jLabelEmail;
    private JLabel jLabelEndereco;
    private JLabel jLabelDataNascimento;

    private JTextField jTextFieldNome;
    private JTextField jTextFieldTelefone;
    private JTextField jTextFieldCPF;
    private JTextField jTextFieldEmail;
    private JTextField jTextFieldEndereco;
    private JTextField jTextFieldDataNascimento;

    private JButton jButtonCancelar;
    private JButton jButtonCadastrar;

    private JPanel jPanelHeader;
    private JPanel jPanelButtons;
    private JPanel jPanelContent;
    private JPanel jPanel;

    private GridBagConstraints gbc;

    private JSeparator jSeparator;
}
