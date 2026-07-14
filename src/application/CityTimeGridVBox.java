package application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import open_weather_response.WeatherResponse;

public class CityTimeGridVBox extends VBox {
	public CityTimeGridVBox(WeatherResponse weatherResponse) {
		String cityName = weatherResponse.city.name;
		Label cityNameLabel = new Label(cityName);
		cityNameLabel.setTextAlignment(TextAlignment.CENTER);

		LocalDateTime currentTime = LocalDateTime.now();

		DateTimeFormatter hourMinuteFormatter = DateTimeFormatter.ofPattern("H:mm");
		String hourMinuteString = hourMinuteFormatter.format(currentTime);
		Label hourMinuteLabel = new Label(hourMinuteString);
		hourMinuteLabel.getStyleClass().add("bigLabel");
		hourMinuteLabel.setTextAlignment(TextAlignment.CENTER);

		DateTimeFormatter monthDayFormatter = DateTimeFormatter.ofPattern("EEEE, d MMMM");
		String monthDayString = monthDayFormatter.format(currentTime);
		Label monthDayLabel = new Label(monthDayString);
		monthDayLabel.setTextAlignment(TextAlignment.CENTER);

		Region vSpacer = new Region();
		vSpacer.setPrefHeight(40);
		
		cityNameLabel.setMaxWidth(Double.MAX_VALUE);
		cityNameLabel.setAlignment(Pos.CENTER);

		hourMinuteLabel.setMaxWidth(Double.MAX_VALUE);
		hourMinuteLabel.setAlignment(Pos.CENTER);

		monthDayLabel.setMaxWidth(Double.MAX_VALUE);
		monthDayLabel.setAlignment(Pos.CENTER);
		
		super(10, cityNameLabel, hourMinuteLabel, vSpacer, monthDayLabel);
		setPadding(new Insets(20));
		getStyleClass().add("cityTimeGridTile");
	}
}
