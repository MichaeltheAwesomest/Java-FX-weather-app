package application;

import java.time.LocalDateTime;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import open_weather_response.Main;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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
			WeatherResponse weatherResponse = appIpClient.getWeatherResponse(ipInformation);
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

		SearchBarVBox searchBar = new SearchBarVBox();

		HBox.setHgrow(searchBar, Priority.ALWAYS);

		HBox header = new HBox(30, darkModeToggle, searchBar, weatherAtLocationButton);

		GridPane gridPane = new GridPane(20, 20);

		String cityName = weatherResponse.city.name;

		CityTimeGridVBox cityTimeGridTile = new CityTimeGridVBox(cityName);

		Main currentMainWeather = weatherResponse.list.get(0).main;
		double currentTemperature = currentMainWeather.temp;
		Label currentTemperatureLabel = new Label(currentTemperature + "°C");
		currentTemperatureLabel.setId("bigTemperatureLabel");
		currentTemperatureLabel.setMaxWidth(Double.MAX_VALUE);
		currentTemperatureLabel.setAlignment(Pos.CENTER);

		double feelsLikeTemp = currentMainWeather.feels_like;
		Label feelsLikeTempLabel = new Label("Feels like: " + feelsLikeTemp + "°C");
		feelsLikeTempLabel.setMaxWidth(Double.MAX_VALUE);
		feelsLikeTempLabel.setAlignment(Pos.CENTER);

		VBox temperatureVBox = new VBox(currentTemperatureLabel, feelsLikeTempLabel);

		ArrayList<WeatherInformation> weatherBroadCastList = weatherResponse.list;

		String weatherName = weatherBroadCastList.get(0).weather.get(0).description;
		Label weatherLabel = new Label(weatherName);
		weatherLabel.setAlignment(Pos.CENTER);
		weatherLabel.setMaxWidth(Double.MAX_VALUE);

		String currentWeatherIconCode = weatherBroadCastList.get(0).weather.get(0).icon;
		String currentWeatherImageUrl = "https://openweathermap.org/img/wn/" + currentWeatherIconCode + "@2x.png";
		Image currentWeatherIcon = new Image(currentWeatherImageUrl);
		ImageView currentWeatherIconView = new ImageView(currentWeatherIcon);

		VBox weatherVBox = new VBox(10, currentWeatherIconView, weatherLabel);

		HBox mainWeatherGridTile = new HBox(20, temperatureVBox, weatherVBox);
		mainWeatherGridTile.getStyleClass().add("cityTimeGridTile");
		mainWeatherGridTile.setPadding(new Insets(20));

		Label dayForeCastLabel = new Label("5 days forecast");
		dayForeCastLabel.setMaxWidth(Double.MAX_VALUE);
		dayForeCastLabel.setTextAlignment(TextAlignment.CENTER);
		
		VBox dayForeCastVBox = new VBox(10, dayForeCastLabel);
		System.out.println(weatherBroadCastList.size());

		for (int i = 7; i < weatherBroadCastList.size(); i += 8) {
			WeatherInformation weatherBroadCast = weatherBroadCastList.get(i);
			String weatherIconCode = weatherBroadCast.weather.get(0).icon;
			String weatherIconUrl = "https://openweathermap.org/img/wn/" + weatherIconCode + "@2x.png";
			Image weatherIcon = new Image(weatherIconUrl);
			ImageView weatherIconView = new ImageView(weatherIcon);
			double temp = weatherBroadCast.main.temp;
			Label temperatureLabel = new Label(temp + "°C");

			HBox broadCaseWeatherRow = new HBox(10, weatherIconView, temperatureLabel);
			dayForeCastVBox.getChildren().add(broadCaseWeatherRow);

		}

		gridPane.add(cityTimeGridTile, 0, 0);
		gridPane.add(mainWeatherGridTile, 1, 0);
		gridPane.setAlignment(Pos.CENTER);

		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(60, 20, 20, 20));
		borderPane.setCenter(gridPane);

		StackPane root = new StackPane(borderPane, header);

		Scene scene = new Scene(root, 400, 400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		TextField searchTextField = searchBar.getTextField();
		searchTextField.setOnAction(_ -> {
			String textValue = searchTextField.getText().toLowerCase();
			if (textValue.length() < 3) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Input Validation");
				alert.setHeaderText(null);
				alert.setContentText("Text must be at least 3 characters");
				alert.show();
				return;
			}
			weatherDataScene(null);
		});

		return scene;
	}
}
