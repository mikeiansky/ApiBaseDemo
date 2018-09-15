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

public class PullRefreshLayout extends FrameLayout {

    public static final String TAG = PullRefreshLayout.class.getSimpleName();

    public interface OnRefreshListener {

        /**
         * When touch event release and the condition can refresh ,this method will be call.
         */
        void onRefresh();


    }

    public static class HeadWatcherWrapper implements PullRefreshHeadWatcher {

        private View headView;

        public HeadWatcherWrapper(View headView) {
            this.headView = headView;
        }

        @Override
        public void onPullProgressUpdate(int progress) {

        }

        @Override
        public void onRefresh() {

        }

        @Override
        public void onRefreshComplete() {

        }

        @Override
        public View getStick() {
            return headView;
        }
    }

    public static class ContentWatcherWrapper implements PullRefreshContentWatcher {

        View contentView;

        public ContentWatcherWrapper(View contentView) {
            this.contentView = contentView;
            contentView.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }

        @Override
        public View getStick() {
            return contentView;
        }

        @Override
        public boolean onTop() {
            return true;
        }

        @Override
        public void setOnDrag(boolean onDrag) {

        }

    }

    private PullRefreshContentWatcher contentWatcher;
    private PullRefreshHeadWatcher headWatcher;
    private ValueAnimator releaseAnimator, refreshAnimator;
    private OnRefreshListener refreshListener;
    private int touchSlop;
    private int duration = 250;
    int checkStartY;
    int lastY;
    int maxOffset;
    int totalOffset;
    int pullProgress;
    boolean onDrag, pullDown, onMove, onRefresh, onTouch, releasePressed;
    int lastReleaseY;
    float resistance = 2f;
    DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator();
    ValueAnimator.AnimatorUpdateListener releaseUpdateListener = new ValueAnimator.AnimatorUpdateListener() {

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            int cy = (int) animation.getAnimatedValue();

            int offset = cy - lastReleaseY;
            headWatcher.getStick().offsetTopAndBottom(offset);
            contentWatcher.getStick().offsetTopAndBottom(offset);

            totalOffset += offset;
            lastReleaseY = cy;

            computeRefreshProgress();
        }

    };

    private Runnable releaseRun = new Runnable() {
        @Override
        public void run() {
            release();
        }
    };

    public PullRefreshLayout(@NonNull Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public PullRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public PullRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PullRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    public void setRefreshListener(OnRefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

    public void addHeadWatcher(PullRefreshHeadWatcher headWatcher) {
        if (headWatcher.getStick() == null) {
            throw new IllegalArgumentException("Stick view must not be null!");
        }
        this.headWatcher = headWatcher;
        addView(headWatcher.getStick());
    }

    public void addHeadView(View headView) {
        addHeadWatcher(new HeadWatcherWrapper(headView));
    }

    public void addContentWatcher(PullRefreshContentWatcher contentView) {
        if (contentView.getStick() == null) {
            throw new IllegalArgumentException("Stick view must not be null!");
        }
        this.contentWatcher = contentView;
        addView(contentView.getStick());
    }

    public void addContentView(View contentView) {
        addContentWatcher(new ContentWatcherWrapper(contentView));
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
        if (headWatcher != null) {
            LayoutParams lp = (LayoutParams) headWatcher.getStick().getLayoutParams();
            lp.topMargin = -headWatcher.getStick().getMeasuredHeight();
            maxOffset = Math.abs(lp.topMargin);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (headWatcher != null && contentWatcher != null) {
            detailMotionEvent(ev);
        }
        boolean result = super.dispatchTouchEvent(ev);
        return result;
    }

    public void refreshComplete() {
        onRefresh = false;
        headWatcher.onRefreshComplete();
        if (!onTouch) {
            release();
        }
    }

    public void refreshComplete(int delay) {
        onRefresh = false;
        headWatcher.onRefreshComplete();
        if (!onTouch) {
            postDelayed(releaseRun, delay);
        }
    }

    private void computeRefreshProgress() {
        int pullProgress = Math.round((totalOffset * 1f / maxOffset) * 100);
        if (pullProgress < 0) {
            pullProgress = 0;
        }
        if (pullProgress > 100) {
            pullProgress = 100;
        }

        if (this.pullProgress != pullProgress) {
            headWatcher.onPullProgressUpdate(pullProgress);
        }

        this.pullProgress = pullProgress;

    }

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
                onDrag = contentWatcher.onTop()
                        && (totalOffset > 0 || offset > 0);
                contentWatcher.setOnDrag(onDrag);

                if (Math.abs(cy - checkStartY) >= touchSlop) {
                    onMove = true;
                }

                if (onDrag) {
                    computeRefreshProgress();
                    if (!releasePressed) {
                        contentWatcher.getStick().setPressed(false);
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

                    headWatcher.getStick().offsetTopAndBottom(offset);
                    contentWatcher.getStick().offsetTopAndBottom(offset);

                }
                lastY = cy;
                break;
            case MotionEvent.ACTION_UP:
                onTouch = false;
                if (onDrag) {
                    if (!onMove) {
                        contentWatcher.setOnDrag(false);
                    }
                    if (onRefresh) {
                        // need release immediately
                        if (totalOffset >= headWatcher.getStick().getHeight()) {
                            // need to release
                            release();
                        }
                    } else {
                        if (totalOffset >= headWatcher.getStick().getHeight() * 2f / 3f
                                || (totalOffset >= headWatcher.getStick().getHeight() / 2f && pullDown)) {
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
            headWatcher.onRefresh();
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
