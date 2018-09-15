package com.github.neowen.apibasedemo.design.refresh;

import android.view.View;

public interface PullRefreshHeadWatcher {

    /**
     * @param progress region is (0 - 100)
     */
    void onPullProgressUpdate(int progress);

    void onRefresh();

    void onRefreshComplete();

    View getStick();

}
