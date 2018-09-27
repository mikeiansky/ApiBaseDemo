package com.github.neowen.apibasedemo.animation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.github.neowen.apibasedemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ZWX on 15/10/21.
 */
public class DrawableAnimationActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = DrawableAnimationActivity.class.getSimpleName();

    @BindView(R.id.btn)
    ImageView mBtn;
    @BindView(R.id.select)
    Button mSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawable_animation);
        ButterKnife.bind(this);
        mBtn.setOnClickListener(this);
        mSelect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn:
                break;
            case R.id.select:
                mBtn.setSelected(!mBtn.isSelected());
                break;
        }
    }
}
