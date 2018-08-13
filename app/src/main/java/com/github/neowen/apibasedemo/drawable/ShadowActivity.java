package com.github.neowen.apibasedemo.drawable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.github.neowen.apibasedemo.BaseActivity;
import com.github.neowen.apibasedemo.R;

public class ShadowActivity extends BaseActivity {

    private static final String TAG = ShadowActivity.class.getSimpleName();

    ImageView roundImage;
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    PorterDuffXfermode porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shadow_layout);

        roundImage = (ImageView) findViewById(R.id.round_image);
        MyShadowView myShadowView = (MyShadowView) findViewById(R.id.my_shadow_view);

        myShadowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImage();

            }
        });
        myShadowView.setSelectBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.car));
        myShadowView.setNormalBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.car));
        getImage();
    }

    private void getImage() {
        Bitmap textBg = BitmapFactory.decodeResource(getResources(), R.drawable.text_bg);
        Bitmap car = BitmapFactory.decodeResource(getResources(), R.drawable.car);


        RectF rectF = new RectF(0, 0, car.getWidth(), car.getHeight());

        Log.d(TAG, "car width : " + car.getWidth() + " , height : " + car.getHeight());
        Log.d(TAG, "text bg width : " + textBg.getWidth() + " , height : " + textBg.getHeight());
//        Log.d(TAG, "image bg width : " + roundImage.getWidth() +" , height : " + roundImage.getHeight());

        Bitmap output = Bitmap.createBitmap(car.getWidth(), car.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        paint.setXfermode(null);
        canvas.drawRoundRect(rectF, 80, 80, paint);
        paint.setXfermode(porterDuffXfermode);

        canvas.drawBitmap(car, 0, 0, paint);

        float scale = 1f * car.getWidth() / textBg.getWidth();
        float tranY = canvas.getHeight() - textBg.getHeight() * scale;
        Log.d(TAG, "scale / " + scale);
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        matrix.postTranslate(0, tranY);
        canvas.drawBitmap(textBg, matrix, paint);




        roundImage.setImageBitmap(output);
    }


}
