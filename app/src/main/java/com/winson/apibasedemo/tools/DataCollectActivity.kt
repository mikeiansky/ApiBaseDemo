package com.winson.apibasedemo.tools

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.wifi.WifiManager
import android.os.Bundle
import android.telephony.TelephonyManager
import android.widget.TextView
import com.winson.apibasedemo.base.BaseActivity
import java.lang.StringBuilder
import android.net.ConnectivityManager
import android.provider.Settings
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import com.google.gson.Gson
import com.winson.apibasedemo.bean.ParasiteBean
import java.util.*


class DataCollectActivity : BaseActivity() {

    // deviceId 设备唯一ID --
    // version 版本号 --
    // channel 渠道
    // platform 平台 --
    // osType 操作系统平台 --
    // deviceStyle 机器型号 --
    // brand 品牌 --
    // size 分辨率 --
    // network 网络
    // carrier 运营商 --
    // duration 停留时长

    fun getAPNType(context: Context): Int {
        var netType = 0
        val connMgr = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo ?: return netType
        val nType = networkInfo.type
        if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = 1// wifi
        } else if (nType == ConnectivityManager.TYPE_MOBILE) {
            val nSubType = networkInfo.subtype
            val mTelephony = context
                .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            if (nSubType == TelephonyManager.NETWORK_TYPE_UMTS && !mTelephony.isNetworkRoaming) {
                netType = 2// 3G
            } else {
                netType = 3// 2G
            }
        }
        return netType
    }


    override fun onBind(savedInstanceState: Bundle?) {
        super.onBind(savedInstanceState)
        setContentView(com.winson.apibasedemo.R.layout.act_data_collect)

        //wifi mac地址
        val infoText = findViewById<TextView>(com.winson.apibasedemo.R.id.info)
        val androidId = Settings.System.getString(contentResolver, Settings.System.ANDROID_ID)

        // 9774d56d682e549c
        val infoBuilder = StringBuilder()
        infoBuilder.append("androidId:")
        infoBuilder.append(androidId)
        infoBuilder.append("\r\n")

        infoBuilder.append("packageName:")
        infoBuilder.append(applicationInfo.packageName)
        infoBuilder.append("\r\n")

        val tm = applicationContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        tm.networkOperator
        infoBuilder.append("simOperator:")
        infoBuilder.append(tm.simOperator)
        infoBuilder.append("\r\n")
        infoBuilder.append("networkOperator:")
        infoBuilder.append(tm.networkOperator)
        infoBuilder.append("\r\n")

        val packageInfo = packageManager.getPackageInfo(applicationInfo.packageName, 0)
        infoBuilder.append("versionName:")
        infoBuilder.append(packageInfo.versionName)
        infoBuilder.append("\r\n")
        infoBuilder.append("versionCode:")
        infoBuilder.append(packageInfo.versionCode)
        infoBuilder.append("\r\n")
        infoBuilder.append("platform:")
        infoBuilder.append("android")
        infoBuilder.append("\r\n")
        infoBuilder.append("osType:")
        infoBuilder.append("android")
        infoBuilder.append("\r\n")

        val uuid = UUID.randomUUID().toString()
        infoBuilder.append("uuid:")
        infoBuilder.append(uuid)
        infoBuilder.append("\r\n")

        //制造商 （Manufacturer）
        val manufacturer = android.os.Build.MANUFACTURER
        infoBuilder.append("manufacturer:")
        infoBuilder.append(manufacturer)
        infoBuilder.append("\r\n")

        //型号（Model）
        val model = android.os.Build.MODEL
        infoBuilder.append("deviceStyle:")
        infoBuilder.append(model)
        infoBuilder.append("\r\n")

        //品牌（Brand）
        val brand = android.os.Build.BRAND
        infoBuilder.append("brand:")
        infoBuilder.append(brand)
        infoBuilder.append("\r\n")

        // size 分辨率

        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(displayMetrics)
//        val displayMetrics = resources.displayMetrics
        infoBuilder.append("size:")
        infoBuilder.append("${windowManager.defaultDisplay.width}x${windowManager.defaultDisplay.height}")
        infoBuilder.append("\r\n")

        //设备名 （Device）
        val device = android.os.Build.DEVICE
        infoBuilder.append("device:")
        infoBuilder.append(device)
        infoBuilder.append("\r\n")

        val apnType = getAPNType(this)
        infoBuilder.append("apnType:")
        infoBuilder.append(apnType)
        infoBuilder.append("\r\n")

        val androidVersion = android.os.Build.VERSION.SDK_INT
        infoBuilder.append("androidVersion:")
        infoBuilder.append(androidVersion)
        infoBuilder.append("\r\n")

        infoText.text = infoBuilder.toString()

        val gson = Gson()

        infoText.setOnClickListener {
            val bean = ParasiteBean()
            Log.d("TAG","result ---> ${gson.toJson(bean)}")
        }
    }

}