package com.example.ece498rc_mp1;

//import java.text.DecimalFormat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;
//import java.util.ArrayList;
//import java.util.List;
//import android.content.Context;

//import com.csvreader.CsvWriter;
//package au.com.bytecode.opencsv;

public class SecondActivity extends Activity implements SensorEventListener {
	final String TAG = "MP1";
	SensorManager senseM = null;

	TextView Tview = null;
	TextView AviewX = null;
	TextView AviewY = null;
	TextView AviewZ = null;
	TextView GviewX = null;
	TextView GviewY = null;
	TextView GviewZ = null;
	TextView MviewX = null;
	TextView MviewY = null;
	TextView MviewZ = null;
	TextView Lview = null;
	TextView Cview = null;
	TextView Azview = null;
	TextView Dir = null;
	// List<String> GArray = new ArrayList<String>();
	// File f = new File(Environment.getExternalStorageDirectory().getPath(),
	// "sensordata.csv");

	File f;
	long etime;
	int steps = 0;
	float thresholdp = (float) 14 * 14;
	float thresholdn = (float) 5 * 5;
	float[] history = new float[3];
	int[] doubleturn = new int[2];
	int next = 0;
	boolean nextStable = false;

	// CSV csv = CSV
	// .separator(',') // delimiter of fields
	// .quote('"') // quote character
	// .create(); // new instance is immutable

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		senseM = (SensorManager) getSystemService(SENSOR_SERVICE); // reference
																	// to
																	// SensorManager
		setContentView(R.layout.activity_main);

		Tview = (TextView) findViewById(R.id.T);

		Cview = (TextView) findViewById(R.id.C);

		AviewX = (TextView) findViewById(R.id.xA); // references to the 10
													// textview widgets
		AviewY = (TextView) findViewById(R.id.yA);
		AviewZ = (TextView) findViewById(R.id.zA);
		GviewX = (TextView) findViewById(R.id.xG);
		GviewY = (TextView) findViewById(R.id.yG);
		GviewZ = (TextView) findViewById(R.id.zG);
		MviewX = (TextView) findViewById(R.id.xM);
		MviewY = (TextView) findViewById(R.id.yM);
		MviewZ = (TextView) findViewById(R.id.zM);
		Lview = (TextView) findViewById(R.id.L);
		Azview = (TextView) findViewById(R.id.Az);
		// Dir = (TextView) findViewById(R.id.Dir);

		f = new File(Environment.getExternalStorageDirectory().getPath(),
				"sensordata.csv"); // create file sensordata.csv
	}

	@Override
	public void onStart() { // timestamp incomplete, only displays ms timestamp
							// of app. start
		super.onStart();
		etime = System.currentTimeMillis();
		Tview.setText("Elapsed time (since app. started in ms): " + etime);
	}

	float[] grav;
	float[] geomag;
	float azimuth = 0;

	public void onSensorChanged(SensorEvent event) {
		synchronized (this) {
			etime = System.nanoTime();
			Tview.setText("TIME: " + etime);
			if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
				AviewX.setText("Accel_x: " + event.values[0]);
				AviewY.setText("Accel_y: " + event.values[1]);
				AviewZ.setText("Accel_z: " + event.values[2]);
				grav = event.values;

				if (!nextStable) {
					if (next == 3)
						nextStable = true;
					else {
						history[next] = event.values[2];
						next++;
					}
				} else {
					history[0] = history[1];
					history[1] = history[2];
					history[2] = history[3];
					// history[3] = history[4];
					// history[4] = history[5];
					history[3] = event.values[2];
				}
				counter();

				String datastringA = "A:," + Float.toString(event.values[0])
						+ "," + Float.toString(event.values[1]) + ","
						+ Float.toString(event.values[2]) + ","
						+ Long.toString(etime);
				// GArray.add(datastringA);

				try {
					FileWriter fr = new FileWriter(f, true);
					BufferedWriter out = new BufferedWriter(fr);
					out.write(datastringA);
					out.newLine();
					out.flush();
					out.close();
				} catch (IOException e) {
					System.out.println("Exception");
				}
			}
			// case Sensor.TYPE_GYROSCOPE:
			// GviewX.setText("Gyro_x: " + event.values[0]);
			// GviewY.setText("Gyro_y: " + event.values[1]);
			// GviewZ.setText("Gyro_z: " + event.values[2]);

			// String datastringG = "G:," + Float.toString(event.values[0]) +
			// "," + Float.toString(event.values[1]) + "," +
			// Float.toString(event.values[2])+","+Long.toString(etime);
			// //GArray.add(datastringG);
			// try {
			// FileWriter fr = new FileWriter(f, true);
			// BufferedWriter out = new BufferedWriter(fr);
			// out.write(datastringG);
			// out.newLine();
			// out.flush();
			// out.close();
			// }
			// catch (IOException e) {
			// System.out.println("Exception");
			// }
			//
			// break;

			if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
				MviewX.setText("Mag_x: " + event.values[0]);
				MviewY.setText("Mag_y: " + event.values[1]);
				MviewZ.setText("Mag_z: " + event.values[2]);
				geomag = event.values;

				String datastringM = "M: " + ","
						+ Float.toString(event.values[0]) + ","
						+ Float.toString(event.values[1]) + ","
						+ Float.toString(event.values[2]) + ","
						+ String.valueOf(etime);

				// GArray.add(datastringM);

				// //Writer out = new FileWriter("sensordata1.csv");
				//
				// CSVWriter<Float> csvwriter = new
				// CSVWriterBuilder<Float>(f).build();
				// csvwriter.writeAll(GArray);

				try {
					FileWriter fr = new FileWriter(f, true);
					BufferedWriter out = new BufferedWriter(fr);
					out.write(datastringM);
					out.newLine();
					out.flush();
					out.close();
				} catch (IOException e) {
					System.out.println("Exception");
				}
			}

			// case Sensor.TYPE_LIGHT:
			// Lview.setText("light_intensity: " + event.values[0]);

			// String datastringL = "L:,"+Float.toString(event.values[0])+","+
			// Long.toString(etime);
			// System.out.println(datastringL);
			// // GArray.add(datastringL);
			// try {
			// FileWriter fr = new FileWriter(f, true);
			// BufferedWriter out = new BufferedWriter(fr);
			// out.write(datastringL);
			// out.newLine();
			// out.flush();
			// out.close();
			// }
			// catch (IOException e) {
			// System.out.println("Exception");
			// }
			// break;
			// default:
			// break;
			//

			if (grav != null && geomag != null) {
				float R[] = new float[9];
				float I[] = new float[9];
				boolean success = SensorManager.getRotationMatrix(R, I, grav,
						geomag);
				if (success) {
					float orientation[] = new float[3];
					SensorManager.getOrientation(R, orientation);
					azimuth = orientation[0];
				}
				Azview.setText("Azimuth: " + azimuth);
			}
		}
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	} // doesn't do anything, but is needed for SensorManager

	@Override
	protected void onResume() {
		super.onResume();
		// SENSOR_DELAY_FASTEST is the fastest sensing rate available
		senseM.registerListener(this,
				senseM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_FASTEST);
		// senseM.registerListener(this,
		// senseM.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
		// SensorManager.SENSOR_DELAY_FASTEST);
		// senseM.registerListener(this,
		// senseM.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
		// SensorManager.SENSOR_DELAY_FASTEST);
		// senseM.registerListener(this,
		// senseM.getDefaultSensor(Sensor.TYPE_LIGHT),
		// SensorManager.SENSOR_DELAY_FASTEST);
	}

	@Override
	protected void onStop() {
		super.onStop();
		steps = 0;
		senseM.unregisterListener(this);
	}

	public void counter() {
		// when first 2 values are smaller than threshold and last 2 are
		// greater, we recognize a local maxima
		float h0 = history[0] * history[0];
		float h1 = history[1] * history[1];
		float h2 = history[2] * history[2];
		float h3 = history[3] * history[3];

		if (h0 < thresholdp && h1 < thresholdp && h2 < thresholdp
				&& h3 > thresholdp)
			steps++;

		// // we count 1 step for each pair of local maxima and minima
		// if ((doubleturn[0] == 1) && (doubleturn[1] == 1)) {
		// steps++;
		// doubleturn[0] = 0;
		// doubleturn[1] = 0;
		// }

		Cview.setText("Steps so far: " + (int) (Math.round(steps / 2)));
	}
}
