package resources.interface_card;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import javax.swing.border.MatteBorder;
import javax.swing.text.MaskFormatter;
import com.google.gson.JsonObject;
import io.github.cdimascio.dotenv.Dotenv;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.io.*;

public class CadastroClienteDialog extends JDialog {
    private JLabel jLabelTelefone, jLabelCPF, jLabelEmail, jLabelDataNascimento,
            jLabelCadastro, jLabelNome, jLabelRua, jLabelBairro,
            jLabelNumero, jLabelCidade, jLabelUF;
    private JTextField jTextFieldNome, jTextFieldEmail, jTextFieldRua,
            jTextFieldBairro, jTextFieldNumero, jTextFieldCidade;
    private JFormattedTextField jTextFieldTelefone, jTextFieldCPF, jTextFieldDataNascimento;
    private JComboBox<UF> jComboBoxUF;
    private JButton jButtonCadastrar, jButtonCancelar;
    private JPanel jPanelButtons, jPanelContent, jPanelHeader, jPanel;
    private JSeparator jSeparator;
    private GridBagConstraints gbc;
    private final Dotenv dotenv = Dotenv.load();
    private boolean result;

    public CadastroClienteDialog(Frame parent) {
        super(parent, "Cadastrar Cliente", true);
        initComponentes();
        setLocationRelativeTo(parent);
    }

    private void initComponentes() {
        // Inicialização dos componentes
        jLabelTelefone = new JLabel("Telefone:");
        jLabelCPF = new JLabel("CPF:");
        jLabelEmail = new JLabel("E-mail:");
        jLabelDataNascimento = new JLabel("Data de Nascimento:");
        jLabelCadastro = new JLabel("Cadastrar Cliente");
        jLabelNome = new JLabel("Nome:");
        jLabelRua = new JLabel("Rua:");
        jLabelBairro = new JLabel("Bairro:");
        jLabelNumero = new JLabel("Número:");
        jLabelCidade = new JLabel("Cidade:");
        jLabelUF = new JLabel("UF:");

        jTextFieldNome = new JTextField();
        jTextFieldEmail = new JTextField();
        jTextFieldRua = new JTextField();
        jTextFieldBairro = new JTextField();
        jTextFieldNumero = new JTextField();
        jTextFieldCidade = new JTextField();
        jComboBoxUF = new JComboBox<>();

        try {
            jTextFieldTelefone = new JFormattedTextField(new MaskFormatter("(##) #####-####"));
            jTextFieldCPF = new JFormattedTextField(new MaskFormatter("###.###.###-##"));
            jTextFieldDataNascimento = new JFormattedTextField(new MaskFormatter("##/##/####"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        jButtonCadastrar = new JButton("Cadastrar");
        jButtonCancelar = new JButton("Cancelar");

        jPanelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        jPanelContent = new JPanel(new GridBagLayout());
        jPanelButtons.setBackground(Color.WHITE);
        jPanelContent.setBackground(Color.WHITE);
        jPanelHeader = new JPanel();
        jPanel = new JPanel();
        gbc = new GridBagConstraints();
        jSeparator = new JSeparator();

        // Configuração do dialog
        setSize(900, 600);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(243, 243, 223));

        // Painel principal
        jPanel.setPreferredSize(new Dimension(850, 550));
        jPanel.setBackground(Color.WHITE);
        jPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel.setLayout(new BorderLayout());

        // Header
        jPanelHeader.setLayout(new BoxLayout(jPanelHeader, BoxLayout.Y_AXIS));
        jPanelHeader.setBackground(Color.WHITE);
        jPanelHeader.setPreferredSize(new Dimension(850, 50));

        jLabelCadastro.setFont(new Font("Cormorant Garamond SemiBold", 0, 30));
        jLabelCadastro.setAlignmentX(Component.CENTER_ALIGNMENT);

        jSeparator.setBackground(new Color(128, 0, 32));
        jSeparator.setForeground(new Color(128, 0, 32));
        jSeparator.setPreferredSize(new Dimension(200, 8));
        jSeparator.setAlignmentX(Component.CENTER_ALIGNMENT);

        jPanelHeader.add(jLabelCadastro);
        jPanelHeader.add(jSeparator);

        // Configuração dos campos
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        Dimension fieldSize = new Dimension(350, 40);

        configureField(jLabelNome, jTextFieldNome, 0, 0, fieldSize);
        configureField(jLabelTelefone, jTextFieldTelefone, 0, 2, fieldSize);
        configureField(jLabelCPF, jTextFieldCPF, 0, 4, fieldSize);
        configureField(jLabelEmail, jTextFieldEmail, 0, 6, fieldSize);
        configureField(jLabelDataNascimento, jTextFieldDataNascimento, 0, 8, fieldSize);
        configureField(jLabelRua, jTextFieldRua, 1, 0, fieldSize);
        configureField(jLabelBairro, jTextFieldBairro, 1, 2, fieldSize);
        configureField(jLabelNumero, jTextFieldNumero, 1, 4, fieldSize);
        configureField(jLabelCidade, jTextFieldCidade, 1, 6, fieldSize);
        configureField(jLabelUF, jComboBoxUF, 1, 8, fieldSize);

        // Configuração do ComboBox UF
        for (UF uf : UF.values()) {
            jComboBoxUF.addItem(uf);
        }

        // Botões
        jButtonCancelar.setBackground(new Color(225, 225, 200));
        jButtonCancelar.setFont(new Font("Cormorant Garamond Bold", 1, 18));
        jButtonCancelar.addActionListener(e -> dispose());

        jButtonCadastrar.setBackground(new Color(0, 128, 17));
        jButtonCadastrar.setForeground(new Color(255, 255, 200));
        jButtonCadastrar.setFont(new Font("Cormorant Garamond Bold", 1, 18));
        jButtonCadastrar.addActionListener(e -> cadastrarCliente());

        jPanelButtons.add(jButtonCancelar);
        jPanelButtons.add(jButtonCadastrar);

        // Montagem do layout
        jPanel.add(jPanelHeader, BorderLayout.NORTH);
        jPanel.add(jPanelContent, BorderLayout.CENTER);
        jPanel.add(jPanelButtons, BorderLayout.SOUTH);
        add(jPanel, BorderLayout.CENTER);
    }

    private void configureField(JLabel label, JComponent field, int gridx, int gridy, Dimension size) {
        label.setFont(new Font("Cormorant Garamond", 1, 18));
        label.setForeground(Color.BLACK);
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.insets = new Insets(0, gridx == 0 ? 0 : 50, 0, 50);
        jPanelContent.add(label, gbc);

        field.setPreferredSize(size);
        field.setFont(new Font("Cormorant Infant", 1, 18));
        if (field instanceof JTextField || field instanceof JComboBox) {
            field.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        }
        gbc.gridy = gridy + 1;
        gbc.insets = new Insets(0, gridx == 0 ? 0 : 50, 20, 50);
        jPanelContent.add(field, gbc);
    }

    private void cadastrarCliente() {
        try {
            String name = jTextFieldNome.getText();
            if (name.trim().isEmpty() || name.equals("Nome Completo")) {
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
                    jComboBoxUF.getSelectedItem());

            JsonObject jsonData = new JsonObject();
            jsonData.addProperty("name", name);
            jsonData.addProperty("email", email);
            jsonData.addProperty("phoneNumber", phone_number);
            jsonData.addProperty("address", address);
            jsonData.addProperty("cpf", cpf);
            jsonData.addProperty("dateBirth", dataString);

            String urlAPI = this.dotenv.get("API_HOST");
            URL url = new URL(urlAPI + "/client");
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
                JOptionPane.showMessageDialog(this,
                        "O Cliente foi cadastrado com sucesso!",
                        "Cliente Cadastrado",
                        JOptionPane.INFORMATION_MESSAGE);
                this.result = true;
                dispose();
            } else {
                StringBuilder response = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8))) {
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                }
                JOptionPane.showMessageDialog(this,
                        "Não foi possível cadastrar o cliente: " + response.toString(),
                        "Erro no Cadastro",
                        JOptionPane.ERROR_MESSAGE);
                this.result = false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
            this.result = false;
        }
    }

    public boolean getResult(){
        return this.result;
    }

    // Para testar o dialog
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JButton btn = new JButton("Abrir Cadastro");
            btn.addActionListener(e -> new CadastroClienteDialog(frame).setVisible(true));
            frame.add(btn);
            frame.pack();
            frame.setVisible(true);
        });
    }
}
