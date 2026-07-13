package application;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AppWebClient {

    private final HttpClient httpClient = HttpClient.newHttpClient();

    public String getIpInformation() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://ipapi.co/json/"))
                .header("User-Agent", "Java-HttpClient") 
                .GET()
                .build();
        try {
            HttpResponse<String> response = httpClient.send(
                    request, 
                    HttpResponse.BodyHandlers.ofString()
            );

            return response.body();
        } catch (IOException | InterruptedException e) {
            System.err.println("Error fetching IP information: " + e.getMessage());
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            return null;
        }
    }
}