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
public class CirclePresenter extends WDPresenter<IAppRequest> {

    private int page=1;

    public int getPage() {
        return page;
    }

    public CirclePresenter(DataCall consumer) {
        super(consumer);
    }

    @Override
    protected Observable getModel(Object... args) {
        boolean refresh = (boolean)args[0];
        if (refresh){
            page = 1;
        }else{
            page++;
        }
        return iRequest.findCircleList((long)args[1],(String)args[2],page,20);
    }


}
