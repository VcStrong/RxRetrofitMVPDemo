package com.dingtao.rrmmp.presenter;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;

import com.dingtao.rrmmp.activity.LoginActivity;
import com.dingtao.rrmmp.bean.Result;
import com.dingtao.rrmmp.bean.UserInfo;
import com.dingtao.rrmmp.core.DataCall;
import com.dingtao.rrmmp.core.WDActivity;
import com.dingtao.rrmmp.core.WDApplication;
import com.dingtao.rrmmp.core.db.DaoMaster;
import com.dingtao.rrmmp.core.db.UserInfoDao;
import com.dingtao.rrmmp.core.exception.ApiException;
import com.dingtao.rrmmp.core.exception.CustomException;
import com.dingtao.rrmmp.core.exception.ResponseTransformer;
import com.dingtao.rrmmp.util.UIUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author dingtao
 * @date 2018/12/28 11:30
 * qq:1940870847
 */
public abstract class BasePresenter {
    private DataCall dataCall;

    private boolean running;
    private Observable observable;

    public BasePresenter(DataCall dataCall) {
        this.dataCall = dataCall;
    }

    protected abstract Observable observable(Object... args);

    public void reqeust(Object... args) {
        if (running) {
            return;
        }

        running = true;
        observable = observable(args);
        observable.compose(ResponseTransformer.handleResult())//添加了一个全局的异常-观察者
                .compose(new ObservableTransformer() {
                    @Override
                    public ObservableSource apply(Observable upstream) {
                        return upstream.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                })
//                .subscribeOn(Schedulers.newThread())//将请求调度到子线程上
//                .observeOn(AndroidSchedulers.mainThread())//观察响应结果，把响应结果调度到主线程中处理
                .subscribe(new Consumer<Result>() {
                    @Override
                    public void accept(Result result) throws Exception {
                        running = false;
//                        if (result.getStatus().equals("1001")){
//                            Dialog dialog = new AlertDialog.Builder().setMessage("").set.create().sh;
//                        }else {
                        result.setArgs(args);//请求参数
                        dataCall.success(result);
//                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        running = false;
                        // 处理异常
//                        UIUtils.showToastSafe("请求失败");
                        //通过异常工具类封装成自定义的ApiException
                        dataCall.fail(CustomException.handleException(throwable));
                    }
                });

    }

    public void cancelRequest() {
        if (observable!=null){
            observable.unsubscribeOn(Schedulers.io());
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void unBind() {
        dataCall = null;
    }
}
