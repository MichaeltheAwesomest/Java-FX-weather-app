package application;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import open_weather_response.OpenWeatherApiKey;
import open_weather_response.WeatherResponse;

public class AppApiClient {

	private final HttpClient httpClient = HttpClient.newHttpClient();

	public IpInformation getIpInformation() {
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://ipapi.co/json/"))
				.header("User-Agent", "Java-HttpClient").GET().build();
		try {
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
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

	public WeatherResponse getWeatherResponse(IpInformation ipInfo) {
		double lat = ipInfo.getLatitude();
		double lon = ipInfo.getLongitude();
		String apiKey = OpenWeatherApiKey.value;
		String url = "https://api.openweathermap.org/data/2.5/forecast?lat=" + lat + "&lon=" + lon + "&appid=" + apiKey
				+ "&units=metric";
		try {
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			Gson gson = new Gson();
			WeatherResponse weatherResponse = gson.fromJson(response.body(), WeatherResponse.class);
			return weatherResponse;
		} catch (IOException | InterruptedException e) {
			if (e instanceof InterruptedException) {
				Thread.currentThread().interrupt();
			}
			return null;
		}
	}
	
	public WeatherResponse getWeatherResponse(String cityName) throws JsonSyntaxException,NumberFormatException {
		String apiKey = OpenWeatherApiKey.value;
		String url ="https://api.openweathermap.org/data/2.5/forecast?q=" + cityName + "&appid=" + apiKey
				+ "&units=metric";
		try {
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			Gson gson = new Gson();
			WeatherResponse weatherResponse = gson.fromJson(response.body(), WeatherResponse.class);
			return weatherResponse;
		} catch (IOException | InterruptedException e) {
			if (e instanceof InterruptedException) {
				Thread.currentThread().interrupt();
			}
			return null;
		}
	}
}