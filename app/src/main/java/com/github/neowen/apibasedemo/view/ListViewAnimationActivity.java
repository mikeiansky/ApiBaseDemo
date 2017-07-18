package com.github.neowen.apibasedemo.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
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
 * Created by ZWX on 15/10/20.
 */
public class ListViewAnimationActivity extends AppCompatActivity {

    public static final String TAG = ListViewAnimationActivity.class.getSimpleName();

    MyAdapter mMyAdapter;

    @Bind(R.id.list_view)
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_animation);
        ButterKnife.bind(this);

        ArrayList<String> datas = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            datas.add("position : " + i);
        }

        mMyAdapter = new MyAdapter(this, datas);
        mListView.setAdapter(mMyAdapter);

        LayoutAnimationController lac = new LayoutAnimationController(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        mListView.setLayoutAnimation(lac);
    }

    class MyAdapter extends CommonAdapter<String> {

        public MyAdapter(Context context, List<String> datas) {
            super(context, R.layout.text_list_item, datas);
        }

        @Override
        public void convert(ViewHolder viewHolder, String obj, int position) {
            MyViewHolder mh = new MyViewHolder(viewHolder.getConvertView());

            mh.mTitle.setText(obj);

        }

    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'text_list_item.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class MyViewHolder {
        @Bind(R.id.title)
        TextView mTitle;

        MyViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
