package com.example.mapsdemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class FormularActivity extends Activity {
	
	TextView address;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formular);
		
		address = (TextView) findViewById(R.id.addressOutcome);
		Bundle bundle = getIntent().getExtras();
		String addressSting = bundle.getString("bronowiceAddress");
		address.setText(addressSting);
	}

}
