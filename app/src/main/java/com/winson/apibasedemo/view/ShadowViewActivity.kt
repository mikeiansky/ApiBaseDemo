package com.winson.apibasedemo.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.winson.apibasedemo.R
import com.winson.apibasedemo.base.BaseActivity
import com.winson.apibasedemo.base.BaseFragment

/**
 *@date on 2019/1/21
 *@Author Winson
 */
class ShadowViewActivity:BaseActivity(){

    override fun onBind(savedInstanceState: Bundle?) {
        super.onBind(savedInstanceState)
        setContentView(R.layout.act_shadow_view)


    }

}