package com.dingtao.rrmmp.activity;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.dingtao.rrmmp.util.MD5Utils;
import com.dingtao.rrmmp.util.UIUtils;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class LoginActivity extends WDActivity {

    LoginPresenter requestPresenter;
    @BindView(R.id.login_mobile)
    EditText mMobile;

    @BindView(R.id.login_pas)
    EditText mPas;

    @BindView(R.id.login_rem_pas)
    CheckBox mRemPas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        List<UserInfo> list = userInfoDao.loadAll();
//        for (int i = 0; i < list.size(); i++) {
//            if (list.get(i).getStaus()==1){
//                //跳转
//                return;
//            }
//        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void destoryData() {
        requestPresenter.unBind();
    }

    @Override
    protected void initView() {
        requestPresenter = new LoginPresenter(new LoginCall());
        boolean remPas = WDApplication.getShare().getBoolean("remPas",true);
        if (remPas){
            mRemPas.setChecked(true);
            mMobile.setText(WDApplication.getShare().getString("mobile",""));
            mPas.setText(WDApplication.getShare().getString("pas",""));
        }
    }

    @OnClick(R.id.login_btn)
    public void login(){
        String m = mMobile.getText().toString();
        String p = mPas.getText().toString();
        if (TextUtils.isEmpty(m)){
            Toast.makeText(this,"请输入正确的手机号",Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(p)){
            Toast.makeText(this,"请输入密码",Toast.LENGTH_LONG).show();
            return;
        }
        if (mRemPas.isChecked()){
            WDApplication.getShare().edit().putString("mobile",m)
                    .putString("pas",p).commit();
        }
        mLoadDialog.show();
        requestPresenter.reqeust(m,MD5Utils.md5(p));
    }

    @OnClick(R.id.login_rem_pas)
    public void remPas(){
        WDApplication.getShare().edit()
                .putBoolean("remPas",mRemPas.isChecked()).commit();
    }

    /**
     * @author dingtao
     * @date 2018/12/28 10:44 AM
     * 登录
     */
    class LoginCall implements DataCall<Result> {

        @Override
        public void success(Result result) {
            mLoadDialog.cancel();
            if (result.getStatus().equals("0000")){

            }else{
                UIUtils.showToastSafe(result.getStatus()+"  "+result.getMessage());
            }
            //result.getData().setStatus(1);设置用户登录状态为1
            //userdao.insertOrReplace(result.getData());保存用户数据
            //跳转页面
        }

        @Override
        public void fail(ApiException e) {
            mLoadDialog.cancel();
            UIUtils.showToastSafe(e.getCode()+" "+e.getDisplayMessage());
        }
    }
}
