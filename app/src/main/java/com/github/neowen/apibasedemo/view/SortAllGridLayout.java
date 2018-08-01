package com.github.neowen.apibasedemo.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Winson on 2018/7/17.
 */
public class SortAllGridLayout extends ViewGroup implements View.OnClickListener {

    public static final String TAG = SortAllGridLayout.class.getSimpleName();

    private boolean haveChild;

    private int leftUnit, topUnit, paddingRight;
    private List<String> datas;
    private OnItemClickListener onItemClickListener;
    private int pageSize;
    private int page;
    private int oldHeight = -1;
    private boolean atFirst = true;
    private boolean atLast = false;

    public interface OnItemClickListener {
        void onItemClick(int position, String item);
    }

    public SortAllGridLayout(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public SortAllGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public SortAllGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SortAllGridLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        topUnit = (int) (context.getResources().getDisplayMetrics().density * 50);
        paddingRight = (int) (context.getResources().getDisplayMetrics().density * 30);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setData(List<String> datas) {
        this.datas = datas;
        page = 0;
        refreshData();
    }

    private void refreshData() {
        if (!haveChild) {
            return;
        }
        int endPosition = pageSize * (page + 1);
        int startPosition = page * pageSize;

        if (datas == null) {
            startPosition = 0;
            endPosition = 0;
        } else if (endPosition > datas.size()) {
            endPosition = datas.size();
        }

        int count = endPosition - startPosition;
        int cs = getChildCount();
        for (int i = 0; i < cs; i++) {
            if (i >= count) {
                ((TextView) getChildAt(i)).setText("");
            } else {
                if (i == 9) {
                    ((TextView) getChildAt(i)).setText("ABCDEFGHIJKLMNOPQRSTUVWXYZHELL");
                } else {
                    ((TextView) getChildAt(i)).setText(datas.get(startPosition + i));
                }
            }
        }

    }

    private void checkChild(int height) {
        if (!haveChild || oldHeight != height) {
            oldHeight = height;

            int count = (height / topUnit) * 2;

            removeAllViews();
            pageSize = count;
            page = 0;

            for (int i = 0; i < count; i++) {
                TextView textView = new TextView(getContext());
                textView.setSingleLine();
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                textView.setBackgroundColor((int) (Math.random() * Integer.MAX_VALUE));
                textView.setOnClickListener(this);
                textView.setEllipsize(TextUtils.TruncateAt.END);
                textView.setTag(i);
                textView.setPadding(0, 0, paddingRight, 0);
                addView(textView);
            }

            haveChild = true;

            refreshData();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        leftUnit = MeasureSpec.getSize(widthMeasureSpec) / 2;

        checkChild(MeasureSpec.getSize(heightMeasureSpec));

        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int hw = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec) / 2, MeasureSpec.EXACTLY);
            int ws = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec) / 2, MeasureSpec.UNSPECIFIED);
//            child.
            int hs = MeasureSpec.makeMeasureSpec(topUnit, MeasureSpec.EXACTLY);
            child.measure(ws, hs);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        int maxWidth = (r - l) / 2;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);

            int left = (i % 2) * leftUnit;
            int top = (i / 2) * topUnit;

            int width = child.getMeasuredWidth();
            if (width > maxWidth) {
                width = maxWidth;
            }

            child.layout(left, top, left + width, top + child.getMeasuredHeight());
        }

    }

    @Override
    public void onClick(View v) {
        if (datas == null || pageSize <= 0) {
            return;
        }
        int size = datas.size();
        if (size <= 0) {
            return;
        }

        int position = (int) v.getTag();
        int realPosition = page * pageSize + position;
        if (realPosition >= size) {
            return;
        }
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(position, "Hello " + realPosition);
        }
    }

    public boolean isAtFirst() {
        return page == 0;
    }

    public boolean isAtLast() {
        if (datas == null) {
            return true;
        }
        int size = datas.size();
        return pageSize * (page + 1) >= size;
    }

    public void pageUp() {
        page = page - 1;
        if (page < 0) {
            page = 0;
            return;
        }
        refreshData();
    }

    public void pageDown() {
        int cp = page + 1;
        if (pageSize <= 0) {
            page = 0;
            return;
        }
        if (datas == null) {
            return;
        }
        int size = datas.size();
        if (size <= pageSize || ((cp + 1) * pageSize - pageSize) >= size) {
            return;
        }
        page = cp;
        refreshData();
    }

}
