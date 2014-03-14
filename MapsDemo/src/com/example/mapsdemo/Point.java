package com.example.mapsdemo;

import com.google.android.gms.maps.model.LatLng;

public class Point {
	private String name;
	private LatLng latLng;
	
	public Point(String name, Double lat, Double lng){
		this.name = name;
		this.latLng = new LatLng(lat, lng);
	}
	
	public String getName(){
		return name;
	}
	
	public LatLng getLatLng(){
		return latLng;
	}
}
