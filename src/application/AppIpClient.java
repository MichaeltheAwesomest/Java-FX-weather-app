package application;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;

import open_weather_response.WeatherResponse;

public class AppIpClient {

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
    
    public String getWeatherResponse(IpInformation ipInfo) {
            if (ipInfo == null || ipInfo.getLatitude() == null || ipInfo.getLongitude() == null) {
                return "{\"error\": \"Missing latitude or longitude in IP information.\"}";
            }
            double lat = ipInfo.getLatitude();
            double lon = ipInfo.getLongitude();
            String url = "https://api.openweathermap.org/data/2.5/forecast?lat=" + lat 
                       + "&lon=" + lon 
                       + "&appid=" + apiKey 
                       + "&units=metric";
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .GET()
                        .build();
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                return response.body();
            } catch (IOException | InterruptedException e) {
                if (e instanceof InterruptedException) {
                    Thread.currentThread().interrupt();
                }
                return "{\"error\": \"Failed to retrieve weather data: " + e.getMessage() + "\"}";
            }
        }
    
    public WeatherResponse getWeatherResponse(HttpClient client, IpInformation ipInfo) {
    	return null;
    }
}