import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AtualizarEstoque {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Gestão de Estoque");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JButton btnProximo = new JButton("Próximo");
        frame.add(btnProximo);

        btnProximo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirModal();
            }
        });

        frame.setVisible(true);
    }

    private static void abrirModal() {
        JDialog modal = new JDialog();
        modal.setTitle("Atualizar estoque");
        modal.setSize(350, 200);
        modal.setLayout(new GridLayout(4, 1));
        modal.setModal(true);
        
        JLabel lblInfo = new JLabel("Informe a nova quantidade em estoque para os produtos selecionados.", SwingConstants.CENTER);
        modal.add(lblInfo);
        
        JSpinner spinnerQuantidade = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
        modal.add(spinnerQuantidade);
        
        JPanel panelBotoes = new JPanel();
        JButton btnCancelar = new JButton("Cancelar");
        JButton btnAplicar = new JButton("Aplicar novo estoque");
        btnAplicar.setBackground(new Color(0, 128, 17));
        btnAplicar.setForeground(Color.WHITE);
        
        btnCancelar.addActionListener(e -> modal.dispose());
        btnAplicar.addActionListener(e -> {
            int quantidade = (int) spinnerQuantidade.getValue();
            JOptionPane.showMessageDialog(modal, "Novo estoque atualizado: " + quantidade);
            modal.dispose();
        });
        
        panelBotoes.add(btnCancelar);
        panelBotoes.add(btnAplicar);
        modal.add(panelBotoes);
        
        modal.setLocationRelativeTo(null);
        modal.setVisible(true);
    }
}

