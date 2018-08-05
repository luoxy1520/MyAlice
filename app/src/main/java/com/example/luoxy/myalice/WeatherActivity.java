package com.example.luoxy.myalice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luoxy.myalice.gson.Forecast;
import com.example.luoxy.myalice.gson.Weather;
import com.example.luoxy.myalice.util.HttpUtil;
import com.example.luoxy.myalice.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {

    private Button navButton;

    private TextView titleCity;

    private TextView titleUpdateTime;

    private TextView degreeText;

    private TextView weatherInfoText;

    private ScrollView weatherLayout;

    private LinearLayout forecastLayout;

    private TextView aqiText;

    private TextView pm25Text;

    private TextView comfortText;

    private TextView carWashText;

    private TextView sportText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        navButton = (Button) findViewById(R.id.nav_button);
        titleCity = (TextView) findViewById(R.id.title_city);
        titleUpdateTime = (TextView) findViewById(R.id.title_update_time);
        degreeText = (TextView) findViewById(R.id.degree_text);
        weatherInfoText = (TextView) findViewById(R.id.weather_info_text);
        weatherLayout = (ScrollView) findViewById(R.id.weather_layout);
        forecastLayout = (LinearLayout) findViewById(R.id.forecast_layout);
        aqiText = (TextView) findViewById(R.id.aqi_text);
        pm25Text = (TextView) findViewById(R.id.pm25_text);
        comfortText = (TextView) findViewById(R.id.comfort_text);
        carWashText = (TextView) findViewById(R.id.car_wash_text);
        sportText = (TextView) findViewById(R.id.sport_text);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = prefs.getString("weather", null); // 看有没有已经缓存好了的数据
        // SharedPreferences是Android平台上一个轻量级的存储类，用来保存应用的一些常用配置，
        // 比如Activity状态，Activity暂停时，将此activity的状态保存到SharedPereferences中；
        // 当Activity重载，系统回调方法onSaveInstanceState时，再从SharedPreferences中将值取出。

        if (weatherString != null) {
            Weather weather = Utility.handleWeatherResponse(weatherString);
            showWeatherInfo(weather); // 有数据就直接 show weather information
        } else {
            String weatherId = getIntent().getStringExtra("weather_id"); // 从上一个活动通过 Intent 传来 weatherId, Extra 名称为 weather_id
            weatherLayout.setVisibility(View.INVISIBLE); // 请求时不可见
            requestWeather(weatherId);
        }

    }

    public void requestWeather(final String weatherId) {
        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=ff1ddd67a54f4c60b73c00b5680b484d";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string(); // 要传入的 response 是 String 类型的哦
                final Weather weather = Utility.handleWeatherResponse(responseText); // GSON解析出来 返回一个类

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.status)) {

                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather", responseText);
                            editor.apply();

                            //先创建SharedPreferences.Editor对象接口，参数名分别为“文件名”，“写入操作”
                            //SharedPreferences.Editor editor = getSharedPreferences("lock", MODE_WORLD_WRITEABLE).edit();
                            //editor.putString("key名称"，“你想要保存的数据”);//key名称，任意填写。
                            //edtir.commint();// 提交是一定需要的。
                            //之后就是获取了
                            //读操作
                            //SharedPreferences reder= getSharedPreferences("lock", MODE_WORLD_READABLE);
                            //这里的key就是保存数据时候的key名称这里的第二个参数，如果没有读取到值，那么value就是空;
                            //String value = read.getString("key名称", "");

                            showWeatherInfo(weather); // 展示数据

                        } else {
                            Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

    }

    private void showWeatherInfo(Weather weather) {

        String cityName = weather.basic.cityName;
        String updateTime = weather.basic.update.updateTime.split(" ")[1];
        String degree = weather.now.temperature + "℃";
        String weatherInfo = weather.now.theCondition.info;

        titleCity.setText(cityName);
        titleUpdateTime.setText(updateTime);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        forecastLayout.removeAllViews(); // 先把原来的清空


        for (Forecast forecast : weather.forecastList) { // 根据网页的数组forecastList的个数创建相应个数的forecastList_item

            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);

            TextView dateText = (TextView) view.findViewById(R.id.date_text);
            TextView infoText = (TextView) view.findViewById(R.id.info_text);
            TextView maxText = (TextView) view.findViewById(R.id.max_text);
            TextView minText = (TextView) view.findViewById(R.id.min_text);

            dateText.setText(forecast.date);
            infoText.setText(forecast.theCondition.info);
            maxText.setText(forecast.temperature.max);
            minText.setText(forecast.temperature.min);

            forecastLayout.addView(view); // 一个个加到这个 LinearLayout 里面
        }


        if (weather.aqi != null) {
            aqiText.setText(weather.aqi.city.aqi);
            pm25Text.setText(weather.aqi.city.pm25);
        }

        String comfort = "舒适度：" + weather.suggestion.comfort.info;
        String carWash = "洗车指数：" + weather.suggestion.carWash.info;
        String sport = "运行建议：" + weather.suggestion.sport.info;

        comfortText.setText(comfort);
        carWashText.setText(carWash);
        sportText.setText(sport);

        weatherLayout.setVisibility(View.VISIBLE);
    }
}
