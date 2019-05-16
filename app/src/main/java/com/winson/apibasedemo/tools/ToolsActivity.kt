package com.winson.apibasedemo.tools

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.winson.apibasedemo.R
import com.winson.apibasedemo.base.BaseActivity

/**
 *@date on 2019/3/6
 *@Author Winson
 */
class ToolsActivity : BaseActivity() {

    override fun onBind(savedInstanceState: Bundle?) {
        super.onBind(savedInstanceState)
        setContentView(R.layout.act_tools)
        val result = findViewById<TextView>(R.id.result)
        check()
    }

    fun check(){
        val service = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifiNetworkInfo = service.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        Log.d("TAG", "wifi network info : " + wifiNetworkInfo.isConnected)
    }

}