package com.github.neowen.apibasedemo.base;

import android.os.Bundle;

import com.github.neowen.apibasedemo.ApiItemFragment;
import com.github.neowen.apibasedemo.ApiItemListFragmentActivity;

public class BaseApiActivity extends ApiItemListFragmentActivity {

    @Override
    protected void addApiItemData() {
        super.addApiItemData();
        ApiItemFragment apiItem = new ApiItemFragment("Dialog", TestDialogFragment.class);
        mApiItems.add(apiItem);

        apiItem = new ApiItemFragment("My Service", MyServiceFragment.class);
        mApiItems.add(apiItem);
    }

}
