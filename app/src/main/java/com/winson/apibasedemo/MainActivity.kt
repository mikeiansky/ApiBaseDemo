package com.winson.apibasedemo

import android.os.Bundle
import android.util.Log
import com.winson.apibasedemo.base.ApiItem
import com.winson.apibasedemo.base.ApiItemListActivity
import com.winson.apibasedemo.hybird.HybirdActivity
import com.winson.apibasedemo.tools.ToolsActivity
import com.winson.apibasedemo.view.ViewActivity

class MainActivity : ApiItemListActivity() {

    private val TAG = MainActivity::class.java.simpleName

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "lifeCycle onStop --------->")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG,"lifeCycle onRestart --------->")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG,"lifeCycle onStart --------->")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"lifeCycle onCreate --------->")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG,"lifeCycle onResume --------->")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG,"lifeCycle onPause --------->")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"lifeCycle onDestroy --------->")
    }

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
