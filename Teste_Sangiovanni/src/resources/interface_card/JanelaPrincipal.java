package resources.interface_card;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class JanelaPrincipal extends javax.swing.JFrame {

    public JanelaPrincipal() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        // Painel principal

        jPanel1 = new javax.swing.JPanel();

        // Painéis adicionais
        jPanel2 = new javax.swing.JPanel(); // Painel superior com título
        jPanel3 = new javax.swing.JPanel(); // Painel com botões
        cardPanel = new javax.swing.JPanel(); // Painel com CardLayout

        // Botões para alternar os cards
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new JButton();
        jButton5 = new JButton();

        // Paineis dos cards
        cardVisualizarCupom = new VisualizarCupomPosCompra(this);
        cardCadastroProdutos = new CadastrarProduto(cardPanel, this.rootPane);
        cardCadastroFornecedores = new CadastrarFornecedor(cardPanel, this.rootPane);
        cardCadastrarClientes = new CadastrarCliente(cardPanel, this.rootPane);
        cardRegistrarVenda = new RegistrarVenda(cardPanel, this,cardVisualizarCupom);
        cardListarProdutos = new ListarProduto(this.rootPane, cardPanel, cardCadastroProdutos);
        cardListarFornecedores = new ListarFornecedor(this.rootPane, cardPanel, cardCadastroFornecedores);
        cardListarVendas = new ListarVendas(this.rootPane, cardPanel);
        cardListarClientes = new ListarCliente(this.rootPane, cardPanel, cardCadastrarClientes);
        cardCadastroFuncionario = new CadastrarFuncionario(cardPanel, this.rootPane);
        cardListarFuncionario = new ListarFuncionario(this.rootPane, cardPanel, cardCadastroFuncionario);

        // Labels
        jLabel1 = new javax.swing.JLabel();

        // Configurando a janela
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1366, 770));
        setBackground(new java.awt.Color(243, 243, 223));
        getContentPane().setLayout(new BorderLayout());
        setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/images/icon.png"))).getImage());

        // Configuração do painel principal
        jPanel1.setLayout(new BorderLayout());

        //COMEÇO DO BOTAO


        // Criando o painel principal (jPanel2)
        JPanel jPanel2 = new JPanel(new BorderLayout());
        jPanel2.setBackground(new Color(243, 243, 223));
        jPanel2.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));


        JPanel jPanelSuperior = new JPanel(new GridBagLayout());
        jPanelSuperior.setBackground(new Color(243, 243, 223));


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Criando o painel do título
        JPanel jPanelTitulo = new JPanel();
        jPanelTitulo.setBackground(new Color(243, 243, 223));

        JLabel jLabel1 = new JLabel("Casa San'Giovanni");
        jLabel1.setFont(new Font("Cinzel", Font.PLAIN, 24));
        jLabel1.setForeground(new Color(128, 0, 32));

        jPanelTitulo.add(jLabel1);

        // Ajustando GridBagConstraints para o título
        gbc.gridx = 0; // Coluna 0
        gbc.gridy = 0; // Linha 0
        gbc.gridwidth = 2; // O título ocupa as duas colunas disponíveis
        gbc.anchor = GridBagConstraints.CENTER; // Alinha no centro
        gbc.weightx = 1;  // O título ocupa o espaço disponível
        jPanelSuperior.add(jPanelTitulo, gbc);

        // Criando o painel do botão de sair
        JPanel jPanelBotaoWrapper = new JPanel();
        jPanelBotaoWrapper.setBackground(new Color(243, 243, 223));

        // Definindo um tamanho preferido maior para o botão de sair
        JButton botaoSair = new JButton("Sair");

        // Carregar e redimensionar a imagem do ícone
        String caminhoIcone = "../images/sair.png"; // Substitua pelo caminho correto
        ImageIcon iconeRedimensionado = null;

        try {
            ImageIcon iconeOriginal = new ImageIcon(caminhoIcone);
            Image imagemRedimensionada = iconeOriginal.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            iconeRedimensionado = new ImageIcon(imagemRedimensionada);
        } catch (Exception e) {
            System.out.println("Erro ao carregar ícone: " + e.getMessage());
        }

        if (iconeRedimensionado != null) {
            botaoSair.setIcon(iconeRedimensionado); // Adiciona a imagem no botão
            botaoSair.setHorizontalTextPosition(SwingConstants.LEFT); // Texto antes da imagem
        }

        // Estilizando o botão diretamente
        botaoSair.setFont(new Font("Arial", Font.BOLD, 14));
        botaoSair.setForeground(new Color(128, 0, 32)); // Cor vinho
        botaoSair.setBackground(new Color(243, 243, 223)); // Fundo igual ao painel
        botaoSair.setFocusPainted(false);
        botaoSair.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        botaoSair.setContentAreaFilled(false); // Remove o fundo do botão

        // Definindo um tamanho preferido para o botão para garantir que ele seja largo o suficiente
        botaoSair.setPreferredSize(new Dimension(120, 40)); // Ajuste a largura conforme necessário

        // Adicionando o ActionListener ao botão para torná-lo funcional
        botaoSair.addActionListener(e -> exibirModalConfirmacao());

        jPanelBotaoWrapper.add(botaoSair);

        // Ajustando GridBagConstraints para o botão
        gbc.gridx = 2; // Coluna 2 (botão à direita)
        gbc.gridy = 0; // Linha 0 (mesma linha do título)
        gbc.gridwidth = 1; // Ocupa uma coluna
        gbc.anchor = GridBagConstraints.EAST; // Alinha à direita
        gbc.weightx = 0;  // O botão ocupa apenas o espaço necessário
        jPanelSuperior.add(jPanelBotaoWrapper, gbc);

        // Adicionando o painel superior ao painel principal
        jPanel2.add(jPanelSuperior, BorderLayout.NORTH);

        // Adicionando jPanel2 ao painel principal existente (supondo que jPanel1 já exista)
        jPanel1.add(jPanel2, BorderLayout.NORTH);


        // Painel de botões (jPanel3)
        jPanel3.setBackground(new java.awt.Color(128, 0, 32));
        jPanel3.setPreferredSize(new java.awt.Dimension(1366, 40)); // Altura reduzida
        jPanel3.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 5));

        jButton1.setBackground(new java.awt.Color(128, 0, 32));
        jButton1.setFont(new java.awt.Font("Cormorant Garamond SemiBold", 0, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(225, 235, 43));
        jButton1.setIcon(new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/images/wine.png"))));
        jButton1.setText("Produtos");
        jButton1.setBorder(null);
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(128, 0, 32));
        jButton2.setFont(new java.awt.Font("Cormorant Garamond SemiBold", 0, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(225, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/images/caminhao.png")))); // NOI18N
        jButton2.setText("Fornecedores");
        jButton2.setBorder(null);
        jButton2.setContentAreaFilled(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(128, 0, 32));
        jButton3.setFont(new java.awt.Font("Cormorant Garamond SemiBold", 0, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/images/sacola.png")))); // NOI18N
        jButton3.setText("Vendas");
        jButton3.setBorder(null);
        jButton3.setContentAreaFilled(false);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(128, 0, 32));
        jButton4.setFont(new java.awt.Font("Cormorant Garamond SemiBold", 0, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(225, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/images/clientes.png")))); // NOI18N
        jButton4.setText("Clientes");
        jButton4.setBorder(null);
        jButton4.setFocusable(false);
        jButton4.setContentAreaFilled(false);
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.setPreferredSize(new java.awt.Dimension(120, 35));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(128, 0, 32));
        jButton5.setFont(new java.awt.Font("Cormorant Garamond SemiBold", 0, 18)); // NOI18N
        jButton5.setForeground(new java.awt.Color(225, 255, 255));
        jButton5.setIcon(new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/images/carteira-de-identidade.png")))); // NOI18N
        jButton5.setText("Funcionários");
        jButton5.setBorder(null);
        jButton5.setFocusable(false);
        jButton5.setContentAreaFilled(false);
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton5.setPreferredSize(new java.awt.Dimension(180, 35));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        // Botões para alternar os cards
        jButton1.addActionListener(evt -> showCard("listar_produtos"));
        jButton2.addActionListener(evt -> showCard("listar_fonecedores"));
        jButton3.addActionListener(evt -> showCard("listar_vendas"));
        jButton4.addActionListener(evt -> showCard("listar_clientes"));
        jButton5.addActionListener(evt -> showCard("listar_Funcionario"));

        jPanel3.add(jButton1);
        jPanel3.add(jButton2);
        jPanel3.add(jButton3);
        jPanel3.add(jButton4);
        jPanel3.add(jButton5);

        // Adicionando jPanel3 ao centro do jPanel1
        jPanel1.add(jPanel3, BorderLayout.CENTER);

        // Configuração do CardLayout no cardPanel
        cardPanel.setLayout(new CardLayout());


        // Adicionar os painéis ao CardLayout
        cardPanel.add(cardListarProdutos, "listar_produtos");
        cardPanel.add(cardListarFornecedores, "listar_fonecedores");
        cardPanel.add(cardListarVendas, "listar_vendas");
        cardPanel.add(cardCadastroProdutos, "cadastrar_produto");
        cardPanel.add(cardCadastroFornecedores, "cadastrar_fornecedor");
        cardPanel.add(cardListarClientes, "listar_clientes");
        cardPanel.add(cardCadastrarClientes, "cadastrar_cliente");
        cardPanel.add(cardRegistrarVenda, "registrar_venda");
        cardPanel.add(cardCadastroFuncionario, "cadastrar_funcionario");
        cardPanel.add(cardListarFuncionario, "listar_Funcionario");
        cardPanel.add(cardVisualizarCupom,"visualizar_cupom");

        // Adicionar cardPanel ao sul do jPanel1
        jPanel1.add(cardPanel, BorderLayout.SOUTH);

        // Adicionar jPanel1 como o conteúdo principal
        getContentPane().add(jPanel1, BorderLayout.CENTER);

        pack();
    }

    private void exibirModalConfirmacao() {
        new ModalConfirmacao(this).setVisible(true);
    }

    private class ModalConfirmacao extends JDialog {
        public ModalConfirmacao(JFrame parent) {
            super(parent, "Sair da Conta", true);
            setLayout(new BorderLayout());
            setSize(350, 150);
            setLocationRelativeTo(parent);

            JLabel mensagem = new JLabel("Tem certeza que deseja sair da conta?", SwingConstants.CENTER);
            mensagem.setFont(new Font("Cormorant Garamond", Font.BOLD, 20));
            add(mensagem, BorderLayout.CENTER);

            JPanel painelBotoes = new JPanel();

            JButton btnCancelar = new JButton("Cancelar");
            btnCancelar.setFont(new Font("Cormorant Garamond", Font.BOLD, 15));
            btnCancelar.addActionListener(e -> dispose());

            JButton btnSair = new JButton("Sair");
            btnSair.setFont(new Font("Cormorant Garamond", Font.BOLD, 15));
            btnSair.addActionListener(e -> logout());

            painelBotoes.add(btnCancelar);
            painelBotoes.add(btnSair);

            add(painelBotoes, BorderLayout.SOUTH);
        }
    }

    // Método para alternar o card visível
    public void showCard(String cardName) {
        CardLayout cl = (CardLayout) cardPanel.getLayout();
        switch (cardName) {
            case "listar_vendas":
                cardListarVendas.atualizarDados();
                cl.show(cardPanel, cardName);
                break;
            case "listar_clientes":
                cardListarClientes.atualizarDados();
                cl.show(cardPanel, cardName);
                break;
            case "listar_produtos":
                cardListarProdutos.atualizarDados();
                cl.show(cardPanel, cardName);
                break;
            case "listar_fonecedores":
                cardListarFornecedores.atualizarDados();
                cl.show(cardPanel, cardName);
                break;
            case "registrar_venda":
                cardRegistrarVenda.showCard("produtos");
                cl.show(cardPanel, cardName);
                break;
            case "listar_Funcionario":
                cardListarFuncionario.atualizarDados();
                cl.show(cardPanel, cardName);
            default:
                cl.show(cardPanel, cardName);
                break;
        }

    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        jButton1.setForeground(new java.awt.Color(225, 235, 43));
        jButton2.setForeground(Color.WHITE);
        jButton3.setForeground(Color.WHITE);
        jButton4.setForeground(Color.WHITE);
        jButton5.setForeground(Color.WHITE);
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        jButton1.setForeground(Color.WHITE);
        jButton2.setForeground(new java.awt.Color(225, 235, 43));
        jButton3.setForeground(Color.WHITE);
        jButton4.setForeground(Color.WHITE);
        jButton5.setForeground(Color.WHITE);
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        jButton1.setForeground(Color.WHITE);
        jButton2.setForeground(Color.WHITE);
        jButton3.setForeground(new java.awt.Color(225, 235, 43));
        jButton4.setForeground(Color.WHITE);
        jButton5.setForeground(Color.WHITE);
    }

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
        jButton1.setForeground(Color.WHITE);
        jButton2.setForeground(Color.WHITE);
        jButton3.setForeground(Color.WHITE);
        jButton4.setForeground(new java.awt.Color(225, 235, 43));
        jButton5.setForeground(Color.WHITE);
    }

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {
        jButton1.setForeground(Color.WHITE);
        jButton2.setForeground(Color.WHITE);
        jButton3.setForeground(Color.WHITE);
        jButton4.setForeground(Color.WHITE);
        jButton5.setForeground(new java.awt.Color(225, 235, 43));
    }

    private void logout() {
        this.dispose();
        JFrame login = new Login();
        login.setVisible(true);
        this.setVisible(false);
    }

    public static void main(String[] args) {
        try{
            java.awt.EventQueue.invokeLater(() -> new resources.interface_card.JanelaPrincipal().setVisible(true));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Declaração de variáveis
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;

    private javax.swing.JLabel jLabel1;

    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;

    private javax.swing.JPanel cardPanel;
    private ListarProduto cardListarProdutos;
    private ListarFornecedor cardListarFornecedores;
    private CadastrarProduto cardCadastroProdutos;
    private CadastrarFornecedor cardCadastroFornecedores;
    private ListarVendas cardListarVendas;
    private ListarCliente cardListarClientes;
    private CadastrarCliente cardCadastrarClientes;
    private RegistrarVenda cardRegistrarVenda;
    private CadastrarFuncionario cardCadastroFuncionario;
    private ListarFuncionario cardListarFuncionario;
    private VisualizarCupomPosCompra cardVisualizarCupom;
}

