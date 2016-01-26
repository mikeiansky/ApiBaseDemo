package com.github.neowen.apibasedemo.attr;

import com.github.neowen.apibasedemo.ApiItem;
import com.github.neowen.apibasedemo.ApiItemListActivity;

/**
 * Created by Winson on 2016/1/26.
 */
public class AttrListActivity extends ApiItemListActivity {

    @Override
    protected void addApiItemData() {

        // ?attr is not apply now ,but when you used for difined attr , it will be used
        ApiItem item = new ApiItem("Attr Normal", AttrActivity.class);
        mApiItems.add(item);

    }
}
