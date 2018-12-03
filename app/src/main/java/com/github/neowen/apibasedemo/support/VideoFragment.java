package com.github.neowen.apibasedemo.support;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.VideoView;

import com.github.neowen.apibasedemo.R;


public class VideoFragment extends Fragment {

    public static final String TAG = VideoFragment.class.getSimpleName();

    String TEST_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/testmp4.mp4";
    Handler handler = new Handler();

    ProgressBar bottomProgressBar;
    SeekBar seekBar;
    VideoView videoView;
    View controller;

    Runnable progressRunnable = new Runnable() {
        @Override
        public void run() {
            updateProgress();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frag_video, container, false);
        videoView = root.findViewById(R.id.video_view);
        bottomProgressBar = root.findViewById(R.id.bottom_progress);
        seekBar = root.findViewById(R.id.seek_bar);
        controller = root.findViewById(R.id.controller);
        return root;
    }

    private void updateProgress() {
        int currentPosition = videoView.getCurrentPosition();
        int duration = videoView.getDuration();
        if (duration <= 0) {
            return;
        }
        int progress = (int) (100f * currentPosition / duration);
        bottomProgressBar.setProgress(progress);
        seekBar.setProgress(progress);
        handler.postDelayed(progressRunnable, 400);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public boolean isPlay() {
        if (videoView == null) {
            return false;
        }
        return videoView.isPlaying();
    }

    public void play() {
        if (videoView != null) {
            videoView.setVideoURI(Uri.parse(TEST_PATH));
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    updateProgress();
                }
            });
            videoView.start();
        }

    }


}
