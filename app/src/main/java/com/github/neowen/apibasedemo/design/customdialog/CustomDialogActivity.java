package com.github.neowen.apibasedemo.design.customdialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.github.neowen.apibasedemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ZWX on 15/10/19.
 */
public class CustomDialogActivity extends AppCompatActivity {

    public static final String TAG = CustomDialogActivity.class.getSimpleName();

    @Bind(R.id.show_dialog)
    Button mShowDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog);
        ButterKnife.bind(this);
        mShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(CustomDialogActivity.this, R.style.CustomDialog);
                dialog.setContentView(R.layout.custom_dialog_layout);
                dialog.show();

            }
        });
    }

}
