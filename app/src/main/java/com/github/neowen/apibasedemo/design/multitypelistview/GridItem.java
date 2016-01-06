package com.github.neowen.apibasedemo.design.multitypelistview;

import java.util.ArrayList;

/**
 * Created by Winson on 2016/1/6.
 */
public class GridItem extends Item {
    ArrayList<String> mGrid;

    public GridItem(int type, String title) {
        super(type, title);
    }

    public void setGrid(ArrayList<String> list) {
        mGrid = list;
    }

}
