package com.github.neowen.apibasedemo.support.photo;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

import com.github.neowen.apibasedemo.R;


/**
 * Created by ZWX on 15/9/6.
 */
public class MySwipeBackLayout extends FrameLayout implements ValueAnimator.AnimatorUpdateListener,
        ValueAnimator.AnimatorListener {

    public static final String TAG = MySwipeBackLayout.class.getSimpleName();

    public interface IgnoreCheckTouch {

    }

    interface OnViewRemovedListener {

        void onChildViewRemoved(View removedView);

        void onSelfRemoved(MySwipeBackLayout swipeBackLayout);
    }

    int mChildLeft;
    int mChildTop;
    int mChildRight;
    int mChildBottom;

    int mBehindChildLeft;
    int mBehindChildTop;
    int mBehindChildRight;
    int mBehindChildBottom;
    int mBehindWidth;

    int mLayoutLeft;
    int mChildCount = -1;
    int mDuration;
    int mEdgeX;
    int mMoveX;
    float mLastTouchX;
    float mDensity;
    float mInterceptLastX;

    boolean mOnAnimator;
    boolean mMoveSelf;
    boolean mFinish;
    boolean mSelfFinish;
    boolean mSlideShowTopView;
    boolean mMoveBehind;
    boolean mEnableSlideFinish;
    boolean mSlideShowSelf;
    boolean mFilterTouch;

    View mTopView;
    GestureDetector mGestureDetector;
    ValueAnimator mAnimator;
    Interpolator mInterpolator;
    Drawable mShadowDrawable;
    Rect mShadowRect;
    OnViewRemovedListener mOnViewRemovedListener;

    OnTouchListener mInterruptTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return true;
        }
    };

    public MySwipeBackLayout(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public MySwipeBackLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public MySwipeBackLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MySwipeBackLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        mDensity = getResources().getDisplayMetrics().density;

        mEnableSlideFinish = true;
        mMoveBehind = false;
        mFilterTouch = false;
        mEdgeX = getSize(10);
        mMoveX = getSize(20);
        mDuration = 250;

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MySwipeBackLayout);
            mEnableSlideFinish = a.getBoolean(R.styleable.MySwipeBackLayout_slide_finish, true);
            mMoveBehind = a.getBoolean(R.styleable.MySwipeBackLayout_move_behind, mMoveBehind);
            mFilterTouch = a.getBoolean(R.styleable.MySwipeBackLayout_filter_touch, mFilterTouch);
            mEdgeX = a.getDimensionPixelSize(R.styleable.MySwipeBackLayout_edge, mEdgeX);
            mMoveX = a.getDimensionPixelSize(R.styleable.MySwipeBackLayout_move_check, mMoveX);
            mDuration = a.getInteger(R.styleable.MySwipeBackLayout_move_duration, mDuration);
            mShadowDrawable = a.getDrawable(R.styleable.MySwipeBackLayout_shadow_drawable);
            a.recycle();
        }

        mGestureDetector = new GestureDetector(context, mOnGestureListener);
        mAnimator = new ValueAnimator();
        mInterpolator = new DecelerateInterpolator();
        mAnimator.setDuration(mDuration);
        mAnimator.setInterpolator(mInterpolator);
        mAnimator.addUpdateListener(this);
        mAnimator.addListener(this);

        mShadowRect = new Rect();
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (mFilterTouch) {
            if (!viewNotNeedAddTouchListener(child)) {
                child.setOnTouchListener(mInterruptTouchListener);
            }
        }
        super.addView(child, index, params);
    }

    public boolean isOnAnimator() {
        return mOnAnimator;
    }

    public boolean canRemoveSelf() {
        return getChildCount() > 1;
    }

    private boolean viewNotNeedAddTouchListener(View child) {
        return child instanceof IgnoreCheckTouch
                || child instanceof AdapterView
                || child instanceof ScrollView
                || child instanceof HorizontalScrollView;
    }

    void setOnViewRemovedListener(OnViewRemovedListener onViewRemovedListener) {
        mOnViewRemovedListener = onViewRemovedListener;
    }

    public int getSize(int size) {
        return (int) Math.ceil(mDensity * size);
    }

    public void setShadowDrawable(Drawable shadowDrawable) {
        mShadowDrawable = shadowDrawable;
    }

    GestureDetector.OnGestureListener mOnGestureListener = new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            mLastTouchX = e.getX();
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (mLastTouchX == Integer.MIN_VALUE) {
                mLastTouchX = e2.getX();
            }
            float tx = e2.getX();
            float offsetX = tx - mLastTouchX;
            mLastTouchX = tx;
            layoutChildByOffset((int) Math.ceil(offsetX), 0);
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (Math.abs(velocityX) > Math.abs(velocityY)) {
                if (velocityX > 300) {
                    resetChild(true);
                    return true;
                }
            }
            return false;
        }
    };

    public boolean isMoreThanHalf() {
        return mLayoutLeft > (getWidth() / 2f);
    }

    private boolean checkCanTouch() {
        if (!mEnableSlideFinish) {
            int count = getChildCount();
            if (count <= 1) {
                return false;
            }
            return true;
        }
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean result = super.dispatchTouchEvent(ev);
        return result;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mOnAnimator || mSelfFinish) {
            return true;
        }
        if (!checkCanTouch()) {
            return false;
        }
        boolean result = super.onInterceptTouchEvent(ev);
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                float tx = ev.getX();
                if (getChildCount() > 1) {
                    mMoveSelf = false;
                } else {
                    mMoveSelf = true;
                }
                if (tx <= mEdgeX) {
                    return true;
                }
                mInterceptLastX = tx;
                break;
            case MotionEvent.ACTION_MOVE:
                float mx = ev.getX();
                if (Math.abs(mx - mInterceptLastX) > mMoveX) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                break;
        }
        return result;
    }

    public void enableSlideFinish(boolean enable) {
        mEnableSlideFinish = enable;
    }

    public boolean isEnableSlideFinish() {
        return mEnableSlideFinish;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mOnAnimator || mSelfFinish) {
            return false;
        }
        if (!checkCanTouch()) {
            return false;
        }
        boolean analyse = mGestureDetector.onTouchEvent(ev);
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (!analyse) {
                    resetChild(isMoreThanHalf());
                }
                mLastTouchX = Integer.MIN_VALUE;
                break;
        }
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        if (mMoveSelf) {
            canvas.save();
            canvas.translate(mLayoutLeft, 0);
            super.draw(canvas);
            drawShadow(this, canvas);
            canvas.restore();
        } else {
            super.draw(canvas);
//            int childCount = getChildCount();
//            if (childCount > 1) {
//                drawShadow(getChildAt(childCount - 1), canvas);
//            }
        }
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        if (mMoveSelf) {
            return super.drawChild(canvas, child, drawingTime);
        }
        if (child == mTopView) {
            canvas.save();
            canvas.translate(mLayoutLeft, 0);
            boolean result = super.drawChild(canvas, child, drawingTime);
            drawShadow(child, canvas);
            canvas.restore();
            return result;
        } else {
            return super.drawChild(canvas, child, drawingTime);
        }
    }

    private void drawShadow(View bind, Canvas canvas) {
        if (mShadowDrawable != null) {
            bind.getHitRect(mShadowRect);
            int width = mShadowDrawable.getIntrinsicWidth();
            mShadowDrawable.setBounds(
                    mShadowRect.left - width,
                    mShadowRect.top,
                    mShadowRect.left,
                    mShadowRect.bottom
            );
            mShadowDrawable.draw(canvas);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int childCount = getChildCount();
        if (mChildCount != childCount) {
            saveTopChildInfo();
            mChildCount = childCount;
            if (mSlideShowSelf || (childCount > 1 && mSlideShowTopView)) {
                slideShowTop();
            }
        }
    }

    private void slideShowTop() {
        if (mOnAnimator) {
            return;
        }
        mSlideShowSelf = false;
        mSlideShowTopView = false;
        mFinish = false;
        mAnimator.setIntValues(getWidth(), 0);
        mAnimator.start();
    }

    private void resetChild(boolean finish) {
        if (mMoveSelf) {
            mSelfFinish = finish;
        } else {
            mFinish = finish;
        }
        mAnimator.setIntValues(mLayoutLeft, finish ? getWidth() : 0);
        mAnimator.start();
    }

    private void saveTopChildInfo() {
        int childCount = getChildCount();
        if (childCount > 1) {

            mTopView = getChildAt(childCount - 1);

            View child = getChildAt(childCount - 1);
            mChildLeft = child.getLeft();
            mChildRight = child.getRight();
            mChildTop = child.getTop();
            mChildBottom = child.getBottom();

            View behind = getChildAt(childCount - 2);
            mBehindChildLeft = behind.getLeft();
            mBehindChildRight = behind.getRight();
            mBehindChildTop = behind.getTop();
            mBehindChildBottom = behind.getBottom();
            mBehindWidth = mBehindChildRight - mBehindChildLeft;

            mMoveSelf = false;
        } else {
            mChildLeft = getLeft();
            mChildRight = getRight();
            mChildTop = getTop();
            mChildBottom = getBottom();
            mMoveSelf = true;

            mTopView = null;
        }
    }

    private void layoutChildByPosition(int lx, int ly) {
        mLayoutLeft = lx;
        layoutChild();
    }

    private void layoutChildByOffset(int offsetX, int offsetY) {
        if (offsetX == 0) {
            return;
        }
        int changeValue = mLayoutLeft + offsetX;
        if (changeValue < 0) {
            changeValue = 0;
        } else if (changeValue > getWidth()) {
            changeValue = getWidth();
        }
        if (changeValue != mLayoutLeft) {
            mLayoutLeft = changeValue;
            layoutChild();
        }
    }

    private void layoutChild() {
        invalidate();
    }

    void layoutByOther(float offsetX) {
        mMoveSelf = true;
        float halfWidth = getWidth() / 3f;
        mLayoutLeft = (int) (-halfWidth + halfWidth * offsetX);
        invalidate();
    }

    public void slideShowTopView() {
        mSlideShowTopView = true;
    }

    void slideShow() {
        if (!mEnableSlideFinish) {
            return;
        }
        mSlideShowSelf = true;
    }

    void slideRemove() {
        if (mOnAnimator) {
            return;
        }
        int childCount = getChildCount();
        if (childCount <= 1) {
            // remove it self;
            mMoveSelf = true;
            mSelfFinish = true;
            if (!mEnableSlideFinish) {
                if (mOnViewRemovedListener != null) {
                    mOnViewRemovedListener.onSelfRemoved(this);
                }
                return;
            }
        } else {
            // remove top view
            mSelfFinish = false;
            mMoveSelf = false;
        }
        mFinish = true;
        mAnimator.setIntValues(0, getWidth());
        mAnimator.start();
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        int value = (int) animation.getAnimatedValue();
        layoutChildByPosition(value, 0);
    }

    @Override
    public void onAnimationStart(Animator animation) {
        mOnAnimator = true;
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        mOnAnimator = false;
        if (mSelfFinish) {
            ViewParent parent = getParent();
            if (parent instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) parent;
                group.removeView(this);
            }
            if (mOnViewRemovedListener != null) {
                mOnViewRemovedListener.onSelfRemoved(this);
            }
            return;
        }
        if (mFinish) {

            mLayoutLeft = 0;
            int childCount = getChildCount();

            if (childCount > 1) {
                View child = getChildAt(childCount - 1);
                removeView(child);
                if (mOnViewRemovedListener != null) {
                    mOnViewRemovedListener.onChildViewRemoved(child);
                }
            }
        }
    }

    @Override
    public void onAnimationCancel(Animator animation) {
        mOnAnimator = false;
    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
