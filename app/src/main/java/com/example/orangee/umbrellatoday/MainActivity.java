package com.example.orangee.umbrellatoday;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.orangee.umbrellatoday.data.Channel;
import com.example.orangee.umbrellatoday.data.Item;
import com.example.orangee.umbrellatoday.database.databaseHelper;
import com.example.orangee.umbrellatoday.service.WeatherServiceCallback;
import com.example.orangee.umbrellatoday.service.Yahoo;

public class MainActivity extends AppCompatActivity implements WeatherServiceCallback {

    private TextView temp;
    private TextView cond;
    private TextView loc;
    private ImageView icon;

    private Button settingsBtn;
    private Button refreshBtn;
    private databaseHelper db;

    private Yahoo service;
    private ProgressDialog dialog;

    private String weatherlocation;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new databaseHelper(this);

        context = this;

        temp = (TextView)  findViewById(R.id.temprature);
        cond = (TextView)  findViewById(R.id.condition);
        loc  = (TextView)  findViewById(R.id.location);
        icon = (ImageView) findViewById(R.id.WeatherIcon);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading Current Weather!");
        dialog.show();

        service = new Yahoo(this);
        weatherlocation = db.fetchType("location");

        if(weatherlocation == null){
            weatherlocation = "oslo";
            db.updateOrInsert("location", weatherlocation);
            db.updateOrInsert("gps_cords", "0");
        }

        service.refreshWeather(weatherlocation, context);

        settingsBtn = (Button) findViewById(R.id.savebtn);
        refreshBtn = (Button) findViewById(R.id.refreshWeather);

        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));

            }
        });

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                service.refreshWeather(weatherlocation, context);
            }
        });

    }


    @Override
    public void serviceSuccess(Channel chn) {
        dialog.hide();
        dialog.dismiss();

        Item item = chn.getItem();

        int rssId = getResources().getIdentifier("drawable/i" + item.getCondition().getCode(), null, getPackageName());


        Drawable iconDrawable = getResources().getDrawable(rssId);
        icon.setImageDrawable(iconDrawable);

        loc.setText(chn.getLocation().getCity() + ", " + chn.getLocation().getCountry());
        // unicode for degrees u00B0
        temp.setText(String.format("%s \u00B0%s", item.getCondition().getTemp(), chn.getUnits().getTemp()));
        cond.setText(item.getCondition().getDesc());

    }

    @Override
    public void serviceFailed(Exception exc) {
        dialog.hide();
        dialog.dismiss();
    }
}
