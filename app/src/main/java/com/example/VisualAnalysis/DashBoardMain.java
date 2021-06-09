package com.example.VisualAnalysis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.anastr.speedviewlib.SpeedView;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import data.TableData;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;


public class DashBoardMain extends Fragment {

    public static Handler handler;
    public static Handler piehandler;
    public static Handler barhandler;


    public static ArrayList<Float> speedviewData = new ArrayList<>(Arrays.asList(12.0f, 56.5f, 23.7f, 49.9f, 75.0f, 10f));

   public static ArrayList<PieEntry> piedatas = new ArrayList<>();
   public TableView<String[]> tableView;

//    static String[] tableHeaders = {"Rank", "Subcity", "Sales"};
//    static String[][] tableValues = {
//            {"1", "Kirkos", "22K"},
//            {"2", "Nifas Silk", "20K"},
//            {"3", "Bole", "35K"},
//            {"4", "Lideta", "45K"},
//    };
    PieChart pieChart;
    BarChart barChart;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboardmain, container, false);
        // Inflate the layout for this fragment

        barChart = (BarChart) view.findViewById(R.id.barchart);
        pieChart = (PieChart) view.findViewById(R.id.piechart);


        // pieChart.getRadius();
        //Toast.makeText(this, (int) pieChart.getRadius(),Toast.LENGTH_LONG).show();


        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);

        CircleDisplay circleDisplay = (CircleDisplay) view.findViewById(R.id.circleDisplay);
        circleDisplay.setColor(Color.parseColor("#d68894"));
        circleDisplay.setAnimDuration(3000);
        circleDisplay.setStepSize(1f);
        circleDisplay.showValue(75f, 100f, true);
        circleDisplay.setTouchEnabled(true);


        GaugeThread gaugeThread = new GaugeThread();
        gaugeThread.start();

        SpeedView speedView = (SpeedView) view.findViewById(R.id.gauge);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String message = (String) msg.obj;
                int index = Integer.parseInt(message);
                Float ptr = speedviewData.get(index);
                speedView.speedTo(ptr);

            }
        };
        speedView.speedTo(50, 4000);
        //speedView.setTrembleData(5,2);
        speedView.setWithTremble(false);
        speedView.setUnit("%");

        Handler h = new Handler();

        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                NavHostFragment.findNavController(DashBoardMain.this).navigate(R.id.action_dashBoardMain_to_dashBoardFragment2);
            }
        }, 30000);



        tableView = (TableView<String []>) view.findViewById(R.id.table_data_view);
        makeRequests(getContext(),"http://192.168.1.234:8001/api/OnlineData/GetDataToDisplayOnTables","1");
        makeRequests(getContext(),"http://192.168.1.234:8001/api/OnlineData/GetDataToDisplayOnPieChart","2");
        makeRequests(getContext(),"http://192.168.1.234:8001/api/OnlineData/GetDataToDisplayOnBarChart","3");

        return view;
    }


    public void updateTableWithData(JSONObject jsonObject) throws JSONException {
        TableData tableData = new TableData();

        JSONArray tableHeadersJson = jsonObject.getJSONArray("tableHeaders");
        JSONArray tableValuesJson = jsonObject.getJSONArray("tableValues");

        ArrayList<String> tableHeaders = new ArrayList<>();
        ArrayList<ArrayList<String>> tableValues = new ArrayList<>();

        String[][] valuesArray = new String[tableValuesJson.length()][];

        for (int i = 0; i < tableHeadersJson.length(); i++) {
            tableHeaders.add(tableHeadersJson.getString(i));
        }


        for (int i = 0; i < tableValuesJson.length(); i++) {
            JSONArray array = new JSONArray(tableValuesJson.get(i).toString());
            ArrayList<String> tableValue = new ArrayList<>();
            valuesArray[i] = new String[array.length()];

            for (int j = 0; j < array.length(); j++) {
//                Log.i(TAG, array.get(j).toString());
                tableValue.add(array.get(j).toString());
                valuesArray[i][j] = array.get(j).toString();
            }
            tableValues.add(tableValue);
        }

        tableView.setColumnCount(3);

//        tableHeaders.toArray(new String[0])
        SimpleTableHeaderAdapter simpleTableHeaderAdapter = new SimpleTableHeaderAdapter(getContext(), tableHeaders.toArray(new String[0]) );
        simpleTableHeaderAdapter.setTextColor(Color.parseColor("#d9f5ff"));


        // set header
        tableView.setHeaderAdapter(simpleTableHeaderAdapter);
        tableView.setHeaderBackgroundColor(Color.parseColor("#212c5d"));

        // set the data
        tableView.setBackgroundColor(Color.parseColor("#d9f5ff"));
        tableView.setDataAdapter(new SimpleTableDataAdapter(getContext(), valuesArray));

    }

    public void updatePieChartWithData(JSONObject jsonObject) throws JSONException {

        JSONArray jsonLabels = jsonObject.getJSONArray("labels");
        JSONArray jsonValues = jsonObject.getJSONArray("values");

        ArrayList<String> pieLabel = new ArrayList<>();
        ArrayList<Float> pieValue = new ArrayList<>();

        Log.i("TAG", jsonObject.get("labels").toString());
        Log.i("TAG", jsonObject.get("values").toString());


        for (int i = 0; i < jsonLabels.length(); i++) {
            pieLabel.add((String) jsonLabels.get(i));
            pieValue.add(Float.parseFloat(jsonValues.get(i).toString()));
        }

        Log.i("TAG", pieLabel.toString());
        Log.i("TAG", pieValue.toString());

        for (int i = 0; i < pieLabel.size(); i++) {
            piedatas.add(new PieEntry(pieValue.get(i), pieLabel.get(i)));
        }

//        piedatas.add(new PieEntry((float) 690.8, "Poland"));
//        piedatas.add(new PieEntry((float) 84.4, "United States"));
//        piedatas.add(new PieEntry((float) 71.2, "India"));
//        piedatas.add(new PieEntry((float) 437.7, "Others"));

        PieDataSet pieDataSet = new PieDataSet(piedatas, "Piedata label");
        pieDataSet.setColors(
                Color.parseColor("#e73a55"),
                Color.parseColor("#110f49"),
                Color.parseColor("#f8f8fa"),
                Color.parseColor("#b848fa"),
                Color.parseColor("#f8dc5a"),
                Color.parseColor("#d9f5ff"),
                Color.parseColor("#115849")
        );
        pieDataSet.setDrawValues(false);


        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.setDrawSliceText(false);
        //pieChart.setHoleRadius(0);
        pieChart.setDrawHoleEnabled(false);
        //pieChart.setOutlineSpotShadowColor(Color.parseColor(""));
        pieChart.spin(5000, 90f, 360f, Easing.EaseInOutQuad);
        pieChart.getDescription().setEnabled(false);


    }

    public void updateBarChartWithData(JSONObject jsonObjectp) throws JSONException{


        JSONArray jsonArray = jsonObjectp.getJSONArray("values");
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        initBarChart(jsonObject);

        barhandler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                String message = (String) msg.obj;
                int index = Integer.parseInt(message);
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(index);
                    initBarChart(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };



        BarThread barThread = new BarThread(jsonArray.length());
        barThread.start();

    }

    public void initBarChart(JSONObject jsonObject) throws JSONException{
        JSONArray labels = jsonObject.getJSONArray("labels");
        JSONArray xValues = jsonObject.getJSONArray("xValues");
        JSONArray yValues = jsonObject.getJSONArray("yValues");

        Log.i("TAG",labels.toString());
        Log.i("TAG",xValues.toString());
        Log.i("TAG",yValues.toString());


        ArrayList<String> xAxisLabels = new ArrayList<>();
        for (int i = 0; i < labels.length(); i++) {
            xAxisLabels.add(labels.get(i).toString());
        }

        ArrayList<BarEntry> dataVals = new ArrayList<>();

        for (int i = 0; i < xValues.length(); i++) {
            dataVals.add(new BarEntry((int)(Double.parseDouble(xValues.get(i).toString()))-1, (int)Double.parseDouble(yValues.get(i).toString())));
        }

        BarDataSett barDataSet = new BarDataSett(dataVals, "dataset1");

        barDataSet.setDrawValues(false);
        for (int i = 0; i < dataVals.size(); i++) {
            if (dataVals.get(i).getY() < 0) {
                barDataSet.setColors(Color.parseColor("#d9f5ff"));
            } else if (dataVals.get(i).getY() > 0) {
                barDataSet.setColors(Color.parseColor("#212c5d"));
            }

        }//this code sets all the dataset color based on the last condition.//has to be fixed

        //barChart.getAxisLeft().setDrawGridLines(false);
        barChart.setDrawGridBackground(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisRight().setDrawGridLines(false);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(dataVals.size());//if it doesn't show all the labels, use this

        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawAxisLine(false);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getAxisRight().setDrawAxisLine(false);


        //baChart.setDrawGridBackground(false);
        barDataSet.setBarBorderWidth(1f);


        BarData barData = new BarData();
        barData.addDataSet(barDataSet);
        barChart.setData(barData);
        barChart.invalidate();
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabels));
        barChart.getXAxis().setLabelRotationAngle(-15);
        barChart.animateXY(1000, 1000);
        barChart.getAxisRight().setDrawLabels(false);
        barChart.getDescription().setEnabled(false);


    }

    public JSONObject makeRequests(Context context, String URL, String reqNo){

        final JSONObject RealResponse = new JSONObject();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JSONObject jsonBody = new JSONObject();

        // making the request object using the Volley library utility methods
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, URL, null , new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject responsep) {
                try {

                    // again using the requestNumber to find which parser method to use since every request results in different object result

                    JSONObject myObject = responsep.getJSONObject("data");
                    switch (reqNo) {
                        case "1":
                            updateTableWithData(myObject);
                            break;
                        case "2":
                            updatePieChartWithData(myObject);
                            break;
                        case "3":
                            updateBarChartWithData(myObject);
                    }
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
                    Log.i("TAG" + "second", response.data.toString());
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

class GaugeThread extends Thread {
    @Override
    public void run() {

        for (int i = 0; i < DashBoardMain.speedviewData.size(); i++) {
            Message msg = DashBoardMain.handler.obtainMessage();
            msg.obj = String.valueOf(i);
            DashBoardMain.handler.sendMessage(msg);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}

class PieThread extends Thread {
    int piedatas;
    public PieThread(int piedatas) {
        this.piedatas = piedatas;
    }

    @Override
    public void run() {

        for (int i = 0; i < piedatas; i++) {
            Message msg = DashBoardMain.piehandler.obtainMessage();
            msg.obj = String.valueOf(i);
            DashBoardMain.piehandler.sendMessage(msg);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}

class BarThread extends Thread {
    int bardatas;

    public BarThread(int bardatas) {
        this.bardatas = bardatas;
    }

    @Override
    public void run() {

        for (int i = 0; i < bardatas; i++) {
            Message msg = DashBoardMain.barhandler.obtainMessage();
            msg.obj = String.valueOf(i);
            DashBoardMain.barhandler.sendMessage(msg);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}
//class TableThread extends Thread {
//    int tabledatas;
//
//    public TableThread(int tabledatas) {
//        this.tabledatas = tabledatas;
//    }
//
//    @Override
//    public void run() {
//
//        for (int i = 0; i < tabledatas; i++) {
//            Message msg = DashBoardMain.piehandler.obtainMessage();
//            msg.obj = String.valueOf(i);
//            DashBoardMain.piehandler.sendMessage(msg);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//    }
//}



