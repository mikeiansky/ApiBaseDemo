package com.winson.apibasedemo.hybird

import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
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
            "\t\t<button onclick=\"window.winson.callAndroid('From WebView')\">Call Android</button>\n" +
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
        webView.webViewClient = object : WebViewClient(){}
        webView.addJavascriptInterface(this, "winson")
//        webView.loadData(HTML, "text/html", "UTF-8")

        webView.loadUrl("http://v.qq.com/cover/f/f7hbxgwnskb2obt.html?vid=8KUE9qOMlFT")

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