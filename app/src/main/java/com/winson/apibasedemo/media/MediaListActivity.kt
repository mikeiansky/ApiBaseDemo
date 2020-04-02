package com.winson.apibasedemo.media

import com.winson.apibasedemo.base.ApiItem
import com.winson.apibasedemo.base.ApiItemListActivity
import com.winson.apibasedemo.base.BaseActivity

/**
 * @date 2020/2/22
 * @author Winson
 */
class MediaListActivity :ApiItemListActivity(){

    override fun bindData() {
        super.bindData()
        val mediaVoice = ApiItem("Media Voice")
        mediaVoice.activityClassName = MediaVoiceActivity::class.java
        items.add(mediaVoice)

        val mediaAidl = ApiItem("Media Aidl")
        mediaAidl.activityClassName = MediaAidlActivity::class.java
        items.add(mediaAidl)
    }

}