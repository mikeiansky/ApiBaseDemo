package com.winson.apibasedemo

import com.winson.apibasedemo.base.ApiItem
import com.winson.apibasedemo.base.ApiItemListActivity
import com.winson.apibasedemo.hybird.HybirdActivity
import com.winson.apibasedemo.view.ViewActivity

class MainActivity : ApiItemListActivity() {

    override fun bindData() {
        var item = ApiItem("Hybird")
        item.activityClassName = HybirdActivity::class.java
        items.add(item)

        item = ApiItem("Custom View")
        item.activityClassName = ViewActivity::class.java
        items.add(item)
    }

}
