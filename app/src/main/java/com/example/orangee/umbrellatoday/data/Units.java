package com.example.orangee.umbrellatoday.data;

import org.json.JSONObject;

/**
 * Created by orangee on 17/09/16.
 */
public class Units implements JSONpopulator {

    private String temp;

    public String getTemp() {
        return temp;
    }

    @Override
    public void populate(JSONObject data) {

        temp = data.optString("temperature");

    }
}
