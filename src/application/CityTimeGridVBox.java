package application;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import open_weather_response.WeatherResponse;

public class CityTimeGridVBox extends VBox {
	private volatile boolean running = true;
	
	public CityTimeGridVBox(WeatherResponse weatherResponse, ZoneOffset cityZoneOffset) {
		String cityName = weatherResponse.city.name;
		Label cityNameLabel = new Label(cityName);
		cityNameLabel.setTextAlignment(TextAlignment.CENTER);

		Label hourMinuteLabel = new Label();
		hourMinuteLabel.getStyleClass().add("bigLabel");
		hourMinuteLabel.setTextAlignment(TextAlignment.CENTER);

		Label monthDayLabel = new Label();
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
		
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm:ss");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, d MMMM");

        Thread.ofVirtual().start(() -> {
            while (running) {
                ZonedDateTime liveCityTime = Instant.now().atZone(cityZoneOffset);
                
                String timeString = timeFormatter.format(liveCityTime);
                String dateString = dateFormatter.format(liveCityTime);

                Platform.runLater(() -> {
                    hourMinuteLabel.setText(timeString);
                    monthDayLabel.setText(dateString);
                });

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
	}
	
	public void stopClock() {
        this.running = false;
    }
}
