package resources.interface_card;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class RegistrarVenda extends JPanel {
    public RegistrarVenda(JPanel mainPanel, JanelaPrincipal janelaPrincipal, VisualizarCupomPosCompra cupom) {
        // inicialização de variáveis
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(janelaPrincipal);

        jLabelCadastro = new JLabel();

        jButtonCadastrar = new JButton();
        jButtonCancelar = new JButton();
        jButtonAdicionarCarrinho = new JButton();
        jButtonProdutoCard = new JButton();
        jButtonClienteCard = new JButton();
        jButtonFinalizarVendaCard = new JButton();

        jPanelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        jPanelContent = new JPanel(new CardLayout());
        jPanelButtonsCard = new JPanel();
        jPanelHeader = new JPanel();
        jPanel = new JPanel();

        // cards
        cardProdutos = new ProdutoVenda(frame);
        cardClientes = new ClienteVenda(frame);
        cardFinalizarVenda = new FinalizarVenda(frame,janelaPrincipal,cupom);

        jSeparator = new JSeparator();

        // configuração do card
        setBackground(new Color(243, 243, 223));
        setPreferredSize(new Dimension(1366, 650)); // tamanho do painel que tem tudo
        setLayout(new FlowLayout(FlowLayout.CENTER,30,30));

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
        jLabelCadastro.setText("Registrar Venda");
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

        // Butões de mudança de tela
        jPanelButtonsCard.setBackground(Color.WHITE);
        jPanelButtonsCard.setPreferredSize(new Dimension(1200, 50));

        // painel dos campos
        jPanelContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanelContent.setPreferredSize(new Dimension(1200, 400));
        jPanelContent.setBackground(new Color(255, 255, 255));
        
        jPanelContent.add(cardProdutos,"produtos");
        jPanelContent.add(cardClientes,"clientes");
        jPanelContent.add(cardFinalizarVenda,"finalizar_venda");

        jPanelButtonsCard.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        jPanelButtonsCard.setOpaque(false);
        jPanelButtonsCard.setBorder(BorderFactory.createEmptyBorder(10, 60, 10, 10));

        jButtonProdutoCard.setBackground(Color.WHITE);
        jButtonProdutoCard.setFont(new Font("Cormorant Garamond Bold", 1, 24));
        jButtonProdutoCard.setForeground(new Color(128, 0, 32));
        jButtonProdutoCard.setText("Produto");
        jButtonProdutoCard.setFocusPainted(false);
        jButtonProdutoCard.setBorderPainted(false);
        jButtonProdutoCard.setOpaque(false);
        jButtonProdutoCard.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jButtonProdutoCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonProdutoCardActionEvent(evt);
            }
        });
        jPanelButtonsCard.add(jButtonProdutoCard);

        jButtonClienteCard.setBackground(Color.WHITE);
        jButtonClienteCard.setFont(new Font("Cormorant Garamond Bold", 1, 24));
        jButtonClienteCard.setForeground(new Color(0, 0, 0));
        jButtonClienteCard.setText("Cliente");
        jButtonClienteCard.setFocusPainted(false);
        jButtonClienteCard.setBorderPainted(false);
        jButtonClienteCard.setOpaque(false);
        jButtonClienteCard.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jButtonClienteCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClienteCardActionEvent(evt);
            }
        });
        jPanelButtonsCard.add(jButtonClienteCard);

        jButtonFinalizarVendaCard.setBackground(Color.WHITE);
        jButtonFinalizarVendaCard.setFont(new Font("Cormorant Garamond Bold", 1, 24));
        jButtonFinalizarVendaCard.setForeground(new Color(0, 0, 0));
        jButtonFinalizarVendaCard.setText("Finalizar Venda");
        jButtonFinalizarVendaCard.setFocusPainted(false);
        jButtonFinalizarVendaCard.setBorderPainted(false);
        jButtonFinalizarVendaCard.setOpaque(false);
        jButtonFinalizarVendaCard.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jButtonFinalizarVendaCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFinalizarVendaCardActionEvent(evt);
            }
        });
        jPanelButtonsCard.add(jButtonFinalizarVendaCard);

        jPanelButtons.setLayout(new BorderLayout());
        jPanelButtons.setBackground(Color.WHITE);
        jPanelButtons.setPreferredSize(new Dimension(1200, 50));
        jButtonCancelar.setMargin(new Insets(0, 30, 0, 20));

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));

        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(new EmptyBorder(0,50,0,0));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(new EmptyBorder(0,0,0,50));

        leftPanel.add(jButtonCancelar);
        rightPanel.add(jButtonAdicionarCarrinho);
        rightPanel.add(jButtonCadastrar);

        jButtonCancelar.setBackground(new Color(225, 225, 200));
        jButtonCancelar.setFont(new Font("Cormorant Garamond SemiBold", 1, 22));
        jButtonCancelar.setText("Cancelar");
        jButtonCancelar.setForeground(Color.BLACK);
        jButtonCancelar.setFocusPainted(false);
        jButtonCancelar.setPreferredSize(new Dimension(150, 40));
        jButtonCancelar.setBorder(null);
        jButtonCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jButtonCancelar.setMargin(new Insets(10, 20, 10, 20));
        jButtonCancelar.addActionListener(e -> {
            janelaPrincipal.showCard("listar_vendas");
        });

        jButtonCadastrar.setBackground(new Color(0, 128, 17));
        jButtonCadastrar.setFont(new Font("Cormorant Garamond SemiBold", 1, 22));
        jButtonCadastrar.setForeground(new Color(255, 255, 200));
        jButtonCadastrar.setPreferredSize(new Dimension(200, 40));
        jButtonCadastrar.setText("Próximo");
        jButtonCadastrar.setFocusPainted(false);
        jButtonCadastrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jButtonCadastrar.setFocusPainted(false);
        jButtonCadastrar.addActionListener(evt -> {
            if(cardProdutos.isVisible()){
                jButtonCadastrar.setText("Próximo");
                this.jButtonClienteCardActionEvent(evt);
            }else if(cardClientes.isVisible()){
                jButtonCadastrar.setText("Finalizar Venda");
                this.jButtonFinalizarVendaCardActionEvent(evt);
            }else if(cardFinalizarVenda.isVisible()){
                this.makeSale(evt);
            }

        });

        jButtonAdicionarCarrinho.setBackground(new Color(0, 28, 128));
        jButtonAdicionarCarrinho.setFont(new Font("Cormorant Garamond SemiBold", 1, 22));
        jButtonAdicionarCarrinho.setForeground(new Color(255, 255, 200));
        jButtonAdicionarCarrinho.setPreferredSize(new Dimension(300, 40));
        jButtonAdicionarCarrinho.setText("Adicionar ao Carrinho");
        jButtonAdicionarCarrinho.setFocusPainted(false);
        jButtonAdicionarCarrinho.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jButtonAdicionarCarrinho.setFocusPainted(false);
        jButtonAdicionarCarrinho.addActionListener(evt->{
            if(cardFinalizarVenda.isVisible()) {
                abrirModal(frame);
            }else if(cardProdutos.isVisible()){
                cardProdutos.addProduct();
            }
        });

        jPanelButtons.add(leftPanel, BorderLayout.WEST);
        jPanelButtons.add(rightPanel, BorderLayout.EAST);

        jPanel.add(jPanelButtonsCard);
        jPanel.add(jPanelContent);
        jPanel.add(jPanelButtons);

        add(jPanel);

    }

    public void showCard(String cardName) {
        CardLayout cl = (CardLayout) jPanelContent.getLayout();
        cl.show(jPanelContent, cardName);
    }

    private void abrirModal(JFrame parentFrame) {
        Double totalVenda = cardFinalizarVenda.getValorTotal();
        JDialog modal = new JDialog(parentFrame, "Aplicar Desconto", true);
        modal.setIconImage(new ImageIcon(Objects.requireNonNull(RegistrarVenda.class.getResource("/resources/images/icon.png"))).getImage());
        modal.setSize(350, 200);
        modal.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel lblTotalAtual = new JLabel("Valor inicial da venda: ");
        lblTotalAtual.setFont(new Font("Cormorant Infant",1,14));
        lblTotalAtual.setForeground(Color.BLACK);
        JLabel lblValorAtual = new JLabel("R$ " + String.format("%.2f", totalVenda));
        lblValorAtual.setFont(new Font("Cormorant Infant",1,14));
        lblValorAtual.setForeground(Color.BLACK);

        JLabel lblDesconto = new JLabel("Desconto: ");
        lblDesconto.setFont(new Font("Cormorant Infant",1,14));
        lblDesconto.setForeground(Color.BLACK);
        JTextField txtDesconto = new JTextField();
        txtDesconto.setFont(new Font("Cormorant Infant",1,14));
        txtDesconto.setForeground(Color.BLACK);

        JLabel lblTotalFinal = new JLabel("Valor final: ");
        lblTotalFinal.setFont(new Font("Cormorant Infant",1,14));
        lblTotalFinal.setForeground(Color.BLACK);
        JLabel lblValorFinal = new JLabel("R$ " + String.format("%.2f", totalVenda));
        lblValorFinal.setFont(new Font("Cormorant Infant",1,14));
        lblValorFinal.setForeground(Color.BLACK);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Cormorant Infant Bold",1,18));
        JButton btnAplicar = new JButton("Aplicar Desconto");
        btnAplicar.setFont(new Font("Cormorant Infant Bold",1,16));

        btnAplicar.setBackground(new Color(0, 128, 17));
        btnAplicar.setForeground(Color.WHITE);
        btnCancelar.setBackground(Color.RED);
        btnCancelar.setForeground(Color.WHITE);

        modal.add(lblTotalAtual);
        modal.add(lblValorAtual);
        modal.add(lblDesconto);
        modal.add(txtDesconto);
        modal.add(lblTotalFinal);
        modal.add(lblValorFinal);
        modal.add(btnCancelar);
        modal.add(btnAplicar);

        txtDesconto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double desconto = Double.parseDouble(txtDesconto.getText());
                    double novoTotal = totalVenda - desconto;
                    if (novoTotal < 0) {
                        JOptionPane.showMessageDialog(modal, "Desconto inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
                        txtDesconto.setText("");
                    } else {
                        lblValorFinal.setText("R$ " + String.format("%.2f", novoTotal));
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(modal, "Digite um valor numérico!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnCancelar.addActionListener(e -> modal.dispose());

        btnAplicar.addActionListener(e -> {
            try {
                double desconto = Double.parseDouble(txtDesconto.getText());
                double novoTotal = totalVenda - desconto;
                if (novoTotal < 0) {
                    JOptionPane.showMessageDialog(modal, "Desconto inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Atualiza apenas o valor final, sem modificar o valor inicial
                    lblValorFinal.setText("R$ " + String.format("%.2f", novoTotal));
                    JOptionPane.showMessageDialog(modal, "Desconto aplicado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    txtDesconto.setText(""); // Limpa o campo de desconto
                    cardFinalizarVenda.setDicount(novoTotal,desconto);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(modal, "Digite um valor numérico!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        modal.setLocationRelativeTo(parentFrame);
        modal.setVisible(true);
    }

    private void jButtonProdutoCardActionEvent(java.awt.event.ActionEvent evt){
        jButtonProdutoCard.setForeground(new Color(128, 0, 32));
        jButtonClienteCard.setForeground(new Color(0, 0, 0));
        jButtonFinalizarVendaCard.setForeground(new Color(0, 0, 0));
        jButtonAdicionarCarrinho.setText("Adicionar ao Carrinho");
        jButtonCadastrar.setText("Próximo");
        this.showCard("produtos");
    }

    private void jButtonClienteCardActionEvent(java.awt.event.ActionEvent evt){
        jButtonProdutoCard.setForeground(new Color(0, 0, 0));
        jButtonClienteCard.setForeground(new Color(128, 0, 32));
        jButtonFinalizarVendaCard.setForeground(new Color(0, 0, 0));
        jButtonAdicionarCarrinho.setText("Adicionar Novo Cliente");
        jButtonCadastrar.setText("Próximo");
        this.showCard("clientes");
    }

    private void jButtonFinalizarVendaCardActionEvent(java.awt.event.ActionEvent evt){
        jButtonProdutoCard.setForeground(new Color(0, 0, 0));
        jButtonClienteCard.setForeground(new Color(0, 0, 0));
        jButtonFinalizarVendaCard.setForeground(new Color(128, 0, 32));
        jButtonAdicionarCarrinho.setText("Aplicar Desconto");
        jButtonCadastrar.setText("Finalizar Venda");
        cardFinalizarVenda.setClient(cardClientes.getClientDAO());
        cardFinalizarVenda.setProducts(cardProdutos.getProductsList());
        this.showCard("finalizar_venda");
    }

    private void makeSale(java.awt.event.ActionEvent evt){
        cardFinalizarVenda.makeSale();
    }

    // componentes que vão ser usados na tela, só o essencial e com os nomes certinhos
    private JLabel jLabelCadastro;

    private JButton jButtonCancelar;
    private JButton jButtonCadastrar;
    private JButton jButtonAdicionarCarrinho;
    private JButton jButtonProdutoCard;
    private JButton jButtonClienteCard;
    private JButton jButtonFinalizarVendaCard;

    private JPanel jPanelHeader;
    private JPanel jPanelButtons;
    private JPanel jPanelContent;
    private JPanel jPanelButtonsCard;
    private JPanel jPanel;
    private ProdutoVenda cardProdutos;
    private ClienteVenda cardClientes;
    private FinalizarVenda cardFinalizarVenda;
    private JPanel leftPanel;
    private JPanel rightPanel;

    private JSeparator jSeparator;
}
