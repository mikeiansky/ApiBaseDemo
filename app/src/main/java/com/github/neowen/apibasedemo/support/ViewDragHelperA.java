package com.github.neowen.apibasedemo.support;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.github.neowen.apibasedemo.BAct;
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
                Intent intent = new Intent(ViewDragHelperA.this, BAct.class);
                startActivityForResult(intent, 20000);
//                View decorView = getWindow().getDecorView();
//                int uiOptions = mHidden ? View.SYSTEM_UI_FLAG_VISIBLE : View.SYSTEM_UI_FLAG_FULLSCREEN;
//                mHidden = !mHidden;
//                decorView.setSystemUiVisibility(uiOptions);
//
//// Remember that you should never show the action bar if the
//// status bar is hidden, so hide that too if necessary.
////                ActionBar actionBar = getActionBar();
////                actionBar.hide();
//                Toast.makeText(ViewDragHelperA.this, "A", Toast.LENGTH_SHORT).show();


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG", "----> data : " + data.getStringExtra("hello"));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d("TAG","onKeyDown --> keycode : " + keyCode + " , event : " + event.getAction());
//        return super.onKeyDown(keyCode, event);
        return true;
    }
}
