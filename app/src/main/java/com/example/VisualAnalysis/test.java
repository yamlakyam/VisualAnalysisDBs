package com.example.VisualAnalysis;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.widget.Button;
import android.widget.TextView;

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

import java.util.ArrayList;

import data.TableData;

public class test extends Fragment {
    TextView checkTextView;
    private static final String TAG = "test";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_test, container, false);
        Button button = view.findViewById(R.id.makeReqBtn);
        button.setOnClickListener(makeRequest());

        checkTextView = view.findViewById(R.id.checkTextView);


        return  view;
    }

    private View.OnClickListener makeRequest() {
        return v -> {
           makeRequests(getContext(),"http://192.168.1.234:8001/api/OnlineData/GetDataToDisplayOnTables");
        };
    }

    public void updateTextViewWithData(JSONObject jsonObject) throws JSONException {
        TableData tableData = new TableData();

        JSONArray tableHeadersJson = jsonObject.getJSONArray("tableHeaders");
        JSONArray tableValuesJson = jsonObject.getJSONArray("tableValues");

        ArrayList<String> tableHeaders = new ArrayList<>();
        ArrayList<ArrayList<String>> tableValues = new ArrayList<>();

        for (int i = 0; i < tableHeadersJson.length(); i++) {
            tableHeaders.add(tableHeadersJson.getString(i));
        }


        for (int i = 0; i < tableValuesJson.length(); i++) {
            JSONArray array = new JSONArray(tableValuesJson.get(i).toString());
            ArrayList<String> tableValue = new ArrayList<>();
            for (int j = 0; j < array.length(); j++) {
//                Log.i(TAG, array.get(j).toString());
                tableValue.add(array.get(j).toString());
            }
            tableValues.add(tableValue);
        }

//        Log.i(TAG, tableValues.toString());


//        checkTextView.setText(tableHeaders.get(0) + " " + tableHeaders.get(1) + " " + tableHeaders.get(2));
        String x = "";
        for (int i = 0; i < tableValues.size(); i++) {
            ArrayList<String> column = tableValues.get(i);
            for (int j = 0; j < column.size(); j++) {
                x = x + " " + column.get(j);
            }

            checkTextView.setText(x);

        }
    }


    public JSONObject makeRequests(Context context, String URL){

        final JSONObject RealResponse = new JSONObject();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JSONObject jsonBody = new JSONObject();

        // making the request object using the Volley library utility methods
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, URL, null , new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject responsep) {
                try {

                    // again using the requestNumber to find which parser method to use since every request results in different object result
                    JSONObject myArray = responsep.getJSONObject("data");
                    updateTextViewWithData(myArray);

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
                JSONObject responseArray;
                JSONObject jsonObject = new JSONObject();
                if (response != null) {
                    Log.i(TAG + "second", response.data.toString());
                    try {
                        responseArray = new JSONObject(new String(response.data));
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