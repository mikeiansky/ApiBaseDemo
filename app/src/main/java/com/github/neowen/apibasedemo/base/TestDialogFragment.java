package com.github.neowen.apibasedemo.base;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup root = (ViewGroup) inflater.inflate(R.layout.test_dialog, container, false);

        root.findViewById(R.id.insert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentResolver contentResolver = getActivity().getContentResolver();
                ContentValues cv = new ContentValues();
                cv.put(MyDbHelper.DOMAIN, "192.168.1.1");
                contentResolver.insert(Uri.parse(MyContentProvider.DOMAIN_URI), cv);
            }
        });

        root.findViewById(R.id.query).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                MyDbHelper myDbHelper = new MyDbHelper(v.getContext());
//                Cursor cursor = myDbHelper.getWritableDatabase().query(MyDbHelper.TABLE_NAME, null, null, null, null, null, null);
//                Log.d("TAG", "item count : " + cursor.getCount());
//                cursor.close();

                ContentResolver cr = getActivity().getContentResolver();
                Cursor cursor = cr.query(Uri.parse(MyContentProvider.DOMAIN_URI), null, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    String server = cursor.getString(cursor.getColumnIndex(MyDbHelper.DOMAIN));
                    Log.d("TAG", "server is : " + server);
                    cursor.close();
                }

            }
        });

        root.findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentResolver cr = getActivity().getContentResolver();
                ContentValues cv = new ContentValues();
                cv.put(MyDbHelper.DOMAIN, "172.1.1.160");
                int cursor = cr.update(Uri.parse(MyContentProvider.DOMAIN_URI), cv, null, null);
            }
        });

        root.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentResolver cr = getActivity().getContentResolver();
                cr.delete(Uri.parse(MyContentProvider.DOMAIN_URI), null, null);
            }
        });

        return root;
    }


}
