package com.qli26.hw7;

import android.content.Context;
import android.view.Gravity;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class WebAppInterface {

	Context mContext;

    /** Instantiate the interface and set the context */
    WebAppInterface(Context c) {
        mContext = c;
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast t = Toast.makeText(mContext, toast, Toast.LENGTH_LONG);
        t.setGravity(Gravity.CENTER, 0, 0);
        t.show();
    }
}
