package com.github.neowen.apibasedemo.performance;

import com.github.neowen.apibasedemo.ApiItem;
import com.github.neowen.apibasedemo.ApiItemListActivity;

/**
 * Created by ZWX on 15/11/11.
 */
public class PerformanceListActivity extends ApiItemListActivity {

    @Override
    protected void addApiItemData() {

        ApiItem apiItem = new ApiItem("Memory", MemoryActivity.class);
        mApiItems.add(apiItem);

        apiItem = new ApiItem("Message", MessageActivity.class);
        mApiItems.add(apiItem);

    }
}
