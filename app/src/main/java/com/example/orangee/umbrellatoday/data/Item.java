package com.example.orangee.umbrellatoday.data;

import org.json.JSONObject;

/**
 * Created by orangee on 17/09/16.
 */
public class Item implements JSONpopulator {

    private Condition condition;

    public Condition getCondition() {
        return condition;
    }

    @Override
    public void populate(JSONObject data) {

        condition = new Condition();
        condition.populate(data.optJSONObject("condition"));


    }
}
