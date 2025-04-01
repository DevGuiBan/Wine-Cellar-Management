package resources.interface_card;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import javax.imageio.ImageIO;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

public class Teste {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Teste::criarJanelaPrincipal);
    }

    private static void criarJanelaPrincipal() {
        JFrame frame = new JFrame("Sistema de Vendas");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());

        JButton btnVisualizar = new JButton("Visualizar Cupom Fiscal");
        btnVisualizar.addActionListener(e -> abrirModalCupom());

        frame.add(btnVisualizar);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void abrirModalCupom() {
        String jsonResponse = obterDadosCupom();
        if (jsonResponse == null) {
            JOptionPane.showMessageDialog(null, "Erro ao obter dados do cupom.");
            return;
        }

        JsonObject json = JsonParser.parseString(jsonResponse).getAsJsonObject();
        String empresa = json.get("enterpiseName").getAsString();
        String endereco = json.get("address").getAsString();
        String cidadeEstado = json.get("cityState").getAsString();
        String cnpj = json.get("cpnj").getAsString();
        String data = json.get("dateOpenCnpj").getAsString();
        String hora = json.get("hourIE").getAsString();
        String ccf = json.get("ccf").getAsString();
        String ie = json.get("ie").getAsString();
        String cdd = json.get("cdd").getAsString();
        String im = json.get("im").getAsString();
        String qrCode = json.get("qrCode").getAsString();

        JsonObject sale = json.getAsJsonObject("sale");
        String pagamento = sale.get("paymentMethod").getAsString();
        double totalPrice = sale.get("totalPrice").getAsDouble();
        double discount = sale.get("discount").getAsDouble();
        JsonArray products = sale.getAsJsonArray("products");

        StringBuilder produtosStr = new StringBuilder();
        produtosStr.append("<table border='1' width='100%'>");
        produtosStr.append("<tr><th>Item</th><th>Nome</th><th>Valor Unit.</th><th>Qtd</th><th>Total</th></tr>");

        for (int i = 0; i < products.size(); i++) {
            JsonObject product = products.get(i).getAsJsonObject();
            int quantity = product.get("quantity").getAsInt();
            double price = product.get("price").getAsDouble();
            double total = quantity * price;

            produtosStr.append("<tr>");
            produtosStr.append("<td>" + (i + 1) + "</td>");
            produtosStr.append("<td>" + product.get("name").getAsString() + "</td>");
            produtosStr.append("<td>R$" + price + "</td>");
            produtosStr.append("<td>" + quantity + "</td>");
            produtosStr.append("<td>R$" + total + "</td>");
            produtosStr.append("</tr>");
        }
        produtosStr.append("</table>");

        JDialog modal = new JDialog();
        modal.setTitle("Cupom Fiscal");
        modal.setSize(500, 600);
        modal.setModal(true);
        modal.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Centralizando a label com as informações do cupom
        JLabel lblCupom = new JLabel("<html><center><b>CUPOM FISCAL</b><br><br>"
                + empresa + "<br>"
                + endereco + "<br>"
                + cidadeEstado + "<br><br>"
                + "CNPJ: " + cnpj + " &nbsp;&nbsp; " + data + "<br>"
                + "Hora: " + hora + "<br>"
                + "CCF: " + ccf + " - IE: " + ie + " - CDD: " + cdd + "<br>"
                + "IM: " + im + "<br>"
                + "------------------------------------------------------------------------------------------------<br>"
                + produtosStr.toString() + "<br>"
                + "TOTAL: <b>R$" + totalPrice + "</b><br>"
                + "DESCONTO: R$" + discount + "<br>"
                + "PAGAMENTO: " + pagamento + "<br>"
                + "----------------------------------------------------------------<br>"
                +"CÓDIGO DO QR CODE: " + qrCode + "<br>"
                + "QR CODE:" + "<br></center></html>");

        lblCupom.setHorizontalAlignment(SwingConstants.CENTER);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(lblCupom, constraints);
        panel.setBackground(Color.WHITE);

        try {
            BufferedImage qrImage = gerarQRCode(qrCode, 100, 100);
            JLabel qrLabel = new JLabel(new ImageIcon(qrImage));


            constraints.gridy = 1;
            panel.add(qrLabel, constraints);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }

        JButton btnImprimir = new JButton("Imprimir");
        btnImprimir.addActionListener(e -> JOptionPane.showMessageDialog(modal, "Imprimindo cupom..."));
        constraints.gridy = 2;
        panel.add(btnImprimir, constraints);

        modal.add(panel, BorderLayout.CENTER);
        modal.setLocationRelativeTo(null);
        modal.setVisible(true);
    }

    private static BufferedImage gerarQRCode(String text, int width, int height) throws WriterException, IOException {
        BitMatrix matrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height);
        return MatrixToImageWriter.toBufferedImage(matrix);
    }

    private static String obterDadosCupom() {
        try {
            URL url = new URL("http://localhost:8081/api/sale/tax-receipt/1");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                return null;
            }

            Scanner scanner = new Scanner(conn.getInputStream());
            StringBuilder json = new StringBuilder();
            while (scanner.hasNext()) {
                json.append(scanner.nextLine());
            }
            scanner.close();
            return json.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
