package com.example.VisualAnalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

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

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    public GoogleMap googleMap;

    LatLng loc1 = new LatLng(9.016947, 38.764635);
    LatLng loc2 = new LatLng(9.016677, 38.766920);
    LatLng loc3 = new LatLng(9.016210, 38.770046);
    LatLng loc4 = new LatLng(9.017683, 38.770271);
    LatLng loc5 = new LatLng(9.007278, 38.776499);
    LatLng loc6 = new LatLng(9.030900,38.848000);
    LatLng loc7 = new LatLng(9.051921,38.738136);
    LatLng loc8 = new LatLng(9.005130,38.696251);
    //LatLng loc6 = new LatLng(11.512322,37.402954);
    public static ArrayList<LatLng> locations = new ArrayList<LatLng>();

    public static Handler handler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this::onMapReady);

        }

        locations.add(loc1);
        locations.add(loc2);
        locations.add(loc3);
        locations.add(loc4);
        locations.add(loc5);
        locations.add(loc6);
        locations.add(loc7);
        locations.add(loc8);


        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent =new Intent(MapActivity.this, MainActivity.class);
                startActivity(intent);
            }
        },30000);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

//        locations.add(loc1);
//        locations.add(loc2);
//        locations.add(loc3);
//        locations.add(loc4);
//        locations.add(loc5);
        MarkerThread markerThread = new MarkerThread();
        markerThread.start();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String message = (String) msg.obj;
                int index = Integer.parseInt(message);
                LatLng loc1 = locations.get(index);
                googleMap.addMarker(new MarkerOptions().position((loc1)));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc1));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(loc1.latitude, loc1.longitude), 14.0f));

            }
        };

    }



};

class MarkerThread extends Thread {

//    MapActivity mapActivity = new MapActivity();
    public MarkerThread(){

    }
    @Override
    public void run() {
        for (int i=0; i<MapActivity.locations.size();i++) {
            Log.v("MapIndex",""+i);
            Message msg = MapActivity.handler.obtainMessage();
            msg.obj = ""+i;
            MapActivity.handler.sendMessage(msg);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}