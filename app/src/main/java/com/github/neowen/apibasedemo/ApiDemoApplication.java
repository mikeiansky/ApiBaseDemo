package com.github.neowen.apibasedemo;

import android.app.Application;
import android.graphics.Color;
import android.os.StrictMode;
import android.widget.TextView;

import com.github.neowen.apibasedemo.utils.CrashHandler;
//import com.github.neowen.apibasedemo.utils.MapUtils;
//import com.umeng.analytics.MobclickAgent;
import com.squareup.leakcanary.LeakCanary;
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

    private static void enabledStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder() //
                .detectAll() //
                .penaltyLog() //
                .penaltyDeath() //
                .build());
    }

    protected void setupLeakCanary() {
//        enabledStrictMode();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setupLeakCanary();
        CrashHandler c = new CrashHandler(this);
//        mapUtils = new MapUtils(this);
//        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "1fe6a20054bcef865eeb0991ee84525b");
        UMConfigure.setLogEnabled(true);
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "5b8945a1f43e482909000031");
        textView = new TextView(this);
        textView.setTextColor(Color.RED);
        textView.setText("Hello Api Demo!");

//        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_DUM_NORMAL);
    }

    public String getProvince() {
//        return mapUtils.getProvince();
        return null;
    }

}
