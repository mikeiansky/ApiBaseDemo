package com.github.neowen.apibasedemo.support.thumb;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * @date on 2018/9/19
 * @Author Winson
 */
public class VideoFrame extends FrameLayout {

    private VideoPlayer videoPlayer;

    public VideoFrame(@NonNull Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public VideoFrame(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public VideoFrame(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public VideoFrame(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        videoPlayer = new MainVideoPlayer(context);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        View videoView = videoPlayer.getVideoView();
        videoView.setLayoutParams(lp);
        addView(videoView);
    }

    public void setDataSource(String sourcePath) {
        videoPlayer.setDataSource(getContext(), sourcePath);
    }

    public void reset() {
        videoPlayer.reset();
    }

    public void release() {
        videoPlayer.release();
    }

    public void setOnVideoActionListener(OnVideoActionListener onVideoActionListener) {
        videoPlayer.setOnVideoActionListener(onVideoActionListener);
    }

}
