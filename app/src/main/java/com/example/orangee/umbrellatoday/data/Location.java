package com.example.orangee.umbrellatoday.data;

import org.json.JSONObject;

/**
 * Created by orangee on 17/09/16.
 */
public class Location implements JSONpopulator {

    private String city;
    private String country;

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public void populate(JSONObject data) {

        city = data.optString("city");
        country = data.optString("country");

    }
}
