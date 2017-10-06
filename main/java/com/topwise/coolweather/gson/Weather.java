package com.topwise.coolweather.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yangwei on 17-10-5.
 */

public class Weather {

    public String status;

    public Basic basic;

    public Now now;

    public AQI aqi;

    public Suggestion suggestion;

    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;

    @Override
    public String toString() {
        return "Weather{" +
                "status='" + status + '\'' +
                ", basic=" + basic +
                ", now=" + now +
                ", aqi=" + aqi +
                ", suggestion=" + suggestion +
                ", forecastList=" + forecastList +
                '}';
    }
}
