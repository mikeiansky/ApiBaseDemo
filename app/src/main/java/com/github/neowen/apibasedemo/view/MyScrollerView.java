package com.github.neowen.apibasedemo.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * Created by Winson on 2018/7/15.
 */
public class MyScrollerView extends FrameLayout {

    private Scroller scroller;
    private float lastX, lastY;

    public MyScrollerView(@NonNull Context context) {
        super(context);

        init(context, null, 0, 0);
    }

    public MyScrollerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public MyScrollerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyScrollerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        scroller = new Scroller(context);
    }

    public void change() {
        View child = getChildAt(0);
        if (child != null) {
            child.setScaleX(2);
            child.setScaleY(2);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastX = event.getX();
                lastY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                scroll(event);
                break;
            case MotionEvent.ACTION_UP:
                scroll(event);
                break;
        }
        return true;
    }

    private void scroll(MotionEvent event) {
        float cx = event.getX();
        float cy = event.getY();
        float dx = lastX - cx;
        float dy = lastY - cy;
        lastX = cx;
        lastY = cy;
        Log.d("TAg", "----> scroll dx : " + dx + " , dy : " + dy);
        scrollBy((int) dx, (int) dy);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
    }
}
