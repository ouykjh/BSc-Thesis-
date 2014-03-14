package com.example.mapsdemo;

import java.text.DecimalFormat;
import java.util.Map.Entry;

import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public class DistanceCalculator {
	public static Double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius=6371;
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2-lat1);
        double dLon = Math.toRadians(lon2-lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
        Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult= Radius*c;
        double km=valueResult/1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec =  Integer.valueOf(newFormat.format(km));
        double meter=valueResult%1000;
        int  meterInDec= Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value",""+valueResult+"   KM  "+kmInDec+" Meter   "+meterInDec);

        return meter * 1000;
     }
	
	public static LatLng findPossibleLocation(GoogleMap map, Route route, int possibleDistance){
		LatLng myLocation = new LatLng(map.getMyLocation().getLatitude(), map.getMyLocation().getLongitude());
		for (Entry<String, LatLng> entry : route.getRoute().entrySet()){
			if(DistanceCalculator.CalculationByDistance(route.getPoint(entry.getKey()), myLocation) <= possibleDistance){
				return entry.getValue();
			}
		}
		return null;
	}
	
	public static int closetLocation(GoogleMap map, Route route){
		LatLng myLocation = new LatLng(map.getMyLocation().getLatitude(), map.getMyLocation().getLongitude());
		double min = 0.0;
		Double minNext = Double.MAX_VALUE;
		for (Entry<String, LatLng> entry : route.getRoute().entrySet()){
				min = DistanceCalculator.CalculationByDistance(route.getPoint(entry.getKey()), myLocation);
				if(min < minNext) minNext = min;
			}
		return minNext.intValue() ;
	}
}
