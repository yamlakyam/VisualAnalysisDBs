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


public class vsmTransactionFragment extends Fragment {

    TableLayout tableLayout;
    FrameLayout frameLayout;
    ScrollView scrollView;
    TextView distributorTextView;
    static Activity activity;
    static Fragment me;

    String url = "http://192.168.1.248:8001/api/ChartData/GetSalesDataToDisplayVsmCards/GetSalesDataToDisplayOnVsmTable";

    public static Handler VsmTableRowsHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vsm_transaction, container, false);

        tableLayout = view.findViewById(R.id.VSMtableLayout);
        frameLayout = view.findViewById(R.id.VSM_progressBar_frame);
        scrollView = view.findViewById(R.id.VSMscroll);
        activity = getActivity();
        me = this;
        distributorTextView = view.findViewById(R.id.distributorHeaderVsmTransaction);

        makeRequest(getContext());

//        initRow();
        View tableElements = LayoutInflater.from(getContext()).inflate(R.layout.vsm_table_element, null, false);
        tableLayout.addView(tableElements);

        return view;
    }

    private void makeRequest(Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            if (response != null) {
                try {
                    frameLayout.setVisibility(View.GONE);

                    JSONObject jsonObject = response.getJSONObject(0);
                    String name = jsonObject.getString("nameOfOrg");

                    distributorTextView.setText(name);


                    JSONArray jsonArray = jsonObject.getJSONArray("vsmCards");


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

    void initTable(ArrayList<Table> tables) {
        addRowsSequentially(tables);
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

    public ArrayList<Table> getTableDataFromRequestBody(JSONArray tables) throws JSONException {
        ArrayList<Table> parsedTables = new ArrayList<>();
        for (int i = 0; i < tables.length(); i++) {
            JSONObject jsonObject = tables.getJSONObject(i);

            double grandTotal = jsonObject.getDouble("totalSalesAmount");
            double roundedGrandTotal = Math.round(grandTotal * 100.0) / 100.0;

            Table tableRow = new Table(
                    jsonObject.getString("vsm"),
                    jsonObject.getInt("salesOutLateCount"),
                    jsonObject.getString("lastActive"),
                    jsonObject.getInt("allLineItemCount"),
                    roundedGrandTotal
            );

            parsedTables.add(tableRow);
        }
        return parsedTables;
    }


    private void initRow(int finalI, ArrayList<Table> tableList) {
        View tableElements = LayoutInflater.from(getContext()).inflate(R.layout.vsm_table_element, null, false);
        TextView textView1 = tableElements.findViewById(R.id.vsmElement1);
        TextView textView2 = tableElements.findViewById(R.id.vsmElement2);
        TextView textView3 = tableElements.findViewById(R.id.vsmElement3);
        TextView textView4 = tableElements.findViewById(R.id.vsmElement4);
        TextView textView5 = tableElements.findViewById(R.id.vsmElement5);
        TextView textView6 = tableElements.findViewById(R.id.vsmElement6);
        TextView textView7 = tableElements.findViewById(R.id.vsmElement7);
        TextView textView8 = tableElements.findViewById(R.id.vsmElement8);
        TextView textView9 = tableElements.findViewById(R.id.vsmElement9);


        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setGroupingUsed(true);

        textView1.setText(String.valueOf(tableList.get(finalI).sN));
        textView2.setText(String.valueOf(tableList.get(finalI).voucherN));
        textView3.setText(String.valueOf(tableList.get(finalI).salesOutLateCount));
        textView4.setText(String.valueOf(tableList.get(finalI).TIN));
        textView5.setText(String.valueOf(tableList.get(finalI).startTime));
        textView6.setText(String.valueOf(tableList.get(finalI).itemCount));
        textView7.setText(numberFormat.format(tableList.get(finalI).subTotal));
        textView8.setText(String.valueOf(tableList.get(finalI).VAT));
        textView9.setText(numberFormat.format(tableList.get(finalI).totalSalesAmountAfterTax));
        distributorTextView.setText(String.valueOf(tableList.get(finalI).organizationName));

        TableRow tableRow = tableElements.findViewById(R.id.VSMtableRow);
        tableLayout.addView(tableElements);

        Util.animate(tableLayout, tableRow);
    }
}

class VsmTransactionThread extends Thread {
    int distributordataSize;

    VsmTransactionThread(int distributordataSize) {
        this.distributordataSize = distributordataSize;
    }

    @Override
    public void run() {

        for (int i = 0; i < distributordataSize; i++) {
            Message msg = vsmTransactionFragment.VsmTableRowsHandler.obtainMessage();
            msg.obj = String.valueOf(i);
            vsmTransactionFragment.VsmTableRowsHandler.sendMessage(msg);

            try {
                if (i >= 2) {
                    Thread.sleep(10000);
                    vsmTransactionFragment.activity.runOnUiThread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    Log.i("current_dest", NavHostFragment.findNavController(vsmTransactionFragment.me).getCurrentDestination() + "");
//                                    if (NavHostFragment.findNavController(DashBoard4Fragment.me).getCurrentDestination().getId() != R.id.vsmCardFragment) {
//                                        NavHostFragment.findNavController(DashBoard4Fragment.me).navigate(R.id.action_dashBoard4Fragment_to_vsmCardFragment2);
//
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