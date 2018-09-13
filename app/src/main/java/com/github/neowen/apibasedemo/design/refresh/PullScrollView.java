package com.github.neowen.apibasedemo.design.refresh;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

public class PullScrollView extends ScrollView implements PullContentWatcher {

    public static final String TAG = PullScrollView.class.getSimpleName();

    boolean onDrag;

    public PullScrollView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public PullScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public PullScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PullScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {

    }

    @Override
    public View getStick() {
        return this;
    }

    @Override
    public boolean onTop() {
        return getScrollY() == 0;
    }

    @Override
    public void setOnDrag(boolean onDrag) {
        this.onDrag = onDrag;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Log.d(TAG, "getScrollY ------> " + getScrollY());
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
//        int action = ev.getAction();
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                Log.d(TAG, "onTouchEvent ---> ACTION_DOWN");
//                break;
//            case MotionEvent.ACTION_MOVE:
//
//                break;
//            case MotionEvent.ACTION_UP:
//                Log.d(TAG, "onTouchEvent --dddddd-> ACTION_UP");
//                break;
//        }
        if (!onDrag) {
            super.onTouchEvent(ev);
        }
        return true;
    }
}
