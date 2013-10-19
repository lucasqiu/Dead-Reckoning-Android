package com.example.ece498rc_mp1;

//import java.text.DecimalFormat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
//import android.content.Context;
import android.widget.TextView;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;

//import com.csvreader.CsvWriter;
//package au.com.bytecode.opencsv;


public class MainActivity extends Activity implements SensorEventListener {
	final String TAG = "MP1";
	SensorManager senseM = null;
	
	TextView Tview  = null;
	TextView AviewX = null;
	TextView AviewY = null;
	TextView AviewZ = null;
	TextView GviewX = null;
	TextView GviewY = null;
	TextView GviewZ = null;
	TextView MviewX = null;
	TextView MviewY = null;
	TextView MviewZ = null;
	TextView Lview  = null;
	TextView Cview = null;
	//List<String> GArray = new ArrayList<String>();
	//File f = new File(Environment.getExternalStorageDirectory().getPath(), "sensordata.csv");
	
	File f;
	long etime;
	int steps = 0;
	float thresholdp = (float)14;
	float thresholdn = (float)5;
	float[] history = new float[5];
	int[] doubleturn = new int[2];
	int next = 0;
	
//	CSV csv = CSV
//		    .separator(',')  // delimiter of fields
//		    .quote('"')      // quote character
//		    .create();       // new instance is immutable
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		senseM = (SensorManager) getSystemService(SENSOR_SERVICE); //reference to SensorManager
		setContentView(R.layout.activity_main);
		
		Tview  = (TextView) findViewById(R.id.T);
		
		Cview  = (TextView) findViewById(R.id.C);
		
		AviewX = (TextView) findViewById(R.id.xA); //references to the 10 textview widgets
		AviewY = (TextView) findViewById(R.id.yA);
		AviewZ = (TextView) findViewById(R.id.zA);
		GviewX = (TextView) findViewById(R.id.xG);
		GviewY = (TextView) findViewById(R.id.yG);
		GviewZ = (TextView) findViewById(R.id.zG);
		MviewX = (TextView) findViewById(R.id.xM);
		MviewY = (TextView) findViewById(R.id.yM);
		MviewZ = (TextView) findViewById(R.id.zM);
		Lview  = (TextView) findViewById(R.id.L);
	
		
		
		f = new File(Environment.getExternalStorageDirectory().getPath(), "sensordata.csv"); //create file sensordata.csv	
	}
	
	@Override
	public void onStart() { //timestamp incomplete, only displays ms timestamp of app. start
		super.onStart();
		etime = System.currentTimeMillis();
		Tview.setText("Elapsed time (since app. started in ms): " + etime);
	}

	public void onSensorChanged(SensorEvent event) {
		synchronized (this) {
			etime = System.nanoTime();
			Tview.setText("TIME: " + etime);
				switch(event.sensor.getType()) {
				
				case Sensor.TYPE_ACCELEROMETER:
					AviewX.setText("Accel_x: " + event.values[0]);
					AviewY.setText("Accel_y: " + event.values[1]);
					AviewZ.setText("Accel_z: " + event.values[2]);
					
					counter(event.values[2]);
					
					history[next] = event.values[2];
					next++;
					if (next==5) 
						next = 0;
					
					
					String datastringA = "A:," + Float.toString(event.values[0]) + "," + Float.toString(event.values[1]) + "," + Float.toString(event.values[2]) +","+Long.toString(etime);
				//	GArray.add(datastringA);
					try {
						FileWriter fr = new FileWriter(f, true);
						BufferedWriter out = new BufferedWriter(fr);
							out.write(datastringA);
							out.newLine();  
							out.flush();
							out.close();
					}
				    catch (IOException e) {
				        System.out.println("Exception");
				    } 
					break;
//				case Sensor.TYPE_GYROSCOPE:
//					GviewX.setText("Gyro_x: " + event.values[0]);
//					GviewY.setText("Gyro_y: " + event.values[1]);
//					GviewZ.setText("Gyro_z: " + event.values[2]);
					
//					String datastringG = "G:," + Float.toString(event.values[0]) + "," + Float.toString(event.values[1]) + "," + Float.toString(event.values[2])+","+Long.toString(etime);
//					//GArray.add(datastringG);
//					try {
//						FileWriter fr = new FileWriter(f, true);
//						BufferedWriter out = new BufferedWriter(fr);
//							out.write(datastringG);
//							out.newLine();
//							out.flush();
//							out.close();
//					}
//				    catch (IOException e) {
//				        System.out.println("Exception");
//				    } 
//					
//					break;
//				case Sensor.TYPE_MAGNETIC_FIELD:
//					MviewX.setText("Mag_x: " + event.values[0]);
//					MviewY.setText("Mag_y: " + event.values[1]);
//					MviewZ.setText("Mag_z: " + event.values[2]);
					
//					String datastringM = "M: " +","+ Float.toString(event.values[0]) + "," + Float.toString(event.values[1]) + "," + Float.toString(event.values[2])+","+String.valueOf(etime);
//				//	GArray.add(datastringM);
//					
////					//Writer out = new FileWriter("sensordata1.csv");
////					
////					CSVWriter<Float> csvwriter = new CSVWriterBuilder<Float>(f).build();
////					csvwriter.writeAll(GArray);
//					try {
//						FileWriter fr = new FileWriter(f, true);
//						BufferedWriter out = new BufferedWriter(fr);
//							out.write(datastringM);
//							out.newLine();
//							out.flush();
//							out.close();
//					}
//				    catch (IOException e) {
//				        System.out.println("Exception");
//				    } 
//
//					break;
//				case Sensor.TYPE_LIGHT:
//					Lview.setText("light_intensity: " + event.values[0]);
					
//					String datastringL = "L:,"+Float.toString(event.values[0])+","+ Long.toString(etime);
//					System.out.println(datastringL);
//				//	GArray.add(datastringL);
//					try {
//						FileWriter fr = new FileWriter(f, true);
//						BufferedWriter out = new BufferedWriter(fr);
//							out.write(datastringL);
//							out.newLine();
//							out.flush();
//							out.close();
//					}
//				    catch (IOException e) {
//				        System.out.println("Exception");
//				    } 
//					break;
//				default:
//					break;
				}
		}
	}
	
	public void onAccuracyChanged (Sensor sensor, int accuracy) {
	} //doesn't do anything, but is needed for SensorManager
	
	@Override
	protected void onResume() {
		super.onResume();
		senseM.registerListener(this, senseM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_FASTEST); //SENSOR_DELAY_FASTEST is the fastest sensing rate available
//		senseM.registerListener(this, senseM.getDefaultSensor(Sensor.TYPE_GYROSCOPE), 
//				SensorManager.SENSOR_DELAY_FASTEST);
//		senseM.registerListener(this, senseM.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), 
//				SensorManager.SENSOR_DELAY_FASTEST);
//		senseM.registerListener(this, senseM.getDefaultSensor(Sensor.TYPE_LIGHT), 
//				SensorManager.SENSOR_DELAY_FASTEST);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		steps = 0;
		senseM.unregisterListener(this);
	}
	
	
	public void counter(float acc){
		
		if (history[0] < thresholdp && history[1] < thresholdp && history[2] < thresholdp && history[3] < thresholdp && history[4] < thresholdp && acc > thresholdp) 				
			doubleturn[0] = 1;
			
		if (history[0] > thresholdn && history[1] > thresholdn && history[2] > thresholdn && history[3] > thresholdn && history[4] > thresholdn && acc < thresholdn)
			doubleturn[1] = 1;
		
		if ( (doubleturn[0]==1) && (doubleturn[1]==1)){
			steps++;
			doubleturn[0] = 0;
			doubleturn[1] = 0;
		}
		Cview.setText("Steps so far: " + steps);	
	}
	
	
	
}
