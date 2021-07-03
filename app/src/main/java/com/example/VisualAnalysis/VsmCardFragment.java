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
import android.widget.GridView;

import androidx.annotation.NonNull;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class VsmCardFragment extends Fragment {

    GridView VSMcardGridView;
    public static Fragment me;

    ArrayList<Table> vsmCardList;

    String url = "http://192.168.1.248:8001/api/ChartData/GetSalesDataForSingleVan";
    static Handler vsmCardChangeHandler;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_vsm_card, container, false);
        VSMcardGridView = view.findViewById(R.id.vsmCardGridLayout);
        me = this;

        vsmCardList = new ArrayList<>();


        makeRequest(getContext());
        Thread gThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    int finalI = i;
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            VSMcardGridView.smoothScrollToPosition(finalI);
                        }
                    });
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (finalI == vsmCardList.size() - 1) {
                        Log.i("TAG", vsmCardList.size() + "");
//                        NavHostFragment.findNavController(VsmCardFragment.me).navigate(R.id.action_vsmCardFragment_to_mapActivity);
                    }
                }
            }
        });
        gThread.start();

        updateVsmCard(getContext());

        return view;

    }

    @SuppressLint("HandlerLeak")
    private void updateVsmCard(Context context) {
        vsmCardChangeHandler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                String message = (String) msg.obj;
                Integer index = Integer.parseInt(message);
                if (index != null) {
                    initUpdatingVsmCard(context,index);
                }
            }
        };

        Thread vsmCardThread = new vsmCardThread(3);
        vsmCardThread.start();


    }

    private void makeRequest(Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            if (response != null) {
                try {
                    JSONObject jsonObject = response.getJSONObject(0);
                    initVsmCardList(jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> {
            Log.i("TAG-error", error + "");
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


    private void initVsmCardList(JSONObject jsonObject) throws JSONException {
        JSONArray jsonArray = jsonObject.getJSONArray("cardDataForEachOrganization");
        vsmCardList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length() ; i++) {
            JSONObject cardData = jsonArray.getJSONObject(i);

            Date lastActive = formatTime(cardData.getString("lastActive"));
            Date currentTime = Calendar.getInstance().getTime();


            String formattedLastSeen = timeElapsed(lastActive, currentTime);

            vsmCardList.add(new Table(cardData.getString("vsm"),
                    cardData.getInt("salesOutLateCount"),
                    formattedLastSeen,
                    cardData.getInt("allLineItemCount"),
                    cardData.getDouble("totalSalesAmount")
            ));


        }
        VSMcardGVAdapter adapter = new VSMcardGVAdapter(requireContext(), vsmCardList);
        VSMcardGridView.setAdapter(adapter);
    }

    private Date formatTime(String lastActive) {
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date currentTime = Calendar.getInstance().getTime();

        Date parsed = null;
        try {
            parsed = input.parse(lastActive);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parsed;
    }


    private String timeElapsed(Date startDate, Date endDate) {
        long difference = endDate.getTime() - startDate.getTime();
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        long monthsInMilli = daysInMilli * 30;

        long elapsedMonths = difference / monthsInMilli;
        difference = difference % monthsInMilli;
        long elapsedDays = difference / daysInMilli;
        difference = difference % daysInMilli;
        long elapsedHours = difference / hoursInMilli;
        difference = difference % hoursInMilli;
        long elapsedMinutes = difference / minutesInMilli;
        difference = difference % minutesInMilli;

        long elapsedSeconds = difference / secondsInMilli;
        if (elapsedMonths > 7) {
            return " long ago";
        } else if (elapsedMonths > 0) {
            return elapsedMonths + " months ago";
        } else if (elapsedMonths == 0 && elapsedDays > 0) {
            return elapsedDays + " days ago";
        } else if (elapsedDays == 0 && elapsedHours > 0) {
            return elapsedHours + " hours ago";
        } else if (elapsedDays == 0 && elapsedHours == 0 & elapsedMinutes > 0) {
            return elapsedMinutes + " minutes ago";
        } else if (elapsedDays == 0 && elapsedHours == 0 & elapsedMinutes == 0 && elapsedSeconds > 0) {
            return elapsedSeconds + " seconds ago";
        } else
//            return elapsedDays + " days, " + elapsedHours + " hours, " + elapsedMinutes + " minutes, " + elapsedSeconds + " seconds ago";
            return elapsedSeconds + "";
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

            try {
                if(n==2){
                    Thread.sleep(20000);
                    NavHostFragment.findNavController(VsmCardFragment.me).navigate(R.id.action_vsmCardFragment_to_mapActivity);
                }
                Thread.sleep(20000);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


