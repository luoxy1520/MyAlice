package com.example.luoxy.myalice.util;

// 解析处理服务器返回的JSON数据

import android.service.carrier.CarrierIdentifier;
import android.text.TextUtils;

import com.example.luoxy.myalice.db.City;
import com.example.luoxy.myalice.db.County;
import com.example.luoxy.myalice.db.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utility {
    public static boolean handleProvinceResponse(String response) {
        if (!TextUtils.isEmpty(response)){
            try{
                JSONArray allProvinces= new JSONArray(response); // 只有当打完捕获异常时，才会显示正确，不然会有红线
                for (int i = 0; i < allProvinces.length(); i++){

                    JSONObject provinceObject = allProvinces.getJSONObject(i);

                    Province province = new Province();
                    province.setProviceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();

                }
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean handleCityResponse(String response ,int provinceId) {
        if (!TextUtils.isEmpty(response)){
            try{
                JSONArray allCities= new JSONArray(response); // 只有当打完捕获异常时，才会显示正确，不然会有红线
                for (int i = 0; i < allCities.length(); i++){

                    JSONObject cityObject = allCities.getJSONObject(i);

                    City city = new City();
                    city.setCityNanme(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();

                }
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean handleCountyResponse(String response ,int CityId) {
        if (!TextUtils.isEmpty(response)){
            try{
                JSONArray allCounties= new JSONArray(response); // 只有当打完捕获异常时，才会显示正确，不然会有红线
                for (int i = 0; i < allCounties.length(); i++){

                    JSONObject countyObject = allCounties.getJSONObject(i);

                    County county = new County();
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.setCityId(CityId);
                    county.save();


                }
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }
    
}
