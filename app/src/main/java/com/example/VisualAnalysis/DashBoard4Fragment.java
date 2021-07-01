package com.example.VisualAnalysis;

import android.annotation.SuppressLint;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;

public class DashBoard4Fragment extends Fragment {

    FrameLayout frameLayout;
    TableLayout tableLayout;
    ScrollView scrollView;


    public static Handler tableRowsHandler;

    NumberFormat numberFormat;

    String url = "http://192.168.1.248:8001/api/ChartData/GetSalesDataByOrganization";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dash_board4, container, false);

        tableLayout = view.findViewById(R.id.tableLayout2);
        frameLayout = view.findViewById(R.id.progressBarFrame2);
        scrollView = view.findViewById(R.id.scrolll2);

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
                    initTable(tablesToDisplay.get(0));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> {
            Log.i("TAG-error", error + "");
            frameLayout.setVisibility(View.GONE);
        });

        requestQueue.add(jsonArrayRequest);
        jsonArrayRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
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
                        roundedGrandTotal
                );
                parsedTableData.add(tableRow);
            }
            parsedTables.add(parsedTableData);
        }
        return parsedTables;
    }

    void initTable(ArrayList<Table> tables) {
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

    private void initRow(int finalI, ArrayList<Table> tableList) {
        View tableElements = LayoutInflater.from(getContext()).inflate(R.layout.table_elements_2, null, false);
        TextView textView0 = tableElements.findViewById(R.id.t2Value0);
        TextView textView1 = tableElements.findViewById(R.id.t2value1);
        TextView textView2 = tableElements.findViewById(R.id.t2value2);
        TextView textView3 = tableElements.findViewById(R.id.t2value3);
        TextView textView4 = tableElements.findViewById(R.id.t2value4);
        TextView textView5 = tableElements.findViewById(R.id.t2value5);
        TextView textView6 = tableElements.findViewById(R.id.t2value6);
        TextView textView7 = tableElements.findViewById(R.id.t2value7);
        TextView textView8 = tableElements.findViewById(R.id.t2value8);
        TextView textView9 = tableElements.findViewById(R.id.t2value9);
        TextView textView10 = tableElements.findViewById(R.id.t2value10);


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

        textView0.setText(String.valueOf(finalI+1));
        textView1.setText(preciseOrgName);
        textView6.setText(String.valueOf(tableList.get(finalI).salesOutLateCount));

        textView8.setText(String.valueOf(tableList.get(finalI).skuCount));
        textView9.setText(numberFormat.format(quantityCount));
        textView10.setText(numberFormat.format(totalSalesAmountAfterTax));

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
                        DashBoard4Fragment.this.setUpdatedTables(tablesToDisplay.get(index), view);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, error -> Log.i("TAG-error", error + ""));


        requestQueue.add(jsonArrayRequest);
        jsonArrayRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 60000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 60000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
    }

    private void setUpdatedTables(ArrayList<Table> editedTableData, View view) {
        TableLayout tableLayout = view.findViewById(R.id.tableLayout2);
        tableLayout.removeAllViews();

        Thread tThread = new Thread(() -> {
            for (int i = 0; i < editedTableData.size(); i++) {
                int finalI = i;
                requireActivity().runOnUiThread(new Runnable() {
                    @SuppressLint("HandlerLeak")
                    @Override
                    public void run() {
                        if (finalI >= 0) {
                            initRow(finalI, editedTableData);

                        }
                    }

                });

                try {
                    if (editedTableData.size() == 0) {
                        Thread.sleep(10);
                    } else
                        Thread.sleep(700);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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

        for (int i = 0; i < 3; i++) {
            Message msg = DashBoard4Fragment.tableRowsHandler.obtainMessage();
            msg.obj = String.valueOf(i);
            DashBoard4Fragment.tableRowsHandler.sendMessage(msg);
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}