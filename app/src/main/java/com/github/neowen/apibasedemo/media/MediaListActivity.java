package com.github.neowen.apibasedemo.media;

import com.github.neowen.apibasedemo.ApiItem;
import com.github.neowen.apibasedemo.ApiItemListActivity;

/**
 * Created by ZWX on 15/10/21.
 */
public class MediaListActivity extends ApiItemListActivity {

    @Override
    protected void addApiItemData() {
        super.addApiItemData();

        ApiItem apiItem = new ApiItem("MediaDatabase", MediaDatabaseActivity.class);
        mApiItems.add(apiItem);
    }
}
