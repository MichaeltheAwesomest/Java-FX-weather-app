package application;

import javafx.scene.layout.GridPane;
import open_weather_response.Main;
import open_weather_response.WeatherResponse;

public class OtherWeatherDetailsGridPane extends GridPane {
	public OtherWeatherDetailsGridPane(Main currentMainWeather, WeatherResponse weatherResponse) {
		super(20, 10);
		
		HumidityInfoTileVBox humidityGridTile = new HumidityInfoTileVBox(currentMainWeather);
		PressureInfoTileVBox pressureGridTile = new PressureInfoTileVBox(currentMainWeather);
		WindSpeedInfoTileVBox windSpeedGridTile = new WindSpeedInfoTileVBox(weatherResponse.list.get(0), currentMainWeather);
		UviInfoGridTile uviGridTile = new UviInfoGridTile();
		add(humidityGridTile, 0, 0);
		add(pressureGridTile, 0, 1);
		add(windSpeedGridTile, 1, 0);
		add(uviGridTile, 1, 1);
	}
}
