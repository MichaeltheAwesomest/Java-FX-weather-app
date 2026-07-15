package application;

import java.time.ZoneOffset;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import open_weather_response.Main;
import open_weather_response.WeatherInformation;
import open_weather_response.WeatherResponse;

public class CurrentWeatherGridTile extends HBox {
	public CurrentWeatherGridTile(WeatherResponse weatherResponse, ZoneOffset cityZoneOffset) {
		WeatherInformation weatherInformation = weatherResponse.list.get(0);
		Main currentMainWeather = weatherInformation.main;

		CurrentTemperatureSunEventsVBox temperatureSunEventsVBox = new CurrentTemperatureSunEventsVBox(weatherResponse.city, cityZoneOffset, currentMainWeather);
		CurrentWeatherVBox weatherVBox = new CurrentWeatherVBox(weatherInformation.weather.get(0));
		OtherWeatherDetailsGridPane otherWeatherInfoGrid = new OtherWeatherDetailsGridPane(currentMainWeather, weatherResponse);

		super(20,temperatureSunEventsVBox, weatherVBox, otherWeatherInfoGrid);
		getStyleClass().add("cityTimeGridTile");
		setPadding(new Insets(20));
		setAlignment(Pos.CENTER);
	}
}
