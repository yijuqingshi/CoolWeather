package com.topwise.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yangwei on 17-10-4.
 */

public class Basic {

    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String weatherId;

    public Update  update;

    public class Update{

        @SerializedName("loc")
        public String updateTime;

    }

}
