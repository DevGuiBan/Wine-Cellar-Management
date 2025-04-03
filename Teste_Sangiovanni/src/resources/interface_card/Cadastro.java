package resources.interface_card;

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
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Cadastro extends JFrame{

    private Dotenv dotenv;

    private void initComponents() {
        setTitle("Cadastrar");
        setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/images/icon.png"))).getImage());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridLayout(1, 2));

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setLayout(new BorderLayout());


        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.WHITE);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(Box.createVerticalGlue());
        JLabel casaSanGiovanni = new JLabel("Casa San'Giovanni");
        casaSanGiovanni.setFont(new Font("Cinzel", Font.PLAIN, 24));
        casaSanGiovanni.setForeground(new Color(128, 0, 32));
        casaSanGiovanni.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(casaSanGiovanni);
        topPanel.add(Box.createVerticalGlue());
        leftPanel.add(topPanel, BorderLayout.CENTER);


        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(Color.WHITE);
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.X_AXIS));

        ImageIcon icon = null;
        try {
            icon = new ImageIcon(Cadastro.class.getResource("/resources/images/icon.png"));

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

        JLabel cadastrar = new JLabel("Cadastrar-se");
        cadastrar.setFont(new Font("Cormorant Infant", Font.BOLD, 38));
        cadastrar.setForeground(Color.WHITE);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);

        Dimension fieldSize = new Dimension(250, 30);

        JLabel nome = new JLabel("Nome: ");
        nome.setFont(new Font("Cormorant Infant", Font.PLAIN, 18));
        nome.setForeground(Color.WHITE);
        textoNome = new JTextField();
        textoNome.setFont(new Font("Cormorant Infant", Font.PLAIN, 18));
        textoNome.setPreferredSize(fieldSize);

        JLabel cpf = new JLabel("CPF: ");
        cpf.setFont(new Font("Cormorant Infant", Font.PLAIN, 18));
        cpf.setForeground(Color.WHITE);
        textoCPF = new JTextField();
        textoCPF.setFont(new Font("Cormorant Infant", Font.PLAIN, 18));
        textoCPF.setPreferredSize(fieldSize);

        JLabel email = new JLabel("Email: ");
        email.setFont(new Font("Cormorant Infant", Font.PLAIN, 18));
        email.setForeground(Color.WHITE);
        textoEmail = new JTextField();
        textoEmail.setFont(new Font("Cormorant Infant", Font.PLAIN, 18));
        textoEmail.setPreferredSize(fieldSize);

        JLabel senha = new JLabel("Senha: ");
        senha.setFont(new Font("Cormorant Infant", Font.PLAIN, 18));
        senha.setForeground(Color.WHITE);
        textoSenha = new JPasswordField();
        textoSenha.setFont(new Font("Cormorant Infant", Font.PLAIN, 18));
        textoSenha.setPreferredSize(fieldSize);

        JLabel confirmarSenha = new JLabel("Confirmar Senha: ");
        confirmarSenha.setFont(new Font("Cormorant Infant", Font.PLAIN, 18));
        confirmarSenha.setForeground(Color.WHITE);
        textoConfirmarSenha = new JPasswordField();
        textoConfirmarSenha.setFont(new Font("Cormorant Infant", Font.PLAIN, 18));
        textoConfirmarSenha.setPreferredSize(fieldSize);

        JButton btnCadastrar = new JButton("Cadastrar-se");
        btnCadastrar.setFont(new Font("Cormorant Infant", Font.BOLD, 18));
        btnCadastrar.setForeground(Color.BLACK);
        btnCadastrar.setBackground(new Color(255, 235, 43));
        btnCadastrar.setPreferredSize(fieldSize);
        btnCadastrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCadastrar.addActionListener(evt->{
                cadastrar();
        });

        JButton btnJaTenhoConta = new JButton("Já tenho conta");
        btnJaTenhoConta.setFont(new Font("Cormorant Infant", Font.BOLD, 18));
        btnJaTenhoConta.setForeground(Color.WHITE);
        btnJaTenhoConta.setBackground(new Color(128, 0, 32));
        btnJaTenhoConta.setBorder(BorderFactory.createLineBorder(new Color(255, 235, 43), 2));
        btnJaTenhoConta.setPreferredSize(fieldSize);
        btnJaTenhoConta.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnJaTenhoConta.addActionListener(evt->{
            login();
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 10);

        formPanel.add(nome, gbc);
        gbc.gridy++;
        formPanel.add(textoNome, gbc);
        gbc.gridy++;
        formPanel.add(cpf, gbc);
        gbc.gridy++;
        formPanel.add(textoCPF, gbc);
        gbc.gridy++;
        formPanel.add(email, gbc);
        gbc.gridy++;
        formPanel.add(textoEmail, gbc);
        gbc.gridy++;
        formPanel.add(senha, gbc);
        gbc.gridy++;
        formPanel.add(textoSenha, gbc);
        gbc.gridy++;
        formPanel.add(confirmarSenha, gbc);
        gbc.gridy++;
        formPanel.add(textoConfirmarSenha, gbc);
        gbc.gridy++;
        formPanel.add(btnCadastrar, gbc);
        gbc.gridy++;
        formPanel.add(btnJaTenhoConta, gbc);

        GridBagConstraints gbcRight = new GridBagConstraints();
        gbcRight.gridx = 0;
        gbcRight.gridy = 0;
        gbcRight.anchor = GridBagConstraints.CENTER;
        gbcRight.insets = new Insets(10, 10, 10, 10);
        rightPanel.add(cadastrar, gbcRight);

        gbcRight.gridy++;
        gbcRight.anchor = GridBagConstraints.WEST;
        rightPanel.add(formPanel, gbcRight);

        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        add(mainPanel);
        setVisible(true);
    }

    public Cadastro(){
        this.dotenv = Dotenv.load();
        initComponents();
    }

    private void cadastrar(){
        try {
            String name = textoNome.getText();
            String email = textoEmail.getText();
            String cpf = textoCPF.getText();
            char[] senhaArr = textoSenha.getPassword();
            String senha = new String(senhaArr);
            char[] senhaArrConf = textoConfirmarSenha.getPassword();
            String senhaConf = new String(senhaArrConf);

            if(!senha.equals(senhaConf)){
                JOptionPane.showMessageDialog(this.rootPane, "As senhas precisam ser iguais!", "Erro", JOptionPane.ERROR_MESSAGE);
            }


            // Criando o JSON do funcionário
            JsonObject jsonData = new JsonObject();
            jsonData.addProperty("name", name);
            jsonData.addProperty("email", email);
            jsonData.addProperty("cpf", cpf);
            jsonData.addProperty("password", senha);

            // Configurando a conexão HTTP
            String urlAPI = this.dotenv.get("API_HOST");
            URL url = new URL(urlAPI + "/managers");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Enviando a requisição com JSON
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonData.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int statusCode = connection.getResponseCode();
            StringBuilder response = new StringBuilder();

            if (statusCode >= 200 && statusCode < 300) {
                JFrame frame = new Login();
                JOptionPane.showMessageDialog(this.rootPane,
                        "O Gerente foi cadastrado com sucesso!",
                        "Gerente Cadastrado",
                        JOptionPane.INFORMATION_MESSAGE);
                frame.setVisible(true);
                this.setVisible(false);
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
                            "Não foi possível cadastrar o gerente. Verifique os dados e tente novamente!\n" + errorMessage,
                            "Erro no Cadastro",
                            JOptionPane.ERROR_MESSAGE);
                } catch (Exception jsonException) {
                    // Se a resposta não for um JSON válido, exibir a resposta bruta
                    JOptionPane.showMessageDialog(this.rootPane,
                            "Erro inesperado ao processar a resposta da API.\nCódigo: " + statusCode + "\nResposta: " + response,
                            "Erro no Cadastro",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

            connection.disconnect();

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this.rootPane, e.getMessage(), "Erro de Validação", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.rootPane, "Erro inesperado: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void login(){
        JFrame login = new Login();
        this.setVisible(false);
        login.setVisible(true);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Cadastro().setVisible(true);
            }
        });
    }

    private JTextField textoNome;
    private JTextField textoCPF;
    private JTextField textoEmail;
    private JPasswordField textoSenha;
    private JPasswordField textoConfirmarSenha;
}
