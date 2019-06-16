package com.dingtao.rrmmp.presenter;

import com.dingtao.common.core.WDPresenter;
import com.dingtao.common.core.DataCall;
import com.dingtao.common.core.http.IAppRequest;

import io.reactivex.Observable;

/**
 * @author dingtao
 * @date 2018/12/28 11:23
 * qq:1940870847
 */
public class GreatPresenter extends WDPresenter<IAppRequest> {

    public GreatPresenter(DataCall consumer) {
        super(consumer);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.addCircleGreat((String)args[0],(String)args[1],(long)args[2]);
    }


}
