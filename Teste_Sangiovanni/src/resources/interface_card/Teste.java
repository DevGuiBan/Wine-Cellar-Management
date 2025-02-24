package resources.interface_card;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

public class Teste {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Teste::new);
    }

    public Teste() {
        criarGUI();
    }

    private void criarGUI() {
        JFrame frame = new JFrame("Filtro Dialog");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new FlowLayout());

        JButton btnFiltro = new JButton("Filtros");

        // Criar JDialog que servirá como popup
        JDialog dialog = new JDialog(frame, "Filtros", true);
        dialog.setSize(250, 300);
        dialog.setLayout(new GridBagLayout());
        dialog.setLocationRelativeTo(frame);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Checkbox "Data de Nascimento"
        JCheckBox chkDataNascimento = new JCheckBox("Data de Nascimento:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        dialog.add(chkDataNascimento, gbc);

        // Campo "de" e "até" com JDateChooser
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        dialog.add(new JLabel("de"), gbc);
        gbc.gridx = 1;
        JDateChooser dateChooserDe = new JDateChooser();
        dateChooserDe.setDateFormatString("dd/MM/yyyy");
        dialog.add(dateChooserDe, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        dialog.add(new JLabel("até"), gbc);
        gbc.gridx = 1;
        JDateChooser dateChooserAte = new JDateChooser();
        dateChooserAte.setDateFormatString("dd/MM/yyyy");
        dialog.add(dateChooserAte, gbc);

        // Checkbox "Endereço"
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JCheckBox chkEndereco = new JCheckBox("Endereço");
        dialog.add(chkEndereco, gbc);

        // Campo de endereço
        gbc.gridy = 4;
        JTextField txtEndereco = new JTextField(15);
        dialog.add(txtEndereco, gbc);

        // Botão Filtrar
        gbc.gridy = 5;
        JButton btnFiltrar = new JButton("Filtrar");
        dialog.add(btnFiltrar, gbc);

        // Ação do botão "Filtrar" para capturar os valores
        btnFiltrar.addActionListener(e -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String dataDe = dateChooserDe.getDate() != null ? sdf.format(dateChooserDe.getDate()) : "Não selecionado";
            String dataAte = dateChooserAte.getDate() != null ? sdf.format(dateChooserAte.getDate()) : "Não selecionado";
            String endereco = txtEndereco.getText().trim().isEmpty() ? "Não informado" : txtEndereco.getText();

            JOptionPane.showMessageDialog(frame,
                    "Filtros Aplicados:\n" +
                            "Data de: " + dataDe + "\n" +
                            "Data até: " + dataAte + "\n" +
                            "Endereço: " + endereco);

            dialog.setVisible(false); // Fecha o popup após filtrar
        });

        // Ação para abrir o JDialog ao clicar no botão de filtros
        btnFiltro.addActionListener(e -> dialog.setVisible(true));

        frame.add(btnFiltro);
        frame.setVisible(true);
    }
}
