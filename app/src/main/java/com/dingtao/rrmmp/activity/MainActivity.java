package com.dingtao.rrmmp.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.dingtao.rrmmp.R;
import com.dingtao.rrmmp.bean.Result;
import com.dingtao.rrmmp.core.WDActivity;
import com.dingtao.rrmmp.fragment.CircleFragment;
import com.dingtao.rrmmp.fragment.HomeFragment;
import com.dingtao.rrmmp.fragment.MeFragment;
import com.dingtao.rrmmp.util.UIUtils;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class MainActivity extends WDActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.bottom_menu)
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

        currentFragment = circleFragment;
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.add(R.id.container, circleFragment)
                .show(circleFragment).commit();

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
