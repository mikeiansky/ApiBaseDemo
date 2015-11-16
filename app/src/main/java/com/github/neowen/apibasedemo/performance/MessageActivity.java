package com.github.neowen.apibasedemo.performance;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.neowen.apibasedemo.R;
import com.github.neowen.apibasedemo.common.DebugUtils;


import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ZWX on 15/11/11.
 */
public class MessageActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = MessageActivity.class.getSimpleName();

    @Bind(R.id.send)
    Button mSend;

    Handler mHandler;

    Handler mHandlerOne = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            if (DebugUtils.debug) {
                Log.d(TAG, "message one what : " + what);
            }
        }

    };

    Handler mHandlerTwo = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            if (DebugUtils.debug) {
                Log.d(TAG, "message two what : " + what);
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_looper);
        ButterKnife.bind(this);
        mSend.setOnClickListener(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (DebugUtils.debug) {
                    Log.d(TAG, "Looper start");
                }
                Looper.prepare();
                mHandler = new Handler();
                Looper.loop();
                if (DebugUtils.debug) {
                    Log.d(TAG, "Looper end");
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.send:
                send();
                mHandler.getLooper().quit();
                break;
        }
    }

    private void send() {


        Message mg = new Message();
        mg.setTarget(mHandlerTwo);
        mg.what = 45;
        mHandlerOne.sendMessage(mg);

//        mHandlerOne.sendEmptyMessage(3);
    }
}
