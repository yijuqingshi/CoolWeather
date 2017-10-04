package com.topwise.coolweather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.topwise.coolweather.R;
import com.topwise.coolweather.db.City;
import com.topwise.coolweather.db.County;
import com.topwise.coolweather.db.Province;
import com.topwise.coolweather.utils.HttpURL;
import com.topwise.coolweather.utils.HttpUtils;
import com.topwise.coolweather.utils.Utiloty;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by yangwei on 17-10-3.
 */

public class AreaFragment extends Fragment {


    private ListView mListView;
    private Button mButton;
    private TextView mTextView;
    private List<String> dataList = new ArrayList<>();
    private ArrayAdapter<String> mAdapter;


    private List<Province> mProvinceList;
    private List<City> mCityList;
    private List<County> mCountyList;


    private City mCurrentCity;
    private Province mCurrentProvince;


    public static final int PROVINCE_LEVEL = 0;
    public static final int CITY_LEVEL = 1;
    public static final int COUNTY_LEVEL = 2;
    private int mCurrentLevel = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.select_area, container, false);
        mListView = (ListView) mView.findViewById(R.id.id_lv_info);
        mButton = (Button) mView.findViewById(R.id.id_btn_back);
        mTextView = (TextView) mView.findViewById(R.id.id_tv_title);
        mAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, dataList);
        mListView.setAdapter(mAdapter);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (mCurrentLevel) {
                    case CITY_LEVEL:
                        Log.d("-------------", "CITY_LEVEL: " + mCurrentLevel);
                        loadProvince();
                        break;
                    case COUNTY_LEVEL:
                        Log.d("-------------", "COUNTY_LEVEL: " + mCurrentLevel);
                        loadCity();
                        break;
                }

            }
        });

        return mView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
         mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (mCurrentLevel) {
                    case PROVINCE_LEVEL:
                        mCurrentProvince = mProvinceList.get(i);
                        loadCity();
                        break;
                    case CITY_LEVEL:
                        mCurrentCity = mCityList.get(i);
                        loadCounty();
                        break;
                    case COUNTY_LEVEL:


                        break;


                }
            }
        });
        loadProvince();
    }


    public void loadCounty() {
        mTextView.setText(mCurrentCity.getCityName());
        mButton.setVisibility(View.VISIBLE);
        mCountyList = DataSupport.where("cityId = ?", String.valueOf(mCurrentCity.getCityCode())).find(County.class);
        if (mCountyList.size() > 0) {
            dataList.clear();
            for (int i = 0; i < mCountyList.size(); i++) {
                dataList.add(mCountyList.get(i).getCountyName());
            }
            mAdapter.notifyDataSetChanged();
            mCurrentLevel = COUNTY_LEVEL;
        } else {
            loadDateForServer(HttpURL.getAllCityUrl(mCurrentProvince.getCode(), mCurrentCity.getCityCode()), "county");

        }

    }

    public void loadProvince() {

        mTextView.setText("中国");
        mButton.setVisibility(View.GONE);
        mProvinceList = DataSupport.findAll(Province.class);
        if (mProvinceList.size() > 0) {
            dataList.clear();
            for (int i = 0; i < mProvinceList.size(); i++) {
                dataList.add(mProvinceList.get(i).getProvinceName());
            }
            mAdapter.notifyDataSetChanged();
            mListView.setSelection(0);
            mCurrentLevel = PROVINCE_LEVEL;
        } else {
            loadDateForServer(HttpURL.PROVINCE_URL, "province");
        }

    }

    public void loadCity() {
        mTextView.setText(mCurrentProvince.getProvinceName());
        mButton.setVisibility(View.VISIBLE);
        mCityList = DataSupport.where("ProvinceId = ?", String.valueOf(mCurrentProvince.getCode())).find(City.class);

        if (mCityList.size() > 0) {
            dataList.clear();
            for (int i = 0; i < mCityList.size(); i++) {
                dataList.add(mCityList.get(i).getCityName());
            }
            mAdapter.notifyDataSetChanged();
            mListView.setSelection(0);
            mCurrentLevel = CITY_LEVEL;
        } else {
            loadDateForServer(HttpURL.getProvinceAllCityUrl(mCurrentProvince.getCode()), "city");
        }


    }


    public void loadDateForServer(String address, final String type) {

        HttpUtils.getInstance().setOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                //显示加载进度条

                boolean result = false;
                if (200 == response.code()) {
                    switch (type) {
                        case "province":
                            result = Utiloty.handleProinceResponse(response.body().string());
                            break;
                        case "city":
                            result = Utiloty.handleCityResponse(response.body().string(), mCurrentProvince.getCode());
                            break;
                        case "county":
                            result = Utiloty.handleCountyResponse(response.body().string(), mCurrentCity.
                                    getCityCode());
                            break;
                    }
                    if (result) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                switch (type) {
                                    case "province":
                                        loadProvince();
                                        break;
                                    case "city":
                                        loadCity();
                                        break;
                                    case "county":
                                        loadCounty();
                                        break;
                                }
                            }
                        });
                    }

                }
            }
        });


    }

}
