package resources.interface_card;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.cdimascio.dotenv.Dotenv;
import resources.interfaces.ProductType;
import resources.interfaces.Employee;

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
import java.util.Objects;

public class CadastrarFuncionario extends JPanel {
    private JPanel mainPanel;
    private String id;
    private Dotenv dotenv;
    private JRootPane rootPane;
    private JanelaPrincipal frame;

    public CadastrarFuncionario(JPanel mainPanel,JRootPane rootPane) {
        this.mainPanel = mainPanel;
        this.rootPane = rootPane;
        this.frame = (JanelaPrincipal) SwingUtilities.getWindowAncestor(rootPane);
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
        jLabelRua = new JLabel();
        jLabelBairro = new JLabel();
        jLabelNumero = new JLabel();
        jLabelCidade = new JLabel();

        // Adicionar novas labels de endereço

        jTextFieldNome = new JTextField();
        jTextFieldEmail = new JTextField();
        try{
            jTextFieldTelefone = new javax.swing.JFormattedTextField(new MaskFormatter("(##) #####-####"));
            jTextFieldCPF = new javax.swing.JFormattedTextField(new MaskFormatter("###.###.###-##"));
            jTextFieldDataNascimento = new javax.swing.JFormattedTextField(new MaskFormatter("##/##/####"));
            jTextFieldCidade = new javax.swing.JFormattedTextField(new MaskFormatter("-##"));
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
        setBackground(new Color(243, 243, 223));
        setPreferredSize(new Dimension(1366, 650)); // tamanho do painel que tem tudo
        setLayout(new FlowLayout(FlowLayout.CENTER,30,30));

        gbc.fill = GridBagConstraints.HORIZONTAL; // Permitir redimensionamento horizontal
        gbc.anchor = GridBagConstraints.WEST; // Alinhamento à esquerda
        Dimension fieldSize = new Dimension(400, 40); // tamanho padrão dos campos

        // painel branco que vai conter tudo
        jPanel.setPreferredSize(new Dimension(1200, 600));
        jPanel.setBackground(Color.WHITE);
        jPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // painel do texto de cadastro de produto(header)
        jPanelHeader.setLayout(new BoxLayout(jPanelHeader, BoxLayout.Y_AXIS));
        jPanelHeader.setBackground(new Color(255, 255, 255));
        jPanelHeader.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanelHeader.setPreferredSize(new Dimension(1200, 50));

        jLabelCadastro.setFont(new Font("Cormorant Garamond SemiBold", 0, 30));
        jLabelCadastro.setText("Cadastrar Funcionário");
        jLabelCadastro.setAlignmentX(Component.CENTER_ALIGNMENT); // centralizar

        jSeparator.setBackground(new Color(128, 0, 32));
        jSeparator.setForeground(new Color(128, 0, 32));
        jSeparator.setPreferredSize(new Dimension(200, 8));
        jSeparator.setMaximumSize(new Dimension(230, 8));
        jSeparator.setAlignmentX(Component.CENTER_ALIGNMENT); // centralizar

        jPanelHeader.setOpaque(false);
        jPanelHeader.add(jLabelCadastro);
        jPanelHeader.add(jSeparator); // adiciona no painel

        // adiciona o painelHeader no principal
        jPanel.add(jPanelHeader);

        // painel dos campos
        jPanelContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanelContent.setPreferredSize(new Dimension(1200, 450));
        jPanelContent.setBackground(new Color(255, 255, 255));

        jLabelNome.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelNome.setText("Nome:");
        jLabelNome.setForeground(Color.BLACK);
        gbc.gridx = 0; // coluna
        gbc.gridy = 0; // linha
        gbc.insets = new Insets(0, 0, 0, 100); // adicionar espaçamento por componente
        jPanelContent.add(jLabelNome, gbc); // sempre adicionar neste formato por conta do grid

        jTextFieldNome.setBackground(new Color(255, 255, 255));
        jTextFieldNome.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jTextFieldNome.setFont(new Font("Cormorant Garamond", 1, 18));
        jTextFieldNome.setPreferredSize(fieldSize);
        jTextFieldNome.setForeground(Color.BLACK);
        jTextFieldNome.setText("Nome do Funcionário");
        jTextFieldNome.addFocusListener(new FocusAdapter() { // adicionar um evento ao clicar no campo
            @Override
            public void focusGained(FocusEvent e) { // clicou
                if (jTextFieldNome.getText().equals("Nome do Funcionário")) {
                    jTextFieldNome.setText("");
                    jTextFieldNome.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) { // clicou em outra coisa
                if (jTextFieldNome.getText().isEmpty()) {
                    jTextFieldNome.setText("Nome do Funcionário");
                    jTextFieldNome.setForeground(Color.BLACK);
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 100);
        jPanelContent.add(jTextFieldNome, gbc);

        jLabelCPF.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelCPF.setForeground(Color.BLACK);
        jLabelCPF.setText("CPF:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 0, 100);
        jPanelContent.add(jLabelCPF, gbc);

        jTextFieldCPF.setBackground(new Color(255, 255, 255));
        jTextFieldCPF.setBackground(Color.WHITE);
        jTextFieldCPF.setForeground(Color.BLACK);
        jTextFieldCPF.setFont(new Font("Cormorant Infant", Font.BOLD, 18));
        jTextFieldCPF.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jTextFieldCPF.setPreferredSize(fieldSize);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 20, 100);
        jPanelContent.add(jTextFieldCPF, gbc);

        jLabelTelefone.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelTelefone.setText("Telefone:");
        jLabelTelefone.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        jPanelContent.add(jLabelTelefone, gbc);

        jTextFieldTelefone.setBackground(new Color(255, 255, 255));
        jTextFieldTelefone.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jTextFieldTelefone.setFont(new Font("Cormorant Infant", 1, 18));
        jTextFieldTelefone.setForeground(Color.BLACK);
        jTextFieldTelefone.setPreferredSize(fieldSize);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 20, 0);
        jPanelContent.add(jTextFieldTelefone, gbc);

        jLabelEmail.setText("E-mail:");
        jLabelEmail.setForeground(Color.BLACK);
        jLabelEmail.setFont(new Font("Cormorant Garamond", 1, 18));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 0, 0, 0);
        jPanelContent.add(jLabelEmail, gbc);

        jTextFieldEmail.setBackground(Color.WHITE);
        jTextFieldEmail.setFont(new Font("Cormorant Garamond", 1, 18));
        jTextFieldEmail.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jTextFieldEmail.setPreferredSize(fieldSize);
        jTextFieldEmail.setForeground(Color.BLACK);
        jTextFieldEmail.setText("exemplo@email.com");
        jTextFieldEmail.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (jTextFieldEmail.getText().equals("exemplo@email.com")) {
                    jTextFieldEmail.setText("");
                    jTextFieldEmail.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (jTextFieldEmail.getText().isEmpty()) {
                    jTextFieldEmail.setText("exemplo@email.com");
                    jTextFieldEmail.setForeground(Color.BLACK);
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.insets = new Insets(0, 0, 20, 0);
        jPanelContent.add(jTextFieldEmail, gbc);

        jLabelDataNascimento.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelDataNascimento.setText("Data de Nascimento:");
        jLabelDataNascimento.setForeground(Color.BLACK);
        jLabelDataNascimento.setBackground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.insets = new Insets(0, 0, 0, 100);
        jPanelContent.add(jLabelDataNascimento, gbc);

        jTextFieldDataNascimento.setBackground(Color.WHITE);
        jTextFieldDataNascimento.setFont(new Font("Cormorant Infant", 1, 18));
        jTextFieldDataNascimento.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jTextFieldDataNascimento.setPreferredSize(fieldSize);
        jTextFieldDataNascimento.setForeground(Color.BLACK);
        jTextFieldDataNascimento.setText("DD/MM/AAAA");
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.insets = new Insets(0, 0, 20, 100);
        jPanelContent.add(jTextFieldDataNascimento, gbc);

        jLabelRua.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelRua.setText("Rua:");
        jLabelRua.setForeground(Color.BLACK);
        jLabelRua.setBackground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 100);
        jPanelContent.add(jLabelRua, gbc);

        jTextFieldRua.setBackground(Color.WHITE);
        jTextFieldRua.setFont(new Font("Cormorant Infant", 1, 18));
        jTextFieldRua.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jTextFieldRua.setPreferredSize(fieldSize);
        jTextFieldRua.setForeground(Color.BLACK);
        jTextFieldRua.setText("Rua");
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 100);
        jPanelContent.add(jTextFieldRua, gbc);

        jLabelBairro.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelBairro.setText("Bairro:");
        jLabelBairro.setForeground(Color.BLACK);
        jLabelBairro.setBackground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 0, 100);
        jPanelContent.add(jLabelBairro, gbc);

        jTextFieldBairro.setBackground(Color.WHITE);
        jTextFieldBairro.setFont(new Font("Cormorant Infant", 1, 18));
        jTextFieldBairro.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jTextFieldBairro.setPreferredSize(fieldSize);
        jTextFieldBairro.setForeground(Color.BLACK);
        jTextFieldBairro.setText("Bairro");
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 20, 100);
        jPanelContent.add(jTextFieldBairro, gbc);

        jLabelNumero.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelNumero.setText("Número:");
        jLabelNumero.setForeground(Color.BLACK);
        jLabelNumero.setBackground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 0, 100);
        jPanelContent.add(jLabelNumero, gbc);

        jTextFieldNumero.setBackground(Color.WHITE);
        jTextFieldNumero.setFont(new Font("Cormorant Infant", 1, 18));
        jTextFieldNumero.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jTextFieldNumero.setPreferredSize(fieldSize);
        jTextFieldNumero.setForeground(Color.BLACK);
        jTextFieldNumero.setText("Nº");
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 20, 100);
        jPanelContent.add(jTextFieldNumero, gbc);

        jLabelCidade.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelCidade.setText("Cidade-UF:");
        jLabelCidade.setForeground(Color.BLACK);
        jLabelCidade.setBackground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 0, 0, 100);
        jPanelContent.add(jLabelCidade, gbc);

        jTextFieldCidade.setBackground(Color.WHITE);
        jTextFieldCidade.setFont(new Font("Cormorant Infant", 1, 18));
        jTextFieldCidade.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jTextFieldCidade.setPreferredSize(fieldSize);
        jTextFieldCidade.setForeground(Color.BLACK);
        jTextFieldCidade.setText("Cidade-UF");
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.insets = new Insets(0, 0, 20, 100);
        jPanelContent.add(jTextFieldCidade, gbc);

        jButtonCancelar.setBackground(new Color(225, 225, 200));
        jButtonCancelar.setFont(new Font("Cormorant Garamond Bold", 1, 18));
        jButtonCancelar.setText("Cancelar");
        jButtonCancelar.setFocusPainted(false);
        jButtonCancelar.setBorder(new EmptyBorder(5,20,5,20));
        jButtonCancelar.addActionListener(e -> {
            this.reset();
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            cl.show(mainPanel, "listar_funcionario");
        });

        jButtonCadastrar.setBackground(new Color(0, 128, 17));
        jButtonCadastrar.setFont(new Font("Cormorant Garamond Bold", 1, 18));
        jButtonCadastrar.setForeground(new Color(255, 255, 200));
        jButtonCadastrar.setText("Cadastrar");
        jButtonCadastrar.setFocusPainted(false);
        jButtonCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (id == null) {
                    cadastrarFuncionario();
                } else  {
                    editarFuncionario();
                }
            }
        });


        jPanel.add(jPanelContent);

        jPanelButtons.setBackground(new Color(255, 255, 255));
        jPanelButtons.setPreferredSize(new Dimension(1200, 50));
        jPanelButtons.add(jButtonCancelar);
        jPanelButtons.add(jButtonCadastrar);
        jPanelButtons.setLayout(new FlowLayout(FlowLayout.RIGHT,10,10));
        jPanelButtons.setBorder(new EmptyBorder(5,10,5,140));

        jPanel.add(jPanelButtons);

        add(jPanel);
    }

    private void getEmployee(){
        try {
            String urlAPI = this.dotenv.get("API_HOST");
            String encodedId = URLEncoder.encode(this.id, StandardCharsets.UTF_8.toString());
            URL url = new URL(urlAPI + "/employee/"+this.id);
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
                JsonObject employees = JsonParser.parseString(response.toString()).getAsJsonObject();

                jTextFieldNome.setText(employees.get("name").getAsString());
                jTextFieldTelefone.setText(employees.get("phone_number").getAsString());
                jTextFieldEmail.setText(employees.get("email").getAsString());
                jTextFieldDataNascimento.setText(employees.get("date_birth").getAsString());
                jTextFieldCPF.setText(employees.get("cpf").getAsString());
                jTextFieldRua.setText(employees.get("street").getAsString());
                jTextFieldBairro.setText(employees.get("district").getAsString());
                jTextFieldNumero.setText(employees.get("number").getAsString());
                jTextFieldCidade.setText(employees.get("city").getAsString());

                connection.disconnect();
            }
        }
        catch (Exception e) {
            JOptionPane.showOptionDialog(rootPane,
                    "Não foi possivel carregar os dados do funcionário!\n"+e.getMessage(),
                    "Funcionário Não Encontrado",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.ERROR_MESSAGE,
                    null,null,null);
        }
    }

    public void setId(String id) {
        this.id = id;
        this.getEmployee();
        this.jButtonCadastrar.setText("Atualizar");
        this.jLabelCadastro.setText("Editar Funcionário");
    }

    public void reset(){
        jTextFieldNome.setText("Nome do Funcionário");
        jTextFieldTelefone.setText(null);
        jTextFieldEmail.setText("exemplo@email.com");
        jTextFieldDataNascimento.setText("DD/MM/AAAA");
        jTextFieldCPF.setText(null);
        jTextFieldRua.setText("Rua");
        jTextFieldBairro.setText("Bairro");
        jTextFieldNumero.setText("Nº");
        jTextFieldCidade.setText("Cidade-UF");
        this.jButtonCadastrar.setText("Cadastrar");
        this.jLabelCadastro.setText("Cadastrar Funcionário");
    }

    private void cadastrarFuncionario(){
        try {
            String name = jTextFieldNome.getText();
            String cpf = jTextFieldCPF.getText();
            String email = jTextFieldEmail.getText();
            String date_birth = jTextFieldDataNascimento.getText();
            String street = jTextFieldRua.getText();
            String district = jTextFieldBairro.getText();
            String number = jTextFieldNumero.getText();
            String city = jTextFieldCidade.getText();
            String phone_number = jTextFieldTelefone.getText();


            JsonObject jsonData = new JsonObject();
            jsonData.addProperty("name", name);
            jsonData.addProperty("cpf", cpf);
            jsonData.addProperty("email", email);
            jsonData.addProperty("date_birth", date_birth);
            jsonData.addProperty("street", street);
            jsonData.addProperty("district", district);
            jsonData.addProperty("number", number);
            jsonData.addProperty("city", city);
            jsonData.addProperty("phone_number", phone_number);

            // making the request
            String urlAPI = this.dotenv.get("API_HOST");
            URL url = new URL(urlAPI + "/employee");
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
                this.frame.showCard("listar_fonecedores");
                JOptionPane.showOptionDialog(this.rootPane,
                        "O Funcionário foi cadastrado com sucesso!",
                        "Funcionário Cadastrado",
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
                JOptionPane.showOptionDialog(this.rootPane,
                        "Não foi possível cadastrar o Funcionário, verifique as informações dos campos e tente novamente!\n" + response.toString(),
                        "Funcionário Não Cadastrado",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,null,null);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.rootPane, e.getMessage());
        }
    }

    private void editarFuncionario(){
        try {
            String name = jTextFieldNome.getText();
            String cpf = jTextFieldCPF.getText();
            String email = jTextFieldEmail.getText();
            String date_birth = jTextFieldDataNascimento.getText();
            String street = jTextFieldRua.getText();
            String district = jTextFieldBairro.getText();
            String number = jTextFieldNumero.getText();
            String city = jTextFieldCidade.getText();
            String phone_number = jTextFieldTelefone.getText();


            JsonObject jsonData = new JsonObject();
            jsonData.addProperty("name", name);
            jsonData.addProperty("cpf", cpf);
            jsonData.addProperty("email", email);
            jsonData.addProperty("date_birth", date_birth);
            jsonData.addProperty("street", street);
            jsonData.addProperty("district", district);
            jsonData.addProperty("number", number);
            jsonData.addProperty("city", city);
            jsonData.addProperty("phone_number", phone_number);

            // making the request
            String urlAPI = this.dotenv.get("API_HOST");
            URL url = new URL(urlAPI + "/employee/"+this.id);
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
                this.frame.showCard("listar_funcionario");
                JOptionPane.showOptionDialog(this.rootPane,
                        "O Funcionário foi atualizado com sucesso!",
                        "Funcionário Atualizado",
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
                JOptionPane.showOptionDialog(this.rootPane,
                        "Não foi possível atualizar o Funcionário, verifique as informações dos campos e tente novamente!\n" + response.toString(),
                        "Funcionário Não Atualizado",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,null,null);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.rootPane, e.getMessage());
        }
    }

    // componentes que vão ser usados na tela, só o essencial e com os nomes certinhos
    private JLabel jLabelCadastro;
    private JLabel jLabelNome;
    private JLabel jLabelTelefone;
    private JLabel jLabelCPF;
    private JLabel jLabelEmail;
    private JLabel jLabelDataNascimento;
    private JLabel jLabelRua;
    private JLabel jLabelBairro;
    private JLabel jLabelNumero;
    private JLabel jLabelCidade;

    private JTextField jTextFieldNome;
    private JTextField jTextFieldTelefone;
    private JTextField jTextFieldCPF;
    private JTextField jTextFieldEmail;
    private JTextField jTextFieldDataNascimento;
    private JTextField jTextFieldRua;
    private JTextField jTextFieldBairro;
    private JTextField jTextFieldNumero;
    private JTextField jTextFieldCidade;

    private JButton jButtonCancelar;
    private JButton jButtonCadastrar;

    private JPanel jPanelHeader;
    private JPanel jPanelButtons;
    private JPanel jPanelContent;
    private JPanel jPanel;

    private GridBagConstraints gbc;

    private JSeparator jSeparator;
}
