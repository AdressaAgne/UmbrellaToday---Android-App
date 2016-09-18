package com.example.orangee.umbrellatoday.data;

import org.json.JSONObject;

/**
 * Created by orangee on 17/09/16.
 */
public class Channel implements JSONpopulator {

    private Item item;
    private Units units;
    private Location location;

    public Item getItem() {
        return item;
    }

    public Units getUnits() {
        return units;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public void populate(JSONObject data) {

        units = new Units();
        units.populate(data.optJSONObject("units"));

        item = new Item();
        item.populate(data.optJSONObject("item"));

        location = new Location();
        location.populate(data.optJSONObject("location"));

    }
}
