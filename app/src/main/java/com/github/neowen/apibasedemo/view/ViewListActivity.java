package com.github.neowen.apibasedemo.view;


import com.github.neowen.apibasedemo.ApiItem;
import com.github.neowen.apibasedemo.ApiItemListActivity;

/**
 * Created by ZWX on 15/10/15.
 */
public class ViewListActivity extends ApiItemListActivity {

    @Override
    protected void addApiItemData() {
        super.addApiItemData();
        ApiItem apiItem = new ApiItem("Touch", TouchActivity.class);
        mApiItems.add(apiItem);
    }

}
