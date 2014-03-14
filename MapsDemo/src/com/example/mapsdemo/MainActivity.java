package com.example.mapsdemo;

import java.util.Map.Entry;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends Activity {
	Context context;
	static Route route = new Route();
	Point bronowice = new Point("Bronowice - 1", 50.0844851, 19.88061085 );
	Point informatyka = new Point("Informatyka - 2", 50.0684387, 19.91253451);
	Point kazimierz = new Point("Kazimierz- 3", 50.04656206, 19.94311169);

	static final int zoom = 10;
	private static final int FORMULARZ = 1;
	private static final String MENU_BUTTON = "Formularz";
	private static final int possibleDistance = 300; //meters
	
	private GoogleMap map;
	
	public void addMarekrs(GoogleMap map, Route route){
		for (Entry<String, LatLng> entry : route.getRoute().entrySet()){
			map.addMarker(new MarkerOptions().position(entry.getValue()).title(entry.getKey()));
		}
	}
	public void addAllMarkers(GoogleMap map, Route route){
		route.addPoint(bronowice);
		route.addPoint(informatyka);
		route.addPoint(kazimierz);
		addMarekrs(map, route);
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = getApplicationContext();
		
		map = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
		addAllMarkers(map, route);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(informatyka.getLatLng(), zoom));
	    map.setMyLocationEnabled(true);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, FORMULARZ, 0, MENU_BUTTON);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(DistanceCalculator.findPossibleLocation(map, route, possibleDistance) == null){
			Toast.makeText(context, "You are too far from the target: " + 
									Double.toString(DistanceCalculator.closetLocation(map, route)) + " meters", Toast.LENGTH_LONG).show();
		}else{
			Intent intent = new Intent(context, FormularActivity.class);
			String bronowiceAddress = bronowice.getName();
			intent.putExtra("bronowiceAddress", bronowiceAddress);
		    startActivity(intent);
		}
		return true;
	}
}
