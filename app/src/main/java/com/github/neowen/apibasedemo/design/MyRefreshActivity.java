package com.github.neowen.apibasedemo.design;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.neowen.apibasedemo.BaseActivity;
import com.github.neowen.apibasedemo.R;
import com.github.neowen.apibasedemo.common.CommonAdapter;
import com.github.neowen.apibasedemo.common.ViewHolder;
import com.github.neowen.apibasedemo.design.refresh.PullRefreshListView;
import com.github.neowen.apibasedemo.design.refresh.PullRefreshScrollView;
import com.github.neowen.apibasedemo.design.refresh.RefreshView;

import java.util.ArrayList;
import java.util.List;

public class MyRefreshActivity extends BaseActivity {

    public static final String TAG = MyRefreshActivity.class.getSimpleName();

    RefreshView refreshView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_refresh);

        refreshView = (RefreshView) findViewById(R.id.refresh_view);
        View headView = LayoutInflater.from(this).inflate(R.layout.head_view, refreshView, false);
//        PullRefreshScrollView contentView = (PullRefreshScrollView) LayoutInflater.from(this).inflate(R.layout.content_view, refreshView, false);
        PullRefreshListView contentView = (PullRefreshListView) LayoutInflater.from(this).inflate(R.layout.pull_refresh_list_view, refreshView, false);
        refreshView.addHeadView(headView);
        refreshView.addContentView(contentView);
        refreshView.setRefreshListener(new RefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshView.refreshComplete();
                    }
                }, 3000);
            }
        });

        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            datas.add(" position : " + i);
        }

        contentView.setAdapter(new CommonAdapter<String>(
                this, R.layout.text_list_item, datas
        ) {
            @Override
            public void convert(ViewHolder viewHolder, String obj, int position) {
                ((TextView)viewHolder.findViewById(R.id.title)).setText(obj);
            }
        });

    }

}
