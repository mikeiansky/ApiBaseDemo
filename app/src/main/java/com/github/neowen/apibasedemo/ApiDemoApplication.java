package com.github.neowen.apibasedemo;

import android.app.Application;

import com.github.neowen.apibasedemo.utils.CrashHandler;
import com.github.neowen.apibasedemo.utils.MapUtils;

/**
 * Created by Winson on 2018/8/3.
 */
public class ApiDemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler c = new CrashHandler(this);
        MapUtils mapUtils = new MapUtils(this);
    }

}
