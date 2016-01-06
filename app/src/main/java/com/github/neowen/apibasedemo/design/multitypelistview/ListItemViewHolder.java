package com.github.neowen.apibasedemo.design.multitypelistview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.neowen.apibasedemo.R;

/**
 * Created by Winson on 2016/1/6.
 */
public class ListItemViewHolder extends TypeViewHolder<ListItem> {

    public static final String TAG = ListItemViewHolder.class.getSimpleName();

    public ListItemViewHolder(Context context, ViewGroup parent, Item item) {
        super(context, parent, item);
    }

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(mContext).inflate(R.layout.list_item_holder, mParent, false);
        return root;
    }

    @Override
    protected void updateData() {
        Log.d(TAG, "updateData removeAllViews and create new views.");
        LinearLayout linearLayout = (LinearLayout) mRootView.findViewById(R.id.root);
        linearLayout.removeAllViews();
        int size = mItem.mList.size();

        TextView textView = new TextView(mContext);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.bottomMargin = 20;
//        textView.setBackgroundColor(mContext.getResources().getColor(R.color.md_amber_200));
        textView.setLayoutParams(lp);
        textView.setText("" + size);
        linearLayout.addView(textView);

        for (int i = 0; i < size; i++) {
            ImageView imageView = new ImageView(mContext);
            lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 30);
            lp.bottomMargin = 10;
            imageView.setBackgroundColor(mContext.getResources().getColor(R.color.md_amber_600));
            imageView.setLayoutParams(lp);
            linearLayout.addView(imageView);
        }
    }

}