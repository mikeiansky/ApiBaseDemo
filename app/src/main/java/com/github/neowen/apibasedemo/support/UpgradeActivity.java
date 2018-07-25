package com.github.neowen.apibasedemo.support;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.neowen.apibasedemo.BaseActivity;
import com.github.neowen.apibasedemo.R;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Winson on 2018/7/25.
 */
public class UpgradeActivity extends BaseActivity {

    public static final String DOWNLOAD_URL = "http://shouji.360tpcdn.com/160217/690a4e4f66d91d354cbd51eeb75686c3/com.pengtx.wonderworld_86.apk";
    public static final String DOWNLOAD_FILE_NAME = Environment.getExternalStorageDirectory().getPath() + "/winson/app.apk";
    private static final String TAG = UpgradeActivity.class.getSimpleName();

    public interface UpgradeListener {
        void onStart();

        void onFinish();

        void onError();

        void update(int rate);
    }

    DownloadUtils.UpgradeListener upgradeListener = new DownloadUtils.UpgradeListener() {
        @Override
        public void onStart() {

        }

        @Override
        public void onFinish() {

        }

        @Override
        public void onError() {

        }

        @Override
        public void update(int rate) {
            Log.d(TAG, "---------> update rate : " + rate);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upgrade_layout);

        findViewById(R.id.upgrade).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upgrade();
            }
        });


    }

    private void upgrade() {
        getCurrentVersion(this);
        new Thread(new Runnable() {
            @Override
            public void run() {

                downloadFile();

            }
        }).start();
    }

    private void getCurrentVersion(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            Toast.makeText(this, "" + packageInfo.versionName, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "" + packageInfo.versionCode, Toast.LENGTH_SHORT).show();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void downloadFile() {
        DownloadUtils.downloadFile(DOWNLOAD_URL, DOWNLOAD_FILE_NAME, upgradeListener);

    }

}
