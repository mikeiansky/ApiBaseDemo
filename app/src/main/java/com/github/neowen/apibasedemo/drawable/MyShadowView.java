package com.github.neowen.apibasedemo.drawable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
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
    RectF selectRect, normalRect;
    Matrix normalMatrix, selectMatrix;
    int selectPadding, normalPadding;
    float radius;
    float border;
    private boolean special;
    private Bitmap selectBitmap;
    private Bitmap normalBitmap;
    BitmapShader bitmapShader;
    int resizeWidth;

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

        ShadowDrawable sd = new ShadowDrawable(this);
        setBackgroundDrawable(sd);

        float dpi = getResources().getDisplayMetrics().density;
        radius = (int) (dpi * 20);
        border = (int) (dpi * 10);

        selectPadding = (int) (dpi * 30);
        normalPadding = (int) (dpi * 40);

        specialPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        specialPaint.setStyle(Paint.Style.FILL);
        specialPaint.setColor(Color.BLACK);
        specialPaint.setShadowLayer(radius, 0, 0, Color.YELLOW);

        normalPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        normalMatrix = new Matrix();
        selectMatrix = new Matrix();

        selectRect = new RectF();
        normalRect = new RectF();

//        Log.d(TAG, "normal bitmap width : " + normalBitmap.getWidth() + " , height : " + normalBitmap.getHeight());

    }

    public void setSelectBitmap(Bitmap bitmap) {
        selectBitmap = convert(bitmap, true);
        invalidate();
    }

    public void setNormalBitmap(Bitmap bitmap) {
        normalBitmap = convert(bitmap, false);
        invalidate();
    }

    private Bitmap convert(Bitmap bitmap, boolean widthBorder) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        RectF rectF1 = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());


        float r = border / 2f;
        RectF rectF = new RectF();
        rectF.set(rectF1);
        rectF.inset(r, r);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(bitmapShader);

        Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(border);
        borderPaint.setColor(Color.YELLOW);

        canvas.drawRoundRect(rectF1, radius + r, radius + r, paint);
        if (widthBorder) {
            canvas.drawRoundRect(rectF, radius, radius, borderPaint);
        }

        return output;
    }

    public void setSpecial(boolean special) {
        this.special = special;
        invalidate();
    }

    private void resize(int width){

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        resize(width);

        // update normal layout
        normalRect.left = normalPadding;
        normalRect.top = normalPadding;
        normalRect.right = width - normalPadding;
        normalRect.bottom = height - normalPadding;
        if (normalBitmap != null) {
            float nsx = 1f * normalRect.width() / normalBitmap.getWidth();
            float nsy = 1f * normalRect.height() / normalBitmap.getHeight();
            normalMatrix.reset();
            normalMatrix.preTranslate(normalRect.left, normalRect.top);
            normalMatrix.preScale(nsx, nsy);
        }

        // update select layout
        selectRect.left = selectPadding;
        selectRect.top = selectPadding;
        selectRect.right = width - selectPadding;
        selectRect.bottom = height - selectPadding;
        if (selectBitmap != null) {
            float ssx = 1f * selectRect.width() / selectBitmap.getWidth();
            float ssy = 1f * selectRect.height() / selectBitmap.getHeight();
            selectMatrix.reset();
            selectMatrix.preTranslate(selectRect.left, selectRect.top);
            selectMatrix.preScale(ssx, ssy);
        }



    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw background
        if (special) {
            canvas.drawRoundRect(selectRect, radius, radius, specialPaint);
            if (selectBitmap != null) {
                canvas.drawBitmap(selectBitmap, selectMatrix, normalPaint);
            }
        } else {
            if (normalBitmap != null) {
                canvas.drawBitmap(normalBitmap, normalMatrix, normalPaint);
            }
        }


    }

}
