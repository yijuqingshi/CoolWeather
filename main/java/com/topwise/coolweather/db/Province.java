package com.topwise.coolweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by yangwei on 17-10-3.
 */

public class Province extends DataSupport {

    private int id;
    private String provinceName;
    private int code;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
