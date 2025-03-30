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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class PerfilFuncionario extends JPanel {
    private JPanel mainPanel;
    private String id;
    private Dotenv dotenv;
    private JRootPane rootPane;
    private JanelaPrincipal frame;
    private JsonObject userData;

    public PerfilFuncionario(JPanel mainPanel, JRootPane rootPane,JsonObject user) {
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
                throw new Exception("Usuario do tipo funcionário");
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
        jLabelCadastro.setText("Funcionário");
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

        jPanel.add(jPanelContent);

    }

    private void getEmployee() {
        this.jTextFieldNome.setText(userData.get("name").getAsString());
        this.jTextFieldEmail.setText(userData.get("email").getAsString());
        this.jTextFieldCPF.setText(userData.get("cpf").getAsString());
        this.jTextFieldSenha.setText(userData.get("password").getAsString());
        this.jPanelButtons.setVisible(false);
        jLabelCadastro.setText("Funcionário");
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

    private JPanel jPanelHeader;
    private JPanel jPanelContent;
    private JPanel jPanel;

    private GridBagConstraints gbc;

    private JLabel profileIcon;
}
