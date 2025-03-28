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
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PerfilGerente extends JPanel {
    private JPanel mainPanel;
    private String id;
    private Dotenv dotenv;
    private JRootPane framePrincipal;
    private JanelaPrincipal frame;

    public PerfilGerente(JPanel mainPanel,JRootPane framePrincipal) {
        this.mainPanel = mainPanel;
        this.framePrincipal = framePrincipal;
        this.frame = (JanelaPrincipal) SwingUtilities.getWindowAncestor(framePrincipal);
        this.dotenv = Dotenv.load();
        this.initComponentes();
    }

    private void initComponentes(){
        // inicialização de variáveis
        jLabelEmail = new JLabel();
        jLabelNome = new JLabel();
        jTextFieldNome = new JTextField();

        jTextFieldEmail = new JTextField();

        jButtonCadastrar = new JButton();

        jPanelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        jPanelContent = new JPanel(new GridBagLayout());
        jPanelHeader = new JPanel();
        jPanel = new JPanel();

        gbc = new GridBagConstraints();

        jSeparator = new JSeparator();

        // configuração do card
        this.setBackground(new Color(243, 243, 223));
        this.setPreferredSize(new Dimension(1366, 650)); // tamanho do painel que tem tudo
        this.setLayout(new FlowLayout(FlowLayout.CENTER,30,30));

        this.gbc.fill = GridBagConstraints.HORIZONTAL; // Permitir redimensionamento horizontal
        this.gbc.anchor = GridBagConstraints.WEST; // Alinhamento à esquerda
        Dimension fieldSize = new Dimension(400, 40); // tamanho padrão dos campos

        // painel branco que vai conter tudo
        this.jPanel.setPreferredSize(new Dimension(1200, 600));
        this.jPanel.setBackground(Color.WHITE);
        this.jPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // painel do texto de cadastro de produto(header)
        this.jPanelHeader.setLayout(new BoxLayout(this.jPanelHeader, BoxLayout.Y_AXIS));
        this.jPanelHeader.setBackground(new Color(255, 255, 255));
        this.jPanelHeader.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.jPanelHeader.setPreferredSize(new Dimension(1200, 50));

        this.jLabelCadastro.setFont(new Font("Cormorant Garamond SemiBold", 0, 30));
        this.jLabelCadastro.setText("Perfil - Gerente");
        this.jLabelCadastro.setAlignmentX(Component.CENTER_ALIGNMENT); // centralizar

        this.jSeparator.setBackground(new Color(128, 0, 32));
        this.jSeparator.setForeground(new Color(128, 0, 32));
        this.jSeparator.setPreferredSize(new Dimension(200, 8));
        this.jSeparator.setMaximumSize(new Dimension(230, 8));
        this.jSeparator.setAlignmentX(Component.CENTER_ALIGNMENT); // centralizar

        this.jPanelHeader.setOpaque(false);
        this.jPanelHeader.add(this.jLabelCadastro);
        this.jPanelHeader.add(this.jSeparator); // adiciona no painel

        // adiciona o painelHeader no principal
        this.jPanel.add(this.jPanelHeader);

        // painel dos campos
        this.jPanelContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.jPanelContent.setPreferredSize(new Dimension(1200, 450));
        this.jPanelContent.setBackground(new Color(255, 255, 255));

        this.jLabelNome.setFont(new Font("Cormorant Garamond", 1, 18));
        this.jLabelNome.setText("Nome:");
        this.jLabelNome.setForeground(Color.BLACK);
        this.gbc.gridx = 0; // coluna
        this.gbc.gridy = 0; // linha
        this.gbc.insets = new Insets(0, 0, 0, 100); // adicionar espaçamento por componente
        this.jPanelContent.add(this.jLabelNome, this.gbc); // sempre adicionar neste formato por conta do grid

        this.jTextFieldNome.setBackground(new Color(255, 255, 255));
        this.jTextFieldNome.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        this.jTextFieldNome.setFont(new Font("Cormorant Garamond", 1, 18));
        this.jTextFieldNome.setPreferredSize(fieldSize);
        this.jTextFieldNome.setForeground(Color.BLACK);
        this.jTextFieldNome.setText("Nome Completo");

        this.gbc.gridx = 0;
        this.gbc.gridy = 1;
        this.gbc.insets = new Insets(0, 0, 20, 100);
        this.jPanelContent.add(this.jTextFieldNome, this.gbc);

        this.jLabelEmail.setText("E-mail:");
        this.jLabelEmail.setFont(new Font("Cormorant Garamond", 1, 18));
        this.jLabelEmail.setForeground(Color.BLACK);
        this.gbc.gridx = 0;
        this.gbc.gridy = 2;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        this.jPanelContent.add(this.jLabelEmail, this.gbc);

        this.jTextFieldEmail.setBackground(Color.WHITE);
        this.jTextFieldEmail.setFont(new Font("Cormorant Garamond", 1, 18));
        this.jTextFieldEmail.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        this.jTextFieldEmail.setPreferredSize(fieldSize);
        this.jTextFieldEmail.setText("email@gmail.com");
        this.jTextFieldEmail.setForeground(Color.BLACK);

        this.gbc.gridx = 0;
        this.gbc.gridy = 3;
        this.gbc.insets = new Insets(0, 0, 20, 0);
        this.jPanelContent.add(this.jTextFieldEmail, this.gbc);

        this.jButtonCadastrar.setBackground(new Color(0, 128, 17));
        this.jButtonCadastrar.setFont(new Font("Cormorant Garamond Bold", 1, 18));
        this.jButtonCadastrar.setForeground(new Color(255, 255, 200));
        this.jButtonCadastrar.setText("Cadastrar");
        this.jButtonCadastrar.setFocusPainted(false);
        this.jButtonCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (id == null) {
                    cadastrarCliente();
                } else  {
                    editarCliente();
                }
            }
        });

        this.jPanel.add(this.jPanelContent);


        add(this.jPanel);
    }

    

    private void getCliente(){
            try {
                // making the request
                String urlAPI = this.dotenv.get("API_HOST");
                URL url = new URL(urlAPI + "/client/" + this.id);
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
                    JsonObject prod = JsonParser.parseString(response.toString()).getAsJsonObject();

                    String name = prod.get("name").getAsString();
                    String cpf = prod.get("cpf").getAsString();
                    String email = prod.get("email").getAsString();
                    String address = prod.get("address").getAsString();
                    String phone_number = prod.get("phoneNumber").getAsString();
                    String data = prod.get("dateBirth").getAsString();

                    DateTimeFormatter formatoEntrada = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    DateTimeFormatter formatoSaida = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String dataFormatada = LocalDate.parse(data, formatoEntrada).format(formatoSaida);

                    jTextFieldNome.setText(name);
                    jTextFieldTelefone.setText(phone_number);
                    jTextFieldEmail.setText(email);
                    jTextFieldEndereco.setText(address);
                    jTextFieldCPF.setText(cpf);
                    jTextFieldDataNascimento.setText(dataFormatada.replace("/",""));

                    connection.disconnect();

                } else {
                    JOptionPane.showMessageDialog(framePrincipal, "Erro ao carregar o cliente: " + responseCode);
                    connection.disconnect();
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(framePrincipal, e.getMessage());
            }
    }

    public void reset(){
        this.jTextFieldDataNascimento.setText(null);
        this.jButtonCadastrar.setText("Cadastrar");
        this.jTextFieldEmail.setText(null);
        this.jTextFieldNome.setText(null);
        this.jLabelCadastro.setText("Cadastrar Cliente");
        this.id = null;
    }

    // componentes que vão ser usados na tela, só o essencial e com os nomes certinhos
    private JLabel jLabelCadastro;
    private JLabel jLabelNome;
    private JLabel jLabelEmail;

    private JTextField jTextFieldNome;
    private JTextField jTextFieldEmail;

    private JButton jButtonCadastrar;

    private JPanel jPanelHeader;
    private JPanel jPanelContent;
    private JPanel jPanel;

    private GridBagConstraints gbc;

    private JSeparator jSeparator;
}

