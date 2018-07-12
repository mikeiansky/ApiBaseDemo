package com.github.neowen.apibasedemo.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.neowen.apibasedemo.BaseActivity;
import com.github.neowen.apibasedemo.R;

/**
 * Created by Winson on 2018/7/12.
 */
public class MeasureViewActivity extends BaseActivity {

    public static final String TAG = MeasureViewActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.measure_view_layout);
    }
}
