package com.winson.apibasedemo.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


/**
 * Created by wenxiang on 2015/5/22.
 */
public class AldViewPager extends ViewPager {

    public static final String TAG = AldViewPager.class.getSimpleName();

    private InnerAdapter mAdapter;
    private PagerAdapter mOriginAdapter;
    private int mIndex = 1;
    private int mDuration = 1000*60*60;
    private Handler mHandler;
    private AldPagerIndicator mPagerIndicator;

    Runnable mImageSlideRun = new Runnable() {
        @Override
        public void run() {
            if (mOriginAdapter == null || mOriginAdapter.getCount() <= 1) {
                return;
            }
            mIndex++;
            mIndex = mIndex % (mOriginAdapter.getCount() + 2);
            if (mIndex == 0) {
                mIndex = 1;
            }
            setCurrentItem(mIndex, true);
            if (mIndex == (mOriginAdapter.getCount() + 1)) {
                mIndex = 1;
            }
            mHandler.postDelayed(this, mDuration);
        }
    };

    public AldViewPager(Context context) {
        this(context, null);
    }

    public AldViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnPageChangeListener(new InnerPagerChangeListener(null));
        mHandler = new Handler();
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        super.setOnPageChangeListener(new InnerPagerChangeListener(listener));
    }

    public void setAldPagerIndicator(AldPagerIndicator pagerIndicator) {
        mPagerIndicator = pagerIndicator;
        if (mOriginAdapter != null) {
            mPagerIndicator.updateData(mOriginAdapter.getCount());
        }
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        mOriginAdapter = adapter;
        mAdapter = new InnerAdapter(adapter);
        super.setAdapter(mAdapter);
        setCurrentItem(1);
        updatePagerIndicatorCount();
    }

    public void notifyDataSetChanged() {
        if (mOriginAdapter != null) {
            mOriginAdapter.notifyDataSetChanged();
        }
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
            setCurrentItem(1, false);
            mIndex = 1;
            runImageSlide();
            updatePagerIndicatorCount();
        }
    }

    private void updatePagerIndicatorCount() {
        if (mPagerIndicator != null) {
            mPagerIndicator.updateData(mOriginAdapter.getCount());
        }
    }

    public void stopImageSlide() {
        mHandler.removeCallbacks(mImageSlideRun);
    }

    public void runImageSlide() {
        mHandler.removeCallbacks(mImageSlideRun);
        mHandler.postDelayed(mImageSlideRun, mDuration);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean ret = super.dispatchTouchEvent(ev);
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                stopImageSlide();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIndex = getCurrentItem();
                runImageSlide();
                break;
        }
        return ret;
    }

    private class InnerAdapter extends PagerAdapter {

        PagerAdapter mPageAdapter;

        public InnerAdapter(PagerAdapter mPageAdapter) {
            this.mPageAdapter = mPageAdapter;
        }

        @Override
        public int getCount() {
            if (mPageAdapter.getCount() == 0) {
                return 0;
            }
            return mPageAdapter.getCount() + 2;
        }

        public int getPosition(int position) {
            if (position == 0) {
                position = mPageAdapter.getCount() - 1;
            } else if (position == getCount() - 1) {
                position = 0;
            } else {
                position -= 1;
            }
            return position;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return mPageAdapter.instantiateItem(container, getPosition(position));
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            mPageAdapter.destroyItem(container, position, object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return mPageAdapter.isViewFromObject(view, object);
        }
    }

    private int convertPosition(int position) {
        if (position == 0) {
            position = mAdapter.getCount() - 2;
        } else if (position == mAdapter.getCount() - 1) {
            position = 1;
        }
        return position;
    }

    private class InnerPagerChangeListener implements OnPageChangeListener {

        OnPageChangeListener mListener;

        public InnerPagerChangeListener(OnPageChangeListener listener) {
            mListener = listener;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (mListener != null) {
                mListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageSelected(int position) {
            position = convertPosition(position);
            int select = mAdapter.getPosition(position);
            if (mListener != null) {
                mListener.onPageSelected(select);
            }
            if (mPagerIndicator != null) {
                mPagerIndicator.setSelect(select);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (mListener != null) {
                mListener.onPageScrollStateChanged(state);
            }
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                if (mAdapter == null) {
                    return;
                }
                int currentItem = getCurrentItem();
                int changeItem = convertPosition(currentItem);
                if (currentItem != changeItem) {
                    setCurrentItem(changeItem, false);
                }
            }
        }
    }

}
