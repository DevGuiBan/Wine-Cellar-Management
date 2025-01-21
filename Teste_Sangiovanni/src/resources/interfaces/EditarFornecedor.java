package resources.interfaces;

import com.google.gson.*;
import io.github.cdimascio.dotenv.Dotenv;
import com.google.gson.JsonObject;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author rafaj
 */
public class EditarFornecedor extends javax.swing.JFrame {
    private final String id_supplier;
    private final Dotenv dotenv;
    /**
     * Creates new form CadastrarProduto
     */
    public EditarFornecedor(String id) {
        this.dotenv = Dotenv.load();
        this.id_supplier = id;
        initComponents();
        getSupplier();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        try{
            jTextField2 = new javax.swing.JFormattedTextField(new MaskFormatter("(##) #####-####"));
            jTextField4 = new javax.swing.JFormattedTextField(new MaskFormatter("##.###.###/####-##"));
            jTextField5 = new javax.swing.JFormattedTextField();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        jLabel8 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(243, 243, 223));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(1366, 770));

        jPanel1.setBackground(new java.awt.Color(243, 243, 223));

        jPanel2.setBackground(new java.awt.Color(243, 243, 223));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Cinzel", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Casa San'Giovanni");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel2.add(jLabel1, java.awt.BorderLayout.CENTER);

        jPanel3.setBackground(new java.awt.Color(128, 0, 32));
        jPanel3.setMaximumSize(new java.awt.Dimension(1366, 35));
        jPanel3.setMinimumSize(new java.awt.Dimension(1366, 35));
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 50, 10);
        flowLayout1.setAlignOnBaseline(true);
        jPanel3.setLayout(flowLayout1);

        jButton1.setBackground(new java.awt.Color(128, 0, 32));
        jButton1.setFont(new java.awt.Font("Cormorant Garamond SemiBold", 0, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/images/wine.png")))); // NOI18N
        jButton1.setText("Produtos");
        jButton1.setBorder(null);
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1);

        jButton2.setBackground(new java.awt.Color(128, 0, 32));
        jButton2.setFont(new java.awt.Font("Cormorant Garamond SemiBold", 0, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(225, 235, 43));
        jButton2.setIcon(new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/images/caminhao.png")))); // NOI18N
        jButton2.setText("Fornecedores");
        jButton2.setBorder(null);
        jButton2.setContentAreaFilled(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2);

        jButton3.setBackground(new java.awt.Color(128, 0, 32));
        jButton3.setFont(new java.awt.Font("Cormorant Garamond SemiBold", 0, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/images/sacola.png")))); // NOI18N
        jButton3.setText("Vendas");
        jButton3.setContentAreaFilled(false);
        jButton3.setBorder(null);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton3);

        jPanel4.setBackground(new java.awt.Color(243, 243, 223));
        jPanel4.setForeground(new java.awt.Color(255, 255, 255));
        jPanel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel4.setLayout(null);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, java.awt.Color.darkGray));

        jLabel2.setFont(new java.awt.Font("Cormorant Garamond SemiBold", 0, 30)); // NOI18N
        jLabel2.setText("Editar Fornecedor");

        jSeparator1.setBackground(new java.awt.Color(128, 0, 32));
        jSeparator1.setForeground(new java.awt.Color(128, 0, 32));

        jPanel6.setBackground(new java.awt.Color(204, 153, 0));
        jPanel6.setOpaque(false);
        jPanel6.setLayout(new java.awt.GridLayout(6, 2, 0, 5));

        jLabel3.setFont(new java.awt.Font("Cormorant Garamond", 1, 18)); // NOI18N
        jLabel3.setText("Nome:");
        jPanel6.add(jLabel3);

        jTextField1.setBackground(new java.awt.Color(255, 255, 255));
        jTextField1.setBorder(new MatteBorder(2,2,2,2,new Color(128,0,32)));
        jTextField1.setFont(new java.awt.Font("Cormorant Garamond", 1, 18));
        jPanel6.add(jTextField1);

        jLabel4.setFont(new java.awt.Font("Cormorant Garamond", 1, 18)); // NOI18N
        jLabel4.setText("CNPJ:");
        jPanel6.add(jLabel4);

        jTextField4.setBackground(new java.awt.Color(255, 255, 255));
        jTextField4.setBorder(new MatteBorder(2,2,2,2,new Color(128,0,32)));
        jTextField4.setFont(new java.awt.Font("Cormorant Garamond", 1, 18));
        jPanel6.add(jTextField4);

        jLabel5.setFont(new java.awt.Font("Cormorant Garamond", 1, 18)); // NOI18N
        jLabel5.setText("Endereço:");
        jPanel6.add(jLabel5);

        jTextField5.setBackground(new java.awt.Color(255, 255, 255));
        jTextField5.setBorder(new MatteBorder(2,2,2,2,new Color(128,0,32)));
        jTextField5.setFont(new java.awt.Font("Cormorant Garamond", 1, 18));
        String regex = "^(.+?) - (.+?) - (\\d+) - ([A-Z]{2})$";
        Pattern pattern = Pattern.compile(regex);
        jTextField5.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                String text = ((JFormattedTextField) input).getText();
                Matcher matcher = pattern.matcher(text);
                if (matcher.matches()) {
                    input.setBackground(Color.WHITE);
                    return true;
                } else {
                    input.setBackground(Color.PINK);
                    JOptionPane.showMessageDialog(EditarFornecedor.super.rootPane,
                            "O formato deve ser: rua - bairro - numero - UF",
                            "Formato Inválido",
                            JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        });
        jPanel6.add(jTextField5);

        jPanel7.setBackground(new java.awt.Color(204, 153, 0));
        jPanel7.setOpaque(false);
        jPanel7.setLayout(new java.awt.GridLayout(6, 2, 0, 5));

        jLabel6.setFont(new java.awt.Font("Cormorant Infant", Font.BOLD, 18)); // NOI18N
        jLabel6.setText("Telefone:");
        jPanel7.add(jLabel6);

        jTextField2.setBackground(new java.awt.Color(255, 255, 255));
        jTextField2.setBorder(new MatteBorder(2,2,2,2,new Color(128,0,32)));
        jTextField2.setFont(new java.awt.Font("Cormorant Garamond", 1, 18));
        jPanel7.add(jTextField2);

        jLabel7.setFont(new java.awt.Font("Cormorant Garamond", 1, 18)); // NOI18N
        jLabel7.setText("Email:");
        jPanel7.add(jLabel7);

        jTextField3.setBackground(new java.awt.Color(255, 255, 255));
        jTextField3.setBorder(new MatteBorder(2,2,2,2,new Color(128,0,32)));
        jTextField3.setFont(new java.awt.Font("Cormorant Garamond", 1, 18));
        jPanel7.add(jTextField3);

        jLabel8.setFont(new java.awt.Font("Cormorant Garamond", 1, 18)); // NOI18N
        jLabel8.setText("Observações:");
        jPanel7.add(jLabel8);

        jTextField6.setBackground(new java.awt.Color(255, 255, 255));
        jTextField6.setBorder(new MatteBorder(2,2,2,2,new Color(128,0,32)));
        jTextField6.setFont(new java.awt.Font("Cormorant Garamond", 1, 18));
        jPanel7.add(jTextField6);

        jButton4.setBackground(new java.awt.Color(225, 225, 200));
        jButton4.setFont(new java.awt.Font("Cormorant Garamond SemiBold", 0, 18)); // NOI18N
        jButton4.setText("Cancelar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(0, 128, 17));
        jButton5.setFont(new java.awt.Font("Cormorant Garamond SemiBold", 0, 18)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Editar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addGap(117, 117, 117)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(50, 50, 50)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addGap(512, 512, 512)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(96, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1366, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(662, 662, 662)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 52, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1368, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        JFrame tela = new ListarProduto();
        SwingUtilities.invokeLater(() -> {
            this.setVisible(false);
            tela.setVisible(true);
        });
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        JFrame tela = new ListarFornecedor();
        SwingUtilities.invokeLater(() -> {
            this.setVisible(false);
            tela.setVisible(true);
        });
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        JFrame tela = new ListarVenda();
        SwingUtilities.invokeLater(() -> {
            this.setVisible(false);
            tela.setVisible(true);
        });
    }

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt){
        JFrame tela = new ListarFornecedor();
        SwingUtilities.invokeLater(() -> {
            this.setVisible(false);
            tela.setVisible(true);
        });
    }

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            // getting the data to send for api
            String name = jTextField1.getText();
            String address = jTextField5.getText();
            String cnpj = jTextField4.getText();
            String phone_number = jTextField2.getText();
            String email = jTextField3.getText();
            String observations = jTextField6.getText();

            if(name == null || name.isEmpty()){
                throw new Exception("Informe o nome do Fornecedor!");
            }

            if(email == null || email.isEmpty()){
                throw new Exception("Informe o e-mail do Fornecedor!");
            }

            // creating the json to send
            JsonObject jsonData = new JsonObject();
            jsonData.addProperty("name", name);
            jsonData.addProperty("address", address);
            jsonData.addProperty("cnpj", cnpj);
            jsonData.addProperty("phone_number", phone_number);
            jsonData.addProperty("email", email);
            jsonData.addProperty("observation", observations);

            // making the request
            String urlAPI = this.dotenv.get("API_HOST");
            URL url = new URL(urlAPI + "/supplier/"+this.id_supplier);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // send the request with json
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonData.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int statusCode = connection.getResponseCode();
            StringBuilder response = new StringBuilder();

            if (statusCode >= 200 && statusCode <= 300) {
                jTextField1.setText("");
                jTextField2.setText("");
                jTextField3.setText("");
                jTextField4.setText("");
                jTextField5.setText("");
                jTextField6.setText("");
                JOptionPane.showOptionDialog(rootPane,
                        "O fornecedor e suas informações foram atualizadas com sucesso!",
                        "Fornecedor Atualizado",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,null,null);

                connection.disconnect();

                JFrame tela = new ListarFornecedor();
                SwingUtilities.invokeLater(() -> {
                    this.setVisible(false);
                    tela.setVisible(true);
                });
            } else {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream(), "utf-8"))) {
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                }
                JOptionPane.showOptionDialog(rootPane,
                        "Por favor, verifique as informações dos campos e tente novamente!\n",
                        "Fornecedor Não Atualizado",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,null,null);
                connection.disconnect();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }

    private void getSupplier(){
        try {
            String urlAPI = this.dotenv.get("API_HOST");
            URL url = new URL(urlAPI + "/supplier/"+this.id_supplier);
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

                Gson gson = new Gson();
                JsonObject suppliers = JsonParser.parseString(response.toString()).getAsJsonObject();

                jTextField1.setText(suppliers.get("name").getAsString());
                jTextField2.setText(suppliers.get("phone_number").getAsString());
                jTextField3.setText(suppliers.get("email").getAsString());
                jTextField4.setText(suppliers.get("cnpj").getAsString());
                jTextField5.setText(suppliers.get("address").getAsString());
                if(suppliers.has("observation") && !suppliers.get("observation").isJsonNull()){
                    jTextField6.setText(suppliers.get("observation").getAsString());
                }

                connection.disconnect();
            }
        }
            catch (Exception e) {
                JOptionPane.showOptionDialog(rootPane,
                        "Não foi possivel carregar os dados do fornecedor!\n"+e.getMessage(),
                        "Fornecedor Não Encontrado",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,null,null);
            }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EditarFornecedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditarFornecedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditarFornecedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditarFornecedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditarFornecedor("").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JFormattedTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JFormattedTextField jTextField4;
    private javax.swing.JFormattedTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables
}
