package com.github.neowen.apibasedemo.support;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.github.neowen.apibasedemo.BaseActivity;
import com.github.neowen.apibasedemo.R;

/**
 * Created by Winson on 2018/7/25.
 */
public class UpgradeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upgrade_layout);

        findViewById(R.id.upgrade).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upgrade();
            }
        });

    }

    private void upgrade() {

    }

}
