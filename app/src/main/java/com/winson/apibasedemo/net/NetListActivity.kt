package com.winson.apibasedemo.net

import com.winson.apibasedemo.base.ApiItem
import com.winson.apibasedemo.base.ApiItemListActivity
import com.winson.apibasedemo.text.TextsActivity

/**
 * @date 2020/4/2
 * @author Winson
 */
class NetListActivity : ApiItemListActivity() {

    override fun bindData() {

        val socket = ApiItem("Socket")
        socket.activityClassName = SocketActivity::class.java
        items.add(socket)

    }

}