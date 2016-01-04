package com.github.neowen.apibasedemo.csdn;

import com.github.neowen.apibasedemo.ApiItem;
import com.github.neowen.apibasedemo.ApiItemListActivity;
import com.github.neowen.apibasedemo.R;
import com.github.neowen.apibasedemo.csdn.circle_media.CircleNoBlackVideoActivity;

/**
 * Created by Winson on 2016/1/4.
 */
public class CsdnListActivity extends ApiItemListActivity {


    @Override
    protected void addApiItemData() {

        ApiItem apiItem = new ApiItem(getString(R.string.circle_no_black_video), CircleNoBlackVideoActivity.class);
        mApiItems.add(apiItem);

    }
}
