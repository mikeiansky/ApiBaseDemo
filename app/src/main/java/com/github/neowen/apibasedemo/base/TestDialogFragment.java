package com.github.neowen.apibasedemo.base;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.github.neowen.apibasedemo.BaseFragment;
import com.github.neowen.apibasedemo.R;

public class TestDialogFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup root = (ViewGroup) inflater.inflate(R.layout.test_dialog, container, false);

        View edit = root.findViewById(R.id.edit);

        edit.requestFocus();
        edit.setFocusable(true);
        edit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.d("TAG","onKey22 ---> " + keyCode + " , event : " + event.getAction());
                return false;
            }
        });

        root.findViewById(R.id.show_pop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 用于PopupWindow的View
                View contentView = LayoutInflater.from(v.getContext()).inflate(R.layout.pop_window, null, false);
                // 创建PopupWindow对象，其中：
                // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
                // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
                PopupWindow window = new PopupWindow(contentView, WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT, true);
                // 设置PopupWindow的背景
                window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#cc000000")));
                // 设置PopupWindow是否能响应外部点击事件
                window.setOutsideTouchable(true);
                // 设置PopupWindow是否能响应点击事件
                window.setTouchable(true);
                // 显示PopupWindow，其中：
                // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
                window.showAtLocation(v, Gravity.LEFT | Gravity.BOTTOM, 0, 0);
                // 或者也可以调用此方法显示PopupWindow，其中：
                // 第一个参数是PopupWindow的父View，第二个参数是PopupWindow相对父View的位置，
                // 第三和第四个参数分别是PopupWindow相对父View的x、y偏移
                // window.showAtLocation(parent, gravity, x, y);
                contentView.setFocusable(true);
                contentView.setFocusableInTouchMode(true);
                contentView.requestFocus();
                contentView.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        Log.d("TAG","onKey ---> " + keyCode + " , event : " + event.getAction());
                        return false;
                    }
                });
            }
        });

        return root;
    }

    public void getInfo() {

    }
}
