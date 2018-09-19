package com.github.neowen.apibasedemo.support.thumb;

import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.github.neowen.apibasedemo.BaseActivity;
import com.github.neowen.apibasedemo.R;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * @date on 2018/9/19
 * @Author Winson
 */
public class ThumbVideoActivity extends BaseActivity {

    public static final String TAG = ThumbVideoActivity.class.getSimpleName();

//    String path = "http://rmcdn.2mdn.net/MotifFiles/html/1248596/android_1330378998288.mp4";
    String path = "http://v.ysbang.cn//data/video/2015/rkb/2015rkb01.mp4";
//    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/zwxtest.mp4";

    TextureView textureView;
    Button getThumbBtn;
    ImageView thumbIV;
    MediaPlayer mediaPlayer;
    MediaMetadataRetriever mediaMetadataRetriever;
    Handler handler;

    class MyRun implements Runnable{

        Bitmap bitmap;

        public MyRun(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        @Override
        public void run() {
            thumbIV.setImageBitmap(bitmap);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.reset();
        mediaPlayer.release();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_thumb_video);

        mediaPlayer = new MediaPlayer();
        handler = new Handler();

        Button pause = (Button) findViewById(R.id.pause);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
            }
        });
        SeekBar seekBar = (SeekBar) findViewById(R.id.progress_bar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int lastProgress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (lastProgress != progress) {

                    int max = mediaPlayer.getDuration();
                    final int position = (int) (( progress / 100f ) * max);
//
//                    Bitmap bitmap = mediaMetadataRetriever.getFrameAtTime(position * 1000,MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
//                    Log.d(TAG, "duration : " + position + " , bitmap : " + bitmap);
//                    thumbIV.setImageBitmap(bitmap);
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Bitmap bitmap = mediaMetadataRetriever.getFrameAtTime(position * 1000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
//                            Log.d(TAG, "duration : " + position + " , bitmap : " + bitmap);
////                            thumbIV.setImageBitmap(bitmap);
//                            handler.post(new MyRun(bitmap));
//                        }
//                    }).start();


                    lastProgress = progress;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int max = mediaPlayer.getDuration();
                final int position = (int) (( seekBar.getProgress() / 100f ) * max);
                mediaPlayer.seekTo(position);

            }
        });

        getThumbBtn = (Button) findViewById(R.id.get_thumb);
        getThumbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int w = textureView.getWidth();
//                int h = textureView.getHeight();
////                Bitmap thumb = textureView.getBitmap();
//                Bitmap thumb = textureView.getBitmap(w / 5, h / 5);
//                thumbIV.setImageBitmap(thumb);
//                Log.d(TAG, "thumb bitmap width : " + thumb.getWidth() + " , height : " + thumb.getHeight());
                int position = mediaPlayer.getCurrentPosition();
                Bitmap bitmap = mediaMetadataRetriever.getFrameAtTime(position * 1000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
                Log.d(TAG, "duration : " + position + " , bitmap : " + bitmap);
                thumbIV.setImageBitmap(bitmap);

            }
        });
        thumbIV = (ImageView) findViewById(R.id.iv_thumb);

        textureView = (TextureView) findViewById(R.id.texture_view);
        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                Surface s = new Surface(surface);
                mediaPlayer.setSurface(s);
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

        try {

            mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(path, new HashMap<String, String>());

            mediaPlayer.setDataSource(this, Uri.parse(path));
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
            mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(MediaPlayer mp, int percent) {
                    Log.d(TAG, "onBufferingUpdate ---> " + percent);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
