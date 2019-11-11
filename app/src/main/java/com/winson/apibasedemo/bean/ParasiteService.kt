package com.winson.apibasedemo.bean

import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.IBinder
import android.util.Log
import kotlin.concurrent.thread

/**
 * @date 2019/11/7
 * @author winson-zhou
 */
class ParasiteService : Service() {

    private var onRunning = false
    private var parasiteDbHelper: ParasiteDbHelper? = null

    fun checkNet(): Boolean {
        // 判断是否具有可以用于通信渠道
        val mobileConnection = isMobileConnection(applicationContext!!)
        val wifiConnection = isWIFIConnection(applicationContext!!)
        if (!mobileConnection && !wifiConnection) {
            // 没有网络
            return false
        }
        return true
    }

    /**
     * 判断手机接入点（APN）是否处于可以使用的状态
     *
     * @param context
     * @return
     */
    private fun isMobileConnection(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        if (networkInfo != null && networkInfo.isConnected) {
            return true
        }
        return false
    }

    /**
     * 判断当前wifi是否是处于可以使用状态
     *
     * @param context
     * @return
     */
    private fun isWIFIConnection(context: Context): Boolean {
        val manager =
            context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)

        if (networkInfo != null && networkInfo.isConnected) {
            return true
        }
        return false
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        parasiteDbHelper = ParasiteDbHelper(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startReport()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun startReport() {
        thread {
            if (!onRunning) {
                onRunning = true
                while (true) {
                    reportRecord()
                    Thread.sleep(1000 * 60 * 10)
                }
                onRunning = false
            }
        }
    }

    private fun reportRecord() {
        val datas = parasiteDbHelper?.queryRecord()
        if (datas != null && datas.isNotEmpty()) {
            // check network
            for (data in datas) {
                if (!checkNet()) {
                    return
                }

            }
        }
    }

}