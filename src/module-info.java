module JavaFxWeatherDashBoardApp {
	requires javafx.controls;
	requires java.net.http;
	requires com.google.gson;
	
	opens application to javafx.graphics, javafx.fxml,com.google.gson;
}
