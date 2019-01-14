package com.winson.apibasedemo

import com.winson.apibasedemo.base.ApiItem
import com.winson.apibasedemo.base.ApiItemListActivity
import com.winson.apibasedemo.hybird.HybirdActivity

class MainActivity : ApiItemListActivity() {

    override fun bindData() {
        val item = ApiItem("Hybird")
        item.activityClassName = HybirdActivity::class.java
        items.add(item)
    }

}
