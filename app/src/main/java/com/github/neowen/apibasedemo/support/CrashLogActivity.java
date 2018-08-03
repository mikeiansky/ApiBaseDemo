package com.github.neowen.apibasedemo.support;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.github.neowen.apibasedemo.BaseActivity;
import com.github.neowen.apibasedemo.R;
import com.github.neowen.apibasedemo.utils.Utils;

import java.io.File;

/**
 * Created by Winson on 2018/8/3.
 */
public class CrashLogActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = CrashLogActivity.class.getSimpleName();

//    WebView webView;
    TextView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crash_log_layout);

        webView = (TextView) findViewById(R.id.web_view);
//        webView = (WebView) findViewById(R.id.web_view);
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        webSettings.setLoadsImagesAutomatically(true);
//        webSettings.setLoadWithOverviewMode(true);
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setUseWideViewPort(true);
//        webSettings.setSupportZoom(true);
//        webSettings.setTextSize(WebSettings.TextSize.LARGER);
//        webSettings.setBuiltInZoomControls(false);
//        webSettings.setDomStorageEnabled(true);
//        webView.setWebViewClient(new WebViewClient());

        findViewById(R.id.add_crash_log).setOnClickListener(this);
        findViewById(R.id.show_log).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.add_crash_log:
                String hel = null;
                Log.d(TAG, "----> " + hel.length());
                break;
            case R.id.show_log:
                showLog();
                break;
        }
    }

    private void showLog() {

//        webView.loadUrl("file://"+getCacheDir().getPath() + File.separator + "crash_log.txt");
//        webView.loadUrl("http://www.baidu.com");

        String result = Utils.readLog(getCacheDir().getPath() + File.separator + "crash_log.txt");
        webView.setText(result);

//        webView.setText("hell\r\nNIhao\r\ndd");
    }

}
