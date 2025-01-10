package test;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestAPIConsumer {
    public static void main(String[] args) {
        try {
            // URL da API
            String apiUrl = "https://jsonplaceholder.typicode.com/posts/1"; // Exemplo de API

            // Criação da conexão
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Verifica o código de resposta
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // Status 200
                // Lê a resposta da API
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Converte a string JSON em um objeto formatado com Gson
                Gson gson = new Gson();
                // pega o objeto json
                JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();

                // separa por chave do json
                int userId = jsonObject.get("userId").getAsInt();
                int id = jsonObject.get("id").getAsInt();
                String title = jsonObject.get("title").getAsString();
                String body = jsonObject.get("body").getAsString();

                // mostra chaves retiradas
                System.out.println("UserId: " + userId);
                System.out.println("Id: " + id);
                System.out.println("Title: " + title);
                System.out.println("Body: " + body);
            } else {
                System.out.println("Erro ao consumir API: Código " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
