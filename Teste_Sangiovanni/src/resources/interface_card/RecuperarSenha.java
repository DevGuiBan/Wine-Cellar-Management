package resources.interface_card;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.cdimascio.dotenv.Dotenv;

import javax.swing.*;
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

public class RecuperarSenha extends JFrame {
    private static JPanel formPanel;
    private static JPanel rightPanel;
    private static GridBagConstraints gbc;
    private final Dotenv dotenv = Dotenv.load();

    public RecuperarSenha() {
        initComponents();
    }

    private void initComponents() {
        setTitle("RECUPERAR SENHA");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridLayout(1, 2));

        JPanel leftPanel = createLeftPanel();
        rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(new Color(128, 0, 32));

        JLabel titulo = new JLabel("Recuperar Senha");
        titulo.setFont(new Font("Cormorant Infant", Font.BOLD, 50));
        titulo.setForeground(Color.WHITE);

        formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);

        createInitialForm();

        GridBagConstraints gbcRight = new GridBagConstraints();
        gbcRight.gridx = 0;
        gbcRight.gridy = 0;
        gbcRight.anchor = GridBagConstraints.CENTER;
        gbcRight.insets = new Insets(10, 10, 10, 10);
        rightPanel.add(titulo, gbcRight);

        gbcRight.gridy++;
        gbcRight.anchor = GridBagConstraints.WEST;
        rightPanel.add(formPanel, gbcRight);

        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        add(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RecuperarSenha::new);
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(Color.WHITE);

        JLabel casaSanGiovanni = new JLabel("Casa San'Giovanni", SwingConstants.CENTER);
        casaSanGiovanni.setFont(new Font("Cinzel", Font.PLAIN, 26));
        casaSanGiovanni.setForeground(new Color(128, 0, 32));

        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(Color.WHITE);
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.X_AXIS));

        ImageIcon icon = null;
        try {
            icon = new ImageIcon(RecuperarSenha.class.getResource("/resources/images/icon.png"));
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

        leftPanel.add(casaSanGiovanni, BorderLayout.CENTER);
        leftPanel.add(logoPanel, BorderLayout.SOUTH);

        return leftPanel;
    }

    private void createInitialForm() {
        formPanel.removeAll();
        Dimension fieldSize = new Dimension(250, 30);

        JLabel nome = new JLabel("Nome: ");
        nome.setFont(new Font("Cormorant Infant", Font.PLAIN, 23));
        nome.setForeground(Color.WHITE);
        JTextField nomeTexto = new JTextField();
        nomeTexto.setFont(new Font("Cormorant Infant", Font.PLAIN, 20));
        nomeTexto.setPreferredSize(fieldSize);

        JLabel cpf = new JLabel("CPF: ");
        cpf.setFont(new Font("Cormorant Infant", Font.PLAIN, 23));
        cpf.setForeground(Color.WHITE);
        JTextField cpfTexto = new JTextField();
        cpfTexto.setFont(new Font("Cormorant Infant", Font.PLAIN, 20));
        cpfTexto.setPreferredSize(fieldSize);

        JLabel email = new JLabel("Email: ");
        email.setFont(new Font("Cormorant Infant", Font.PLAIN, 23));
        email.setForeground(Color.WHITE);
        JTextField textoEmail = new JTextField();
        textoEmail.setFont(new Font("Cormorant Infant", Font.PLAIN, 20));
        textoEmail.setPreferredSize(fieldSize);

        JButton btnEnviar = new JButton("Verificar");
        btnEnviar.setFont(new Font("Cormorant Infant", Font.BOLD, 25));
        btnEnviar.setForeground(Color.BLACK);
        btnEnviar.setBackground(new Color(255, 235, 43));
        btnEnviar.setPreferredSize(fieldSize);

        btnEnviar.addActionListener(e -> createPasswordForm(nomeTexto.getText(), textoEmail.getText(), cpfTexto.getText()));

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 10);

        formPanel.add(nome, gbc);
        gbc.gridy++;
        formPanel.add(nomeTexto, gbc);
        gbc.gridy++;
        formPanel.add(cpf, gbc);
        gbc.gridy++;
        formPanel.add(cpfTexto, gbc);
        gbc.gridy++;
        formPanel.add(email, gbc);
        gbc.gridy++;
        formPanel.add(textoEmail, gbc);
        gbc.gridy++;
        formPanel.add(btnEnviar, gbc);

        formPanel.revalidate();
        formPanel.repaint();
    }

    private void createPasswordForm(String nome, String email, String cpf) {
        JsonObject manager = null;
        try {
            JsonObject jsonData = new JsonObject();
            jsonData.addProperty("name", nome);
            jsonData.addProperty("email", email);
            jsonData.addProperty("cpf", cpf);
            jsonData.addProperty("password", "1");

            String urlAPI = this.dotenv.get("API_HOST");
            URL url = new URL(urlAPI + "/managers/verify");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonData.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int statusCode = connection.getResponseCode();

            if (statusCode >= 200 && statusCode < 300) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    manager = JsonParser.parseString(response.toString()).getAsJsonObject();
                }
            } else {
                StringBuilder response = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8))) {
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                }
                JsonObject err = JsonParser.parseString(response.toString()).getAsJsonObject();
                String errorMessage = err.has("message") && !err.get("message").isJsonNull() ? err.get("message").getAsString() : response.toString();
                JOptionPane.showMessageDialog(this, "Erro ao verificar: " + errorMessage, "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar com a API: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (manager == null || !manager.has("id")) {
            JOptionPane.showMessageDialog(this, "ID do gerente não retornado pela API.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        formPanel.removeAll();
        Dimension fieldSize = new Dimension(250, 30);

        JLabel novaSenhaLabel = new JLabel("Nova Senha: ");
        novaSenhaLabel.setFont(new Font("Cormorant Infant", Font.PLAIN, 23));
        novaSenhaLabel.setForeground(Color.WHITE);
        JPasswordField novaSenhaField = new JPasswordField();
        novaSenhaField.setFont(new Font("Cormorant Infant", Font.PLAIN, 20));
        novaSenhaField.setPreferredSize(fieldSize);

        JLabel confirmarSenhaLabel = new JLabel("Confirmar Senha: ");
        confirmarSenhaLabel.setFont(new Font("Cormorant Infant", Font.PLAIN, 23));
        confirmarSenhaLabel.setForeground(Color.WHITE);
        JPasswordField confirmarSenhaField = new JPasswordField();
        confirmarSenhaField.setFont(new Font("Cormorant Infant", Font.PLAIN, 20));
        confirmarSenhaField.setPreferredSize(fieldSize);

        JButton btnConfirmar = new JButton("Confirmar");
        btnConfirmar.setFont(new Font("Cormorant Infant", Font.BOLD, 25));
        btnConfirmar.setForeground(Color.BLACK);
        btnConfirmar.setBackground(new Color(255, 235, 43));
        btnConfirmar.setPreferredSize(fieldSize);

        JsonObject finalManager = manager;
        btnConfirmar.addActionListener(evt -> {
            String senha = new String(novaSenhaField.getPassword());
            String senhaConfirm = new String(confirmarSenhaField.getPassword());
            if (senha.equals(senhaConfirm)) {
                changePassword(senha, finalManager.get("id").getAsString());
            } else {
                JOptionPane.showMessageDialog(this, "As senhas não coincidem.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 10);

        formPanel.add(novaSenhaLabel, gbc);
        gbc.gridy++;
        formPanel.add(novaSenhaField, gbc);
        gbc.gridy++;
        formPanel.add(confirmarSenhaLabel, gbc);
        gbc.gridy++;
        formPanel.add(confirmarSenhaField, gbc);
        gbc.gridy++;
        formPanel.add(btnConfirmar, gbc);

        formPanel.revalidate();
        formPanel.repaint();
    }

    private void changePassword(String password, String id) {
        try {
            String urlAPI = this.dotenv.get("API_HOST");
            String urlString = urlAPI + "/managers/reset-password?password=" + URLEncoder.encode(password, StandardCharsets.UTF_8) +
                    "&id=" + URLEncoder.encode(id, StandardCharsets.UTF_8);
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            int responseCode = connection.getResponseCode();
            if (responseCode >= 200 && responseCode < 300) {
                JOptionPane.showMessageDialog(this, "Senha alterada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                JFrame login = new Login();
                login.setVisible(true);
                dispose();
            } else {
                StringBuilder response = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                }
                JOptionPane.showMessageDialog(this, "Erro ao alterar senha: " + response.toString(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (
                Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar com a API: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}