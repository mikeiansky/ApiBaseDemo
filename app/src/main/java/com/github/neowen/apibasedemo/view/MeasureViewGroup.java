package com.github.neowen.apibasedemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import com.github.neowen.apibasedemo.common.DebugUtils;

/**
 * Created by Winson on 2016/2/5.
 */
public class MeasureViewGroup extends FrameLayout {

    public static final String TAG = MeasureViewGroup.class.getSimpleName();

    public MeasureViewGroup(Context context) {
        super(context);
    }

    public MeasureViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MeasureViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MeasureViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mw = getMeasuredWidth();
        int mh = getMeasuredHeight();
        int wsize = MeasureSpec.getSize(widthMeasureSpec);
        int hsize = MeasureSpec.getSize(heightMeasureSpec);
        if (DebugUtils.debug) {
            Log.d(TAG, "onMeasure width : " + mw + " , height : " + mh + " , wsize : " + wsize +" , hsize : " + hsize);
        }
    }
}
