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
import open_weather_response.WeatherInformation;

public class DailyForeCastGridTile extends VBox {
	private static final HashMap<String, Image> IMAGE_CACHE = new HashMap<>();
	
	public DailyForeCastGridTile(ArrayList<WeatherInformation> weatherBroadCastList, ZoneOffset cityZoneOffset) {
		Label daysForeCastLabel = new Label("5 days forecast");
		daysForeCastLabel.setMaxWidth(Double.MAX_VALUE);
		daysForeCastLabel.setTextAlignment(TextAlignment.CENTER);
		daysForeCastLabel.setAlignment(Pos.CENTER);
		daysForeCastLabel.getStyleClass().add("mediumLabel");
		
		super(10,daysForeCastLabel);
		setPadding(new Insets(10));
		getStyleClass().add("cityTimeGridTile");
		
		for (int i = 7; i < weatherBroadCastList.size(); i += 8) {
			WeatherInformation weatherBroadCast = weatherBroadCastList.get(i);
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
			weatherIconView.setFitHeight(30);
			weatherIconView.setFitWidth(30);
			double temp = weatherBroadCast.main.temp;
			Label temperatureLabel = new Label(temp + "°C");

			int absoluteTime = weatherBroadCast.dt;
			LocalDateTime broadCastTime = LocalDateTime.ofEpochSecond(absoluteTime, 0, cityZoneOffset);
			DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("EEEE, d MMMM");
			String weatherTimeString = timeFormatter.format(broadCastTime);
			Label weatherTimeLabel = new Label(weatherTimeString);

			HBox broadCaseWeatherRow = new HBox(10, weatherIconView, temperatureLabel, weatherTimeLabel);
			getChildren().add(broadCaseWeatherRow);
			setAlignment(Pos.CENTER);
		}
	}
}
