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
        StringBuilder produtosStr = new StringBuilder();
        produtosStr.append("<table border='1' width='100%'>");
        produtosStr.append("<tr><th>Item</th><th>Nome</th><th>Valor Unit.</th><th>Qtd</th><th>Total</th></tr>");

        for (int i = 0; i < produtos.size(); i++) {
            JsonObject product = produtos.get(i).getAsJsonObject();
            int quantity = product.get("quantity").getAsInt();
            double price = product.get("price").getAsDouble();
            double total = quantity * price;

            produtosStr.append("<tr>");
            produtosStr.append("<td>" + (i + 1) + "</td>");
            produtosStr.append("<td>" + product.get("name").getAsString() + "</td>");
            produtosStr.append("<td>R$" + String.format("%.2f", price) + "</td>");
            produtosStr.append("<td>" + quantity + "</td>");
            produtosStr.append("<td>R$" + String.format("%.2f", total) + "</td>");
            produtosStr.append("</tr>");
        }
        produtosStr.append("</table>");

        String cupomHtml = "<html><center><b>CUPOM FISCAL</b><br><br>"
                + empresa + "<br>"
                + endereco + "<br>"
                + cidadeEstado + "<br><br>"
                + "CNPJ: " + cnpj + "    " + data + "<br>"
                + "Hora: " + hora + "<br>"
                + "CCF: " + ccf + " - IE: " + ie + " - CDD: " + cdd + "<br>"
                + "IM: " + im + "<br>"
                + "------------------------------------------------------------------------------------------------<br>"
                + produtosStr + "<br>"
                + "TOTAL: <b>R$" + String.format("%.2f", totalPrice) + "</b><br>"
                + "DESCONTO: R$" + String.format("%.2f", discount) + "<br>"
                + "PAGAMENTO: " + pagamento + "<br>"
                + "----------------------------------------------------------------<br>"
                + "CÓDIGO DO QR CODE: " + qrCode + "<br>"
                + "</center></html>";

        cupomTextPane.setText(cupomHtml);

        // Geração e exibição do QR Code
        try {
            if (qrCode == null || qrCode.trim().isEmpty()) {
                throw new IllegalArgumentException("Código do QR Code está vazio ou nulo.");
            }

            BufferedImage qrImage = gerarQRCodePDF(qrCode, 100, 100);
            if (qrImage == null) {
                throw new IllegalStateException("Imagem do QR Code não foi gerada.");
            }

            // Definir a imagem no qrCodeLabel
            qrCodeLabel.setIcon(new ImageIcon(qrImage));
            qrCodeLabel.setText(""); // Limpar qualquer texto anterior
            qrCodeLabel.setPreferredSize(new Dimension(100, 100)); // Garantir que o label tenha tamanho suficiente

            // Forçar a atualização da interface
            qrCodeLabel.revalidate();
            qrCodeLabel.repaint();
        } catch (WriterException | IOException | IllegalArgumentException | IllegalStateException e) {
            qrCodeLabel.setIcon(null); // Limpar qualquer ícone anterior
            qrCodeLabel.setText("Erro ao gerar QR Code: " + e.getMessage());
            qrCodeLabel.revalidate();
            qrCodeLabel.repaint();
            e.printStackTrace();
        }

        // Habilitar o botão de impressão
        imprimirButton.setEnabled(true);
    }

    private void inicializarUI() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel principal para exibição do cupom
        cupomTextPane = new JTextPane();
        cupomTextPane.setContentType("text/html");
        cupomTextPane.setEditable(false);
        cupomTextPane.setBackground(Color.WHITE);
        cupomTextPane.setText("<html><center><h3>Aguardando carregamento do cupom...</h3></center></html>");

        JScrollPane scrollPane = new JScrollPane(cupomTextPane);
        add(scrollPane, BorderLayout.CENTER);

        // Painel para o QR Code
        JPanel qrCodePanel = new JPanel(new BorderLayout());
        qrCodePanel.setBackground(Color.WHITE);
        qrCodeLabel = new JLabel("", SwingConstants.CENTER);
        qrCodePanel.setPreferredSize(new Dimension(200, 220));
        qrCodeLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        qrCodePanel.add(qrCodeLabel, BorderLayout.CENTER);
        add(qrCodePanel, BorderLayout.SOUTH);

        // Painel de botões
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);

        imprimirButton = new JButton("Imprimir");
        imprimirButton.setEnabled(false);
        imprimirButton.addActionListener(evt -> {
            try {
                gerarPDF();
                JOptionPane.showMessageDialog(rootPane, "PDF gerado com sucesso!");
            } catch (DocumentException | IOException e) {
                JOptionPane.showMessageDialog(rootPane, "Erro ao gerar PDF: " + e.getMessage());
            }
        });

        salvarVoltarButton = new JButton("Salvar e Voltar");
        salvarVoltarButton.addActionListener(evt -> rootPane.showCard("listar_vendas"));

        buttonPanel.add(imprimirButton);
        buttonPanel.add(salvarVoltarButton);
        add(buttonPanel, BorderLayout.PAGE_END);

        // Forçar a atualização da interface
        revalidate();
        repaint();
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
