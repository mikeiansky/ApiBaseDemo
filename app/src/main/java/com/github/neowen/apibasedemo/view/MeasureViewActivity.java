package com.github.neowen.apibasedemo.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.neowen.apibasedemo.BaseActivity;
import com.github.neowen.apibasedemo.R;
import com.github.neowen.apibasedemo.view.map.HVScaleScrollView;
import com.github.neowen.apibasedemo.view.map.ScreenUtils;

/**
 * Created by Winson on 2018/7/12.
 */
public class MeasureViewActivity extends BaseActivity {

    public static final String TAG = MeasureViewActivity.class.getSimpleName();
    float scale;
    float mapWidth = 1450;
    float mapHeight = 1200;
    int screenWidth;
    FrameLayout rlMap;
    TextView tvData ;
    HVScaleScrollView scaleScrollView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.measure_view_layout);

        rlMap = (FrameLayout) findViewById(R.id.rl_map);
        scaleScrollView = (HVScaleScrollView) findViewById(R.id.scrollView);
        scaleScrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                scaleAndScroll();
            }
        },500);
        screenWidth = ScreenUtils.getScreenWidth(this);
        scale = screenWidth / mapWidth;
        findViewById(R.id.info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "map width : " + rlMap.getWidth()+" , height : " + rlMap.getHeight());
                Log.d(TAG, "scroll width : " + scaleScrollView.getWidth()+" , height : " + scaleScrollView.getHeight());
            }
        });
    }

    private void scaleAndScroll() {
        rlMap.setScaleX(scale);
        rlMap.setScaleY(scale);
        scaleScrollView.setMinScale(scale);
//        scaleScrollView.smoothScrollTo((rlMap.getWidth() - screenWidth) / 2, (int) (rlMap.getHeight() - (scale * mapWidth)) / 2);
//        scaleScrollView.smoothScrollTo((rlMap.getWidth() - screenWidth) / 2,0);
//        scaleScrollView.smoothScrollTo((rlMap.getWidth() - scaleScrollView.getWidth()) / 2,(rlMap.getHeight() - scaleScrollView.getHeight()) / 2);
        scaleScrollView.scrollToSave((rlMap.getWidth() - scaleScrollView.getWidth()) / 2,(rlMap.getHeight() - scaleScrollView.getHeight()) / 2);
    }
}
