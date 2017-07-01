package com.sywwifi.syw.base;

import android.app.Application;

import com.sywwifi.syw.utils.OkHttpUtil;

/**
 * Created by shaowen on 2017/6/26.
 */

public  class App extends Application {
    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        //初始化
        init();
    }

    private void init(){
        OkHttpUtil.initOkHttp(this);
    }



    public static App getApp()
    {
        return app;
    }

}
