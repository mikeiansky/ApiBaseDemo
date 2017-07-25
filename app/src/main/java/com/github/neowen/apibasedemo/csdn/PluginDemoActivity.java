package com.github.neowen.apibasedemo.csdn;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.github.neowen.apibasedemo.BaseActivity;
import com.github.neowen.apibasedemo.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import dalvik.system.DexClassLoader;

/**
 * Created by Winson on 2017/7/24.
 */

public class PluginDemoActivity extends BaseActivity {

    private static final String TAG = PluginDemoActivity.class.getSimpleName();

    public static final String APK_PATH = "/mnt/sdcard/plugapp-debug.apk";
//    public static final String APK_PATH = "/mnt/sdcard/app-debug.apk";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plugin_demo_activity);
        findViewById(R.id.load_apk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                lunchActivity();
            }
        });

        Picasso.with(this)
                .load("http://pic23.nipic.com/20120908/10639194_105138442151_2.jpg")
                .fit()
                .centerInside()
                .into((ImageView) findViewById(R.id.map));

    }

    public void lunchActivity() {
        PackageInfo packageInfo = getPackageManager().getPackageArchiveInfo(APK_PATH, PackageManager.COMPONENT_ENABLED_STATE_ENABLED);
        String className = packageInfo.activities[0].name;
        Log.d(TAG, "lunch activity : " + className);

        File dexOutputDir = getDir("dex", 0);
        String dexOutputPath = dexOutputDir.getAbsolutePath();
        Log.d(TAG, "dexOutputPath : " + dexOutputPath);
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        DexClassLoader dexClassLoader = new DexClassLoader(APK_PATH, dexOutputPath, null, classLoader);
        try {
            Class ac = dexClassLoader.loadClass(className);
            Log.d(TAG, "lunch ac : " + ac);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
