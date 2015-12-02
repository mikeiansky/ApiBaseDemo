package com.github.neowen.apibasedemo.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.neowen.apibasedemo.R;
import com.github.neowen.apibasedemo.common.DebugUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhouwenxiang on 2015/12/2.
 */
public class RecyclerViewActivity extends AppCompatActivity {

    public static final String TAG = RecyclerViewActivity.class.getSimpleName();

    ArrayList<Item> mData;
    MyAdapter mMyAdapter;

    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview);
        ButterKnife.bind(this);

        initData();
        mMyAdapter = new MyAdapter();
        if (DebugUtils.debug) {
            Log.d(TAG, "onCreate MyAdapter count : " + mMyAdapter.getItemCount());
        }
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.setAdapter(mMyAdapter);
    }

    private void initData() {
        mData = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Item item = new Item(i % 3);
            item.mTitle = "position : " + i;
            mData.add(item);
        }
    }

    class Item {

        public static final int RED = 0;
        public static final int GREEN = 1;
        public static final int BLUE = 2;

        public int mViewType;
        public String mTitle;

        public Item(int viewType) {
            mViewType = viewType;
        }

    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mRed;
        TextView mGreen;
        TextView mBlue;

        public MyViewHolder(View itemView, int viewType) {
            super(itemView);
            switch (viewType) {
                case Item.RED:
                    findRedView();
                    break;
                case Item.GREEN:
                    findGreenView();
                    break;
                case Item.BLUE:
                    findBlueView();
                    break;
            }
        }

        private void findRedView() {
            mRed = (TextView) itemView.findViewById(R.id.title);
        }

        private void findGreenView() {
            mGreen = (TextView) itemView.findViewById(R.id.title);
        }

        private void findBlueView() {
            mBlue = (TextView) itemView.findViewById(R.id.title);
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @Override
        public int getItemViewType(int position) {
//            if (DebugUtils.debug) {
//                Log.d(TAG, "getItemViewType position : " + position);
//            }
            if (mData == null) {
                return Item.RED;
            }
            return mData.get(position).mViewType;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (DebugUtils.debug) {
                Log.d(TAG, "onCreateViewHolder position : " + viewType);
            }

            View view = null;
            switch (viewType) {
                case Item.RED:
                    view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.recyclerview_item_red, parent, false);
                    break;
                case Item.GREEN:
                    view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.recyclerview_item_green, parent, false);
                    break;
                case Item.BLUE:
                    view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.recyclerview_item_blue, parent, false);
                    break;
            }

            return new MyViewHolder(view, viewType);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
//            if (DebugUtils.debug) {
//                Log.d(TAG, "onBindViewHolder position : " + position);
//            }
            int viewType = getItemViewType(position);
            switch (viewType) {
                case Item.RED:
                    bindRedViewHolder(holder, position);
                    break;
                case Item.GREEN:
                    bindGreenViewHolder(holder, position);
                    break;
                case Item.BLUE:
                    bindBlueViewHolder(holder, position);
                    break;
            }
        }

        private void bindRedViewHolder(MyViewHolder holder, int position) {
            holder.mRed.setText(mData.get(position).mTitle);
        }

        private void bindGreenViewHolder(MyViewHolder holder, int position) {
            holder.mGreen.setText(mData.get(position).mTitle);

        }

        private void bindBlueViewHolder(MyViewHolder holder, int position) {
            holder.mBlue.setText(mData.get(position).mTitle);

        }

        @Override
        public int getItemCount() {
            if (mData == null) {
                return 0;
            }
            return mData.size();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
