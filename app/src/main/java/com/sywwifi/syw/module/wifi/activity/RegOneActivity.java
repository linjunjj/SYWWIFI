package com.sywwifi.syw.module.wifi.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.sywwifi.syw.R;

public class RegOneActivity extends AppCompatActivity implements View.OnClickListener {
   private View mIvBack;
    private Button mBtReg;
    private EditText mEtRegOne;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_one);
        initView();
    }

    private void initView() {
        mIvBack = findViewById(R.id.iv_oneback);
        mIvBack.setOnClickListener(this);
        mBtReg = (Button) findViewById(R.id.bt_regone);
        mBtReg.setOnClickListener(this);
        mEtRegOne = (EditText) findViewById(R.id.et_regone);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_oneback:
                 startActivity(new Intent(this, LoginActivity.class));
                 finish();
                break;
            case R.id.bt_regone:
                if(phoneCheckInfo()){
                    Intent intent = new Intent(this, RegTwoActivity.class);
                    intent.putExtra("phone",phone);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }
    //检查电话号码
    private boolean phoneCheckInfo() {
        phone = mEtRegOne.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "请输入您的手机号", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (phone.length() != 11) {
            Toast.makeText(this, "请您输入正确长度的手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
        super.onBackPressed();
    }
}
