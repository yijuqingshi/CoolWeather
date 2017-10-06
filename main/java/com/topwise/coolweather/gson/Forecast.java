package com.topwise.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yangwei on 17-10-5.
 */

public class Forecast {

    public String date;

    @SerializedName("tmp")
    public Temperature temperature;
    public Cond  cond;

    public class Temperature {
        public String max;
        public String min;
    }

    public class Cond {
        @SerializedName("txt_d")
        public String info;
    }

}
