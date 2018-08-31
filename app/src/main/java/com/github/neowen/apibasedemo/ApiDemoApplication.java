package com.github.neowen.apibasedemo;

import android.app.Application;
import android.graphics.Color;
import android.widget.TextView;

import com.github.neowen.apibasedemo.utils.CrashHandler;
import com.github.neowen.apibasedemo.utils.MapUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

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
//        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "1fe6a20054bcef865eeb0991ee84525b");
        UMConfigure.setLogEnabled(true);
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "5b8945a1f43e482909000031");
        textView = new TextView(this);
        textView.setTextColor(Color.RED);
        textView.setText("Hello Api Demo!");

        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_DUM_NORMAL);
    }

    public String getProvince() {
//        return mapUtils.getProvince();
        return null;
    }

}
