package com.topwise.coolweather.utils;

/**
 * Created by yangwei on 17-10-3.
 */

public class HttpURL {

    public static final  String PROVINCE_URL = "http://guolin.tech/api/china";
    public static final  String WEATHER_URL = "http://guolin.tech/api/weather?cityid=";
    public static final  String IMAGE_URL= "http://guolin.tech/api/bing_pic";

    public static String getProvinceAllCityUrl(int ProvinceId)
    {
         return  PROVINCE_URL + "/" + ProvinceId;
    }


    public static String getAllCityUrl(int ProvinceId,int CityId)
    {
        return PROVINCE_URL + "/" + ProvinceId + "/" + CityId;
    }


    public static String getWeatherUrl(String wheatherId)
    {

        return  WEATHER_URL+wheatherId+"&key=cc34f83971e843088c67e12eb64a4a92";

    }




}
