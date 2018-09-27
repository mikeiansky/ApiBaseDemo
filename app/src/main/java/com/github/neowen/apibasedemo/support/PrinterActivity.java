package com.github.neowen.apibasedemo.support;

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
import android.os.Looper;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
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
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.sina.weibo.sdk.api.share.Base;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;

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

        Logger.addLogAdapter(new AndroidLogAdapter());
        Logger.json("{name:'wen',age:25,birthday:2017-02-02}");

        final WebView webView = (WebView) findViewById(R.id.web_view);
        final WebSettings webSettings = webView.getSettings();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setDisplayZoomControls(false);
        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
        webSettings.setSupportZoom(false); // 支持缩放
//        webView.addJavascriptInterface(this, CONSENSUS_LABLE);
        webSettings.setLoadWithOverviewMode(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        String htmlData = "<html>\n" +
                "\n" +
                "\t<head>\n" +
                "<script type=\"text/javascript\">\n" +
                "function display_alert()\n" +
                "  {\n" +
                "  alert(\"I am an alert box!!\")\n" +
//                "  window.location.assign(\"http://www.w3school.com.cn\")" +
                "  }\n" +
                "</script>\n" +
                "<script type=\"text/javascript\">\n" +
                "function testBack()\n" +
                "  {\n" +
                "  alert(\"I am back button!!\")\n" +
//                "  window.location.assign(\"http://www.w3school.com.cn\")" +
                "  }\n" +
                "</script>\n" +
                "</head>\n" +
                "\n" +
                "\t<body>\n" +
                "\t\n" +
                "\t\t<a href=\"javascript:display_alert()\">click</a>\t\n" +
                "\n" +
                "\t</body>\n" +
                "\n" +
                "\t\n" +
                "\n" +
                "</html>";

        webView.loadData(htmlData, "text/html", "UTF -8");

//        getSupportFragmentManager()
//                .beginTransaction()
//                .add(R.id.content, new SupportFragment())
//                .commit();

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

//                boolean result = isAppRunning(v.getContext(), "com.github.neowen.apibasedemo");
//                Toast.makeText(v.getContext(), "result : " + result, Toast.LENGTH_SHORT).show();
//
//                isForeground(v.getContext());

                webView.loadUrl("javascript:testBack()");

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
