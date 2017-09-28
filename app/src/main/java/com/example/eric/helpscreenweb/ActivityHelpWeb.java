package com.example.eric.helpscreenweb;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Nice html generator:
 * https://html-online.com/editor/
 *
 *
 */
public class ActivityHelpWeb extends AppCompatActivity {

    WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_web);

        web = (WebView) findViewById(R.id.webview);

        web.getSettings().setJavaScriptEnabled(true);
//        web.setWebViewClient(new WebViewClient());

        if (android.os.Build.VERSION.SDK_INT >= 24) {
            web.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
                    if (webResourceRequest.getUrl().getScheme().equals("file")) {
                        webView.loadUrl(webResourceRequest.getUrl().toString());
                    } else {
                        // If the URI is not pointing to a local file, open with an ACTION_VIEW Intent
                        webView.getContext().startActivity(new Intent(Intent.ACTION_VIEW, webResourceRequest.getUrl()));
                    }
                    return true; // in both cases we handle the link manually
                }
            });
        } else {
            web.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                    if (Uri.parse(url).getScheme().equals("file")) {
                        webView.loadUrl(url);
                    } else {
                        // If the URI is not pointing to a local file, open with an ACTION_VIEW Intent
                        webView.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    }
                    return true; // in both cases we handle the link manually
                }
            });
        }

//        web.getSettings().setUseWideViewPort(true);
//        web.getSettings().setLoadWithOverviewMode(true);

        web.getSettings().setSupportZoom(true);
//        web.getSettings().setBuiltInZoomControls(true);
//        web.getSettings().setDisplayZoomControls(false);

        web.loadUrl("file:///android_asset/helpweb.html");

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (web.canGoBack()) {
                        web.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}
