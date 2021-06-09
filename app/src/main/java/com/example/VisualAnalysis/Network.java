package com.example.VisualAnalysis;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Network {

    private static final String TAG = "Network";

    public static JSONObject makeRequests(Context context , String URL){

        final JSONObject RealResponse = new JSONObject();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JSONObject jsonBody = new JSONObject();

        // making the request object using the Volley library utility methods
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, URL, null , new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject responsep) {
                try {

                    // again using the requestNumber to find which parser method to use since every request results in different object result
                    JSONArray myArray = responsep.getJSONArray("data");
                    Log.i(TAG, myArray.toString());

                    RealResponse.put("data", myArray);
                    Log.i("CHECK", RealResponse.toString());

                }
                catch(JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    , new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("VOLLEY", error.toString());
        }
    }) {

        @Override
        protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
            JSONArray responseArray;
            JSONObject jsonObject = new JSONObject();
            if (response != null) {
                Log.i(TAG + "second", response.data.toString());
                try {
                    responseArray = new JSONArray(new String(response.data));
                    jsonObject.put("data",responseArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return Response.success(jsonObject, HttpHeaderParser.parseCacheHeaders(response));
        }
    };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                1000000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

        Log.i("CHECK", RealResponse.toString());
        return RealResponse;
    }
}

