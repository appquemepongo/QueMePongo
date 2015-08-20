package com.example.quemepongo.data;

import org.json.JSONObject;

/**
 * Created by angel on 19/08/15.
 */
public class Item implements JSONPopulator {
    private Condition condition;

    public Condition getCondition() {
        return condition;
    }

    @Override
    public void populete(JSONObject date) {
        condition = new Condition();
        condition.populete(date.optJSONObject("condition"));
    }
}
