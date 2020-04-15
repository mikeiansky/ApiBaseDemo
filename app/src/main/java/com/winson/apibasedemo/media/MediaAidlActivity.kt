package com.winson.apibasedemo.media

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.IBinder
import android.text.format.DateFormat
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.winson.apibasedemo.R
import com.winson.apibasedemo.base.BaseActivity
import java.io.File
import java.io.IOException
import java.util.*

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


        findViewById<View>(R.id.record).setOnTouchListener { v, event ->

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    Toast.makeText(this, "touch down", Toast.LENGTH_SHORT).show()
                    startRecord()
                }
                MotionEvent.ACTION_UP -> {
                    Toast.makeText(this, "touch up", Toast.LENGTH_SHORT).show()
                    stopRecord()
                }
            }
            true
        }

        ActivityCompat.requestPermissions(this, PERMISSIONS, 0)

    }

    var PERMISSIONS = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
        )
    var filePath: String? = null
    var mMediaRecorder: MediaRecorder? = null

    fun startRecord() {

        if (mMediaRecorder == null)
            mMediaRecorder = MediaRecorder()
        try {

            mMediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)// 设置麦克风
            mMediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)

            mMediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            var fileName ="${DateFormat.format("yyyyMMdd_HHmmss", Calendar.getInstance(Locale.CHINA))}.m4a"
            var destDir = File(Environment.getExternalStorageDirectory().absolutePath + "/awinso-test/vioce")
            println(destDir.absolutePath)
            if (!destDir.exists()) {
                println("exits ${destDir.exists()} , mkdirs")
                destDir.mkdirs()
            }
            println("exits ${destDir.exists()}")
            filePath = destDir.absolutePath + "/" + fileName
            mMediaRecorder?.setOutputFile(filePath)
            mMediaRecorder?.prepare()
            mMediaRecorder?.start()

            Log.i("success!", "startRecord success")
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            Log.i("failed!", "startRecord "+e.message)
        } catch (e: IOException) {
            e.printStackTrace()
            Log.i("failed!", "startRecord "+e.message)
        }

    }

    fun stopRecord() {
        try {
            mMediaRecorder?.stop()
            mMediaRecorder?.release()
            mMediaRecorder = null
            Log.i("success!", "stopRecord success")
        } catch (e: RuntimeException) {
            e.printStackTrace()
            mMediaRecorder?.reset()
            mMediaRecorder?.release()
            mMediaRecorder = null
            Log.i("failed!", "stopRecord "+e.message)

            var file = File(filePath)
            if (file.exists()) {
                file.delete()
            }
        }
    }


}