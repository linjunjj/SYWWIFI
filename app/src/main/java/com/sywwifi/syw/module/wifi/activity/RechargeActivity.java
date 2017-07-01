package com.sywwifi.syw.module.wifi.activity;

import android.graphics.Color;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sywwifi.syw.R;

public class RechargeActivity extends AppCompatActivity implements View.OnClickListener {

    private View mLlHj ,mLlBy,mLlDz;
    private TextView mTvHj,mTvBy,mTvDz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        intView();
    }

    private void intView() {
        mLlHj = findViewById(R.id.ll_recharge_hj);
        mLlHj.setOnClickListener(this);
        mLlBy =  findViewById(R.id.ll_recharge_by);
        mLlBy.setOnClickListener(this);
        mLlDz = findViewById(R.id.ll_recharge_dz);
        mLlDz.setOnClickListener(this);
        mTvHj = (TextView) findViewById(R.id.tv_recharge_hj);
        mTvBy = (TextView) findViewById(R.id.tv_recharge_by);
        mTvDz = (TextView) findViewById(R.id.tv_recharge_dz);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_recharge_hj:
                mLlHj.setBackground(getResources().getDrawable(R.mipmap.recharge_bt_check));
                mLlBy.setBackground(getResources().getDrawable(R.drawable.shape_bt_recharge));
                mLlDz.setBackground(getResources().getDrawable(R.drawable.shape_bt_recharge));
                mTvHj.setTextColor(Color.parseColor("#B79958"));
                mTvBy.setTextColor(Color.parseColor("#AFAFAF"));
                mTvDz.setTextColor(Color.parseColor("#AFAFAF"));
                break;
            case R.id.ll_recharge_by:
                mLlHj.setBackground(getResources().getDrawable(R.drawable.shape_bt_recharge));
                mLlBy.setBackground(getResources().getDrawable(R.mipmap.recharge_bt_check));
                mLlDz.setBackground(getResources().getDrawable(R.drawable.shape_bt_recharge));
                mTvHj.setTextColor(Color.parseColor("#AFAFAF"));
                mTvBy.setTextColor(Color.parseColor("#B79958"));
                mTvDz.setTextColor(Color.parseColor("#AFAFAF"));
                break;
            case R.id.ll_recharge_dz:
                mLlHj.setBackground(getResources().getDrawable(R.drawable.shape_bt_recharge));
                mLlBy.setBackground(getResources().getDrawable(R.drawable.shape_bt_recharge));
                mLlDz.setBackground(getResources().getDrawable(R.mipmap.recharge_bt_check));
                mTvHj.setTextColor(Color.parseColor("#AFAFAF"));
                mTvBy.setTextColor(Color.parseColor("#AFAFAF"));
                mTvDz.setTextColor(Color.parseColor("#B79958"));
                break;


        }


    }
}
