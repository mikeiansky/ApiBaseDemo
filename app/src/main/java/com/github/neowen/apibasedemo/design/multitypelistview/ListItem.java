package com.github.neowen.apibasedemo.design.multitypelistview;

import java.util.ArrayList;

/**
 * Created by Winson on 2016/1/6.
 */
public class ListItem extends Item {

    ArrayList<String> mList;

    public ListItem(int type, String title) {
        super(type, title);
    }

    public void setList(ArrayList<String> list) {
        mList = list;
    }

}
