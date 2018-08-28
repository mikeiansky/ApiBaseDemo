package com.github.neowen.apibasedemo.support;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.neowen.apibasedemo.BaseActivity;
import com.github.neowen.apibasedemo.R;

public class HandlerActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = HandlerActivity.class.getSimpleName();
    Button start, quit;
    Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.handler_layout);

        start = (Button) findViewById(R.id.start);
        quit = (Button) findViewById(R.id.quit);
        start.setOnClickListener(this);
        quit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.start:
                start();
                break;
            case R.id.quit:
                quit();
                break;
        }

    }

    private void quit() {

//        Message msg = handler.obtainMessage(2);
        handler.sendMessage(handler.obtainMessage(2));
    }

    private void start() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "thread start---->");

                Looper.prepare();
                handler = new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        Log.d(TAG, "handleMessage msg ----> " + msg);
                        return false;
                    }
                });
                handler.sendMessage(handler.obtainMessage(1));
                Log.d(TAG, "Looper prepare---->");
                Log.d(TAG, "main loop ----> " + getMainLooper());
                Log.d(TAG, "handler loop ----> " + handler.getLooper());
                Log.d(TAG, "handler loop2 ----> " + Looper.myLooper());
                Looper.loop();
                Log.d(TAG, "Looper loop---->");

                Log.d(TAG, "thread end---->");
            }
        }).start();

    }

}
