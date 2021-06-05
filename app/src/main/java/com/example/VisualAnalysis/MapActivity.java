package com.example.VisualAnalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity {

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {

            LatLng loc1 = new LatLng(9.016947,38.764635);
            LatLng loc2 = new LatLng(9.016677,38.766920);
            LatLng loc3 = new LatLng(9.016210,38.770046);
            LatLng loc4 = new LatLng(9.017683,38.770271);
            LatLng loc5 = new LatLng(9.007278,38.776499);

            ArrayList<LatLng> locations = new ArrayList<LatLng>();
            locations.add(loc1);
            locations.add(loc2);
            locations.add(loc3);
            locations.add(loc4);
            locations.add(loc5);

            for(LatLng i:locations){
                googleMap.addMarker(new MarkerOptions().position(i).title("location "+locations.indexOf(i)));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(i));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(i.latitude,i.longitude),12.0f));
            }


//            LatLng kazanchis = new LatLng(9.015599,38.771664);
//
//            googleMap.addMarker(new MarkerOptions().position(kazanchis).title("Marker in Sydney"));
//            googleMap.moveCamera(CameraUpdateFactory.newLatLng(kazanchis));

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
}