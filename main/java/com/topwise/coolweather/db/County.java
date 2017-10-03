package com.topwise.coolweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by yangwei on 17-10-3.
 */

public class County extends DataSupport {

    private int id ;
    private int cityId;
    private String weatherId;
    private String CountyName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public String getCountyName() {
        return CountyName;
    }

    public void setCountyName(String countyName) {
        CountyName = countyName;
    }
}
