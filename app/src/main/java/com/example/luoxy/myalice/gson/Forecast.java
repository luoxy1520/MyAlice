package com.example.luoxy.myalice.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Forecast
 */

public class Forecast {


    public String date;

    @SerializedName("cond")
    public TheCondition theCondition; // 这里也改了

    @SerializedName("tmp")
    public Temperature temperature;

    public class TheCondition {

        @SerializedName("txt_d")
        public String info;

    }

    public class Temperature {

        public String max;

        public String min;

    }


}
