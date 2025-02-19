package resources.interface_card;

import com.google.gson.*;
import io.github.cdimascio.dotenv.Dotenv;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class ProdutoVenda extends JPanel {
    private final Dotenv dotenv;
    private final JFrame rootPane;
    public ProdutoVenda(JFrame rootPane) {
      initComponents();
      this.dotenv = Dotenv.load();
      getProducts();
      this.rootPane = rootPane;
    }

    private void initComponents(){
        // inicialização de variáveis
        jLabelValorUnitario = new JLabel();
        jLabelQuantidade = new JLabel();
        jLabelNome = new JLabel();

        jPanelContent = new JPanel(new GridBagLayout());

        jSpinnerQuantidade = new JSpinner((new SpinnerNumberModel(0.0, 0.0, Integer.MAX_VALUE, 1)));
        jTextFildQtdValorUnitario = new JTextField();

        jComboBoxProduct = new JComboBox<Product>();

        gbc = new GridBagConstraints();

        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(1200, 300));
        setLayout(new FlowLayout(FlowLayout.CENTER,30,30));

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        Dimension fieldSize = new Dimension(400, 40);

        jPanelContent.setPreferredSize(new Dimension(1200, 450));
        jPanelContent.setBackground(Color.WHITE);
        jPanelContent.setBorder(BorderFactory.createEmptyBorder(-150, 10, 10, 10));

        jLabelNome.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelNome.setText("Nome:");
        jLabelNome.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 100);
        jPanelContent.add(jLabelNome, gbc);

        jComboBoxProduct.setBackground(new Color(255, 255, 255));
        jComboBoxProduct.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jComboBoxProduct.setFont(new Font("Cormorant Garamond", 1, 18));
        jComboBoxProduct.setPreferredSize(fieldSize);
        jComboBoxProduct.setForeground(Color.BLACK);
        jComboBoxProduct.addItemListener(evt->{
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                Product prod = (Product) jComboBoxProduct.getSelectedItem();
                Double value = prod.getValorUnitario();
                jTextFildQtdValorUnitario.setText(value.toString());
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 100);
        jPanelContent.add(jComboBoxProduct, gbc);

        jLabelQuantidade.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelQuantidade.setText("Quantidade:");
        jLabelQuantidade.setForeground(Color.BLACK);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        jPanelContent.add(jLabelQuantidade, gbc);

        jSpinnerQuantidade.setBackground(new Color(255, 255, 255));
        jSpinnerQuantidade.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jSpinnerQuantidade.setFont(new Font("Cormorant Infant", 1, 18));
        jSpinnerQuantidade.setPreferredSize(fieldSize);
        jSpinnerQuantidade.setForeground(Color.BLACK);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 0);
        jPanelContent.add(jSpinnerQuantidade, gbc);

        jLabelValorUnitario.setFont(new Font("Cormorant Garamond", 1, 18));
        jLabelValorUnitario.setText("Valor Unitário:");
        jLabelValorUnitario.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 0, 100);
        jPanelContent.add(jLabelValorUnitario, gbc);

        jTextFildQtdValorUnitario.setBackground(new Color(255, 255, 255));
        jTextFildQtdValorUnitario.setBackground(Color.WHITE);
        jTextFildQtdValorUnitario.setForeground(Color.BLACK);
        jTextFildQtdValorUnitario.setEditable(false);
        jTextFildQtdValorUnitario.setFont(new Font("Cormorant Infant", Font.BOLD, 18));
        jTextFildQtdValorUnitario.setBorder(new MatteBorder(2, 2, 2, 2, new Color(128, 0, 32)));
        jTextFildQtdValorUnitario.setPreferredSize(fieldSize);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 20, 100);
        jPanelContent.add(jTextFildQtdValorUnitario, gbc);

        add(jPanelContent);
    }

    private void getProducts(){
        try {
            String urlAPI = this.dotenv.get("API_HOST");
            URL url = new URL(urlAPI + "/product");
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

                JsonArray products = JsonParser.parseString(response.toString()).getAsJsonArray();

                DefaultComboBoxModel<Product> model = new DefaultComboBoxModel<>();

                for (JsonElement element : products) {
                    JsonObject type = element.getAsJsonObject();
                    int id = type.get("id").getAsInt();
                    String name = type.get("name").getAsString();
                    Double price = type.get("price").getAsDouble();

                    model.addElement(new Product(name,id,price));
                }
                this.jComboBoxProduct.setModel(model);
            } else {
                JOptionPane.showOptionDialog(rootPane,
                        "Ocorreu um erro ao carregar os produtos",
                        "Problema no Servidor",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,null,null);
                connection.disconnect();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }

    private JLabel jLabelQuantidade;
    private JLabel jLabelNome;
    private JLabel jLabelValorUnitario;

    private JPanel jPanelContent;

    private JTextField jTextFildQtdValorUnitario;
    private JSpinner jSpinnerQuantidade;

    private JComboBox<Product> jComboBoxProduct;

    private GridBagConstraints gbc;
}
