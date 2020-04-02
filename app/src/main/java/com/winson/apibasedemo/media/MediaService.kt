package com.winson.apibasedemo.media

import android.app.Service
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.winson.apibasedemo.service.MediaAidl

/**
 * @date 2020/3/28
 * @author Winson
 */
class MediaService : Service() {

    var binder = MyController()

    class MyController : Binder(), MyMediaPlay {

        var myListener: MyPlayListener? = null

        override fun play(name: String) {
            Log.d("TAG", "play ----> name is : $name")
            myListener?.updateName(name)
        }

        override fun setListener(listener: MyPlayListener) {
            myListener = listener
        }

    }

    override fun onCreate() {
        super.onCreate()
        Log.d("TAG", "MediaService ---------> oncreate ")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("TAG", "MediaService ---------> onStartCommand ")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun unbindService(conn: ServiceConnection) {
        super.unbindService(conn)
        Log.d("TAG", "MediaService ---------> unbindService ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAG", "MediaService ---------> onDestroy ")
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d("TAG", "MediaService ---------> onBind ")
        return binder
    }


}