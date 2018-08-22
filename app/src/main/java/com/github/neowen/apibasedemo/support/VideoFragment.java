package com.github.neowen.apibasedemo.support;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class VideoFragment extends Fragment {

    public static final String TAG = VideoFragment.class.getSimpleName();

    SurfaceView frontSurface, backgroundSurface;
    FrameLayout mFrame;
    MediaPlayer frontMediaPlayer, backgroundMediaPlayer;
    Handler mHandler;
    int mIndex = 0;
    String path1 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/testmp4.mp4";
    String path2 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/testmp4.mp4";
    String path3 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/testmp4.mp4";
    String path4 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/testmp4.mp4";
    String path5 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/testmp4.mp4";
    String path6 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/testmp4.mp4";
    String[] paths = new String[]{path1, path2, path3, path4, path5, path6};
    Runnable mPlayRun;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPlayRun = new Runnable() {

            @Override
            public void run() {
                frontMediaPlayer.pause();
                frontMediaPlayer.reset();
                backgroundMediaPlayer.pause();
                backgroundMediaPlayer.reset();
                backgroundMediaPlayer.setVolume(0,0);

                try {
                    String path = paths[mIndex % paths.length];
                    mIndex++;

                    backgroundMediaPlayer.setDataSource(getActivity(), Uri.parse(path));
                    backgroundMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                        @Override
                        public void onPrepared(MediaPlayer arg0) {
                            Log.d(TAG, "media play background onPrepared ... ");
//                            backgroundSurface.setVisibility(View.GONE);
                            backgroundMediaPlayer.start();
//                            // seek to last
//                            backgroundMediaPlayer.seekTo(backgroundMediaPlayer.getDuration());
                        }
                    });
                    backgroundMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            Log.d(TAG, "media play background OnCompletionListener ... ");
                        }
                    });
                    backgroundMediaPlayer.prepareAsync();

                    frontMediaPlayer.setDataSource(getActivity(), Uri.parse(path));
                    frontMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                        @Override
                        public void onPrepared(MediaPlayer arg0) {
                            Log.d(TAG, "media play front onPrepared ... ");
                            frontMediaPlayer.start();
                            backgroundMediaPlayer.seekTo(backgroundMediaPlayer.getDuration());
//                            frontSurface.setVisibility(View.VISIBLE);
                        }
                    });
                    frontMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            Log.d(TAG, "media play front OnCompletionListener ... ");
//                            backgroundSurface.setVisibility(View.VISIBLE);
                            mHandler.post(mPlayRun);
                        }
                    });
                    Log.d(TAG, "prepare 333");
                    frontMediaPlayer.prepareAsync();
                    Log.d(TAG, "prepare 444");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        };

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        float density = getResources().getDisplayMetrics().density;

        mFrame = new FrameLayout(getActivity());
        mHandler = new Handler();

        frontMediaPlayer = new MediaPlayer();
        backgroundMediaPlayer = new MediaPlayer();

        backgroundSurface = new SurfaceView(getActivity());
        backgroundSurface.getHolder().addCallback(new NextVideoSurfaceHodlerCallback());

        frontSurface = new SurfaceView(getActivity());
        frontSurface.getHolder().addCallback(new VideoSurfaceHodlerCallback());

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        lp.gravity = Gravity.LEFT | Gravity.TOP;

        frontSurface.setLayoutParams(lp);
        backgroundSurface.setLayoutParams(lp);

        mFrame.addView(frontSurface);
        mFrame.addView(backgroundSurface);

        mHandler.postDelayed(mPlayRun, 2000);

        return mFrame;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mPlayRun);
        if (frontMediaPlayer != null) {
            frontMediaPlayer.release();
            frontMediaPlayer = null;
        }
        if (backgroundMediaPlayer != null) {
            backgroundMediaPlayer.release();
            backgroundMediaPlayer = null;
        }
    }

    class VideoSurfaceHodlerCallback implements SurfaceHolder.Callback {

        @Override
        public void surfaceChanged(
                SurfaceHolder holder,
                int format, int width, int height) {

        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            frontMediaPlayer.setDisplay(frontSurface.getHolder());
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
            backgroundMediaPlayer.setDisplay(backgroundSurface.getHolder());
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }

    }

}
