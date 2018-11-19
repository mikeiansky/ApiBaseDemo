package com.github.neowen.apibasedemo.base;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * @date on 2018/11/18
 * @Author Winson
 */
public class MyService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MyService", "onStart command");
        Log.d("MyService", "msg is : " + intent.getStringExtra("msg"));

        Intent intent1 = new Intent();
        intent1.setAction("com.winson.demo");
        sendBroadcast(intent1);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyService", "onCreate ---->");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyService", "onDestroy ---->");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
