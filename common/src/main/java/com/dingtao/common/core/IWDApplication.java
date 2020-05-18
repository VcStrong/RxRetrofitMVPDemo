package com.dingtao.common.core;

import android.app.Application;

public interface IWDApplication {
    public static final String[] MODULE_APP= new String[]{
            "com.dingtao.rrmmp.push.core.PushApplication",
            "com.dingtao.rrmmp.im.core.IMApplication"
    };
    void onCreate(Application application);
}