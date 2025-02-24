package resources.interface_card;

import javax.swing.*;
import java.awt.*;

public class Cadastro {
    public static void main(String[] args) {
        JFrame frame = new JFrame("CADASTRAR");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

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
        JTextField textoNome = new JTextField();
        textoNome.setFont(new Font("Cormorant Infant", Font.PLAIN, 18));
        textoNome.setPreferredSize(fieldSize);

        JLabel cpf = new JLabel("CPF: ");
        cpf.setFont(new Font("Cormorant Infant", Font.PLAIN, 18));
        cpf.setForeground(Color.WHITE);
        JTextField textoCPF = new JTextField();
        textoCPF.setFont(new Font("Cormorant Infant", Font.PLAIN, 18));
        textoCPF.setPreferredSize(fieldSize);

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

        JLabel confirmarSenha = new JLabel("Confirmar Senha: ");
        confirmarSenha.setFont(new Font("Cormorant Infant", Font.PLAIN, 18));
        confirmarSenha.setForeground(Color.WHITE);
        JPasswordField textoConfirmarSenha = new JPasswordField();
        textoConfirmarSenha.setFont(new Font("Cormorant Infant", Font.PLAIN, 18));
        textoConfirmarSenha.setPreferredSize(fieldSize);

        JButton btnCadastrar = new JButton("Cadastrar-se");
        btnCadastrar.setFont(new Font("Cormorant Infant", Font.BOLD, 18));
        btnCadastrar.setForeground(Color.BLACK);
        btnCadastrar.setBackground(new Color(255, 235, 43));
        btnCadastrar.setPreferredSize(fieldSize);

        JButton btnJaTenhoConta = new JButton("Já tenho conta");
        btnJaTenhoConta.setFont(new Font("Cormorant Infant", Font.BOLD, 18));
        btnJaTenhoConta.setForeground(Color.BLACK);
        btnJaTenhoConta.setBackground(new Color(128, 0, 32));
        btnJaTenhoConta.setBorder(BorderFactory.createLineBorder(new Color(255, 235, 43), 2));
        btnJaTenhoConta.setPreferredSize(fieldSize);

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

        frame.add(mainPanel);
        frame.setVisible(true);
    }
}
