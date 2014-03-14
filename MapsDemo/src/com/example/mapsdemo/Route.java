package com.example.mapsdemo;

import java.util.HashMap;

import com.google.android.gms.maps.model.LatLng;

public class Route {
	private HashMap<String, LatLng> _route;
	
	public Route(){
		_route = new HashMap<String, LatLng>();
	}
	
	public void addPoint(Point point){
		_route.put(point.getName(), point.getLatLng());
	}
	
	public HashMap<String, LatLng> getRoute(){
		return _route;
	}
	
	public LatLng getPoint(String name){
		return _route.get(name);
	}
	
}
