package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import open_weather_response.WeatherResponse;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;


public class Main extends Application {
	AppIpClient appIpClient = new AppIpClient();
	WeatherResponse weatherResponse = null;
	@Override
	public void start(Stage primaryStage) {
			Scene loadLocalWeatherDataScene = loadLocalWeatherDataScene();
			primaryStage.setScene(loadLocalWeatherDataScene);
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
		Scene scene = new Scene(root,400,400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		
		Thread localWeatherInfoThread = new Thread(()->{
			IpInformation ipInformation = appIpClient.getIpInformation();
			Platform.runLater(()->{
				taskLabel.setText("Got Ip information.\nGetting weather Information.");
			});
			weatherResponse = appIpClient.getWeatherResponse(null,ipInformation);
			Platform.runLater(()->{
				taskLabel.setText("Done");
			});
		});
		
		localWeatherInfoThread.start();
		return scene;
	}
		
}
