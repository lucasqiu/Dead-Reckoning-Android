package com.example.ece498rc_mp1;

//import java.text.DecimalFormat;

//import java.util.ArrayList;
//import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
//import android.content.Context;


//import com.csvreader.CsvWriter;
//package au.com.bytecode.opencsv;


public class MainActivity extends Activity {
	Button btn_test;
	Button btn_cnt;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.button_layout);
		btn_test = (Button) findViewById(R.id.button2);
		btn_cnt = (Button) findViewById(R.id.button1);
			
		btn_test.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View view) {
		    	Intent nextScreen = new Intent(getApplicationContext(),  SecondActivity.class);
                //passing parameters into SecondActivity:
		    	//nextScreen.putExtra("firstKeyName","FirstKeyValue");
		    	//in SecondActivity (obtaining parameters):
		    	//Intent nextScreen = getIntent();
		    	//String firstKeyName = nextScreen.getStringExtra("firstKeyName"); - returns "FirstKeyValue"
		    	//getIntExtra for ints, etc.
		    	startActivity(nextScreen);
		    }
		});
		
		btn_cnt.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View view) {
		    	Intent nextScreen = new Intent(getApplicationContext(),  SecondActivity.class);
                startActivity(nextScreen);
		    }
		});
	}	
}
