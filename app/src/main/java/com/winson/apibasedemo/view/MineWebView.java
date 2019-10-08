package com.winson.apibasedemo.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebView;
import androidx.annotation.RequiresApi;

/**
 * @author Winson
 * @date on 2019/8/7
 */
public class MineWebView extends WebView {

    public MineWebView(Context context) {
        super(context);
    }

    public MineWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MineWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MineWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public MineWebView(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Log.d("TAG","onScrollChanged --------> ");
    }
}
