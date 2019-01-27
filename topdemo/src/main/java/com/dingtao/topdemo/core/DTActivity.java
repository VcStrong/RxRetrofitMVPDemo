package com.dingtao.topdemo.core;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dingtao.topdemo.bean.UserInfo;

import butterknife.ButterKnife;

/**
 * @author dingtao
 * @date 2019/1/27 08:39
 * qq:1940870847
 */
public abstract class DTActivity extends AppCompatActivity {

    private UserInfo USER_INFO;
//    private UserDao USER_DAO;
    private ProgressDialog LOAD_DIALOG;

    public static DTActivity mForegroundActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("dt","onCreate方法线程："+Thread.currentThread().getName()+"   TaskId:"+getTaskId());

        //查询数据

        setContentView(layoutId());
        ButterKnife.bind(this);

        initDialog();
        initView();
    }

    private void initDialog(){
        LOAD_DIALOG = new ProgressDialog(this);
        LOAD_DIALOG.setMessage("loading...");
        LOAD_DIALOG.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mForegroundActivity = this;
    }

    /**
     * 获取当前处于前台的activity
     */
    public static DTActivity getForegroundActivity() {
        return mForegroundActivity;
    }

    protected abstract void initView();

    protected abstract int layoutId();

    protected void intent(Class myClass){
        startActivity(new Intent(this,myClass));
    }

    protected void intent(Class myClass,Bundle bundle){
        Intent intent = new Intent(this,myClass);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
