package com.github.neowen.apibasedemo.design;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.github.neowen.apibasedemo.BaseActivity;
import com.github.neowen.apibasedemo.R;
import com.github.neowen.apibasedemo.design.refresh.RefreshView;

public class MyRefreshActivity extends BaseActivity {

    public static final String TAG = MyRefreshActivity.class.getSimpleName();

    RefreshView refreshView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_refresh);

        refreshView = (RefreshView) findViewById(R.id.refresh_view);
        View headView = LayoutInflater.from(this).inflate(R.layout.head_view, refreshView, false);
        View contentView = LayoutInflater.from(this).inflate(R.layout.content_view, refreshView, false);
        refreshView.addHeadView(headView);
        refreshView.addContentView(contentView);

    }

}
