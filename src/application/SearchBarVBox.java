package application;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class SearchBarVBox extends VBox {
	private TextField searchTextField = new TextField();

	public SearchBarVBox() {
		searchTextField.setPromptText("Search for a city...");
		HBox.setHgrow(searchTextField, Priority.ALWAYS);
		setSpacing(10);
		
		searchTextField.getStyleClass().add("search-field");
		
		getChildren().addAll(searchTextField);
		getStyleClass().add("search-container");
		HBox.setHgrow(this, Priority.ALWAYS);
	}

	public TextField getTextField() {
		return searchTextField;
	}
}
