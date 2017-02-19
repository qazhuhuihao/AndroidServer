package cn.hhh.myandroidserver.util;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import cn.hhh.myandroidserver.MyApplication;

/**
 * Created by hhh on 2017/2/18.
 */

public class WifiUtil {
    public String ipString, mac, ssid;
    private static WifiUtil wifiUtil;

    private WifiUtil() {
    }

    public static WifiUtil getInstance() {
        if (null == wifiUtil) {
            wifiUtil = new WifiUtil();
            wifiUtil.init();
            return wifiUtil;
        } else
            return wifiUtil;
    }

    private void init() {
        WifiManager wifiMan = (WifiManager) MyApplication.getInstance().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifiMan.getConnectionInfo();
        mac = info.getMacAddress();// 获得本机的MAC地址
        ssid = info.getSSID();// 获得本机所链接的WIFI名称

        int ipAddress = info.getIpAddress();
        // ipString = "";// 本机在WIFI状态下路由分配给的IP地址

        // 获得IP地址的方法一：
        if (ipAddress != 0) {
            ipString = ((ipAddress & 0xff) + "." + (ipAddress >> 8 & 0xff) + "."
                    + (ipAddress >> 16 & 0xff) + "." + (ipAddress >> 24 & 0xff));
        }
    }

}
