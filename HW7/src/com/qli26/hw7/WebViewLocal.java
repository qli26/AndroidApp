package com.qli26.hw7;

import com.qli26.hw7.WebViewLink.OnHeadlineSelectedListener;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewLocal extends Fragment {
	public WebView webview;
	public Context mContext;
	String myURL;

	public WebViewLocal() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		myURL = "file:///android_asset/ViewportConnection.html";
		
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

		webview = (WebView) getActivity().findViewById(R.id.webview);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.addJavascriptInterface(new WebAppInterface(getActivity()), "Android");
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
	
	
}
