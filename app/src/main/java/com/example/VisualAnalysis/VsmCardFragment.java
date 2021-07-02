package com.example.VisualAnalysis;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;

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

import java.util.ArrayList;


public class VsmCardFragment extends Fragment {

    GridView VSMcardGridView;
    public static Fragment me;

    ArrayList<Table> vsmCardList;

    String url ="http://192.168.1.248:8001/api/ChartData/GetSalesDataForSingleVan";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_vsm_card, container, false);
        VSMcardGridView = view.findViewById(R.id.vsmCardGridLayout);
        me = this;

        vsmCardList = new ArrayList<>();


        makeRequest(getContext());



//        vsmCardList.add(new Table("VAN1", 20, "20 mins ago", 20, 20098.67));
//        vsmCardList.add(new Table("VAN2", 25, "30 mins ago", 25, 2098.87));
//        vsmCardList.add(new Table("VAN3", 10, "10 mins ago", 29, 200098.68));
//        vsmCardList.add(new Table("VAN4", 15, "15 mins ago", 15, 20098.57));
//        vsmCardList.add(new Table("VAN5", 7, "25 mins ago", 26, 95098.68));
//        vsmCardList.add(new Table("VAN6", 8, "12 mins ago", 10, 208698.97));
//        vsmCardList.add(new Table("VAN7", 8, "5 mins ago", 10, 208698.97));
//        vsmCardList.add(new Table("VAN8", 8, "2 hrs ago", 10, 208698.97));
//        vsmCardList.add(new Table("VAN9", 8, "4 hrs ago", 10, 208698.97));
//        vsmCardList.add(new Table("VAN10", 8, "5 hrs ago", 10, 208698.97));




        Thread gThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < vsmCardList.size(); i++) {
                    int finalI = i;
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            VSMcardGridView.smoothScrollToPosition(finalI);
                        }
                    });
                    try {
                        Thread.sleep(3000);
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
        return view;

    }

    private void makeRequest(Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            if (response != null) {
                try {
                    JSONObject jsonObject=response.getJSONObject(0);
                    JSONArray jsonArray=jsonObject.getJSONArray("cardDataForEachOrganization");

                    for (int i=0; i<jsonArray.length()-1;i++){
                        JSONObject cardData =jsonArray.getJSONObject(i);

//                        Log.i("cardData", cardData+"");

                        vsmCardList.add(new Table(cardData.getString("vsm"),
                                cardData.getInt("salesOutLateCount"),
                                cardData.getString("lastActive"),
                                cardData.getInt("allLineItemCount"),
                                cardData.getDouble("totalSalesAmount")
                                ));


                    }
                    VSMcardGVAdapter adapter = new VSMcardGVAdapter(requireContext(), vsmCardList);
                    VSMcardGridView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                    ArrayList<ArrayList<Table>> tablesToDisplay = getTableDataFromRequestBody(response);

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
}


