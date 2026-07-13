package open_weather_response;

import java.util.ArrayList;

public class WeatherResponse {
	public String cod;
	public int message;
	public int cnt;
	public ArrayList<WeatherInformation> list;
	public City city;
}