package com.github.neowen.apibasedemo.performance;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.neowen.apibasedemo.R;
import com.github.neowen.apibasedemo.common.DebugUtils;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/12/1.
 */
public class MemoryLeakActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = MemoryLeakActivity.class.getSimpleName();

    @BindView(R.id.handler_leak)
    Button mHandlerLeak;
    @BindView(R.id.thread_leak)
    Button mThreadLeak;
    @BindView(R.id.singleton_leak)
    Button mSingletonLeak;

    Singleton mSingleton;
    Handler mHandler;
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
//            try {
//                Thread.sleep(60 * 60 * 1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            Context c = MemoryLeakActivity.this;
            if (DebugUtils.debug) {
                Log.d(TAG, "threadLeak -->");
            }
        }
    };

    public static class ThreadRunnable implements Runnable {

        WeakReference<Context> mContenxt;

        public ThreadRunnable(Context mContenxt) {
            this.mContenxt = new WeakReference<Context>(mContenxt);
        }

        @Override
        public void run() {
            try {
                Thread.sleep(60 * 60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (DebugUtils.debug) {
                Log.d(TAG, "threadLeak -->");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memoryleak);
        ButterKnife.bind(this);

        mHandler = new Handler();

        mHandlerLeak.setOnClickListener(this);
        mThreadLeak.setOnClickListener(this);
        mSingletonLeak.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        ButterKnife.unbind(this);
//        mHandler.removeCallbacksAndMessages(null);
//        mHandler.removeCallbacks(mRunnable);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.handler_leak:
                handlerLeak();
                break;
            case R.id.thread_leak:
                threadLeak();
                break;
            case R.id.singleton_leak:
                singletonLeak();
                break;
        }
    }

    private void singletonLeak() {
        mSingleton = Singleton.getInstance(this);
    }

    private void handlerLeak() {
//        mHandler.postDelayed(mRunnable, 60 * 60 * 1000);
        mHandler.postDelayed(mRunnable, 60 * 1000);
    }

    private void threadLeak() {
        new Thread(new ThreadRunnable(this)).start();
    }

    public static class Singleton {
        Context mContext;
        private static Singleton mSingleton;

        private Singleton(Context context) {
            this.mContext = context;
        }

        public static Singleton getInstance(Context context) {
            if (mSingleton == null) {
                mSingleton = new Singleton(context);
            }
            return mSingleton;
        }
    }

}
