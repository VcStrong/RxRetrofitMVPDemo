package com.dingtao.common.bean;

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
