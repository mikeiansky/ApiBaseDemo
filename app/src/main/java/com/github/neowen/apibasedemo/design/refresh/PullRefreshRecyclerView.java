package com.github.neowen.apibasedemo.design.refresh;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class PullRefreshRecyclerView extends RecyclerView implements PullRefreshContentWatcher {

    boolean onDrag;

    public PullRefreshRecyclerView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public PullRefreshRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public PullRefreshRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {

    }

    @Override
    public View getStick() {
        return this;
    }

    @Override
    public boolean onTop() {
        int count = getChildCount();
        if (count == 0) {
            return true;
        }
        View child = getChildAt(0);
        return child.getTop() == 0;
    }

    @Override
    public void setOnDrag(boolean onDrag) {
        this.onDrag = onDrag;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                if (!onDrag) {
                    super.onTouchEvent(ev);
                }
                break;
            default:
                super.onTouchEvent(ev);
                break;
        }
        return true;
    }

}
