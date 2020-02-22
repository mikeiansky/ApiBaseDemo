package com.winson.apibasedemo.text

import android.os.Bundle
import android.widget.TextView
import com.winson.apibasedemo.R
import com.winson.apibasedemo.base.BaseActivity

/**
 * @date 2020/2/22
 * @author Winson
 */
class TextsActivity :BaseActivity(){

    override fun onBind(savedInstanceState: Bundle?) {
        super.onBind(savedInstanceState)
        setContentView(R.layout.act_texts)
        val announceTextView = findViewById<TextView>(R.id.announce)
        val announceStr = "1.今天会有直播哦\n2.尽情期待\n3.完善八点开始哦\n4.来了就不要错过哦"
        announceTextView.text = announceStr
    }

}