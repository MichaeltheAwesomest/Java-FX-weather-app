package open_weather_response;

public class OpenWeatherApiKey {
	public static String getValue() {
		String key = System.getenv("OPENWEATHER_API_KEY");
		if (key == null || key.isBlank()) {
			throw new IllegalStateException("Set the OPENWEATHER_API_KEY environment variable.");
		}
		return key.trim();
	}
}