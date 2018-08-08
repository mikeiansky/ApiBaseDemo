package com.github.neowen.apibasedemo.drawable;

import com.github.neowen.apibasedemo.ApiItem;
import com.github.neowen.apibasedemo.ApiItemListActivity;

/**
 * Created by zhouwenxiang on 2015/12/10.
 */
public class DrawableListActivity extends ApiItemListActivity {

    @Override
    protected void addApiItemData() {

        ApiItem apiItem = new ApiItem("Vector Drawable", VectorDrawableActivity.class);
        mApiItems.add(apiItem);

        apiItem = new ApiItem("Custom Drawable", CustomDrawableActivity.class);
        mApiItems.add(apiItem);

        apiItem = new ApiItem("Shadow Drawable", ShadowActivity.class);
        mApiItems.add(apiItem);
    }
}
