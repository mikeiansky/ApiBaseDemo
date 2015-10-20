package com.github.neowen.apibasedemo.animation;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.Button;
import android.widget.ImageView;

import com.github.neowen.apibasedemo.R;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ZWX on 15/10/20.
 */
public class ViewPropertyAnimatorActivity extends AppCompatActivity {

    public static final String TAG = ViewPropertyAnimatorActivity.class.getSimpleName();

    @Bind(R.id.target)
    ImageView mTarget;
    @Bind(R.id.start_animator)
    Button mStartAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewproperty_animator);
        ButterKnife.bind(this);

        mStartAnimator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTarget.setAlpha(0f);
                ViewPropertyAnimator vpa = mTarget.animate().setDuration(1000);
                vpa.alpha(1f);
                vpa.alphaBy(1f);

                vpa.translationXBy(200);

                vpa.start();
            }
        });


    }

}
