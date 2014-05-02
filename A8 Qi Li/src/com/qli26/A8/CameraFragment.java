package com.qli26.A8;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class CameraFragment extends Fragment {
	private final static String TAG = "CameraActivity";
	private SurfaceView surfaceView;
	private SurfaceHolder surfaceHolder;
	private Camera camera;
	private File picture;
	private Button btnSave;
	private Context mContext;
	private Bitmap previousSavedImage;

	public CameraFragment() {
		// TODO Auto-generated constructor stub
		super();
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mContext = getActivity();
		setupViews();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater
				.inflate(R.layout.camera_fragment, container, false);
		return view;
	}

	private void setupViews() {
		surfaceView = (SurfaceView) getView().findViewById(R.id.camera_preview); // Camera
																					// interface
																					// to
																					// instantiate
																					// components
		surfaceHolder = surfaceView.getHolder(); // Camera interface to
													// instantiate components
		surfaceHolder.addCallback(surfaceCallback); // Add a callback for the
													// SurfaceHolder
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		btnSave = (Button) getActivity().findViewById(R.id.save_pic);
		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("Save picture");
				btnSave.setEnabled(false);
				takePic();
				
			}

		});

	}

	private void takePic() {

		camera.takePicture(null, null, pictureCallback); // picture
	}

	ByteArrayOutputStream baos;
	// Photo call back
	Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
		// @Override
		public void onPictureTaken(byte[] data, Camera camera) {
			Bitmap bMap;
			try {

				bMap = BitmapFactory.decodeByteArray(data, 0, data.length);
				Bitmap bMapRotate;
				Configuration config = getResources().getConfiguration();
				if (config.orientation == 1) { // 坚拍
					Matrix matrix = new Matrix();
					matrix.reset();
					matrix.postRotate(270);
					bMapRotate = Bitmap.createBitmap(bMap, 0, 0,
							bMap.getWidth(), bMap.getHeight(), matrix, true);
					bMap = bMapRotate;
					previousSavedImage = bMap;
					
					baos = new ByteArrayOutputStream();
					bMap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
					
					new SavePictureTask().execute(baos.toByteArray());
					camera.startPreview();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			btnSave.setEnabled(true);
		}
	};

	// save pic
	class SavePictureTask extends AsyncTask<byte[], String, String> {
		@Override
		protected String doInBackground(byte[]... params) {
			String fname = DateFormat.format("yyyyMMddhhmmss", new Date())
					.toString() + ".jpg";

			Log.i(TAG,
					"fname=" + fname + ";dir="
							+ Environment.getExternalStorageDirectory());
			// picture = new
			// File(Environment.getExternalStorageDirectory(),fname);// create
			// file

			picture = new File(Environment.getExternalStorageDirectory() + "/"
					+ fname);

			try {
				FileOutputStream fos = new FileOutputStream(picture.getPath()); // Get
																				// file
																				// output
																				// stream
				fos.write(params[0]); // Written to the file
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			Intent i = new Intent();
			i.putExtra("TakenPhotos", picture.getAbsolutePath());
			i.setAction("android.intent.action.photos");//action与接收器相同
			System.out.println("send broadcast");
			getActivity().sendBroadcast(i);
		

			return null;
		}
	}

	// SurfaceHodler Callback handle to open the camera, off camera and photo
	// size changes
	SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {

		public void surfaceCreated(SurfaceHolder holder) {
			Log.i(TAG, "surfaceCallback====");
			camera = Camera.open(); // Turn on the camera

			camera.setDisplayOrientation(90);
			try {
				camera.setPreviewDisplay(holder); // Set Preview
			} catch (IOException e) {
				camera.release();// release camera
				camera = null;
			}
		}

		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			Log.i(TAG, "====surfaceChanged");
			Camera.Parameters parameters = camera.getParameters(); // Camera
																	// parameters
																	// to obtain
			parameters.setPictureFormat(PixelFormat.JPEG);// Setting Picture
															// Format
			parameters.set("rotation", 180); // Arbitrary rotation
			camera.setDisplayOrientation(90);
			// parameters.setPreviewSize(400, 300); // Set Photo Size
			camera.setParameters(parameters); // Setting camera parameters
			camera.startPreview(); // Start Preview
		}

		public void surfaceDestroyed(SurfaceHolder holder) {
			Log.i(TAG, "====surfaceDestroyed");
			camera.stopPreview();// stop preview
			camera.release(); // Release camera resources
			camera = null;
		}
	};

}
