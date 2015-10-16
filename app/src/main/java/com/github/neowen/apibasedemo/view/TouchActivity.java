package com.github.neowen.apibasedemo.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.neowen.apibasedemo.R;
import com.github.neowen.apibasedemo.common.CommonAdapter;
import com.github.neowen.apibasedemo.common.DebugUtils;
import com.github.neowen.apibasedemo.common.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ZWX on 15/10/15.
 */
public class TouchActivity extends AppCompatActivity {

    public static final String TAG = TouchActivity.class.getSimpleName();

    MyAdapter mMyAdapter;

    @Bind(R.id.list)
    ListView mList;
    @Bind(R.id.touch_child)
    ImageView mTouchChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.touch);
        ButterKnife.bind(this);

        mTouchChild.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (DebugUtils.debug) {
                    Log.d(TAG, "touch child onTouch action : " + action);
                }
                return true;
            }
        });

        ArrayList<String> datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datas.add("position : " + i);
        }

        mMyAdapter = new MyAdapter(this, datas);
        mList.setAdapter(mMyAdapter);

        mList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (DebugUtils.debug) {
                    Log.d(TAG, "List onTouch : " + action);
                }
                return false;
            }
        });
    }

    class MyAdapter extends CommonAdapter<String> {

        public MyAdapter(Context context, List<String> datas) {
            super(context, R.layout.text_list_item, datas);
        }

        @Override
        public void convert(ViewHolder viewHolder, String obj) {
            TextView title = (TextView) viewHolder.findViewById(R.id.title);
            title.setText(obj);
            title.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (DebugUtils.debug) {
                        Log.d(TAG, "MyAdapter onTouch action : " + event.getAction());
                    }
                    return true;
                }
            });
        }

    }
}
