import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class FiltrarVendas {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Gestão de Vendas");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JButton btnFiltrar = new JButton("Filtrar");
        frame.add(btnFiltrar);

        btnFiltrar.addActionListener((ActionEvent e) -> abrirModal());

        frame.setVisible(true);
    }

    private static void abrirModal() {
        JDialog modal = new JDialog();
        modal.setTitle("Filtrar vendas");
        modal.setSize(400, 300);
        modal.setLayout(new GridLayout(6, 2));
        modal.setModal(true);

        JCheckBox checkData = new JCheckBox("Data");
        JTextField inputDataDe = new JTextField();
        inputDataDe.setEnabled(false);
        inputDataDe.setToolTipText("De");
        JTextField inputDataAte = new JTextField();
        inputDataAte.setEnabled(false);
        inputDataAte.setToolTipText("Até");
        
        checkData.addActionListener(e -> {
            boolean selected = checkData.isSelected();
            inputDataDe.setEnabled(selected);
            inputDataAte.setEnabled(selected);
        });

        JCheckBox checkTotal = new JCheckBox("Total (R$)");
        JTextField inputTotal = new JTextField();
        inputTotal.setEnabled(false);
        inputTotal.setToolTipText("00,00");
        
        checkTotal.addActionListener(e -> inputTotal.setEnabled(checkTotal.isSelected()));
        
        JCheckBox checkCliente = new JCheckBox("Cliente");
        JTextField inputCliente = new JTextField();
        inputCliente.setEnabled(false);
        inputCliente.setToolTipText("Cliente");
        
        checkCliente.addActionListener(e -> inputCliente.setEnabled(checkCliente.isSelected()));
        
        JCheckBox checkProduto = new JCheckBox("Produto");
        JTextField inputProduto = new JTextField();
        inputProduto.setEnabled(false);
        inputProduto.setToolTipText("Produto");
        
        checkProduto.addActionListener(e -> inputProduto.setEnabled(checkProduto.isSelected()));
        
        JButton btnAplicar = new JButton("Aplicar Filtro");
        btnAplicar.setBackground(new Color(0, 28, 128));
        btnAplicar.setForeground(Color.WHITE);
        
        btnAplicar.addActionListener(e -> modal.dispose());
        
        modal.add(checkData);
        modal.add(new JLabel());
        modal.add(new JLabel("De:"));
        modal.add(inputDataDe);
        modal.add(new JLabel("Até:"));
        modal.add(inputDataAte);
        modal.add(checkTotal);
        modal.add(inputTotal);
        modal.add(checkCliente);
        modal.add(inputCliente);
        modal.add(checkProduto);
        modal.add(inputProduto);
        modal.add(new JLabel());
        modal.add(btnAplicar);
        
        modal.setLocationRelativeTo(null);
        modal.setVisible(true);
    }
}

