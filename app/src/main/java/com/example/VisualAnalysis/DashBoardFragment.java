package com.example.VisualAnalysis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
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
import android.widget.ProgressBar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BubbleChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.BubbleDataSet;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;


public class DashBoardFragment extends Fragment {

    LineChart lineChart;
    LineChart lineChart2;
    BarChart groupBarChart;
    BubbleChart bubbleChart;
    HorizontalBarChart horizontalBarChart;
    ProgressBar progressBar1;
    ProgressBar progressBar2;
    PieChart donutPieChart;


    public static Handler lineChartHandler;
    public static Handler horizontalBarchartHandler;
    public static Handler groupLineChartHandler;
    public static Handler groupBarChartHandler;

    @Override
    public void onResume() {
        super.onResume();
        if(groupBarChartHandler == null) {
            makeRequests(getContext(), "http://192.168.1.248:8001/api/OnlineData/GetDataToDisplayOnDoublyBarChart", "4");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_dash_board2, container, false);
        lineChart = (LineChart) rootView.findViewById(R.id.linechart);
        lineChart2 = (LineChart) rootView.findViewById(R.id.linechart2);
        groupBarChart = (BarChart) rootView.findViewById(R.id.barchart2);
        horizontalBarChart = (HorizontalBarChart) rootView.findViewById(R.id.horizontalbC);
        bubbleChart = (BubbleChart) rootView.findViewById(R.id.bubblechart);
        progressBar1 = (ProgressBar) rootView.findViewById(R.id.progressBar1);
        progressBar2 = (ProgressBar) rootView.findViewById(R.id.progressBar2);
        donutPieChart = (PieChart) rootView.findViewById(R.id.piechartOS);


        initBubbleChart();
        initProgressBars();
        initDonutChart();

        makeRequests(getContext(), "http://192.168.1.248:8001/api/OnlineData/GetDataToDisplayOnLineChart", "1");
        makeRequests(getContext(), "http://192.168.1.248:8001/api/OnlineData/GetDataToDisplayOnDoublyLineChart", "2");
        makeRequests(getContext(), "http://192.168.1.248:8001/api/OnlineData/GetDataToDisplayOnHorizontalBarChart", "3");
        makeRequests(getContext(), "http://192.168.1.248:8001/api/OnlineData/GetDataToDisplayOnDoublyBarChart", "4");


        Handler h = new Handler();

       h.postDelayed(new Runnable() {
            @Override
            public void run() {
                NavHostFragment.findNavController(DashBoardFragment.this).navigate(R.id.action_dashBoardFragment_to_mapActivity3);
            }
        },40000);

        return rootView;

    }

    @SuppressLint("HandlerLeak")
    private void updateLineChartOneWithData(JSONObject jsonObjectp) throws JSONException {
        JSONArray jsonArray = jsonObjectp.getJSONArray("values");
        JSONObject jsonObject = jsonArray.getJSONObject(0);

//       Log.i("TAG",jsonObject.toString());
        initLineChart1(jsonObject);

        lineChartHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                String message = (String) msg.obj;
                int index = Integer.parseInt(message);
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(index);
                    initLineChart1(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        LineChartThread lineChartThread = new LineChartThread(jsonArray.length());
        lineChartThread.start();
    }

    @SuppressLint("HandlerLeak")
    private void updateLineChartTwoWithData(JSONObject jsonObjectp) throws JSONException {
        JSONArray jsonArray = jsonObjectp.getJSONArray("doubleLineValues");

        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
        JSONArray jsonArray1 = jsonObject1.getJSONArray("values");
        JSONObject mainJsonObj1 = jsonArray1.getJSONObject(0);

        JSONObject jsonObject2 = jsonArray.getJSONObject(1);
        JSONArray jsonArray2 = jsonObject2.getJSONArray("values");
        JSONObject mainJsonObj2 = jsonArray2.getJSONObject(0);


        Log.i("LINECHART1", mainJsonObj1.toString());
        Log.i("LINECHART2", mainJsonObj2.toString());

        initLineChart2(mainJsonObj1, mainJsonObj2);

        groupLineChartHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                String message = (String) msg.obj;
                int index = Integer.parseInt(message);
                try {
                    JSONObject jsonObject = jsonArray1.getJSONObject(index);
                    JSONObject jsonObject2 = jsonArray2.getJSONObject(index);
                    initLineChart2(jsonObject,jsonObject2);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        GroupLineThread groupLineChartThread = new GroupLineThread(jsonArray.length());
        groupLineChartThread.start();
    }

    @SuppressLint("HandlerLeak")
    private void updateHorizontalBarChartOneWithData(JSONObject jsonObjectp) throws JSONException {
        JSONArray jsonArray = jsonObjectp.getJSONArray("values");
        JSONObject jsonObject = jsonArray.getJSONObject(0);

        initHorizontalBarChart(jsonObject);

        horizontalBarchartHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                String message = (String) msg.obj;
                int index = Integer.parseInt(message);
                try {

                    JSONObject jsonObject = jsonArray.getJSONObject(index);
                    initHorizontalBarChart(jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        HorizontalBarThread horizontalBarThread = new HorizontalBarThread(jsonArray.length());
        horizontalBarThread.start();
    }

    @SuppressLint("HandlerLeak")
    private void updateGroupBarChartWithData(JSONObject jsonObjectp) throws JSONException {

        JSONArray jsonArray = jsonObjectp.getJSONArray("doubleBarValues");

        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
        JSONArray jsonArray1 = jsonObject1.getJSONArray("values");
        JSONObject mainJsonObj1 = jsonArray1.getJSONObject(0);

        JSONObject jsonObject2 = jsonArray.getJSONObject(1);
        JSONArray jsonArray2 = jsonObject2.getJSONArray("values");
        JSONObject mainJsonObj2 = jsonArray2.getJSONObject(0);


        Log.i("BARCHART1", mainJsonObj1.toString());
        Log.i("BARCHART2", mainJsonObj2.toString());


        initGroupBarChart(mainJsonObj1, mainJsonObj2);

        groupBarChartHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                String message = (String) msg.obj;
                int index = Integer.parseInt(message);
                try {

                    JSONObject jsonObject = jsonArray1.getJSONObject(index);
                    JSONObject jsonObject2 = jsonArray2.getJSONObject(index);

                    initGroupBarChart(jsonObject, jsonObject2);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        GroupBarChartThread groupBarChartThread = new GroupBarChartThread(jsonArray1.length());
        groupBarChartThread.start();

    }


    private void initLineChart1(JSONObject jsonObject) throws JSONException {
        ArrayList<Entry> dataVals = new ArrayList<Entry>();

        JSONArray xValues = jsonObject.getJSONArray("xValues");
        JSONArray yValues = jsonObject.getJSONArray("yValues");
        JSONArray labels = jsonObject.getJSONArray("labels");

        for (int i = 0; i < xValues.length(); i++) {
            dataVals.add(new Entry(Float.parseFloat(xValues.get(i).toString()), Float.parseFloat(yValues.get(i).toString())));
        }

//        dataVals.add(new Entry(0, 2));
//        dataVals.add(new Entry(1, 3));
//        dataVals.add(new Entry(2, 1.5f));
//        dataVals.add(new Entry(3, 2));
//        dataVals.add(new Entry(4, 3.5f));
//        dataVals.add(new Entry(5, 1.5f));
//        dataVals.add(new Entry(6, 3.4f));

        LineDataSet lineDataSet = new LineDataSet(dataVals, "active users");
        LineData lineData = new LineData();
        lineData.addDataSet(lineDataSet);
        lineChart.setData(lineData);

        lineChart.getAxisLeft().setDrawLabels(true);
        lineChart.getAxisRight().setDrawGridLines(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        //lineChart.getAxisLeft().setEnabled(false);
        lineChart.getAxisRight().setDrawAxisLine(false);
        lineChart.getAxisRight().setDrawLabels(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.getXAxis().setDrawAxisLine(false);
        lineChart.getXAxis().setDrawGridLines(false);
//        lineChart.getXAxis().enableAxisLineDashedLine(10f,10f,0);
//        lineChart.getAxisRight().enableAxisLineDashedLine(10f,10f,0);
//        lineChart.getAxisLeft().enableAxisLineDashedLine(10f,10f,0);

        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.setDrawGridBackground(false);
        lineChart.getAxisLeft().setDrawGridLines(false);


        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setDrawValues(false);
        lineDataSet.setColors(Color.parseColor("#5b79e7"));
        lineDataSet.setDrawCircles(false);
//        lineDataSet.enableDashedLine(10f,10f,0);

        String[] labelValues = new String[labels.length()];

        for (int i = 0; i < labels.length(); i++) {
            labelValues[i] = labels.getString(i);
        }

        ArrayList<String> xAxisVals = new ArrayList<>(Arrays.asList(labelValues));
        ArrayList<String> yAxisVals = new ArrayList<>(Arrays.asList("0", "10k", "20k", "30k", "40k", "50k", "60k"));
        lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisVals));
        //lineChart.getAxisLeft().setValueFormatter(new IndexAxisValueFormatter(yAxisVals));
        lineChart.getAxisLeft().setLabelCount(yAxisVals.size());
        lineChart.setExtraBottomOffset(15f);
        lineChart.setExtraTopOffset(15f);
        lineChart.animateX(500, Easing.EaseInCubic);
    }

    private void initLineChart2(JSONObject jsonObject1, JSONObject jsonObject2) throws JSONException {

        JSONArray xValues1 = jsonObject1.getJSONArray("xValues");
        JSONArray yValues1 = jsonObject1.getJSONArray("yValues");
        JSONArray labels1 = jsonObject1.getJSONArray("labels");

        JSONArray xValues2 = jsonObject2.getJSONArray("xValues");
        JSONArray yValues2 = jsonObject2.getJSONArray("yValues");
        JSONArray labels2 = jsonObject2.getJSONArray("labels");


        ArrayList<Entry> dataVal1 = new ArrayList<Entry>();
        ArrayList<Entry> dataVal2 = new ArrayList<Entry>();


        for (int i = 0; i < xValues1.length(); i++) {
            dataVal1.add(new Entry(Float.parseFloat(xValues1.get(i).toString()), Float.parseFloat(yValues1.get(i).toString())));
            dataVal2.add(new Entry(Float.parseFloat(xValues2.get(i).toString()), Float.parseFloat(yValues2.get(i).toString())));
        }

//
//        dataVal1.add(new Entry(0, 10f));
//        dataVal1.add(new Entry(1, 2));
//        dataVal1.add(new Entry(2, 10f));
//        dataVal1.add(new Entry(3, 6f));
//        dataVal1.add(new Entry(4, 15f));
//        dataVal1.add(new Entry(5, 4f));
//        dataVal1.add(new Entry(6, 8f));

//        dataVal2.add(new Entry(0, 5f));
//        dataVal2.add(new Entry(1, 18f));
//        dataVal2.add(new Entry(2, 11f));
//        dataVal2.add(new Entry(3, 14f));
//        dataVal2.add(new Entry(4, 4f));
//        dataVal2.add(new Entry(5, 16f));
//        dataVal2.add(new Entry(6, 18f));

        LineDataSet lineDataSet1 = new LineDataSet(dataVal1, "Customers");
        LineDataSet lineDataSet2 = new LineDataSet(dataVal2, "Visitors");

        lineDataSet1.setColors(Color.parseColor("#27adb9"));
        lineDataSet2.setColors(Color.parseColor("#5473e8"));
        lineDataSet1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet2.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet1.setDrawCircles(false);
        lineDataSet2.setDrawCircles(false);
        lineDataSet1.setDrawValues(false);
        lineDataSet2.setDrawValues(false);
        lineDataSet1.setForm(Legend.LegendForm.CIRCLE);
        lineDataSet2.setForm(Legend.LegendForm.CIRCLE);


        LineData lineData2 = new LineData();
        lineData2.addDataSet(lineDataSet1);
        lineData2.addDataSet(lineDataSet2);
        lineChart2.setData(lineData2);
        lineChart2.setDrawGridBackground(false);
        lineChart2.getAxisRight().setDrawGridLines(false);
        lineChart2.getAxisLeft().setDrawGridLines(false);
        lineChart2.getAxisRight().setDrawAxisLine(false);
        lineChart2.getAxisRight().setDrawLabels(false);
        lineChart2.getDescription().setEnabled(false);
        lineChart2.getXAxis().setDrawAxisLine(false);
        lineChart2.getXAxis().setDrawGridLines(false);
        lineChart2.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        String[] labels = new String[labels1.length()];
        for (int i = 0; i < labels1.length(); i++) {
            labels[i] = labels1.getString(i);
        }

        ArrayList<String> chart2xAxis = new ArrayList<String>(Arrays.asList(labels));
        lineChart2.getXAxis().setValueFormatter(new IndexAxisValueFormatter(chart2xAxis));
        lineChart2.setExtraBottomOffset(15f);
        lineChart.getXAxis().setTextColor(Color.parseColor("#708099"));
        lineChart2.getXAxis().setTextColor(Color.parseColor("#708099"));
        lineChart.getAxisLeft().setTextColor(Color.parseColor("#708099"));
        lineChart2.getAxisLeft().setTextColor(Color.parseColor("#708099"));
        lineChart.getLegend().setTextColor(Color.parseColor("#708099"));
        lineChart2.getLegend().setTextColor(Color.parseColor("#708099"));

        lineChart2.animateY(3000, Easing.EaseInOutBack);
    }

    private void initGroupBarChart(JSONObject jsonObject1, JSONObject jsonObject2) throws JSONException {

        ArrayList<BarEntry> dataVal3 = new ArrayList<BarEntry>();
        JSONArray xValues1 = jsonObject1.getJSONArray("xValues");
        JSONArray yValues1 = jsonObject1.getJSONArray("yValues");
        JSONArray labels = jsonObject1.getJSONArray("labels");

        JSONArray xValues2 = jsonObject2.getJSONArray("xValues");
        JSONArray yValues2 = jsonObject2.getJSONArray("yValues");

        for (int i = 0; i < xValues1.length(); i++) {
            dataVal3.add(new BarEntry(Float.parseFloat(xValues1.getString(i)), Float.parseFloat(yValues1.getString(i))));
        }

//        dataVal3.add(new BarEntry(1, 12.5f));
//        dataVal3.add(new BarEntry(2, 10));
//        dataVal3.add(new BarEntry(3, 7.5f));
//        dataVal3.add(new BarEntry(4, 8));
//        dataVal3.add(new BarEntry(5, 6.5f));


        ArrayList<BarEntry> dataVal4 = new ArrayList<BarEntry>();
        for (int i = 0; i < xValues2.length(); i++) {
            dataVal4.add(new BarEntry(Float.parseFloat(xValues2.getString(i)), Float.parseFloat(yValues2.getString(i))));
        }

//        dataVal4.add(new BarEntry(1, 6f));
//        dataVal4.add(new BarEntry(2, 8));
//        dataVal4.add(new BarEntry(3, 5));
//        dataVal4.add(new BarEntry(4, 2));
//        dataVal4.add(new BarEntry(5, 3.5f));

        groupBarChart.getDescription().setEnabled(false);
        groupBarChart.setDrawGridBackground(false);
        groupBarChart.getXAxis().setCenterAxisLabels(true);
        groupBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        groupBarChart.getXAxis().setDrawGridLines(false);
        groupBarChart.getAxisRight().setDrawGridLines(false);
        groupBarChart.getAxisRight().setDrawAxisLine(false);
        groupBarChart.getAxisRight().setDrawLabels(false);
        groupBarChart.getAxisLeft().setDrawGridLines(false);
        groupBarChart.setPinchZoom(false);
        groupBarChart.getXAxis().setGranularity(1f);


        BarDataSet barDataSet2 = new BarDataSet(dataVal3, labels.getString(9));
        BarDataSet barDataSet3 = new BarDataSet(dataVal4, labels.getString(4));

        barDataSet2.setForm(Legend.LegendForm.CIRCLE);
        barDataSet3.setForm(Legend.LegendForm.CIRCLE);

        BarData barData2 = new BarData(barDataSet2, barDataSet3);

        groupBarChart.setData(barData2);
        groupBarChart.setVisibleXRangeMaximum(dataVal3.size());
        groupBarChart.setExtraBottomOffset(15f);


        barDataSet2.setColor(Color.parseColor("#5b79e7"));
        barDataSet3.setColor(Color.parseColor("#27adb9"));
        barDataSet2.setDrawValues(false);
        barDataSet3.setDrawValues(false);

        barData2.setBarWidth(0.3f);
        groupBarChart.getXAxis().setAxisMaximum(0);
        groupBarChart.getXAxis().setAxisMaximum(0 + groupBarChart.getBarData().getGroupWidth(0.4f, 0f) * 6);
        groupBarChart.groupBars(1, 0.4f, 0f);

        groupBarChart.animateXY(3000, 3000);
        groupBarChart.getAxisRight().setDrawLabels(false);

    }

    private void initBubbleChart() {
        ArrayList<BubbleEntry> bubbleEntries = new ArrayList<BubbleEntry>();
        bubbleEntries.add(new BubbleEntry(1, 1, 0.001f));
        bubbleEntries.add(new BubbleEntry(2, 2, 0.0021f));
        bubbleEntries.add(new BubbleEntry(3, 3, 0.0017f));
        bubbleEntries.add(new BubbleEntry(4, 4, 0.0008f));
        bubbleEntries.add(new BubbleEntry(5, 1, 0.0002f));
        bubbleEntries.add(new BubbleEntry(6, 3, 0.0018f));
        bubbleEntries.add(new BubbleEntry(7, 1, 0.0012f));

        BubbleDataSet bubbleDataSet = new BubbleDataSet(bubbleEntries, "Event actions");
        bubbleDataSet.setColors(Color.parseColor("#5d78df"), Color.parseColor("#768fec"), Color.parseColor("#7790ec"));
        bubbleDataSet.setForm(Legend.LegendForm.CIRCLE);
        bubbleDataSet.setDrawValues(false);
        BubbleData bubbleData = new BubbleData();
        bubbleData.addDataSet(bubbleDataSet);
        bubbleChart.setData(bubbleData);
        bubbleChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        //bubbleChart.getAxisLeft().setDrawLabels(false);
        bubbleChart.setDrawGridBackground(false);
        //bubbleChart.getAxisLeft().setDrawAxisLine(false);
        bubbleChart.getAxisLeft().setDrawGridLines(false);
        bubbleChart.getAxisRight().setDrawLabels(false);
        bubbleChart.getAxisRight().setDrawGridLines(false);
        bubbleChart.getAxisRight().setDrawAxisLine(false);
        bubbleChart.getXAxis().setDrawGridLines(false);
        //bubbleChart.getXAxis().setAvoidFirstLastClipping(true);
        bubbleChart.getXAxis().setCenterAxisLabels(true);
        bubbleChart.getXAxis().setAxisMinimum(0.5f);
        bubbleChart.getXAxis().setAxisMaximum(7.5f);

        String[] bubbleXlabel = {"Apr 6", "Apr 7", "Apr 8", "Apr 9", "Apr 10", "Apr 11", "Apr 12"};
        String[] bubbleYlablel = {"Payment", "Scan", "Activate", "Serach"};

        bubbleChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(bubbleXlabel));
        //bubbleChart.getAxisLeft().setValueFormatter(new IndexAxisValueFormatter(bubbleYlablel));
        bubbleChart.getXAxis().setGranularity(1f);
        //bubbleChart.getXAxis().setLabelCount(10);
        //bubbleChart.setViewPortOffsets(60,10,50,60);
        bubbleChart.animateXY(3000, 3000);
        bubbleChart.getLegend().setEnabled(false);
    }

    private void initHorizontalBarChart(JSONObject jsonObjectp) throws JSONException {

        JSONArray xValues = jsonObjectp.getJSONArray("xValues");
        JSONArray yValues = jsonObjectp.getJSONArray("xValues");
        JSONArray labels = jsonObjectp.getJSONArray("labels");

        ArrayList<BarEntry> hbardatavals = new ArrayList<>();
        for (int i = 0; i < xValues.length(); i++) {
            hbardatavals.add(new BarEntry(Float.parseFloat(xValues.getString(i)) - 1, Float.parseFloat(yValues.getString(i))));
        }
//
//        hbardatavals.add(new BarEntry(0, 10f));
//        hbardatavals.add(new BarEntry(1, 20f));
//        hbardatavals.add(new BarEntry(2, 30f));
//        hbardatavals.add(new BarEntry(3, 40f));
//        hbardatavals.add(new BarEntry(4, 50f));
//        hbardatavals.add(new BarEntry(5, 60f));

        BarDataSet hbarDataSet = new BarDataSet(hbardatavals, "sample horizontal bar graph");

        hbarDataSet.setColors(Color.parseColor("#5b79e7"),
                Color.parseColor("#317892"),
                Color.parseColor("#61c2cc"),
                Color.parseColor("#61c2cc"),
                Color.parseColor("#92a4d4"),
                Color.parseColor("#abdbe3"));
        BarData hbarData = new BarData(hbarDataSet);
        hbarData.setBarWidth(0.5f);

        horizontalBarChart.setData(hbarData);
        horizontalBarChart.getXAxis().setDrawAxisLine(false);
        horizontalBarChart.getXAxis().setDrawGridLines(false);
        horizontalBarChart.getAxisRight().setDrawAxisLine(false);
        horizontalBarChart.getAxisRight().setDrawGridLines(false);
        horizontalBarChart.getAxisLeft().setDrawGridLines(false);
        horizontalBarChart.getAxisLeft().setDrawAxisLine(false);
        horizontalBarChart.getAxisLeft().setDrawLabels(false);
        horizontalBarChart.getAxisRight().setDrawLabels(false);
        //horizontalBarChart.getXAxis().setDrawLabels(false);

        horizontalBarChart.getAxisRight().setSpaceMax(0f);
        horizontalBarChart.getAxisRight().setGranularity(1f);
        horizontalBarChart.getLegend().setEnabled(false);
        horizontalBarChart.getDescription().setEnabled(false);
        horizontalBarChart.getAxisLeft().setAxisMinimum(0f);
        horizontalBarChart.getXAxis().setLabelCount(labels.length());


        String[] labelss = new String[labels.length()];
        for (int i = 0; i < labels.length(); i++) {
            labelss[i] = labels.getString(i);
        }

        ArrayList<String> hbarxlabels = new ArrayList<>(Arrays.asList(labelss));
        horizontalBarChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(hbarxlabels));
        horizontalBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        horizontalBarChart.animateY(3000);
    }

    private void initDonutChart() {
        donutPieChart.setDrawSliceText(false);
        donutPieChart.setHoleRadius(80);
        donutPieChart.animateX(3000, Easing.EaseInOutCirc);

        ArrayList<PieEntry> piedatas = new ArrayList<>();
        piedatas.add(new PieEntry(58, "Cash Sales Adjustment"));
        piedatas.add(new PieEntry(42, "Credit Sales Adjustment"));

        PieDataSet pieDataSetOS = new PieDataSet(piedatas, "");
        pieDataSetOS.setColors(Color.parseColor("#5472e8"), Color.parseColor("#26adb9"));
        pieDataSetOS.setDrawValues(false);

        PieData pieDataos = new PieData(pieDataSetOS);
        donutPieChart.setData(pieDataos);
    }

    private void initProgressBars() {
        progressBar1.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#5472e8")));
        progressBar2.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#26adb9")));
    }

    public void makeRequests(Context context, String URL, String reqNo) {

        final JSONObject RealResponse = new JSONObject();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JSONObject jsonBody = new JSONObject();

        // making the request object using the Volley library utility methods
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject responsep) {
                try {

                    // again using the requestNumber to find which parser method to use since every request results in different object result

                    JSONObject myObject = responsep.getJSONObject("data");
                    switch (reqNo) {
                        case "1":
                            updateLineChartOneWithData(myObject);
                            break;
                        case "2":
                            updateLineChartTwoWithData(myObject);
                            break;
                        case "3":
                            updateHorizontalBarChartOneWithData(myObject);
                        case "4":
                            updateGroupBarChartWithData(myObject);
                    }
                } catch (JSONException e) {
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
                        jsonObject.put("data", responseArray);
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

    }

}

class LineChartThread extends Thread {
    int bardatas;

    public LineChartThread(int bardatas) {
        this.bardatas = bardatas;
    }


    @Override
    public void run() {

        for (int i = 0; i < bardatas; i++) {
            Message msg = DashBoardFragment.lineChartHandler.obtainMessage();
            msg.obj = String.valueOf(i);
            DashBoardFragment.lineChartHandler.sendMessage(msg);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}

class HorizontalBarThread extends Thread {
    int bardatas;

    public HorizontalBarThread(int bardatas) {
        this.bardatas = bardatas;
    }


    @Override
    public void run() {

        for (int i = 0; i < bardatas; i++) {
            Message msg = DashBoardFragment.horizontalBarchartHandler.obtainMessage();
            msg.obj = String.valueOf(i);
            DashBoardFragment.horizontalBarchartHandler.sendMessage(msg);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}

class GroupLineThread extends Thread {
    int bardatas;

    public GroupLineThread(int bardatas) {
        this.bardatas = bardatas;
    }


    @Override
    public void run() {

        for (int i = 0; i < bardatas; i++) {
            Message msg = DashBoardFragment.groupLineChartHandler.obtainMessage();
            msg.obj = String.valueOf(i);
            DashBoardFragment.groupLineChartHandler.sendMessage(msg);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}

class GroupBarChartThread extends Thread {
    int bardatas;

    public GroupBarChartThread(int bardatas) {
        this.bardatas = bardatas;
    }


    @Override
    public void run() {

        for (int i = 0; i < bardatas; i++) {
            if(DashBoardFragment.groupLineChartHandler != null){
                Message msg = DashBoardFragment.groupBarChartHandler.obtainMessage();
                msg.obj = String.valueOf(i);
                DashBoardFragment.groupBarChartHandler.sendMessage(msg);
            }
            else {
                Message msg = new Message();
                msg.obj = "0";
                DashBoardFragment.groupBarChartHandler.sendMessage(msg);
            }

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}
