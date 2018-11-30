package com.career.pathshala.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import com.career.pathshala.R;
import com.career.pathshala.api_call.ConstantValues;


public class WebView extends Activity {
    private android.webkit.WebView localWebView;
    String url1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_activity);


        localWebView = (android.webkit.WebView) findViewById(R.id.webView1);

        Intent intent = getIntent();
        url1 = intent.getStringExtra("url");

        localWebView.setBackgroundColor(Color.parseColor("#fff7f7"));
        localWebView.getSettings().setJavaScriptEnabled(true);
        localWebView.getSettings().setPluginState(WebSettings.PluginState.ON);

        final int USER_MOBILE = 0;
        final int USER_DESKTOP = 1;
        localWebView.getSettings().setUserAgentString("" + USER_DESKTOP);


        localWebView.loadUrl(url1);
        localWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(android.webkit.WebView view, int progress) {
                WebView.this.setTitle(ConstantValues.CATEGORY + "...");
                WebView.this.setProgress(progress * 100);
                if (progress == 100) {
                    WebView.this.setTitle(ConstantValues.CATEGORY);
                }
            }
        });
        localWebView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(android.webkit.WebView paramAnonymousWebView, int paramAnonymousInt, String paramAnonymousString1, String paramAnonymousString2) {
                localWebView.loadUrl("");
            }


            public boolean shouldOverrideUrlLoading(android.webkit.WebView paramAnonymousWebView, String url_str) {
                Log.e("...", "url_str= " + url_str);
                ConstantValues.URI = url_str;
                paramAnonymousWebView.loadUrl(ConstantValues.URI);
                return true;
            }
        });
    }
}