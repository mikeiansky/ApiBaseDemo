package com.github.neowen.apibasedemo.design.multitypelistview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.github.neowen.apibasedemo.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Winson on 2016/1/6.
 */
public class MultiTypeListViewActivity extends AppCompatActivity {

    static final String TAG = MultiTypeListViewActivity.class.getSimpleName();

    ArrayList<Item> mItems;
    MyAdapter mMyAdapter;

    @Bind(R.id.list_view)
    ListView mListView;
    @Bind(R.id.refresh)
    Button mRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multitypelistview);
        ButterKnife.bind(this);

        initDatas();
        mMyAdapter = new MyAdapter();
        mListView.setAdapter(mMyAdapter);

        mRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyAdapter.notifyDataSetChanged();
            }
        });

    }

    private int getRandom() {
        return (int) (Math.random() * 6) + 1;
    }

    private void initDatas() {
        mItems = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            int mType = i % 3;
            switch (mType) {
                case 0:
                    mItems.add(new Item(mType, "" + i));
                    break;
                case 1:
                    ListItem listItem = new ListItem(mType, "" + i);
                    int count = getRandom();
                    ArrayList<String> list = new ArrayList<>();
                    for (int j = 0; j < count; j++) {
                        list.add("String");
                    }
                    listItem.setList(list);
                    mItems.add(listItem);
                    break;
                case 2:
                    GridItem gridItem = new GridItem(mType, "" + i);
                    count = getRandom();
                    ArrayList<String> grid = new ArrayList<>();
                    for (int j = 0; j < count; j++) {
                        grid.add("String");
                    }
                    gridItem.setGrid(grid);
                    mItems.add(gridItem);
                    break;
            }


        }
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public Item getItem(int position) {
            return mItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Item item = getItem(position);
            TypeViewHolder typeViewHolder = null;
            if (convertView != null) {
                typeViewHolder = (TypeViewHolder) convertView.getTag();
                if (typeViewHolder.mType == item.mType) {
                    typeViewHolder.updateItem(item);
                    return convertView;
                }
            }

            Context context = parent.getContext();
            int type = item.mType;
            switch (type) {
                case Item.TYPE_GRID:
                    typeViewHolder = new GridItemViewHolder(context, parent, item);
                    break;
                case Item.TYPE_LIST:
                    typeViewHolder = new ListItemViewHolder(context, parent, item);
                    break;
                default:
                    typeViewHolder = new NormalItemViewHolder(context, parent, item);
                    break;
            }

            return typeViewHolder.getRootView();
        }
    }

}
