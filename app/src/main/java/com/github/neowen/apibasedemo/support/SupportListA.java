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

        apiItem = new ApiItem("Select Photo", PhotoSelectActivity.class);
        mApiItems.add(apiItem);

        apiItem = new ApiItem("WebView", MyWebViewActivity.class);
        mApiItems.add(apiItem);

        apiItem = new ApiItem("Printer", PrinterActivity.class);
        mApiItems.add(apiItem);

        apiItem = new ApiItem("Upgrade", UpgradeActivity.class);
        mApiItems.add(apiItem);

        apiItem = new ApiItem("Crash Log", CrashLogActivity.class);
        mApiItems.add(apiItem);

        apiItem = new ApiItem("Location", LocationActivity.class);
        mApiItems.add(apiItem);

        apiItem = new ApiItem("Media", MediaPlayActivity.class);
        mApiItems.add(apiItem);

        apiItem = new ApiItem("Handler", HandlerActivity.class);
        mApiItems.add(apiItem);
    }
}
