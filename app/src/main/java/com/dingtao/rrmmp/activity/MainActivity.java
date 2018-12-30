package com.dingtao.rrmmp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dingtao.rrmmp.R;
import com.dingtao.rrmmp.bean.Result;
import com.dingtao.rrmmp.presenter.RequestPresenter;
import com.dingtao.rrmmp.util.UIUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    RequestPresenter requestPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        requestPresenter = new RequestPresenter(new RegisterCall());
    }

    @OnClick(R.id.register_btn)
    public void register(){
        requestPresenter.reqeust("112312","24234");
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
