package com.sywwifi.syw.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.sywwifi.syw.broadcastreceiver.NetworkConnectChangedReceiver;
import com.sywwifi.syw.broadcastreceiver.TrafficMonitoringReceiver;

/**
 * Created by shaowen on 2017/6/26.
 */

public class NetworkStateService extends Service {
    private TrafficMonitoringReceiver mTrafficMonitoringReceiver;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //注册广播
        mTrafficMonitoringReceiver = new TrafficMonitoringReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mTrafficMonitoringReceiver,filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mTrafficMonitoringReceiver);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
