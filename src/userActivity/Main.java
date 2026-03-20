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
        System.out.println("github-user-activity of " + userName);

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

            String json =  builder.toString();
            String[] events = json.split("},\\{");

            for(String event : events) {

                int start = event.indexOf("\"name\":\"") + 8;
                if(start <= 0) continue;
                int end = event.indexOf("\"", start);
                if(end <= 0) continue;
                String repoName = event.substring(start, end);

                if(event.contains("PushEvent")) {
                    System.out.println("- Pushed to " + repoName);
                } else if(event.contains("CreateEvent")) {
                    System.out.println("- Created repository " + repoName);
                } else if(event.contains("WatchEvent")) {
                    System.out.println("- Starred " + repoName);
                } else {
                    System.out.println("no matching event");
                }
            }

        } else if (responseCode == 404) {
                System.out.println("User not found");
        }
    }
}