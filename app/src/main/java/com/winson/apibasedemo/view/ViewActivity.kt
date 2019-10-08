package com.winson.apibasedemo.view

import android.os.Bundle
import android.util.Log
import com.winson.apibasedemo.base.ApiItem
import com.winson.apibasedemo.base.ApiItemListActivity
import com.winson.apibasedemo.hybird.HybirdActivity

/**
 *@date on 2019/1/21
 *@Author Winson
 */
class ViewActivity : ApiItemListActivity(){

    private val TAG = ViewActivity::class.java.simpleName

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "lifeCycle onStop --------->")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "lifeCycle onRestart --------->")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "lifeCycle onStart --------->")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "lifeCycle onCreate --------->")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "lifeCycle onResume --------->")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "lifeCycle onPause --------->")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "lifeCycle onDestroy --------->")
    }

    override fun bindData() {
        var item = ApiItem("Shadow View")
        item.activityClassName = ShadowViewActivity::class.java
        items.add(item)

        item = ApiItem("Tab Layout")
        item.activityClassName = TLActivity::class.java
        items.add(item)

        item = ApiItem("Round")
        item.activityClassName = RoundActivity::class.java
        items.add(item)

        item = ApiItem("AnimatorFour")
        item.activityClassName = AnimatorActivity::class.java
        items.add(item)
    }

}