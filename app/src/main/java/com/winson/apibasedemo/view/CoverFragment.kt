package com.winson.apibasedemo.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.winson.apibasedemo.R
import com.winson.apibasedemo.base.BaseFragment

/**
 *
 * @date on 2019/7/28
 * @author Winson
 */
class CoverFragment : BaseFragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.frag_cover, container, false)
        root.setOnClickListener {
            activity?.onBackPressed()
        }
        return root
    }

}