package com.winson.apibasedemo.hybird

import android.os.Bundle
import com.winson.apibasedemo.R
import com.winson.apibasedemo.base.BaseActivity

/**
 *@date on 2019/1/14
 *@Author Winson
 */
class HybirdActivity : BaseActivity() {

    override fun onBind(savedInstanceState: Bundle?) {
        super.onBind(savedInstanceState)
        setContentView(R.layout.act_hybird)
    }

}