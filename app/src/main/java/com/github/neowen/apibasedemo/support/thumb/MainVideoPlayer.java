package com.github.neowen.apibasedemo.support.thumb;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.github.neowen.apibasedemo.R;

import java.io.IOException;
import java.util.HashMap;

/**
 * @date on 2018/9/19
 * @Author Winson
 */
class MainVideoPlayer implements VideoPlayer {

    private Context context;
    private OnVideoActionListener onVideoActionListener;
    private String dataSource;
    private int lastProgress;

    private TextureView textureView;
    private MediaPlayer mediaPlayer;
    private MediaMetadataRetriever mediaMetadataRetriever;

    private FrameLayout videoFrame;
    private SeekBar seekBar;
    private ImageView thumbImageView;

    public void setOnVideoActionListener(OnVideoActionListener onVideoActionListener) {
        this.onVideoActionListener = onVideoActionListener;
    }

    public MainVideoPlayer(Context context) {
        this.context = context;

        videoFrame = new FrameLayout(context);
        mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                seekBar.setSecondaryProgress(percent);
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

            }
        });

        textureView = new TextureView(context);
        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                Surface surf = new Surface(textureView.getSurfaceTexture());
                mediaPlayer.setSurface(surf);
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {

            }
        });
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT
        );
        textureView.setLayoutParams(lp);
        videoFrame.addView(textureView);

        View controlView = LayoutInflater.from(context).inflate(R.layout.video_control, videoFrame, false);
        videoFrame.addView(controlView);

        View videoThumb = LayoutInflater.from(context).inflate(R.layout.video_thumb, videoFrame, false);
        FrameLayout.LayoutParams vtlp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT
        );
        vtlp.gravity = Gravity.CENTER;
        videoThumb.setLayoutParams(vtlp);
        videoFrame.addView(videoThumb);
        thumbImageView = (ImageView) videoFrame.findViewById(R.id.thumb_image);

        seekBar = (SeekBar) videoFrame.findViewById(R.id.seek_bar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (lastProgress != progress) {

                    int position = (int) ((1f * progress / seekBar.getMax()) * mediaPlayer.getDuration());
                    Bitmap frame = mediaMetadataRetriever.getFrameAtTime(position * 1000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
                    thumbImageView.setImageBitmap(frame);

                    lastProgress = progress;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int duration = mediaPlayer.getDuration();
                if (duration > 0) {
                    int progress = seekBar.getProgress();
                    int position = (int) ((1f * progress / seekBar.getMax()) * duration);
                    mediaPlayer.seekTo(position);
                }
            }
        });
    }

    public View getVideoView() {
        return videoFrame;
    }

    public void reset() {
        if (mediaPlayer != null) {
            mediaPlayer.reset();
        }
    }

    public void release() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    @Override
    public void setDataSource(Context context, String path) {
        this.dataSource = path;
        mediaMetadataRetriever.setDataSource(path, new HashMap<String, String>());
        try {
            mediaPlayer.setDataSource(context, Uri.parse(path));
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
            mediaPlayer.release();
        }
    }

}
