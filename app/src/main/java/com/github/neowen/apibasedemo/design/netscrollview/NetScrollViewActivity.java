package com.github.neowen.apibasedemo.design.netscrollview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.github.neowen.apibasedemo.R;

/**
 * @date on 2018/10/21
 * @Author Winson
 */
public class NetScrollViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_net_scroll2);

        CoordinatorLayout coordinatorLayout = findViewById(R.id.target);

        AppBarLayout appBarLayout = findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.d("TAG", "verticalOffset ---> " + verticalOffset);
            }
        });

//        ViewPager vp = (ViewPager) findViewById(R.id.view_pager);
//        vp.setAdapter(new MyAdapter(getSupportFragmentManager()));
//        ((TabLayout) findViewById(R.id.tab_layout)).setupWithViewPager(vp);

    }

    public static class MyAdapter extends FragmentStatePagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return new ScrollFragment();
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return "TITLE" + position;
        }

    }

}
