package com.sywwifi.syw.module.wifi.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sywwifi.syw.R;
import com.sywwifi.syw.module.main.MainActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mBtReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        mBtReg = (Button) findViewById(R.id.bt_reg);
        mBtReg.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_reg:
                startActivity(new Intent(this,RegOneActivity.class));
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
        super.onBackPressed();
    }
}
