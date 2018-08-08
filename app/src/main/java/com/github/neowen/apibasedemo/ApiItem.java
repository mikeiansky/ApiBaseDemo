package com.github.neowen.apibasedemo;

import android.app.Activity;

/**
 * Created by ZWX on 15/10/15.
 */
public class ApiItem {

    public final String mTitle;
    public final Class<? extends Activity> mClazz;

    public ApiItem(String mTitle, Class<? extends Activity> mClazz) {
        this.mTitle = mTitle;
        this.mClazz = mClazz;

    }
}
