package resources.interface_card;

import javax.swing.*;
import java.awt.*;

public class Login {
    public static void main(String[] args) {
        JFrame frame = new JFrame("LOGIN");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

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
        loginLabel.setFont(new Font("Cormorant Infant", Font.BOLD, 38));
        loginLabel.setForeground(Color.WHITE);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);

        Dimension fieldSize = new Dimension(250, 30);

        JLabel email = new JLabel("Email: ");
        email.setFont(new Font("Cormorant Infant", Font.PLAIN, 18));
        email.setForeground(Color.WHITE);
        JTextField textoEmail = new JTextField();
        textoEmail.setFont(new Font("Cormorant Infant", Font.PLAIN, 18));
        textoEmail.setPreferredSize(fieldSize);

        JLabel senha = new JLabel("Senha: ");
        senha.setFont(new Font("Cormorant Infant", Font.PLAIN, 18));
        senha.setForeground(Color.WHITE);
        JPasswordField textoSenha = new JPasswordField();
        textoSenha.setFont(new Font("Cormorant Infant", Font.PLAIN, 18));
        textoSenha.setPreferredSize(fieldSize);

        JLabel esqueciSenha = new JLabel("Esqueci minha senha");
        esqueciSenha.setFont(new Font("Cormorant Infant", Font.PLAIN, 14));
        esqueciSenha.setForeground(new Color(255, 235, 43));

        JButton btnEntrar = new JButton("Entrar");
        btnEntrar.setFont(new Font("Cormorant Infant", Font.BOLD, 18));
        btnEntrar.setForeground(Color.BLACK);
        btnEntrar.setBackground(new Color(255, 235, 43));
        btnEntrar.setPreferredSize(fieldSize);

        JButton btnCadastrar = new JButton("Cadastrar-se");
        btnCadastrar.setFont(new Font("Cormorant Infant", Font.BOLD, 18));
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setBackground(new Color(128, 0, 32));
        btnCadastrar.setBorder(BorderFactory.createLineBorder(new Color(255, 235, 43), 2));
        btnCadastrar.setPreferredSize(fieldSize);

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

        frame.add(mainPanel);
        frame.setVisible(true);
    }
}
