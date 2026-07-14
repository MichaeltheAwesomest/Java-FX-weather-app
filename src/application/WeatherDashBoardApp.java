package application;

import java.time.ZoneOffset;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import open_weather_response.WeatherInformation;
import open_weather_response.WeatherResponse;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class WeatherDashBoardApp extends Application {
	AppApiClient appIpClient = new AppApiClient();
	Stage primaryStage;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		Scene loadLocalWeatherDataScene = loadLocalWeatherDataScene();
		primaryStage.setScene(loadLocalWeatherDataScene);
		primaryStage.setTitle("Weather Dash Board App.");
		primaryStage.setFullScreen(true);
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

		Thread.ofVirtual().start(() -> {
			IpInformation ipInformation = appIpClient.getIpInformation();
			if (ipInformation == null) {
				Platform.runLater(() -> {
					taskLabel.setText(
							"Couldn't get your IP adress information.\nPerhaps you have internet connection isussues");
				});
				return;
			}
			Platform.runLater(() -> {
				taskLabel.setText("Got Ip Adress information.\nGetting weather Information at location.");
			});
			WeatherResponse weatherResponse = appIpClient.getWeatherResponse(ipInformation);
			Platform.runLater(() -> {
				loadingSpinner.setProgress(1);
				taskLabel.setText("Building Layout...");
			});

			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			Platform.runLater(() -> {
				primaryStage.setScene(weatherDataScene(weatherResponse));
				primaryStage.setFullScreen(true);
			});
		});
		return scene;
	}

	public Scene loadCityWeatherDataScene(String cityName) {
		ProgressIndicator loadingSpinner = new ProgressIndicator(ProgressIndicator.INDETERMINATE_PROGRESS);
		loadingSpinner.setMaxSize(60, 60);
		String formattedCityName = Character.toUpperCase(cityName.charAt(0)) + cityName.substring(1);
		Label taskLabel = new Label("Getting " + formattedCityName + "'s weather Information.");
		taskLabel.setTextAlignment(TextAlignment.CENTER);

		VBox content = new VBox(10, loadingSpinner, taskLabel);
		content.setAlignment(Pos.CENTER);

		BorderPane root = new BorderPane();
		root.setCenter(content);
		Scene scene = new Scene(root, 400, 400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		Thread.ofVirtual().start(() -> {
			WeatherResponse weatherResponse = appIpClient.getWeatherResponse(cityName);
			Platform.runLater(() -> {
				loadingSpinner.setProgress(1);
				taskLabel.setText("Building Layout...");
			});

			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			Platform.runLater(() -> {
				primaryStage.setScene(weatherDataScene(weatherResponse));
				primaryStage.setFullScreen(true);
			});
		});
		return scene;
	}

	public Scene weatherDataScene(WeatherResponse weatherResponse) {
		ToggleButton darkModeToggle = new ToggleButton("toggle mode");
		darkModeToggle.getStyleClass().add("toggle-button");

		Button weatherAtLocationButton = new Button("Current Location");
		weatherAtLocationButton.getStyleClass().add("location-button");

		SearchBarVBox searchBar = new SearchBarVBox();
		searchBar.setMaxHeight(40);

		HBox header = new HBox(20, darkModeToggle, searchBar, weatherAtLocationButton);
		header.setAlignment(Pos.TOP_CENTER);
		
		GridPane gridPane = new GridPane(20, 20);

		int cityTimeZoneOffsetInSeconds = weatherResponse.city.timezone;
		ZoneOffset cityZoneOffset = ZoneOffset.ofTotalSeconds(cityTimeZoneOffsetInSeconds);

		CityTimeGridVBox cityTimeGridTile = new CityTimeGridVBox(weatherResponse, cityZoneOffset);
		CurrentWeatherGridTile currentWeatherGridTile = new CurrentWeatherGridTile(weatherResponse);

		ArrayList<WeatherInformation> weatherBroadCastList = weatherResponse.list;

		DailyForeCastGridTile dailyForeCastGridTile = new DailyForeCastGridTile(weatherBroadCastList, cityZoneOffset);
		HourlyForeCastGridTile hourlyForeCastGridTile = new HourlyForeCastGridTile(weatherBroadCastList,
				cityZoneOffset);

		gridPane.add(cityTimeGridTile, 0, 0);
		gridPane.add(currentWeatherGridTile, 1, 0);
		gridPane.add(dailyForeCastGridTile, 0, 1);
		gridPane.add(hourlyForeCastGridTile, 1, 1);
		gridPane.setAlignment(Pos.CENTER);

		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(60, 20, 20, 20));
		borderPane.setCenter(gridPane);

		StackPane root = new StackPane(borderPane, header);
		root.setPadding(new Insets(20));

		Scene scene = new Scene(root, 400, 400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		TextField searchTextField = searchBar.getTextField();
		searchTextField.setOnAction(_ -> {
			String textValue = searchTextField.getText().toLowerCase().trim();
			if (textValue.length() < 3) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Input Validation");
				alert.setHeaderText(null);
				alert.setContentText("Text must be at least 3 characters");
				alert.show();
				return;
			}
			cityTimeGridTile.stopClock();
			primaryStage.setScene(loadCityWeatherDataScene(textValue));
			primaryStage.setFullScreen(true);
		});
		
		weatherAtLocationButton.setOnAction(_ -> {
			cityTimeGridTile.stopClock();
			primaryStage.setScene(loadLocalWeatherDataScene());
		});
		
		root.setOnMouseClicked(_ -> {
	        if (!searchBar.getStyleClass().contains("focused-within")) {
	            root.requestFocus(); 
	        }
	    });

		return scene;
	}
}
