package com.github.neowen.apibasedemo.view;

/**
 * Created by zhouwenxiang on 2015/12/2.
 */
public class ListItem {

    public static final int RED = 0;
    public static final int GREEN = 1;
    public static final int BLUE = 2;

    public final int mViewType;
    public String mTitle;

    public ListItem(int viewType) {
        mViewType = viewType;
    }

}
