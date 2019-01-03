package com.dingtao.rrmmp.activity;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.dingtao.rrmmp.R;
import com.dingtao.rrmmp.bean.Result;
import com.dingtao.rrmmp.core.DataCall;
import com.dingtao.rrmmp.core.WDActivity;
import com.dingtao.rrmmp.core.WDApplication;
import com.dingtao.rrmmp.core.exception.ApiException;
import com.dingtao.rrmmp.presenter.LoginPresenter;
import com.dingtao.rrmmp.presenter.RegisterPresenter;
import com.dingtao.rrmmp.util.MD5Utils;
import com.dingtao.rrmmp.util.UIUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends WDActivity {

    RegisterPresenter requestPresenter;
    @BindView(R.id.login_mobile)
    EditText mMobile;

    @BindView(R.id.login_pas)
    EditText mPas;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void destoryData() {
        requestPresenter.unBind();
    }

    @Override
    protected void initView() {
        requestPresenter = new RegisterPresenter(new RegisterCall());
    }

    @OnClick(R.id.register_btn)
    public void login(){
        String m = mMobile.getText().toString();
        String p = mPas.getText().toString();
        if (TextUtils.isEmpty(m)){
            UIUtils.showToastSafe("请输入正确的手机号");
            return;
        }
        if (TextUtils.isEmpty(p)){
            UIUtils.showToastSafe("请输入密码");
            return;
        }
        mLoadDialog.show();
        requestPresenter.reqeust(m,MD5Utils.md5(p));
    }

    private boolean pasVisibile = false;

    @OnClick(R.id.login_pas_eye)
    public void eyePas(){
        if (pasVisibile){//密码显示，则隐藏
            mPas.setTransformationMethod(PasswordTransformationMethod.getInstance());
            pasVisibile = false;
        }else{//密码隐藏则显示
            mPas.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            pasVisibile = true;
        }
    }

    @OnClick(R.id.login_text)
    public void remPas(){
       finish();
    }

    /**
     * @author dingtao
     * @date 2018/12/28 10:44 AM
     * 注册
     */
    class RegisterCall implements DataCall<Result> {

        @Override
        public void success(Result result) {
            mLoadDialog.cancel();
            if (result.getStatus().equals("0000")){
                UIUtils.showToastSafe("注册成功，请登录");
                finish();
            }else{
                UIUtils.showToastSafe(result.getStatus()+"  "+result.getMessage());
            }
        }

        @Override
        public void fail(ApiException e) {
            mLoadDialog.cancel();
            UIUtils.showToastSafe(e.getCode()+" "+e.getDisplayMessage());
        }
    }
}
