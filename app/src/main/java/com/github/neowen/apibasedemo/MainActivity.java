package com.github.neowen.apibasedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.neowen.apibasedemo.common.CommonAdapter;
import com.github.neowen.apibasedemo.common.ViewHolder;
import com.github.neowen.apibasedemo.support.ViewDragHelperA;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();


    ArrayList<ApiItem> mApiItems;
    ApiItemAdapter mApiItemAdapter;

    @Bind(R.id.list_view)
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initApiItemData();
        mApiItemAdapter = new ApiItemAdapter(this, mApiItems);
        mListView.setAdapter(mApiItemAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ApiItem apiItem = mApiItemAdapter.getItem(position);
                Intent intent = new Intent(MainActivity.this, apiItem.mClazz);
                startActivity(intent);
            }
        });
    }

    private void initApiItemData() {
        mApiItems = new ArrayList<>();

        ApiItem apiItem = new ApiItem("ViewDragHelper", ViewDragHelperA.class);
        mApiItems.add(apiItem);
    }

}
