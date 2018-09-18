package com.github.neowen.apibasedemo.design.loadmore;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

/**
 * @date on 2018/9/17
 * @Author Winson
 */
public class PageRecyclerViewHelper<T> {

    private static final String TAG = PageRecyclerViewHelper.class.getSimpleName();

    public interface OnPageLoadListener {

        void onLoadMore();

        void onLoadFinish();

    }

    private long loadMoreDuration = 2000;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter<T> adapter;
    private boolean onLoading;
    private boolean cancel;
    private boolean hasMore = true;
    private int pageIndex = 1;
    private int pageSize = 20;
    private OnPageLoadListener onPageLoadListener;
    private Runnable loadMoreRunnable;

    static boolean isOnBottom(RecyclerView recyclerView) {
        LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();
        int totalCount = llm.getItemCount();
        int lastPosition = llm.findLastVisibleItemPosition();
        if ((lastPosition + 1) >= totalCount) {
            return true;
        }
        return false;
    }

    public void setLoadMoreDuration(long duration) {
        this.loadMoreDuration = duration;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setOnPageLoadListener(OnPageLoadListener onPageLoadListener) {
        this.onPageLoadListener = onPageLoadListener;
    }

    public PageRecyclerViewHelper(RecyclerView recyclerView, RecyclerViewAdapter<T> adapter, int footLayout) {
        this.recyclerView = recyclerView;
        this.recyclerView.setAdapter(adapter);
        this.adapter = adapter;
        this.adapter.setFootLayout(footLayout);
        this.adapter.showFoot(true);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!cancel && !onLoading && hasMore && isOnBottom(recyclerView)) {
                    // need load more
                    loadMore();
                }
            }
        });

    }

    public void cancel() {
        cancel = true;
        recyclerView.removeCallbacks(loadMoreRunnable);
    }

    private void loadFinish() {
        onLoading = false;
        hasMore = false;
        if (onPageLoadListener != null) {
            onPageLoadListener.onLoadFinish();
        }
    }

    private void loadMore() {
        onLoading = true;
        if (onPageLoadListener != null) {
            onPageLoadListener.onLoadMore();
        }
    }

    public void refreshData(List<T> datas) {
        // remove add page data runnable
        recyclerView.removeCallbacks(loadMoreRunnable);
        onLoading = false;
        if (datas == null) {
            loadFinish();
            return;
        }
        int size = datas.size();
        if (size >= pageSize) {
            // has more
            hasMore = true;
        } else {
            // no more data
            hasMore = false;
            loadFinish();
        }
        pageIndex = 2;
        adapter.replaceData(datas);
        adapter.showFoot(hasMore);

    }

    public void addPageData(final List<T> datas) {
        loadMoreRunnable = new Runnable() {
            @Override
            public void run() {
                onLoading = false;
                if (datas == null) {
                    loadFinish();
                    return;
                }
                int size = datas.size();
                if (size >= pageSize) {
                    // has more
                    hasMore = true;
                } else {
                    // no more data
                    hasMore = false;
                    loadFinish();
                }
                pageIndex++;
                adapter.addData(datas);
                adapter.showFoot(hasMore);
            }
        };
        recyclerView.postDelayed(loadMoreRunnable, loadMoreDuration);
    }


}
