package com.qli26.A8;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

public class PreviousPhoto extends Activity {
	ImageView iv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.b);

		iv = (ImageView) findViewById(R.id.previous_image);

		myBroadcast mb = new myBroadcast();
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.intent.action.photos");
		PreviousPhoto.this.registerReceiver(mb, filter);
	}

	public class myBroadcast extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			// Toast.makeText(context, "已经接收到测试广播", Toast.LENGTH_SHORT).show();
			String filePath = intent.getStringExtra("TakenPhotos");
			
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 2;
			Bitmap bm = BitmapFactory.decodeFile(filePath, options);
			iv.setImageBitmap(bm);
		}
	}
}
