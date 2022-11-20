package com.example.demo.pojo;

import java.util.List;

public class DeserializedPostCode {
	
	private List<DeserializedPlaces> places;

	public DeserializedPostCode() {}
	
	public List<DeserializedPlaces> getPlaces() {
		return places;
	}

	public void setPlaces(List<DeserializedPlaces> places) {
		this.places = places;
	}
	
}
