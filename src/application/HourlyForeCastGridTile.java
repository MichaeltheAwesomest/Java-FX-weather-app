package application;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import open_weather_response.WeatherInformation;

public class HourlyForeCastGridTile extends VBox {
	public HourlyForeCastGridTile(ArrayList<WeatherInformation> weatherBroadCastList, ZoneOffset cityZoneOffset) {
		Label dayForeCastLabel = new Label("Hourly forecast");
		dayForeCastLabel.setMaxWidth(Double.MAX_VALUE);
		dayForeCastLabel.setTextAlignment(TextAlignment.CENTER);
		dayForeCastLabel.setAlignment(Pos.CENTER);
		dayForeCastLabel.getStyleClass().add("mediumLabel");

		HBox dayForeCastHBox = new HBox(10);
		dayForeCastHBox.setPadding(new Insets(10));

		for (int i = 1; i < 8; i++) {
			WeatherInformation weatherBroadCast = weatherBroadCastList.get(i);

			int absoluteTime = weatherBroadCast.dt;
			LocalDateTime broadCastTime = LocalDateTime.ofEpochSecond(absoluteTime, 0, cityZoneOffset);
			DateTimeFormatter monthDayFormatter = DateTimeFormatter.ofPattern("H:mm");
			String weatherTimeString = monthDayFormatter.format(broadCastTime);
			Label weatherTimeLabel = new Label(weatherTimeString);
			weatherTimeLabel.setTextAlignment(TextAlignment.CENTER);
			weatherTimeLabel.setMaxWidth(Double.MAX_VALUE);
			weatherTimeLabel.setAlignment(Pos.CENTER);

			String weatherIconCode = weatherBroadCast.weather.get(0).icon;
			String weatherIconUrl = "https://openweathermap.org/img/wn/" + weatherIconCode + "@2x.png";
			Image weatherIcon = new Image(weatherIconUrl);
			ImageView weatherIconView = new ImageView(weatherIcon);
			weatherIconView.setFitHeight(30);
			weatherIconView.setFitWidth(30);

			double temp = weatherBroadCast.main.temp;
			Label temperatureLabel = new Label(temp + "°C");
			
			double windSpeed = weatherBroadCast.wind.speed;
			
			Label windSpeedLabel = new Label(windSpeed + "km/h");

			VBox broadCastWeatherColumn = new VBox(10, weatherTimeLabel, weatherIconView, temperatureLabel,windSpeedLabel);
			broadCastWeatherColumn.setId("hourlyBroadCastColumn");
			broadCastWeatherColumn.setAlignment(Pos.CENTER);
			
			dayForeCastHBox.getChildren().add(broadCastWeatherColumn);
		}
		super(20,dayForeCastLabel,dayForeCastHBox);
		setPadding(new Insets(20));
		getStyleClass().add("cityTimeGridTile");
	}
}
