package com.github.neowen.apibasedemo.design;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.neowen.apibasedemo.BaseActivity;
import com.github.neowen.apibasedemo.R;
import com.github.neowen.apibasedemo.common.CommonAdapter;
import com.github.neowen.apibasedemo.common.ViewHolder;
import com.github.neowen.apibasedemo.design.refresh.PullRefreshListView;
import com.github.neowen.apibasedemo.design.refresh.PullRefreshView;

import java.util.ArrayList;
import java.util.List;

public class MyRefreshActivity extends BaseActivity {

    public static final String TAG = MyRefreshActivity.class.getSimpleName();

    PullRefreshView pullRefreshView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_refresh);

        pullRefreshView = (PullRefreshView) findViewById(R.id.refresh_view);
        View headView = LayoutInflater.from(this).inflate(R.layout.head_view, pullRefreshView, false);
//        PullRefreshScrollView contentView = (PullRefreshScrollView) LayoutInflater.from(this).inflate(R.layout.content_view, refreshView, false);
        PullRefreshListView contentView = (PullRefreshListView) LayoutInflater.from(this).inflate(R.layout.pull_refresh_list_view, pullRefreshView, false);
        pullRefreshView.addHeadView(headView);
        pullRefreshView.addContentView(contentView);
        pullRefreshView.setRefreshListener(new PullRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullRefreshView.refreshComplete();
                    }
                }, 3000);
            }
        });

        contentView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(view.getContext(), "position : " + position , Toast.LENGTH_SHORT).show();
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
//                if(position == 4){
//                    viewHolder.getConvertView().setPressed(true);
//                }
            }
        });

//        contentView.getChildAt(4).setPressed(true);

    }

}
