package application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import open_weather_response.WeatherResponse;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class Main extends Application {
	AppIpClient appIpClient = new AppIpClient();
	Stage primaryStage;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		Scene loadLocalWeatherDataScene = loadLocalWeatherDataScene();
		primaryStage.setScene(loadLocalWeatherDataScene);
		primaryStage.setTitle("Weather Dash Board App.");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public Scene loadLocalWeatherDataScene() {
		ProgressIndicator loadingSpinner = new ProgressIndicator(ProgressIndicator.INDETERMINATE_PROGRESS);
		loadingSpinner.setMaxSize(60, 60);
		Label taskLabel = new Label("Getting Ip information.");
		taskLabel.setTextAlignment(TextAlignment.CENTER);

		VBox content = new VBox(10, loadingSpinner, taskLabel);
		content.setAlignment(Pos.CENTER);

		BorderPane root = new BorderPane();
		root.setCenter(content);
		Scene scene = new Scene(root, 400, 400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		Thread localWeatherInfoThread = new Thread(() -> {
			IpInformation ipInformation = appIpClient.getIpInformation();
			Platform.runLater(() -> {
				taskLabel.setText("Got Ip information.\nGetting weather Information.");
			});
			WeatherResponse weatherResponse = appIpClient.getWeatherResponse(null, ipInformation);
			Platform.runLater(() -> {
				taskLabel.setText("Done");
				primaryStage.setScene(weatherDataScene(weatherResponse));
			});
		});

		localWeatherInfoThread.start();
		return scene;
	}

	public Scene weatherDataScene(WeatherResponse weatherResponse) {
		ToggleButton darkModeToggle = new ToggleButton("toggle mode");

		Button weatherAtLocationButton = new Button("Current Location");

		VBox searchBar = new SearchBarVBox();

		HBox.setHgrow(searchBar, Priority.ALWAYS);

		HBox header = new HBox(30, darkModeToggle, searchBar, weatherAtLocationButton);

		GridPane gridPane = new GridPane(20, 20);

		String cityName = weatherResponse.city.name;
		Label cityNameLabel = new Label(cityName);

		LocalDateTime currentTime = LocalDateTime.now();

		DateTimeFormatter hourMinuteFormatter = DateTimeFormatter.ofPattern("H:mm");
		String hourMinuteString = hourMinuteFormatter.format(currentTime);
		Label hourMinuteLabel = new Label(hourMinuteString);

		DateTimeFormatter monthDayFormatter = DateTimeFormatter.ofPattern("EEEEd',' MMMM");
		String monthDayString = monthDayFormatter.format(currentTime);
		Label monthDayLabel = new Label(monthDayString);

		Region vSpacer = new Region();
		vSpacer.setPrefHeight(40);

		VBox cityTimeGrid = new VBox(10, cityNameLabel, hourMinuteLabel, vSpacer, monthDayLabel);
		
		gridPane.add(cityTimeGrid, 0, 0);

		BorderPane root = new BorderPane();
		root.setPadding(new Insets(20));
		root.setTop(header);
		root.setCenter(gridPane);
		Scene scene = new Scene(root, 400, 400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		return scene;
	}
}
