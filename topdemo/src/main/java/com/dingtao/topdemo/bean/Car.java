package com.dingtao.topdemo.bean;

/**
 * @author dingtao
 * @date 2019/1/10 10:52
 * qq:1940870847
 */
public class Car {

    private String carId;//汽车ID
    private String carName;//汽车名
    private String brandId;//品牌ID
    private String brand;//品牌

    public Car(String carId, String carName, String brandId, String brand) {
        this.carId = carId;
        this.carName = carName;
        this.brandId = brandId;
        this.brand = brand;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
