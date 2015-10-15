package com.github.neowen.apibasedemo.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.neowen.apibasedemo.R;

/**
 * Created by ZWX on 15/10/15.
 */
public class TouchActivity extends AppCompatActivity {

    public static final String TAG = TouchActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.touch);
    }
}
