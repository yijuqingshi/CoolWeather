package com.topwise.coolweather.utils;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by yangwei on 17-10-3.
 */

public class HttpUtils {

    private static HttpUtils mHttpUtils;

    private OkHttpClient mOkHttpClient;

    private HttpUtils() {
        mOkHttpClient = new OkHttpClient();
    }


    public static HttpUtils getInstance() {
        if (mHttpUtils == null) {
            synchronized (HttpUtils.class) {
                if (mHttpUtils == null) {
                    mHttpUtils = new HttpUtils();
                }
            }
        }
        return mHttpUtils;
    }

    public void setOkHttpRequest(String address, Callback callback) {

        Request request = new Request.Builder().url(address).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);
    }

}
