package com.github.neowen.apibasedemo.design;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.neowen.apibasedemo.BaseActivity;
import com.github.neowen.apibasedemo.R;
import com.github.neowen.apibasedemo.common.CommonAdapter;
import com.github.neowen.apibasedemo.common.ViewHolder;
import com.github.neowen.apibasedemo.design.loadmore.PageListViewHelper;
import com.github.neowen.apibasedemo.utils.Utils;

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

        com.winson.widget.CommonAdapter adapter = Utils.getTestAdapter(this);

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

        findViewById(R.id.refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> datas = new ArrayList<>();
                int region = pageListViewHelper.getPageSize();
                for (int i = 0; i < region; i++) {
                    datas.add("position " + i);
                }
                pageListViewHelper.refreshData(datas);
            }
        });

    }

}
