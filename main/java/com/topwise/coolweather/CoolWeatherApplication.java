package com.topwise.coolweather;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePalApplication;

/**
 * Created by yangwei on 17-10-3.
 */

public class CoolWeatherApplication extends Application {

   private static Context mContext;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        LitePalApplication.initialize(this);

    }

    public static Context getContext()
    {
          return mContext;
    }
}
