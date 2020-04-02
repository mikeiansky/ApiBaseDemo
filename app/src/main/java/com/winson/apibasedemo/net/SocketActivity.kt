package com.winson.apibasedemo.net

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.winson.apibasedemo.R
import com.winson.apibasedemo.base.BaseActivity
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.Socket
import kotlin.concurrent.thread


/**
 * @date 2020/4/2
 * @author Winson
 */
class SocketActivity : BaseActivity() {

    override fun onBind(savedInstanceState: Bundle?) {
        super.onBind(savedInstanceState)
        setContentView(R.layout.act_socket)

        val rt = findViewById<TextView>(R.id.result)

        findViewById<View>(R.id.connect).setOnClickListener {

            thread {
                val socket = Socket("172.16.2.12", 10086)
                val `in`: InputStream = socket.getInputStream()
                val br = BufferedReader(InputStreamReader(`in`))
                println("client connect success")
                val result: String = br.readLine()
                println("read msg is : $result")
                socket.close()

                runOnUiThread {
                    rt.text = result
                }
            }


        }

    }

}