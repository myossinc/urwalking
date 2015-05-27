package com.example.simplecamera;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.Window;

public class SimpleCameraMain extends Activity implements CvCameraViewListener2 {

	private static MenuItem menuToMap;

	private static DatabaseHandler db;
	private static DBAdapter adapter;
	private static ArrayList<DBItem> list;
	private static AssetManager assets;

	private static Mat img2, gray1, gray2, hist1, hist2;
	private static List<Mat> l1, l2;

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

	private JavaCameraView cameraView;

	
	//mein Kommentar
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Remove notification bar
		// this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//Test
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
		assets = getResources().getAssets();
		img2 = new Mat();
		gray1 = new Mat();
		gray2 = new Mat();
		hist1 = new Mat();
		hist2 = new Mat();
		l1 = new ArrayList<Mat>();
		l2 = new ArrayList<Mat>();
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

	private void compareImgWithDB(CvCameraViewFrame inputFrame)
			throws InterruptedException, IOException {
		gray1 = inputFrame.gray();
		for (int i = 0; i < list.size(); i++) {
			
		}
	}

	private void processGeoData() {

	}

	private void openMapView() {
		Intent i = new Intent(this, SimpleCameraMap.class);
		startActivity(i);
	}
}
