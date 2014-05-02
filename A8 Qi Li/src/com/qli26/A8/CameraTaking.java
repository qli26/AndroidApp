package com.qli26.A8;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CameraTaking extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera);

		final FragmentManager fm = this.getFragmentManager();
		final CameraFragment cf = (CameraFragment) fm
				.findFragmentById(R.id.camera);

		fm.beginTransaction().hide(cf).commit();

		Button btnStartCamera = (Button) findViewById(R.id.start_camera);
		btnStartCamera.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				fm.beginTransaction().show(cf).commit();
			}
		});
	}

}
