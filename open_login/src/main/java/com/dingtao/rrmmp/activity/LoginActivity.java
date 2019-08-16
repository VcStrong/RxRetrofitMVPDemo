package com.dingtao.rrmmp.activity;

import android.Manifest;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dingtao.common.bean.UserInfo;
import com.dingtao.common.core.DataCall;
import com.dingtao.common.core.WDActivity;
import com.dingtao.common.core.WDApplication;
import com.dingtao.common.core.db.DaoMaster;
import com.dingtao.common.core.db.UserInfoDao;
import com.dingtao.common.core.exception.ApiException;
import com.dingtao.common.util.Constant;
import com.dingtao.rrmmp.login.R;
import com.dingtao.rrmmp.login.R2;
import com.dingtao.rrmmp.presenter.LoginPresenter;
import com.dingtao.rrmmp.util.MD5Utils;
import com.dingtao.common.util.UIUtils;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

@Route(path = Constant.ACTIVITY_URL_LOGIN)
public class LoginActivity extends WDActivity {


    LoginPresenter requestPresenter;
    @BindView(R2.id.login_mobile)
    EditText mMobile;

    @BindView(R2.id.login_pas)
    EditText mPas;

    @BindView(R2.id.login_rem_pas)
    CheckBox mRemPas;

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
        boolean remPas = WDApplication.getShare().getBoolean("remPas", true);
        if (remPas) {
            mRemPas.setChecked(true);
            mMobile.setText(WDApplication.getShare().getString("mobile", ""));
            mPas.setText(WDApplication.getShare().getString("pas", ""));
        }
        if (!EasyPermissions.hasPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE})) {
            EasyPermissions.requestPermissions(this,
                    "发布图片", 10,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE});
        }
    }

    @OnClick(R2.id.login_btn)
    public void login() {
        String m = mMobile.getText().toString();
        String p = mPas.getText().toString();
        if (TextUtils.isEmpty(m)) {
            Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(p)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_LONG).show();
            return;
        }
        if (mRemPas.isChecked()) {
            WDApplication.getShare().edit().putString("mobile", m)
                    .putString("pas", p).commit();
        }
        mLoadDialog.show();
        requestPresenter.reqeust(m, MD5Utils.md5(p));
    }

    /**
     * 重写onRequestPermissionsResult，用于接受请求结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //将请求结果传递EasyPermission库处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void cancelLoadDialog() {
        super.cancelLoadDialog();
        requestPresenter.cancelRequest();
    }

    @OnClick(R2.id.login_rem_pas)
    public void remPas() {
        WDApplication.getShare().edit()
                .putBoolean("remPas", mRemPas.isChecked()).commit();
    }

    private boolean pasVisibile = false;

    @OnClick(R2.id.login_pas_eye)
    public void eyePas() {
        if (pasVisibile) {//密码显示，则隐藏
            mPas.setTransformationMethod(PasswordTransformationMethod.getInstance());
            pasVisibile = false;
        } else {//密码隐藏则显示
            mPas.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            pasVisibile = true;
        }
    }

    @OnClick(R2.id.register_text)
    public void register() {
        intentByRouter(Constant.ACTIVITY_URL_REGISTER);
    }


    /**
     * @author dingtao
     * @date 2018/12/28 10:44 AM
     * 登录
     */
    class LoginCall implements DataCall<UserInfo> {

        @Override
        public void success(UserInfo result, Object... args) {
            mLoadDialog.cancel();
            result.setStatus(1);//设置登录状态，保存到数据库
            UserInfoDao userInfoDao = DaoMaster.newDevSession(getBaseContext(), UserInfoDao.TABLENAME).getUserInfoDao();
            userInfoDao.insertOrReplace(result);//保存用户数据
            intentByRouter(Constant.ACTIVITY_URL_MAIN);
            finish();
        }

        @Override
        public void fail(ApiException e, Object... args) {
            mLoadDialog.cancel();
            UIUtils.showToastSafe(e.getCode() + " " + e.getDisplayMessage());
        }
    }
}
