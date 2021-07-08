package com.example.VisualAnalysis;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;

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

public class VsmCardFragment extends Fragment {

    GridView VSMcardGridView;
    FrameLayout frameLayout;
    static Activity activity;

    public static Fragment me;
    ArrayList<Table> vsmCardList;
    String url = "http://192.168.1.248:8001/api/ChartData/GetSalesDataToDisplayVsmCards";
    static Handler vsmCardChangeHandler;
    public static Context context;

    Thread vsmCardThread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();

    }

    @Override
    public void onStop() {
        super.onStop();
        vsmCardThread.interrupt();
    }

    @Override
    public void onPause() {
        super.onPause();
        vsmCardThread.interrupt();

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_vsm_card, container, false);
        VSMcardGridView = view.findViewById(R.id.vsmCardGridLayout);
        frameLayout = view.findViewById(R.id.vsmCardFrameLayout);
        me = this;
        activity = getActivity();

        makeRequest(getContext());
        updateVsmCard(getContext());

        return view;
    }

    private void scrollCard(int cards) {

        Thread gThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < cards; i++) {
                    int finalI = i;
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            VSMcardGridView.smoothScrollToPosition(finalI);
                        }
                    });
                    try {
                        Thread.sleep(700);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        gThread.start();
    }

    @SuppressLint("HandlerLeak")
    private void updateVsmCard(Context context) {
        vsmCardChangeHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                String message = (String) msg.obj;
                Integer index = Integer.parseInt(message);
                if (index != null) {
                    initUpdatingVsmCard(context, index);
                }
            }
        };
        vsmCardThread = new vsmCardThread(3);
        vsmCardThread.start();

    }

    private void makeRequest(Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            if (response != null) {
                frameLayout.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = response.getJSONObject(0);
                    initVsmCardList(jsonObject);

                } catch (JSONException e) {
                    frameLayout.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }
        }, error -> {
            Log.i("TAG-error", error + "");
        });

        requestQueue.add(jsonArrayRequest);
        Util.retryRequest(jsonArrayRequest);
    }

    private void initUpdatingVsmCard(Context context, int index) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            if (response != null) {
                try {
                    JSONObject jsonObject = response.getJSONObject(index);
                    initVsmCardList(jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> {
            Log.i("TAG-error", error + "");
        });

        requestQueue.add(jsonArrayRequest);
        Util.retryRequest(jsonArrayRequest);
    }


    private void initVsmCardList(JSONObject jsonObject) throws JSONException {
        JSONArray jsonArray = jsonObject.getJSONArray("vsmCards");
        String distributor = jsonObject.getString("nameOfOrg");


        vsmCardList = new ArrayList<>();
        int vsmCards = jsonObject.getJSONArray("vsmCards").length();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject cardData = jsonArray.getJSONObject(i);

            Date lastActive = Util.formatTime(cardData.getString("lastActive"));
            Date currentTime = Calendar.getInstance().getTime();

            String preciseOrgName;

            if (distributor.length() > 20)
                preciseOrgName = distributor.substring(0, 10) + "...";
            else
                preciseOrgName = distributor;

            String formattedLastSeen = Util.timeElapsed(lastActive, currentTime);

            NumberFormat numberFormat = NumberFormat.getInstance();
            numberFormat.setGroupingUsed(true);
            double totalSales = cardData.getDouble("totalSalesAmount");
            vsmCardList.add(new Table(cardData.getString("vsm"),
                    cardData.getInt("salesOutLateCount"),
                    formattedLastSeen,
                    cardData.getInt("allLineItemCount"),
                    numberFormat.format(Math.round(totalSales * 100.0) / 100.0),
                    preciseOrgName
            ));

        }
        VSMcardGVAdapter adapter = new VSMcardGVAdapter(requireContext(), vsmCardList);
        VSMcardGridView.setAdapter(adapter);
        scrollCard(vsmCards);
    }
}

class vsmCardThread extends Thread {

    private final int n;

    public vsmCardThread(int n) {
        this.n = n;
    }

    @Override
    public void run() {
        for (int i = 0; i < n; i++) {
            Message msg = VsmCardFragment.vsmCardChangeHandler.obtainMessage();
            msg.obj = String.valueOf(i);
            VsmCardFragment.vsmCardChangeHandler.sendMessage(msg);
//            Log.i("last list of cards",i+"" );

            try {
                if (i == 2) {
                    Thread.sleep(10000);
                    Log.i("current frag", NavHostFragment.findNavController(VsmCardFragment.me).getCurrentDestination() + "");
                    VsmCardFragment.activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(VsmCardFragment.context, MapActivity.class);
                            VsmCardFragment.context.startActivity(intent);
                        }
                    });

                } else {
                    Thread.sleep(10000);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


