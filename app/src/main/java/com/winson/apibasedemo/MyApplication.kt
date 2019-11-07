package com.winson.apibasedemo

import android.app.Application
import com.winson.apibasedemo.bean.ParasiteBaseInfo

/**
 * @date 2019/11/7
 * @author winson-zhou
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ParasiteBaseInfo.init(this)
    }

}