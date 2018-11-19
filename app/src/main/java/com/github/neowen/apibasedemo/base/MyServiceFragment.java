package com.github.neowen.apibasedemo.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.neowen.apibasedemo.BaseFragment;
import com.github.neowen.apibasedemo.R;

/**
 * @date on 2018/11/18
 * @Author Winson
 */
public class MyServiceFragment extends BaseFragment {

    class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("MyService", "get receiver!!!");
        }

    }

    BroadcastReceiver receiver;

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(receiver);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frag_my_service, container, false);

        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.winson.demo");
        getActivity().registerReceiver(receiver, filter);

        root.findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MyService.class);
                intent.putExtra("msg", "Hello");
                getActivity().startService(intent);
            }
        });

        root.findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MyService.class);
                getActivity().stopService(intent);
            }
        });

        return root;
    }
}
