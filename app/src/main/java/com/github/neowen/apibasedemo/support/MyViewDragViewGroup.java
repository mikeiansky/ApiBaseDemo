package com.github.neowen.apibasedemo.support;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by ZWX on 15/10/15.
 */
public class MyViewDragViewGroup extends FrameLayout {

    public static final String TAG = MyViewDragViewGroup.class.getSimpleName();

    ViewDragHelper mViewDragHelper;

    public MyViewDragViewGroup(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public MyViewDragViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public MyViewDragViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyViewDragViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        mViewDragHelper = ViewDragHelper.create(this, mViewDragHelperCallback);
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_ALL);
    }

    private ViewDragHelper.Callback mViewDragHelperCallback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        @Override
        public boolean onEdgeLock(int edgeFlags) {
            return true;
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            super.onEdgeDragStarted(edgeFlags, pointerId);
            mViewDragHelper.captureChildView(getChildAt(0), pointerId);
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (left < getPaddingLeft()) {
                return getPaddingLeft();
            }
            int maxLeft = getWidth() - getPaddingRight() - child.getWidth();
            if (left > maxLeft) {
                return maxLeft;
            }
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            if (top < getPaddingTop()) {
                return getPaddingTop();
            }
            int maxTop = getHeight() - getPaddingBottom() - child.getHeight();
            if (top > maxTop) {
                return maxTop;
            }
            return top;
        }
    };

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        boolean result = super.onTouchEvent(event);
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

}
