package com.github.neowen.apibasedemo.drawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

public class MyShadowView extends View {

    public static final String TAG = MyShadowView.class.getSimpleName();

    Paint specialPaint;
    Paint normalPaint;
    RectF rectF;
    int padding;
    int radius;
    private boolean special;

    public MyShadowView(@NonNull Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public MyShadowView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public MyShadowView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyShadowView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);  // 关闭硬件加速

        float dpi = getResources().getDisplayMetrics().density;
        radius = (int) (dpi * 10);
        padding = (int) (dpi * 20);

        specialPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        specialPaint.setColor(Color.RED);
        specialPaint.setShadowLayer(radius, 0, 0, Color.BLACK);

        normalPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        rectF = new RectF();


    }

    public void setSpecial(boolean special) {
        this.special = special;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

//        rectF.left = padding;
//        rectF.top = padding;
//        rectF.right = width - padding;
//        rectF.bottom = height - padding;

        rectF.left = padding;
        rectF.top = padding;
        rectF.right = width - padding;
        rectF.bottom = height - padding;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (special) {
            canvas.drawRect(rectF, specialPaint);
        } else {
            canvas.drawRect(rectF, normalPaint);
        }

    }

}
