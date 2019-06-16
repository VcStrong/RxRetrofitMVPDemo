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
public class BannerPresenter extends WDPresenter<IAppRequest> {

    public BannerPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.bannerShow();
    }


}
