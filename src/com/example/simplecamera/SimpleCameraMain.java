package com.example.simplecamera;

import java.io.IOException;
import java.util.ArrayList;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.Window;

public class SimpleCameraMain extends Activity implements CvCameraViewListener2 {

	// Instance Variables
	private static MenuItem menuToMap;
	private static DatabaseHandler db;
	private static DBAdapter adapter;
	private static ArrayList<DBItem> list;
//	private static AssetManager assets;
//	private static Mat img2, gray1, gray2, hist1, hist2;
//	private static List<Mat> l1, l2;
	private JavaCameraView cameraView;

	private BaseLoaderCallback loaderCallback = new BaseLoaderCallback(this) {
		public void onManagerConnected(int status) {
			switch (status) {
			case LoaderCallbackInterface.SUCCESS: {
				Log.i("In BaseLoader", "OpenCV loaded successfully");
				cameraView.enableView();
				break;
			}
			default: {
				super.onManagerConnected(status);
			}
			}
		};

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Remove notification bar
		// this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);

		try {
			setupDatabase();
		} catch (IOException e) {
			e.printStackTrace();
		}

		setupViews();
		setupInstanceVariables();
		setupCamera();
	}

	private void setupInstanceVariables() {
//		assets = getResources().getAssets();
//		img2 = new Mat();
//		gray1 = new Mat();
//		gray2 = new Mat();
//		hist1 = new Mat();
//		hist2 = new Mat();
//		l1 = new ArrayList<Mat>();
//		l2 = new ArrayList<Mat>();
	}

	private void setupDatabase() throws IOException {
		db = new DatabaseHandler(this);
		db.createDataBase();
		adapter = new DBAdapter(this);
		adapter.open();

		// Get a list of all DBItems
		list = adapter.getTestData();
	}

	private void setupCamera() {
		cameraView = (JavaCameraView) findViewById(R.id.mainCameraView);
		cameraView.setVisibility(SurfaceView.VISIBLE);
		cameraView.setCvCameraViewListener(this);
	}

	private void setupViews() {
		// text = (TextView) findViewById(R.id.text);
		// img = (ImageView)findViewById(R.id.testimage);
	}

	// Setup Option Menu Items
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menuToMap = menu.add(R.string.menu_toMap);
		return true;
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item == menuToMap) {
			openMapView();
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		super.onResume();
		OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_8, this,
				loaderCallback);
	}

	// Disable Camera when closing the application
	public void onDestroy() {
		super.onDestroy();
		if (cameraView != null) {
			cameraView.disableView();
		}
	}

	@Override
	public void onCameraViewStarted(int width, int height) {
	}

	@Override
	public void onCameraViewStopped() {

	}

	// Handle Camera Frames
	@Override
	public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
		try {
			compareImgWithDB(inputFrame);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		processGeoData();
		return inputFrame.rgba();
	}

	// Calculate similarity frame <-> database-image
	private void compareImgWithDB(CvCameraViewFrame inputFrame)
			throws InterruptedException, IOException {

		for (int i = 0; i < list.size(); i++) {

		}
	}

	// Display current calculated position
	private void processGeoData() {

	}

	// Switch to Map Activity
	private void openMapView() {
		Intent i = new Intent(this, SimpleCameraMap.class);
		startActivity(i);
	}
}
