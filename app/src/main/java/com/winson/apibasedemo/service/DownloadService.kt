package com.winson.apibasedemo.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

/**
 * @date 2019/11/12
 * @author winson-zhou
 */
class DownloadService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("TAG", "DownloadService ------> onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAG", "DownloadService ------> onDestroy")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("TAG", "DownloadService ------> onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

}