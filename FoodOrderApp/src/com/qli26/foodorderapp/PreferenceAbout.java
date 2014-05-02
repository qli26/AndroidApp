package com.qli26.foodorderapp;

import android.content.Context;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceClickListener;
import android.widget.Toast;

public class PreferenceAbout extends PreferenceActivity {

	Preference author = null;
	Preference email = null;
	Preference version = null;
	Preference description = null;
	
	Context mContext = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.addPreferencesFromResource(R.xml.preferenceabout);
		mContext = this;

		author = findPreference("author");
/*
		preference0
				.setOnPreferenceClickListener(new OnPreferenceClickListener() {

					@Override
					public boolean onPreferenceClick(Preference preference) {
						Toast.makeText(mContext, "自定义布局A被按下", Toast.LENGTH_LONG)
								.show();
						return false;
					}
				});*/
		email = findPreference("email");
		version = findPreference("version");
		description = findPreference("description");
	}
}
