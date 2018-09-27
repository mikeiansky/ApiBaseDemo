package com.github.neowen.apibasedemo.media;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.github.neowen.apibasedemo.R;
import com.github.neowen.apibasedemo.common.DebugUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ZWX on 15/10/21.
 */
public class MediaDatabaseActivity extends AppCompatActivity implements OnClickListener {

    public static final String TAG = MediaDatabaseActivity.class.getSimpleName();

    @BindView(R.id.query_photo)
    Button mQueryPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_database);
        ButterKnife.bind(this);
        mQueryPhoto.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.query_photo:
                queryPhoto();
                break;
        }
    }

    public void queryPhoto(){
        Log.d(TAG, "queryPhoto ext : " + MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        Uri hello = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "Hello  WOrld");
        Log.d(TAG, "hello ---> " + hello);
        if(cursor != null){
            if(cursor.moveToFirst()){
                do {
                    if(DebugUtils.debug){
//                        Log.d(TAG, "uri : " + cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media._ID)));
                    }
                }while (cursor.moveToNext());
            }
            Log.d(TAG, "queryPhoto count : " + cursor.getCount());
            cursor.close();
        }
    }

}
