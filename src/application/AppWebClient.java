package application;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;

public class AppWebClient {

    private final HttpClient httpClient = HttpClient.newHttpClient();

    public IpInformation getIpInformation() {
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
            Gson gson = new Gson();
            IpInformation ipInformation = gson.fromJson(response.body(), IpInformation.class);
            return ipInformation;
        } catch (IOException | InterruptedException e) {
            System.err.println("Error fetching IP information: " + e.getMessage());
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            return null;
        }
    }
}