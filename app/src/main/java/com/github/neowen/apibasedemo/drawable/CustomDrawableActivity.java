package com.github.neowen.apibasedemo.drawable;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;

import com.github.neowen.apibasedemo.BaseActivity;
import com.github.neowen.apibasedemo.R;

/**
 * Created by zwx on 2018/7/29.
 */

public class CustomDrawableActivity extends BaseActivity {

    public static final String TAG = CustomDrawableActivity.class.getSimpleName();

    ImageView image,customImage;
    Drawable customDrawable;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_drawable_layout);

        image = (ImageView) findViewById(R.id.image);
        customImage = (ImageView) findViewById(R.id.custom_image);
        image.setBackground(getResources().getDrawable(R.drawable.btn));
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        customImage.setBackground(createCustomDrawable());
        customImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private Drawable createCustomDrawable() {

        Drawable selectDrawable = getResources().getDrawable(R.drawable.d_red);
        Drawable normalDrawable = getResources().getDrawable(R.drawable.d_green);
        StateListDrawable sld = new StateListDrawable();
        sld.addState(new int[]{android.R.attr.state_focused}, selectDrawable);
        sld.addState(new int[]{android.R.attr.state_pressed}, selectDrawable);
        sld.addState(new int[]{android.R.attr.state_selected}, selectDrawable);
        sld.addState(new int[]{}, normalDrawable);

        return sld;
    }

}
