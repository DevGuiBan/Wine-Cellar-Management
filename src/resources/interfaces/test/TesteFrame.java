package resources.interfaces.test;

import javax.swing.*;

public class TesteFrame {
    public static void main(String[] args) {
        // Criar a janela
        JFrame frame = new JFrame("Minha Primeira Janela");
        frame.setSize(400, 300); // Tamanho da janela
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Criar um painel para conter componentes
        JPanel panel = new JPanel();

        // Adicionar o painel à janela
        frame.add(panel);

        // Configurar o painel
        colocarComponentesNoPainel(panel);

        // Tornar a janela visível
        frame.setVisible(true);
    }

    private static void colocarComponentesNoPainel(JPanel panel) {
        // Definir layout do painel
        panel.setLayout(null);

        // Criar um botão
        JButton button = new JButton("Clique aqui");
        button.setBounds(150, 100, 100, 30); // Posicionamento (x, y, largura, altura)
        panel.add(button);

        // Adicionar ação ao botão
        button.addActionListener(e -> JOptionPane.showMessageDialog(null, "Você clicou no botão!"));
    }
}
