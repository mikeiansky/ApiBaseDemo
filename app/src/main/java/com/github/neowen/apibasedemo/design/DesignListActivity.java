package com.github.neowen.apibasedemo.design;

import com.github.neowen.apibasedemo.ApiItem;
import com.github.neowen.apibasedemo.ApiItemListActivity;
import com.github.neowen.apibasedemo.design.customdialog.CustomDialogActivity;
import com.github.neowen.apibasedemo.design.emptyview.DialogFragmentEmptyActivity;
import com.github.neowen.apibasedemo.design.emptyview.EmptyViewActivity;
import com.github.neowen.apibasedemo.design.multitypelistview.MultiTypeListViewActivity;
import com.github.neowen.apibasedemo.design.onceloadpager.OnceLoadPagerActivity;
import com.github.neowen.apibasedemo.design.swipebacklayout.SwipeBackActivity;

/**
 * Created by ZWX on 15/10/15.
 */
public class DesignListActivity extends ApiItemListActivity {

    @Override
    protected void addApiItemData() {
        super.addApiItemData();

        ApiItem apiItem = new ApiItem("SwipeBack", SwipeBackActivity.class);
        mApiItems.add(apiItem);

        apiItem = new ApiItem("Custom Dialog", CustomDialogActivity.class);
        mApiItems.add(apiItem);

        apiItem = new ApiItem("Onceload Pager", OnceLoadPagerActivity.class);
        mApiItems.add(apiItem);

        apiItem = new ApiItem("MultiType ListView", MultiTypeListViewActivity.class);
        mApiItems.add(apiItem);

        apiItem = new ApiItem("Empty View", EmptyViewActivity.class);
        mApiItems.add(apiItem);

        apiItem = new ApiItem("Dialog Empty View", DialogFragmentEmptyActivity.class);
        mApiItems.add(apiItem);

        apiItem = new ApiItem("Custom Notification", NotificationActivity.class);
        mApiItems.add(apiItem);
    }
}
