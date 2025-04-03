package resources.interface_card;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.cdimascio.dotenv.Dotenv;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Login extends JFrame {

    private Dotenv dotenv;

    private void initComponents() {
        this.setTitle("Login");
        setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/images/icon.png"))).getImage());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridLayout(1, 2));

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(Color.WHITE);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        JLabel casaSanGiovanni = new JLabel("Casa San'Giovanni", SwingConstants.CENTER);
        casaSanGiovanni.setFont(new Font("Cinzel", Font.PLAIN, 24));
        casaSanGiovanni.setForeground(new Color(128, 0, 32));
        centerPanel.add(casaSanGiovanni); // GridBagLayout centraliza por padrão
        leftPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(Color.WHITE);
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.X_AXIS));
        logoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        ImageIcon icon = null;
        try {
            icon = new ImageIcon(Login.class.getResource("/resources/images/icon.png"));
            Image image = icon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            icon = new ImageIcon(image);
        } catch (Exception e) {
            System.out.println("Erro ao carregar a imagem: " + e.getMessage());
        }
        JLabel iconLabel = (icon != null) ? new JLabel(icon) : new JLabel("LOGO");

        JLabel textLabel = new JLabel("InfinityCode");
        textLabel.setFont(new Font("Coda", Font.PLAIN, 16));
        textLabel.setForeground(Color.BLACK);

        logoPanel.add(iconLabel);
        logoPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        logoPanel.add(textLabel);
        leftPanel.add(logoPanel, BorderLayout.SOUTH);

        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(new Color(128, 0, 32));

        JLabel loginLabel = new JLabel("Login");
        loginLabel.setFont(new Font("Cormorant Infant", Font.BOLD, 50));
        loginLabel.setForeground(Color.WHITE);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);

        Dimension fieldSize = new Dimension(250, 30);

        JLabel email = new JLabel("Email: ");
        email.setFont(new Font("Cormorant Infant", Font.PLAIN, 20));
        email.setForeground(Color.WHITE);
        textoEmail = new JTextField();
        textoEmail.setFont(new Font("Cormorant Infant", Font.PLAIN, 20));
        textoEmail.setPreferredSize(fieldSize);

        JLabel senha = new JLabel("Senha: ");
        senha.setFont(new Font("Cormorant Infant", Font.PLAIN, 20));
        senha.setForeground(Color.WHITE);
        textoSenha = new JPasswordField();
        textoSenha.setFont(new Font("Cormorant Infant", Font.PLAIN, 20));
        textoSenha.setPreferredSize(fieldSize);

        JButton esqueciSenha = new JButton("Esqueci minha senha");
        esqueciSenha.setBorder(null);
        esqueciSenha.setBackground(new Color(128, 0, 32));
        esqueciSenha.setFocusable(false);
        esqueciSenha.setCursor(new Cursor(Cursor.HAND_CURSOR));
        esqueciSenha.setFocusPainted(false);
        esqueciSenha.setFont(new Font("Cormorant Infant", Font.PLAIN, 18));
        esqueciSenha.setForeground(new Color(255, 235, 43));
        esqueciSenha.addActionListener(evt->{
            JFrame tela = new RecuperarSenha();
            tela.setVisible(true);
            this.dispose();
        });

        JButton btnEntrar = new JButton("Entrar");
        btnEntrar.setFont(new Font("Cormorant Infant", Font.BOLD, 20));
        btnEntrar.setForeground(Color.BLACK);
        btnEntrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEntrar.setBackground(new Color(255, 235, 43));
        btnEntrar.setPreferredSize(fieldSize);
        btnEntrar.addActionListener(evt -> {
            login();
        });

        JButton btnCadastrar = new JButton("Cadastrar-se");
        btnCadastrar.setFont(new Font("Cormorant Infant", Font.BOLD, 20));
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCadastrar.setBackground(new Color(128, 0, 32));
        btnCadastrar.setBorder(BorderFactory.createLineBorder(new Color(255, 235, 43), 2));
        btnCadastrar.setPreferredSize(fieldSize);
        btnCadastrar.addActionListener(evt -> {
            cadastrar();
        });

        GridBagConstraints gbcForm = new GridBagConstraints();
        gbcForm.gridx = 0;
        gbcForm.gridy = 0;
        gbcForm.anchor = GridBagConstraints.WEST;
        gbcForm.insets = new Insets(5, 10, 5, 10);
        formPanel.add(email, gbcForm);
        gbcForm.gridy++;
        formPanel.add(textoEmail, gbcForm);
        gbcForm.gridy++;
        formPanel.add(senha, gbcForm);
        gbcForm.gridy++;
        formPanel.add(textoSenha, gbcForm);
        gbcForm.gridy++;
        formPanel.add(esqueciSenha, gbcForm);
        gbcForm.gridy++;
        formPanel.add(btnEntrar, gbcForm);
        gbcForm.gridy++;
        formPanel.add(btnCadastrar, gbcForm);

        GridBagConstraints gbcRight = new GridBagConstraints();
        gbcRight.gridx = 0;
        gbcRight.gridy = 0;
        gbcRight.anchor = GridBagConstraints.CENTER;
        gbcRight.insets = new Insets(10, 10, 10, 10);
        rightPanel.add(loginLabel, gbcRight);
        gbcRight.gridy++;
        gbcRight.anchor = GridBagConstraints.WEST;
        rightPanel.add(formPanel, gbcRight);

        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        add(mainPanel);
        setVisible(true);
    }

    public Login() {
        this.dotenv = Dotenv.load();
        initComponents();
    }

    private void cadastrar() {
        JFrame cadastro = new Cadastro();
        cadastro.setVisible(true);
        this.setVisible(false);
    }

    private void login() {
        try {
            // Pegando os valores do formulário
            String email = textoEmail.getText();
            char[] passwordArr = textoSenha.getPassword();
            String password = new String(passwordArr);

            // Criando o JSON da requisição
            JsonObject jsonData = new JsonObject();
            jsonData.addProperty("email", email);
            jsonData.addProperty("password", password);
            String jsonString = jsonData.toString();

            // Configuração da URL da API
            String urlAPI = this.dotenv.get("API_HOST");

            // Tentando login como Manager
            JsonObject isLogged = tryLogin(urlAPI + "/auth/login", jsonString);

            if (isLogged!=null) {
                JFrame frame = new JanelaPrincipal(isLogged);
                JOptionPane.showMessageDialog(this.rootPane,
                        "Login realizado com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
                frame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this.rootPane,
                        "Falha no login! Verifique suas credenciais.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this.rootPane, e.getMessage(), "Erro de Validação", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.rootPane, "Erro inesperado: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JsonObject tryLogin(String urlString, String jsonString) {
        try {
            // Abrindo conexão HTTP
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            // Enviando JSON no corpo da requisição
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Obtendo o código de resposta HTTP
            int statusCode = connection.getResponseCode();
            StringBuilder response = new StringBuilder();

            // Se o login foi bem-sucedido (código 200 OK)
            if (statusCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                JsonObject resp = JsonParser.parseString(response.toString()).getAsJsonObject();
                return resp.get("user").getAsJsonObject();
            }

            // Se não foi bem-sucedido, lê a resposta de erro
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            // Tenta interpretar o erro em JSON debug
            /*
            try {
                JsonObject err = JsonParser.parseString(response.toString()).getAsJsonObject();
                String errorMessage = err.has("message") ? err.get("message").getAsString() : response.toString();
                JOptionPane.showMessageDialog(this.rootPane,
                        "Erro no login: " + errorMessage,
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception jsonException) {
                JOptionPane.showMessageDialog(this.rootPane,
                        "Erro inesperado ao processar a resposta. Código: " + statusCode + "\nResposta: " + response,
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }*/

            connection.disconnect();
            return null;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.rootPane, "Erro ao tentar login: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }


    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    private JTextField textoEmail;
    private JPasswordField textoSenha;
}
