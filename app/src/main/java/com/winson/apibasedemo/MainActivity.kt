package com.winson.apibasedemo

import com.winson.apibasedemo.base.ApiItem
import com.winson.apibasedemo.base.ApiItemListActivity
import com.winson.apibasedemo.media.MediaListActivity
import com.winson.apibasedemo.text.TextsActivity

class MainActivity : ApiItemListActivity() {

    override fun bindData() {

        val textsItem = ApiItem("Texts")
        textsItem.activityClassName = TextsActivity::class.java
        items.add(textsItem)

        val mediaItem = ApiItem("Media")
        mediaItem.activityClassName = MediaListActivity::class.java
        items.add(mediaItem)

    }

}
