package com.github.neowen.apibasedemo.design.loadmore;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @date on 2018/9/18
 * @Author Winson
 */
public abstract class RecyclerViewAdapter<T> extends RecyclerView.Adapter {

    private int TYPE_NORMAL = 0;
    private int TYPE_FOOT = 1;

    private Context context;
    private List<T> datas;
    private int itemLayout;
    private int footLayout;
    private boolean showFoot;

    public RecyclerViewAdapter(Context context, List<T> datas, int itemLayout) {
        this(context, datas, itemLayout, 0);
    }

    public RecyclerViewAdapter(Context context, List<T> datas, int itemLayout, int footLayout) {
        this.context = context;
        this.datas = datas;
        this.itemLayout = itemLayout;
        this.footLayout = footLayout;
    }

    public void setFootLayout(int footLayout) {
        this.footLayout = footLayout;
    }

    public void showFoot(boolean showFoot) {
        this.showFoot = showFoot;
        if (this.footLayout == 0) {
            throw new IllegalArgumentException("footLayout must not be null when should show foot layout!");
        }
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

    }

    public List<T> getData() {
        return this.datas;
    }

    public void replaceData(List<T> datas) {
        this.datas = datas;
        this.notifyDataSetChanged();
    }

    public void addData(List<T> datas) {
        this.datas.addAll(datas);
        this.notifyDataSetChanged();
    }

    public void addData(T data) {
        this.datas.add(data);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        int add = 0;
        if (showFoot) {
            add = 1;
        }
        if (datas == null) {
            return 0;
        }
        return datas.size() + add;
    }

    @Override
    public int getItemViewType(int position) {
        if (showFoot) {
            if (isFootItem(position)) {
                return TYPE_FOOT;
            }
        }
        return TYPE_NORMAL;
    }

    private boolean isFootItem(int position) {
        return position == (getItemCount() - 1);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOT) {
            // foot item
            View layout = LayoutInflater.from(parent.getContext()).inflate(footLayout, parent, false);
            return new ViewHolder(layout);
        }
        // normal item
        View layout = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (showFoot) {
            if (isFootItem(position)) {
                return;
            }
        }
        bindItemView(holder, datas.get(position), position);
    }

    public abstract void bindItemView(RecyclerView.ViewHolder holder, T t, int position);

}
