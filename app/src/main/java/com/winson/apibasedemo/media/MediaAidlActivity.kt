package com.winson.apibasedemo.media

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.winson.apibasedemo.R
import com.winson.apibasedemo.base.BaseActivity

/**
 * @date 2020/3/28
 * @author Winson
 */
class MediaAidlActivity : BaseActivity() {

    var binder: MyMediaPlay? = null
    var conn: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d("TAG", "onServiceDisconnected -----> $name")
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d("TAG", "onServiceConnected -----> $name , service is : $service")
//            binder =  MediaAidl.Stub.asInterface(service)
            binder = service as MyMediaPlay
            binder?.setListener(object : MyPlayListener {
                override fun updateName(name: String) {
                    Log.d("TAG", "updateName ----> name")
                }

            })
        }

    }

    fun showNotification(context: Context, title: String, desc: String, play: Boolean) {
        var notifyManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationManagerCompat = NotificationManagerCompat.from(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                context.packageName,
                "winson",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notifyManager.createNotificationChannel(channel)
        }

        var rv = RemoteViews(context.packageName, R.layout.nf_media_voice)
        rv.setImageViewResource(
            R.id.nf_action,
            if (play) R.mipmap.ic_nf_pause else R.mipmap.ic_nf_play
        )
        rv.setTextViewText(R.id.title, "Test title!")
        rv.setTextViewText(R.id.desc, "Test desc!")
//        rv.setOnClickPendingIntent(R.id.nf_action, )

        var builder = NotificationCompat.Builder(this)
            .setChannelId(context.packageName)
            //设置小图标
            .setSmallIcon(R.mipmap.ic_launcher)
        //设置通知标题
        Log.d("TAG", "showNotification ---> ")

        builder.setCustomContentView(rv)
        val notification = builder.build()

        val intent = Intent(this, MediaVoiceActivity::class.java)
        var pi = PendingIntent.getActivity(this, 20001, intent, 0)
        notification.contentIntent = pi

        notification.flags = notification.flags or Notification.FLAG_NO_CLEAR
        notificationManagerCompat.notify(1, notification)
    }

    override fun onBind(savedInstanceState: Bundle?) {
        super.onBind(savedInstanceState)
        setContentView(R.layout.act_media_aidl)

        findViewById<View>(R.id.show).setOnClickListener {
            showNotification(it.context, "", "", true)
        }

        findViewById<View>(R.id.dismiss).setOnClickListener {
//            var notifyManager =
//                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            notifyManager.cancel(1)

            showNotification(it.context, "", "", false)

        }
        findViewById<View>(R.id.start).setOnClickListener {
            val intent = Intent(it.context, MediaService::class.java)
            startService(intent)
        }
        findViewById<View>(R.id.connect).setOnClickListener {
            val intent = Intent(it.context, MediaService::class.java)
            bindService(intent, conn, Context.BIND_AUTO_CREATE)
        }
        findViewById<View>(R.id.disconnect).setOnClickListener {
            unbindService(conn)
        }
        findViewById<View>(R.id.send).setOnClickListener {
            binder?.play("winson haha")
        }

    }

}