package com.winson.apibasedemo.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 *@date on 2019/1/14
 *@Author Winson
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBind(savedInstanceState)
    }

    protected open fun onBind(savedInstanceState: Bundle?) {

    }

}