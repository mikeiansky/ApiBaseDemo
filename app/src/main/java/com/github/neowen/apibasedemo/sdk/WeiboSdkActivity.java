package com.github.neowen.apibasedemo.sdk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.neowen.apibasedemo.BaseActivity;
import com.github.neowen.apibasedemo.R;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Winson on 2015/12/31.
 */
public class WeiboSdkActivity extends BaseActivity implements View.OnClickListener {

    public static final String TAG = WeiboSdkActivity.class.getSimpleName();

    AuthInfo mAuthInfo;
    Oauth2AccessToken mAccessToken;
    SsoHandler mSsoHandler;

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
        mAuthInfo = new AuthInfo(this, Constants.APP_KEY,
                Constants.REDIRECT_URL, Constants.SCOPE);
        mSsoHandler = new SsoHandler(this, mAuthInfo);
        mSsoHandler.authorize(new AuthListener());
    }

    class AuthListener implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {
            mAccessToken = Oauth2AccessToken.parseAccessToken(values); // 从 Bundle 中解析 Token
            Log.d(TAG, "onComplete : " + values + " , token : " + mAccessToken);
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Log.d(TAG, "onWeiboException : " + e);
        }

        @Override
        public void onCancel() {
            Log.d(TAG, "onCancel");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

}
