package com.dingtao.rrmmp.bean.shop;

import java.util.List;

/**
 * @author dingtao
 * @date 2019/1/3 10:58
 * qq:1940870847
 */
public class HomeList {
    List<CommodityList> rxxp;
    List<CommodityList> mlss;
    List<CommodityList> pzsh;

    public List<CommodityList> getRxxp() {
        return rxxp;
    }

    public void setRxxp(List<CommodityList> rxxp) {
        this.rxxp = rxxp;
    }

    public List<CommodityList> getMlss() {
        return mlss;
    }

    public void setMlss(List<CommodityList> mlss) {
        this.mlss = mlss;
    }

    public List<CommodityList> getPzsh() {
        return pzsh;
    }

    public void setPzsh(List<CommodityList> pzsh) {
        this.pzsh = pzsh;
    }
}
