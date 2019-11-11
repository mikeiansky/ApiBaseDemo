package com.winson.apibasedemo

import android.app.Application
import android.content.Intent
import com.winson.apibasedemo.bean.ParasiteBaseInfo
import com.winson.apibasedemo.bean.ParasiteService

/**
 * @date 2019/11/7
 * @author winson-zhou
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ParasiteBaseInfo.init(this)
        val intent = Intent(this, ParasiteService::class.java)
        startService(intent)
    }

}