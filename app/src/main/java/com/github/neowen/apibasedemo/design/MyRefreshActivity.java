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
import com.github.neowen.apibasedemo.design.refresh.PullRefreshLayout;
import com.github.neowen.apibasedemo.design.refresh.PullRefreshScrollView;

import java.util.ArrayList;
import java.util.List;

public class MyRefreshActivity extends BaseActivity {

    public static final String TAG = MyRefreshActivity.class.getSimpleName();

    PullRefreshLayout pullRefreshView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_refresh);

        pullRefreshView = (PullRefreshLayout) findViewById(R.id.refresh_view);
        View headView = LayoutInflater.from(this).inflate(R.layout.head_view, pullRefreshView, false);
        PullRefreshScrollView contentView1 = (PullRefreshScrollView) LayoutInflater.from(this).inflate(R.layout.content_view, pullRefreshView, false);
        PullRefreshListView contentView2 = (PullRefreshListView) LayoutInflater.from(this).inflate(R.layout.pull_refresh_list_view, pullRefreshView, false);
        View contentView3 = LayoutInflater.from(this).inflate(R.layout.pull_refresh_native_view, pullRefreshView, false);
        pullRefreshView.addHeadView(headView);
//        pullRefreshView.addContentView(contentView1);
        pullRefreshView.addNativeView(contentView3);
        pullRefreshView.setRefreshListener(new PullRefreshLayout.OnRefreshListener() {
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

        contentView1.findViewById(R.id.action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "action test!!!!!!", Toast.LENGTH_SHORT).show();
            }
        });

        contentView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(view.getContext(), "position : " + position, Toast.LENGTH_SHORT).show();
            }
        });

        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            datas.add(" position : " + i);
        }

        contentView2.setAdapter(new CommonAdapter<String>(
                this, R.layout.text_list_item, datas
        ) {
            @Override
            public void convert(ViewHolder viewHolder, String obj, int position) {
                ((TextView) viewHolder.findViewById(R.id.title)).setText(obj);
//                if(position == 4){
//                    viewHolder.getConvertView().setPressed(true);
//                }
            }
        });

//        contentView.getChildAt(4).setPressed(true);

    }

}
