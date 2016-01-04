package com.github.neowen.apibasedemo.csdn.circle_media;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;

import java.io.File;

/**
 * Created by Winson on 2016/1/4.
 */
public class CircleNoBlackVideoActivity extends AppCompatActivity {

    public static final String TAG = CircleNoBlackVideoActivity.class.getSimpleName();


    public static String path1 = Environment.getExternalStorageDirectory() + File.separator + "test.mp4";
    public static String path2 = Environment.getExternalStorageDirectory() + File.separator + "test.mp4";
    public static String path3 = Environment.getExternalStorageDirectory() + File.separator + "test.mp4";
    public static int DURATION = 15000;

    public static String[] paths = new String[]{path1, path2, path3};

    SurfaceView mVideoSurface, mNextSurface;
    FrameLayout mFrame;
    MediaPlayer mCurrentMediaPlayer, mNextMediaPlayer;
    Handler mHandler;
    int mIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFrame = new FrameLayout(this);
        setContentView(mFrame);
        mHandler = new Handler();

        mCurrentMediaPlayer = new MediaPlayer();
        mNextMediaPlayer = new MediaPlayer();

        mVideoSurface = new SurfaceView(this);
        mVideoSurface.getHolder().addCallback(new VideoSurfaceHodlerCallback());

        mNextSurface = new SurfaceView(this);
        mNextSurface.getHolder().addCallback(new NextVideoSurfaceHodlerCallback());

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                700,
                500);
        lp.gravity = Gravity.LEFT | Gravity.TOP;
        mVideoSurface.setLayoutParams(lp);

        lp = new FrameLayout.LayoutParams(
                700,
                500);
        lp.gravity = Gravity.LEFT | Gravity.TOP;
        mNextSurface.setLayoutParams(lp);

        mFrame.addView(mNextSurface);
        mFrame.addView(mVideoSurface);
        mHandler.postDelayed(mPlayRun, 2000);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mPlayRun);
        if (mCurrentMediaPlayer != null) {
            mCurrentMediaPlayer.release();
            mCurrentMediaPlayer = null;
        }
        if (mNextMediaPlayer != null) {
            mNextMediaPlayer.release();
            mNextMediaPlayer = null;
        }
    }

    Runnable mPlayRun = new Runnable() {

        @Override
        public void run() {
            mCurrentMediaPlayer.pause();
            mNextMediaPlayer.pause();
            mNextMediaPlayer.reset();

            try {
                if (mIndex == 0) {
                    String path = paths[mIndex % paths.length];
                    mIndex++;
                    mCurrentMediaPlayer.setDataSource(CircleNoBlackVideoActivity.this, Uri.parse(path));
                    mCurrentMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                        @Override
                        public void onPrepared(MediaPlayer arg0) {
                            mCurrentMediaPlayer.start();
                            mCurrentMediaPlayer.stop();
                            mVideoSurface.setVisibility(View.GONE);
                        }
                    });
                    mCurrentMediaPlayer.prepareAsync();

                    mNextMediaPlayer.setDataSource(CircleNoBlackVideoActivity.this, Uri.parse(path));
                    mNextMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                        @Override
                        public void onPrepared(MediaPlayer arg0) {
                            mNextMediaPlayer.start();
                            Log.d("Tag", "new duration : " + mNextMediaPlayer.getDuration());
                            mHandler.postDelayed(mPlayRun, DURATION);
                        }
                    });
                    mNextMediaPlayer.prepareAsync();
                } else {
                    String path = paths[mIndex % paths.length];
                    mIndex++;
                    mNextMediaPlayer.setDataSource(CircleNoBlackVideoActivity.this, Uri.parse(path));
                    mNextMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                        @Override
                        public void onPrepared(MediaPlayer arg0) {
                            mNextMediaPlayer.start();
                            Log.d("Tag", "new duration : " + mNextMediaPlayer.getDuration());
                            mHandler.postDelayed(mPlayRun, DURATION);
                        }
                    });
                    mNextMediaPlayer.prepareAsync();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    };

    class VideoSurfaceHodlerCallback implements SurfaceHolder.Callback {

        @Override
        public void surfaceChanged(
                SurfaceHolder holder,
                int format, int width, int height) {

        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            mCurrentMediaPlayer.setDisplay(mVideoSurface.getHolder());
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }

    }

    class NextVideoSurfaceHodlerCallback implements SurfaceHolder.Callback {

        @Override
        public void surfaceChanged(
                SurfaceHolder holder,
                int format, int width, int height) {

        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            mNextMediaPlayer.setDisplay(mNextSurface.getHolder());
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }

    }


}
