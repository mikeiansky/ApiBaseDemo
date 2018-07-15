package com.github.neowen.apibasedemo.support;

import android.Manifest;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Printer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.github.neowen.apibasedemo.BaseActivity;
import com.github.neowen.apibasedemo.R;
import com.github.neowen.apibasedemo.design.emptyview.EmptyDialogFragment;
import com.sina.weibo.sdk.api.share.Base;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Winson on 2017/11/13.
 */

public class PrinterActivity extends BaseActivity {

    public static final String TAG = PrinterActivity.class.getSimpleName();

    boolean change;

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.d(TAG, "onSaveInstanceState -------->");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState ********************>");
        savedInstanceState.getBoolean("");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printer);
        Log.d(TAG, "onCreate +++++++++++++>");

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content, new SupportFragment())
                .commit();

        findViewById(R.id.print).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(PrinterActivity.this, LandActivity.class);
//                startActivity(intent);
                boolean result = isAppRunning(v.getContext(), "com.shangdong.hotel");
                Toast.makeText(v.getContext(), "result : " + result, Toast.LENGTH_SHORT).show();

            }
        });

        findViewById(R.id.change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (change) {
//                    change = false;
//                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
//                } else {
//                    change = true;
//                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);
//                }

//                com.winson.action

//                Intent intent = new Intent();
//                intent.setAction("com.winson.action");
//                sendBroadcast(intent);

//                Intent intent = null;
//                try {
//                    intent = new Intent(v.getContext(), Class.forName("com.shangdong.shangdong.MainActivity"));
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
//                startActivity(intent);
//                openApp("com.github.neowen.apibasedemo");

                boolean result = isAppRunning(v.getContext(), "com.github.neowen.apibasedemo");
                Toast.makeText(v.getContext(), "result : " + result, Toast.LENGTH_SHORT).show();

                isForeground(v.getContext());
            }
        });



    }

    private void openApp(String packageName) {
        PackageManager packageManager = getPackageManager();
        // TODO
        Intent launchIntentForPackage = packageManager.getLaunchIntentForPackage(packageName);
        if (launchIntentForPackage != null) {
            startActivity(launchIntentForPackage);
        } else {
            Toast.makeText(this, "no apps !", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isAppRunning(Context context, String packageName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        if (list.size() <= 0) {
            return false;
        }
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.baseActivity.getPackageName().equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    private void isForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        Log.d(TAG, "Test");

    }

}
