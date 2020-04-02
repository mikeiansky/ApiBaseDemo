package com.winson.apibasedemo.media

/**
 * @date 2020/3/28
 * @author Winson
 */
interface MyMediaPlay {

    fun play(name: String)

    fun setListener(listener: MyPlayListener)

}