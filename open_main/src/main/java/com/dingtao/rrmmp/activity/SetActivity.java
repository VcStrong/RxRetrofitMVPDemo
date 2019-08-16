package com.dingtao.rrmmp.activity;

import android.content.Intent;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dingtao.common.util.Constant;
import com.dingtao.rrmmp.main.R;
import com.dingtao.rrmmp.main.R2;
import com.dingtao.common.core.WDActivity;
import com.dingtao.common.core.db.DaoMaster;
import com.dingtao.common.core.db.UserInfoDao;

import butterknife.OnClick;

/**
 * @author dingtao
 * @date 2019/1/8 14:22
 * qq:1940870847
 */
@Route(path = Constant.ACTIVITY_URL_SET)
public class SetActivity extends WDActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_set;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void destoryData() {

    }

    @OnClick(R2.id.logout_btn)
    public void logout(){
        UserInfoDao userInfoDao = DaoMaster.newDevSession(this,UserInfoDao.TABLENAME).getUserInfoDao();
        userInfoDao.delete(LOGIN_USER);
        //Intent清除栈FLAG_ACTIVITY_CLEAR_TASK会把当前栈内所有Activity清空；
        //FLAG_ACTIVITY_NEW_TASK配合使用，才能完成跳转
        ARouter.getInstance().build(Constant.ACTIVITY_URL_LOGIN)
                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                .navigation();
    }
}
