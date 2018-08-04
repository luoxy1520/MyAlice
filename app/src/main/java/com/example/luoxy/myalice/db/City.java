package com.example.luoxy.myalice.db;

// 市

import org.litepal.crud.DataSupport;

public class City extends DataSupport {

    private int id;

    private  String cityNanme;

    private int cityCode;

    private int provinceId;

    public void setId(int id) {
        this.id = id;
    }

    public void setCityNanme(String cityNanme) {
        this.cityNanme = cityNanme;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getId() {
        return id;
    }

    public String getCityNanme() {
        return cityNanme;
    }

    public int getCityCode() {
        return cityCode;
    }

    public int getProvinceId() {
        return provinceId;
    }
}
