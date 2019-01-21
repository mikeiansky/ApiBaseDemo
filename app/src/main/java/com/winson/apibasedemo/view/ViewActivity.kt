package com.winson.apibasedemo.view

import com.winson.apibasedemo.base.ApiItem
import com.winson.apibasedemo.base.ApiItemListActivity

/**
 *@date on 2019/1/21
 *@Author Winson
 */
class ViewActivity : ApiItemListActivity(){

    override fun bindData() {
        var item = ApiItem("Shadow View")
        item.activityClassName = ShadowViewActivity::class.java
        items.add(item)
    }

}