package com.example.luoxy.myalice.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Basic
 */

public class Basic {

    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String weatherId;

    public Update update;
    public class Update {

        @SerializedName("loc")
        public String updateTime;

    }

}
