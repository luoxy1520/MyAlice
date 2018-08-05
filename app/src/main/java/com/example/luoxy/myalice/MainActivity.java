package com.example.luoxy.myalice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        if (preferences .getString("weather", null) != null) { // 名称 weather 与 WeatherActivity 中 editor 的 weather 一致
            Intent intent = new Intent(this, WeatherActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
