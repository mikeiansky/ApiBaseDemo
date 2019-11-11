package com.winson.apibasedemo

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Environment
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import com.winson.apibasedemo.bean.ParasiteBaseInfo
import com.winson.apibasedemo.bean.ParasiteService
import com.winson.apibasedemo.utils.FileUtils
import com.winson.apibasedemo.utils.PermissionUtils
import java.io.File
import java.util.*

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