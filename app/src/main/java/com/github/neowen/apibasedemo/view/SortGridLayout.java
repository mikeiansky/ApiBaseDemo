package com.github.neowen.apibasedemo.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Winson on 2018/7/17.
 */
public class SortGridLayout extends ViewGroup implements View.OnClickListener {

    public static final String TAG = SortGridLayout.class.getSimpleName();

    private boolean haveChild;

    private int leftUnit, topUnit;
    private List<String> datas;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick();
    }

    public SortGridLayout(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public SortGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public SortGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SortGridLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        topUnit = (int) (context.getResources().getDisplayMetrics().density * 50);

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setData(List<String> datas) {
        this.datas = datas;
    }

    private void checkChild(int height) {
        if (!haveChild) {

            int count = (height / topUnit) * 2;
            for (int i = 0; i < count; i++) {
                TextView textView = new TextView(getContext());
                textView.setBackgroundColor((int) (Math.random() * Integer.MAX_VALUE));
                textView.setText("Hello Wrold : " + i);
                textView.setOnClickListener(this);
                textView.setTag(i);
                addView(textView);
            }

            haveChild = true;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        checkChild(MeasureSpec.getSize(heightMeasureSpec));
        leftUnit = MeasureSpec.getSize(widthMeasureSpec) / 2;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int ws = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec) / 2, MeasureSpec.EXACTLY);
            int hs = MeasureSpec.makeMeasureSpec(topUnit, MeasureSpec.EXACTLY);
            child.measure(ws, hs);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);

            int left = (i % 2) * leftUnit;
            int top = (i / 2) * topUnit;

            child.layout(left, top, left + child.getMeasuredWidth(), top + child.getMeasuredHeight());
        }

    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        Toast.makeText(getContext(), "click : " + position, Toast.LENGTH_SHORT).show();
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick();
        }
    }

}
