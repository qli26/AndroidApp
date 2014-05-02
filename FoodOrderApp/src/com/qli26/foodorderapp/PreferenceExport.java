package com.qli26.foodorderapp;

import android.content.Context;
import android.preference.EditTextPreference;
import android.util.Log;
import android.view.View;

public class PreferenceExport extends EditTextPreference {
	EditTextPreference export;
	PreferenceExport mContext;
	public PreferenceExport(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected View onCreateDialogView() {
		// TODO Auto-generated method stub
		return super.onCreateDialogView();
	}

	@Override
	protected void onDialogClosed(boolean positiveResult) {
		// TODO Auto-generated method stub
		super.onDialogClosed(positiveResult);
		Log.v("msg", "onDialogClosed");
	}
}
