package com.github.neowen.apibasedemo.design.onceloadpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.github.neowen.apibasedemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Winson on 2016/1/5.
 */
public class OnceLoadPagerActivity extends AppCompatActivity {

    public static final String TAG = OnceLoadPagerActivity.class.getSimpleName();

    Fragment[] mFragments = new Fragment[]{
            new FragmentOne("http://cdn.llsapp.com/crm_test_1447220020113.jpg"),
            new FragmentOne("http://cdn.llsapp.com/crm_test_1447220428493.jpg"),
            new FragmentOne("http://cdn.llsapp.com/357070051859561_1390016094611.jpg"),
            new FragmentOne("http://cdn.llsapp.com/crm_test_1449051526097.jpg")};

    @BindView(R.id.pager)
    ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onceload_pager);
        ButterKnife.bind(this);

        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments[position];
        }

        @Override
        public int getCount() {
            return mFragments.length;
        }
    }

}
