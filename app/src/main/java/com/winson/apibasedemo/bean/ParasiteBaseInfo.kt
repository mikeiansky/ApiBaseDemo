package com.winson.apibasedemo.bean

import android.content.Context
import android.provider.Settings

/**
 * @date 2019/11/7
 * @author winson-zhou
 */
object ParasiteBaseInfo {

    // 渠道,安装时就在清单中制定了，appStore等
    var appChannel: String? = "native"
    // 应用版本
    var appVersion: String? = null
    // 品牌
    var brand: String? = null
    // 设备唯一标识
    var deviceId: String? = null
    // 机型
    var deviceStyle: String? = null
    //操作系统
    var osType: String? = null
    // 分辨率
    var screenSize: String? = null

    fun init(context: Context) {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        appVersion = packageInfo.versionName
        brand = android.os.Build.BRAND
        deviceId = Settings.System.getString(context.contentResolver, Settings.System.ANDROID_ID)
        // 9774d56d682e549c
        deviceStyle = android.os.Build.MODEL
        osType = android.os.Build.VERSION.SDK_INT.toString()
        val displayMetrics = context.resources.displayMetrics
        screenSize = "${displayMetrics.widthPixels}x${displayMetrics.heightPixels}"

    }

}