package com.github.neowen.apibasedemo.design.refresh;

import android.view.View;

public interface PullRefreshHeadWatcher {

    /**
     * @param progress region is (0 - 100)
     */
    void onPullProgressUpdate(int progress);

    /**
     * It will be call when refresh start
     */
    void onRefresh();

    /**
     * It will be call when refresh completed
     */
    void onRefreshComplete();

    /**
     * @return the view will be add to pull refresh layout
     */
    View getStick();

}
