package com.github.neowen.apibasedemo.design.swipebacklayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.github.neowen.apibasedemo.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ZWX on 15/10/15.
 */
public class SwipeBackActivity extends AppCompatActivity {

    public static final String TAG = SwipeBackActivity.class.getSimpleName();

    SwipeBackListAdapter mSwipeBackListAdapter;

    @Bind(R.id.top)
    TextView mTop;
    @Bind(R.id.right)
    TextView mRight;
    @Bind(R.id.swipeback)
    SwipeBackLayout mSwipeback;
    @Bind(R.id.swipelayout_list)
    ListView mSwipelayoutList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipeback);
        ButterKnife.bind(this);

        ArrayList<String> datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datas.add("position : " + i);
        }

        mSwipeBackListAdapter = new SwipeBackListAdapter(this, datas);
        mSwipelayoutList.setAdapter(mSwipeBackListAdapter);

//        mSwipeback.setDragRight(mRight);
//        mSwipeback.setDragContent(mTop);

        mRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
