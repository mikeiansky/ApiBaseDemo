package com.github.neowen.apibasedemo;

import android.app.Application;
import android.graphics.Color;
import android.widget.TextView;

import com.github.neowen.apibasedemo.utils.CrashHandler;
import com.github.neowen.apibasedemo.utils.MapUtils;

/**
 * Created by Winson on 2018/8/3.
 */
public class ApiDemoApplication extends Application {

//    MapUtils mapUtils;

    TextView textView;

    public TextView getTextView() {
        return textView;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler c = new CrashHandler(this);
//        mapUtils = new MapUtils(this);

        textView = new TextView(this);
        textView.setTextColor(Color.RED);
        textView.setText("Hello Api Demo!");
    }

    public String getProvince() {
//        return mapUtils.getProvince();
        return null;
    }

}
