package userActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws IOException {
        if(args.length == 0) {
            System.out.println("Please enter a user name");
            return;
        }
        String userName = args[0];
        String apiUrl = "https://api.github.com/users/" + userName + "/events";
        System.out.println("API URL: " + apiUrl);

        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        if(responseCode == 200) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            reader.close();
            System.out.println(builder.toString());
        } else {
            System.out.println("Failed to fetch data. ResponseCode : " + responseCode);
        }
    }
}