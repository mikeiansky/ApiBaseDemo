package com.winson.apibasedemo.base

import android.content.Context
import android.content.Intent
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.winson.apibasedemo.R
import com.winson.widget.CommonAdapter
import com.winson.widget.ViewHolder

/**
 *@date on 2019/1/14
 *@Author Winson
 */
class ApiItemAdapter(context: Context, val fragmentManager: FragmentManager, val rootViewId: Int) :
    CommonAdapter<ApiItem>(context, R.layout.item_api, null) {

    override fun convert(viewHolder: ViewHolder, item: ApiItem, position: Int) {
        (viewHolder.findViewById(R.id.title) as TextView).text = item.title
        viewHolder.convertView.setOnClickListener {
            if (item.isFragment) {
                fragmentManager.beginTransaction()
                    .add(item.fragment!!,"item")
                    .addToBackStack(null)
                    .commit()
            } else {
                val intent = Intent(context, item.activityClassName)
                context.startActivity(intent)
            }
        }
    }

}