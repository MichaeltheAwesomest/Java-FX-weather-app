package application;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import open_weather_response.City;

public class SunEventHBox extends HBox  {
	public SunEventHBox(City city,SunEvent sunEvent, ZoneOffset cityZoneOffset) {
		super(5);
		
		String imageFilePath = "";
		String eventLabel = "";
		int sunEventSecond = 0;
		if(sunEvent == SunEvent.sunRise) {
			imageFilePath = "icons/sun_rise.gif";
			eventLabel = "Sunrise";
			sunEventSecond = city.sunrise;
		}else {
			imageFilePath = "icons/sun_set.gif";
			eventLabel = "Sunset";
			sunEventSecond = city.sunset;
		}
		
		String sunEventImageUrl = getClass().getResource(imageFilePath).toExternalForm();
		Image sunEventImage = new Image(sunEventImageUrl, 30, 30, true, true);
		ImageView sunEventImageView = new ImageView(sunEventImage);

		Label sunEventLabel = new Label(eventLabel);
		
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

		ZonedDateTime sunEventTime = Instant.ofEpochSecond(sunEventSecond).atZone(cityZoneOffset);
		String sunEventTimeString = timeFormatter.format(sunEventTime);
		Label sunEventTimeLabel = new Label(sunEventTimeString);
		

		VBox sunEventTextInfo = new VBox(5, sunEventLabel, sunEventTimeLabel);
		getChildren().addAll(sunEventImageView, sunEventTextInfo);
	}
}
