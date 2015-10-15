package com.github.neowen.apibasedemo.design;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.neowen.apibasedemo.R;
import com.github.neowen.apibasedemo.common.CommonAdapter;
import com.github.neowen.apibasedemo.common.ViewHolder;

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

    public SwipeBackListAdapter(Context context, List<String> datas) {
        super(context, R.layout.swipe_back_list_item, datas);
    }

    @Override
    public void convert(ViewHolder viewHolder, String obj) {
        ButterKnife.bind(this, viewHolder.getConvertView());
        mSwipeBackGroup.setDragContent(mContent);
        mSwipeBackGroup.setDragRight(mRight);

        mRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
