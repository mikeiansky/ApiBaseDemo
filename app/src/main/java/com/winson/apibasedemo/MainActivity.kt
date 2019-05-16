package com.winson.apibasedemo

import android.util.Log
import com.winson.apibasedemo.base.ApiItem
import com.winson.apibasedemo.base.ApiItemListActivity
import com.winson.apibasedemo.hybird.HybirdActivity
import com.winson.apibasedemo.tools.ToolsActivity
import com.winson.apibasedemo.view.ViewActivity

class MainActivity : ApiItemListActivity() {

    var test : Int? = 0
    set(value) {
        Log.d("TAG", "set value at -------> $value")
    }

    override fun bindData() {
        test = 1
        var item = ApiItem("Hybird")
        item.activityClassName = HybirdActivity::class.java
        items.add(item)

        item = ApiItem("Custom View")
        item.activityClassName = ViewActivity::class.java
        items.add(item)

        item = ApiItem("Tools")
        item.activityClassName = ToolsActivity::class.java
        items.add(item)
    }

}
