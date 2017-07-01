package com.sywwifi.syw.base;

import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by linjun on 2017/7/1.
 */

public abstract class BaseAppCompatActivity extends AppCompatActivity {
    /**
     * 获得布局ID
     * @return
     */
    protected  abstract  int getLayoutId();
    protected  abstract  void initView();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setContentView(getLayoutId());
        getLayoutId();
    }
private void FullScreen(){
    if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
        Window window=getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        );

    }


}
}
