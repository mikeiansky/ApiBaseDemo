package com.github.neowen.apibasedemo.support.thumb;

import android.app.ActivityManager;
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
import com.squareup.picasso.LruCache;

import java.io.IOException;
import java.util.HashMap;

import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.pm.ApplicationInfo.FLAG_LARGE_HEAP;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.HONEYCOMB;

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
    private boolean release;
    private FrameLayout videoFrame;
    private SeekBar seekBar;
    private ImageView thumbImageView;
    private LruCache lruCache;

    static class FillRunnable implements Runnable {

        ImageView target;
        Bitmap frame;

        public FillRunnable(ImageView target, Bitmap frame) {
            this.target = target;
            this.frame = frame;
        }

        @Override
        public void run() {
            target.setImageBitmap(frame);
        }
    }

    static int calculateMemoryCacheSize(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        boolean largeHeap = (context.getApplicationInfo().flags & FLAG_LARGE_HEAP) != 0;
        int memoryClass = am.getMemoryClass();
        if (largeHeap && SDK_INT >= HONEYCOMB) {
            memoryClass = am.getLargeMemoryClass();
        }
        // Target ~15% of the available heap.
        return 1024 * 1024 * memoryClass / 7;
    }

    public void setOnVideoActionListener(OnVideoActionListener onVideoActionListener) {
        this.onVideoActionListener = onVideoActionListener;
    }

    public MainVideoPlayer(final Context context) {
        this.context = context;
        lruCache = new LruCache(calculateMemoryCacheSize(context));
        videoFrame = new FrameLayout(context);
        mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if (release) {
                    return;
                }
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
                if (onVideoActionListener != null) {
                    onVideoActionListener.onComplete();
                }
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
            public void onProgressChanged(final SeekBar seekBar, final int progress, boolean fromUser) {
                if (release) {
                    return;
                }
                if (lastProgress != progress) {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int position = (int) ((1f * progress / seekBar.getMax()) * mediaPlayer.getDuration());
                            Bitmap cache = lruCache.get("" + position);
                            if (cache == null) {
                                Bitmap frame = mediaMetadataRetriever.getFrameAtTime(position * 1000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
                                cache = frame;
                                lruCache.set("" + position, cache);
                            }
                            thumbImageView.post(new FillRunnable(thumbImageView, cache));
                        }
                    }).start();

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
        release = true;
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        if (mediaMetadataRetriever != null) {
            mediaMetadataRetriever.release();
        }
    }

    @Override
    public void setDataSource(Context context, String path) {
        this.dataSource = path;
        try {
            if (release) {
                return;
            }
            mediaPlayer.setDataSource(context, Uri.parse(path));
            mediaPlayer.prepareAsync();
            mediaMetadataRetriever.setDataSource(path, new HashMap<String, String>());

        } catch (IOException e) {
            e.printStackTrace();
            mediaPlayer.release();
        }
    }

}
