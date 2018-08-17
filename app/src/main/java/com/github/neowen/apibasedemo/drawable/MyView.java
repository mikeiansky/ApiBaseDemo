package com.github.neowen.apibasedemo.drawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {

    public static final String TAG = MyView.class.getSimpleName();

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint paintS = new Paint(Paint.ANTI_ALIAS_FLAG);
    float radius = 0;
    float sradius = 0;
    float padding = 0;

    public MyView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);


        float density = getResources().getDisplayMetrics().density;
        radius = density * 20;
        padding = density * 50;
        paint.setColor(Color.GREEN);
        paint.setShadowLayer(radius + radius, 0, 0, Color.YELLOW);
//        paint.setShadowLayer(radius, 0, 0, Color.RED);

        paintS.setColor(Color.YELLOW);
        paintS.setShadowLayer(radius + radius + radius, 0, 0, Color.YELLOW);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        RectF rectF = new RectF(padding, padding, getWidth() - padding, getHeight() - padding);

        canvas.drawRoundRect(rectF, radius, radius, paint);

    }

}
