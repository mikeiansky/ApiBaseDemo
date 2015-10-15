package com.github.neowen.apibasedemo.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.github.neowen.apibasedemo.common.DebugUtils;

/**
 * Created by ZWX on 15/10/15.
 */
public class TouchLayout extends FrameLayout {

    public static final String TAG = TouchLayout.class.getSimpleName();

    public TouchLayout(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public TouchLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public TouchLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TouchLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (DebugUtils.debug) {
                    Log.d(TAG, "onTouch --> action : " + event.getAction());
                }
                return false;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        boolean result = super.onTouchEvent(event);
        int action = event.getAction();
        if (DebugUtils.debug) {
            Log.d(TAG, "onTouchEvent action : " + action);
        }
        return true;
    }
}
