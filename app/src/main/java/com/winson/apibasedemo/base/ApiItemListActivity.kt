package com.winson.apibasedemo.base

import android.os.Bundle
import android.widget.ListView
import com.winson.apibasedemo.R

/**
 *@date on 2019/1/14
 *@Author Winson
 */
open class ApiItemListActivity : BaseActivity() {

    protected val items: ArrayList<ApiItem> = ArrayList()

    open fun bindData() {

    }

    override fun onBind(savedInstanceState: Bundle?) {
        super.onBind(savedInstanceState)
        setContentView(R.layout.act_api_list)
        bindData()
        val listView = findViewById<ListView>(R.id.list)
        val adapter = ApiItemAdapter(this, supportFragmentManager, R.id.root)
        adapter.replaceData(items)
        listView.adapter = adapter
    }



}