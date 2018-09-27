package com.github.neowen.apibasedemo.design.onceloadpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.neowen.apibasedemo.R;
import com.github.neowen.apibasedemo.common.DebugUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Winson on 2016/1/5.
 */
public class FragmentOne extends Fragment {

    private static final String TAG = FragmentOne.class.getSimpleName();
    int mIndex;
    View mRootView;
    String mUrl;

    @BindView(R.id.count)
    TextView mCount;
    @BindView(R.id.image)
    ImageView mImage;

    public FragmentOne(String url) {
        this.mUrl = url;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView != null) {
            ButterKnife.bind(this, mRootView);
            return mRootView;
        }
        mRootView = inflater.inflate(R.layout.count_fragment, container, false);
        ButterKnife.bind(this, mRootView);
        mCount.setText("" + mIndex);
        Picasso.with(getActivity())
                .load(mUrl)
                .fit()
                .centerCrop()
                .into(mImage);

        mCount.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                mIndex++;
                mCount.setText("" + mIndex);
            }
        });

        return mRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        ButterKnife.unbind(this);
    }
}
