package com.github.neowen.apibasedemo.support;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.github.neowen.apibasedemo.ApiDemoApplication;
import com.github.neowen.apibasedemo.BaseActivity;
import com.github.neowen.apibasedemo.R;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MediaPlayActivity extends BaseActivity implements View.OnClickListener {

    public static final String TAG = MediaPlayActivity.class.getSimpleName();

    String path = Environment.getExternalStorageDirectory().getPath() + "/testmp4.mp4";
    String path2 = "file:///android_asset/testmp4.mp4";
    String newPath = null;
    VideoFragment videoFragment;
    VideoFragmentTwo videoFragmentTwo;
    FrameLayout testContent;
    //    String TEST_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/testmp4.mp4";
//    String TEST_PATH = "https://scb.liaidi.com//data//video//2017//12//20171214235251279358.mp4";
    String TEST_PATH = "https://scb.liaidi.com//data//video//2017//12//20171214235251279358.mp4";
//    String TEST_PATH = "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-17_17-33-30.mp4";
    NiceVideoPlayer niceVideoPlayer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_play_layout);
        niceVideoPlayer = findViewById(R.id.nice_video_player);
        testContent = (FrameLayout) findViewById(R.id.test_content);
//        videoView.setVideoURI(Uri.parse("file:///android_asset/testmp4.mp4"));
//        videoView.setVideoURI(Uri.parse(path));
//        videoView.start();
        niceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_NATIVE);
        TxVideoPlayerController controller = new TxVideoPlayerController(this);
        niceVideoPlayer.setController(controller);

        findViewById(R.id.copy).setOnClickListener(this);
        findViewById(R.id.play).setOnClickListener(this);

        videoFragment = new VideoFragment();
        videoFragmentTwo = new VideoFragmentTwo();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.video_content, videoFragment)
                .commit();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if(videoView!=null){
//            videoView.pause();
//        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.play:
                videoFragment.play(TEST_PATH);
//                videoFragmentTwo.play();
//                TextView textView = ((ApiDemoApplication)getApplication()).getTextView();
//                int specWidth = View.MeasureSpec.makeMeasureSpec(1000, View.MeasureSpec.EXACTLY);
//                int specHeight = View.MeasureSpec.makeMeasureSpec(100, View.MeasureSpec.EXACTLY);
//                TextView textView = new TextView(this);
//                textView.setText("Hello");
//                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT);
//                textView.setLayoutParams(lp);
//                testContent.addView(textView);

                break;
            case R.id.copy:
                niceVideoPlayer.setUp(TEST_PATH, null);

//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//                Log.d(TAG, "is play ing : " + videoFragment.isPlay());
//                copy();

//                Bitmap bitmap = videoFragmentTwo.getBitmap();
//                videoView.setImageBitmap(bitmap);

//                videoFragmentTwo.release();

                break;
        }
    }

    private void copy() {

//        File f = new File(path2);
//        Toast.makeText(this,""+f.exists(),Toast.LENGTH_SHORT).show();

        try {
            InputStream in = getAssets().open("testmp4.mp4");
            Log.d(TAG, "file length : " + in.available());

            newPath = getCacheDir().getAbsolutePath() + "/test.mp4";

            FileOutputStream out = new FileOutputStream(new File(newPath));
            int length = 0;
            byte[] buf = new byte[1024];
            while ((length = in.read(buf)) != -1) {
                out.write(buf, 0, length);
                out.flush();
            }

            out.close();
            in.close();

            Toast.makeText(this, "Copy success!", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
