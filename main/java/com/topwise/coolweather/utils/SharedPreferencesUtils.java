package com.topwise.coolweather.utils;

import android.content.SharedPreferences;

import com.topwise.coolweather.CoolWeatherApplication;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by yangwei on 17-10-6.
 */

public class SharedPreferencesUtils {

    public static void putValue(String key, String value) {
        SharedPreferences.Editor editor = CoolWeatherApplication.getContext().getSharedPreferences("weatherString", MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getValue(String key) {
        SharedPreferences sp = CoolWeatherApplication.getContext().getSharedPreferences("weatherString", MODE_PRIVATE);
        return  sp.getString(key,"");
    }

}
