package com.github.neowen.apibasedemo.design;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.neowen.apibasedemo.BaseActivity;
import com.github.neowen.apibasedemo.R;

public class WaveViewActivity extends BaseActivity {

    MyWaveView waveview;
    float shift;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waveview_layout);

        waveview = (MyWaveView) findViewById(R.id.waveview);

        waveview.postDelayed(new Runnable() {
            @Override
            public void run() {
                shift += 6f;
                waveview.setShift(shift);
                waveview.postDelayed(this, 50);
            }
        }, 50);

    }
}
