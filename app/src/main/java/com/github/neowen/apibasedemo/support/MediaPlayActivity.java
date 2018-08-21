package com.github.neowen.apibasedemo.support;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

import com.github.neowen.apibasedemo.BaseActivity;
import com.github.neowen.apibasedemo.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MediaPlayActivity extends BaseActivity implements View.OnClickListener {

    public static final String TAG = MediaPlayActivity.class.getSimpleName();

    VideoView videoView;
    String path = Environment.getExternalStorageDirectory().getPath()+"/testmp4.mp4";
    String path2 = "file:///android_asset/testmp4.mp4";
    String newPath = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_play_layout);

        videoView = (VideoView) findViewById(R.id.video_view);
//        videoView.setVideoURI(Uri.parse("file:///android_asset/testmp4.mp4"));
//        videoView.setVideoURI(Uri.parse(path));
//        videoView.start();

        findViewById(R.id.copy).setOnClickListener(this);
        findViewById(R.id.play).setOnClickListener(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.video_content, new VideoFragment())
                .commit();

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(videoView!=null){
            videoView.pause();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.play:
                videoView.setVideoURI(Uri.parse(newPath));
                videoView.start();
                break;
            case R.id.copy:
                copy();
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
            while((length = in.read(buf))!=-1){
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
