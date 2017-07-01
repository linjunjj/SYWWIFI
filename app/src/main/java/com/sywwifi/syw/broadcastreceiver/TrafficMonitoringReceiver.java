package com.sywwifi.syw.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;

import com.sywwifi.syw.entity.MobileTraffic;
import com.sywwifi.syw.utils.IsSywWifi;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by shaowen on 2017/6/26.
 */

public class TrafficMonitoringReceiver extends BroadcastReceiver {

    private long onAlwaysTraffic;
    public TrafficMonitoringReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
       if( IsSywWifi.isMobileConnected(context)){
           //开网时的总流量
           onAlwaysTraffic = getAlwaysTraffic(context);

       }else {
           //关网时的总流量
           long  offAlwaysTraffic= getAlwaysTraffic(context);

           //这次网络开关相减产生的移动总流量
           long traffic =  offAlwaysTraffic - onAlwaysTraffic;

           //得到以存储的移动总流量
           SharedPreferences  mTraffic = context.getSharedPreferences("traffic", MODE_PRIVATE);
           long alwaysTraffic = mTraffic.getLong("wifitraffic", 0);

           // 总外网流量 = 以存储的移动总流量 + 这次网络开关相减产生的移动总流量
           mTraffic.edit().putLong("wifitraffic",alwaysTraffic + traffic);
       }

    }
    public long getAlwaysTraffic(Context context){
        PackageManager pm = context.getPackageManager();
        ApplicationInfo ai = null;
        try {
            ai = pm.getApplicationInfo("com.sywwifi.syw", PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        int uid = ai.uid;
        //统计接受和发送流量
        long always = TrafficStats.getUidRxBytes(uid) + TrafficStats.getUidTxBytes(uid);
        return  always;
    }

}
