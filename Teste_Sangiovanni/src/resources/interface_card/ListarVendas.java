package resources.interface_card;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;
import io.github.cdimascio.dotenv.Dotenv;
import com.itextpdf.text.Font;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Scanner;

public class ListarVendas extends JPanel {

    private Dotenv dotenv;
    private JRootPane rootPane;

    private void initComponents() {
        JanelaPrincipal frame = (JanelaPrincipal) SwingUtilities.getWindowAncestor(rootPane);
        jPanelTopoTabela = new javax.swing.JPanel();
        jPanelTabela = new javax.swing.JPanel();
        jButtonCadastrar = new javax.swing.JButton();
        jButtonFiltrar = new javax.swing.JButton();
        pesquisaProduto = new javax.swing.JTextField();
        jtable = new javax.swing.JTable();
        jScrollPane = new javax.swing.JScrollPane();
        jPanel4 = new JPanel();

        setBackground(new java.awt.Color(243, 243, 223));
        setPreferredSize(new Dimension(1366, 650));


        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new Dimension(1200, 600));

        jPanelTopoTabela.setPreferredSize(new Dimension(1200, 100));
        jPanelTopoTabela.setBackground(new java.awt.Color(255, 255, 255));
        jPanelTopoTabela.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 30));

        pesquisaProduto.setBackground(new java.awt.Color(240, 240, 240));
        pesquisaProduto.setFont(new java.awt.Font("Cormorant Garamond", Font.BOLD, 14));
        pesquisaProduto.setText("Pesquisar Venda");
        pesquisaProduto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pesquisaProduto.setBorder(null);
        pesquisaProduto.setToolTipText("");
        pesquisaProduto.setPreferredSize(new java.awt.Dimension(200, 40));
        pesquisaProduto.addFocusListener(new java.awt.event.FocusListener() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (pesquisaProduto.getText().equals("Pesquisar Venda")) {
                    pesquisaProduto.setText("");
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (pesquisaProduto.getText().isEmpty()) {
                    pesquisaProduto.setText("Pesquisar Venda");
                }
            }
        });
        jPanelTopoTabela.add(pesquisaProduto);

        jPanelTopoTabela.add(Box.createHorizontalStrut(500));

        jButtonCadastrar.setFont(new java.awt.Font("Cormorant Garamond", Font.BOLD, 20));
        jButtonCadastrar.setIcon(new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/images/criar.png"))));
        jButtonCadastrar.setText("Registrar Venda");
        jButtonCadastrar.setBorder(null);
        jButtonCadastrar.setContentAreaFilled(false);
        jButtonCadastrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonCadastrar.setPreferredSize(new java.awt.Dimension(200, 40));
        jButtonCadastrar.addActionListener(e -> {
            frame.showCard("registrar_venda");
        });

        jPanelTopoTabela.add(jButtonCadastrar);

        jButtonFiltrar.setFont(new java.awt.Font("Cormorant Garamond", Font.BOLD, 20));
        jButtonFiltrar.setForeground(new java.awt.Color(6, 6, 10));
        jButtonFiltrar.setIcon(new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/images/filter.png"))));
        jButtonFiltrar.setText("Filtrar");
        jButtonFiltrar.setBorder(null);
        jButtonFiltrar.setContentAreaFilled(false);
        jButtonFiltrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonFiltrar.setPreferredSize(new java.awt.Dimension(90, 40));
        jPanelTopoTabela.add(jButtonFiltrar);

        jPanelTabela.setPreferredSize(new Dimension(1145, 450));
        jPanelTabela.setBackground(new java.awt.Color(255, 255, 255));
        jPanelTabela.setLayout(new BorderLayout());

        jtable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                        "Código", "Cliente", "Data", "Método de Pagamento", "Total", "Ações"
                }
        ));

        jtable.setIntercellSpacing(new Dimension(0, 0));
        jtable.setShowHorizontalLines(false);
        jtable.setShowVerticalLines(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        jtable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

        jtable.setFont(new java.awt.Font("Cormorant Infant", Font.BOLD, 14));
        jtable.setShowGrid(false);
        jtable.setIntercellSpacing(new Dimension(0, 0));
        jtable.setRowHeight(30);
        jtable.setFocusable(false);

        JTableHeader header = jtable.getTableHeader();
        header.setFont(new java.awt.Font("Cormorant Garamond", Font.BOLD, 18));
        header.setBackground(Color.WHITE);
        header.setForeground(new java.awt.Color(128, 0, 32));
        header.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(128, 0, 32)));

        jtable.setBackground(new java.awt.Color(255, 255, 255));
        jtable.setSelectionBackground(new java.awt.Color(228, 236, 242));
        jtable.setSelectionForeground(new java.awt.Color(0, 0, 0));

        for (int i = 0; i < jtable.getColumnCount(); i++) {
            if (!jtable.getColumnName(i).equals("Ações")) {
                jtable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }

        jtable.getColumn("Ações").setCellRenderer(new ButtonRendererProductVenda());
        jtable.getColumn("Ações").setCellEditor(new ButtonEditorProductVenda(jtable, rootPane, "ssss"));

        jScrollPane.setViewportView(jtable);
        jScrollPane.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(128, 0, 32)));
        jScrollPane.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(128, 0, 32)));

        jPanelTabela.add(jScrollPane, BorderLayout.CENTER);

        jPanel4.add(jPanelTopoTabela);
        jPanel4.add(jPanelTabela);
        setBorder(new EmptyBorder(20, 20, 20, 20));
        add(jPanel4);
    }

    public ListarVendas(JRootPane rootPane, JPanel mainPanel) {
        this.rootPane = rootPane;
        this.dotenv = Dotenv.load();
        this.initComponents();
        this.getVendas();
    }

    private void getVendas() {
        try {
            String urlAPI = this.dotenv.get("API_HOST");
            URL url = new URL(urlAPI + "/sale");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    JsonArray sales = JsonParser.parseString(response.toString()).getAsJsonArray();

                    DefaultTableModel tableModel = (DefaultTableModel) jtable.getModel();
                    tableModel.setRowCount(0);

                    for (int i = 0; i < sales.size(); i++) {
                        JsonObject sale = sales.get(i).getAsJsonObject();
                        String id = sale.get("id").getAsString();
                        JsonObject clientDTO = sale.get("client").getAsJsonObject();
                        String clientName = clientDTO.get("name").getAsString();
                        String date = sale.get("saleDate").getAsString();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        String dateFormated = date.formatted(formatter);
                        String paymentMethod = sale.get("paymentMethod").getAsString();
                        String totalValue = sale.get("totalPrice").getAsString();

                        tableModel.addRow(new Object[]{id, clientName, dateFormated, paymentMethod, totalValue, "Ações"});
                    }
                }
            } else {
                JOptionPane.showMessageDialog(rootPane,
                        "Ocorreu um erro ao carregar as vendas",
                        "Problema no Servidor",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(rootPane, "Erro: " + e.getMessage());
        }
    }

    public void atualizarDados() {
        this.getVendas();
    }

    private javax.swing.JTextField pesquisaProduto;
    private javax.swing.JTable jtable;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JPanel jPanelTopoTabela;
    private javax.swing.JPanel jPanelTabela;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JButton jButtonCadastrar;
    private javax.swing.JButton jButtonFiltrar;
}

class ButtonRendererProductVenda extends JPanel implements TableCellRenderer {
    private final JButton taxreceiptButton = new JButton();

    public ButtonRendererProductVenda() {
        setOpaque(true);
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));

        this.taxreceiptButton.setIcon(new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/images/Archive.png"))));
        this.taxreceiptButton.setBackground(Color.WHITE);
        this.taxreceiptButton.setBorder(null);
        this.taxreceiptButton.setContentAreaFilled(false);
        this.taxreceiptButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        add(taxreceiptButton);

        taxreceiptButton.setFocusable(false);
    }


    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(128, 0, 32));
        return this;
    }
}

class ButtonEditorProductVenda extends AbstractCellEditor implements TableCellEditor {
    private final JPanel panel = new JPanel();
    private final JButton taxreceiptButton = new JButton();
    private final JTable table;
    private int currentRow;
    private final JFrame frame;
    private final String APIURL;

    public ButtonEditorProductVenda(JTable table, JRootPane rootPane, String APIURL) {
        this.table = table;
        this.frame = (JFrame) SwingUtilities.getWindowAncestor(rootPane);
        this.APIURL = APIURL;

        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));


        this.taxreceiptButton.setIcon(new javax.swing.ImageIcon(Objects.requireNonNull(
                getClass().getResource("/resources/images/Archive.png"))));
        this.taxreceiptButton.setBackground(new java.awt.Color(255, 255, 255));
        this.taxreceiptButton.setForeground(new java.awt.Color(128, 0, 32));
        this.taxreceiptButton.setFocusPainted(false);
        this.taxreceiptButton.setBorder(null);
        this.taxreceiptButton.setContentAreaFilled(false);
        this.taxreceiptButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        panel.add(taxreceiptButton);

        // Action for delete button
        taxreceiptButton.addActionListener(e -> {
            int cellIndex = table.getSelectedRow();
            Object cellValue = table.getValueAt(cellIndex, 0);
            abrirModalCupom(cellValue.toString());
            stopCellEditing();
        });
    }

    private void abrirModalCupom(String id) {
        String jsonResponse = obterDadosCupom(id);

        int startIndex = jsonResponse.indexOf("{");
        if (startIndex > 0) {
            jsonResponse = jsonResponse.substring(startIndex);
        }

        JsonReader reader = new JsonReader(new StringReader(jsonResponse));
        reader.setLenient(true);

        JsonObject json = JsonParser.parseString(jsonResponse).getAsJsonObject();
        String vendaId = json.get("idSale").getAsString();
        String empresa = json.get("enterpriseName").getAsString();
        String endereco = json.get("address").getAsString();
        String cidadeEstado = json.get("cityState").getAsString();
        String cnpj = json.get("cnpj").getAsString();
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

        JLabel lblCupom = new JLabel("<html><center><b>CUPOM FISCAL</b><br><br>"
                + empresa + "<br>"
                + endereco + "<br>"
                + cidadeEstado + "<br><br>"
                + "CNPJ: " + cnpj + " &nbsp;&nbsp; " + data + "<br>"
                + "Hora: " + hora + "<br>"
                + "CCF: " + ccf + " - IE: " + ie + " - CDD: " + cdd + "<br>"
                + "IM: " + im + "<br>"
                + "------------------------------------------------------------------------------------------------<br>"
                + produtosStr + "<br>"
                + "TOTAL: <b>R$" + totalPrice + "</b><br>"
                + "DESCONTO: R$" + discount + "<br>"
                + "PAGAMENTO: " + pagamento + "<br>"
                + "----------------------------------------------------------------<br>"
                + "CÓDIGO DO QR CODE: " + qrCode + "<br>"
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
        btnImprimir.addActionListener(e -> {
            try {
                gerarPDF(vendaId, empresa, endereco, cidadeEstado, cnpj, data, hora, ccf, ie, cdd, im, qrCode, products, totalPrice, discount, pagamento);
                JOptionPane.showMessageDialog(modal, "PDF gerado com sucesso!");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(modal, "Erro ao gerar PDF.");
            }
        });

        constraints.gridy = 2;
        panel.add(btnImprimir, constraints);

        modal.add(panel, BorderLayout.CENTER);
        modal.setLocationRelativeTo(null);
        modal.setVisible(true);
    }

    private void gerarPDF(String vendaId, String empresa, String endereco, String cidadeEstado, String cnpj,
                          String data, String hora, String ccf, String ie, String cdd, String im, String qrCode,
                          JsonArray produtos, double totalPrice, double discount, String pagamento) throws DocumentException, IOException {

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
            BufferedImage qrImage = gerarQRCode(qrCode, 100, 100);

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

    private BufferedImage gerarQRCode(String text, int width, int height) throws WriterException, IOException {
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


            JsonObject jsonObject = JsonParser.parseString(json.toString()).getAsJsonObject();
            return json.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (com.google.gson.JsonSyntaxException e) {
            System.out.println("Erro de JSON malformado: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        currentRow = row;
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }
}

