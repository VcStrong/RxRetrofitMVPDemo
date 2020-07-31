package com.dingtao.rrmmp.presenter;

import com.dingtao.common.core.WDPresenter;
import com.dingtao.common.core.DataCall;
import com.dingtao.common.core.http.IAppRequest;
import com.dingtao.common.core.http.NetworkManager;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @author dingtao
 * @date 2018/12/28 11:23
 * qq:1940870847
 */
public class RegisterPresenter extends WDPresenter<IAppRequest> {

    public RegisterPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        RequestBody body = NetworkManager.convertJsonBody(new String[]{"phone","pwd"},new String[]{(String)args[0],(String)args[1]});
        return iRequest.register(body);
    }


}
