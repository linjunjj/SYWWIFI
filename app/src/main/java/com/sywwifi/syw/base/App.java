package com.sywwifi.syw.base;

import android.app.Application;

import com.sywwifi.syw.Handler.FetchPatchHandler;
import com.sywwifi.syw.constant.BuildConfig;
import com.sywwifi.syw.utils.OkHttpUtil;
import com.tencent.tinker.loader.app.ApplicationLike;
import com.tinkerpatch.sdk.TinkerPatch;
import com.tinkerpatch.sdk.loader.TinkerPatchApplicationLike;

/**
 * Created by shaowen on 2017/6/26.
 */

public  class App extends Application {
    private static App app;
    private ApplicationLike tinkerApplicationLike;
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
   private  void initTink(){

       if (BuildConfig.TINKER_ENABLE) {

           // 我们可以从这里获得Tinker加载过程的信息
           tinkerApplicationLike = TinkerPatchApplicationLike.getTinkerPatchApplicationLike();

           // 初始化TinkerPatch SDK, 更多配置可参照API章节中的,初始化SDK
           TinkerPatch.init(tinkerApplicationLike)
                   .reflectPatchLibrary()
                   .setPatchRollbackOnScreenOff(true)
                   .setPatchRestartOnSrceenOff(true);

           // 每隔3个小时去访问后台时候有更新,通过handler实现轮训的效果
           new FetchPatchHandler().fetchPatchWithInterval(3);
       }
   }


    public static App getApp()
    {
        return app;
    }

}
