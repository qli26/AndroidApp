package com.qli26.foodorderapp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.qli26.database.Order;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.Toast;

public class PreferenceMenu extends PreferenceActivity {
	Preference about = null;
	EditTextPreference export = null;
	String orders = null;

	Context mContext = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final Bundle bundle = this.getIntent().getExtras();
		orders = bundle.getString("Orders");

		this.addPreferencesFromResource(R.xml.preferencemenu);
		mContext = this;

		about = findPreference("about");
		about.setOnPreferenceClickListener(new OnPreferenceClickListener() {

			@Override
			public boolean onPreferenceClick(Preference preference) {
				Toast toast = Toast.makeText(mContext, "Jumping to About",
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				Intent i = new Intent(mContext, PreferenceAbout.class);
				startActivity(i);
				return true;
			}
		});

		// addPreferencesFromResource(R.xml.preferenceexport);

		export = (EditTextPreference) findPreference("edit_file_name");

		export.setPositiveButtonText("ok");
		export.setNegativeButtonText("cancel");

		export.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

			@Override
			public boolean onPreferenceClick(Preference preference) {
				// TODO Auto-generated method stub
				if (orders.equals("")) {
					Toast toast = Toast.makeText(mContext,
							"No history order yet!", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
					return false;
				} else {
					return true;
				}
			}
		});

		export.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
			@Override
			public boolean onPreferenceChange(Preference preference,
					Object newValue) {
				export.setSummary("Order info is saved to /sdcard/"
						+ newValue.toString() + ".txt");
				// Toast.makeText(getApplicationContext(),Environment.getExternalStorageState(),
				// 1).show();

				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					try {
						saveToSDCard(newValue.toString() + ".txt", orders);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Toast toast = Toast.makeText(getApplicationContext(),
							"success", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				} else {
					Toast toast = Toast.makeText(getApplicationContext(),
							"error", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}

				return true;
			}
		});
	}

	public void saveToSDCard(String filename, String orders) throws IOException {

		File file = new File(Environment.getExternalStorageDirectory(),
				filename); // get SDCARD path
		FileOutputStream outStream = new FileOutputStream(file);
		outStream.write(orders.getBytes());
		outStream.close();
	}
}
