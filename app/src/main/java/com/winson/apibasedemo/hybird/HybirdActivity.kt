package com.winson.apibasedemo.hybird

import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import com.winson.apibasedemo.MainActivity
import com.winson.apibasedemo.R
import com.winson.apibasedemo.base.BaseActivity

/**
 *@date on 2019/1/14
 *@Author Winson
 */
class HybirdActivity : BaseActivity() {

    val HTML = "<html>\n" +
            "\n" +
            "\t<script type=\"text/javascript\">\n" +
            "\n" +
            "\t\tfunction callJs(){\n" +
            "\t\t\tdocument.getElementById(\"content\").innerHTML += \"<br\\>Android 调用了JS函数\"\n" +
            "\t\t}\n" +
            "\n" +
            "\t</script>\n" +
            "\n" +
            "\t<body>\n" +
            "\t\t<button onclick=\"javascript:callJs()\">From Android</button>\n" +
            "\t\t<br/>\n" +
            "\t\t<button onclick=\"window.winson.callAndroid('From MineWebView')\">Call Android</button>\n" +
            "\t\t<br/>\n" +
            "\t\t<div id=\"content\">Hello Android</div>\n" +
            "\n" +
            "\t</body>\n" +
            "\n" +
            "</html>"

    var webView: WebView? = null

    override fun onBind(savedInstanceState: Bundle?) {
        super.onBind(savedInstanceState)
        setContentView(R.layout.act_hybird)

        val callJs = findViewById<Button>(R.id.call_js)
        val webView = findViewById<WebView>(R.id.web_view)
        webView.settings.javaScriptEnabled = true
        webView.webChromeClient = WebChromeClient()
        webView.webViewClient = object : WebViewClient() {}
        webView.addJavascriptInterface(this, "winson")
//        webView.loadData(HTML, "text/html", "UTF-8")

        webView.loadUrl("https://mp.weixin.qq.com/s?__biz=MzU4NjA4NjMwNw==&tempkey=MTAxN19rRUo1REw3dTlwT1lIMHJYM1JBQUNXX2UxYXVvMk9SR3F0bUd5ZTdEdHhETmlaQTZyWnEzVmpVb2RkYU9uQ0lCcDFfV2JPZGNvVHFVZnhDcnhoaEMweVRzWHc2X1BBemRxY05odjdnZjgzMkxhR0tiWTZONFRKdnhiOVB6cUozd3YzaHR5aGFoczNYc0M2am9vZWhqXzZqb3RSV0F2STUzV0lkZkhBfn4%3D&chksm=7d825c454af5d553ed9e126dece1b2b953d21800b4feb0e086641295be834fc782808afe9af3&mpshare=1&scene=1&srcid=0715NrhYA01JDhseQ492X2Jr#wechat_redirect")

        this.webView = webView

        callJs.setOnClickListener {
            webView.loadUrl("javascript:callJs()")
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        webView?.destroy()
    }

    @JavascriptInterface
    fun callAndroid(msg: String) {
        Log.d("TAG", "callAndroid -------> $msg")
    }

}