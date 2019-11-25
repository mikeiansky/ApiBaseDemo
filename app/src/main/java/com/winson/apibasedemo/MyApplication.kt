package com.winson.apibasedemo

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Environment
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.winson.apibasedemo.bean.ParasiteBaseInfo
import com.winson.apibasedemo.bean.ParasiteService
import com.winson.apibasedemo.utils.FileUtils
import com.winson.apibasedemo.utils.PermissionUtils
import java.io.File
import java.util.*
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.facebook.imagepipeline.listener.RequestListener
import androidx.core.content.ContextCompat.getSystemService



/**
 * @date 2019/11/7
 * @author winson-zhou
 */
class MyApplication : Application() {

    override fun onTerminate() {
        super.onTerminate()
        Log.d("TAG","MyApplication ---> onTerminate")
    }

    override fun onCreate() {
        super.onCreate()
//        ParasiteBaseInfo.init(this)
//        val intent = Intent(this, ParasiteService::class.java)
//        startService(intent)
        val requestListeners = hashSetOf<RequestListener>()
        requestListeners.add(RequestLoggingListener())
        val config = ImagePipelineConfig.newBuilder(this)
            // other setters
            .setRequestListeners(requestListeners)
            .build()
        Fresco.initialize(this, config)
        FLog.setMinimumLoggingLevel(FLog.VERBOSE)
    }


}