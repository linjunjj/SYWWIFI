package com.sywwifi.syw.module.wifi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.sywwifi.syw.R;

public class AgreementActivity extends AppCompatActivity {

    private WebView wb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);
        wb = (WebView) findViewById(R.id.wb_xq);
        wb.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        wb.getSettings().setSupportZoom(false);
        // 设置出现缩放工具
        //wb.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        wb.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        wb.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wb.getSettings().setLoadWithOverviewMode(true);
        wb.getSettings().setDomStorageEnabled(true);//有可能是DOM储存API没有打开

        wb.loadUrl("xieyi_sh.html");
    }
}
