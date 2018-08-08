package com.github.neowen.apibasedemo.drawable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.github.neowen.apibasedemo.R;

public class MyShadowView extends View {

    public static final String TAG = MyShadowView.class.getSimpleName();

    Paint specialPaint;
    Paint normalPaint;
    Rect rect;
    RectF rectF;
    Matrix matrix;
    int padding;
    int radius;
    private boolean special;
    private float roundRadius;
    private float radiusX, radiusY;
    private Bitmap normalBitmap, loadBitmap, localBitmap;

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
        radius = (int) (dpi * 20);
        padding = (int) (dpi * 40);
        roundRadius = dpi * 30;

        specialPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        specialPaint.setColor(Color.YELLOW);
        specialPaint.setShadowLayer(radius, 0, 0, Color.YELLOW);

        normalPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        matrix = new Matrix();

        rect = new Rect();
        rectF = new RectF(rect);

        Bitmap originBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.car);
        normalBitmap = convertRoundBitmap(originBitmap);

        Log.d(TAG, "normal bitmap width : " + normalBitmap.getWidth() + " , height : " + normalBitmap.getHeight());


    }

    private Bitmap convertRoundBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        Paint boardPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        boardPaint.setStyle(Paint.Style.STROKE);
        boardPaint.setColor(Color.YELLOW);
        boardPaint.setStrokeWidth(roundRadius);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Rect r1 = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rf = new RectF(r1);

        canvas.drawRoundRect(rf, roundRadius, roundRadius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, r1, rf, paint);
//        canvas.drawRoundRect(rf, roundRadius, roundRadius, boardPaint);

        return output;
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
        rect.left = padding;
        rect.top = padding;
        rect.right = width - padding;
        rect.bottom = height - padding;

        rectF.set(rect);

        float sx = 1f * rect.width() / normalBitmap.getWidth();
        float sy = 1f * rect.height() / normalBitmap.getHeight();

        radiusX = roundRadius * sx;
        radiusY = roundRadius * sy;

        matrix.reset();
        matrix.preTranslate(rect.left, rect.top);
        matrix.preScale(sx, sy);
        Log.d(TAG, "sx : " + sx + " , sy : " + sy + " , left : " + rect.left + " , top : " + rect.top);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw background
        if (special) {
            canvas.drawRoundRect(rectF, radiusX, radiusY, specialPaint);
        }

        // draw bitmap
        canvas.drawBitmap(normalBitmap, matrix, normalPaint);

        // draw stroke

    }

}
