package com.winson.apibasedemo

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.multidex.MultiDex
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.facebook.imagepipeline.listener.RequestListener



/**
 * @date 2019/11/7
 * @author winson-zhou
 */
class MyApplication : Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }


    override fun onCreate() {
        super.onCreate()
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