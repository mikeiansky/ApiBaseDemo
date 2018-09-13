package com.github.neowen.apibasedemo.design.refresh;

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

import com.github.neowen.apibasedemo.R;

public class RefreshView extends FrameLayout {

    public static final String TAG = RefreshView.class.getSimpleName();

    private View headView;
    private View contentView;

    public RefreshView(@NonNull Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public RefreshView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public RefreshView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RefreshView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {

    }

    public void addHeadView(View headView) {
        this.headView = headView;
        headView.setId(R.id.test);
        addView(this.headView);
    }

    public void addContentView(View contentView) {
        this.contentView = contentView;
        addView(this.contentView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();

        if (headView != null) {
            LayoutParams lp = (LayoutParams) headView.getLayoutParams();
            lp.topMargin = -headView.getMeasuredHeight();
            maxOffset = Math.abs(lp.topMargin);
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean result = super.dispatchTouchEvent(ev);
        Log.d(TAG, "dispatchTouchEvent----------->");
        detailMotionEvent(ev);
        return result;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean result = super.onInterceptTouchEvent(ev);
        Log.d(TAG, "onInterceptTouchEvent------->");
        return result;
    }

    int lastY;
    int maxOffset;
    int totalOffset;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = super.onTouchEvent(event);
        return true;
    }

    private void detailMotionEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int cy = (int) event.getRawY();
                int offset = cy - lastY;
                int preTotalOffset = totalOffset + offset;
                if (preTotalOffset >= maxOffset) {
                    offset = maxOffset - totalOffset;
                    preTotalOffset = maxOffset;
                } else if (preTotalOffset <= 0) {
                    offset = -totalOffset;
                    preTotalOffset = 0;
                }
                totalOffset = preTotalOffset;

                headView.offsetTopAndBottom((int) offset);
                contentView.offsetTopAndBottom((int) offset);

                lastY = cy;
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
    }
}
