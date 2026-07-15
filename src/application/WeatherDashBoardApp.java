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
import javafx.scene.image.Image;
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
	Scene primaryScene = new Scene(new BorderPane());;
	boolean isDarkMode = true;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryScene.getStylesheets().add(getClass().getResource("application_dark.css").toExternalForm());
		String iconFilePath = getClass().getResource("icons/icon.png").toExternalForm();
		Image iconImage = new Image(iconFilePath, 40, 40, true, true);
		primaryStage.getIcons().add(iconImage);
		primaryStage.setScene(primaryScene);
		primaryStage.setTitle("Weather Dash Board App.");
		primaryStage.setFullScreen(true);
		primaryStage.show();

		primaryScene.setOnKeyPressed(event -> {
			if (event.getCode() == javafx.scene.input.KeyCode.F) {
				boolean isFullScreen = primaryStage.isFullScreen();
				primaryStage.setFullScreen(!isFullScreen);
			}
		});

		loadLocalshowWeatherData();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void loadLocalshowWeatherData() {
		ProgressIndicator loadingSpinner = new ProgressIndicator(ProgressIndicator.INDETERMINATE_PROGRESS);
		loadingSpinner.setMaxSize(60, 60);
		Label taskLabel = new Label("Getting Ip information.");
		taskLabel.setTextAlignment(TextAlignment.CENTER);
		taskLabel.getStyleClass().add("mediumLabel");

		VBox content = new VBox(10, loadingSpinner, taskLabel);
		content.setAlignment(Pos.CENTER);

		BorderPane root = new BorderPane();
		root.setCenter(content);
		primaryStage.getScene().setRoot(root);

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
				showWeatherData(weatherResponse);
			});
		});
	}

	public void loadCityWeatherData(String cityName) {
		ProgressIndicator loadingSpinner = new ProgressIndicator(ProgressIndicator.INDETERMINATE_PROGRESS);
		loadingSpinner.setMaxSize(60, 60);
		String formattedCityName = Character.toUpperCase(cityName.charAt(0)) + cityName.substring(1);
		Label taskLabel = new Label("Getting " + formattedCityName + "'s weather Information.");
		taskLabel.setTextAlignment(TextAlignment.CENTER);
		taskLabel.getStyleClass().add("mediumLabel");

		VBox content = new VBox(10, loadingSpinner, taskLabel);
		content.setAlignment(Pos.CENTER);

		BorderPane root = new BorderPane();
		root.setCenter(content);

		Thread.ofVirtual().start(() -> {
			try {
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
					showWeatherData(weatherResponse);
				});
			} catch (NumberFormatException | com.google.gson.JsonSyntaxException e) {
				Platform.runLater(() -> {
					taskLabel.setText("Invalid City");
				});
				try {
					Thread.sleep(2000);
				} catch (InterruptedException f) {
					f.printStackTrace();
				}
				Platform.runLater(() -> {
					taskLabel.setText("Will proceed to load local weather data");
				});
				try {
					Thread.sleep(3000);
				} catch (InterruptedException f) {
					f.printStackTrace();
				}
				Platform.runLater(() -> {
					loadLocalshowWeatherData();
				});
			}
		});

		primaryStage.getScene().setRoot(root);
	}

	public void showWeatherData(WeatherResponse weatherResponse) {
		ToggleButton themeModeToggle = new ToggleButton("toggle mode");
		themeModeToggle.getStyleClass().add("toggle-button");

		Button weatherAtLocationButton = new Button("Current Location");
		weatherAtLocationButton.getStyleClass().add("location-button");

		SearchBarVBox searchBar = new SearchBarVBox();
		searchBar.setMaxHeight(40);

		HBox header = new HBox(20, themeModeToggle, searchBar, weatherAtLocationButton);
		header.setAlignment(Pos.TOP_CENTER);

		
		int cityTimeZoneOffsetInSeconds = weatherResponse.city.timezone;
		ZoneOffset cityZoneOffset = ZoneOffset.ofTotalSeconds(cityTimeZoneOffsetInSeconds);
		ArrayList<WeatherInformation> weatherBroadCastList = weatherResponse.list;

		CityTimeGridVBox cityTimeGridTile = new CityTimeGridVBox(weatherResponse, cityZoneOffset);
		CurrentWeatherGridTile currentWeatherGridTile = new CurrentWeatherGridTile(weatherResponse,cityZoneOffset);
		DailyForeCastGridTile dailyForeCastGridTile = new DailyForeCastGridTile(weatherBroadCastList, cityZoneOffset);
		HourlyForeCastGridTile hourlyForeCastGridTile = new HourlyForeCastGridTile(weatherBroadCastList,
				cityZoneOffset);
		
		GridPane gridPane = new GridPane(20, 20);
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

		TextField searchTextField = searchBar.getTextField();

		root.setOnMouseClicked(_ -> {
			if (!searchBar.getStyleClass().contains("focused-within")) {
				root.requestFocus();
			}
		});

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
			loadCityWeatherData(textValue);
		});

		themeModeToggle.setOnAction(_ -> toggleThemeWithTransition(primaryScene, root));

		weatherAtLocationButton.setOnAction(_ -> {
			cityTimeGridTile.stopClock();
			loadLocalshowWeatherData();
		});

		primaryStage.getScene().setRoot(root);
	}

	private void toggleThemeWithTransition(Scene scene, StackPane rootNode) {
		isDarkMode = !isDarkMode;
		scene.getStylesheets().clear();
		if (isDarkMode) {
			scene.getStylesheets().add(getClass().getResource("application_dark.css").toExternalForm());
		} else {
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		}
	}
}
