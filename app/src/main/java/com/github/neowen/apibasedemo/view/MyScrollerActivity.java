package com.github.neowen.apibasedemo.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.github.neowen.apibasedemo.BaseActivity;
import com.github.neowen.apibasedemo.R;

/**
 * Created by Winson on 2018/7/15.
 */
public class MyScrollerActivity extends BaseActivity {

    MyScrollerView myScrollerView;
    View test;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_scroller);

        myScrollerView = (MyScrollerView) findViewById(R.id.my_scroller_view);
        test = findViewById(R.id.test);

        findViewById(R.id.change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myScrollerView.change();
                Log.d("Tag", "change width : " + test.getWidth() +" , height : " + test.getHeight());
            }
        });

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                test.setPivotX(1);
//                test.setPivotY(0);
                test.setScaleX(0.5f);
                test.setScaleY(0.5f);
            }
        });

    }
}
