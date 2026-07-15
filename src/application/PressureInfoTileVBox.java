package application;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import open_weather_response.Main;

public class PressureInfoTileVBox extends VBox {
	public PressureInfoTileVBox(Main currentMainWeather) {
		super(5);
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

		getChildren().addAll(pressureImageView, pressureValueLabel, pressureLabel);
		setAlignment(Pos.CENTER);
	}
}
