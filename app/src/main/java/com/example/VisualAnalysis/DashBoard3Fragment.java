package com.example.VisualAnalysis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class DashBoard3Fragment extends Fragment {

    private TableLayout tableLayout;
    public static Fragment me;
    FrameLayout frameLayout;

    public static ArrayList<Table> tables = new ArrayList<>();
    public static Handler tableRowsHandler;

    NumberFormat numberFormat;


    ScrollView scrollView;
    String url = "http://192.168.1.248:8001/api/ChartData/GetSalesDataForAllOrganizations";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflater.getContext().setTheme(R.style.darkTheme);

        if(me == null)
            me = this;
        View view = getLayoutInflater().inflate(R.layout.fragment_dash_board3, container, false);
        tableLayout = view.findViewById(R.id.tableLayout);
        scrollView = view.findViewById(R.id.scrolll);
        frameLayout = view.findViewById(R.id.progressBarFrame);


        String date = "2021-04-02T11:57:04";

        makeRequest(getContext());
        updateTable(view);
//        frameLayout.setVisibility(View.INVISIBLE);


        return view;
    }


    @SuppressLint("HandlerLeak")
    private void updateTable(View view) {

        Log.i("Update", "I AM UPDATING");

        tableRowsHandler = new Handler() {
            @SuppressLint("SetTextI18n")
            @Override
            public void handleMessage(@NonNull Message msg) {
                String message = (String) msg.obj;
                Integer index = Integer.parseInt(message);
                if (index != null) {
                    editTableValues(view, getContext());
                }
            }
        };

        TableRowThread tableRowThread = new TableRowThread();
        tableRowThread.start();
    }

    private void editTableValues(View view, Context context) {
        Log.i("edit", "I am editing");
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            if (response != null) {
                try {
                    frameLayout.setVisibility(View.GONE);
                    ArrayList<Table> tablesToDisplay = getTableDataFromRequestBody(response);
                    setUpdatedTables(tablesToDisplay, view);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> Log.i("TAG-error", error + ""));
        requestQueue.add(jsonArrayRequest);
        Util.retryRequest(jsonArrayRequest);
    }


    private void setUpdatedTables(ArrayList<Table> editedTableData, View view) {
        TableLayout tableLayout = view.findViewById(R.id.tableLayout);
        Thread tThread = new Thread(() -> {
            for (int i = -1; i < editedTableData.size(); i++) {
                int finalI = i;
                requireActivity().runOnUiThread(new Runnable() {
                    @SuppressLint("HandlerLeak")
                    @Override
                    public void run() {
                        if (finalI >= 0) {
                            TableRow tableRow = (TableRow) tableLayout.getChildAt(finalI);
                            TextView textView2 = tableRow.findViewById(R.id.tVSIcount);
                            TextView textView3 = tableRow.findViewById(R.id.tactive);
                            TextView textView4 = tableRow.findViewById(R.id.tProspect);
                            TextView textView7 = tableRow.findViewById(R.id.tOutlets);
                            TextView textView8 = tableRow.findViewById(R.id.tSKUcount);
                            TextView textView9 = tableRow.findViewById(R.id.tQty);
                            TextView textView10 = tableRow.findViewById(R.id.tTotalSales);

                            numberFormat = NumberFormat.getInstance();
                            numberFormat.setGroupingUsed(true);
                            int quantityFormatted = editedTableData.get(finalI).quantityCount + 5;
                            double totalFormatted = editedTableData.get(finalI).totalSalesAmountAfterTax + 108254;

                            textView2.setText(String.valueOf(editedTableData.get(finalI).vsiCount + 2));
                            textView3.setText(String.valueOf(editedTableData.get(finalI).active + 2));
                            textView4.setText(String.valueOf(editedTableData.get(finalI).prospect + 3));
                            textView7.setText(String.valueOf(editedTableData.get(finalI).salesOutLateCount + 5));
                            textView8.setText(String.valueOf(editedTableData.get(finalI).skuCount + 9));
                            textView9.setText(numberFormat.format(quantityFormatted));
                            textView10.setText(numberFormat.format(totalFormatted));

                        }
                    }

                });

                try {
                    if (finalI == -1) {
                        Thread.sleep(20000);
                    }
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        tThread.start();
    }

    private void makeRequest(Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            if (response != null) {
                try {
                    frameLayout.setVisibility(View.GONE);
                    ArrayList<Table> tablesToDisplay = getTableDataFromRequestBody(response);
                    if(tablesToDisplay.size() > 0)
                        initTable(tablesToDisplay);
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

    void initTable(ArrayList<Table> tables) {
        Thread tThread = new Thread(() -> {

            for (int i = 0; i < tables.size(); i++) {
                int finalI = i;
                requireActivity().runOnUiThread(new Runnable() {
                    @SuppressLint("HandlerLeak")
                    @Override
                    public void run() {

                        View tableElements = LayoutInflater.from(getContext()).inflate(R.layout.table_elements, null, false);

                        TextView textView1 = tableElements.findViewById(R.id.tdistributor);
                        TextView textView2 = tableElements.findViewById(R.id.tVSIcount);
                        TextView textView3 = tableElements.findViewById(R.id.tactive);
                        TextView textView4 = tableElements.findViewById(R.id.tProspect);
                        TextView textView5 = tableElements.findViewById(R.id.tStartTime);
                        TextView textView6 = tableElements.findViewById(R.id.tLastSeen);
                        TextView textView7 = tableElements.findViewById(R.id.tOutlets);
                        TextView textView8 = tableElements.findViewById(R.id.tSKUcount);
                        TextView textView9 = tableElements.findViewById(R.id.tQty);
                        TextView textView10 = tableElements.findViewById(R.id.tTotalSales);

                        String orgName = tables.get(finalI).organizationName;
                        String preciseOrgName;
                        if (orgName.length() > 30)
                            preciseOrgName = orgName.substring(0, 20) + "...";
                        else
                            preciseOrgName = orgName;

                        numberFormat = NumberFormat.getInstance();
                        numberFormat.setGroupingUsed(true);

                        int quantityCount = tables.get(finalI).quantityCount;
                        double totalSalesAmountAfterTax = tables.get(finalI).totalSalesAmountAfterTax;

                        textView1.setText(preciseOrgName);
                        textView2.setText(String.valueOf(tables.get(finalI).vsiCount));
                        textView3.setText(String.valueOf(tables.get(finalI).active));
                        textView4.setText(String.valueOf(tables.get(finalI).prospect));
                        textView5.setText(tables.get(finalI).startTime);
                        textView6.setText(tables.get(finalI).lastSeen);
                        textView7.setText(String.valueOf(tables.get(finalI).salesOutLateCount));
                        textView8.setText(String.valueOf(tables.get(finalI).skuCount));
                        textView9.setText(numberFormat.format(quantityCount));
                        textView10.setText(numberFormat.format(totalSalesAmountAfterTax));

                        TableRow tableRow = tableElements.findViewById(R.id.tableRow);
                        tableLayout.addView(tableElements);

                        Util.animate(tableLayout,tableRow);
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

    public ArrayList<Table> getTableDataFromRequestBody(JSONArray tables) throws JSONException {
        ArrayList<Table> parsedTableData = new ArrayList<>();
        for (int i = 0; i < tables.length(); i++) {
            JSONObject tableObject = tables.getJSONObject(i);

            double grandTotal = tableObject.getDouble("totalSalesAmountAfterTax");
            double roundedGrandTotal = Math.round(grandTotal * 100.0) / 100.0;

            Table tableRow = new Table(
                    tableObject.getString("organizationName"),
                    formatTime(tableObject.getString("startTimeStamp")),
                    formatTime(tableObject.getString("endTimeStamp")),
                    tableObject.getInt("vsiCount"),
                    tableObject.getInt("salesOutLateCount"),
                    tableObject.getInt("skuCount"),
                    tableObject.getInt("quantityCount"),
                    roundedGrandTotal,
                    tableObject.getInt("active"),
                    tableObject.getInt("prospect")
            );
            parsedTableData.add(tableRow);
        }
        return parsedTableData;
    }

    private String formatTime(String lastActive) {
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("HH:mm:ss");


        Date parsed = null;
        String formattedTime = null;
        try {
            parsed = input.parse(lastActive);
            formattedTime = output.format(parsed);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedTime;
    }

}


class TableRowThread extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 2; i++) {
            Message msg = DashBoard3Fragment.tableRowsHandler.obtainMessage();
            msg.obj = String.valueOf(i);
            DashBoard3Fragment.tableRowsHandler.sendMessage(msg);

            try {

                if (i == 1) {
                    Thread.sleep(60000);
                    Log.i("current_dest", NavHostFragment.findNavController(DashBoard3Fragment.me).getCurrentDestination()+"" );
                    NavHostFragment.findNavController(DashBoard3Fragment.me).navigate(R.id.action_dashBoard3Fragment_to_dashBoard4Fragment);
                } else {
                    Thread.sleep(20000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

