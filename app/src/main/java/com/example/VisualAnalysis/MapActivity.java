package com.example.VisualAnalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MapActivity extends AppCompatActivity {

    LatLng loc1 = new LatLng(9.016947, 38.764635);
    LatLng loc2 = new LatLng(9.016677, 38.766920);
    LatLng loc3 = new LatLng(9.016210, 38.770046);
    LatLng loc4 = new LatLng(9.017683, 38.770271);
    LatLng loc5 = new LatLng(9.007278, 38.776499);
    //LatLng loc6 = new LatLng(11.512322,37.402954);

    ArrayList<LatLng> locations = new ArrayList<LatLng>();
    Handler handler;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {

            locations.add(loc1);
            locations.add(loc2);
            locations.add(loc3);
            locations.add(loc4);
            locations.add(loc5);


           handler = new Handler() {
                @SuppressLint("HandlerLeak")
                @Override
                public void handleMessage(Message msg) {
                    int message = msg.what;
                    switch (message) {
                        case 1:
                            googleMap.addMarker(new MarkerOptions().position((loc1)));
                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc1));
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(loc1.latitude, loc1.longitude), 14.0f));
                        case 2:
                            googleMap.addMarker(new MarkerOptions().position((loc2)));
                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc2));
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(loc2.latitude, loc2.longitude), 14.0f));
                        case 3:
                            googleMap.addMarker(new MarkerOptions().position((loc3)));
                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc3));
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(loc3.latitude, loc3.longitude), 14.0f));
                        case 4:
                            googleMap.addMarker(new MarkerOptions().position((loc4)));
                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc4));
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(loc4.latitude, loc4.longitude), 14.0f));
                        case 5:
                            googleMap.addMarker(new MarkerOptions().position((loc4)));
                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc4));
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(loc4.latitude, loc4.longitude), 14.0f));

                            break;

                    }


                }
            };



        }



    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);



        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);


        }

    }
};

class MarkerThread extends Thread{

    MapActivity mapActivity=new MapActivity();

    @Override
    public void run() {
        for (LatLng i :mapActivity.locations) {
//                googleMap.addMarker(new MarkerOptions().position(i).title("location " + locations.indexOf(i)));
//                googleMap.moveCamera(CameraUpdateFactory.newLatLng(i));
//                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(i.latitude, i.longitude), 14.0f));
            Message msg = mapActivity.handler.obtainMessage();
            msg.what =(mapActivity.locations.indexOf(i))+1;
            mapActivity.handler.sendMessage(msg);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}