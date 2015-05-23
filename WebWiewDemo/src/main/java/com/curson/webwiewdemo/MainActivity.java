package com.curson.webwiewdemo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class MainActivity extends ActionBarActivity {

    private final static String URL = "http://list.tmall.com/search_product.htm?q=%BB%FA%D0%B5%BC%FC%C5%CC+%C7%E0%D6%E1&type=p&spm=a220m.1000858.a2227oh.d100&xl=%BB%FA%D0%B5%BC%FC%C5%CC_2&from=.list.pc_1_suggest";
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.loadUrl(URL);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }


}
