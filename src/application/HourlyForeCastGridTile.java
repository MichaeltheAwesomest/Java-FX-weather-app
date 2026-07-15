package application;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import open_weather_response.WeatherInformation;

public class HourlyForeCastGridTile extends VBox {
	private static final HashMap<String, Image> IMAGE_CACHE = new HashMap<>();

	public HourlyForeCastGridTile(ArrayList<WeatherInformation> weatherBroadCastList, ZoneOffset cityZoneOffset) {
		super(20);
		Label dayForeCastLabel = new Label("Hourly forecast");
		dayForeCastLabel.setMaxWidth(Double.MAX_VALUE);
		dayForeCastLabel.setTextAlignment(TextAlignment.CENTER);
		dayForeCastLabel.setAlignment(Pos.CENTER);
		dayForeCastLabel.getStyleClass().add("mediumLabel");

		HBox dayForeCastHBox = new HBox(10);
		dayForeCastHBox.setPadding(new Insets(10));
		
		String windAngleFilePath = getClass().getResource("icons/arrow.gif").toExternalForm();
		Image windAngleImage = new Image(windAngleFilePath, 50, 50, true, true);

		for (int i = 0; i < 7; i++) {
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
			Image weatherIcon = IMAGE_CACHE.get(weatherIconCode);

			if (weatherIcon == null) {
				String iconPath;
				URL localUrl = getClass().getResource("icons/" + weatherIconCode + ".gif");
				if (localUrl == null) {
					iconPath = "https://openweathermap.org/img/wn/" + weatherIconCode + "@2x.png";
				} else {
					iconPath = localUrl.toExternalForm();
				}
				weatherIcon = new Image(iconPath, 60, 60, true, true);
				IMAGE_CACHE.put(weatherIconCode, weatherIcon);
			}

			ImageView weatherIconView = new ImageView(weatherIcon);

			double temp = weatherBroadCast.main.temp;
			Label temperatureLabel = new Label(temp + "°C");

			int windAngle = 180 + weatherBroadCast.wind.deg;
			ImageView windAngleImageView = new ImageView(windAngleImage);
			windAngleImageView.getTransforms().add(new Rotate(windAngle,25,25));

			double windSpeed = weatherBroadCast.wind.speed;
			Label windSpeedLabel = new Label(windSpeed + "km/h");

			VBox broadCastWeatherColumn = new VBox(20, weatherTimeLabel, weatherIconView, temperatureLabel,
					windAngleImageView, windSpeedLabel);
			broadCastWeatherColumn.setId("hourlyBroadCastColumn");
			broadCastWeatherColumn.setAlignment(Pos.CENTER);

			dayForeCastHBox.getChildren().add(broadCastWeatherColumn);
		}
		getChildren().addAll(dayForeCastLabel, dayForeCastHBox);
		setPadding(new Insets(20));
		getStyleClass().add("cityTimeGridTile");
	}
}
