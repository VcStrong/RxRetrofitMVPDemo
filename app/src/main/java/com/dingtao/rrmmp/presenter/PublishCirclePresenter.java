package com.dingtao.rrmmp.presenter;

import com.dingtao.common.core.WDPresenter;
import com.dingtao.common.core.DataCall;
import com.dingtao.common.core.http.IAppRequest;
import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author dingtao
 * @date 2018/12/28 11:23
 * qq:1940870847
 */
public class PublishCirclePresenter extends WDPresenter<IAppRequest> {

    public PublishCirclePresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        builder.addFormDataPart("content", (String)args[3]);
        builder.addFormDataPart("commodityId", "1");
        List<Object> list = (List<Object>) args[4];
        if (list.size()>1) {
            for (int i = 1; i < list.size(); i++) {
                File file = new File((String) list.get(i));
                builder.addFormDataPart("image", file.getName(),
                        RequestBody.create(MediaType.parse("multipart/octet-stream"),
                                file));
            }
        }
        return iRequest.releaseCircle((Long) args[0],
                (String)args[1],builder.build());
    }


}
