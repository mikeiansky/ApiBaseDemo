package com.github.neowen.apibasedemo.design;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.github.neowen.apibasedemo.BaseActivity;
import com.github.neowen.apibasedemo.R;
import com.github.neowen.apibasedemo.design.loadmore.PageListViewHelper;
import com.github.neowen.apibasedemo.design.loadmore.PageRecyclerViewHelper;
import com.github.neowen.apibasedemo.design.loadmore.RecyclerViewAdapter;
import com.github.neowen.apibasedemo.utils.Utils;
import com.winson.widget.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @date on 2018/9/17
 * @Author Winson
 */
public class LoadMoreActivity extends BaseActivity {

    public static final String TAG = LoadMoreActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_load_more);

        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setVisibility(View.GONE);

        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            datas.add(" position : " + i);
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(new RecyclerView.Adapter() {
//            @Override
//            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_list_item, parent, false);
//                MyRefreshActivity.MyViewHolder viewHolder = new MyRefreshActivity.MyViewHolder(root);
//                Log.d(TAG, "onCreateViewHolder ---> ");
//                return viewHolder;
//            }
//
//            @Override
//            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//                ((TextView)holder.itemView.findViewById(R.id.title)).setText("position -- " + position);
//                Log.d(TAG, "onBindViewHolder ---> ");
//            }
//
//            @Override
//            public int getItemCount() {
//                return 30;
//            }
//
//        });
//        PageRecyclerViewHelper pageRecyclerViewHelper = new PageRecyclerViewHelper(recyclerView);

        RecyclerViewAdapter<String> rca = new RecyclerViewAdapter<String>(this, datas, R.layout.text_list_item) {
            @Override
            public void bindItemView(RecyclerView.ViewHolder holder, String s, int position) {
                ((TextView) holder.itemView.findViewById(R.id.title)).setText(s);
            }
        };
        final PageRecyclerViewHelper<String> pageRecyclerViewHelper = new PageRecyclerViewHelper<>(recyclerView, rca, R.layout.load_more_foot);
        pageRecyclerViewHelper.setOnPageLoadListener(new PageRecyclerViewHelper.OnPageLoadListener() {
            @Override
            public void onLoadMore() {
                int pageIndex = pageRecyclerViewHelper.getPageIndex();
                List<String> datas = new ArrayList<>();
                int start = datas.size();
                if (pageIndex >= 5) {
                    for (int i = 0; i < 5; i++) {
                        datas.add("position " + (start + i));
                    }
                } else {
                    int region = pageRecyclerViewHelper.getPageSize();
                    for (int i = 0; i < region; i++) {
                        datas.add("position " + (start + i));
                    }
                }
                pageRecyclerViewHelper.addPageData(datas);
            }

            @Override
            public void onLoadFinish() {

            }
        });

//        final PageListViewHelper<String> pageListViewHelper = testListView(listView);
        findViewById(R.id.refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> datas = new ArrayList<>();
                int region = pageRecyclerViewHelper.getPageSize();
                for (int i = 0; i < region; i++) {
                    datas.add("position " + i);
                }
                pageRecyclerViewHelper.refreshData(datas);
            }
        });
    }

    @NonNull
    private PageListViewHelper<String> testListView(ListView listView) {
        CommonAdapter adapter = Utils.getTestAdapter(this);
        final PageListViewHelper<String> pageListViewHelper = new PageListViewHelper<String>(listView, adapter, R.layout.load_more_foot);
        pageListViewHelper.setOnPageChangeListener(new PageListViewHelper.OnPageChangeListener() {
            @Override
            public void onLoadMore() {
                Log.d(TAG, "onLoadMore ---------> ");
                int pageIndex = pageListViewHelper.getPageIndex();
                List<String> datas = new ArrayList<>();
                int start = datas.size();
                if (pageIndex >= 5) {
                    for (int i = 0; i < 5; i++) {
                        datas.add("position " + (start + i));
                    }
                } else {
                    int region = pageListViewHelper.getPageSize();
                    for (int i = 0; i < region; i++) {
                        datas.add("position " + (start + i));
                    }
                }
                pageListViewHelper.addPageData(datas);
            }

            @Override
            public void onLoadFinish() {
                Log.d(TAG, "onLoadFinish ---------> ");

            }
        });
        return pageListViewHelper;
    }

}
