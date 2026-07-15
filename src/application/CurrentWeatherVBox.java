package application;

import java.net.URL;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import open_weather_response.Weather;

public class CurrentWeatherVBox extends VBox {
	public CurrentWeatherVBox(Weather weather) {
		super();
		
		String weatherName = weather.description;
		Label weatherLabel = new Label(weatherName);
		weatherLabel.setAlignment(Pos.CENTER);
		weatherLabel.setMaxWidth(Double.MAX_VALUE);

		String weatherIconCode = weather.icon;
		String iconPath = "";
		URL localUrl = getClass().getResource("icons/" + weatherIconCode + ".gif");
		if (localUrl == null) {
			iconPath = "https://openweathermap.org/img/wn/" + weatherIconCode + "@2x.png";
		} else {
			iconPath = localUrl.toExternalForm();
		}
		Image weatherIcon = new Image(iconPath, 100, 100, true, true);
		ImageView iconView = new ImageView(weatherIcon);

		getChildren().addAll(iconView, weatherLabel);
		setAlignment(Pos.CENTER);
	}
}
