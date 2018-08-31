package com.github.neowen.apibasedemo;

import android.app.Activity;

/**
 * Created by ZWX on 15/10/15.
 */
public class ApiItemFragment {

    public final String mTitle;
    public final Class<? extends BaseFragment> mClazz;

    public ApiItemFragment(String mTitle, Class<? extends BaseFragment> mClazz) {
        this.mTitle = mTitle;
        this.mClazz = mClazz;
    }

}
