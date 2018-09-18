package com.github.neowen.apibasedemo.design.loadmore;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

/**
 * @date on 2018/9/17
 * @Author Winson
 */
public class PageRecyclerViewHelper {

    private static final String TAG = PageRecyclerViewHelper.class.getSimpleName();

    RecyclerView recyclerView;
    boolean onBottom;

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
                if (!onBottom && isOnBottom(recyclerView)) {
                    onBottom = true;
                    Toast.makeText(recyclerView.getContext(), "onBottom", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    static boolean isOnBottom(RecyclerView recyclerView) {
        LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();
        int totalCount = llm.getItemCount();

        int lastPosition = llm.findLastVisibleItemPosition();

        if ((lastPosition + 1) >= totalCount) {
            Log.d(TAG, "isOnBottom : " + lastPosition + " , totalCount : " + totalCount);
            return true;
        }

        return false;
    }

}
