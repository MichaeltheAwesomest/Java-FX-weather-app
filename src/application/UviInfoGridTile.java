package application;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class UviInfoGridTile extends VBox {
	public UviInfoGridTile() {
		super(5);
		
		double uvi = 0;
		String uviFilePath = getClass().getResource("icons/uv.gif").toExternalForm();
		Image uviImage = new Image(uviFilePath, 40, 40, true, true);
		ImageView uviImageView = new ImageView(uviImage);

		Label uviValueLabel = new Label(uvi + "");
		uviValueLabel.setMaxWidth(Double.POSITIVE_INFINITY);
		uviValueLabel.setAlignment(Pos.CENTER);

		Label uviLabel = new Label("UV");
		uviLabel.setMaxWidth(Double.POSITIVE_INFINITY);
		uviLabel.setAlignment(Pos.CENTER);

		getChildren().addAll(uviImageView, uviValueLabel, uviLabel);
		setAlignment(Pos.CENTER);
	}
}
