package com.example.quemepongo.data;

import org.json.JSONObject;

/**
 * Created by angel on 19/08/15.
 */
public class Condition implements JSONPopulator {
    private int code;
    private int temperature;
    private String description;

    public int getCode() {
        return code;
    }

    public int getTemperature() {
        return temperature;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public void populete(JSONObject date) {
        code = date.optInt("code");
        temperature = date.optInt("temp");
        description = date.optString("text");
    }
}
