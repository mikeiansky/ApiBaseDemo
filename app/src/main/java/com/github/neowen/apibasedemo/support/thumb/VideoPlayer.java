package com.github.neowen.apibasedemo.support.thumb;

import android.content.Context;
import android.view.View;

/**
 * @date on 2018/9/19
 * @Author Winson
 */
public interface VideoPlayer {

    View getVideoView();

    void reset();

    void release();

    void setDataSource(Context context, String path);

    void setOnVideoActionListener(OnVideoActionListener onVideoActionListener);
}
