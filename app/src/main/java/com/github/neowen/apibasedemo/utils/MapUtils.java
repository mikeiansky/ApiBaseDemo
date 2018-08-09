package com.github.neowen.apibasedemo.utils;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import java.util.HashMap;
import java.util.Map;

public class MapUtils {

    private static final String TAG = MapUtils.class.getSimpleName();

    Context context;
    private String provinceId;

    public MapUtils(Context context) {
        this.context = context;
        getLocation();
    }

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

    public String getLocalProvinceId() {
        return provinceId;
    }

    private void getLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(context.getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    provinceId = getProvinceIdByName(aMapLocation.getProvince());
                    Log.d(TAG, "location province : " + aMapLocation.getProvince());
                }
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

        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(1000 * 60 * 60);

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

}
