package com.dingtao.rrmmp.presenter;

import com.dingtao.rrmmp.http.IRequest;
import com.dingtao.rrmmp.http.NetworkManager;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author dingtao
 * @date 2018/12/28 11:30
 * qq:1940870847
 */
public abstract class BasePresenter {
    private Consumer consumer;
    public BasePresenter(Consumer consumer){
        this.consumer = consumer;
    }

    protected abstract Observable observable(Object...args);

    public void reqeust(Object...args){
        observable(args).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }
}
