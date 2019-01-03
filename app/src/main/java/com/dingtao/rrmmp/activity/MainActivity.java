package com.dingtao.rrmmp.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.dingtao.rrmmp.R;
import com.dingtao.rrmmp.bean.Result;
import com.dingtao.rrmmp.core.WDActivity;
import com.dingtao.rrmmp.fragment.MainFragment;
import com.dingtao.rrmmp.presenter.RequestPresenter;
import com.dingtao.rrmmp.util.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class MainActivity extends WDActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.bottom_menu)
    RadioGroup bottomMenu;
    MainFragment mainFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        bottomMenu.setOnCheckedChangeListener(this);

        mainFragment = new MainFragment();
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.add(R.id.container,mainFragment).show(mainFragment).commit();

    }

    @Override
    protected void destoryData() {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId==R.id.home_btn){
//            tx.show(mainFragment);
        }else if (checkedId==R.id.circle_btn){

        }
    }

    /**
     * @author dingtao
     * @date 2018/12/28 10:44 AM
     * 登录
     */
    class RegisterCall implements Consumer<Result> {
        @Override
        public void accept(Result result){
            UIUtils.showToastSafe(result.getStatus()+"   "+result.getMessage());
        }
    }
}
