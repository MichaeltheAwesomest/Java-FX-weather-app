package application;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import open_weather_response.Main;
import open_weather_response.WeatherInformation;

public class WindSpeedInfoTileVBox extends VBox {
	public WindSpeedInfoTileVBox(WeatherInformation weatherInformation, Main currentMainWeather) {
		super(5);
		
		double windSpeed = weatherInformation.wind.speed;
		String windImageFilePath = getClass().getResource("icons/wind_speed.gif").toExternalForm();
		Image windImage = new Image(windImageFilePath, 40, 40, true, true);
		ImageView windSpeedImageView = new ImageView(windImage);

		Label windSpeedValueLabel = new Label(windSpeed + "km/h");
		windSpeedValueLabel.setMaxWidth(Double.POSITIVE_INFINITY);
		windSpeedValueLabel.setAlignment(Pos.CENTER);

		Label windSpeedLabel = new Label("Wind Speed");
		windSpeedLabel.setMaxWidth(Double.POSITIVE_INFINITY);
		windSpeedLabel.setAlignment(Pos.CENTER);

		getChildren().addAll(windSpeedImageView, windSpeedValueLabel, windSpeedLabel);
		setAlignment(Pos.CENTER);
	}
}
