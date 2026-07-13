package open_weather_response;

import java.util.ArrayList;
import com.google.gson.annotations.SerializedName;

public class WeatherResponse {
	public String cod;
	public int message;
	public int cnt;
	public ArrayList<WeatherInfoList> list;
	public City city;
}

class Clouds {
	public int all;
}

class Coord {
	public double lat;
	public double lon;
}

class WeatherInfoList {
	public int dt;
	public Main main;
	public ArrayList<Weather> weather;
	public Clouds clouds;
	public Wind wind;
	public int visibility;
	public double pop;
	public Rain rain;
	public Sys sys;
	public String dt_txt;
}

class Main {
	public double temp;
	public double feels_like;
	public double temp_min;
	public double temp_max;
	public int pressure;
	public int sea_level;
	public int grnd_level;
	public int humidity;
	public double temp_kf;
	public double dew_point;
}

class Rain {
	@SerializedName("3h")
	public double _3h;
}

class Sys {
	public String pod;
}

class Weather {
	public int id;
	public String main;
	public String description;
	public String icon;
}

class Wind {
	public double speed;
	public int deg;
	public double gust;
}
