package com.github.neowen.apibasedemo.design.multitypelistview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Winson on 2016/1/6.
 */
public abstract class TypeViewHolder<T extends Item> {

    T mItem;
    int mType;
    View mRootView;
    Context mContext;
    ViewGroup mParent;

    public TypeViewHolder(Context context, ViewGroup parent, Item item) {
        mItem = (T) item;
        mType = item.mType;
        mParent = parent;
        mContext = context;
        mRootView = onCreateView();
        mRootView.setTag(this);
        updateData();
    }

    public void updateItem(Item item) {
        mItem = (T) item;
        updateData();
    }

    protected abstract View onCreateView();

    protected abstract void updateData();

    public View getRootView() {
        return mRootView;
    }

}
