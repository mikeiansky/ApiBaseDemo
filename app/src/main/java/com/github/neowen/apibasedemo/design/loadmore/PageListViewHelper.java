package com.github.neowen.apibasedemo.design.loadmore;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.winson.widget.CommonAdapter;

import java.util.List;

/**
 * @date on 2018/9/17
 * @Author Winson
 */
public class PageListViewHelper<T> {

    public interface OnPageChangeListener {

        void onLoadMore();

        void onLoadFinish();

    }

    private ListView listView;
    private View footView;
    private CommonAdapter<T> adapter;
    private boolean hasMore = true;
    private boolean onLoading = false;
    private boolean cancel = false;
    private int pageIndex = 1;
    private int pageSize = 20;
    private OnPageChangeListener loadMoreListener;
    private Runnable currentLoadMoreRunnable;
    private int loadMoreDelayDuration = 2000;

    public void setLoadMoreDelayDuration(int duration) {
        loadMoreDelayDuration = duration;
    }

    public void setOnPageChangeListener(OnPageChangeListener listener) {
        loadMoreListener = listener;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public CommonAdapter getCommonAdapter() {
        return adapter;
    }

    public PageListViewHelper(ListView listView, CommonAdapter<T> adapter, int footViewLayoutId) {
        View footView = LayoutInflater.from(listView.getContext()).inflate(footViewLayoutId, listView, false);
        this.listView = listView;
        this.listView.setAdapter(adapter);
        this.adapter = adapter;
        this.listView.setOnScrollListener(onScrollListener);
        this.footView = footView;
        this.listView.addFooterView(footView);
    }

    public PageListViewHelper(ListView listView, CommonAdapter<T> adapter, View footView) {
        this.listView = listView;
        this.listView.setAdapter(adapter);
        this.adapter = adapter;
        this.listView.setOnScrollListener(onScrollListener);
        this.footView = footView;
        this.listView.addFooterView(footView);
    }

    AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (firstVisibleItem != 0
                    && visibleItemCount != 0
                    && hasMore
                    && !onLoading
                    && (firstVisibleItem + visibleItemCount + 1) >= totalItemCount) {
                // onLoadPageData
                loadMore();
            }
        }

    };

    private void loadMore() {
        onLoading = true;
        if (loadMoreListener != null) {
            loadMoreListener.onLoadMore();
        }
    }

    private void loadFinish() {
        hasMore = false;
        onLoading = false;
        listView.removeFooterView(footView);
        if (loadMoreListener != null) {
            loadMoreListener.onLoadFinish();
        }
    }

    public void refreshData(List<T> datas) {
        onLoading = false;
        if (datas != null) {
            pageIndex = 2;
            int size = datas.size();
            if (size < pageSize) {
                hasMore = false;
                loadFinish();
            } else {
                hasMore = true;
            }
        } else {
            loadFinish();
            hasMore = false;
        }

        if (adapter.getData() == null) {
            adapter.replaceData(datas);
        } else {
            if (adapter.getData() != datas) {
                adapter.getData().clear();
                adapter.addData(datas);
            }
        }
        if (hasMore) {
            listView.removeFooterView(footView);
            listView.addFooterView(footView);
            footView.setVisibility(View.VISIBLE);
        }
    }

    public void addPageData(final List<T> datas) {
        currentLoadMoreRunnable = new Runnable() {
            @Override
            public void run() {
                if (cancel) {
                    return;
                }
                onLoading = false;
                if (datas != null) {
                    pageIndex++;
                    int size = datas.size();
                    if (size < pageSize) {
                        hasMore = false;
                        loadFinish();
                    } else {
                        hasMore = true;
                    }
                } else {
                    hasMore = false;
                }
                adapter.addData(datas);
            }
        };
        listView.postDelayed(currentLoadMoreRunnable, loadMoreDelayDuration);
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void cancel() {
        cancel = true;
        listView.removeCallbacks(currentLoadMoreRunnable);
    }

}
