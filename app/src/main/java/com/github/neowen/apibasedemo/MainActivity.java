package com.github.neowen.apibasedemo;

import com.github.neowen.apibasedemo.animation.AnimationListActivity;
import com.github.neowen.apibasedemo.design.DesignListActivity;
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
    }

}
