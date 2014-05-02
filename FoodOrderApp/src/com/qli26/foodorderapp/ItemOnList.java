package com.qli26.foodorderapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ItemOnList extends Activity 
implements SizeFragment.OnHeadlineSelectedListener{

	int price_chosen = 0;
	String myName;
	DescriptionFragment descriptionFragment;
	SizeFragment sizeFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_detail);
		final Bundle bundle = this.getIntent().getExtras();
		/*
		 * Toast toast = Toast.makeText(this .getApplicationContext(),
		 * bundle.getString("name")+"\n"+ bundle.getString("description")+"\n"+
		 * bundle.getInt("small")+"\n"+ bundle.getInt("medium")+"\n"+
		 * bundle.getInt("large")+"\n", Toast.LENGTH_SHORT); toast.show();
		 */
		FragmentManager fm = getFragmentManager();
		descriptionFragment = (DescriptionFragment) fm
				.findFragmentById(R.id.frag_description);

		sizeFragment = (SizeFragment) fm.findFragmentById(R.id.frag_size);


		myName = bundle.getString("name");
		descriptionFragment.setMyName(myName);

		descriptionFragment.setDescription(bundle.getString("description"));
		sizeFragment.setPrice(bundle.getIntArray("price"));

		Button cancel = (Button) findViewById(R.id.Cancel);
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = getIntent();
				setResult(RESULT_CANCELED, intent);
				finish();
			}

		});
		
		Button Order = (Button) findViewById(R.id.Order);
		Order.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (price_chosen == 0) {
					Toast toast = Toast
							.makeText(getApplicationContext(),
									"Please select one size first!",
									Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				} else {
					/*
					 * Toast toast = Toast.makeText(getApplicationContext(),
					 * "Back to previous screen", Toast.LENGTH_SHORT);
					 * toast.setGravity(Gravity.CENTER, 0, 0); toast.show();
					 */
					Intent intent = getIntent();
					Bundle bundle2 = new Bundle();
					bundle2.putString("name", myName);
					bundle2.putInt("price", price_chosen);
					intent.putExtras(bundle2);
					setResult(RESULT_OK, intent);
					finish();
				}
			}

		});
	}
	
	public void choosenPrice(int price_chosen) {
		this.price_chosen = price_chosen;
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		price_chosen = (Integer) savedInstanceState.getSerializable("price");
		myName = (String) savedInstanceState.getSerializable("myName");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putSerializable("price", price_chosen);
		outState.putSerializable("myName", myName);
	}
}
