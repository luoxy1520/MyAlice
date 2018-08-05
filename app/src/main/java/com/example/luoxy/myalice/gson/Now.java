package com.example.luoxy.myalice.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Now
 */

public class Now {

    @SerializedName("tmp")
    public String temperature;

    @SerializedName("cond")
    public TheCondition theCondition; // !!!!!!!!!!!!!!!!!!!! 我改了这里 原来是用 More 的

    public class TheCondition {

        @SerializedName("txt")
        public String info;
    }
}
