package com.github.neowen.apibasedemo.design;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.github.neowen.apibasedemo.ApiItemListActivity;
import com.github.neowen.apibasedemo.BaseActivity;
import com.github.neowen.apibasedemo.R;
import com.github.neowen.apibasedemo.design.swipebacklayout.SwipeBackActivity;

public class NotificationActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_layout);

        findViewById(R.id.show_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotification();
            }
        });

    }

    private void showNotification() {

        Intent it = new Intent(this, SwipeBackActivity.class);
        PendingIntent pit = PendingIntent.getActivity(this, 0, it, 0);
        Notification.Builder mBuilder = new Notification.Builder(this);
        mBuilder.setContentTitle("叶良辰")                        //标题
                .setContentText("我有一百种方法让你呆不下去~")      //内容
//                .setSubText("——记住我叫叶良辰")                    //内容下面的一小段文字
                .setTicker("收到叶良辰发送过来的信息~")             //收到信息后状态栏显示的文字信息
                .setWhen(System.currentTimeMillis())           //设置通知时间
                .setSmallIcon(R.mipmap.ic_launcher)            //设置小图标
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))                     //设置大图标
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)    //设置默认的三色灯与振动器
//                .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.biaobiao))  //设置自定义的提示音
                .setAutoCancel(true)                        //设置点击后取消Notification
                .setContentIntent(pit);                        //设置PendingIntent
        Notification notify1 = mBuilder.getNotification();
        NotificationManager mNManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        notify1.contentView =
        mNManager.notify(1, notify1);

    }


}
