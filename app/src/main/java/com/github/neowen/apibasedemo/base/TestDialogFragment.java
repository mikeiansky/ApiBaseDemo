package com.github.neowen.apibasedemo.base;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
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

//        View edit = root.findViewById(R.id.edit);
//
//        edit.requestFocus();
//        edit.setFocusable(true);
//        edit.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                Log.d("TAG","onKey22 ---> " + keyCode + " , event : " + event.getAction());
//                return false;
//            }
//        });

        root.findViewById(R.id.show_pop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                v.setFocusableInTouchMode(true);
                // 用于PopupWindow的View
                View contentView = LayoutInflater.from(v.getContext()).inflate(R.layout.pop_window2, null, false);
                // 创建PopupWindow对象，其中：
                // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
                // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
                PopupWindow window = new PopupWindow(contentView, WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT, true);
                // 设置PopupWindow的背景
                // 设置PopupWindow是否能响应外部点击事件
                window.setOutsideTouchable(true);
                // 设置PopupWindow是否能响应点击事件
                window.setBackgroundDrawable(new ColorDrawable());
                window.setTouchable(true);
                window.setFocusable(true);
                // 显示PopupWindow，其中：
                // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
//                window.setAnimationStyle(R.style.anim_photo_select);
                window.showAtLocation(v, Gravity.LEFT | Gravity.BOTTOM, 0, 0);
                // 或者也可以调用此方法显示PopupWindow，其中：
                // 第一个参数是PopupWindow的父View，第二个参数是PopupWindow相对父View的位置，
                // 第三和第四个参数分别是PopupWindow相对父View的x、y偏移
                // window.showAtLocation(parent, gravity, x, y);
                contentView.setFocusableInTouchMode(true);
                contentView.setFocusable(true);
                contentView.requestFocus();
                contentView.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        Log.d("TAG", "---------> key event!");
                        return false;
                    }
                });


//
//                Dialog dialog = new Dialog(v.getContext(), R.style.dialog);
//                Dialog dialog = new Dialog(v.getContext());
//                dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
//                dialog.getWindow().setGravity(Gravity.BOTTOM | Gravity.LEFT);
//                LayoutInflater inflater = LayoutInflater.from(v.getContext());
//                View viewDialog = inflater.inflate(R.layout.pop_window2, null);
//                Display display = getActivity().getWindowManager().getDefaultDisplay();
//                int width = display.getWidth();
//                int height = display.getHeight()/2;
//                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, height);
//                dialog.setContentView(viewDialog, layoutParams);
//                final View background = dialog.findViewById(R.id.background);
//
//                dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
//                    @Override
//                    public boolean onKey(final DialogInterface dialog, int keyCode, KeyEvent event) {
//                        if(keyCode == KeyEvent.KEYCODE_BACK){
//                            if(event.getAction() == KeyEvent.ACTION_UP){
//                                ObjectAnimator animator = ObjectAnimator.ofFloat(background, "translationY", 0f, 600f);
//                                animator.setDuration(400);
//                                animator.addListener(new Animator.AnimatorListener() {
//                                    @Override
//                                    public void onAnimationStart(Animator animation) {
//
//                                    }
//
//                                    @Override
//                                    public void onAnimationEnd(Animator animation) {
//                                        dialog.dismiss();
//                                    }
//
//                                    @Override
//                                    public void onAnimationCancel(Animator animation) {
//
//                                    }
//
//                                    @Override
//                                    public void onAnimationRepeat(Animator animation) {
//
//                                    }
//                                });
//                                animator.start();
//                            }
//                            return true;
//                        }
//                        return false;
//                    }
//                });
//                dialog.setCancelable(true);
//                dialog.show();
//
//                ObjectAnimator animator = ObjectAnimator.ofFloat(background, "translationY", 600f, 0f);
//                animator.setDuration(400);
//                animator.start();

            }
        });

        return root;
    }

    public void getInfo() {

    }
}
