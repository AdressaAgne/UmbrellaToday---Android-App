package com.example.orangee.umbrellatoday.service;

import com.example.orangee.umbrellatoday.data.Channel;

/**
 * Created by orangee on 17/09/16.
 */
public interface WeatherServiceCallback {

    void serviceSuccess(Channel channel);

    void serviceFailed(Exception exc);

}
