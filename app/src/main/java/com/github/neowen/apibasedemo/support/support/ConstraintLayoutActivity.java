package com.github.neowen.apibasedemo.support.support;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.github.neowen.apibasedemo.BaseActivity;
import com.github.neowen.apibasedemo.R;

import java.io.IOException;

public class ConstraintLayoutActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_constraint);

//        findViewById(R.id.one).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String[] comd = new String[]{"su","-c","reboot -p"};
//                try {
//                    Runtime.getRuntime().exec(comd);
//                    Log.d("TAG", "execute success");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

}
