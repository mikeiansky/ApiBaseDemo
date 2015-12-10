package com.github.neowen.apibasedemo;

import android.util.Log;
import android.util.SparseArray;

import com.github.neowen.apibasedemo.animation.AnimationListActivity;
import com.github.neowen.apibasedemo.common.DebugUtils;
import com.github.neowen.apibasedemo.design.DesignListActivity;
import com.github.neowen.apibasedemo.drawable.DrawableListActivity;
import com.github.neowen.apibasedemo.media.MediaListActivity;
import com.github.neowen.apibasedemo.performance.PerformanceListActivity;
import com.github.neowen.apibasedemo.support.SupportListA;
import com.github.neowen.apibasedemo.view.ViewListActivity;

public class MainActivity extends ApiItemListActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    protected void addApiItemData() {

        ApiItem apiItem = new ApiItem("Support", SupportListA.class);
        mApiItems.add(apiItem);

        apiItem = new ApiItem("Design", DesignListActivity.class);
        mApiItems.add(apiItem);

        apiItem = new ApiItem("View", ViewListActivity.class);
        mApiItems.add(apiItem);

        apiItem = new ApiItem("Animation", AnimationListActivity.class);
        mApiItems.add(apiItem);

        apiItem = new ApiItem("Media", MediaListActivity.class);
        mApiItems.add(apiItem);

        apiItem = new ApiItem("Performance", PerformanceListActivity.class);
        mApiItems.add(apiItem);

        apiItem = new ApiItem("Drawable", DrawableListActivity.class);
        mApiItems.add(apiItem);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if(DebugUtils.debug){
            Log.d(TAG, "onTrimMemory --> : " +level);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(DebugUtils.debug){
            Log.d(TAG, "onDestroy -->");
        }
    }
}
