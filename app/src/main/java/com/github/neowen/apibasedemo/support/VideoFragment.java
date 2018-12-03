package com.github.neowen.apibasedemo.support;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
    ValueAnimator showAnimator, hiddenAnimator;
    boolean seekBarOnTouch;
    String videoPath;
    ProgressBar bottomProgressBar;
    SeekBar seekBar;
    VideoView videoView;
    View controller;
    TextView progressText, durationText;
    ImageView action;
    boolean onAnimator;
    boolean controllerShow;
    float lastX;

    Runnable progressRunnable = new Runnable() {
        @Override
        public void run() {
            updateProgress();
        }
    };

    Runnable hiddenControllerRunnable = new Runnable() {
        @Override
        public void run() {
            if (controller != null) {
                hiddenAnimator.start();
            }
        }
    };

    private void refreshHiddenController() {
        handler.removeCallbacks(hiddenControllerRunnable);
        handler.postDelayed(hiddenControllerRunnable, 5000);
    }

    private void showController() {
        if (!controllerShow) {
            controllerShow = true;
            showAnimator.start();
        }
        refreshHiddenController();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.frag_video, container, false);
        bottomProgressBar = root.findViewById(R.id.bottom_progress);
        bottomProgressBar.setAlpha(0f);
        seekBar = root.findViewById(R.id.seek_bar);
        controller = root.findViewById(R.id.controller);
        progressText = root.findViewById(R.id.progress_text);
        durationText = root.findViewById(R.id.duration_text);
        action = root.findViewById(R.id.action);
        videoView = root.findViewById(R.id.video_view);
        controllerShow = true;
        showAnimator = ValueAnimator.ofFloat(1f, 0f);
        showAnimator.setDuration(250);
        showAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                controller.setTranslationY(container.getHeight() * value);
                bottomProgressBar.setAlpha(value);
            }
        });
        showAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                controllerShow = true;
                onAnimator = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                onAnimator = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                onAnimator = false;

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        hiddenAnimator = ValueAnimator.ofFloat(0f, 1f);
        hiddenAnimator.setDuration(250);
        hiddenAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                controller.setTranslationY(container.getHeight() * value);
                bottomProgressBar.setAlpha(value);
            }
        });
        hiddenAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                controllerShow = false;
                onAnimator = true;

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                onAnimator = false;

            }

            @Override
            public void onAnimationCancel(Animator animation) {
                onAnimator = false;

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        final GestureDetector gestureDetector = new GestureDetector(getActivity(), new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                if (!onAnimator) {
                    if (controllerShow) {
                        hiddenAnimator.start();
                    } else {
                        showAnimator.start();
                        refreshHiddenController();
                    }
                }
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                Log.d(TAG, "onScroll distanceX : " + distanceX);
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        });
        root.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
//                int action = event.getAction();
//                switch (action) {
//                    case MotionEvent.ACTION_DOWN:
//                        lastX = event.getRawY();
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                    case MotionEvent.ACTION_UP:
//
//                        float currentX = event.getRawX();
//                        float max = root.getWidth() * 5f;
//                        float offsetX = currentX - lastX;
//                        float offset = offsetX / max;
//
//                        break;
//                }
                return true;
            }
        });


        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoView.isPlaying()) {
                    videoView.pause();
                    action.setBackgroundResource(R.drawable.ic_video_play);
                    handler.removeCallbacks(progressRunnable);
                } else {
                    videoView.start();
                    action.setBackgroundResource(R.drawable.ic_video_pause);
                    updateProgress();
                }
                refreshHiddenController();
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                complete();
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                handler.removeCallbacks(hiddenControllerRunnable);
                seekBarOnTouch = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBarOnTouch = false;
                int progress = seekBar.getProgress();
                int duration = videoView.getDuration();
                int seek = (int) ((progress / 100f) * duration);
                videoView.seekTo(seek);
                refreshHiddenController();
            }
        });
        refreshHiddenController();
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
        if (!seekBarOnTouch) {
            seekBar.setProgress(progress);
        }
        progressText.setText(formatDuration(currentPosition));
        durationText.setText(formatDuration(duration));
        action.setBackgroundResource(R.drawable.ic_video_play);
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
        if (!seekBarOnTouch) {
            seekBar.setProgress(progress);
        }
        progressText.setText(formatDuration(currentPosition));
        durationText.setText(formatDuration(duration));

        handler.postDelayed(progressRunnable, 400);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(hiddenControllerRunnable);
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
            action.setBackgroundResource(R.drawable.ic_video_pause);
            showController();
        }

    }


}
