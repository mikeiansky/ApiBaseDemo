package com.github.neowen.apibasedemo.design;

import com.github.neowen.apibasedemo.ApiItem;
import com.github.neowen.apibasedemo.ApiItemListActivity;

/**
 * Created by ZWX on 15/10/15.
 */
public class DesignListActivity extends ApiItemListActivity {

    @Override
    protected void addApiItemData() {
        super.addApiItemData();

        ApiItem apiItem = new ApiItem("swipeBack", SwipeBackActivity.class);
        mApiItems.add(apiItem);
    }
}
