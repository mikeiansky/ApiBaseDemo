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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.github.neowen.apibasedemo.R;


public class VideoFragment extends Fragment {

    public static final String TAG = VideoFragment.class.getSimpleName();

    Handler handler = new Handler();

    String videoPath;
    ProgressBar bottomProgressBar;
    SeekBar seekBar;
    VideoView videoView;
    View controller;
    TextView progressText, durationText;
    ImageView action;

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
        bottomProgressBar = root.findViewById(R.id.bottom_progress);
        seekBar = root.findViewById(R.id.seek_bar);
        controller = root.findViewById(R.id.controller);
        progressText = root.findViewById(R.id.progress_text);
        durationText = root.findViewById(R.id.duration_text);
        action = root.findViewById(R.id.action);
        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoView.isPlaying()) {
                    videoView.pause();
                    action.setBackgroundResource(R.drawable.play);
                    handler.removeCallbacks(progressRunnable);
                } else {
                    videoView.start();
                    action.setBackgroundResource(R.drawable.pause);
                    updateProgress();
                }
            }
        });
        videoView = root.findViewById(R.id.video_view);
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                complete();
            }
        });

        return root;
    }

    private String formatDuration(int duration) {
        int total = duration / 1000;
        int second = total % 60;
        int minute = total / 60 / 60;
        return String.format("%02d:%02d", minute, second);
    }

    private void complete() {
        int duration = videoView.getDuration();
        int currentPosition = duration;
        int progress = 100;
        bottomProgressBar.setProgress(progress);
        seekBar.setProgress(progress);
        progressText.setText(formatDuration(currentPosition));
        durationText.setText(formatDuration(duration));
        action.setBackgroundResource(R.drawable.play);
        handler.removeCallbacks(progressRunnable);
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
        progressText.setText(formatDuration(currentPosition));
        durationText.setText(formatDuration(duration));

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

    public void play(String videoPath) {
        this.videoPath = videoPath;
        if (videoView != null) {
            videoView.setVideoURI(Uri.parse(videoPath));
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    updateProgress();
                }
            });
            videoView.start();
            action.setBackgroundResource(R.drawable.pause);
        }

    }


}
