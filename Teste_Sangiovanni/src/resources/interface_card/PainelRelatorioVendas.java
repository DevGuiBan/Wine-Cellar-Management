package resources.interface_card;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import io.github.cdimascio.dotenv.Dotenv;

public class PainelRelatorioVendas extends JPanel {
    private JTable tabelaVendas;
    private JTextField txtTotalVendas;
    private JTextField txtTotalReais;
    private JTextField txtMediaVendas;
    private JLabel lblTitulo;
    private JLabel lblPeriodo;
    private JLabel lblOrdenacao;
    private final Dotenv dotenv = Dotenv.load();
    private JPanel rootPanel;
    private String dataIn;
    private String dataOut;

    public PainelRelatorioVendas(JPanel rootPanel) {
        this.rootPanel = rootPanel;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(Color.WHITE);

        add(criarPainelSuperior(), BorderLayout.NORTH);
        add(criarPainelTabela(), BorderLayout.CENTER);
        add(criarPainelInferior(), BorderLayout.SOUTH);
    }

    private JPanel criarPainelSuperior() {
        JPanel painelSuperior = new JPanel(new BorderLayout());
        painelSuperior.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        painelSuperior.setBackground(Color.WHITE);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBotoes.setBackground(Color.WHITE);
        JButton btnVoltar = new JButton();
        btnVoltar.setIcon(new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/images/back.png"))));
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVoltar.setFocusable(false);
        btnVoltar.setFocusPainted(false);
        btnVoltar.setBorderPainted(false);
        btnVoltar.setContentAreaFilled(false);
        btnVoltar.setBorder(null);
        btnVoltar.addActionListener(evt -> {
            CardLayout cl = (CardLayout) rootPanel.getLayout();
            cl.show(rootPanel, "listar_vendas");
        });

        JButton btnImprimir = new JButton();
        btnImprimir.setIcon(new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/images/impressora.png"))));
        btnImprimir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnImprimir.setFocusable(false);
        btnImprimir.setFocusPainted(false);
        btnImprimir.setBorderPainted(false);
        btnImprimir.setContentAreaFilled(false);
        btnImprimir.setBorder(null);
        btnImprimir.addActionListener(evt->{
            gerarRelatorioPDF();
        });

        painelBotoes.add(btnVoltar);
        painelBotoes.add(btnImprimir);

        JPanel painelTitulo = new JPanel(new BorderLayout());
        painelTitulo.setBackground(Color.WHITE);
        lblTitulo = new JLabel("Relatório de Vendas", SwingConstants.CENTER);
        lblTitulo.setFont(new java.awt.Font("Cormorant Infant Bold", Font.BOLD, 30));
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setBackground(new Color(128, 0, 32));
        separator.setForeground(new Color(128, 0, 32));

        painelTitulo.add(lblTitulo, BorderLayout.CENTER);
        painelTitulo.add(separator, BorderLayout.SOUTH);

        painelSuperior.add(painelBotoes, BorderLayout.WEST);
        painelSuperior.add(painelTitulo, BorderLayout.CENTER);

        return painelSuperior;
    }

    private JPanel criarPainelTabela() {
        JPanel painelTabela = new JPanel(new BorderLayout());

        painelTabela.setBackground(Color.WHITE);
        lblPeriodo = new JLabel("Período: --/--/---- até --/--/----", SwingConstants.CENTER);
        lblPeriodo.setFont(new java.awt.Font("Cormorant Garamond", Font.BOLD, 22));

        lblOrdenacao = new JLabel("Ordenado por: Maior número de vendas", SwingConstants.CENTER);
        lblOrdenacao.setFont(new java.awt.Font("Cormorant Garamond", Font.BOLD, 18));

        JPanel painelLabels = new JPanel(new GridLayout(2, 1));
        painelLabels.setBackground(Color.WHITE);
        painelLabels.add(lblPeriodo);
        painelLabels.add(lblOrdenacao);

        painelTabela.add(painelLabels, BorderLayout.NORTH);

        String[] colunas = {"Produto", "Quantidade", "TOTAL (R$)"};
        DefaultTableModel modeloTabela = new DefaultTableModel(null, colunas);
        tabelaVendas = new JTable(modeloTabela);
        tabelaVendas.setFillsViewportHeight(true);
        tabelaVendas.setEnabled(false);
        tabelaVendas.setShowHorizontalLines(false);
        tabelaVendas.setShowVerticalLines(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);


        tabelaVendas.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tabelaVendas.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tabelaVendas.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tabelaVendas.setFont(new java.awt.Font("Cormorant Infant", Font.BOLD, 14));
        tabelaVendas.setShowGrid(false);
        tabelaVendas.setIntercellSpacing(new Dimension(0, 0));
        tabelaVendas.setRowHeight(30);
        tabelaVendas.setFocusable(false);

        JTableHeader header = tabelaVendas.getTableHeader();
        header.setFont(new java.awt.Font("Courier New", Font.BOLD, 30));
        header.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        header.setResizingAllowed(false);
        header.setReorderingAllowed(false);

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        headerRenderer.setBackground(new Color(217, 217, 217));
        headerRenderer.setForeground(Color.BLACK);
        for (int i = 0; i < tabelaVendas.getColumnModel().getColumnCount(); i++) {
            tabelaVendas.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }


        painelTabela.add(new JScrollPane(tabelaVendas), BorderLayout.CENTER);
        return painelTabela;
    }

    private JPanel criarPainelInferior() {
        JPanel painelInferior = new JPanel(new GridLayout(1, 3, 10, 10));
        painelInferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painelInferior.setBackground(Color.WHITE);

        painelInferior.add(criarPainelInfo("Total de Vendas Realizadas:", txtTotalVendas = new JTextField("-")));
        painelInferior.add(criarPainelInfo("Total de Vendas em Reais:", txtTotalReais = new JTextField("-")));
        painelInferior.add(criarPainelInfo("Média de Vendas:", txtMediaVendas = new JTextField("-")));

        return painelInferior;
    }

    private JPanel criarPainelInfo(String titulo, JTextField campo) {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(Color.WHITE);
        JLabel label = new JLabel(titulo, SwingConstants.CENTER);
        label.setFont(new java.awt.Font("Cormorant Garamond Bold", Font.BOLD, 16));
        painel.add(label, BorderLayout.NORTH);

        campo.setFont(new java.awt.Font("Cormorant Infant", Font.BOLD, 18));
        campo.setEditable(false);
        campo.setFocusable(false);
        campo.setBorder(new LineBorder(Color.DARK_GRAY, 1));
        campo.setHorizontalAlignment(JTextField.CENTER);
        campo.setBackground(Color.WHITE);
        painel.add(campo, BorderLayout.CENTER);

        return painel;
    }

    public void atualizarDadosDaAPI(String dataIn, String dataOut) {
        this.dataIn = dataIn;
        this.dataOut = dataOut;
        try {
            String requestUrl = dotenv.get("API_HOST") + "/sale/report?start_date="
                    + URLEncoder.encode(dataIn, StandardCharsets.UTF_8)
                    + "&end_date=" + URLEncoder.encode(dataOut, StandardCharsets.UTF_8);
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
                JsonArray salesArray = jsonResponse.getAsJsonArray("sales");
                double totalSalesAmount = 0;
                int totalSalesCount = 0;
                DefaultTableModel tableModel = (DefaultTableModel) tabelaVendas.getModel();
                tableModel.setRowCount(0);

                for (int i = 0; i < salesArray.size(); i++) {
                    JsonObject sale = salesArray.get(i).getAsJsonObject();
                    String name = sale.get("name").getAsString();
                    int quantity = sale.get("quantity").getAsInt();
                    double totalSum = sale.get("totalSum").getAsDouble();

                    totalSalesCount += quantity;
                    totalSalesAmount += totalSum;
                    tableModel.addRow(new Object[]{name, quantity, String.format("%.2f", totalSum)});
                }

                txtTotalVendas.setText(String.valueOf(totalSalesCount));
                txtTotalReais.setText(String.format("R$ %.2f", totalSalesAmount));
                txtMediaVendas.setText(String.format("%.2f", jsonResponse.get("avgSaleTotal").getAsDouble()));
                lblPeriodo.setText("Período: " + dataIn + " até " + dataOut);
            }
            connection.disconnect();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void gerarRelatorioPDF() {
        Document document = new Document();
        try {
            String pasta = "relatorios/";
            File diretorio = new File(pasta);
            if (!diretorio.exists()) {
                diretorio.mkdirs();
            }
            String caminhoPDF = pasta + "relatorio_vendas_"
                    +this.dataIn.replace("/","-")+" - "
                    +this.dataOut.replace("/","-")+".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(caminhoPDF));
            document.open();

            Font fonteTitulo = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Font fonteNormal = new Font(Font.FontFamily.HELVETICA, 12);
            Font fonteNegrito = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Font fonteCinzelVermelho = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, new BaseColor(128,0,32));

            Paragraph titulo = new Paragraph("Relatório de Vendas", fonteTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            document.add(new Paragraph("\n"));

            Paragraph empresa = new Paragraph("Casa San'Giovanni", fonteCinzelVermelho);
            empresa.setAlignment(Element.ALIGN_CENTER);
            document.add(empresa);
            document.add(new Paragraph("\n"));

            JLabel lblEndereco = new JLabel();
            lblEndereco.setText("RUA 9 - 1437 - Bairro Sabores - Cedro - CE - Brasil");
            lblEndereco.setForeground(Color.BLACK);

            JLabel lblTelEm = new JLabel();
            lblTelEm.setText("Tel: 88 91234-5678        E-mail: atendimento@sangiovanni.com");
            lblTelEm.setForeground(Color.BLACK);

            JLabel lblNmCn = new JLabel();
            lblNmCn.setText("Casa San’Giovanni Ltda.         CNPJ: 45.987.654/0001-23");
            lblNmCn.setForeground(Color.BLACK);

            document.add(new Paragraph(lblEndereco.getText(),fonteNormal));
            document.add(new Paragraph(lblTelEm.getText(),fonteNormal));
            document.add(new Paragraph(lblNmCn.getText(),fonteNormal));
            document.add(new Paragraph("\n"));

            document.add(new Paragraph(lblPeriodo.getText(), fonteNegrito));
            document.add(new Paragraph(lblOrdenacao.getText(), fonteNegrito));
            document.add(new Paragraph("\n"));

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.addCell(new PdfPCell(new Phrase("Produto", fonteNegrito)));
            table.addCell(new PdfPCell(new Phrase("Quantidade", fonteNegrito)));
            table.addCell(new PdfPCell(new Phrase("Total (R$)", fonteNegrito)));

            DefaultTableModel model = (DefaultTableModel) tabelaVendas.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                table.addCell(new PdfPCell(new Phrase(model.getValueAt(i, 0).toString(), fonteNormal)));
                table.addCell(new PdfPCell(new Phrase(model.getValueAt(i, 1).toString(), fonteNormal)));
                table.addCell(new PdfPCell(new Phrase(model.getValueAt(i, 2).toString(), fonteNormal)));
            }
            document.add(table);
            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Total de Vendas Realizadas: " + txtTotalVendas.getText(), fonteNegrito));
            document.add(new Paragraph("Total de Vendas em Reais: " + txtTotalReais.getText(), fonteNegrito));
            document.add(new Paragraph("Média de Vendas: " + txtMediaVendas.getText(), fonteNegrito));

            document.close();
            JOptionPane.showMessageDialog(this, "Relatório gerado com sucesso: " + caminhoPDF, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao gerar PDF: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
