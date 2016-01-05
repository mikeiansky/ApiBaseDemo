package com.github.neowen.apibasedemo.design.swipebacklayout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

/**
 * Created by ZWX on 15/10/15.
 */
public class SwipeBackLayout extends FrameLayout {

    public static final String TAG = SwipeBackLayout.class.getSimpleName();

    private View mDragLeft, mDragTop, mDragRight, mDragBottom, mDragContent;
    private ViewDragHelper mViewDragHelper;

    private float mLastTouchX;
    private float mPagingTouchSlop;
    private boolean mChecked;

    public SwipeBackLayout(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        mViewDragHelper = ViewDragHelper.create(this, mCallback);

        ViewConfiguration vc = ViewConfiguration.get(context);
        mPagingTouchSlop = vc.getScaledPagingTouchSlop();
    }

    public void setDragLeft(View mDragLeft) {
        this.mDragLeft = mDragLeft;
        if (mDragLeft.getParent() != this) {
            addView(mDragLeft);
        }
    }

    public void setDragTop(View mDragTop) {
        this.mDragTop = mDragTop;
        if (mDragTop.getParent() != this) {
            addView(mDragTop);
        }
    }

    public void setDragRight(View mDragRight) {
        this.mDragRight = mDragRight;
        if (mDragRight.getParent() != this) {
            addView(mDragRight);
        }
    }

    public void setDragBottom(View mDragBottom) {
        this.mDragBottom = mDragBottom;
        if (mDragBottom.getParent() != this) {
            addView(mDragBottom);
        }
    }

    public void setDragContent(View mDragContent) {
        this.mDragContent = mDragContent;
        if (mDragContent.getParent() != this) {
            addView(mDragContent);
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mDragContent || child == mDragRight;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            if (isSwipeEnable()) {
                if (changedView == mDragContent) {
                    mDragRight.offsetLeftAndRight(dx);
                    mDragRight.offsetTopAndBottom(dy);
                } else {
                    mDragContent.offsetLeftAndRight(dx);
                    mDragContent.offsetTopAndBottom(dy);
                }
                invalidate();
            }
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (!isSwipeEnable()) {
                return 0;
            }
            int width = getWidth();
            int min = mDragRight.getWidth();

            if (child == mDragContent) {
                if (left > 0) {
                    left = 0;
                } else if (left < -min) {
                    left = -min;
                }
            } else {
                int rightMin = width - min;
                if (left > width) {
                    left = width;
                } else if (left < rightMin) {
                    left = rightMin;
                }
            }
            return left;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if (!isSwipeEnable()) {
                return;
            }
            if (xvel <= -500) {
                open(true);
            } else if (xvel > 500) {
                close(true);
            } else {
                int left = mDragContent.getLeft();
                if (Math.abs(left) < mDragRight.getWidth() / 2f) {
                    close(true);
                } else {
                    open(true);
                }
            }
        }
    };

    private boolean isSwipeEnable() {
        return mDragContent != null && mDragRight != null;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        close(false);
    }

    public void close(boolean animation) {
        if (!isSwipeEnable()) {
            return;
        }
        if (!animation) {
            int dl = getWidth() - mDragRight.getLeft();
            mDragRight.offsetLeftAndRight(dl);
            mDragContent.offsetLeftAndRight(0 - mDragContent.getLeft());
        } else {
            mViewDragHelper.smoothSlideViewTo(mDragRight, getWidth(), mDragRight.getTop());
        }
        invalidate();
    }

    public void open(boolean animation) {
        if (!isSwipeEnable()) {
            return;
        }
        if (!animation) {
            int dl = getWidth() - mDragRight.getLeft() - mDragRight.getWidth();
            mDragRight.offsetLeftAndRight(dl);
            mDragContent.offsetLeftAndRight(-mDragRight.getWidth() - mDragContent.getLeft());
        } else {
            mViewDragHelper.smoothSlideViewTo(mDragRight, getWidth() - mDragRight.getWidth(), mDragRight.getTop());
        }
        invalidate();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);

        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:

                mChecked = true;
                mLastTouchX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                if (mChecked) {
                    float cx = event.getX();
                    if (Math.abs(cx - mLastTouchX) > mPagingTouchSlop) {
                        if (getParent() != null) {
                            getParent().requestDisallowInterceptTouchEvent(true);
                        }
                        mChecked = false;
                    }
                }

                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }


}
