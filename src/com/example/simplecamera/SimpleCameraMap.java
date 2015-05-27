package com.example.simplecamera;

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
import android.widget.TextView;

public class SimpleCameraMap extends Activity{

	private static MenuItem menuToMain;	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_map);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menuToMain = menu.add(R.string.menu_toMain);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item == menuToMain) {
			openMainView();
		}

		return super.onOptionsItemSelected(item);
	}
	
	private void openMainView(){
		Intent i = new Intent(this, SimpleCameraMain.class);
		startActivity(i);
	}
}
