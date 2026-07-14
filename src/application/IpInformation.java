package application;

public class IpInformation {
	String ip;
	String network;
	String version;
	String city;
	String region;
	String region_code;
	String country;
	String country_name;
	String country_code;
	String country_code_iso3;
	String country_capital;
	String country_tld;
	String continent_code;
	boolean in_eu;
	Object postal;
	Double latitude;
	Double longitude;
	String timezone;
	String utc_offset;
	String country_calling_code;
	String currency_name;
	String languages;
	Double country_area;
	Long country_population;
	String asn;
	String org;
	public IpInformation() {
		
	}
	
	double getLatitude() {
		return latitude;
	}
	
	double getLongitude() {
		return longitude;
	}
}
