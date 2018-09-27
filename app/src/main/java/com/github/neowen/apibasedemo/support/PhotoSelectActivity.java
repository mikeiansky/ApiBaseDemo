package com.github.neowen.apibasedemo.support;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.neowen.apibasedemo.BaseActivity;
import com.github.neowen.apibasedemo.R;
import com.github.neowen.apibasedemo.support.photo.PhotoSelectUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @date on 2018/9/18
 * @Author Winson
 */
public class PhotoSelectActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.select_by_album)
    Button selectByAlbum;
    @BindView(R.id.select_by_camera)
    Button selectByCamera;
    @BindView(R.id.result)
    TextView result;

    PhotoSelectUtils photoSelectUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_photo_select);
        ButterKnife.bind(this);

        selectByAlbum.setOnClickListener(this);
        selectByCamera.setOnClickListener(this);
        photoSelectUtils = new PhotoSelectUtils(this);
        photoSelectUtils.setOnPhotoSelectListener(new PhotoSelectUtils.OnPhotoSelectListener() {
            @Override
            public void onPhotoSelect(File file) {
                result.setText("select result : " + file.getAbsolutePath());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG", "onActivityResult resultCode : " + resultCode);
        photoSelectUtils.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.select_by_album:
                photoSelectUtils.selectFromPhotoAlbum();
                break;
            case R.id.select_by_camera:
                photoSelectUtils.selectByCamera();
                break;
        }
    }
}
