package com.sywwifi.syw.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by shaowen on 2017/6/24.
 */

public class IsSywWifi {
    /**
     * 判断网络连接是否打开,包括移动数据连接
     *
     * @param context 上下文
     * @return 是否联网
     */
    public static boolean isNetworkAvailable(Context context) {
        boolean netstate = false;
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {

            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {

                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {

                        netstate = true;
                        break;
                    }
                }
            }
        }
        return netstate;
    }
    /**
     * 检测当前打开的网络类型是否WIFI
     *
     * @param context 上下文
     * @return 是否是Wifi上网
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    public  static  int OUTER_NETRE = 1;  //链接外网
    public  static  int SYW_WIFI = 2;    //链接上云网
    public  static  int  NO_WIFI_NET = 3; //链接其他wifi 或者任何没开网络

    public static int isLinkSywWifi(Context  context) {
        boolean isWifi = isWifi(context);
        boolean isNetworkAvailable = isMobileConnected(context);
        if( isWifi && isNetworkAvailable){
           return NO_WIFI_NET;
        }else if(isNetworkAvailable){

            return OUTER_NETRE;
        }else if(isWifi){
            boolean isSyw = isSyw(context);
            if(isSyw){
                return SYW_WIFI;
            }
            return NO_WIFI_NET;
        }

        return NO_WIFI_NET;
    }


    public static boolean  isSyw(Context context){
        WifiManager wifi_service = (WifiManager)context.getSystemService(WIFI_SERVICE);
        WifiInfo info = wifi_service.getConnectionInfo();
        String s = info.toString();
        int i = s.indexOf("上云网");

      return i > -1;
    }

    /**
     * 判断是否链接移动网络
     * @param context
     * @return
     */
    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            //获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            //获取NetworkInfo对象
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            //判断NetworkInfo对象是否为空 并且类型是否为MOBILE
            if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
                return networkInfo.isAvailable();
        }
        return false;
    }
}
