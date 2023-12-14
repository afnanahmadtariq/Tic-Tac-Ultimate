package Tic_Tac_Ultimate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Online {
    public static void main(String[] args) {
        try {
            // Set the server URL
            URL url = new URL("https://tic-tac-server.azurewebsites.net/api/HttpTriggerJava?name=John&index=1,2,3&win=5&code=QDslAbCrmnMxuTsuDcyiKPoRC8xQYq52Ar8HaRbqh25-AzFuKoeMrg==");

            // Open a connection to the server
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method
            connection.setRequestMethod("POST");

            // Send the request
            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write("HTTP Body".getBytes());
            outputStream.flush();
            outputStream.close();

            // Read the response
            int responseCode = connection.getResponseCode();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder responseBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            reader.close();

            // Print the response
            System.out.println("Response Code: " + responseCode);
            System.out.println("Response Body: " + responseBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
