package com.github.neowen.apibasedemo.support;

import android.Manifest;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.github.neowen.apibasedemo.ApiDemoApplication;
import com.github.neowen.apibasedemo.BaseActivity;
import com.github.neowen.apibasedemo.R;

import java.util.HashMap;
import java.util.Map;

import static com.tencent.open.utils.ThreadManager.init;

public class LocationActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = LocationActivity.class.getSimpleName();
    private static final int BAIDU_READ_PHONE_STATE = 100;
    public static Map<String, String> provinceMap = new HashMap<>();

    static {
        provinceMap.clear();
        provinceMap.put("北京", "110000000000");
        provinceMap.put("天津", "120000000000");
        provinceMap.put("河北", "130000000000");
        provinceMap.put("山西", "140000000000");
        provinceMap.put("内蒙", "150000000000");
        provinceMap.put("辽宁", "210000000000");
        provinceMap.put("吉林", "220000000000");
        provinceMap.put("黑龙", "230000000000");
        provinceMap.put("上海", "310000000000");
        provinceMap.put("江苏", "320000000000");
        provinceMap.put("浙江", "330000000000");
        provinceMap.put("安徽", "340000000000");
        provinceMap.put("福建", "350000000000");
        provinceMap.put("江西", "360000000000");
        provinceMap.put("山东", "370000000000");
        provinceMap.put("河南", "410000000000");
        provinceMap.put("湖北", "420000000000");
        provinceMap.put("湖南", "430000000000");
        provinceMap.put("广东", "440000000000");
        provinceMap.put("广西", "450000000000");
        provinceMap.put("海南", "460000000000");
        provinceMap.put("重庆", "500000000000");
        provinceMap.put("四川", "510000000000");
        provinceMap.put("贵州", "520000000000");
        provinceMap.put("云南", "530000000000");
        provinceMap.put("西藏", "540000000000");
        provinceMap.put("陕西", "610000000000");
        provinceMap.put("甘肃", "620000000000");
        provinceMap.put("青海", "630000000000");
        provinceMap.put("宁夏", "640000000000");
        provinceMap.put("新疆", "650000000000");
        provinceMap.put("香港", "810000000000");
        provinceMap.put("澳门", "820000000000");
    }

    public static String getProvinceIdByName(String provinceName) {

        if (provinceName != null) {

            if (provinceName.length() >= 2) {
                String shortName = provinceName.substring(0, 2);
                return provinceMap.get(shortName);
            }

        }

        return null;
    }

    TextView result;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_layout);

        result = (TextView) findViewById(R.id.result);

        findViewById(R.id.fun1).setOnClickListener(this);
        findViewById(R.id.func2).setOnClickListener(this);
        findViewById(R.id.get_metadata).setOnClickListener(this);


        //判断是否为android6.0系统版本，如果是，需要动态添加权限
//        if (Build.VERSION.SDK_INT>=23){

    }

    public void checkCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //申请权限，REQUEST_TAKE_PHOTO_PERMISSION是自定义的常量
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    BAIDU_READ_PHONE_STATE);
            Toast.makeText(this, "request", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "have", Toast.LENGTH_SHORT).show();

        }
    }

    public void showContacts() {
//        Build.VERSION_CODES.M
        if (PermissionChecker.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || PermissionChecker.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || PermissionChecker.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "没有权限,请手动开启定位权限", Toast.LENGTH_SHORT).show();
            // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE}, BAIDU_READ_PHONE_STATE);
        }else {
            Toast.makeText(getApplicationContext(), "have opened", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fun1:
//                getLocaltionFunc1();
                result.setText("");
                break;
            case R.id.func2:
                getLocationFunc2();
                break;
            case R.id.get_metadata:
//                getMetaData();
                mLocationClient.stopLocation();
                mLocationClient.startLocation();
                break;
        }
    }

    private void getMetaData() {
        showContacts();
        checkCamera();
//        try {
//            ApplicationInfo appInfo = this.getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
//
//            String msg=appInfo.metaData.getString("winson");
//
//            result.setText(msg);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }


    }

    private void getLocaltionFunc1() {
//        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//        if (location != null) {
//            Log.d(TAG, "get location x : " + location.getLongitude() + " , y : " + location.getLatitude());
//        }

        String province = ((ApiDemoApplication) getApplication()).getProvince();
        result.setText("localtion province : " + province);

    }

    private void getLocationFunc2() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                Log.d(TAG, "getLocationFunc2 ---> " + aMapLocation);
//                result.setText(getProvinceIdByName(aMapLocation.getProvince()));
                result.setText(aMapLocation.getProvince());
            }
        });

        //声明AMapLocationClientOption对象
        AMapLocationClientOption mLocationOption = null;
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();


        AMapLocationClientOption option = new AMapLocationClientOption();
        /**
         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
         */
        option.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        if (null != mLocationClient) {
            mLocationClient.setLocationOption(option);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }

        option.setLocationCacheEnable(false);
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(1000 * 60 * 60);
//        mLocationOption.setInterval(2000);

        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);

        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();

    }

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
//    public AMapLocationListener mLocationListener = new AMapLocationListener();


}
