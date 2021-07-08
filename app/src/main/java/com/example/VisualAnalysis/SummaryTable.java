package com.example.VisualAnalysis;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
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


public class SummaryTable extends Fragment {

//    TableRowThread tableRowThread;

    private TableLayout tableLayout;
    public static Fragment me;
    FrameLayout frameLayout;
    public static Activity activity;

    public static ArrayList<Table> tables = new ArrayList<>();
    public static Handler tableRowsHandler;

    boolean navigatedOnce;

    NumberFormat numberFormat;
    View view;

    ScrollView scrollView;
    String url = "http://192.168.1.248:8001/api/ChartData/GetSalesDataForAllOrganizations";

    Handler h;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflater.getContext().setTheme(R.style.darkTheme);
        activity = getActivity();

        if (me == null)
            me = this;
        view = getLayoutInflater().inflate(R.layout.fragment_dash_board3, container, false);
        tableLayout = view.findViewById(R.id.tableLayout);
        scrollView = view.findViewById(R.id.scrolll);
        frameLayout = view.findViewById(R.id.progressBarFrame);

        makeRequest(getContext());
//        updateTable(view);
//        frameLayout.setVisibility(View.INVISIBLE);
//        NavHostFragment.findNavController(DashBoard3Fragment.me).navigate(R.id.action_SummaryTable_to_DistributorTable);


        return view;
    }


    //    @SuppressLint("HandlerLeak")
//    private void updateTable(View view) {
//
//        Log.i("Update", "I AM UPDATING");
//
//        tableRowsHandler = new Handler() {
//            @SuppressLint("SetTextI18n")
//            @Override
//            public void handleMessage(@NonNull Message msg) {
//                String message = (String) msg.obj;
//                Integer index = Integer.parseInt(message);
//                if (index != null) {
//                    editTableValues(view, getContext());
//                }
//            }
//        };
//
//        tableRowThread = new TableRowThread();
//        tableRowThread.start();
//    }

//    private void editTableValues(View view, Context context) {
//
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
//            if (response != null) {
//                try {
//                    frameLayout.setVisibility(View.GONE);
//                    ArrayList<Table> tablesToDisplay = getTableDataFromRequestBody(response);
//                    setUpdatedTables(tablesToDisplay, view);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, error -> Log.i("TAG-error", error + ""));
//        requestQueue.add(jsonArrayRequest);
//        Util.retryRequest(jsonArrayRequest);
//    }


//    private void setUpdatedTables(ArrayList<Table> editedTableData, View view) {
//        TableLayout tableLayout = view.findViewById(R.id.tableLayout);
//        Thread tThread = new Thread(() -> {
//            for (int i = -1; i < editedTableData.size(); i++) {
//                int finalI = i;
//                requireActivity().runOnUiThread(new Runnable() {
//                    @SuppressLint("HandlerLeak")
//                    @Override
//                    public void run() {
//                        if (finalI >= 0 && editedTableData.size() > 0) {
//                            TableRow tableRow = (TableRow) tableLayout.getChildAt(finalI);
//                            if (tableRow != null) {
//                                TextView textViewVSIcount = tableRow.findViewById(R.id.tVSIcount);
//                                TextView textViewActives = tableRow.findViewById(R.id.tactive);
//                                TextView textViewProspects = tableRow.findViewById(R.id.tProspect);
//                                TextView textViewOutlets = tableRow.findViewById(R.id.tOutlets);
//                                TextView textViewSKUcount = tableRow.findViewById(R.id.tSKUcount);
//                                TextView textViewTotalQty = tableRow.findViewById(R.id.tQty);
//                                TextView textViewTotalSales = tableRow.findViewById(R.id.tTotalSales);
//
//                                numberFormat = NumberFormat.getInstance();
//                                numberFormat.setGroupingUsed(true);
//                                int quantityFormatted = editedTableData.get(finalI).quantityCount + 5;
//                                double totalFormatted = editedTableData.get(finalI).totalSalesAmountAfterTax + 108254;
//                                textViewVSIcount.setText(String.valueOf(editedTableData.get(finalI).vsiCount + 2));
//                                textViewActives.setText(String.valueOf(editedTableData.get(finalI).active + 2));
//                                textViewProspects.setText(String.valueOf(editedTableData.get(finalI).prospect + 3));
//                                textViewOutlets.setText(String.valueOf(editedTableData.get(finalI).salesOutLateCount + 5));
//                                textViewSKUcount.setText(String.valueOf(editedTableData.get(finalI).skuCount + 9));
//                                textViewTotalQty.setText(numberFormat.format(quantityFormatted));
//                                textViewTotalSales.setText(numberFormat.format(totalFormatted));
//                            }
//
//
//                        }
//                    }
//
//                });
//
//                try {
//                    if (finalI == -1) {
//                        Thread.sleep(20000);
//                    }
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        });
//        tThread.start();
//    }

    private void makeRequest(Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            if (response != null) {
                try {
                    frameLayout.setVisibility(View.GONE);
                    ArrayList<Table> tablesToDisplay = getTableDataFromRequestBody(response);
                    if (tablesToDisplay.size() > 0)
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

            for (int i = 0; i <= tables.size(); i++) {
                int finalI = i;
                requireActivity().runOnUiThread(new Runnable() {
                    @SuppressLint("HandlerLeak")
                    @Override
                    public void run() {

                        View tableElements = LayoutInflater.from(getContext()).inflate(R.layout.table_elements, null, false);
                        TextView textViewSN = tableElements.findViewById(R.id.tVsN);
                        TextView textViewDistributor = tableElements.findViewById(R.id.tdistributor);
                        TextView textViewVSIcount = tableElements.findViewById(R.id.tVSIcount);
                        TextView textViewActives = tableElements.findViewById(R.id.tactive);
                        TextView textViewProspects = tableElements.findViewById(R.id.tProspect);
                        TextView textViewStartT = tableElements.findViewById(R.id.tStartTime);
                        TextView textViewLastActive = tableElements.findViewById(R.id.tLastSeen);
                        TextView textViewOutlets = tableElements.findViewById(R.id.tOutlets);
                        TextView textViewSKUcount = tableElements.findViewById(R.id.tSKUcount);
                        TextView textViewTotalQty = tableElements.findViewById(R.id.tQty);
                        TextView textViewTotalSales = tableElements.findViewById(R.id.tTotalSales);

                        View view = getActivity().findViewById(R.id.nav_host_fragment);
                        Fragment navhost = FragmentManager.findFragment(view);

                        if (finalI == tables.size()) {
                            NavHostFragment.findNavController(navhost).navigate(R.id.DistributorTable);
                        } else {
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
                            textViewSN.setText(String.valueOf(finalI + 1));
                            textViewDistributor.setText(preciseOrgName);
                            textViewVSIcount.setText(String.valueOf(tables.get(finalI).vsiCount));
                            textViewActives.setText(String.valueOf(tables.get(finalI).active));
                            textViewProspects.setText(String.valueOf(tables.get(finalI).prospect));
                            textViewStartT.setText(tables.get(finalI).startTime);
                            textViewLastActive.setText(tables.get(finalI).lastSeen);
                            textViewOutlets.setText(String.valueOf(tables.get(finalI).salesOutLateCount));
                            textViewSKUcount.setText(String.valueOf(tables.get(finalI).skuCount));
                            textViewTotalQty.setText(numberFormat.format(quantityCount));
                            textViewTotalSales.setText(numberFormat.format(totalSalesAmountAfterTax));

                            TableRow tableRow = tableElements.findViewById(R.id.tableRow);
                            tableLayout.addView(tableElements);

                            Util.animate(tableLayout, tableRow);
                        }

                    }

                });

                try {
                    Thread.sleep(1000);
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

//class TableRowThread extends Thread {
//
//
//    @Override
//    public void run() {
//        for (int i = 0; i < 2; i++) {
//            Message msg = SummaryTable.tableRowsHandler.obtainMessage();
//            msg.obj = String.valueOf(i);
//            SummaryTable.tableRowsHandler.sendMessage(msg);
//
//            try {
//
//                if (i == 1) {
//                    Thread.sleep(10000);
//                    Log.i("current_dest", NavHostFragment.findNavController(SummaryTable.me).getCurrentDestination() + "");
//
//
//                    SummaryTable.activity.runOnUiThread(new Runnable() {
//
//                        @SuppressLint("RestrictedApi")
//                        @Override
//                        public void run() {
//                            Log.i("current_dest", NavHostFragment.findNavController(SummaryTable.me).getCurrentDestination() + "");
//
//
//                            if (NavHostFragment.findNavController(SummaryTable.me).getCurrentDestination().getId() == R.id.vsmCardFragment) {
////                                NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.vsmCardFragment, true).build();
////                                NavHostFragment.findNavController(SummaryTable.me).navigate(R.id.DistributorTable,null, navOptions);
//                                NavHostFragment.findNavController(SummaryTable.me).popBackStack();
//                                Log.i("current_dest after pop", NavHostFragment.findNavController(SummaryTable.me).getCurrentDestination() + "");
//                            } else {
//                                NavHostFragment.findNavController(SummaryTable.me).navigate(R.id.action_SummaryTable_to_DistributorTable);
//
//                            }
//                        }
//                    });
//
//                } else {
//                    Thread.sleep(10000);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}

