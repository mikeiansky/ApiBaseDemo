package com.github.neowen.apibasedemo.design.emptyview;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.github.neowen.apibasedemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Winson on 2016/2/5.
 */
public class DialogFragmentEmptyActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = DialogFragmentEmptyActivity.class.getSimpleName();

    @Bind(R.id.show)
    Button mShow;
    @Bind(R.id.hidden)
    Button mHidden;
//    @Bind(R.id.content)
//    FrameLayout mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_view_dialog);
        ButterKnife.bind(this);

        mShow.setOnClickListener(this);
        mHidden.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.show:
                show();
                break;
            case R.id.hidden:
                hidden();
                break;
        }
    }

    private void show() {
        EmptyDialogFragment dialogFragment = new EmptyDialogFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.content, dialogFragment);
//        ft.addToBackStack(null);
        ft.commit();
    }

    private void hidden() {

    }

}
