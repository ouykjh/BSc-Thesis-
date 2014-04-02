package com.example.mapsdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.db.Address;
import com.example.db.DatabaseHelper;
import com.example.db.WasteAmount;
import com.google.android.gms.internal.ad;

public class DatabaseViewActivity extends Activity {
	
	EditText fdAddress;
	RatingBar rbWasteAmount;
	Button btnSendFormular;
	
	private DatabaseHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formular);
		initUIElements();
		initOnClickListeners();
	}
	
	private void initUIElements(){
		fdAddress = (EditText) findViewById(R.id.fdAddress);
		rbWasteAmount = (RatingBar) findViewById(R.id.rbWasteAmount);
		btnSendFormular = (Button) findViewById(R.id.btnSend);
		
		Bundle bundle = getIntent().getExtras();
		String addressString = bundle.getString("bronowiceAddress");
		fdAddress.setText(addressString);
	}

	private void addToDatabase(Address address, WasteAmount wasteAmount){
		long waId;
		
		dbHelper = new DatabaseHelper(getApplicationContext());
		dbHelper.open();
		waId = dbHelper.insertWasteAmount(wasteAmount);
		address.setWasteId(waId);
		dbHelper.insertAddress(address);
		
		Log.d("LOG", "dodano " + address + " i " + wasteAmount);
	}
	
	private void initOnClickListeners (){
		OnClickListener onClickListener = new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(v.getId() == R.id.btnSend)
					sendFormular();
			}	
		};
		btnSendFormular.setOnClickListener(onClickListener);
	}
	
	private void sendFormular(){
		Address address = new Address();
		WasteAmount wasteAmount= new WasteAmount();
		
		wasteAmount.setValue(rbWasteAmount.getNumStars());
/*		address.setStreet(street);
		address.setCity(city);
		address.setCountry(country);
*/	
		addToDatabase(address, wasteAmount);
		}
	
	private void getFromDatabase(){
		
	}
}
