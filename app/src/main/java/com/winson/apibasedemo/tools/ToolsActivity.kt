package com.winson.apibasedemo.tools

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.alipay.sdk.app.EnvUtils
import com.alipay.sdk.app.PayTask
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.winson.apibasedemo.R
import com.winson.apibasedemo.base.BaseActivity
import com.winson.apibasedemo.net.TestService
import com.winson.apibasedemo.net.MyResult
import com.winson.apibasedemo.net.TestNetManager
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.concurrent.thread

/**
 *@date on 2019/3/6
 *@Author Winson
 */
class ToolsActivity : BaseActivity() {

    override fun onBind(savedInstanceState: Bundle?) {
        super.onBind(savedInstanceState)
        setContentView(R.layout.act_tools)
        val api = WXAPIFactory.createWXAPI(this@ToolsActivity, "wxd68da83dd32becc5")
        val register = api.registerApp("wxd68da83dd32becc5")
        Log.d("TAG", "pay with wechat register : $register")
        val sellerId = findViewById<EditText>(R.id.seller_id)
        val userId = findViewById<EditText>(R.id.user_id)
        val amount = findViewById<EditText>(R.id.amount)
        val subject = findViewById<EditText>(R.id.subject)

        val aliPay = findViewById<TextView>(R.id.ali_pay)
//        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX)
        aliPay.setOnClickListener {

//            val outTradeNo = MD5Utils.getMD5(UUID.randomUUID().toString())
//
            val localService = TestNetManager.buildTestApi().create(TestService::class.java)
//            localService.pay(
//                sellerId.editableText.toString(),
//                userId.editableText.toString(),
//                amount.editableText.toString(),
//                outTradeNo,
//                subject.editableText.toString()
//            )
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(object : Observer<MyResult> {
//                    override fun onComplete() {
//                    }
//
//                    override fun onSubscribe(d: Disposable) {
//                    }
//
//                    override fun onNext(t: MyResult) {
//                        Log.d("TAG", "server result ---------> ${t.result}")
//
//                        thread {
//                            val alipay = PayTask(this@ToolsActivity)
//                            val result = alipay.payV2(t.result, true)
//                            Log.d("TAG", "pay result ---------> ${result}")
//                        }
//
//                    }
//
//                    override fun onError(e: Throwable) {
//                        e.printStackTrace()
//                    }
//
//                })


            localService.app()
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

        val wxPay = findViewById<Button>(R.id.wx_pay);
        wxPay.setOnClickListener {
            Log.d("TAG", "start WxPay result --------->")
            val outTradeNo = MD5Utils.getMD5(UUID.randomUUID().toString())
            val localService = TestNetManager.buildTestApi().create(TestService::class.java)
            localService.getWxPay(
                sellerId.editableText.toString(),
                userId.editableText.toString(),
                amount.editableText.toString(),
                outTradeNo,
                subject.editableText.toString()
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : io.reactivex.Observer<WxPay> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: WxPay) {
                        Log.d("TAG", "my wxPay result ---------> ${t.prepay_id}")
                        val request = PayReq()
                        request.appId = t.appid
                        request.partnerId = "1900006771"
                        request.prepayId = t.prepay_id
                        request.packageValue = "com.ciwei.live"
                        request.nonceStr = t.nonce_str
                        request.timeStamp = "" + (System.currentTimeMillis() / 1000)
//                        request.sign= t.sign
                        request.sign = "Sign=WXPay"
                        val reqResult = api.sendReq(request)
                        Log.d("TAG", "my wxPay -----> $reqResult")

                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }

                })
        }

    }

}