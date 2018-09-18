package com.github.neowen.apibasedemo.design;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.neowen.apibasedemo.BaseActivity;
import com.github.neowen.apibasedemo.R;
import com.github.neowen.apibasedemo.common.CommonAdapter;
import com.github.neowen.apibasedemo.common.ViewHolder;
import com.github.neowen.apibasedemo.design.refresh.PullRefreshGridView;
import com.github.neowen.apibasedemo.design.refresh.PullRefreshHeadLayout;
import com.github.neowen.apibasedemo.design.refresh.PullRefreshListView;
import com.github.neowen.apibasedemo.design.refresh.PullRefreshLayout;
import com.github.neowen.apibasedemo.design.refresh.PullRefreshRecyclerView;
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
        PullRefreshHeadLayout headView2 = (PullRefreshHeadLayout) LayoutInflater.from(this).inflate(R.layout.pull_refresh_head_view, pullRefreshView, false);
        PullRefreshScrollView contentView1 = (PullRefreshScrollView) LayoutInflater.from(this).inflate(R.layout.content_view, pullRefreshView, false);
        PullRefreshListView contentView2 = (PullRefreshListView) LayoutInflater.from(this).inflate(R.layout.pull_refresh_list_view, pullRefreshView, false);
        View contentView3 = LayoutInflater.from(this).inflate(R.layout.pull_refresh_native_view, pullRefreshView, false);
        PullRefreshRecyclerView contentView4 = (PullRefreshRecyclerView) LayoutInflater.from(this).inflate(R.layout.pull_refresh_recycler_view, pullRefreshView, false);
        PullRefreshGridView contentView5 = (PullRefreshGridView) LayoutInflater.from(this).inflate(R.layout.pull_refresh_grid_view, pullRefreshView, false);

        //        pullRefreshView.addHeadView(headView);
        pullRefreshView.addHeadWatcher(headView2);

//        pullRefreshView.addContentWatcher(contentView1);
        pullRefreshView.addContentWatcher(contentView2);
//        pullRefreshView.addContentView(contentView3);
//        pullRefreshView.addContentWatcher(contentView4);
//        pullRefreshView.addContentWatcher(contentView5);
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
            }
        });

        contentView5.setAdapter(new CommonAdapter<String>(
                this, R.layout.text_list_item, datas
        ) {
            @Override
            public void convert(ViewHolder viewHolder, String obj, int position) {
                ((TextView) viewHolder.findViewById(R.id.title)).setText(obj);
            }
        });

        contentView4.setLayoutManager(new LinearLayoutManager(this));
        contentView4.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_list_item, parent, false);
                MyViewHolder viewHolder = new MyViewHolder(root);
                Log.d(TAG, "onCreateViewHolder");
                return viewHolder;
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                ((TextView)holder.itemView.findViewById(R.id.title)).setText("position -- " + position);
                Log.d(TAG, "onBindViewHolder");
            }

            @Override
            public int getItemCount() {
                return 30;
            }
        });

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(View itemView) {
            super(itemView);
        }

    }

}
