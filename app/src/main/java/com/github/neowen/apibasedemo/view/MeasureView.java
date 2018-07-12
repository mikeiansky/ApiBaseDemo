package com.github.neowen.apibasedemo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.github.neowen.apibasedemo.common.DebugUtils;

/**
 * Created by Winson on 2018/7/12.
 */
public class MeasureView extends View {

    public static final String TAG = MeasureView.class.getSimpleName();

    public MeasureView(Context context) {
        super(context);
    }

    public MeasureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MeasureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MeasureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
