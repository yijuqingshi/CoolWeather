package com.topwise.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yangwei on 17-10-5.
 */

public class Now {

    @SerializedName("tmp")
    public String temperature;
    public Cond cond;

   public class Cond {

        public String txt;
    }
}
