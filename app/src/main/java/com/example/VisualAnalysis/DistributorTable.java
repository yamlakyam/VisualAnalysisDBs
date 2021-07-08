package com.example.VisualAnalysis;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DistributorTable extends Fragment {

    FrameLayout frameLayout;
    TableLayout tableLayout;
    ScrollView scrollView;
    TextView distributorTextview;

    public static Fragment me;
    public static Activity activity;

    public static Handler tableRowsHandler;

    NumberFormat numberFormat;
    Table2Thread tableRowThread;

    String url = "http://192.168.1.248:8001/api/ChartData/GetSalesDataForSingleOrganization";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dash_board4, container, false);

        tableLayout = view.findViewById(R.id.tableLayout2);
        frameLayout = view.findViewById(R.id.progressBarFrame2);
        scrollView = view.findViewById(R.id.scrolll2);

        distributorTextview = view.findViewById(R.id.distributorHeaderDB4);
        me = this;
        activity = getActivity();

        makeRequest(getContext());
        updateTable(view);

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        tableRowThread.interrupt();
    }

    @Override
    public void onPause() {
        super.onPause();
        tableRowThread.interrupt();
    }

    @SuppressLint("HandlerLeak")
    private void makeRequest(Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            if (response != null) {
                try {
                    frameLayout.setVisibility(View.GONE);

                    JSONObject jsonObject = response.getJSONObject(0);
                    String name = jsonObject.getString("nameOfOrg");

                    distributorTextview.setText(name);
                    Log.i("name", name);

                    JSONArray jsonArray = jsonObject.getJSONArray("organizationChartDataList");


                    ArrayList<Table> tablesToDisplay = getTableDataFromRequestBody(jsonArray);

                    if (tablesToDisplay.size() > 0) {
                        initTable(tablesToDisplay);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> {
            Log.i("TAG-error", error + "");
            frameLayout.setVisibility(View.GONE);
        });

        requestQueue.add(jsonArrayRequest);
        Util.retryRequest(jsonArrayRequest);

    }

    public ArrayList<Table> getTableDataFromRequestBody(JSONArray tables) throws JSONException {
        ArrayList<Table> parsedTables = new ArrayList<>();
        for (int i = 0; i < tables.length(); i++) {
            JSONObject jsonObject = tables.getJSONObject(i);

            double grandTotal = jsonObject.getDouble("totalSalesAmountAfterTax");
            double roundedGrandTotal = Math.round(grandTotal * 100.0) / 100.0;

            Table tableRow = new Table(
                    jsonObject.getString("vsi"),
                    jsonObject.getInt("salesOutLateCount"),
                    jsonObject.getInt("skuCount"),
                    jsonObject.getInt("quantityCount"),
                    roundedGrandTotal,
                    jsonObject.getInt("prospect"),
                    jsonObject.getString("startTimeStamp")
            );

            parsedTables.add(tableRow);
        }
        return parsedTables;
    }

    void initTable(ArrayList<Table> tables) {
        addRowsSequentially(tables);
    }

    private void initRow(int finalI, ArrayList<Table> tableList) {
        View tableElements = LayoutInflater.from(getContext()).inflate(R.layout.table_elements_2, null, false);
        TextView textViewSN = tableElements.findViewById(R.id.t2sn);
        TextView textViewVSI = tableElements.findViewById(R.id.t2Vsi);
        TextView textViewProspects = tableElements.findViewById(R.id.t2vprospect);
        TextView textViewLastActive = tableElements.findViewById(R.id.t2lastSeen);
        TextView textViewTotalOutlets = tableElements.findViewById(R.id.t2totalOutlet);
        TextView textViewTotalSKU = tableElements.findViewById(R.id.t2totalSKU);
        TextView textViewTotalQty = tableElements.findViewById(R.id.t2totalQty);
        TextView textViewTotalSales = tableElements.findViewById(R.id.t2totalSales);


        String orgName = tableList.get(finalI).vsi;
        String preciseOrgName;
        if (orgName.length() > 30)
            preciseOrgName = orgName.substring(0, 20) + "...";
        else
            preciseOrgName = orgName;

        numberFormat = NumberFormat.getInstance();
        numberFormat.setGroupingUsed(true);

        int quantityCount = tableList.get(finalI).quantityCount;
        double totalSalesAmountAfterTax = tableList.get(finalI).totalSalesAmountAfterTax;

        Date lastActive = Util.formatTime(tableList.get(finalI).lastSeen);
        Date currentTime = Calendar.getInstance().getTime();

        String formattedLastSeen = Util.timeElapsed(lastActive, currentTime);

        textViewSN.setText(String.valueOf(finalI + 1));
        textViewVSI.setText(preciseOrgName);
        textViewProspects.setText(String.valueOf(tableList.get(finalI).prospect));
        textViewLastActive.setText(formattedLastSeen);
        textViewTotalOutlets.setText(String.valueOf(tableList.get(finalI).salesOutLateCount));
        textViewTotalSKU.setText(String.valueOf(tableList.get(finalI).skuCount));
        textViewTotalQty.setText(numberFormat.format(quantityCount));
        textViewTotalSales.setText(numberFormat.format(totalSalesAmountAfterTax));

        TableRow tableRow = tableElements.findViewById(R.id.VSMtableRow);
        tableLayout.addView(tableElements);

        Util.animate(tableLayout, tableRow);
    }

    @SuppressLint("HandlerLeak")
    private void updateTable(View view) {
        tableRowsHandler = new Handler() {
            @SuppressLint("SetTextI18n")
            @Override
            public void handleMessage(@NonNull Message msg) {
                String message = (String) msg.obj;
                int index = Integer.parseInt(message);

                Log.i("INDEX", message);
                editTableValues(view, getContext(), index);
            }
        };

        tableRowThread = new Table2Thread(3);
        tableRowThread.start();
    }

    private void editTableValues(View view, Context context, int index) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            if (response != null ) {
                try {
                    frameLayout.setVisibility(View.GONE);

                    if (index > 0) {
                        JSONObject jsonObject = response.getJSONObject(index);
                        String name = jsonObject.getString("nameOfOrg");

                        distributorTextview.setText(name);
                        JSONArray jsonArray = jsonObject.getJSONArray("organizationChartDataList");
                        ArrayList<Table> tablesToDisplay = getTableDataFromRequestBody(jsonArray);
                        DistributorTable.this.setUpdatedTables(tablesToDisplay, view);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, error -> Log.i("TAG-error", error + ""));

        requestQueue.add(jsonArrayRequest);
        Util.retryRequest(jsonArrayRequest);
    }

    private void setUpdatedTables(ArrayList<Table> editedTableData, View view) {
        TableLayout tableLayout = view.findViewById(R.id.tableLayout2);
        tableLayout.removeAllViews();
        addRowsSequentially(editedTableData);
    }

    private void addRowsSequentially(ArrayList<Table> tables) {
        Thread tThread = new Thread(() -> {
            for (int i = 0; i < tables.size(); i++) {
                int finalI = i;

                requireActivity().runOnUiThread(new Runnable() {
                    @SuppressLint("HandlerLeak")
                    @Override
                    public void run() {
                        initRow(finalI, tables);
                    }
                });

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }

        });
        tThread.start();
    }
}

class Table2Thread extends Thread {
    int distributordataSize;

    Table2Thread(int distributordataSize) {
        this.distributordataSize = distributordataSize;
    }

    @Override
    public void run() {

        for (int i = 0; i < distributordataSize; i++) {
            Message msg = DistributorTable.tableRowsHandler.obtainMessage();
            msg.obj = String.valueOf(i);
            DistributorTable.tableRowsHandler.sendMessage(msg);

            try {
                if (i >= 2) {
                    Thread.sleep(10000);
                    DistributorTable.activity.runOnUiThread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    Log.i("current_dest", NavHostFragment.findNavController(DistributorTable.me).getCurrentDestination() + "");
//                                    if (NavHostFragment.findNavController(DistributorTable.me).getCurrentDestination().getId() == R.id.vsmCardFragment) {
//
//                                        NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.vsmCardFragment, true).build();
//                                        NavHostFragment.findNavController(DistributorTable.me).navigate(R.id.vsmCardFragment,null, navOptions);
////
//                                    }
//                                    else{
                                        NavHostFragment.findNavController(DistributorTable.me).navigate(R.id.vsmCardFragment);

//                                    }
                                }
                            });

                } else {
                    Thread.sleep(10000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}