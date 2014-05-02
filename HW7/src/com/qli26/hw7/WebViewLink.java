package com.qli26.hw7;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebViewLink extends Fragment {
	public WebView webview;
	public Context mContext;
	public String ConnectionType;

	public WebViewLink() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);

		try {
			mCallback = (OnHeadlineSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnHeadlineSelectedListener");
		}
	}

	OnHeadlineSelectedListener mCallback;

	// Container Activity must implement this interface
	public interface OnHeadlineSelectedListener {
		public void onWebView(WebView wv);
	}
 
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		if(!isMobileNetworkAvailable(getActivity())){
			showConnectionNADialog(getActivity());
		}
		
		// String myURL = "file:///android_asset/Connection API.htm";
		String myURL = "http://www.iit.edu";

		// webview = (WebView) getActivity().findViewById(R.id.webview);
		webview.getSettings().setJavaScriptEnabled(true);
		// webview.addJavascriptInterface(new WebAppInterface(getActivity()),
		// "Android");
		// webview.loadUrl("http://www.illinoistechathletics.com/");
		webview.setWebViewClient(new HelloWebViewClient());
		webview.loadUrl(myURL);
		this.mCallback.onWebView(webview);
	}

	private class HelloWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.webviewlink, container, false);
		webview = (WebView) view.findViewById(R.id.webview);
		return view;
	}

	private ConnectivityManager connMgr;

	public boolean isMobileNetworkAvailable(Context con) {
		if (null == connMgr) {
			connMgr = (ConnectivityManager) con
					.getSystemService(Context.CONNECTIVITY_SERVICE);
		}
		NetworkInfo wifiInfo = connMgr
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (wifiInfo.isConnected()) {
			ConnectionType = "WIFI";
			Toast t = Toast.makeText(getActivity(), "ConnectionType = "+ConnectionType, Toast.LENGTH_LONG);
			t.setGravity(Gravity.CENTER, 0, 0);
			t.show();
			return true;
		} else {
			return false;
		}
	}

	public void showConnectionNADialog(final Context con) {
		AlertDialog.Builder builder = new AlertDialog.Builder(con);
		builder.setTitle("Network not available")
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setMessage("Go and check Wireless & networks settings?")
				.setPositiveButton("OK", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(
								Settings.ACTION_WIRELESS_SETTINGS);
						con.startActivity(intent);
					}
				}).setNegativeButton("Cancel", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).setCancelable(true);

		builder.create().show();
	}

}
