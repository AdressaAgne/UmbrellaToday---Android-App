package com.example.orangee.umbrellatoday;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.audiofx.BassBoost;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.orangee.umbrellatoday.database.databaseHelper;

public class SettingsActivity extends AppCompatActivity {


    private Button save;
    private EditText locationText;
    private TextView latlngtext;
    private Button gpsLocBtn;

    private LocationManager lManager;
    private LocationListener lListener;

    private databaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        db = new databaseHelper(this);

        save            = (Button)   findViewById(R.id.savebtn);
        gpsLocBtn       = (Button)   findViewById(R.id.gpsLocBtn);
        locationText    = (EditText) findViewById(R.id.locationtext);
        latlngtext      = (TextView) findViewById(R.id.latlngtext);

        locationText.setText(db.fetchType("location"));

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.updateOrInsert("location", String.valueOf(locationText.getText()));
                db.updateOrInsert("gps_cords", "0");
                startActivity(new Intent(SettingsActivity.this, MainActivity.class));
            }
        });
        gpsLocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configureButton();
            }
        });

        lManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        lListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                String lat = Double.toString(location.getLatitude());
                String lng = Double.toString(location.getLongitude());

                db.updateOrInsert("lat", lat);
                db.updateOrInsert("lng", lng);
                db.updateOrInsert("gps_cords", "1");

                latlngtext.setText(lat + ", " + lng);

                startActivity(new Intent(SettingsActivity.this, MainActivity.class));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }
            @Override
            public void onProviderEnabled(String provider) {

            }
            @Override
            public void onProviderDisabled(String provider) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        };
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    configureButton();
                }
        }
    }

    private void configureButton() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, 10);
            } else {
                lManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, lListener);
            }
        }

    }
}
