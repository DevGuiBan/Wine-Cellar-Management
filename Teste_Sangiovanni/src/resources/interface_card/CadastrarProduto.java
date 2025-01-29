package resources.interface_card;

import resources.interfaces.ProductType;
import resources.interfaces.Supplier;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Objects;

public class CadastrarProduto extends JPanel {
    public CadastrarProduto(JPanel mainPanel, JRootPane framePrincipal) {
        // inicialização de variáveis
        frame = (JFrame) SwingUtilities.getWindowAncestor(framePrincipal);

        jLabelTipoProduto = new JLabel();
        jLabelQuantidade = new JLabel();
        jLabelDescricao = new JLabel();
        jLabelPrecoVenda = new JLabel();
        jLabelCadastro = new JLabel();
        jLabelNome = new JLabel();
        jLabelFornecedor = new JLabel();

        jSpinnerPrecoVenda = new JSpinner((new SpinnerNumberModel(0.0, 0.0, Long.MAX_VALUE, 0.1)));
        jSpinnerQuantidade = new JSpinner((new SpinnerNumberModel(0.0, 0.0, Integer.MAX_VALUE, 1)));

        jTextFieldNome = new JTextField();
        jTextFieldDescricao = new JTextField();

        jComboBoxProductType = new JComboBox<ProductType>();
        jComboBoxSupplier = new JComboBox<Supplier>();

        jButtonCadastrar = new JButton();
        jButtonCancelar = new JButton();
        jButtonVisualizarProduto = new JButton();

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
        jLabelCadastro.setText("Cadastrar Produto");
        jLabelCadastro.setAlignmentX(Component.CENTER_ALIGNMENT); // centralizar

        jSeparator.setBackground(new Color(128, 0, 32));
        jSeparator.setForeground(new Color(128, 0, 32));
        jSeparator.setPreferredSize(new Dimension(35, 8));
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
        jTextFieldNome.setText("Nome do Produto");
        jTextFieldNome.addFocusListener(new FocusAdapter() { // adicionar um evento ao clicar no campo
            @Override
            public void focusGained(FocusEvent e) { // clicou
                if (jTextFieldNome.getText().equals("Nome do Produto")) {
                    jTextFieldNome.setText("");
                    jTextFieldNome.setForeground(Color.GRAY);
                }
            }

            @Override
            public void focusLost(FocusEvent e) { // clicou em outra coisa
                if (jTextFieldNome.getText().isEmpty()) {
                    jTextFieldNome.setText("Nome do Produto");
                    jTextFieldNome.setForeground(Color.GRAY);
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 100);
        jPanelContent.add(jTextFieldNome, gbc);

        jLabelDescricao.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelDescricao.setText("Descrição:");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        jPanelContent.add(jLabelDescricao, gbc);

        jTextFieldDescricao.setBackground(new Color(255, 255, 255));
        jTextFieldDescricao.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jTextFieldDescricao.setFont(new Font("Cormorant Garamond", 1, 18));
        jTextFieldDescricao.setPreferredSize(fieldSize);
        jTextFieldDescricao.setText("Descrição do Produto");
        jTextFieldDescricao.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (jTextFieldDescricao.getText().equals("Descrição do Produto")) {
                    jTextFieldDescricao.setText("");
                    jTextFieldDescricao.setForeground(Color.GRAY);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (jTextFieldDescricao.getText().isEmpty()) {
                    jTextFieldDescricao.setText("Descrição do Produto");
                    jTextFieldDescricao.setForeground(Color.GRAY);
                }
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 0);
        jPanelContent.add(jTextFieldDescricao, gbc);

        jLabelFornecedor.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelFornecedor.setText("Fornecedor:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 0, 100);
        jPanelContent.add(jLabelFornecedor, gbc);

        jComboBoxSupplier.setBackground(new Color(255, 255, 255));
        jComboBoxSupplier.setBackground(Color.WHITE);
        jComboBoxSupplier.setFont(new Font("Cormorant Garamond", Font.BOLD, 18));
        jComboBoxSupplier.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jComboBoxSupplier.setPreferredSize(fieldSize);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 20, 100);
        jPanelContent.add(jComboBoxSupplier, gbc);

        jLabelQuantidade.setBackground(Color.WHITE);
        jLabelQuantidade.setText("Quantidade:");
        jLabelQuantidade.setFont(new Font("Cormorant Garamond", 1, 18));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        jPanelContent.add(jLabelQuantidade, gbc);

        jSpinnerQuantidade.setBackground(Color.WHITE);
        jSpinnerQuantidade.setFont(new Font("Cormorant Garamond", 1, 18));
        jSpinnerQuantidade.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jSpinnerQuantidade.setPreferredSize(fieldSize);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 20, 0);
        jPanelContent.add(jSpinnerQuantidade, gbc);

        jLabelPrecoVenda.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelPrecoVenda.setText("Preço de Venda:");
        jLabelPrecoVenda.setBackground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 0, 100);
        jPanelContent.add(jLabelPrecoVenda, gbc);

        jSpinnerPrecoVenda.setBackground(Color.WHITE);
        jSpinnerPrecoVenda.setFont(new Font("Cormorant Garamond", 1, 18));
        jSpinnerPrecoVenda.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jSpinnerPrecoVenda.setPreferredSize(fieldSize);
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(jSpinnerPrecoVenda, "R$ #,##0.00"); // máscara de preço pro spinner
        jSpinnerPrecoVenda.setEditor(editor);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 0, 100);
        jPanelContent.add(jSpinnerPrecoVenda, gbc);

        jLabelTipoProduto.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelTipoProduto.setText("Tipo:");
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        jPanelContent.add(jLabelTipoProduto, gbc);

        jComboBoxProductType.setBackground(new Color(255, 255, 255));
        jComboBoxProductType.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jComboBoxProductType.setPreferredSize(fieldSize);
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 0, 0);
        jPanelContent.add(jComboBoxProductType, gbc);


        frame.add(jButtonVisualizarProduto);

        jButtonVisualizarProduto.setFont(new java.awt.Font("Cormorant Garamond Bold", 1, 18));
        jButtonVisualizarProduto.setForeground(new java.awt.Color(0, 28, 128));
        jButtonVisualizarProduto.setBackground(Color.WHITE);
        jButtonVisualizarProduto.setText("Visualizar Tipos de Produtos");
        jButtonVisualizarProduto.setBorder(null);
        jButtonVisualizarProduto.setOpaque(false);
        jButtonVisualizarProduto.setFocusPainted(false);
        jButtonVisualizarProduto.setFocusable(false);
        jButtonVisualizarProduto.setBorder(new EmptyBorder(0,0,0,460));
        jButtonVisualizarProduto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                abrirModalTiposProdutos(frame);
            }
        });

        jButtonCancelar.setBackground(new Color(225, 225, 200));
        jButtonCancelar.setFont(new Font("Cormorant Garamond SemiBold", 0, 18));
        jButtonCancelar.setText("Cancelar");
        jButtonCancelar.setFocusPainted(false);
        jButtonCancelar.setBorder(new EmptyBorder(5,20,5,20));
        jButtonCancelar.addActionListener(e -> {
            // Redireciona para "listar_produtos"
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            cl.show(mainPanel, "card1");
        });

        jButtonCadastrar.setBackground(new Color(0, 128, 17));
        jButtonCadastrar.setFont(new Font("Cormorant Garamond SemiBold", 0, 18));
        jButtonCadastrar.setForeground(new Color(255, 255, 200));
        jButtonCadastrar.setText("Cadastrar");
        jButtonCadastrar.setFocusPainted(false);


        jPanel.add(jPanelContent);

        jPanelButtons.setBackground(new Color(255, 255, 255));
        jPanelButtons.setPreferredSize(new Dimension(1200, 50));
        jPanelButtons.add(jButtonVisualizarProduto);
        jPanelButtons.add(jButtonCancelar);
        jPanelButtons.add(jButtonCadastrar);
        jPanelButtons.setLayout(new FlowLayout(FlowLayout.RIGHT,10,10));
        jPanelButtons.setBorder(new EmptyBorder(5,10,5,140));

        jPanel.add(jPanelButtons);

        add(jPanel);

    }

    public void abrirModalTiposProdutos(JFrame parent) {
        JDialog modal = new JDialog(parent, "Tipos de Produtos", true);
        modal.setSize(400, 300);
        modal.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Tipos de Produtos", SwingConstants.LEFT);
        titleLabel.setFont(new Font("Cormorant Garamond SemiBold", Font.BOLD, 18));
        titleLabel.setForeground(Color.BLACK);

        JPanel closePanel = new JPanel(new BorderLayout());
        closePanel.setBackground(Color.WHITE);

        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(closePanel, BorderLayout.EAST);

        DefaultListModel<String> model = new DefaultListModel<>();
        model.addElement("Espumante");
        model.addElement("Refrigerante");

        JList<String> list = new JList<>(model);
        list.setFont(new Font("Cormorant Garamond", Font.PLAIN, 16));
        list.setForeground(Color.BLACK);
        list.setBackground(new Color(238,238,238));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        list.setFixedCellHeight(40);

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(new EmptyBorder(5, 10, 5, 10));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(Color.WHITE);

        JButton novoButton = new JButton();
        novoButton.setBackground(Color.WHITE);
        novoButton.setIcon(new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/images/add.png"))));
        novoButton.setFocusPainted(false);
        novoButton.setBorderPainted(false);
        novoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton editarButton = new JButton();
        editarButton.setBackground(Color.WHITE);
        editarButton.setIcon(new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/images/editar.png"))));
        editarButton.setFocusPainted(false);
        editarButton.setBorderPainted(false);
        editarButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton removerButton = new JButton();
        removerButton.setBackground(Color.WHITE);
        removerButton.setIcon(new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/images/excluir.png"))));
        removerButton.setFocusPainted(false);
        removerButton.setBorderPainted(false);
        removerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        bottomPanel.add(novoButton);
        bottomPanel.add(editarButton);
        bottomPanel.add(removerButton);

        novoButton.addActionListener(e -> {
            String novoItem = JOptionPane.showInputDialog(modal, "Digite o novo tipo de produto:");
            if (novoItem != null && !novoItem.trim().isEmpty()) {
                model.addElement(novoItem.trim());
            } else {
                JOptionPane.showMessageDialog(modal, "O item não pode estar vazio!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        editarButton.addActionListener(e -> {
            int selectedIndex = list.getSelectedIndex();
            if (selectedIndex != -1) {
                String novoNome = JOptionPane.showInputDialog(modal, "Editar:", model.getElementAt(selectedIndex));
                if (novoNome != null && !novoNome.trim().isEmpty()) {
                    model.set(selectedIndex, novoNome.trim());
                }
            } else {
                JOptionPane.showMessageDialog(modal, "Selecione um item para editar.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        removerButton.addActionListener(e -> {
            int selectedIndex = list.getSelectedIndex();
            if (selectedIndex != -1) {
                model.remove(selectedIndex);
            } else {
                JOptionPane.showMessageDialog(modal, "Selecione um item para remover.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        modal.add(topPanel, BorderLayout.NORTH);
        modal.add(scrollPane, BorderLayout.CENTER);
        modal.add(bottomPanel, BorderLayout.SOUTH);

        modal.setLocationRelativeTo(parent);
        modal.setVisible(true);
    }

    // componentes que vão ser usados na tela, só o essencial e com os nomes certinhos
    private JLabel jLabelCadastro;
    private JLabel jLabelNome;
    private JLabel jLabelFornecedor;
    private JLabel jLabelPrecoVenda;
    private JLabel jLabelDescricao;
    private JLabel jLabelQuantidade;
    private JLabel jLabelTipoProduto;

    private JComboBox<Supplier> jComboBoxSupplier;
    private JComboBox<ProductType> jComboBoxProductType;

    private JSpinner jSpinnerQuantidade;
    private JSpinner jSpinnerPrecoVenda;

    private JTextField jTextFieldNome;
    private JTextField jTextFieldDescricao;

    private JButton jButtonCancelar;
    private JButton jButtonCadastrar;
    private JButton jButtonVisualizarProduto;

    private JPanel jPanelHeader;
    private JPanel jPanelButtons;
    private JPanel jPanelContent;
    private JPanel jPanel;

    private JFrame frame;

    private GridBagConstraints gbc;

    private JSeparator jSeparator;
}

