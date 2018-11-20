package com.github.neowen.apibasedemo.design.customdialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.github.neowen.apibasedemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ZWX on 15/10/19.
 */
public class CustomDialogActivity extends AppCompatActivity {

    public static final String TAG = CustomDialogActivity.class.getSimpleName();

    @BindView(R.id.show_dialog)
    Button mShowDialog;
    @BindView(R.id.show_custom)
    Button mShowCustom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog);
        ButterKnife.bind(this);
        mShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(CustomDialogActivity.this, R.style.CustomDialog);
//                Dialog dialog = new Dialog(CustomDialogActivity.this, 0);
//                Dialog dialog = new Dialog(CustomDialogActivity.this);
                dialog.setContentView(R.layout.custom_dialog_layout);
                dialog.show();

            }
        });

        mShowCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Dialog dialog = new Dialog(CustomDialogActivity.this, R.style.CustomDialog);
                Dialog dialog = new Dialog(CustomDialogActivity.this);
                dialog.setContentView(R.layout.custom_dialog_layout);
                dialog.show();

//                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
//                builder.setTitle("Title");
//                builder.setMessage("Message");
//                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                builder.setPositiveButton("confirm",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface arg0, int arg1) {
//
//                                arg0.dismiss();
//                            }
//                        });
//                builder.create().show();

            }
        });

        findViewById(R.id.show_style_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), 0);
                builder.setTitle("Default Style");
                builder.setMessage("Message");
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                arg0.dismiss();
                            }
                        });
                builder.create().show();
            }
        });
    }

}
