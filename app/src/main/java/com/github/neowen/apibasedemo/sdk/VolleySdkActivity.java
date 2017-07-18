package com.github.neowen.apibasedemo.sdk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.neowen.apibasedemo.BaseActivity;
import com.github.neowen.apibasedemo.R;

/**
 * Created by Winson on 2017/7/18.
 */

public class VolleySdkActivity extends BaseActivity {

    private static final String TAG = VolleySdkActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volley_sdk);


        findViewById(R.id.volley).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue rq = Volley.newRequestQueue(v.getContext());
                Request q = new StringRequest("http://www.baidu.com", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse --> " + response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse --> " + error.toString());

                    }
                });
                rq.add(q);
            }
        });
    }

}
