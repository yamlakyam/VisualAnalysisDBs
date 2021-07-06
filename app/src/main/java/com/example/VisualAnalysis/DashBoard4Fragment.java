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

public class DashBoard4Fragment extends Fragment {

    FrameLayout frameLayout;
    TableLayout tableLayout;
    ScrollView scrollView;

    public static Fragment me;
    public static Activity activity;

    public static Handler tableRowsHandler;

    NumberFormat numberFormat;

    String url = "http://192.168.1.248:8001/api/ChartData/GetSalesDataForSingleOrganization";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dash_board4, container, false);

        tableLayout = view.findViewById(R.id.tableLayout2);
        frameLayout = view.findViewById(R.id.progressBarFrame2);
        scrollView = view.findViewById(R.id.scrolll2);
        me = this;
        activity = getActivity();

        makeRequest(getContext());
        updateTable(view);

        return view;
    }

    @SuppressLint("HandlerLeak")
    private void makeRequest(Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            if (response != null) {
                try {
                    Log.i("size", response.length() + "");
                    frameLayout.setVisibility(View.GONE);
                    ArrayList<ArrayList<Table>> tablesToDisplay = getTableDataFromRequestBody(response);
//                    if(tablesToDisplay!=null){
                    if (tablesToDisplay.size() > 0) {
                        initTable(tablesToDisplay.get(0));
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

    public ArrayList<ArrayList<Table>> getTableDataFromRequestBody(JSONArray tables) throws JSONException {
        ArrayList<ArrayList<Table>> parsedTables = new ArrayList<>();
        for (int i = 0; i < tables.length(); i++) {
            JSONArray jsonArray = tables.getJSONArray(i);
            ArrayList<Table> parsedTableData = new ArrayList<>();
            for (int j = 0; j < jsonArray.length(); j++) {
                JSONObject tableObject = jsonArray.getJSONObject(j);
                double grandTotal = tableObject.getDouble("totalSalesAmountAfterTax");
                double roundedGrandTotal = Math.round(grandTotal * 100.0) / 100.0;

                Table tableRow = new Table(
                        tableObject.getString("vsi"),
                        tableObject.getInt("salesOutLateCount"),
                        tableObject.getInt("skuCount"),
                        tableObject.getInt("quantityCount"),
                        roundedGrandTotal,
                        tableObject.getInt("prospect"),
                        tableObject.getString("startTimeStamp")
                );
                parsedTableData.add(tableRow);
            }
            parsedTables.add(parsedTableData);
        }
        return parsedTables;
    }

    void initTable(ArrayList<Table> tables) {
        addRowsSequentially(tables);
    }

    private void initRow(int finalI, ArrayList<Table> tableList) {
        View tableElements = LayoutInflater.from(getContext()).inflate(R.layout.table_elements_2, null, false);
        TextView textView0 = tableElements.findViewById(R.id.t2sn);
        TextView textView1 = tableElements.findViewById(R.id.t2Vsi);
        TextView textView2 = tableElements.findViewById(R.id.t2vprospect);
        TextView textView3 = tableElements.findViewById(R.id.t2lastSeen);
        TextView textView4 = tableElements.findViewById(R.id.t2totalOutlet);
        TextView textView5 = tableElements.findViewById(R.id.t2totalSKU);
        TextView textView6 = tableElements.findViewById(R.id.t2totalQty);
        TextView textView7 = tableElements.findViewById(R.id.t2totalSales);


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

        textView0.setText(String.valueOf(finalI + 1));
        textView1.setText(preciseOrgName);
        textView2.setText(String.valueOf(tableList.get(finalI).prospect));
        textView3.setText(formattedLastSeen);
        textView4.setText(String.valueOf(tableList.get(finalI).salesOutLateCount));
        textView5.setText(String.valueOf(tableList.get(finalI).skuCount));
        textView6.setText(numberFormat.format(quantityCount));
        textView7.setText(numberFormat.format(totalSalesAmountAfterTax));

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

        Table2Thread tableRowThread = new Table2Thread(3);
        tableRowThread.start();
    }

    private void editTableValues(View view, Context context, int index) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            if (response != null) {
                try {
                    frameLayout.setVisibility(View.GONE);
                    ArrayList<ArrayList<Table>> tablesToDisplay = DashBoard4Fragment.this.getTableDataFromRequestBody(response);
                    if (index > 0) {
                        if (tablesToDisplay.size() > 0) {
                            DashBoard4Fragment.this.setUpdatedTables(tablesToDisplay.get(index), view);
                        }
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
            Message msg = DashBoard4Fragment.tableRowsHandler.obtainMessage();
            msg.obj = String.valueOf(i);
            DashBoard4Fragment.tableRowsHandler.sendMessage(msg);

            try {
                if (i >= 2) {
                    Thread.sleep(20000);
                    DashBoard4Fragment.activity.runOnUiThread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    Log.i("current_dest", NavHostFragment.findNavController(DashBoard4Fragment.me).getCurrentDestination() + "");

//                                    NavHostFragment.findNavController(DashBoard3Fragment.me).popBackStack(R.id.dashBoard4Fragment,true);
//                                    if (NavHostFragment.findNavController(DashBoard4Fragment.me).getCurrentDestination().getId() == R.id.dashBoard4Fragment) {
                                    NavHostFragment.findNavController(DashBoard4Fragment.me).navigate(R.id.action_dashBoard4Fragment_to_vsmCardFragment2);
//                                    DashBoard4Fragment.activity.finishAffinity();
//                                    } else {
//                                        NavHostFragment.findNavController(DashBoard3Fragment.me).navigate(R.id.action_dashBoard3Fragment_to_vsmCardFragment);
//                                    }
                                }
                            });

                } else {
                    Thread.sleep(20000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}