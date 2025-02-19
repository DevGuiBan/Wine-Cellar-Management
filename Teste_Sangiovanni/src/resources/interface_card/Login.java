import javax.swing.*;
import java.awt.*;

public class Login {
    public static void main(String[] args) {
        JFrame frame = new JFrame("LOGIN");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridLayout(1, 2));

        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(Color.WHITE);

        JLabel casaSanGiovanni = new JLabel("Casa San'Giovanni");
        casaSanGiovanni.setFont(new Font("Cinzel", Font.PLAIN, 24));

        leftPanel.add(casaSanGiovanni);

        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(new Color(128, 0, 32));

        JLabel login = new JLabel("Login");
        login.setFont(new Font("Cormorant Infant", Font.BOLD, 38));
        login.setForeground(Color.WHITE);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);

        JLabel email = new JLabel("Email: ");
        email.setFont(new Font("Cormorant Infant", Font.PLAIN, 18));
        email.setForeground(Color.WHITE);
        JTextField textoEmail = new JTextField(15);
        textoEmail.setFont(new Font("Cormorant Infant", Font.PLAIN, 18));

        JLabel senha = new JLabel("Senha: ");
        senha.setFont(new Font("Cormorant Infant", Font.PLAIN, 18));
        senha.setForeground(Color.WHITE);
        JTextField textoSenha = new JTextField(15);
        textoSenha.setFont(new Font("Cormorant Infant", Font.PLAIN, 18));

        JLabel esqueciSenha = new JLabel("Esqueci minha senha");
        esqueciSenha.setFont(new Font("Cormorant Infant", Font.PLAIN, 14));
        esqueciSenha.setForeground(new Color(255, 235, 43));

        JButton btnEntrar = new JButton("Entrar");
        btnEntrar.setFont(new Font("Cormorant Infant", Font.BOLD, 18));
        btnEntrar.setForeground(Color.BLACK);
        btnEntrar.setBackground(new Color(255, 235, 43));
        btnEntrar.setPreferredSize(new Dimension(textoEmail.getPreferredSize().width, 30));

        JButton btnCadastrar = new JButton("Cadastrar-se");
        btnCadastrar.setFont(new Font("Cormorant Infant", Font.BOLD, 18));
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setBackground(new Color(128, 0, 32)); // Mesma cor do fundo do painel
        btnCadastrar.setBorder(BorderFactory.createLineBorder(new Color(255, 235, 43), 2));
        btnCadastrar.setPreferredSize(new Dimension(textoEmail.getPreferredSize().width, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        formPanel.add(email, gbc);

        gbc.gridy++;
        formPanel.add(textoEmail, gbc);

        gbc.gridy++;
        formPanel.add(senha, gbc);

        gbc.gridy++;
        formPanel.add(textoSenha, gbc);

        gbc.gridy++;
        formPanel.add(esqueciSenha, gbc);

        gbc.gridy++;
        formPanel.add(btnEntrar, gbc);

        gbc.gridy++;
        formPanel.add(btnCadastrar, gbc);

        GridBagConstraints gbcRight = new GridBagConstraints();
        gbcRight.gridx = 0;
        gbcRight.gridy = 0;
        gbcRight.anchor = GridBagConstraints.CENTER;
        gbcRight.insets = new Insets(20, 10, 20, 10);
        rightPanel.add(login, gbcRight);

        gbcRight.gridy++;
        gbcRight.anchor = GridBagConstraints.WEST;
        rightPanel.add(formPanel, gbcRight);

        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        frame.add(mainPanel);
        frame.setVisible(true);
    }
}
