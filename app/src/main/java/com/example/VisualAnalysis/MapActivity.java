package com.example.VisualAnalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    public GoogleMap googleMap;
    int width;
    int height;


    int lastIndex;

    LatLng loc1 = new LatLng(9.016947, 38.764635);
    LatLng loc2 = new LatLng(9.016677, 38.766920);
    LatLng loc3 = new LatLng(9.016210, 38.770046);
    LatLng loc4 = new LatLng(9.017683, 38.770271);
    LatLng loc5 = new LatLng(9.007278, 38.776499);
    LatLng loc6 = new LatLng(9.030900, 38.848000);
    LatLng loc7 = new LatLng(9.051921, 38.738136);
    LatLng loc8 = new LatLng(9.005130, 38.696251);
    //LatLng loc6 = new LatLng(11.512322,37.402954);
    public static ArrayList<LatLng> locations = new ArrayList<LatLng>();

    public static Handler handler;

    public static ArrayList<String> place_names = new ArrayList<>();


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

        place_names.add("Branch 1");
        place_names.add("Branch 2");
        place_names.add("Branch 3");
        place_names.add("Branch 4");
        place_names.add("Branch 5");
        place_names.add("Branch 6");
        place_names.add("Branch 7");
        place_names.add("Branch 8");


        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MapActivity.this, MainActivity.class);
                intent.putExtra("Last Index",lastIndex+"");
                Log.i("TAG-mapctivity",""+lastIndex);
                startActivity(intent);
            }
        }, 40000);

    }

    @SuppressLint("HandlerLeak")

    @Override
    public void onMapReady(GoogleMap googleMap) {

//        locations.add(loc1);
//        locations.add(loc2);
//        locations.add(loc3);
//        locations.add(loc4);
//        locations.add(loc5);


        MarkerThread markerThread = new MarkerThread();
        markerThread.start();

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String message = (String) msg.obj;
                int index = Integer.parseInt(message);
                LatLng loc = locations.get(index);

                //googleMap.addMarker(new MarkerOptions().position((loc1)));
                //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(loc1.latitude, loc1.longitude), 14.0f));


                MarkerOptions marker = new MarkerOptions().position(loc);
                // Marker mMarker = googleMap.addMarker(marker.title("location " + index).snippet(loc.toString()));
                Marker mMarker = googleMap.addMarker(marker);
                builder.include(marker.getPosition());
                LatLngBounds bounds = builder.build();
                width = getResources().getDisplayMetrics().widthPixels;
                //height = getResources().getDisplayMetrics().heightPixels;
                int padding = (int) (width * 0.15); // offset from edges of the map 10% of screen

                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                //googleMap.moveCamera(cu);
                googleMap.animateCamera(cu, 1000, null);
//                googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getApplicationContext(),R.raw.style_json));

//                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//                googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//                googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
//                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);




                googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                    @Override
                    public View getInfoWindow(Marker marker) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {

                        View view = getLayoutInflater().inflate(R.layout.custom_pop_up, null);
                        TextView nameTextView = (TextView) view.findViewById(R.id.nameTextView);
                        nameTextView.setText(place_names.get(index));
                        TextView descTextView = (TextView) view.findViewById(R.id.descTextView);
                        descTextView.setText(loc.toString());

                        return view;
                    }
                });
                mMarker.showInfoWindow();
                lastIndex = index;

            }
        };
    }

};

class MarkerThread extends Thread {

    //    MapActivity mapActivity = new MapActivity();
    public MarkerThread() {

    }

    @Override
    public void run() {
        for (int i = 0; i < MapActivity.locations.size(); i++) {
            Log.v("MapIndex", "" + i);
            Message msg = MapActivity.handler.obtainMessage();
            msg.obj = "" + i;
            MapActivity.handler.sendMessage(msg);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}