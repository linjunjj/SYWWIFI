package com.sywwifi.syw.module.wifi.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.sywwifi.syw.R;
import com.sywwifi.syw.constant.Urls;
import com.sywwifi.syw.utils.GateWayMd5;
import com.sywwifi.syw.utils.IsSywWifi;
import com.sywwifi.syw.utils.OkHttpUtil;

import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class RegTwoActivity extends AppCompatActivity implements View.OnClickListener {
    private View mIvBack;
    private EditText mEtRegPhone;
    private EditText mEtRegPwd;
    private String mPhone;
    private TextView mTvCode;
    private Handler handler = new Handler();
    private EditText mEtCode;
    private Button mBtReg;
    private String mCode = "-1";
    private CheckBox mCbXiYi;
    private TextView mTvXiYi;
    private SweetAlertDialog mLoadDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_two);
        initView();
    }

    private void initView() {
        mCbXiYi = (CheckBox) findViewById(R.id.cb_xiyi);
        mPhone = getIntent().getStringExtra("phone");
        mIvBack = findViewById(R.id.iv_oneback);
        mIvBack.setOnClickListener(this);
        mEtRegPhone = (EditText) findViewById(R.id.et_regtwo_phone);
        mEtRegPhone.setFocusable(false);
        mEtRegPhone.setText(mPhone);
        mEtRegPwd = (EditText) findViewById(R.id.et_regtwo_pwd);
        //默认密码时间戳
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = sdf.format(new Date());
        mEtRegPwd.setText(format);
        mTvCode = (TextView) findViewById(R.id.tv_getCode);
        mTvCode.setOnClickListener(this);
        mEtCode = (EditText) findViewById(R.id.et_code);
        mBtReg = (Button) findViewById(R.id.bt_regtwo_reg);
        mBtReg.setOnClickListener(this);
        mTvXiYi = (TextView) findViewById(R.id.tv_xiyi);
        mTvXiYi.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_oneback:
                startActivity(new Intent(this, RegOneActivity.class));
                finish();
                break;
            case R.id.tv_getCode:
                getCode();
                setCountDown(mTvCode);
                break;
            case R.id.bt_regtwo_reg:
               if(checkInfo()){
                 //注册
               }
                break;
            case R.id.tv_xiyi:
                startActivity(new Intent(RegTwoActivity.this, AgreementActivity.class));
                break;


        }
    }

    //获取验证码后倒计时
    public void setCountDown(final TextView mTvCode) {
        mTvCode.setClickable(false);
        mTvCode.setEnabled(false);
        handler.postDelayed(new Runnable() {
            int recLen = 60;

            @Override
            public void run() {
                mTvCode.setText(recLen + "s");
                recLen--;
                if (recLen == 0) {
                    mTvCode.setText("重新获取");
                    mCode = "-1";
                    mTvCode.setEnabled(true);
                    mTvCode.setClickable(true);
                    return;
                } else {
                    handler.postDelayed(this, 1000);
                }
            }
        }, 1000);
    }

    //获取验证码
    public void getCode() {
        //检查网络
        if (!IsSywWifi.isNetworkAvailable(this)) {
            Toast.makeText(this, "请检查网络设置", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> params = new HashMap();
        params.put("phone", mPhone);
        OkHttpUtil.postSubmitForm(Urls.URL_GET_CODE, params, new OkHttpUtil.OnGetDataListener() {
            @Override
            public void onResponse(String url, String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String backResult = jsonObject.getString("Result");
                    if (backResult.equals("1")) {
                        mCode = jsonObject.getString("sCode");
                        Log.d("TAG",mCode+"------------------------");
                    } else {
                        Toast.makeText(RegTwoActivity.this, "获取验证码失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(RegTwoActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String url, String error) {
                Log.d("TAG", error);
                Toast.makeText(RegTwoActivity.this, "服务器出错", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //检查注册信息
    private boolean checkInfo() {
        String pwd = mEtRegPwd.getText().toString().trim();
        String phone = mEtRegPhone.getText().toString().trim();
        String code = mEtCode.getText().toString().trim();

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "请输入您的手机号", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (phone.length() != 11) {
            Toast.makeText(this, "请您输入正确长度的手机号", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "请您输入密码", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (pwd.length() < 6) {
            Toast.makeText(this, "密码长度不能少于6位", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (pwd.length() > 20) {
            Toast.makeText(this, "密码过长", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mCode.equals("-1")) {
            Toast.makeText(this, "请获取验证码", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!mCode.equals(code)) {
            Toast.makeText(this, "验证码错误", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!mCbXiYi.isChecked()) {
            Toast.makeText(this, "您还没有同意用户协议", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //注册方法
    private void reg(String phone ,String pwd) {
        //检查网络
        if (!IsSywWifi.isNetworkAvailable(this)) {
            Toast.makeText(this, "请检查网络设置", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mLoadDialog == null) {
            mLoadDialog = new SweetAlertDialog(this);
            mLoadDialog.setCancelable(false);
            mLoadDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.hide();
                    if (sweetAlertDialog.getAlerType() == SweetAlertDialog.SUCCESS_TYPE) {
                    }
                }
            });
        }
        mLoadDialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
        mLoadDialog.setTitleText("正在注册");
        mLoadDialog.show();
        HashMap<String,String> params = new HashMap<>();
        params.put("phone",phone);
        params.put("password", GateWayMd5.toMD5(pwd));
        params.put("address","中国");
        OkHttpUtil.postSubmitForm(Urls.URL_REG, params, new OkHttpUtil.OnGetDataListener() {
            @Override
            public void onResponse(String url, String data) {

            }

            @Override
            public void onFailure(String url, String error) {

            }
        });


}

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, RegOneActivity.class));
        finish();
        super.onBackPressed();
    }

}


