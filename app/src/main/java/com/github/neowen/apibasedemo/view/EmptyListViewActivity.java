package com.github.neowen.apibasedemo.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.neowen.apibasedemo.R;
import com.github.neowen.apibasedemo.common.CommonAdapter;
import com.github.neowen.apibasedemo.common.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ZWX on 15/10/23.
 */
public class EmptyListViewActivity extends AppCompatActivity {

    public static final String TAG = EmptyListViewActivity.class.getSimpleName();

    @Bind(R.id.list_view)
    ListView mListView;
    @Bind(R.id.empty_view)
    TextView mEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_listview);
        ButterKnife.bind(this);

        View emptyView = LayoutInflater.from(this).inflate(R.layout.empty_view, mListView, false);

        ArrayList<String> datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datas.add("position : "+i);
        }

        TextView tv = new TextView(this);
        tv.setText("This is empty view!");
        mListView.setAdapter(new MyAdapter(this, datas));
//        mListView.setEmptyView(emptyView);
        mEmptyView.setVisibility(View.GONE);
        mListView.setEmptyView(mEmptyView);
    }

    class MyAdapter extends CommonAdapter<String> {

        public MyAdapter(Context context, List<String> datas) {
            super(context, R.layout.text_list_item, datas);
        }

        @Override
        public void convert(ViewHolder viewHolder, String obj , int position) {

        }
    }

}
