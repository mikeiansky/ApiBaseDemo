package com.github.neowen.apibasedemo.design.multitypelistview;

/**
 * Created by Winson on 2016/1/6.
 */
public class Item {

    public static final int TYPE_LIST = 1;
    public static final int TYPE_GRID = 2;
    public static final int TYPE_NORMAL = 0;

    int mType;
    String mTitle;

    public Item(int type, String title) {
        mTitle = title;
        mType = type;
    }

}
