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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.security.auth.login.LoginException;


public class DashBoard3Fragment extends Fragment {

    private TableLayout tableLayout;
    int cellWidth;

    FrameLayout frameLayout;

    public static ArrayList<Table> tables = new ArrayList<>();
    public static Handler tableRowHandler;


    ScrollView scrollView;
    String url = "http://192.168.1.248:8001/api/ChartData/GetTotalSalesDataByOrganization";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflater.getContext().setTheme(R.style.darkTheme);


        View view = getLayoutInflater().inflate(R.layout.fragment_dash_board3, container, false);
        tableLayout = view.findViewById(R.id.tableLayout);

        cellWidth = (getResources().getDisplayMetrics().widthPixels) / 7;

        scrollView = view.findViewById(R.id.scrolll);
        frameLayout = view.findViewById(R.id.progressBarFrame);

        Table tableRow1 = new Table("CNET", 10, 455, 15, 100, 500000);
        Table tableRow2 = new Table("CNET", 10, 455, 15, 100, 500000);

        tables.add(tableRow1);
        tables.add(tableRow2);
        tables.add(tableRow2);


        makeRequest(getContext());
//        initTable(tables);


        return view;
    }


    @SuppressLint("HandlerLeak")
    private void updateTable(){
        tableLayout.removeAllViews();
//        tableRowHandler = new Handler() {
//            @Override
//            public void handleMessage(@NonNull Message msg) {
//                String message = (String) msg.obj;
//                int index = Integer.parseInt(message);
//                initTable(tables);
//            }
//        };
//
//        TableRowThread tableRowThread = new TableRowThread();
//        tableRowThread.start();

    }

    private void makeRequest(Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            if (response != null) {
                try {
                    frameLayout.setVisibility(View.GONE);
                    ArrayList<Table> tablesToDisplay = getTableDataFromRequestBody(response);
                    initTable(tablesToDisplay);
                    updateTable();
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> Log.i("TAG-error", error + ""));
        requestQueue.add(jsonArrayRequest);
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

                        TextView textView1 = tableElements.findViewById(R.id.tvalue1);
                        TextView textView2 = tableElements.findViewById(R.id.tvalue2);
                        TextView textView3 = tableElements.findViewById(R.id.tvalue3);
                        TextView textView4 = tableElements.findViewById(R.id.tvalue4);
                        TextView textView5 = tableElements.findViewById(R.id.tvalue5);
                        TextView textView6 = tableElements.findViewById(R.id.tvalue6);

                        String orgName = tables.get(finalI).organizationName;
                        String preciseOrgName;
                        if (orgName.length() > 20)
                            preciseOrgName = orgName.substring(0, 20) + "...";
                        else
                            preciseOrgName = orgName;

                        textView1.setText(preciseOrgName);
                        textView2.setText(String.valueOf(tables.get(finalI).vsiCount));
                        textView3.setText(String.valueOf(tables.get(finalI).salesOutLateCount));
                        textView4.setText(String.valueOf(tables.get(finalI).skuCount));
                        textView5.setText(String.valueOf(tables.get(finalI).quantityCount));
                        textView6.setText(String.valueOf(tables.get(finalI).totalSalesAmountAfterTax));


                        TableRow tableRow = tableElements.findViewById(R.id.tableRow);
                        tableLayout.addView(tableElements);

                        Animation animation1 = AnimationUtils.loadAnimation(tableLayout.getContext(), R.anim.slide_in_bottom);
                        Animation animation2 = AnimationUtils.loadAnimation(tableLayout.getContext(), R.anim.slide_out_bottom);
                        tableRow.startAnimation(animation2);

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
                    tableObject.getInt("vsiCount"),
                    tableObject.getInt("salesOutLateCount"),
                    tableObject.getInt("skuCount"),
                    tableObject.getInt("quantityCount"),
                    roundedGrandTotal
            );
            parsedTableData.add(tableRow);
        }
        return parsedTableData;
    }

}


class TableRowThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 2; i++) {
            Message msg = DashBoard3Fragment.tableRowHandler.obtainMessage();
            msg.obj = String.valueOf(i);
            DashBoard3Fragment.tableRowHandler.sendMessage(msg);

            try {
                Thread.sleep(30000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}