package com.github.neowen.apibasedemo;

import com.github.neowen.apibasedemo.support.SupportListA;
import com.github.neowen.apibasedemo.support.ViewDragHelperA;

public class MainActivity extends ApiItemListActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    protected void addApiItemData(){
        ApiItem apiItem = new ApiItem("Support", SupportListA.class);
        mApiItems.add(apiItem);
    }

}
