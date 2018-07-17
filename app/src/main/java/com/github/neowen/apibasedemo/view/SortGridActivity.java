package com.github.neowen.apibasedemo.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.github.neowen.apibasedemo.BaseActivity;
import com.github.neowen.apibasedemo.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Winson on 2018/7/17.
 */
public class SortGridActivity extends BaseActivity {

    SortGridLayout sortGridLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sort_grid);
        sortGridLayout = (SortGridLayout) findViewById(R.id.sort_grid_layout);
        sortGridLayout.setData(Arrays.asList("zhou"
                ,"wen 1"
                ,"wen 2"
                ,"wen 3"
                ,"wen 4"
                ,"wen 5"
                ,"wen 6"
                ,"wen 7"
                ,"wen 8"
                ,"wen 9"
                ,"wen 10"
                ,"wen 11"
                ,"wen 12"
                ,"wen 13"
                ,"wen 14"
                ,"wen 15"
                ,"wen 16"
                ,"wen 17"
                ,"wen 18"
                ,"wen 19"
                ,"wen 20"
                ,"wen 21"));
        sortGridLayout.setOnItemClickListener(new SortGridLayout.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String item) {
                Toast.makeText(SortGridActivity.this, item, Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.page_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortGridLayout.pageUp();
            }
        });

        findViewById(R.id.page_down).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortGridLayout.pageDown();
            }
        });

    }

}
