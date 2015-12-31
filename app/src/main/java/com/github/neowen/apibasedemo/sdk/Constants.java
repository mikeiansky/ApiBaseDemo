package com.github.neowen.apibasedemo.sdk;

/**
 * Created by Winson on 2015/12/31.
 */
public interface Constants {
    public static final String APP_KEY = "2249124552"; // 应用的APP_KEY
    public static final String REDIRECT_URL = "http://www.baidu.com";// 应用的回调页
    public static final String SCOPE = // 应用申请的高级权限
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";
}
