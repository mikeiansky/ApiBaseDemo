package com.github.neowen.apibasedemo.performance;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.github.neowen.apibasedemo.BaseActivity;
import com.github.neowen.apibasedemo.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Winson on 2017/7/25.
 */

public class HandlerActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = HandlerActivity.class.getSimpleName();

    private Handler handler;
    private Looper mLooper;

    public static class API {

    }

    public static class BPI {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void test(API api, BPI bpi) {
        Log.d(TAG, "get event bus message at test method.");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiver(API api) {
        Log.d(TAG, "get event bus message at receiver method.");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiverNull() {
        Log.d(TAG, "get event bus with null params");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.handler_activity);

        EventBus.getDefault().register(this);
        findViewById(R.id.handler_button).setOnClickListener(this);
        findViewById(R.id.send_msg).setOnClickListener(this);
        findViewById(R.id.start_thread).setOnClickListener(this);
        findViewById(R.id.stop_looper).setOnClickListener(this);
        findViewById(R.id.event_bus).setOnClickListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.event_bus:
                shutEventBus();
                break;
            case R.id.handler_button:
                runLoop();
                break;
            case R.id.send_msg:
                sendMssage();
                break;
            case R.id.start_thread:
                startThread();
                break;
            case R.id.stop_looper:
                stopLooper();
                break;
        }
    }

    private void shutEventBus() {
        EventBus.getDefault().post(new API());
    }

    private void stopLooper() {
        handler.getLooper().quit();
//        EventBus.getDefault().post(null);
    }

    private void startThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "looper prepare!");
                Looper.prepare();
                mLooper = Looper.myLooper();
                Log.d(TAG, "looper ready!");
                Looper.loop();
                Log.d(TAG, "looper stop!");

            }
        }).start();

        Looper.getMainLooper();
    }

    private void sendMssage() {
        handler.sendMessage(handler.obtainMessage(101));
    }

    private void runLoop() {

        handler = new Handler(mLooper) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.d(TAG, " thread :  " + Thread.currentThread().getName() + " get message : " + msg.what);
            }
        };

    }

}
