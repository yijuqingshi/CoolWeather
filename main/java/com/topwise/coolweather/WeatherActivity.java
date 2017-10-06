package com.topwise.coolweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.topwise.coolweather.gson.Forecast;
import com.topwise.coolweather.gson.Weather;
import com.topwise.coolweather.utils.HttpURL;
import com.topwise.coolweather.utils.HttpUtils;
import com.topwise.coolweather.utils.SharedPreferencesUtils;
import com.topwise.coolweather.utils.Utiloty;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by yangwei on 17-10-5.
 */

public class WeatherActivity extends AppCompatActivity {


    private ScrollView mScrollView;
    private TextView mTitle;
    private TextView mTemperature;
    private TextView mInfo;
    private LinearLayout mForecastLayout;
    private TextView mUpdate;
    private TextView mAqi;
    private TextView mPm25;
    private TextView mComf;
    private TextView mCw;
    private TextView mSpont;

    private String mWeatherJson;
    private String mWeatherId;

    private Weather mWeather;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        initViews();
    }

    private void initViews() {

        mTitle = (TextView) findViewById(R.id.id_title_tv_location);
        mUpdate = (TextView) findViewById(R.id.id_title_tv_update_time);
        mTemperature = (TextView) findViewById(R.id.id_now_tv_tem);
        mInfo = (TextView) findViewById(R.id.id_now_tv_info);
        mAqi = (TextView) findViewById(R.id.id_api_info);
        mPm25 = (TextView) findViewById(R.id.id_api_pm_info);
        mComf = (TextView) findViewById(R.id.id_suggestion_comf);
        mCw = (TextView) findViewById(R.id.id_suggestion_cw);
        mSpont = (TextView) findViewById(R.id.id_suggestion_spont);
        mForecastLayout = (LinearLayout) findViewById(R.id.id_forecast_layout);
        mScrollView = (ScrollView) findViewById(R.id.id_weather_scrollview);
        Intent intent = getIntent();
        mScrollView.setVisibility(View.VISIBLE);
        mWeatherId = intent.getStringExtra("weatherId");
        Log.d("WeatherActivity", "initViews:  + WeatherId = " + mWeatherId);
        mWeatherJson = SharedPreferencesUtils.getValue("weatherJson");
        if (!mWeatherJson.equals("")) {
            mWeather = Utiloty.handleWeatherResponse(mWeatherJson);
            showWeatherInfo(mWeather);
        } else {
            HttpUtils.getInstance().setOkHttpRequest(HttpURL.getWeatherUrl(mWeatherId, "cc34f83971e843088c67e12eb64a4a92"), new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(WeatherActivity.this, "加载天气失败1", Toast.LENGTH_SHORT).show();

                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.code() == 200) {
                        mWeatherJson = response.body().string();
                        mWeather = Utiloty.handleWeatherResponse(mWeatherJson);
                        if (mWeather != null && mWeather.status.equals("ok")) {
                            SharedPreferencesUtils.putValue("weatherJson",mWeatherJson);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showWeatherInfo(mWeather);
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(WeatherActivity.this, "加载天气失败", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }

                    }
                }
            });
        }

    }

    private void showWeatherInfo(Weather weather) {
        if (weather == null)
            return;

        mScrollView.setVisibility(View.VISIBLE);
        mTitle.setText(weather.basic.cityName);
        mUpdate.setText(weather.basic.update.updateTime.split(" ")[1]);
        mAqi.setText(weather.aqi.city.aqi);
        mPm25.setText(weather.aqi.city.pm25);
        mTemperature.setText(weather.now.temperature + "℃");
        mInfo.setText(weather.now.cond.txt);

        for (Forecast forecast : weather.forecastList) {

            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, mForecastLayout, false);
            TextView mDate = (TextView) view.findViewById(R.id.id_forecast_item_data);
            TextView mMax = (TextView) view.findViewById(R.id.id_forecast_item_max);
            TextView mMin = (TextView) view.findViewById(R.id.id_forecast_item_min);
            TextView mInfo = (TextView) view.findViewById(R.id.id_forecast_item_info);
            mDate.setText(forecast.date);
            mMax.setText(forecast.temperature.max);
            mMin.setText(forecast.temperature.min);
            mInfo.setText(forecast.cond.info);
            mForecastLayout.addView(view);
        }
        mComf.setText("舒适度：" + weather.suggestion.comf.info);
        mCw.setText("洗车指数：" + weather.suggestion.comf.info);
        mSpont.setText("运动建议：" + weather.suggestion.comf.info);
    }

}
