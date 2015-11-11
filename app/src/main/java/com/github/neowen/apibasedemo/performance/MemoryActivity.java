package com.github.neowen.apibasedemo.performance;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.neowen.apibasedemo.R;

import java.io.File;
import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ZWX on 15/11/11.
 */
public class MemoryActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = MemoryActivity.class.getSimpleName();

    WeakReference<Bitmap> mBitmap;
    WeakReference<Bitmap> mLastBitmap;

    @Bind(R.id.add_bitmap)
    Button mAddBitmap;
    @Bind(R.id.memory_indicator)
    TextView mMemoryIndicator;
    @Bind(R.id.image)
    ImageView mImage;
    @Bind(R.id.show_memory)
    Button mShowMemory;
    @Bind(R.id.show)
    Button mShow;
    @Bind(R.id.recycle)
    Button mRecycle;
    @Bind(R.id.recycle_last)
    Button mRecycleLast;
    @Bind(R.id.size)
    TextView mSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memory);
        ButterKnife.bind(this);

        mAddBitmap.setOnClickListener(this);
        mShowMemory.setOnClickListener(this);
        mShow.setOnClickListener(this);
        mRecycle.setOnClickListener(this);
        mRecycleLast.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.add_bitmap:
                showBitmap();
                break;
            case R.id.show:
                mImage.setVisibility(mImage.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                break;
            case R.id.recycle:
                recycle();
                break;
            case R.id.show_memory:
                showMemory();
                break;
            case R.id.recycle_last:
                recycleLast();
                break;
        }
    }

    private void recycleLast() {
        if (mLastBitmap != null && mLastBitmap.get() != null) {
            mLastBitmap.get().recycle();
            mLastBitmap = null;
//            System.gc();
        }
    }

    private void recycle() {
        if (mBitmap != null && mBitmap.get() != null) {
            mBitmap.get().recycle();
//            System.gc();
        }
    }

    private void showMemory() {
        long total = Runtime.getRuntime().totalMemory() / 1024;
        long free = Runtime.getRuntime().freeMemory() / 1024;
        long max = Runtime.getRuntime().maxMemory() / 1024;

        mMemoryIndicator.setText("total : " + total
                + " , free : " + free
                + " , max : " + max);
    }

    private void showBitmap() {
        mLastBitmap = mBitmap;
        mBitmap = new WeakReference<Bitmap>(loadBitmap());
        mImage.setImageBitmap(mBitmap.get());
    }

    private Bitmap loadBitmap() {
        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "map.jpg";
            recycle();
            recycleLast();
            WeakReference<Bitmap> bitmap = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path));

//            mSize.setText("size1 : " + bitmap.getWidth() * bitmap.getHeight() / 1024
//                            + " , size2 : " + bitmap.getRowBytes() * bitmap.getHeight() / 1024
//                            + " , size3 : " + bitmap.getByteCount() / 1024
//            );

            showMemory();
            return bitmap.get();
        } catch (OutOfMemoryError out) {
            mMemoryIndicator.setText("out of memory!");
            mImage.setImageBitmap(null);
            recycle();
            recycleLast();
            System.gc();
            out.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
