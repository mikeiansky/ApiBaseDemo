package com.github.neowen.apibasedemo.drawable;

import android.graphics.drawable.StateListDrawable;

public class ShadowDrawable extends StateListDrawable {

    public static final String TAG = ShadowDrawable.class.getSimpleName();

    MyShadowView master;

    public ShadowDrawable(MyShadowView master) {
        this.master = master;
    }

    @Override
    protected boolean onStateChange(int[] stateSet) {
        boolean special = false;
        if (stateSet != null) {
            for (int state : stateSet) {
                if (state == android.R.attr.state_focused
                        || state == android.R.attr.state_pressed
                        || state == android.R.attr.state_selected) {
                    special = true;
                }
            }
        }
        if (master != null) {
            master.setSpecial(special);
        }
        return super.onStateChange(stateSet);
    }
}
