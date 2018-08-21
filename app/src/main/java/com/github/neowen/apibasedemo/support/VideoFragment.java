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

    SurfaceView mVideoSurface, mNextSurface;
    FrameLayout mFrame;
    MediaPlayer mCurrentMediaPlayer, mNextMediaPlayer;
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
                mCurrentMediaPlayer.pause();
                mNextMediaPlayer.pause();
                mNextMediaPlayer.reset();
                int duration = 0;
                try {
                    if (mIndex == 0) {
                        String path = paths[mIndex % paths.length];
                        mIndex++;
                        mCurrentMediaPlayer.setDataSource(getActivity(), Uri.parse(path));
                        mCurrentMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                            @Override
                            public void onPrepared(MediaPlayer arg0) {
                                mCurrentMediaPlayer.start();
                                mVideoSurface.setVisibility(View.GONE);
                            }
                        });
                        mCurrentMediaPlayer.prepareAsync();

                        mNextMediaPlayer.setDataSource(getActivity(), Uri.parse(path));
                        mNextMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                            @Override
                            public void onPrepared(MediaPlayer arg0) {
                                mNextMediaPlayer.start();
                                Log.d(TAG, "media play real duration : " + mNextMediaPlayer.getDuration());
                                mHandler.postDelayed(mPlayRun, mNextMediaPlayer.getDuration());
                            }
                        });
                        mNextMediaPlayer.prepareAsync();
                    } else {
                        String path = paths[mIndex % paths.length];
                        mIndex++;
                        mNextMediaPlayer.setDataSource(getActivity(), Uri.parse(path));
                        mNextMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                            @Override
                            public void onPrepared(MediaPlayer arg0) {
                                mNextMediaPlayer.start();
                                Log.d(TAG, "media play duration : " + mNextMediaPlayer.getDuration());
                                mHandler.postDelayed(mPlayRun, mNextMediaPlayer.getDuration());
                            }
                        });
                        mNextMediaPlayer.prepareAsync();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                Log.d(TAG, "media play duration : " + duration);
            }

        };

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        float density = getResources().getDisplayMetrics().density;

        mFrame = new FrameLayout(getActivity());
        mHandler = new Handler();

        mCurrentMediaPlayer = new MediaPlayer();
        mNextMediaPlayer = new MediaPlayer();

        mVideoSurface = new SurfaceView(getActivity());
        mVideoSurface.getHolder().addCallback(new VideoSurfaceHodlerCallback());

        mNextSurface = new SurfaceView(getActivity());
        mNextSurface.getHolder().addCallback(new NextVideoSurfaceHodlerCallback());

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);
//        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
//                (int) (400 * density),
//                (int) (300 * density));
        lp.gravity = Gravity.LEFT | Gravity.TOP;
        mVideoSurface.setLayoutParams(lp);

//        lp = new FrameLayout.LayoutParams(
//                (int) (400 * density),
//                (int) (300 * density));
//        lp.gravity = Gravity.LEFT | Gravity.TOP;
        mNextSurface.setLayoutParams(lp);

        mFrame.addView(mNextSurface);
        mFrame.addView(mVideoSurface);
        mHandler.postDelayed(mPlayRun, 2000);

        return mFrame;
    }

    @Override
    public void onDestroy() {
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
