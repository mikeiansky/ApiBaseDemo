package com.winson.apibasedemo.tools

import android.annotation.SuppressLint
import android.content.Context
import android.net.wifi.WifiManager
import android.os.Bundle
import android.telephony.TelephonyManager
import android.widget.TextView
import com.winson.apibasedemo.base.BaseActivity
import java.lang.StringBuilder
import android.net.ConnectivityManager
import android.provider.Settings
import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.util.*


class DataCollectActivity : BaseActivity() {

    // deviceId 设备唯一ID
    // version 版本号
    // channel 渠道
    // platform 平台
    // osType 操作系统平台
    // deviceStyle 机器型号
    // brand 品牌
    // size 分辨率
    // network 网络
    // duration 停留时长

    fun getMacAddressFromIp(context: Context): String {
        var mac_s = ""
        val buf = StringBuilder()
        try {
            val mac: ByteArray
            val ne = NetworkInterface.getByInetAddress(InetAddress.getByName(getIpAddress(context)))
            mac = ne.getHardwareAddress()
            for (b in mac) {
                buf.append(String.format("%02X:", b))
            }
            if (buf.length > 0) {
                buf.deleteCharAt(buf.length - 1)
            }
            mac_s = buf.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return mac_s
    }


    fun getIpAddress(context: Context): String? {
        val info =
            (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
        if (info != null && info.isConnected) {
            // 3/4g网络
            if (info.type == ConnectivityManager.TYPE_MOBILE) {
                try {
                    val en = NetworkInterface.getNetworkInterfaces()
                    while (en.hasMoreElements()) {
                        val intf = en.nextElement()
                        val enumIpAddr = intf.getInetAddresses()
                        while (enumIpAddr.hasMoreElements()) {
                            val inetAddress = enumIpAddr.nextElement()
                            if (!inetAddress.isLoopbackAddress() && inetAddress is Inet4Address) {
                                return inetAddress.getHostAddress()
                            }
                        }
                    }
                } catch (e: SocketException) {
                    e.printStackTrace()
                }

            } else if (info.type == ConnectivityManager.TYPE_WIFI) {
                //  wifi网络
                val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
                val wifiInfo = wifiManager.connectionInfo
                return intIP2StringIP(wifiInfo.ipAddress)
            } else if (info.type == ConnectivityManager.TYPE_ETHERNET) {
                // 有限网络
                return getLocalIp()
            }
        }
        return null
    }

    private fun intIP2StringIP(ip: Int): String {
        return (ip and 0xFF).toString() + "." +
                (ip shr 8 and 0xFF) + "." +
                (ip shr 16 and 0xFF) + "." +
                (ip shr 24 and 0xFF)
    }


    // 获取有限网IP
    private fun getLocalIp(): String {
        try {
            val en = NetworkInterface.getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val intf = en.nextElement()
                val enumIpAddr = intf.getInetAddresses()
                while (enumIpAddr.hasMoreElements()) {
                    val inetAddress = enumIpAddr.nextElement()
                    if (!inetAddress.isLoopbackAddress() && inetAddress is Inet4Address) {
                        return inetAddress.getHostAddress()
                    }
                }
            }
        } catch (ex: SocketException) {
            ex.printStackTrace()
        }

        return "0.0.0.0"
    }


    @SuppressLint("HardwareIds")
    override fun onBind(savedInstanceState: Bundle?) {
        super.onBind(savedInstanceState)
        setContentView(com.winson.apibasedemo.R.layout.act_data_collect)

        //wifi mac地址
        val wifi = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val info = wifi.connectionInfo
        val wifiMac = info.macAddress

        val infoText = findViewById<TextView>(com.winson.apibasedemo.R.id.info)
        val SerialNumber = android.os.Build.SERIAL
        val tm = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val deviceId = getMacAddressFromIp(this)

        val ANDROID_ID = Settings.System.getString(contentResolver, Settings.System.ANDROID_ID)

        val infoBuilder = StringBuilder()
        infoBuilder.append("deviceId:")
        infoBuilder.append(deviceId)
        infoBuilder.append("\r\n")
        infoBuilder.append("ANDROID_ID:")
        infoBuilder.append(ANDROID_ID)
        infoBuilder.append("\r\n")

        val uuid = UUID.randomUUID().toString()

        infoBuilder.append("uuid:")
        infoBuilder.append(uuid)
        infoBuilder.append("\r\n")

        //制造商 （Manufacturer）
        val manufacturer = android.os.Build.MANUFACTURER
        infoBuilder.append("manufacturer:")
        infoBuilder.append(manufacturer)
        infoBuilder.append("\r\n")

        //型号（Model）
        val model = android.os.Build.MODEL
        infoBuilder.append("model:")
        infoBuilder.append(model)
        infoBuilder.append("\r\n")

        //品牌（Brand）
        val brand = android.os.Build.BRAND
        infoBuilder.append("brand:")
        infoBuilder.append(brand)
        infoBuilder.append("\r\n")

        //设备名 （Device）
        val device = android.os.Build.DEVICE
        infoBuilder.append("device:")
        infoBuilder.append(device)
        infoBuilder.append("\r\n")


        infoText.text = infoBuilder.toString()

    }

}