package com.github.neowen.apibasedemo.sdk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.neowen.apibasedemo.BaseActivity;
import com.github.neowen.apibasedemo.R;
import com.github.neowen.apibasedemo.common.DebugUtils;
import com.sina.weibo.sdk.api.share.Base;
import com.tencent.connect.UserInfo;
import com.tencent.open.SocialConstants;
import com.tencent.open.utils.HttpUtils;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Winson on 2015/12/31.
 */
public class QQSdkActivity extends BaseActivity implements View.OnClickListener {

    public static final String TAG = QQSdkActivity.class.getSimpleName();

    public static Tencent mTencent;
    BaseUiListener mListener;

    @BindView(R.id.login)
    Button mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qq_sdk);
        ButterKnife.bind(this);

        mLogin.setOnClickListener(this);
        mListener = new BaseUiListener();
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
        mTencent = Tencent.createInstance("1105067532", this.getApplicationContext());
        mTencent.login(this, "test", mListener);
    }

    private class BaseUiListener implements IUiListener {

//        @Override
//        public void onComplete(JSONObject response) {
////            doComplete(response);
//        }

        protected void doComplete(final JSONObject values) {
            if (DebugUtils.debug) {
                Log.d(TAG, "doComplete values : " + values);
            }

        }

        @Override
        public void onComplete(Object o) {
            if (DebugUtils.debug) {
                Log.d(TAG, "onComplete : " + o);
            }
        }

        @Override
        public void onError(UiError e) {
//            showResult("onError:", "code:" + e.errorCode + ", msg:"
//                    + e.errorMessage + ", detail:" + e.errorDetail);
            if (DebugUtils.debug) {
                Log.d(TAG, "onError : " + e);
            }
        }

        @Override
        public void onCancel() {
//            showResult("onCancel", "");
            if (DebugUtils.debug) {
                Log.d(TAG, "onCancel");
            }
        }
    }

//    private class BaseApiListener implements IRequestListener {
//        @Override
//        public void onComplete(final JSONObject response, Object state) {
//            showResult("IRequestListener.onComplete:", response.toString());
//            doComplete(response, state);
//        }
//
//        protected void doComplete(JSONObject response, Object state) {
//        }
//
//        @Override
//        public void onIOException(final IOException e, Object state) {
//            showResult("IRequestListener.onIOException:", e.getMessage());
//        }
//
//        @Override
//        public void onMalformedURLException(final MalformedURLException e,
//                                            Object state) {
//            showResult("IRequestListener.onMalformedURLException", e.toString());
//        }
//
//        @Override
//        public void onJSONException(final JSONException e, Object state) {
//            showResult("IRequestListener.onJSONException:", e.getMessage());
//        }
//
//        @Override
//        public void onConnectTimeoutException(ConnectTimeoutException arg0,
//                                              Object arg1) {
//// TODO Auto-generated method stub
//        }
//
//        @Override
//        public void onSocketTimeoutException(SocketTimeoutException arg0,
//                                             Object arg1) {
//// TODO Auto-generated method stub
//        }
//
//        //1.4版本中IRequestListener 新增两个异常
//        @Override
//        public void onNetworkUnavailableException(HttpUtils.NetworkUnavailableException e, Object state) {
//// 当前网络不可用时触发此异常
//        }
//
//        @Override
//        public void onHttpStatusException(HttpUtils.HttpStatusException e, Object state) {
//// http请求返回码非200时触发此异常
//        }
//
//        public void onUnknowException(Exception e, Object state) {
//// 出现未知错误时会触发此异常
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult");
        Tencent.onActivityResultData(requestCode, resultCode, data, mListener);
    }

}
