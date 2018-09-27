package com.github.neowen.apibasedemo.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.neowen.apibasedemo.R;
import com.github.neowen.apibasedemo.common.DebugUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhouwenxiang on 2015/12/2.
 * Use these method will have some problem.
 */
public class MultipleViewTypeListViewActivity extends AppCompatActivity {

    public static final String TAG = MultipleViewTypeListViewActivity.class.getSimpleName();

    MyAdapter mMyAdapter;
    ArrayList<ListItem> mDatas = new ArrayList<>();

    @BindView(R.id.list)
    ListView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multiple_viewtype_listview);
        ButterKnife.bind(this);

        initData();
        mMyAdapter = new MyAdapter();
        mList.setAdapter(mMyAdapter);
    }

    private void initData() {
        for (int i = 0; i <= 20; i++) {
            ListItem listItem = new ListItem(i % 3);
            listItem.mTitle = "position : " + i;
            mDatas.add(listItem);
        }
    }

    class MultipleViewHolder {

        // red
        TextView mRed;

        // blue
        TextView mBlue;

        // green
        TextView mGreen;

        public MultipleViewHolder(View view, int viewType) {
            switch (viewType) {
                case ListItem.RED:
                    mRed = (TextView) view.findViewById(R.id.title);
                    break;
                case ListItem.GREEN:
                    mGreen = (TextView) view.findViewById(R.id.title);
                    break;
                case ListItem.BLUE:
                    mBlue = (TextView) view.findViewById(R.id.title);
                    break;
            }
        }

    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mDatas == null) {
                return 0;
            }
            return mDatas.size();
        }

        @Override
        public ListItem getItem(int position) {
            return mDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MultipleViewHolder mv = null;
            ListItem listItem = getItem(position);

            if (convertView == null) {
                if (DebugUtils.debug) {
                    Log.d(TAG, "create a new view , position : " + position);
                }
                switch (listItem.mViewType) {
                    case ListItem.RED:
                        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_red, parent, false);
                        break;
                    case ListItem.GREEN:
                        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_green, parent, false);
                        break;
                    case ListItem.BLUE:
                        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_blue, parent, false);
                        break;
                }
                mv = new MultipleViewHolder(convertView, listItem.mViewType);
                convertView.setTag(mv);
            } else {
                mv = (MultipleViewHolder) convertView.getTag();
            }

            switch (listItem.mViewType) {
                case ListItem.RED:
                    showRed(mv, listItem);
                    break;
                case ListItem.GREEN:
                    showGreen(mv, listItem);
                    break;
                case ListItem.BLUE:
                    showBlue(mv, listItem);
                    break;
            }

            return convertView;
        }

        private void showRed(MultipleViewHolder mv, ListItem listItem) {
            mv.mRed.setText(listItem.mTitle);
        }

        private void showGreen(MultipleViewHolder mv, ListItem listItem) {
            mv.mGreen.setText(listItem.mTitle);
        }

        private void showBlue(MultipleViewHolder mv, ListItem listItem) {
            mv.mBlue.setText(listItem.mTitle);
        }

    }

}
