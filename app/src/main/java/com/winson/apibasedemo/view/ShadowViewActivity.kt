package com.winson.apibasedemo.view

import android.os.Bundle
import com.winson.apibasedemo.R
import com.winson.apibasedemo.base.BaseActivity


/**
 *@date on 2019/1/21
 *@Author Winson
 */
class ShadowViewActivity:BaseActivity(){

    override fun onBind(savedInstanceState: Bundle?) {
        super.onBind(savedInstanceState)
        setContentView(R.layout.act_shadow_view)

//        val shadowView = findViewById<ImageView>(R.id.shadow_view)
//        val shadowDrawable = ShadowDrawable(this)
//        ViewCompat.setBackground(shadowView, shadowDrawable)
//        shadowView.setOnClickListener {
//            val intent = Intent()
//            intent.data = Uri.parse("app://ciwei/ciwei")
//            startActivity(intent)
//        }

        val view = findViewById<ProgressView>(R.id.indicator_view)
        view.setProgress(35)

    }

}