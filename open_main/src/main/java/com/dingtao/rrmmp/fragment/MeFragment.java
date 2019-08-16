package com.dingtao.rrmmp.fragment;

import android.content.Intent;
import android.net.Uri;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dingtao.common.util.Constant;
import com.dingtao.rrmmp.main.R;
import com.dingtao.rrmmp.main.R2;
import com.dingtao.rrmmp.activity.SetActivity;
import com.dingtao.rrmmp.adapter.CircleAdpater;
import com.dingtao.common.core.WDFragment;
import com.dingtao.common.core.db.DaoMaster;
import com.dingtao.common.core.db.UserInfoDao;
import com.dingtao.rrmmp.presenter.CirclePresenter;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author dingtao
 * @date 2019/1/2 10:28
 * qq:1940870847
 */
public class MeFragment extends WDFragment {

    @BindView(R2.id.me_bg)
    SimpleDraweeView mMeBg;
    @BindView(R2.id.me_avatar)
    SimpleDraweeView mMeAvatar;
    @BindView(R2.id.me_nickname)
    TextView mMeName;
    @BindView(R2.id.me_info)
    RelativeLayout mMeInfo;
    @BindView(R2.id.me_circle)
    RelativeLayout mMeCircle;
    @BindView(R2.id.me_foot)
    RelativeLayout mMeFoot;
    @BindView(R2.id.me_wallet)
    RelativeLayout mMeWallet;
    @BindView(R2.id.me_address)
    RelativeLayout mMeAddress;

    private CircleAdpater mCircleAdapter;

    CirclePresenter circlePresenter;

    @Override
    public String getPageName() {
        return "我的Fragment";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_me;
    }

    @Override
    protected void initView() {
        mMeBg.setImageURI(Uri.parse(LOGIN_USER.getHeadPic()));
        mMeAvatar.setImageURI(Uri.parse(LOGIN_USER.getHeadPic()));
        mMeName.setText(LOGIN_USER.getNickName());
    }

    @OnClick(R2.id.logout_btn)
    public void logout(){
        UserInfoDao userInfoDao = DaoMaster.newDevSession(getActivity(),UserInfoDao.TABLENAME).getUserInfoDao();
        userInfoDao.delete(LOGIN_USER);//删除用户

        intentByRouter(Constant.ACTIVITY_URL_LOGIN);
        getActivity().finish();//本页面关闭
    }

    @OnClick(R2.id.me_setting)
    public void setting(){
        startActivity(new Intent(getActivity(),SetActivity.class));
    }

}
