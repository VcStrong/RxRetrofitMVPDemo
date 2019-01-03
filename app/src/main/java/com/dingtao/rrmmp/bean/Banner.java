package com.dingtao.rrmmp.bean;

/**
 * @author dingtao
 * @date 2019/1/2 15:00
 * qq:1940870847
 */
public class Banner {
    String imageUrl;
    String jumpUrl;
    UserInfo rank;

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

    public UserInfo getRank() {
        return rank;
    }

    public void setRank(UserInfo rank) {
        this.rank = rank;
    }
}
