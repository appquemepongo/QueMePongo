package com.example.quemepongo.data;

import org.json.JSONObject;

/**
 * Created by angel on 19/08/15.
 */
public class Channel implements JSONPopulator {
    private Units units;
    private Item items;

    public Units getUnits() {
        return units;
    }

    public Item getItems() {
        return items;
    }

    @Override
    public void populete(JSONObject date) {
        units = new Units();
        units.populete(date.optJSONObject("units"));

        items = new Item();
        items.populete(date.optJSONObject("item"));
    }
}
