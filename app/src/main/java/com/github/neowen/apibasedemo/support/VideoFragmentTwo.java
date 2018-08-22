package com.github.neowen.apibasedemo.support;

import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
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
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.IOException;

public class VideoFragmentTwo extends Fragment {

    public static final String TAG = VideoFragmentTwo.class.getSimpleName();

    String TEST_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/testmp4.mp4";
    int mIndex = 0;
    String path1 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/testmp4.mp4";
    String path2 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/testmp4.mp4";
    String path3 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/testmp4.mp4";
    String path4 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/testmp4.mp4";
    String path5 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/testmp4.mp4";
    String path6 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/testmp4.mp4";
    String[] paths = new String[]{path1, path2, path3, path4, path5, path6};

    Handler mHandler;
    Runnable mPlayRun;
    boolean destory;
    FrameLayout content;
    TextureView textureView;
    ImageView frameImage;
    MediaPlayer mediaPlayer;
    Bitmap lastFrameBitmap;

    public void updateResources(String[] paths) {
        this.paths = paths;
        mHandler.post(mPlayRun);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHandler = new Handler();
        mediaPlayer = new MediaPlayer();
        mPlayRun = new Runnable() {

            @Override
            public void run() {

                if (mediaPlayer == null || destory) {
                    return;

                }
                mediaPlayer.pause();
                mediaPlayer.reset();
//                mediaPlayer.setVolume(0,0);

                try {
                    String path = paths[mIndex % paths.length];
                    mIndex++;

                    mediaPlayer.setDataSource(getActivity(), Uri.parse(path));
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                        @Override
                        public void onPrepared(MediaPlayer arg0) {
                            Log.d(TAG, "media play front onPrepared ... ");
                            mediaPlayer.start();
                            frameImage.setVisibility(View.GONE);
                        }
                    });
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            Log.d(TAG, "media play front OnCompletionListener ... ");
                            frameImage.setVisibility(View.VISIBLE);
                            Bitmap currentFrameBitmap = textureView.getBitmap();
                            frameImage.setImageBitmap(currentFrameBitmap);
                            if (lastFrameBitmap != null) {
                                lastFrameBitmap.recycle();
                            }
                            lastFrameBitmap = currentFrameBitmap;

                            mHandler.post(mPlayRun);
                        }
                    });
                    mediaPlayer.prepareAsync();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            mediaPlayer.release();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        content = new FrameLayout(getActivity());

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        lp.gravity = Gravity.LEFT | Gravity.TOP;

        textureView = new TextureView(getActivity());
        textureView.setLayoutParams(lp);
        content.addView(textureView);

        frameImage = new ImageView(getActivity());
        frameImage.setScaleType(ImageView.ScaleType.FIT_XY);
        frameImage.setLayoutParams(lp);
        content.addView(frameImage);

        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                Log.d(TAG, "onSurfaceTextureAvailable --- > ");
                Surface s = new Surface(surface);
                mediaPlayer.setSurface(s);

            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
                Log.d(TAG, "onSurfaceTextureSizeChanged --- > ");

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                Log.d(TAG, "onSurfaceTextureDestroyed --- > ");
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {
                Log.d(TAG, "onSurfaceTextureUpdated --- > ");

            }
        });

        return content;
    }

    public void release() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            mediaPlayer.release();
        }
    }

    public Bitmap getBitmap() {
        return textureView == null ? null : textureView.getBitmap();
    }

    public void play() {
        mediaPlayer.pause();
        mediaPlayer.reset();

        try {
            mediaPlayer.setDataSource(getActivity(), Uri.parse(TEST_PATH));
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
