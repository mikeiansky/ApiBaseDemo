package com.github.neowen.apibasedemo.design.multitypelistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.neowen.apibasedemo.R;

/**
 * Created by Winson on 2016/1/6.
 */
public class NormalItemViewHolder extends TypeViewHolder {

    public NormalItemViewHolder(Context context, ViewGroup parent, Item item) {
        super(context, parent, item);
    }

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(mContext).inflate(R.layout.normal_item_holder, mParent, false);
        return root;
    }

    @Override
    protected void updateData() {

    }
}
