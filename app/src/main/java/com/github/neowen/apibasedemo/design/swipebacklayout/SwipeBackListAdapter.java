package com.github.neowen.apibasedemo.design.swipebacklayout;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.neowen.apibasedemo.R;
import com.github.neowen.apibasedemo.common.CommonAdapter;
import com.github.neowen.apibasedemo.common.ViewHolder;

import java.util.HashSet;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ZWX on 15/10/15.
 */
public class SwipeBackListAdapter extends CommonAdapter<String> {

    @Bind(R.id.content)
    TextView mContent;
    @Bind(R.id.right)
    TextView mRight;
    @Bind(R.id.swipe_back_group)
    SwipeBackLayout mSwipeBackGroup;

    HashSet<Integer> opens = new HashSet<>();

    public SwipeBackListAdapter(Context context, List<String> datas) {
        super(context, R.layout.swipe_back_list_item, datas);
    }

    @Override
    public void convert(ViewHolder viewHolder, String obj, final int position) {
        ButterKnife.bind(this, viewHolder.getConvertView());
        mSwipeBackGroup.setDragContent(mContent);
        mSwipeBackGroup.setDragRight(mRight);
        if (opens.contains(position)) {
            Log.d("MyData", "opens.contains : " + position);
            mSwipeBackGroup.open(false);
        } else {
            Log.d("MyData", "opens.close : " + position);
            mSwipeBackGroup.close(false);
        }
        mSwipeBackGroup.setOnOpenListener(new SwipeBackLayout.OnOpenListener() {
            @Override
            public void opened(boolean opened) {
//                Log.d("MyData", "opens.add : " + position);
                if (opened) {
                    opens.add(position);
                } else {
                    opens.remove(position);
                }
            }
        });

        mRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
