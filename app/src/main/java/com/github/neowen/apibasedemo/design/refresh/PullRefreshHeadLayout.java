package com.github.neowen.apibasedemo.design.refresh;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PullRefreshHeadLayout extends LinearLayout implements PullRefreshHeadWatcher {

    public static final String TAG = PullRefreshHeadWatcher.class.getSimpleName();

    private TextView flagTV;

    public PullRefreshHeadLayout(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public PullRefreshHeadLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public PullRefreshHeadLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PullRefreshHeadLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        View co = new View(context);
        LayoutParams lp1 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp1.gravity = Gravity.BOTTOM | Gravity.LEFT;
        lp1.weight = 1;
        co.setLayoutParams(lp1);
        addView(co);

        flagTV = new TextView(context);
        flagTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        flagTV.setTextColor(Color.RED);
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.BOTTOM | Gravity.LEFT;
        flagTV.setLayoutParams(lp);
        addView(flagTV);
//        setBackgroundColor(0xFFCC80);
    }

    @Override
    public void onPullProgressUpdate(int progress) {
        flagTV.setText("onPullProgressUpdate : " + progress);
        Log.d(TAG, "onPullProgressUpdate ---> " + progress);
    }

    @Override
    public void onRefresh() {
        flagTV.setText("OnRefresh");
    }

    @Override
    public void onRefreshComplete() {
        flagTV.setText("OnRefreshComplete");
    }

    @Override
    public View getStick() {
        return this;
    }
}
