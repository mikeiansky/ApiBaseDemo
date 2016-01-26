package com.github.neowen.apibasedemo.attr;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.neowen.apibasedemo.R;

/**
 * Created by Winson on 2016/1/26.
 */
public class AttrActivity extends AppCompatActivity {

    public static final String TAG = AttrActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attr_layout);

    }
}
