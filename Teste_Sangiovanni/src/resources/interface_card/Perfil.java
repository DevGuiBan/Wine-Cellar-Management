package resources.interface_card;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.cdimascio.dotenv.Dotenv;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Perfil extends JPanel {
    private JPanel mainPanel;
    private Dotenv dotenv;
    private JRootPane rootPane;
    private JanelaPrincipal frame;
    private JsonObject userData;

    public Perfil(JPanel mainPanel, JRootPane rootPane, JsonObject user) {
        this.mainPanel = mainPanel;
        this.rootPane = rootPane;
        this.userData = user;
        this.frame = (JanelaPrincipal) SwingUtilities.getWindowAncestor(rootPane);
        this.dotenv = Dotenv.load();
        this.initComponentes();
        try{
            if(!user.get("dateBirth").isJsonNull()){
                this.getEmployee();
            }else{
                throw new Exception("Usuario do tipo gerente");
            }
        } catch (Exception e) {
            this.getManager();
        }

    }

    private void initComponentes() {
        // inicialização de variáveis
        jLabelCPF = new JLabel();
        jLabelEmail = new JLabel();
        jLabelCadastro = new JLabel();
        jLabelNome = new JLabel();
        jLabelSenha = new JLabel();

        // Adicionar novas labels de endereço

        jTextFieldNome = new JTextField();
        jTextFieldEmail = new JTextField();
        jTextFieldSenha = new JTextField();

        try {
            jTextFieldCPF = new javax.swing.JFormattedTextField(new MaskFormatter("###.###.###-##"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        jButtonCadastrar = new JButton();
        jButtonCancelar = new JButton();

        jPanelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        jPanelContent = new JPanel(new GridBagLayout());
        jPanelHeader = new JPanel(new GridBagLayout());
        jPanel = new JPanel();

        gbc = new GridBagConstraints();

        profileIcon = new JLabel();

        // configuração do card
        setBackground(new Color(243, 243, 223));
        setPreferredSize(new Dimension(1366, 650)); // tamanho do painel que tem tudo
        setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));

        gbc.fill = GridBagConstraints.HORIZONTAL; // Permitir redimensionamento horizontal
        gbc.anchor = GridBagConstraints.WEST; // Alinhamento à esquerda
        Dimension fieldSize = new Dimension(400, 30); // tamanho padrão dos campos

        // painel branco que vai conter tudo
        jPanel.setPreferredSize(new Dimension(1200, 600));
        jPanel.setBackground(Color.WHITE);
        jPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // painel do texto de cadastro de produto(header)
        jPanelHeader.setBackground(new Color(255, 255, 255));
        jPanelHeader.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanelHeader.setPreferredSize(new Dimension(1200, 150));

        profileIcon.setIcon(new javax.swing.ImageIcon(Objects.requireNonNull(
                getClass().getResource("/resources/images/profile.png"))));
        profileIcon.setPreferredSize(new Dimension(150,150));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        jPanelHeader.add(profileIcon, gbc);

        jLabelCadastro.setFont(new Font("Cormorant Garamond SemiBold", 0, 30));
        jLabelCadastro.setText("Gerente");
        jLabelCadastro.setPreferredSize(new Dimension(250, 30));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        jPanelHeader.add(jLabelCadastro, gbc);

        jPanelHeader.setOpaque(false);
        jPanelHeader.add(profileIcon);
        jPanelHeader.add(jLabelCadastro);

        // adiciona o painelHeader no principal
        jPanel.add(jPanelHeader);

        // painel dos campos
        jPanelContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        jPanelContent.setPreferredSize(new Dimension(1200, 350));
        jPanelContent.setBackground(new Color(255, 255, 255));

        jLabelNome.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelNome.setText("Nome:");
        jLabelNome.setForeground(Color.BLACK);
        gbc.gridx = 0; // coluna
        gbc.gridy = 0; // linha
        gbc.insets = new Insets(0, 0, 0, 0); // adicionar espaçamento por componente
        jPanelContent.add(jLabelNome, gbc); // sempre adicionar neste formato por conta do grid

        jTextFieldNome.setBackground(Color.LIGHT_GRAY);
        jTextFieldNome.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jTextFieldNome.setFont(new Font("Cormorant Garamond", 1, 18));
        jTextFieldNome.setPreferredSize(fieldSize);
        jTextFieldNome.setForeground(Color.BLACK);
        jTextFieldNome.setText("");
        jTextFieldNome.setEditable(false);
        jTextFieldNome.setFocusable(false);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 0);
        jPanelContent.add(jTextFieldNome, gbc);

        jLabelCPF.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelCPF.setForeground(Color.BLACK);
        jLabelCPF.setText("CPF:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        jPanelContent.add(jLabelCPF, gbc);

        jTextFieldCPF.setBackground(Color.LIGHT_GRAY);
        jTextFieldCPF.setForeground(Color.BLACK);
        jTextFieldCPF.setFont(new Font("Cormorant Infant", Font.BOLD, 18));
        jTextFieldCPF.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jTextFieldCPF.setPreferredSize(fieldSize);
        jTextFieldCPF.setText("");
        jTextFieldCPF.setEditable(false);
        jTextFieldCPF.setFocusable(false);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 20, 0);
        jPanelContent.add(jTextFieldCPF, gbc);

        jLabelEmail.setText("E-mail:");
        jLabelEmail.setForeground(Color.BLACK);
        jLabelEmail.setFont(new Font("Cormorant Garamond", 1, 18));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        jPanelContent.add(jLabelEmail, gbc);

        jTextFieldEmail.setBackground(Color.LIGHT_GRAY);
        jTextFieldEmail.setFont(new Font("Cormorant Garamond", 1, 18));
        jTextFieldEmail.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jTextFieldEmail.setPreferredSize(fieldSize);
        jTextFieldEmail.setForeground(Color.BLACK);
        jTextFieldEmail.setText("");
        jTextFieldEmail.setEditable(false);
        jTextFieldEmail.setFocusable(false);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 20, 0);
        jPanelContent.add(jTextFieldEmail, gbc);

        jLabelSenha.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelSenha.setText("Senha:");
        jLabelSenha.setForeground(Color.BLACK);
        jLabelSenha.setBackground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 0, 0, 0);
        jPanelContent.add(jLabelSenha, gbc);

        jTextFieldSenha.setBackground(Color.LIGHT_GRAY);
        jTextFieldSenha.setFont(new Font("Cormorant Infant", 1, 18));
        jTextFieldSenha.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jTextFieldSenha.setPreferredSize(fieldSize);
        jTextFieldSenha.setForeground(Color.BLACK);
        jTextFieldSenha.setText("");
        jTextFieldSenha.setEditable(false);
        jTextFieldSenha.setFocusable(false);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.insets = new Insets(0, 0, 20, 0);
        jPanelContent.add(jTextFieldSenha, gbc);

        jButtonCancelar.setBackground(new Color(225, 225, 200));
        jButtonCancelar.setFont(new Font("Cormorant Garamond Bold", 1, 18));
        jButtonCancelar.setText("Cancelar");
        jButtonCancelar.setFocusPainted(false);
        jButtonCancelar.setBorder(new EmptyBorder(5, 20, 5, 20));
        jButtonCancelar.setVisible(false);
        jButtonCancelar.addActionListener(e -> {
            reset();
        });

        jButtonCadastrar.setBackground(new Color(0, 28, 128));
        jButtonCadastrar.setFont(new Font("Cormorant Garamond Bold", 1, 18));
        jButtonCadastrar.setForeground(new Color(255, 255, 200));
        jButtonCadastrar.setText("Editar");
        jButtonCadastrar.setFocusPainted(false);
        jButtonCadastrar.addActionListener(evt->{
            if(jButtonCadastrar.getText().equals("Editar")){
                edit();
            }else{
                editManager();
            }
        });


        jPanel.add(jPanelContent);

        jPanelButtons.setBackground(new Color(255, 255, 255));
        jPanelButtons.setPreferredSize(new Dimension(1200, 50));
        jPanelButtons.add(jButtonCancelar);
        jPanelButtons.add(jButtonCadastrar);
        jPanelButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        jPanelButtons.setBorder(new EmptyBorder(-10, 0, 0, 0));

        jPanel.add(jPanelButtons);

        add(jPanel);
    }

    private void editManager() {
        try {
            String name = jTextFieldNome.getText();
            String email = jTextFieldEmail.getText();
            String cpf = jTextFieldCPF.getText();
            String password = jTextFieldSenha.getText();
            if (password.isBlank() || password.trim().isBlank()) {
                throw new IllegalArgumentException("Informe a senha do gerente!");
            }

            JsonObject jsonData = new JsonObject();
            jsonData.addProperty("name", name);
            jsonData.addProperty("cpf", cpf);
            jsonData.addProperty("email", email);
            jsonData.addProperty("password", password);

            // making the request
            String urlAPI = this.dotenv.get("API_HOST");
            URL url = new URL(urlAPI + "/managers/" + userData.get("id").getAsString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonData.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int statusCode = connection.getResponseCode();
            StringBuilder response = new StringBuilder();

            if (statusCode >= 200 && statusCode < 300) {
                this.reset();
                JOptionPane.showMessageDialog(this.rootPane,
                        "O Gerente foi atualizado com sucesso!",
                        "Gerente Atualizado",
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
                            "Não foi possível atualizar o gerente. Verifique os dados e tente novamente!\n" + errorMessage,
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

    private void getManager() {
        this.jTextFieldNome.setText(userData.get("name").getAsString());
        this.jTextFieldEmail.setText(userData.get("email").getAsString());
        this.jTextFieldCPF.setText(userData.get("cpf").getAsString());
        this.jTextFieldSenha.setText(userData.get("password").getAsString());
    }

    private void getEmployee() {
        this.jTextFieldNome.setText(userData.get("name").getAsString());
        this.jTextFieldEmail.setText(userData.get("email").getAsString());
        this.jTextFieldCPF.setText(userData.get("cpf").getAsString());
        this.jTextFieldSenha.setText(userData.get("password").getAsString());
        this.jPanelButtons.setVisible(false);
        jLabelCadastro.setText("Funcionário");
    }

    private void edit(){
        jTextFieldNome.setFocusable(true);
        jTextFieldNome.setEditable(true);
        jTextFieldNome.setBackground(Color.WHITE);

        jTextFieldCPF.setBackground(Color.WHITE);
        jTextFieldCPF.setFocusable(true);
        jTextFieldCPF.setEditable(true);

        jTextFieldEmail.setEditable(true);
        jTextFieldEmail.setBackground(Color.WHITE);
        jTextFieldEmail.setFocusable(true);

        jTextFieldSenha.setEditable(true);
        jTextFieldSenha.setFocusable(true);
        jTextFieldSenha.setBackground(Color.WHITE);

        jButtonCancelar.setVisible(true);

        jButtonCadastrar.setText("Salvar");
    }

    public void reset() {
        this.getManager();
        jTextFieldNome.setFocusable(false);
        jTextFieldNome.setEditable(false);
        jTextFieldNome.setBackground(Color.LIGHT_GRAY);

        jTextFieldCPF.setBackground(Color.LIGHT_GRAY);
        jTextFieldCPF.setFocusable(false);
        jTextFieldCPF.setEditable(false);

        jTextFieldEmail.setEditable(false);
        jTextFieldEmail.setBackground(Color.LIGHT_GRAY);
        jTextFieldEmail.setFocusable(false);

        jTextFieldSenha.setEditable(false);
        jTextFieldSenha.setFocusable(false);
        jTextFieldSenha.setBackground(Color.LIGHT_GRAY);

        jButtonCancelar.setVisible(false);
        jButtonCadastrar.setText("Editar");
    }


    // componentes que vão ser usados na tela, só o essencial e com os nomes certinhos
    private JLabel jLabelCadastro;
    private JLabel jLabelNome;
    private JLabel jLabelCPF;
    private JLabel jLabelEmail;
    private JLabel jLabelSenha;

    private JTextField jTextFieldNome;
    private JTextField jTextFieldCPF;
    private JTextField jTextFieldEmail;
    private JTextField jTextFieldSenha;

    private JButton jButtonCancelar;
    private JButton jButtonCadastrar;

    private JPanel jPanelHeader;
    private JPanel jPanelButtons;
    private JPanel jPanelContent;
    private JPanel jPanel;

    private GridBagConstraints gbc;

    private JLabel profileIcon;
}
