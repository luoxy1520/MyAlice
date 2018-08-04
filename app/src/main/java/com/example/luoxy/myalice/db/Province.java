package com.example.luoxy.myalice.db;

// 省

import org.litepal.crud.DataSupport;

public class Province extends DataSupport {

    private int id;

    private String proviceName;

    private String provinceCode;

    public void setId(int id) {
        this.id = id;
    }

    public void setProviceName(String proviceName) {
        this.proviceName = proviceName;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public int getId() {
        return id;
    }

    public String getProvinceName() {
        return proviceName;
    }

    public String getProvinceCode() {
        return provinceCode;
    }


}
