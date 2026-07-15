package application;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import open_weather_response.Main;

public class HumidityInfoTileVBox extends VBox {
	public HumidityInfoTileVBox(Main currentMainWeather) {
		super(5);
		
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

		getChildren().addAll(humidityImageView, humidityValueLabel, humidityLabel);
		setAlignment(Pos.CENTER);
	}
}
