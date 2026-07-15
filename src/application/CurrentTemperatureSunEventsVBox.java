package application;

import java.time.ZoneOffset;

import javafx.scene.layout.VBox;
import open_weather_response.City;
import open_weather_response.Main;

public class CurrentTemperatureSunEventsVBox extends VBox {
	public CurrentTemperatureSunEventsVBox(City city, ZoneOffset cityZoneOffset, Main currentMainWeather) {
		TemperaturesVBox temperaturesVBox = new TemperaturesVBox(currentMainWeather);
		SunEventHBox sunRiseInfo = new SunEventHBox(city, SunEvent.sunRise, cityZoneOffset);
		SunEventHBox sunSetInfo = new SunEventHBox(city, SunEvent.sunSet, cityZoneOffset);
		VBox sunEventsVBox = new VBox(10, sunRiseInfo, sunSetInfo);

		super(20, temperaturesVBox, sunEventsVBox);
	}
}
