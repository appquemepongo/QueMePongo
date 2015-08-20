package com.example.quemepongo.data;

import org.json.JSONObject;

/**
 * Created by angel on 19/08/15.
 */
public class Units implements JSONPopulator {
    private String temperature;



    @Override
    public void populete(JSONObject date) {
        setTemperature(date.optString("temperature"));
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
