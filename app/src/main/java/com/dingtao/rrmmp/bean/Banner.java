package com.dingtao.rrmmp.bean;

import android.content.Context;
import android.net.Uri;

import java.io.File;
import java.math.BigDecimal;
import java.net.URLConnection;

/**
 * @author dingtao
 * @date 2019/1/2 15:00
 * qq:1940870847
 */
public class Banner {
    String imageUrl;
    String jumpUrl;
    int rank;
    String title;

    BigDecimal value;
    Context context;

    public Banner with(Context context){
//        this.context = context;
//        File file = new File("/a/a");
//        file.mkdirs()//创建文件夹
        return this;
    }

    public Banner load(String url){
//        URLConnection urlConnection = new ().u
        //网络请求
        return this;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title; }
}
