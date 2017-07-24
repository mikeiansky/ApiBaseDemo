package com.github.neowen.apibasedemo.csdn;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.github.neowen.apibasedemo.BaseActivity;
import com.github.neowen.apibasedemo.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Winson on 2017/7/24.
 */

public class PluginDemoActivity extends BaseActivity {

    private static final String TAG = PluginDemoActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plugin_demo_activity);
        findViewById(R.id.load_apk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            InputStream in = view.getContext().getAssets().open("plugapp-debug.apk");
                            Log.d(TAG, "file length : " + in.available());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }
}
