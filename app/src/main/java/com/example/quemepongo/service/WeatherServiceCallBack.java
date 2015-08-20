package com.example.quemepongo.service;

import com.example.quemepongo.data.Channel;

/**
 * Created by angel on 19/08/15.
 */
public interface WeatherServiceCallBack {
    void ServiceSuccess(Channel chanell);
    void ServiceFailture(Exception e);
}
