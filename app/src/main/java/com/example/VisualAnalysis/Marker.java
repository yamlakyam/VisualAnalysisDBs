package com.example.VisualAnalysis;

import android.content.Context;
import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class Marker extends AsyncTask<GoogleMap,Void,String> {
    Context context;
    public Marker(Context contxt){
        this.context=contxt;
    }


    @Override
    protected String doInBackground(GoogleMap... googleMaps) {
        GoogleMap googleMap = googleMaps[0];

        LatLng loc1 = new LatLng(9.016947, 38.764635);
        LatLng loc2 = new LatLng(9.016677, 38.766920);
        LatLng loc3 = new LatLng(9.016210, 38.770046);
        LatLng loc4 = new LatLng(9.017683, 38.770271);
        LatLng loc5 = new LatLng(9.007278, 38.776499);

        ArrayList<LatLng> locations = new ArrayList<LatLng>();
        locations.add(loc1);
        locations.add(loc2);
        locations.add(loc3);
        locations.add(loc4);
        locations.add(loc5);

        for(int i=0; i<locations.size();i++){

            googleMap.addMarker(new MarkerOptions().position(locations.get(i)).title("location " + locations.indexOf(i)));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(locations.get(i)));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(locations.get(i).latitude, locations.get(i).longitude), 14.0f));
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "null";
    }
}
