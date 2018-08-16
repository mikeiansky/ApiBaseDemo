package com.github.neowen.apibasedemo.design;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MyWaveView extends View {

    public static final String TAG = MyWaveView.class.getSimpleName();

    Paint paint;
    Path path;
    Path backPath;
    Paint backPaint;
    float shift;
    float rate = 1.3f;
    float rangeRate = 0.5f;
    float section;
    float sw;
    float wave;

    public MyWaveView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public MyWaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public MyWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    public MyWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        sw = getResources().getDisplayMetrics().density * 137f;

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#bfffffff"));
        path = new Path();

        backPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backPaint.setColor(Color.parseColor("#a0ffffff"));
        backPath = new Path();

    }

    public void setFrontColor(int color) {
        paint.setColor(color);
        invalidate();
    }

    public void setBackColor(int color) {
        backPaint.setColor(color);
        invalidate();
    }

    public void setShift(float shift) {
        this.shift = shift;
        invalidate();
    }

    public void setWave(float wave) {
        this.wave = wave;
        invalidate();
    }

    public float getSectionWidth() {
        return sw;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
//        sw = 1f * width / section;
        section = Math.round(width / sw) + 3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        drawBackWave(canvas);
        drawFrontWave(canvas);
    }

    private void drawBackWave(Canvas canvas) {


        float level = getHeight() / 2f;


        float range = (getHeight() / 2f) * rangeRate * wave;

        float realSection = section;
        float sectionWidth = sw;

        float tx = 0 - shift - 2f * sectionWidth / 3f;

        float rtx = tx % (2f * sectionWidth);

        float startX = 0 + rtx;
        float startY = level;

        backPath.reset();
        backPath.moveTo(startX, startY);
        for (int i = 0; i < realSection; i++) {
            float cx = startX + i * sectionWidth + sectionWidth / 2f;
            float cy = i % 2 == 0 ? level - range : level + range;
            float ex = startX + sectionWidth * (i + 1);
            float ey = level;
            backPath.quadTo(cx, cy, ex, ey);
        }

        backPath.lineTo(getWidth() + sectionWidth, getHeight());
        backPath.lineTo(startX, getHeight());
        backPath.lineTo(startX, startY);
        backPath.close();

        canvas.drawPath(backPath, backPaint);


    }

    private void drawFrontWave(Canvas canvas) {
        float tx = 0 - shift;

        float level = getHeight() * 3 / 5f;


        float range = (getHeight() / 2f) * rangeRate * wave;


        float realSection = section;
        float sectionWidth = sw;
        float rtx = tx % (2f * sectionWidth);

        float startX = 0 + rtx;
        float startY = level;

        path.reset();
        path.moveTo(startX, startY);
        for (int i = 0; i < realSection; i++) {
            float cx = startX + i * sectionWidth + sectionWidth / 2f;
            float cy = i % 2 == 0 ? level - range : level + range;
            float ex = startX + sectionWidth * (i + 1);
            float ey = level;
            path.quadTo(cx, cy, ex, ey);
        }

        path.lineTo(getWidth() + sectionWidth, getHeight());
        path.lineTo(startX, getHeight());
        path.lineTo(startX, startY);

        canvas.drawPath(path, paint);
    }


}
