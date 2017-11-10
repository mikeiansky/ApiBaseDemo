package com.github.neowen.apibasedemo.support;

import com.github.neowen.apibasedemo.ApiItem;
import com.github.neowen.apibasedemo.ApiItemListActivity;

/**
 * Created by ZWX on 15/10/15.
 */
public class SupportListA extends ApiItemListActivity {

    @Override
    protected void addApiItemData() {
        super.addApiItemData();

        ApiItem apiItem = new ApiItem("ViewDragHelper", ViewDragHelperA.class);
        mApiItems.add(apiItem);

        apiItem = new ApiItem("WebView", MyWebViewActivity.class);
        mApiItems.add(apiItem);

    }
}
