package com.github.neowen.apibasedemo.drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

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
