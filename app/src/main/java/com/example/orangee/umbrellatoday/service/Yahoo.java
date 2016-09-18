package com.example.orangee.umbrellatoday.service;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.orangee.umbrellatoday.MainActivity;
import com.example.orangee.umbrellatoday.data.Channel;
import com.example.orangee.umbrellatoday.database.databaseHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

/**
 * Created by orangee on 17/09/16.
 */
public class Yahoo {

    private WeatherServiceCallback callback;

    private String location;

    private Exception error;

    public Yahoo(WeatherServiceCallback callback) {
        this.callback = callback;
    }

    private databaseHelper db;


    public String getLocation() {
        return location;
    }

    public void setLocation(String loc) {
        this.location = loc;
    }

    public void refreshWeather(final String location, Context context){

        db = new databaseHelper(context);

        this.location = location;
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {

                String gps = db.fetchType("gps_cords");
                String YQL;

                String lat = db.fetchType("lat");
                String lng = db.fetchType("lng");


                Log.d("GPS", "gps is: " + gps + ", " + lat + ", " + lng);

                YQL = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\")  and u = 'c'", params[0]);

                if(Objects.equals("1", gps)) {
                    YQL = String.format("select * from weather.forecast where woeid in (SELECT woeid FROM geo.places WHERE text=\"(%s,%s)\") and u = 'c'", lat, lng);
                }

                String endPoint = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", Uri.encode(YQL));

                try {
                    URL url = new URL(endPoint);

                    URLConnection connection = url.openConnection();

                    InputStream stream = connection.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuilder result = new StringBuilder();

                    String line;

                    while((line = reader.readLine()) != null){
                        result.append(line);
                    }

                    return result.toString();


                } catch (Exception e) {
                    error = e;
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {

                if(s == null && error != null){
                    callback.serviceFailed(error);
                    return;
                }

                try {
                    JSONObject data = new JSONObject(s);

                    JSONObject queryResult = data.optJSONObject("query");

                    int c = queryResult.optInt("count");

                    if(c == 0){
                        callback.serviceFailed(new LocWeatherException("No result for "+location));
                        return;
                    }

                    Channel chn = new Channel();
                    chn.populate(queryResult.optJSONObject("results").optJSONObject("channel"));

                    callback.serviceSuccess(chn);

                } catch (JSONException e) {
                    callback.serviceFailed(e);
                }


            }
        }.execute(location);
    }

    public class LocWeatherException extends Exception{
        public LocWeatherException(String message) {
            super(message);
        }
    }
}
