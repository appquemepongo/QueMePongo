package com.example.quemepongo.service;

import android.net.Uri;
import android.os.AsyncTask;

import com.example.quemepongo.data.Channel;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by angel on 19/08/15.
 */
public class YahooWeatherService {
    private WeatherServiceCallBack callBack;
    private String location;
    private Exception error;

    public YahooWeatherService(WeatherServiceCallBack callBack){
        this.callBack = callBack;
    }

    public String getLocation() {
        return location;
    }

    public void refreshWeather(final String location){
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String YQL = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\")", location);
                String endpoint = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", Uri.encode(YQL));

                try{
                    URL url = new URL(endpoint);
                    URLConnection connection = url.openConnection();
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine())!=null) {
                        result.append(line);
                    }

                    return result.toString();
                }
                catch (Exception e){
                    error = e;
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                if (s == null && error != null){
                    callBack.ServiceFailture(error);
                    return;
                }

                try{
                    JSONObject data = new JSONObject(s);
                    JSONObject queryResult = data.optJSONObject("query");
                    int count =  queryResult.optInt("count");
                    if (count==0){
                        callBack.ServiceFailture(new LocationWeatherException("No se encontro Localilidad"));
                    }

                    Channel channel = new Channel();
                    channel.populete(queryResult.optJSONObject("results").optJSONObject("channel"));

                    callBack.ServiceSuccess(channel);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    public class LocationWeatherException extends Exception{
        public LocationWeatherException(String detailMessage) {
            super(detailMessage);
        }
    }

}
