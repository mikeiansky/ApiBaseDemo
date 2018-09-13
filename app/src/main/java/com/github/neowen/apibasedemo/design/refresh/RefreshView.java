package com.github.neowen.apibasedemo.design.refresh;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

public class RefreshView extends FrameLayout {

    public static final String TAG = RefreshView.class.getSimpleName();

    private View headView;
    private PullContentWatcher contentView;
    private ValueAnimator releaseAnimator, refreshAnimator;

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
        handler = new Handler();
    }

    public void addHeadView(View headView) {
        this.headView = headView;
        addView(this.headView);
    }

    public void addContentView(PullContentWatcher contentView) {
        this.contentView = contentView;
        addView(contentView.getStick());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (headView != null) {
            LayoutParams lp = (LayoutParams) headView.getLayoutParams();
            lp.topMargin = -headView.getMeasuredHeight();
            maxOffset = Math.abs(lp.topMargin);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean result = super.dispatchTouchEvent(ev);
        if (headView != null && contentView != null) {
            detailMotionEvent(ev);
        }
        return result;
    }

    int lastY;
    int maxOffset;
    int totalOffset;
    boolean onDrag, pullDown;
    int lastReleaseY;
    Handler handler;

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacks(releaseRun);
    }

    Runnable releaseRun = new Runnable() {
        @Override
        public void run() {
            release();
        }
    };

    DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator();
    ValueAnimator.AnimatorUpdateListener releaseUpdateListener = new ValueAnimator.AnimatorUpdateListener() {

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            int cy = (int) animation.getAnimatedValue();

            int offset = cy - lastReleaseY;
            headView.offsetTopAndBottom(offset);
            contentView.getStick().offsetTopAndBottom(offset);
            totalOffset += offset;
            lastReleaseY = cy;

        }

    };

    private void detailMotionEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastY = (int) event.getRawY();
                handler.removeCallbacks(releaseRun);
                if (releaseAnimator != null) {
                    releaseAnimator.cancel();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int cy = (int) event.getRawY();
                int offset = cy - lastY;
                onDrag = contentView.onTop()
                        && (totalOffset > 0 || offset > 0);
                contentView.setOnDrag(onDrag);

                if (onDrag) {
                    pullDown = offset > 0;
                    int preTotalOffset = totalOffset + offset;
                    if (preTotalOffset >= maxOffset) {
                        offset = maxOffset - totalOffset;
                        preTotalOffset = maxOffset;
                    } else if (preTotalOffset <= 0) {
                        offset = -totalOffset;
                        preTotalOffset = 0;
                    }
                    totalOffset = preTotalOffset;

                    headView.offsetTopAndBottom(offset);
                    contentView.getStick().offsetTopAndBottom(offset);

                }
                lastY = cy;
                break;
            case MotionEvent.ACTION_UP:
                if (onDrag) {

                    if (pullDown && totalOffset >= headView.getHeight() / 2f) {
                        // need to refresh
                        refresh();
                    } else {
                        // need release immediately
                        release();
                    }
                }
                break;
        }
    }

    private void refresh() {
        lastReleaseY = totalOffset;
        refreshAnimator = ValueAnimator.ofInt(totalOffset, maxOffset);
        refreshAnimator.setDuration(500);
        refreshAnimator.setInterpolator(decelerateInterpolator);
        refreshAnimator.addUpdateListener(releaseUpdateListener);
        refreshAnimator.start();

    }

    private void release() {
        lastReleaseY = 0;
        releaseAnimator = ValueAnimator.ofInt(0, -totalOffset);
        releaseAnimator.setDuration(500);
        releaseAnimator.setInterpolator(decelerateInterpolator);
        releaseAnimator.addUpdateListener(releaseUpdateListener);
        releaseAnimator.start();
    }
}
