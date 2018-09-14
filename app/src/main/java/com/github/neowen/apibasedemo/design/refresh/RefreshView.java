package com.github.neowen.apibasedemo.design.refresh;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

public class RefreshView extends FrameLayout {

    public static final String TAG = RefreshView.class.getSimpleName();

    public interface OnRefreshListener {
        void onRefresh();
    }

    private View headView;
    private PullRefreshContentWatcher contentView;
    private ValueAnimator releaseAnimator, refreshAnimator;
    private OnRefreshListener refreshListener;
    private int touchSlop;
    private int duration = 250;
    private Runnable releaseRun = new Runnable() {
        @Override
        public void run() {
            release();
        }
    };

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
        touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    public void setRefreshListener(OnRefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

    public void addHeadView(View headView) {
        this.headView = headView;
        addView(this.headView);
    }

    public void addContentView(PullRefreshContentWatcher contentView) {
        this.contentView = contentView;
        addView(contentView.getStick());
    }

    public void setResistance(float resistance) {
        this.resistance = resistance;
    }

    public void setAnimatorDuration(int duration) {
        this.duration = duration;
    }

    private void cancelAnimator() {
        if (refreshAnimator != null) {
            refreshAnimator.cancel();
        }
        if (releaseAnimator != null) {
            releaseAnimator.cancel();
        }
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
        if (headView != null && contentView != null) {
            detailMotionEvent(ev);
        }
        boolean result = super.dispatchTouchEvent(ev);
        return result;
    }

    public void refreshComplete() {
        onRefresh = false;
        if (!onTouch) {
            release();
        }
    }

    public void refreshComplete(int delay) {
        onRefresh = false;
        if (!onTouch) {
            postDelayed(releaseRun, delay);
        }
    }

    int checkStartY;
    int lastY;
    int maxOffset;
    int totalOffset;
    boolean onDrag, pullDown, onMove, onRefresh, onTouch, releasePressed;
    int lastReleaseY;
    float resistance = 2f;

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
                onTouch = true;
                lastY = (int) event.getRawY();
                checkStartY = lastY;
                onMove = false;
                if (releaseAnimator != null) {
                    releaseAnimator.cancel();
                }
                removeCallbacks(releaseRun);
                onMove = false;
                releasePressed = false;
                break;
            case MotionEvent.ACTION_MOVE:
                int cy = (int) event.getRawY();
                int offset = (int) ((cy - lastY) / resistance);
                onDrag = contentView.onTop()
                        && (totalOffset > 0 || offset > 0);
                contentView.setOnDrag(onDrag);

                if (Math.abs(cy - checkStartY) >= touchSlop) {
                    onMove = true;
                }

                if (onDrag) {
                    if (!releasePressed) {
                        contentView.getStick().setPressed(false);
                        releasePressed = true;
                    }
                    pullDown = offset > 0;
                    int preTotalOffset = totalOffset + offset;
                    if (preTotalOffset >= maxOffset) {
//                        offset = maxOffset - totalOffset;
//                        preTotalOffset = maxOffset;
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
                onTouch = false;
                if (onDrag) {
                    if (!onMove) {
                        contentView.setOnDrag(false);
                    }
                    if (onRefresh) {
                        // need release immediately
                        if (totalOffset >= headView.getHeight()) {
                            // need to release
                            release();
                        }
                    } else {
                        if (totalOffset >= headView.getHeight() * 2f / 3f
                                || (totalOffset >= headView.getHeight() / 2f && pullDown)) {
                            // need to refresh
                            refresh();
                        } else {
                            // need release immediately
                            release();
                        }
                    }
                }
                break;
        }
    }

    private void refresh() {
        if (refreshListener != null) {
            if (releaseAnimator != null) {
                releaseAnimator.cancel();
            }
            onRefresh = true;
            refreshListener.onRefresh();
            lastReleaseY = totalOffset;
            refreshAnimator = ValueAnimator.ofInt(totalOffset, maxOffset);
            refreshAnimator.setDuration(duration);
            refreshAnimator.setInterpolator(decelerateInterpolator);
            refreshAnimator.addUpdateListener(releaseUpdateListener);
            refreshAnimator.start();
        } else {
            release();
        }
    }

    private void release() {
        if (refreshAnimator != null) {
            refreshAnimator.cancel();
        }
        if (onRefresh) {
            if (maxOffset == totalOffset) {
                return;
            }
            lastReleaseY = totalOffset;
            releaseAnimator = ValueAnimator.ofInt(totalOffset, maxOffset);
        } else {
            if (totalOffset == 0) {
                return;
            }
            lastReleaseY = 0;
            releaseAnimator = ValueAnimator.ofInt(0, -totalOffset);
        }
        releaseAnimator.setDuration(duration);
        releaseAnimator.setInterpolator(decelerateInterpolator);
        releaseAnimator.addUpdateListener(releaseUpdateListener);
        releaseAnimator.start();
    }
}
