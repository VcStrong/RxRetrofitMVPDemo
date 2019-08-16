package com.dingtao.rrmmp.activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dingtao.common.util.Constant;
import com.dingtao.rrmmp.main.R;
import com.dingtao.rrmmp.main.R2;
import com.dingtao.common.core.WDActivity;
import com.dingtao.rrmmp.fragment.CircleFragment;
import com.dingtao.rrmmp.fragment.HomeFragment;
import com.dingtao.rrmmp.fragment.MeFragment;

import butterknife.BindView;

@Route(path = Constant.ACTIVITY_URL_MAIN)
public class MainActivity extends WDActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R2.id.bottom_menu)
    RadioGroup bottomMenu;
    HomeFragment homeFragment;
    CircleFragment circleFragment;
    MeFragment meFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        bottomMenu.setOnCheckedChangeListener(this);

        homeFragment = new HomeFragment();
        circleFragment = new CircleFragment();
        meFragment = new MeFragment();

        currentFragment = homeFragment;
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.add(R.id.container, homeFragment)
                .show(homeFragment).commit();

    }

    @Override
    protected void destoryData() {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.home_btn) {
            showFragment(homeFragment);
        } else if (checkedId == R.id.circle_btn) {
            showFragment(circleFragment);
        }else if (checkedId == R.id.me_btn){
            showFragment(meFragment);
        }
    }

    Fragment currentFragment;

    /**
     * 展示Fragment
     */
    private void showFragment(Fragment fragment) {
        if (currentFragment != fragment) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(currentFragment);
            currentFragment = fragment;
            if (!fragment.isAdded()) {
                transaction.add(R.id.container, fragment).show(fragment).commit();
            } else {
                transaction.show(fragment).commit();
            }
        }
    }
}
