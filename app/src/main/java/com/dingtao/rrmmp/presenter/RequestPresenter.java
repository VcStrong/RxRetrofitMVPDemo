package com.dingtao.rrmmp.presenter;

import com.dingtao.rrmmp.http.IRequest;
import com.dingtao.rrmmp.http.NetworkManager;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * @author dingtao
 * @date 2018/12/28 11:23
 * qq:1940870847
 */
public class RequestPresenter extends BasePresenter {

    public RequestPresenter(Consumer consumer) {
        super(consumer);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetworkManager.instance().create(IRequest.class);
        return iRequest.register((String)args[0],(String)args[1]);
    }


}
