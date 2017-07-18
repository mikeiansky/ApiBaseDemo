package com.github.neowen.apibasedemo.sdk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.neowen.apibasedemo.BaseActivity;
import com.github.neowen.apibasedemo.R;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Winson on 2015/12/31.
 */
public class WechatSdkActivity extends BaseActivity implements View.OnClickListener, IWXAPIEventHandler {

    public static final String TAG = WechatSdkActivity.class.getSimpleName();

    IWXAPI mIWXAPI;

    @Bind(R.id.login)
    Button mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qq_sdk);
        ButterKnife.bind(this);

        mLogin.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.login:
                login();
                break;
        }
    }

    private void login() {
        mIWXAPI = WXAPIFactory.createWXAPI(this, Wechat.APP_ID, true);
        mIWXAPI.registerApp(Wechat.APP_ID);
        mIWXAPI.handleIntent(getIntent(), this);

        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        mIWXAPI.sendReq(req);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        Log.d(TAG, "onReq : " + baseReq);
    }

    @Override
    public void onResp(BaseResp baseResp) {
        Log.d(TAG, "onResp : " + baseResp);
    }
}
