package com.github.neowen.apibasedemo.design;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.github.neowen.apibasedemo.BaseActivity;
import com.github.neowen.apibasedemo.R;

public class WaveViewActivity extends BaseActivity {

    MyWaveView waveview;
    MyWaveView waveviewF;
    float shift;
    float range;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waveview_layout);

        waveview = (MyWaveView) findViewById(R.id.waveview);
        waveviewF = (MyWaveView) findViewById(R.id.waveview_f);


        waveview.setFrontColor(Color.parseColor("#a0ffffff"));
        waveviewF.setFrontColor(Color.parseColor("#ffffffff"));
//        waveviewF.setVisibility(View.GONE);
        waveview.postDelayed(new Runnable() {
            @Override
            public void run() {

                ValueAnimator offset = ValueAnimator.ofFloat(0, waveview.getSectionWidth()*2);
                offset.setRepeatCount(Integer.MAX_VALUE);
                offset.setInterpolator(new LinearInterpolator());
                offset.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float v = (float) animation.getAnimatedValue();
                        waveview.setShift(v);
                    }
                });
                offset.setDuration(1200);
                offset.start();

                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.5f, 1.5f);
                valueAnimator.setRepeatCount(Integer.MAX_VALUE);
                valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
                valueAnimator.setInterpolator(new AccelerateInterpolator());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float v = (float) animation.getAnimatedValue();
                        waveview.setWave(v);
                    }
                });
                valueAnimator.setDuration(3500);
                valueAnimator.start();

                ValueAnimator offset2 = ValueAnimator.ofFloat(0, waveview.getSectionWidth()*2);
                offset2.setRepeatCount(Integer.MAX_VALUE);
                offset2.setInterpolator(new LinearInterpolator());
                offset2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float v = (float) animation.getAnimatedValue();
                        waveviewF.setShift(v);
                    }
                });
                offset2.setDuration(1500);
                offset2.start();

                ValueAnimator valueAnimator2 = ValueAnimator.ofFloat(0.5f, 1.5f);
                valueAnimator2.setRepeatCount(Integer.MAX_VALUE);
                valueAnimator2.setRepeatMode(ValueAnimator.REVERSE);
                valueAnimator2.setInterpolator(new AccelerateInterpolator());
                valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float v = (float) animation.getAnimatedValue();
                        waveviewF.setWave(v);
                    }
                });
                valueAnimator2.setDuration(3500);
                valueAnimator2.start();

            }
        }, 1000);


    }
}
