package resources.interface_card;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CadastrarCliente extends JPanel {
    public CadastrarCliente(JPanel mainPanel) {
        // inicialização de variáveis
        jLabelTelefone = new JLabel();
        jLabelCPF = new JLabel();
        jLabelEmail = new JLabel();
        jLabelDataNascimento = new JLabel();
        jLabelCadastro = new JLabel();
        jLabelNome = new JLabel();
        jLabelEndereco = new JLabel();

        jTextFieldNome = new JTextField();
        jTextFieldEmail = new JTextField();
        try{
            jTextFieldTelefone = new javax.swing.JFormattedTextField(new MaskFormatter("(##) #####-####"));
            jTextFieldCPF = new javax.swing.JFormattedTextField(new MaskFormatter("###.###.###-##"));
            jTextFieldDataNascimento = new javax.swing.JFormattedTextField(new MaskFormatter("##/##/####"));
            jTextFieldEndereco = new javax.swing.JFormattedTextField();
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
        jLabelCadastro.setText("Cadastrar Cliente");
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
        gbc.gridx = 0; // coluna
        gbc.gridy = 0; // linha
        gbc.insets = new Insets(0, 0, 0, 100); // adicionar espaçamento por componente
        jPanelContent.add(jLabelNome, gbc); // sempre adicionar neste formato por conta do grid

        jTextFieldNome.setBackground(new Color(255, 255, 255));
        jTextFieldNome.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jTextFieldNome.setFont(new Font("Cormorant Garamond", 1, 18));
        jTextFieldNome.setPreferredSize(fieldSize);
        jTextFieldNome.setText("Nome Completo");
        jTextFieldNome.addFocusListener(new FocusAdapter() { // adicionar um evento ao clicar no campo
            @Override
            public void focusGained(FocusEvent e) { // clicou
                if (jTextFieldNome.getText().equals("Nome Completo")) {
                    jTextFieldNome.setText("");
                    jTextFieldNome.setForeground(Color.GRAY);
                }
            }

            @Override
            public void focusLost(FocusEvent e) { // clicou em outra coisa
                if (jTextFieldNome.getText().isEmpty()) {
                    jTextFieldNome.setText("Nome Completo");
                    jTextFieldNome.setForeground(Color.GRAY);
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 100);
        jPanelContent.add(jTextFieldNome, gbc);

        jLabelTelefone.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelTelefone.setText("Telefone:");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        jPanelContent.add(jLabelTelefone, gbc);

        jTextFieldTelefone.setBackground(new Color(255, 255, 255));
        jTextFieldTelefone.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jTextFieldTelefone.setFont(new Font("Cormorant Garamond", 1, 18));
        jTextFieldTelefone.setPreferredSize(fieldSize);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 0);
        jPanelContent.add(jTextFieldTelefone, gbc);

        jLabelCPF.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelCPF.setText("CPF:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 0, 100);
        jPanelContent.add(jLabelCPF, gbc);

        jTextFieldCPF.setBackground(new Color(255, 255, 255));
        jTextFieldCPF.setBackground(Color.WHITE);
        jTextFieldCPF.setFont(new Font("Cormorant Garamond", Font.BOLD, 18));
        jTextFieldCPF.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jTextFieldCPF.setPreferredSize(fieldSize);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 20, 100);
        jPanelContent.add(jTextFieldCPF, gbc);

        jLabelEmail.setText("E-mail:");
        jLabelEmail.setFont(new Font("Cormorant Garamond", 1, 18));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        jPanelContent.add(jLabelEmail, gbc);

        jTextFieldEmail.setBackground(Color.WHITE);
        jTextFieldEmail.setFont(new Font("Cormorant Garamond", 1, 18));
        jTextFieldEmail.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jTextFieldEmail.setPreferredSize(fieldSize);
        jTextFieldEmail.setText("email@gmail.com");
        jTextFieldEmail.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (jTextFieldEmail.getText().equals("email@gmail.com")) {
                    jTextFieldEmail.setText("");
                    jTextFieldEmail.setForeground(Color.GRAY);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (jTextFieldEmail.getText().isEmpty()) {
                    jTextFieldEmail.setText("email@gmail.com");
                    jTextFieldEmail.setForeground(Color.GRAY);
                }
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 20, 0);
        jPanelContent.add(jTextFieldEmail, gbc);

        jLabelEndereco.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelEndereco.setText("Endereço:");
        jLabelEndereco.setBackground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 0, 100);
        jPanelContent.add(jLabelEndereco, gbc);

        jTextFieldEndereco.setBackground(Color.WHITE);
        jTextFieldEndereco.setFont(new Font("Cormorant Garamond", 1, 18));
        jTextFieldEndereco.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jTextFieldEndereco.setPreferredSize(fieldSize);
        String regex = "^(.+?) - (.+?) - (\\d+) - ([A-Z]{2})$";
        Pattern pattern = Pattern.compile(regex);
        jTextFieldEndereco.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                String text = ((JFormattedTextField) input).getText();
                Matcher matcher = pattern.matcher(text);
                if (matcher.matches()) {
                    input.setBackground(Color.WHITE);
                    return true;
                } else {
                    input.setBackground(Color.PINK);
                    JOptionPane.showMessageDialog(mainPanel.getRootPane(),
                            "O formato deve ser: rua - bairro - numero - UF",
                            "Formato Inválido",
                            JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        });
        jTextFieldEndereco.setText("Rua - Bairro - Número - UF");
        jTextFieldEndereco.addFocusListener(new java.awt.event.FocusListener() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (jTextFieldEndereco.getText().equals("Rua - Bairro - Número - UF")) {
                    jTextFieldEndereco.setText("");
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (jTextFieldEndereco.getText().isEmpty()) {
                    jTextFieldEndereco.setText("Rua - Bairro - Número - UF");
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 20, 100);
        jPanelContent.add(jTextFieldEndereco, gbc);

        jLabelDataNascimento.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelDataNascimento.setText("Data de Nascimento:");
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        jPanelContent.add(jLabelDataNascimento, gbc);

        jTextFieldDataNascimento.setBackground(new Color(255, 255, 255));
        jTextFieldDataNascimento.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jTextFieldDataNascimento.setPreferredSize(fieldSize);
        jTextFieldDataNascimento.setFont(new Font("Cormorant Garamond", 1, 18));
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 20, 0);
        jPanelContent.add(jTextFieldDataNascimento, gbc);


        jButtonCancelar.setBackground(new Color(225, 225, 200));
        jButtonCancelar.setFont(new Font("Cormorant Garamond SemiBold", 0, 18));
        jButtonCancelar.setText("Cancelar");
        jButtonCancelar.setFocusPainted(false);
        jButtonCancelar.setBorder(new EmptyBorder(5,20,5,20));
        jButtonCancelar.addActionListener(e -> {
            // Redireciona para "listar_produtos"
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            cl.show(mainPanel, "card_vendas");
        });

        jButtonCadastrar.setBackground(new Color(0, 128, 17));
        jButtonCadastrar.setFont(new Font("Cormorant Garamond SemiBold", 0, 18));
        jButtonCadastrar.setForeground(new Color(255, 255, 200));
        jButtonCadastrar.setText("Cadastrar");
        jButtonCadastrar.setFocusPainted(false);


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

    // componentes que vão ser usados na tela, só o essencial e com os nomes certinhos
    private JLabel jLabelCadastro;
    private JLabel jLabelNome;
    private JLabel jLabelTelefone;
    private JLabel jLabelCPF;
    private JLabel jLabelEmail;
    private JLabel jLabelEndereco;
    private JLabel jLabelDataNascimento;

    private JTextField jTextFieldNome;
    private JTextField jTextFieldTelefone;
    private JTextField jTextFieldCPF;
    private JTextField jTextFieldEmail;
    private JTextField jTextFieldEndereco;
    private JTextField jTextFieldDataNascimento;

    private JButton jButtonCancelar;
    private JButton jButtonCadastrar;

    private JPanel jPanelHeader;
    private JPanel jPanelButtons;
    private JPanel jPanelContent;
    private JPanel jPanel;

    private GridBagConstraints gbc;

    private JSeparator jSeparator;
}
