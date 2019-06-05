package com.winson.apibasedemo.tools

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.alipay.sdk.app.EnvUtils
import com.alipay.sdk.app.PayTask
import com.winson.apibasedemo.R
import com.winson.apibasedemo.base.BaseActivity
import com.winson.apibasedemo.net.LocalService
import com.winson.apibasedemo.net.MyResult
import com.winson.apibasedemo.net.NetManager
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.act_tools.view.*
import kotlin.concurrent.thread

/**
 *@date on 2019/3/6
 *@Author Winson
 */
class ToolsActivity : BaseActivity() {

    override fun onBind(savedInstanceState: Bundle?) {
        super.onBind(savedInstanceState)
        setContentView(R.layout.act_tools)
        val result = findViewById<TextView>(R.id.result)
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX)
        result.setOnClickListener {

            val localService = NetManager.buildApi().create(LocalService::class.java)
            localService.pay()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<MyResult> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: MyResult) {
                        Log.d("TAG", "server result ---------> ${t.result}")

                        thread {
                            val alipay = PayTask(this@ToolsActivity)
                            val result = alipay.payV2(t.result, true)
                            Log.d("TAG", "pay result ---------> ${result}")
                        }

                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }

                })

        }

    }

}