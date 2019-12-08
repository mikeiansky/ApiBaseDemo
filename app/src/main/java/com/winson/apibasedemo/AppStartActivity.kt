package com.winson.apibasedemo

import android.content.Intent
import android.os.Bundle
import com.winson.apibasedemo.base.BaseActivity

/**
 *
 * @date on 2019/8/31
 * @author Winson
 */
class AppStartActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_app_start)
        val intent = Intent(this@AppStartActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}