package com.github.neowen.apibasedemo.sdk;

import com.github.neowen.apibasedemo.ApiItem;
import com.github.neowen.apibasedemo.ApiItemListActivity;

/**
 * Created by Winson on 2015/12/31.
 */
public class SdkListActivity extends ApiItemListActivity {

    @Override
    protected void addApiItemData() {

        ApiItem apiItem = new ApiItem("QQ Sdk", QQSdkActivity.class);
        mApiItems.add(apiItem);

        apiItem = new ApiItem("Weibo Sdk", WeiboSdkActivity.class);
        mApiItems.add(apiItem);

        apiItem = new ApiItem("Wechat Sdk", WechatSdkActivity.class);
        mApiItems.add(apiItem);
    }
}
