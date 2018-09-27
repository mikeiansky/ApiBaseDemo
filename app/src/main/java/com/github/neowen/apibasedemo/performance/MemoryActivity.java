package com.github.neowen.apibasedemo.performance;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.neowen.apibasedemo.R;
import com.github.neowen.apibasedemo.common.DebugUtils;

import java.io.File;
import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ZWX on 15/11/11.
 */
public class MemoryActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = MemoryActivity.class.getSimpleName();

    WeakReference<Bitmap> mBitmap;
    WeakReference<Bitmap> mLastBitmap;
    Bitmap mRecordBitmap;
    Handler mHandler;

    @BindView(R.id.add_bitmap)
    Button mAddBitmap;
    @BindView(R.id.memory_indicator)
    TextView mMemoryIndicator;
    @BindView(R.id.image)
    ImageView mImage;
    @BindView(R.id.show_memory)
    Button mShowMemory;
    @BindView(R.id.show)
    Button mShow;
    @BindView(R.id.recycle)
    Button mRecycle;
    @BindView(R.id.recycle_last)
    Button mRecycleLast;
    @BindView(R.id.size)
    TextView mSize;
    @BindView(R.id.load_thread)
    Button mLoadThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memory);
        ButterKnife.bind(this);

        mHandler = new Handler();

        mAddBitmap.setOnClickListener(this);
        mShowMemory.setOnClickListener(this);
        mShow.setOnClickListener(this);
        mRecycle.setOnClickListener(this);
        mRecycleLast.setOnClickListener(this);
        mLoadThread.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        ButterKnife.unbind(this);
        mHandler.removeCallbacksAndMessages(null);
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
            case R.id.load_thread:
                loadThread();
                break;
        }
    }

    private void loadThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (DebugUtils.debug) {
                    Log.d(TAG, "loadThread start!");
                }
                for (int i = 0; i < 50; i++) {
//                    mRecordBitmap
                    final Bitmap bp = loadBitmap();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            recycleImageBitmap();
                            mImage.setImageBitmap(bp);
                        }
                    });
                }
                if (DebugUtils.debug) {
                    Log.d(TAG, "loadThread end!");
                }
            }
        }).start();
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
//        mLastBitmap = mBitmap;
//        mBitmap = new WeakReference<Bitmap>(loadBitmap());
//        mImage.setImageBitmap(mBitmap.get());
        mImage.setImageBitmap(loadBitmap());
    }

    private void recycleImageBitmap() {
        if (mImage != null) {
            Drawable drawable = mImage.getDrawable();
            if (drawable instanceof BitmapDrawable) {
                BitmapDrawable bd = (BitmapDrawable) drawable;
                Bitmap bp = bd.getBitmap();
                if (bp != null) {
                    bp.recycle();
                }
            }
        }
    }

    private void recycleBitmap() {
        if (mRecordBitmap != null) {
            mRecordBitmap.recycle();
            mRecordBitmap = null;
        }
    }

    private Bitmap loadBitmap() {
        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "map.jpg";
//            recycle();
//            recycleLast();
//            recycleBitmap();
//            recycle();
//            recycleImageBitmap();

            Bitmap bitmap = BitmapFactory.decodeFile(path);

//            WeakReference<Bitmap> bitmap = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path));

//            mSize.setText("size1 : " + bitmap.getWidth() * bitmap.getHeight() / 1024
//                            + " , size2 : " + bitmap.getRowBytes() * bitmap.getHeight() / 1024
//                            + " , size3 : " + bitmap.getByteCount() / 1024
//            );

//            showMemory();
            return bitmap;
        } catch (OutOfMemoryError out) {
//            mMemoryIndicator.setText("out of memory!");
//            mImage.setImageBitmap(null);
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
