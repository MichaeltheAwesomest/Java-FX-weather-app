package application;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import open_weather_response.Main;

public class TemperaturesVBox extends VBox {
	public TemperaturesVBox(Main currentMainWeather) {
		double currentTemperature = currentMainWeather.temp;
		Label currentTemperatureLabel = new Label(currentTemperature + "°C");
		currentTemperatureLabel.setId("bigTemperatureLabel");
		currentTemperatureLabel.setMaxWidth(Double.MAX_VALUE);
		currentTemperatureLabel.setAlignment(Pos.CENTER);

		double feelsLikeTemp = currentMainWeather.feels_like;
		Label feelsLikeTempLabel = new Label("Feels like: " + feelsLikeTemp + "°C");
		feelsLikeTempLabel.setMaxWidth(Double.MAX_VALUE);
		feelsLikeTempLabel.setAlignment(Pos.CENTER);
		
		super(currentTemperatureLabel, feelsLikeTempLabel);
	}
}
