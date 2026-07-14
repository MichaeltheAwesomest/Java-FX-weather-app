package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class SearchBarVBox extends VBox {
	private TextField searchTextField = new TextField();;

	public SearchBarVBox() {
		searchTextField.setPromptText("Search for a city...");
		HBox.setHgrow(searchTextField, Priority.ALWAYS);

		ObservableList<String> masterData = FXCollections.observableArrayList("Apple", "Banana", "Cherry", "Date",
				"Elderberry", "Fig", "Grape");

		FilteredList<String> filteredData = new FilteredList<>(masterData, _ -> false);

		final ListView<String> listView = new ListView<>();

		listView.visibleProperty().addListener((_, _, newVal) -> {
			listView.setManaged(newVal);
		});

		listView.setVisible(false);
		listView.setManaged(false);

		searchTextField.textProperty().addListener((_, _, newValue) -> {
			filteredData.setPredicate(item -> {
				if (newValue.length() < 3) {
					listView.setVisible(false);
					return false;
				}

				String lowerCaseFilter = newValue.toLowerCase();
				if (item.toLowerCase().contains(lowerCaseFilter)) {
					listView.setVisible(true);
					return true;
				} else {
					listView.setVisible(false);
				}
				return false;
			});
		});
		listView.setItems(filteredData);
		setSpacing(10);
		
		searchTextField.getStyleClass().add("search-field");
		
		getChildren().addAll(searchTextField, listView);
		getStyleClass().add("search-container");
		HBox.setHgrow(this, Priority.ALWAYS);
	}

	public TextField getTextField() {
		return searchTextField;
	}
}
