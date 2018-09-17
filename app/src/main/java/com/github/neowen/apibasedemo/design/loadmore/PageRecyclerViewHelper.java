package com.github.neowen.apibasedemo.design.loadmore;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * @date on 2018/9/17
 * @Author Winson
 */
public class PageRecyclerViewHelper {

    RecyclerView recyclerView;

    public PageRecyclerViewHelper(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                linearLayoutManager.findFirstVisibleItemPosition();
                linearLayoutManager.findLastVisibleItemPosition();

                RecyclerView.LayoutManager layoutManager = null;
                GridLayoutManager glm = null;
                glm.findLastVisibleItemPosition();

            }
        });
    }
}
