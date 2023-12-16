package Tic_Tac_Ultimate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Arrays;

class Run{
    public static void main(String[] args) throws IOException {
        Online online = new Online();
        online.test();
    }
}
public class Online {
    private URL url;
    private HttpURLConnection connection;
//    private static String secret = "QDslAbCrmnMxuTsuDcyiKPoRC8xQYq52Ar8HaRbqh25-AzFuKoeMrg==";
    private static String link = "https://tic-tac-server.azurewebsites.net/api/HttpTriggerJava";
    Online() throws IOException {
        // Set the server URL
//        url = new URL(link + "?name=playerName&code=" + secret);
        link = "http://localhost:61877/api/HttpTriggerJava";
        url = new URL(link + "?name=playerName&code=");
        // Open a connection to the server
        connection = (HttpURLConnection) url.openConnection();
    }
    public void test(){
        try {
            send("0,0","POST");

            // Read the response
            int responseCode = connection.getResponseCode();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            System.out.println("Set up");
            String line;
            StringBuilder responseBuilder = new StringBuilder();
            responseBuilder.append(reader.readLine());
            System.out.println("Response Body: " + responseBuilder.toString());
            while ((line = reader.readLine()) != null) {
                responseBuilder = new StringBuilder();
                responseBuilder.append(line);
                String response = responseBuilder.toString();
                int[] index = parseIndexParameter(response);
                System.out.println("Index: " + Arrays.toString(index));
            }
            reader.close();

            // Print the response
            System.out.println("Response Code: " + responseCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private int[] parseIndexParameter(String indexParam) {
        // Parse the index parameter and convert it to an int array
        if (indexParam != null && !indexParam.isEmpty()) {
            String[] indexArray = indexParam.split(",");
            int[] index = new int[indexArray.length];
            for (int i = 0; i < indexArray.length; i++) {
                index[i] = Integer.parseInt(indexArray[i]);
            }
            return index;
        }
        return new int[]{-999,-999};
    }
    private int parseWinParameter(String winParam) {
        // Parse the win parameter and convert it to an int
        if (winParam != null && !winParam.isEmpty()) {
            return Integer.parseInt(winParam);
        }
        return -999;
    }
    private void send(String message, String method) throws IOException {
        // Set the request method
        connection.setRequestMethod(method);

        // Send the request
        connection.setDoOutput(true);
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(message.getBytes());
        outputStream.flush();
        outputStream.close();
    }
    public void authenticate(String username, String password){}
    public void saveProfile(){}
    public void deleteProfile() throws IOException {
        // Set the request method
        connection.setRequestMethod("DELETE");

        // Send the request
        connection.setDoOutput(true);
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write("HTTP Body".getBytes());
        outputStream.flush();
        outputStream.close();
    }
    public void confirm(){}
    public void mark(int[] index){}
    public void mark(int[] index,int[] superIndex){}
    public void slide(int[] drawIndex, int[] insertIndex){}
}
