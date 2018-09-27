package com.github.neowen.apibasedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ApiItemListFragmentActivity extends AppCompatActivity {

    public static final String TAG = ApiItemListFragmentActivity.class.getSimpleName();

    protected ArrayList<ApiItemFragment> mApiItems;
    ApiItemFragmentAdapter mApiItemAdapter;

    @BindView(R.id.list_view)
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initApiItemData();
        mApiItemAdapter = new ApiItemFragmentAdapter(this, mApiItems);
        mListView.setAdapter(mApiItemAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ApiItem apiItem = mApiItemAdapter.getItem(position);
//                Intent intent = new Intent(ApiItemListFragmentActivity.this, apiItem.mClazz);
//                startActivity(intent);

                ApiItemFragment aif = mApiItemAdapter.getItem(position);
                FragmentManager fm = getSupportFragmentManager();
                try {
                    fm.beginTransaction()
                            .add(R.id.content, aif.mClazz.newInstance())
                            .addToBackStack(null)
                            .commit();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initApiItemData() {
        mApiItems = new ArrayList<>();
        addApiItemData();
    }

    protected void addApiItemData() {

    }

}
