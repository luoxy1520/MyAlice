package com.example.luoxy.myalice.db;

// 省 PS:类的颜色和版本控制有关

import org.litepal.crud.DataSupport;

public class Province extends DataSupport {

    private int id;

    private String proviceName;

    private int provinceCode;

    public void setId(int id) {
        this.id = id;
    }

    public void setProviceName(String proviceName) {
        this.proviceName = proviceName;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }

    public int getId() {
        return id;
    }

    public String getProvinceName() {
        return proviceName;
    }

    public int getProvinceCode() {
        return provinceCode;
    }


}
