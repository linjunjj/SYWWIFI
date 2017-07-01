package com.sywwifi.syw.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.TrafficStats;
import android.util.Log;

import com.sywwifi.syw.utils.FileUtil;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Intent.ACTION_BOOT_COMPLETED;
import static android.content.Intent.ACTION_SHUTDOWN;

/**
 * Created by shaowen on 2017/6/28.
 */

public class OnOffBrReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
           Log.d("TAG",intent.getAction()+"");
        if (intent.getAction().equals("android.intent.action.ACTION_SHUTDOWN")) {
            long mobileRxBytes = TrafficStats.getMobileRxBytes();
            long mobileTxBytes = TrafficStats.getMobileTxBytes();
            //这次关机前网络产生的移动总流量
            long traffic = mobileRxBytes + mobileTxBytes;
           /* File traffic1 = FileUtil.getDir("Traffic");
            try {
                File file =  null;
                FileReader fileReader = new FileReader(file);
                if(!file.exists()){
                    file  = new File(String.valueOf(traffic1));
                }
                int temp=0;
                String str="";
                while ((temp=fileReader.read())!=-1){
                  str =str +(char)temp;
                }
                FileWriter fileWriter = new FileWriter(traffic1);
               fileWriter.write(str + String.valueOf(traffic));
                Log.d("TAG",traffic+str);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            SharedPreferences mTraffic = context.getSharedPreferences("traffic", MODE_PRIVATE);
            long alwaysTraffic = mTraffic.getLong("wifitraffic", 0);
            Log.d("TAG",alwaysTraffic+"-------------------------------------");
            // 总外网流量 = 以存储的移动总流量 + 这次关机前网络产生的移动总流量

            mTraffic.edit().putLong("wifitraffic",alwaysTraffic + traffic);
            Log.d("TAG",alwaysTraffic + traffic+"-------------------------------------");

        }
        if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
            Log.d("TAG","----------------------------1");

        }


    }
}
