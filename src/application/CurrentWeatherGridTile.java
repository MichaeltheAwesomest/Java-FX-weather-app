package application;

import java.net.URL;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import open_weather_response.Main;
import open_weather_response.Weather;
import open_weather_response.WeatherResponse;

public class CurrentWeatherGridTile extends HBox {
	public CurrentWeatherGridTile(WeatherResponse weatherResponse) {
		super(20);

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

		String weatherIconCode = currentWeather.icon;

		String iconPath = "";
		URL localUrl = getClass().getResource("icons/" + weatherIconCode + ".gif");
		if (localUrl == null) {
			iconPath = "https://openweathermap.org/img/wn/" + weatherIconCode + "@2x.png";
		} else {
			iconPath = localUrl.toExternalForm();
		}

		Image currentWeatherIcon = new Image(iconPath, 100, 100, true, true);
		ImageView iconView = new ImageView(currentWeatherIcon);

		VBox weatherVBox = new VBox(10, iconView, weatherLabel);
		weatherVBox.setAlignment(Pos.CENTER);

		int humidity = currentMainWeather.humidity;
		String humidityImageFilePath = getClass().getResource("icons/humidity.gif").toExternalForm();
		Image humidityImage = new Image(humidityImageFilePath, 40, 40, true, true);
		ImageView humidityImageView = new ImageView(humidityImage);

		Label humidityValueLabel = new Label(humidity + "%");
		humidityValueLabel.setMaxWidth(Double.POSITIVE_INFINITY);
		humidityValueLabel.setAlignment(Pos.CENTER);

		Label humidityLabel = new Label("Humidity");
		humidityLabel.setMaxWidth(Double.POSITIVE_INFINITY);
		humidityLabel.setAlignment(Pos.CENTER);

		VBox humidityGridTile = new VBox(5, humidityImageView, humidityValueLabel, humidityLabel);

		int pressure = currentMainWeather.pressure;
		String pressureImageFilePath = getClass().getResource("icons/pressure.png").toExternalForm();
		Image pressureImage = new Image(pressureImageFilePath, 40, 40, true, true);
		ImageView pressureImageView = new ImageView(pressureImage);

		Label pressureValueLabel = new Label(pressure + "hPa");
		pressureValueLabel.setMaxWidth(Double.POSITIVE_INFINITY);
		pressureValueLabel.setAlignment(Pos.CENTER);

		Label pressureLabel = new Label("Pressure");
		pressureLabel.setMaxWidth(Double.POSITIVE_INFINITY);
		pressureLabel.setAlignment(Pos.CENTER);

		VBox pressureGridTile = new VBox(5, pressureImageView, pressureValueLabel, pressureLabel);

		double windSpeed = weatherResponse.list.get(0).wind.speed;
		String windImageFilePath = getClass().getResource("icons/wind_speed.gif").toExternalForm();
		Image windImage = new Image(windImageFilePath, 40, 40, true, true);
		ImageView windSpeedImageView = new ImageView(windImage);

		Label windSpeedValueLabel = new Label(windSpeed + "km/h");
		windSpeedValueLabel.setMaxWidth(Double.POSITIVE_INFINITY);
		windSpeedValueLabel.setAlignment(Pos.CENTER);

		Label windSpeedLabel = new Label("Wind Speed");
		windSpeedLabel.setMaxWidth(Double.POSITIVE_INFINITY);
		windSpeedLabel.setAlignment(Pos.CENTER);

		VBox windSpeedGridTile = new VBox(5, windSpeedImageView, windSpeedValueLabel, windSpeedLabel);

		double uvi = 0;
		String uviFilePath = getClass().getResource("icons/uv.gif").toExternalForm();
		Image uviImage = new Image(uviFilePath, 40, 40, true, true);
		ImageView uviImageView = new ImageView(uviImage);

		Label uviValueLabel = new Label(uvi + "");
		uviValueLabel.setMaxWidth(Double.POSITIVE_INFINITY);
		uviValueLabel.setAlignment(Pos.CENTER);

		Label uviLabel = new Label("UV");
		uviLabel.setMaxWidth(Double.POSITIVE_INFINITY);
		uviLabel.setAlignment(Pos.CENTER);

		VBox uviGridTile = new VBox(5, uviImageView, uviValueLabel, uviLabel);

		GridPane otherWeatherInfoGrid = new GridPane(20, 10);
		otherWeatherInfoGrid.add(humidityGridTile, 0, 0);
		otherWeatherInfoGrid.add(pressureGridTile, 0, 1);
		otherWeatherInfoGrid.add(windSpeedGridTile, 1, 0);
		otherWeatherInfoGrid.add(uviGridTile, 1, 1);

		getChildren().addAll(temperatureVBox, weatherVBox, otherWeatherInfoGrid);
		getStyleClass().add("cityTimeGridTile");
		setPadding(new Insets(20));
		setAlignment(Pos.CENTER);
	}
}
