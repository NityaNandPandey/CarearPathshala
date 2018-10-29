package com.career.pathshala.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.career.pathshala.R;
import com.career.pathshala.api_call.AppUtil;
import com.career.pathshala.api_call.ConstantValues;

public class OpenLink extends Activity {

    private WebView localWebView;
    ImageView IV_backpress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.open_link);
        IV_backpress=(ImageView)findViewById(R.id.IV_backpress);
        IV_backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtil.finishActivityWithAnimation(OpenLink.this);
            }
        });
        init();
    }

    @Override
    public void onStart() {
        super.onStart();
        // EasyTracker.getInstance(this).activityStart(this);  //Add this method for google analytics.
    }

    @Override
    public void onStop() {
        super.onStop();
        // EasyTracker.getInstance(this).activityStop(this);  // Add this method for google analytics.
    }


    private void init() {
        localWebView = (WebView) findViewById(R.id.webView1);
        localWebView.setBackgroundColor(Color.parseColor("#fff7f7"));
        localWebView.getSettings().setJavaScriptEnabled(true);
        localWebView.getSettings().setPluginState(PluginState.ON);

        final int USER_MOBILE = 0;
        final int USER_DESKTOP = 1;
        localWebView.getSettings().setUserAgentString("" + USER_DESKTOP);


        localWebView.loadUrl(ConstantValues.URI);
        localWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                OpenLink.this.setTitle(ConstantValues.CATEGORY + "...");
                OpenLink.this.setProgress(progress * 100);
                if (progress == 100) {
                    OpenLink.this.setTitle(ConstantValues.CATEGORY);
                }
            }
        });
        localWebView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView paramAnonymousWebView, int paramAnonymousInt, String paramAnonymousString1, String paramAnonymousString2) {
                localWebView.loadUrl("");
            }

            public boolean shouldOverrideUrlLoading(WebView paramAnonymousWebView, String url_str) {
                Log.e("...", "url_str= " + url_str);
                ConstantValues.URI = url_str;
                paramAnonymousWebView.loadUrl(ConstantValues.URI);
                return true;
            }
        });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && localWebView.canGoBack()) {
            localWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
