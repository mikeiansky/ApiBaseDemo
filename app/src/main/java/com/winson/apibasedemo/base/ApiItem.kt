package com.winson.apibasedemo.base

import android.app.Activity
import androidx.fragment.app.Fragment

/**
 *@date on 2019/1/14
 *@Author Winson
 */
class ApiItem(val title: String) {

    var activityClassName: Class<out Activity>? = null
    var isFragment = false
    var fragment: Fragment? = null

}