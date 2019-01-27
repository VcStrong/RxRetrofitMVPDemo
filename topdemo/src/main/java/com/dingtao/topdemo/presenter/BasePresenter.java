package com.dingtao.topdemo.presenter;

import android.widget.BaseAdapter;

import com.dingtao.topdemo.bean.Car;
import com.dingtao.topdemo.bean.Result;
import com.dingtao.topdemo.http.CustomException;
import com.dingtao.topdemo.http.ResponseTransformer;
import com.dingtao.topdemo.http.SchedulerProvider;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import retrofit2.Response;

/**
 * @author dingtao
 * @date 2019/1/14 14:52
 * qq:1940870847
 */
public abstract class BasePresenter<T> {

    DataCall dataCall;

    public BasePresenter(DataCall dataCall){
        this.dataCall = dataCall;
    }

    public void request(Observable observable){
        observable.compose(ResponseTransformer.handleResult())
                .compose(SchedulerProvider.getInstance().applySchedulers())
                .subscribe(new Consumer<T>() {
                               @Override
                               public void accept(T result) throws Exception {
                                   // 处理数据 直接获取到List<JavaBean> carBeans
                                   dataCall.success(result);
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   // 处理异常
                                   dataCall.fail(CustomException.handleException(throwable));
                               }
                           }

                );
    }

}
