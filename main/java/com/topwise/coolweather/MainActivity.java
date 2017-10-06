package com.topwise.coolweather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.topwise.coolweather.gson.Weather;
import com.topwise.coolweather.utils.SharedPreferencesUtils;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null)
        {
             getSupportActionBar().hide();
        }
        if (!SharedPreferencesUtils.getValue("weatherJson").equals(""))
        {
            Intent intent = new Intent(this, WeatherActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
