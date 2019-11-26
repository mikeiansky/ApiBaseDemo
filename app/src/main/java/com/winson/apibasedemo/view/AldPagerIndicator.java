package com.winson.apibasedemo.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;



/**
 * Created by wenxiang on 2015/5/22.
 */
public class AldPagerIndicator extends View {

    public static final String TAG = AldPagerIndicator.class.getSimpleName();

    Paint mNormalPaint;
    Paint mSelectPaint;
    int mCount;
    int mSelectPosition;
    RectF rectF;

    public AldPagerIndicator(Context context) {
        super(context);
        init(context, null, 0);
    }

    public AldPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public AldPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AldPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        int normalColor = Color.RED;
        int selectColor = Color.BLACK;
//        int normalColor = getResources().getColor(R.color.indicator_normal);
//        int selectColor = getResources().getColor(R.color.indicator_select);
//        if (attrs != null) {
//            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PagerIndicator);
//            normalColor = a.getColor(R.styleable.PagerIndicator_IndicatorNormal, normalColor);
//            selectColor = a.getColor(R.styleable.PagerIndicator_IndicatorSelect, selectColor);
//            a.recycle();
//        }
        rectF = new RectF();
        mNormalPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        mNormalPaint.setColor(normalColor);

        mSelectPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        mSelectPaint.setColor(selectColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void updateData(int count) {
        mCount = count;
        invalidate();
    }

    public void setSelect(int position) {
        mSelectPosition = position;
        invalidate();
    }

    private float caculateX(float radius, float gap, int position) {
        return radius + (2 * radius + gap) * position;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float tranx = getMeasuredWidth() / 2f;
        float radius = getMeasuredHeight() / 2f;
        float gap = radius;

        float totalWidth = 0f;
        if (mCount <= 1) {
            totalWidth = 2 * radius * mCount + 2 * radius;
        } else {
            totalWidth = 2 * radius * mCount + gap * (mCount - 1) + 2 * radius;
        }
        boolean lastIsSelect = false;
        float startX = (getWidth() - totalWidth) / 2f;
        for (int i = 0; i < mCount; i++) {
            if (i == mSelectPosition) {
                float offset = i != 0 ? radius + gap : 0;
                startX += offset;
                float fl = startX;
                float fr = startX + getHeight() * 2;
                float ft = 0;
                float fb = getHeight();
                rectF.set(fl, ft, fr, fb);
                canvas.drawRoundRect(rectF, radius, radius, mSelectPaint);
                lastIsSelect = true;
            } else {
                if (lastIsSelect) {
                    float rgap = gap;
                    float rradius = radius * 5;
                    startX += rradius + rgap;
                    canvas.drawCircle(startX, radius, radius, mNormalPaint);
                } else {
                    float rgap = i != 0 ? gap : 0;
                    float rradius = i != 0 ? 2 * radius : radius;
                    startX += rradius + rgap;
                    canvas.drawCircle(startX, radius, radius, mNormalPaint);
                }
                lastIsSelect = false;
            }
        }
    }
}
