package com.github.neowen.apibasedemo.drawable;

import com.github.neowen.apibasedemo.ApiItem;
import com.github.neowen.apibasedemo.ApiItemListActivity;

/**
 * Created by zhouwenxiang on 2015/12/10.
 */
public class DrawableListActivity extends ApiItemListActivity {

    @Override
    protected void addApiItemData() {
        ApiItem apiItem = new ApiItem("VectorDrawable",VectorDrawableActivity.class);
        mApiItems.add(apiItem);
    }
}
