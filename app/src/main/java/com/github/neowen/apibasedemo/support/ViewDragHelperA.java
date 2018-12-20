package com.github.neowen.apibasedemo.support;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.github.neowen.apibasedemo.BAct;
import com.github.neowen.apibasedemo.R;

/**
 * Created by ZWX on 15/10/15.
 */
public class ViewDragHelperA extends AppCompatActivity {

    boolean mHidden;

    HttpRequestHelper httpRequestHelper;

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return httpRequestHelper;
    }

//    @Override
//    public Object onRetainNonConfigurationInstance() {
//        return httpRequestHelper;
//    }


    @SuppressLint("StaticFieldLeak")
    void startAsyncWork() {
        // This runnable is an anonymous class and therefore has a hidden reference to the outer
        // class MainActivity. If the activity gets destroyed before the thread finishes (e.g. rotation),
        // the activity instance will leak.
        Runnable work = new Runnable() {
            @Override
            public void run() {
                // Do some slow work in background
                SystemClock.sleep(20000);
            }
        };
        new Thread(work).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewdraghelper);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        View test = findViewById(R.id.test);
        httpRequestHelper = (HttpRequestHelper) getLastCustomNonConfigurationInstance();
        if (httpRequestHelper == null) {
            httpRequestHelper = new HttpRequestHelper(test);
        }

        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ViewDragHelperA.this, BAct.class);
//                startActivityForResult(intent, 20000);

                startAsyncWork();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG", "----> data : " + data.getStringExtra("hello"));
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        Log.d("TAG", "onKeyDown --> keycode : " + keyCode + " , event : " + event.getAction());
////        return super.onKeyDown(keyCode, event);
//        return true;
//    }
}
