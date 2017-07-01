package com.sywwifi.syw.module.wifi.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.TrafficStats;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sywwifi.syw.R;
import com.sywwifi.syw.base.BaseFragment;
import com.sywwifi.syw.eventbus.MessageIsSyw;
import com.sywwifi.syw.module.wifi.activity.LoginActivity;
import com.sywwifi.syw.utils.IsSywWifi;

import com.sywwifi.syw.views.WaveProgressView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class WifiFragment extends BaseFragment implements View.OnClickListener {

    public View view;

    private WaveProgressView mCircleView;
    private TextView mTvIsSyw;
    private TextView mTvFlow;
    private TextView mTvWenHao;
    private Button  mBtNet;
    private Button mBtLogin;

    public WifiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wifi, container, false);
        return view;
    }

    @Override
    public void requestData() {
        mCurState = STATE_SUCCESS;
    }

    @Override
    public void showPage() {
        view=View.inflate(getActivity(),R.layout.fragment_wifi,null);
        initView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    private void initView() {
        mBtLogin = (Button) view.findViewById(R.id.bt_wifi_login);
        mBtLogin.setOnClickListener(this);
        mBtNet = (Button) view.findViewById(R.id.bt_wifi_net);
        mBtNet.setOnClickListener(this);
        mTvWenHao = (TextView) view.findViewById(R.id.tv_wifi_wenhao);
        mTvFlow = (TextView) view.findViewById(R.id.tv_wifi_flow);
        mTvFlow.setText(getTraffic()+"M");//设置用了多少M流量
        mCircleView = (WaveProgressView) view.findViewById(R.id.wpv_2);
        mCircleView.setProgress((int)(getTraffic()));//用的流量百分比
        mTvIsSyw = (TextView) view.findViewById(R.id.tv_isnetwifi);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_wifi_net:
                isSyw();
                break;
            case R.id.bt_wifi_login:
                //跳到登入页面
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;

        }

    }

    public void isSyw(){
        int linkSywWifi = IsSywWifi.isLinkSywWifi(getContext());
        switch (linkSywWifi){
            case 1:
            case 3:
                //打开wifi系统设置
                Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                startActivity(intent);
                break;
            case 2:
                Toast.makeText(getActivity(), "已经连接上云网", Toast.LENGTH_LONG).show();
                break;
        }
     }

    //从网络监听广播里接受，判断链接是否连接上云网wifi
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void isSyw(MessageIsSyw messageIsSyw){
        int message = messageIsSyw.isSyw;
        switch (message) {
            case 1:
                    mTvIsSyw.setText("手机4G流量");
                    mTvWenHao.setTextColor(Color.parseColor("#ff0000"));

                break;
            case 2:
                    mTvIsSyw.setText("已连接上云网");
                    mTvWenHao.setTextColor(Color.parseColor("#23BD75"));
                break;
            case 3:
                   mTvIsSyw.setText("未使用上云网");
                   mTvWenHao.setTextColor(Color.parseColor("#23BD75"));
                break;
        }

    }

    //统计流量
    public double getTraffic(){
       // 这次开机后的流量
       long traffic = TrafficStats.getMobileRxBytes() + TrafficStats.getMobileTxBytes();

       //这次前的流量
       SharedPreferences mTraffic = getContext().getSharedPreferences("traffic", MODE_PRIVATE);
       long alwaysTraffic = mTraffic.getLong("wifitraffic", 0);
       //相加转换为mb
       double mb = (traffic + alwaysTraffic) / (1024 * 1024);
       return mb;
   }


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();

    }
}
