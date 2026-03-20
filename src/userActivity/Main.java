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

        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        if(responseCode == 200) {
            System.out.println("Github-User-Activity of " + userName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            reader.close();

            String json =  builder.toString();
            String[] events = json.split("},\\{");

            int count = 0;

            for(String event : events) {
                if(count == 10) break;
                count++;
                int startIndex = event.indexOf("\"name\":\"");
                if (startIndex == -1) continue;

                int start = startIndex + 8;
                int end = event.indexOf("\"", start);
                if (end == -1) continue;

                String repoName = event.substring(start, end);

                if(event.contains("PushEvent")) {
                    System.out.println("- Pushed to " + repoName);
                } else if(event.contains("CreateEvent")) {
                    System.out.println("- Created repository " + repoName);
                } else if(event.contains("WatchEvent")) {
                    System.out.println("- Starred " + repoName);
                } else if(event.contains("DeleteEvent")) {
                    System.out.println("- Deleted repository " + repoName);
                } else {
                    int typeStart = event.indexOf("\"type\":\"") + 8;
                    int typeEnd = event.indexOf("\"", typeStart);
                    String type = event.substring(typeStart, typeEnd);

                    System.out.println("- " + type.replace("Event", "") + " on " + repoName);
                }
            }
        } else if (responseCode == 404) {
                System.out.println("User not found : " +  userName);
        }
    }
}