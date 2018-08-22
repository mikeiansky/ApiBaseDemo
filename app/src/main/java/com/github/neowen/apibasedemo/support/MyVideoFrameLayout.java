package com.github.neowen.apibasedemo.support;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import com.github.neowen.apibasedemo.drawable.MyView;

public class MyVideoFrameLayout extends FrameLayout {

    public static final String TAG = MyVideoFrameLayout.class.getSimpleName();

    public MyVideoFrameLayout(@NonNull Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public MyVideoFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public MyVideoFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    public MyVideoFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        setWillNotDraw(false);
    }

//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        Log.d(TAG, "onDraw ----> ");
//    }
//
//    @Override
//    public void draw(Canvas canvas) {
//        super.draw(canvas);
//        Log.d(TAG, "draw ----> ");
//    }

}
