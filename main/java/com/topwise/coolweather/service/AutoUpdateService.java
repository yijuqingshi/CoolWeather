package com.topwise.coolweather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.IntDef;

import com.topwise.coolweather.gson.Weather;
import com.topwise.coolweather.utils.HttpURL;
import com.topwise.coolweather.utils.HttpUtils;
import com.topwise.coolweather.utils.SharedPreferencesUtils;
import com.topwise.coolweather.utils.Utiloty;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AutoUpdateService extends Service {
    public AutoUpdateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        updateWeather();
        updateImageBg();
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int  time =  8*60*60*1000;
        long AtTime = SystemClock.elapsedRealtime() + time;
        Intent intent1 = new Intent(this,AutoUpdateService.class);
        PendingIntent pi = PendingIntent.getService(this,0,intent1,0);
        alarmManager.cancel(pi);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,AtTime,pi);

        return super.onStartCommand(intent, flags, startId);
    }

    public void updateWeather() {
        String wheatherJson = SharedPreferencesUtils.getValue("weatherJson");
        if (wheatherJson != null && !wheatherJson.equals("")) {
            String weatherId = Utiloty.handleWeatherResponse(wheatherJson).basic.weatherId;
            HttpUtils.getInstance().setOkHttpRequest(HttpURL.getWeatherUrl(weatherId), new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String json = response.body().string();
                    Weather weather = Utiloty.handleWeatherResponse(json);
                    if (weather != null && weather.status.equals("ok")) {
                        SharedPreferencesUtils.putValue("weatherJson", json);
                    }
                }
            });
        }
    }


    public void updateImageBg() {

        HttpUtils.getInstance().setOkHttpRequest(HttpURL.IMAGE_URL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String imageUrl = response.body().string();
                if (!imageUrl.equals("")) {
                    SharedPreferencesUtils.putValue("image_url", imageUrl);
                }
            }
        });


    }


}
