package com.dingtao.rrmmp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.dingtao.rrmmp.bean.Result;
import com.dingtao.rrmmp.http.IRequest;
import com.dingtao.rrmmp.http.NetworkManager;
import com.dingtao.rrmmp.presenter.RequestPresenter;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    RequestPresenter requestPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        requestPresenter = new RequestPresenter(new RegisterCall());
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
        public void accept(Result result) throws Exception {
            Toast.makeText(getBaseContext(),result.getCode()+"   "+result.getMsg(),Toast.LENGTH_LONG).show();
        }
    }
}
