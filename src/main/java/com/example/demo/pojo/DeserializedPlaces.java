package com.example.demo.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeserializedPlaces {
	@JsonProperty("place name")
	private String placeName;
	private String longitude;
	private String latitude;
	
	public DeserializedPlaces() {}

	public DeserializedPlaces(String placeName, String longitude, String latitude) {
		super();
		this.placeName = placeName;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
}
