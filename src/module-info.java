module JavaFxWeatherDashBoardApp {
	requires javafx.controls;
	requires java.net.http;
	requires com.google.gson;
	requires javafx.graphics;
	
	opens application to javafx.graphics, javafx.fxml,com.google.gson;
}
