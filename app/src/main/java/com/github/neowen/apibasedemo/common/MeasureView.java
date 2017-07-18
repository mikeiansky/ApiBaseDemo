package com.github.neowen.apibasedemo.common;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewOverlay;
import android.widget.FrameLayout;

/**
 * Created by Winson on 2016/2/5.
 */
public class MeasureView extends FrameLayout {

    public static final String TAG = MeasureView.class.getSimpleName();

    public MeasureView(Context context) {
        super(context);
    }

    public MeasureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MeasureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MeasureView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (DebugUtils.debug) {
            Log.d(TAG, "onMeasure width : " + getMeasuredWidth() + " , height : " + getMeasuredHeight());
        }
    }
}
