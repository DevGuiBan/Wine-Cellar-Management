package resources.interface_card;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class VisualizarCupomPosCompra extends JPanel {
    private JTextPane cupomTextPane;
    private JLabel qrCodeLabel;
    private JButton imprimirButton;
    private JButton salvarVoltarButton;
    private JanelaPrincipal rootPane;

    private String vendaId, empresa, endereco, cidadeEstado, cnpj, data, hora, ccf, ie, cdd, im, qrCode, pagamento;
    private double totalPrice, discount;
    private JsonArray produtos;

    public VisualizarCupomPosCompra(JanelaPrincipal frame) {
        this.rootPane = frame;
        inicializarUI();
    }

    private void atualizarCupomExibicao() {
        StringBuilder html = new StringBuilder();
        html.append("<html><body style='font-family: monospace;'>");
        html.append("<h3 style='text-align: center;'>CUPOM FISCAL</h3>");
        html.append("<p>").append(empresa).append("<br>")
                .append(endereco).append("<br>")
                .append(cidadeEstado).append("</p>");
        html.append("<p><strong>CNPJ:</strong> ").append(cnpj)
                .append(" <strong>Data:</strong> ").append(data)
                .append(" <strong>Hora:</strong> ").append(hora).append("</p>");
        html.append("<p><strong>CCF:</strong> ").append(ccf)
                .append(" <strong>IE:</strong> ").append(ie)
                .append(" <strong>CDD:</strong> ").append(cdd)
                .append(" <strong>IM:</strong> ").append(im).append("</p>");

        html.append("<hr><h4>Produtos</h4>");
        html.append("<table border='1' width='100%' style='border-collapse: collapse;'>");
        html.append("<tr><th>Item</th><th>Nome</th><th>Qtd</th><th>Preço Unit.</th><th>Total</th></tr>");

        for (int i = 0; i < produtos.size(); i++) {
            JsonObject produto = produtos.get(i).getAsJsonObject();
            int qtd = produto.get("quantity").getAsInt();
            double preco = produto.get("price").getAsDouble();
            double total = qtd * preco;

            html.append("<tr>")
                    .append("<td>").append(i + 1).append("</td>")
                    .append("<td>").append(produto.get("name").getAsString()).append("</td>")
                    .append("<td>").append(qtd).append("</td>")
                    .append("<td>R$ ").append(preco).append("</td>")
                    .append("<td>R$ ").append(total).append("</td>")
                    .append("</tr>");
        }

        html.append("</table>");
        html.append("<p><strong>Total:</strong> R$ ").append(totalPrice)
                .append(" <strong>Desconto:</strong> R$ ").append(discount)
                .append(" <strong>Pagamento:</strong> ").append(pagamento).append("</p>");

        html.append("</body></html>");
        cupomTextPane.setText(html.toString());
    }

    private void gerarQRCode(String qrCodeText) {
        if (qrCodeText == null || qrCodeText.trim().isEmpty()) {
            qrCodeLabel.setText("QR Code inválido");
            qrCodeLabel.setIcon(null);
            return;
        }

        try {
            int tamanho = 100;
            BitMatrix matrix = new MultiFormatWriter().encode(qrCodeText, BarcodeFormat.QR_CODE, tamanho, tamanho);
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(matrix);
            qrCodeLabel.setIcon(new ImageIcon(qrImage));
            qrCodeLabel.revalidate();
            qrCodeLabel.repaint();
            this.revalidate();
            this.repaint();
        } catch (WriterException e) {
            e.printStackTrace();
            qrCodeLabel.setText("Erro ao gerar QR Code");
        }
    }

    private void inicializarUI() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        cupomTextPane = new JTextPane();
        cupomTextPane.setContentType("text/html");
        cupomTextPane.setEditable(false);
        cupomTextPane.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(cupomTextPane);
        add(scrollPane, BorderLayout.CENTER);

        JPanel qrCodePanel = new JPanel(new BorderLayout());
        qrCodePanel.setBackground(Color.WHITE);
        qrCodeLabel = new JLabel("", SwingConstants.CENTER);
        qrCodePanel.setPreferredSize(new Dimension(200, 220));
        qrCodeLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        qrCodePanel.add(qrCodeLabel, BorderLayout.CENTER);
        add(qrCodePanel, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel();
        imprimirButton = new JButton("Imprimir");
        imprimirButton.addActionListener(evt -> {
            try {
                gerarPDF();
            } catch (DocumentException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        salvarVoltarButton = new JButton("Salvar e Voltar");
        salvarVoltarButton.addActionListener(evt -> rootPane.showCard("listar_vendas"));

        buttonPanel.add(imprimirButton);
        buttonPanel.add(salvarVoltarButton);
        add(buttonPanel, BorderLayout.PAGE_END);
    }

    public void carregarCupom(String id) {
        String jsonResponse = obterDadosCupom(id);
        if (jsonResponse == null || jsonResponse.isEmpty()) {
            cupomTextPane.setText("<html><h3>Erro ao carregar o cupom fiscal.</h3></html>");
            return;
        }

        JsonObject json = JsonParser.parseString(jsonResponse).getAsJsonObject();
        this.vendaId = id;
        this.empresa = json.get("enterpriseName").getAsString();
        this.endereco = json.get("address").getAsString();
        this.cidadeEstado = json.get("cityState").getAsString();
        this.cnpj = json.get("cnpj").getAsString();
        this.data = json.get("dateOpenCnpj").getAsString();
        this.hora = json.get("hourIE").getAsString();
        this.ccf = json.get("ccf").getAsString();
        this.ie = json.get("ie").getAsString();
        this.cdd = json.get("cdd").getAsString();
        this.im = json.get("im").getAsString();
        this.qrCode = json.get("qrCode").getAsString();

        JsonObject sale = json.getAsJsonObject("sale");
        this.pagamento = sale.get("paymentMethod").getAsString();
        this.totalPrice = sale.get("totalPrice").getAsDouble();
        this.discount = sale.get("discount").getAsDouble();
        this.produtos = sale.getAsJsonArray("products");

        gerarQRCode(qrCode);
        atualizarCupomExibicao();
    }

    private void gerarPDF() throws DocumentException, IOException {

        Document document = new Document();
        String pasta = "cupons fiscais/";
        File diretorio = new File(pasta);

        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }

        String caminhoPDF = pasta + "cupom_fiscal_" + vendaId + ".pdf";

        PdfWriter.getInstance(document, new FileOutputStream(caminhoPDF));
        document.open();

        // Título
        Paragraph titulo = new Paragraph("CUPOM FISCAL", new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD));
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);
        document.add(new Chunk("\n"));

        // Informações da Empresa
        Paragraph empresaInfo = new Paragraph(empresa + "\n" + endereco + "\n" + cidadeEstado);
        empresaInfo.setAlignment(Element.ALIGN_CENTER);
        document.add(empresaInfo);
        document.add(new Chunk("\n"));

        // Dados fiscais
        Paragraph dadosFiscais = new Paragraph(
                "CNPJ: " + cnpj + "\n" +
                        "Data: " + data + " Hora: " + hora + "\n" +
                        "IE: " + ie + "\n" +
                        "IM: " + im + "\n" +
                        "CCF: " + ccf + "\n" +
                        "CDD: " + cdd
        );
        dadosFiscais.setAlignment(Element.ALIGN_CENTER);
        document.add(dadosFiscais);
        document.add(new Chunk("\n"));

        // Tabela de produtos
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);

        // Definir alinhamento das células
        String[] headers = {"Item", "Nome", "Valor Unit.", "Qtd", "Total"};
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell);
        }

        for (int i = 0; i < produtos.size(); i++) {
            JsonObject product = produtos.get(i).getAsJsonObject();
            int quantity = product.get("quantity").getAsInt();
            double price = product.get("price").getAsDouble();
            double total = quantity * price;

            table.addCell(new PdfPCell(new Phrase(String.valueOf(i + 1), new Font(Font.FontFamily.HELVETICA, 10))));
            table.addCell(new PdfPCell(new Phrase(product.get("name").getAsString(), new Font(Font.FontFamily.HELVETICA, 10))));
            table.addCell(new PdfPCell(new Phrase("R$ " + price, new Font(Font.FontFamily.HELVETICA, 10))));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(quantity), new Font(Font.FontFamily.HELVETICA, 10))));
            table.addCell(new PdfPCell(new Phrase("R$ " + total, new Font(Font.FontFamily.HELVETICA, 10))));
        }

        document.add(table);
        document.add(new Chunk("\n"));

        // Resumo da compra
        Paragraph resumo = new Paragraph(
                "TOTAL: R$ " + totalPrice + "\n" +
                        "DESCONTO: R$ " + discount + "\n" +
                        "PAGAMENTO: " + pagamento
        );
        resumo.setAlignment(Element.ALIGN_CENTER);
        document.add(resumo);
        document.add(new Chunk("\n"));

        // QR Code
        Paragraph qrCodeTexto = new Paragraph("CÓDIGO DO QR CODE: " + qrCode);
        qrCodeTexto.setAlignment(Element.ALIGN_CENTER);
        document.add(qrCodeTexto);

        try {
            BufferedImage qrImage = gerarQRCodePDF(qrCode, 100, 100);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(qrImage, "PNG", baos);
            baos.flush();
            byte[] qrImageBytes = baos.toByteArray();
            baos.close();

            Image qrCodeImage = Image.getInstance(qrImageBytes);
            qrCodeImage.scaleToFit(100, 100);
            qrCodeImage.setAlignment(Element.ALIGN_CENTER);

            document.add(qrCodeImage);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }

        document.close();
    }

    private BufferedImage gerarQRCodePDF(String text, int width, int height) throws WriterException, IOException {
        BitMatrix matrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height);
        return MatrixToImageWriter.toBufferedImage(matrix);
    }

    private String obterDadosCupom(String id) {
        try {
            URL url = new URL("http://localhost:8081/api/sale/tax-receipt/" + id);
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
