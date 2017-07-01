package com.sywwifi.syw.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import com.sywwifi.syw.eventbus.MessageIsSyw;
import com.sywwifi.syw.utils.IsSywWifi;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by shaowen on 2017/6/26.
 */

public class NetworkConnectChangedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
            int linkSywWifi = IsSywWifi.isLinkSywWifi(context);
            switch (linkSywWifi) {
                case 1:
                    EventBus.getDefault().post(new MessageIsSyw(1));
                    break;
                case 2:
                    EventBus.getDefault().post(new MessageIsSyw(2));
                    break;
                case 3:
                    EventBus.getDefault().post(new MessageIsSyw(3));
                    break;
            }
        }


    }

