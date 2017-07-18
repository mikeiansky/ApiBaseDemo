package com.github.neowen.apibasedemo;

import android.content.Context;
import android.widget.TextView;

import com.github.neowen.apibasedemo.common.CommonAdapter;
import com.github.neowen.apibasedemo.common.ViewHolder;

import java.util.List;

/**
 * Created by ZWX on 15/10/15.
 */
public class ApiItemAdapter extends CommonAdapter<ApiItem> {

    public ApiItemAdapter(Context context, List<ApiItem> datas) {
        super(context, R.layout.text_list_item, datas);
    }

    @Override
    public void convert(ViewHolder viewHolder, ApiItem obj, int position) {
        ((TextView) viewHolder.findViewById(R.id.title)).setText(obj.mTitle);
    }
}
