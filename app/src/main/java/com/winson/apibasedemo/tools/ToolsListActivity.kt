package com.winson.apibasedemo.tools

import android.os.Bundle
import com.winson.apibasedemo.base.ApiItem
import com.winson.apibasedemo.base.ApiItemListActivity

class ToolsListActivity : ApiItemListActivity() {

    override fun onBind(savedInstanceState: Bundle?) {
        super.onBind(savedInstanceState)

        var item = ApiItem("Tools")
        item.activityClassName = ToolsActivity::class.java
        items.add(item)

        item = ApiItem("Data Collect")
        item.activityClassName = DataCollectActivity::class.java
        items.add(item)

    }

}