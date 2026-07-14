package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import open_weather_response.Main;
import open_weather_response.Weather;
import open_weather_response.WeatherResponse;

public class CurrentWeatherGridTile extends HBox {
	public CurrentWeatherGridTile(WeatherResponse weatherResponse) {
		Main currentMainWeather = weatherResponse.list.get(0).main;
		double currentTemperature = currentMainWeather.temp;
		Label currentTemperatureLabel = new Label(currentTemperature + "°C");
		currentTemperatureLabel.setId("bigTemperatureLabel");
		currentTemperatureLabel.setMaxWidth(Double.MAX_VALUE);
		currentTemperatureLabel.setAlignment(Pos.CENTER);

		double feelsLikeTemp = currentMainWeather.feels_like;
		Label feelsLikeTempLabel = new Label("Feels like: " + feelsLikeTemp + "°C");
		feelsLikeTempLabel.setMaxWidth(Double.MAX_VALUE);
		feelsLikeTempLabel.setAlignment(Pos.CENTER);

		VBox temperatureVBox = new VBox(currentTemperatureLabel, feelsLikeTempLabel);
		
		Weather currentWeather = weatherResponse.list.get(0).weather.get(0);

		String weatherName = currentWeather.description;
		Label weatherLabel = new Label(weatherName);
		weatherLabel.setAlignment(Pos.CENTER);
		weatherLabel.setMaxWidth(Double.MAX_VALUE);

		String currentWeatherIconCode = currentWeather.icon;
		String currentWeatherImageUrl = "https://openweathermap.org/img/wn/" + currentWeatherIconCode + "@2x.png";
		Image currentWeatherIcon = new Image(currentWeatherImageUrl);
		ImageView currentWeatherIconView = new ImageView(currentWeatherIcon);

		VBox weatherVBox = new VBox(10, currentWeatherIconView, weatherLabel);

		super(20, temperatureVBox, weatherVBox);
		getStyleClass().add("cityTimeGridTile");
		setPadding(new Insets(20));
	}
}
