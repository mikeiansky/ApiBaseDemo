package com.winson.apibasedemo.bean

class ParasiteBean {

    // 渠道,安装时就在清单中制定了，appStore等
    var appChannel: String? = ParasiteBaseInfo.appChannel

    //应用唯一标识
    val appId: String? = "ciwei_app"

    //平台
    val appPlatform: String = "android"

    // 应用版本
    var appVersion: String? = ParasiteBaseInfo.appVersion

    // 品牌
    var brand: String? = ParasiteBaseInfo.brand

    // 运营商
    var carrier: String? = null

    // 日志创建时间
    val createdAtMs: Long = System.currentTimeMillis()

    // 设备唯一标识
    var deviceId: String? = ParasiteBaseInfo.deviceId

    // 机型
    var deviceStyle: String? = ParasiteBaseInfo.deviceStyle

    // 网络
    var network: String? = null

    //操作系统
    var osType: String? = ParasiteBaseInfo.osType

    // 分辨率
    var screenSize: String? = ParasiteBaseInfo.screenSize

}