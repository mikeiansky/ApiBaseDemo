package com.github.neowen.apibasedemo.support;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.github.neowen.apibasedemo.R;

/**
 * Created by ZWX on 15/10/15.
 */
public class ViewDragHelperA extends AppCompatActivity {

    boolean mHidden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewdraghelper);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View decorView = getWindow().getDecorView();
                int uiOptions = mHidden ? View.SYSTEM_UI_FLAG_VISIBLE : View.SYSTEM_UI_FLAG_FULLSCREEN;
                mHidden = !mHidden;
                decorView.setSystemUiVisibility(uiOptions);

// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
//                ActionBar actionBar = getActionBar();
//                actionBar.hide();
                Toast.makeText(ViewDragHelperA.this, "A", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
