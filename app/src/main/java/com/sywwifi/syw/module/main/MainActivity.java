package com.sywwifi.syw.module.main;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sywwifi.syw.R;
import com.sywwifi.syw.base.BaseAppCompatActivity;
import com.sywwifi.syw.broadcastreceiver.NetworkConnectChangedReceiver;
import com.sywwifi.syw.module.Recreation.fragment.RecreationFragment;
import com.sywwifi.syw.module.consumption.fragment.ConsumFragment;
import com.sywwifi.syw.module.my.fragment.MyFragment;
import com.sywwifi.syw.module.wifi.fragment.WifiFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseAppCompatActivity {
    @BindView(R.id.tv_actionBar)
    TextView mTvActionBar;
    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.rb_wifi)
    RadioButton rbWifi;
    @BindView(R.id.rb_recreation)
    RadioButton rbRecreation;
    @BindView(R.id.rb_consumption)
    RadioButton rbConsumption;
    @BindView(R.id.rb_my)
    RadioButton rbMy;
    @BindView(R.id.rg_main)
    RadioGroup rgMain;
    private int lastIndex = -1;
    private Fragment[] fragments = new Fragment[4];

    private NetworkConnectChangedReceiver mNetworkConnectChangedReceiver;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;

    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        mNetworkConnectChangedReceiver = new NetworkConnectChangedReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetworkConnectChangedReceiver, filter);
        ((RadioButton)rgMain.getChildAt(0)).setChecked(true);
        showFragment(0);
    }
    private void showFragment(int currentIndex) {
        if (lastIndex == currentIndex)
            return;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        //显示? 判断是否曾经添加过...
        if (lastIndex != -1) {
            ft.hide(fragments[lastIndex]);
//            ft.detach(fragments[lastIndex]);
        }
        if (fragments[currentIndex] == null) {
            switch (currentIndex) {
                case 0:
                    fragments[currentIndex] = new WifiFragment();

                    break;
                case 1:

                    fragments[currentIndex] = new RecreationFragment();

                    break;
                case 2:

                    fragments[currentIndex] = new ConsumFragment();

                    break;
                case 3:
                    fragments[currentIndex] = new MyFragment();
                    break;
            }
            ft.add(R.id.fl_container, fragments[currentIndex]);
        } else {
            ft.show(fragments[currentIndex]);
//            ft.attach(fragments[currentIndex]);
        }

        ft.commit();//提交
        lastIndex = currentIndex;//当前的位置保存
    }





    @OnClick({R.id.rb_wifi, R.id.rb_recreation, R.id.rb_consumption, R.id.rb_my, R.id.rg_main})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.rb_wifi:
                showFragment(0);
                mTvActionBar.setVisibility(View.VISIBLE);
                mTvActionBar.setText("上云网");
                break;
            case R.id.rb_recreation:
                showFragment(1);
                mTvActionBar.setVisibility(View.VISIBLE);
                mTvActionBar.setText("娱乐");
                break;
            case R.id.rb_consumption:
                showFragment(2);
                mTvActionBar.setVisibility(View.VISIBLE);
                mTvActionBar.setText("抽奖");
                break;
            case R.id.rb_my:
                showFragment(3);
                mTvActionBar.setVisibility(View.GONE);
                break;
            case R.id.rg_main:
                break;
        }
    }
    @Override
    protected void onDestroy() {
        unregisterReceiver(mNetworkConnectChangedReceiver);
        super.onDestroy();
    }

}
