package com.github.neowen.apibasedemo.animation;


import android.view.ViewPropertyAnimator;

import com.github.neowen.apibasedemo.ApiItem;
import com.github.neowen.apibasedemo.ApiItemListActivity;
import com.github.neowen.apibasedemo.view.ListViewAnimationActivity;
import com.github.neowen.apibasedemo.view.TouchActivity;

/**
 * Created by ZWX on 15/10/15.
 */
public class AnimationListActivity extends ApiItemListActivity {

    @Override
    protected void addApiItemData() {
        super.addApiItemData();
        ApiItem apiItem = new ApiItem("ViewPropertyAnimator", ViewPropertyAnimatorActivity.class);
        mApiItems.add(apiItem);
    }

}
