package com.example.VisualAnalysis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;


public class DashBoard3Fragment extends Fragment {

    private static TableLayout tableLayout;
    //    TableLayout tableLayout;
    public static ArrayList<Table> tables;
    private static int paddingPixel;

    static ScrollView scrollView;

    static int widthDp;
    static int heightDp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflater.getContext().setTheme(R.style.darkTheme);


        View view = inflater.inflate(R.layout.fragment_dash_board3, container, false);


        tableLayout = view.findViewById(R.id.table);
        scrollView = view.findViewById(R.id.scrollView);


        //conversion to dp
        int paddingDp = 10;
        widthDp = 70;
        heightDp = 50;
        float density = getResources().getDisplayMetrics().density;
        paddingPixel = (int) (paddingDp * density);
        widthDp = (int) (widthDp * density);
        heightDp = (int) (heightDp * density);


        tables = new ArrayList<>();
        tables.add(new Table("Credit", "0%", "0", new TableRow(getContext())));
        tables.add(new Table("Admin ", "56%", "2566", new TableRow(getContext())));
        tables.add(new Table("Sales ", "24%", "1940", new TableRow(getContext())));
        tables.add(new Table("Sthg", "5%", "250", new TableRow(getContext())));
        tables.add(new Table("Credit", "0%", "0", new TableRow(getContext())));
        tables.add(new Table("Admin ", "56%", "2566", new TableRow(getContext())));
        tables.add(new Table("Sales ", "24%", "1940", new TableRow(getContext())));
        tables.add(new Table("Sthg", "5%", "250", new TableRow(getContext())));
        tables.add(new Table("Credit", "0%", "0", new TableRow(getContext())));
        tables.add(new Table("Admin ", "56%", "2566", new TableRow(getContext())));
        tables.add(new Table("Sales ", "24%", "1940", new TableRow(getContext())));
        tables.add(new Table("Sthg", "5%", "250", new TableRow(getContext())));
        tables.add(new Table("Credit", "0%", "0", new TableRow(getContext())));
        tables.add(new Table("Admin ", "56%", "2566", new TableRow(getContext())));
        tables.add(new Table("Sales ", "24%", "1940", new TableRow(getContext())));
        tables.add(new Table("Sthg", "5%", "250", new TableRow(getContext())));
        tables.add(new Table("Credit", "0%", "0", new TableRow(getContext())));
        tables.add(new Table("Admin ", "56%", "2566", new TableRow(getContext())));
        tables.add(new Table("Sales ", "24%", "1940", new TableRow(getContext())));
        tables.add(new Table("Sthg", "5%", "250", new TableRow(getContext())));
        tables.add(new Table("Credit", "0%", "0", new TableRow(getContext())));
        tables.add(new Table("Admin ", "56%", "2566", new TableRow(getContext())));
        tables.add(new Table("Sales ", "24%", "1940", new TableRow(getContext())));
        tables.add(new Table("Sthg", "5%", "250", new TableRow(getContext())));
        tables.add(new Table("Credit", "0%", "0", new TableRow(getContext())));
        tables.add(new Table("Admin ", "56%", "2566", new TableRow(getContext())));
        tables.add(new Table("Sales ", "24%", "1940", new TableRow(getContext())));
        tables.add(new Table("Sthg", "5%", "250", new TableRow(getContext())));


        Thread tThread = new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < DashBoard3Fragment.tables.size(); i++) {
                    int finalI = i;
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView txtv1 = new TextView(getContext());
                            TextView txtv2 = new TextView(getContext());
                            TextView txtv3 = new TextView(getContext());
                            txtv1.setText(DashBoard3Fragment.tables.get(finalI).branchName);
                            txtv2.setText(DashBoard3Fragment.tables.get(finalI).percentage);
                            txtv3.setText(DashBoard3Fragment.tables.get(finalI).total);
                            //            initTable(tables.get(i).tableRow, new TextView(this).setText(tables.get(i).branchName),new TextView(this).setText(tables.get(i).percentage),new TextView(this).setText(tables.get(i).total));
                            DashBoard3Fragment.initTable(DashBoard3Fragment.tables.get(finalI).tableRow, txtv1, txtv2, txtv3, getContext());
                            Log.i("TAG", tables.get(finalI).branchName);
                        }
                    });


//                    View child = tableLayout.getChildAt(1);
//                    scrollView.scrollTo(child.getTop(),View.FOCUS_DOWN);


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

            }
        });
        tThread.start();

//        View child =tableLayout.getChildAt(5);
//        scrollView.scrollTo(0,child.getTop());

        return view;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    static void initTable(TableRow tr, TextView tV1, TextView tV2, TextView tV3, Context context) {
        tr.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tr.setBackgroundColor(Color.parseColor("#49515c"));
        tV1.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f));
        tV2.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f));
        tV3.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f));
        tV2.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
        tV3.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
        tV3.setPadding(0, 0, paddingPixel, 0);
        tV1.setPadding(paddingPixel, 0, 0, 0);
        tV1.setTextColor(Color.parseColor("#d2b566"));
        if (Double.parseDouble((tV2.getText().toString()).substring(0, tV2.getText().toString().length() - 1)) < 50) {
            tV2.setTextColor(Color.parseColor("#EA6F6A"));
        } else
            tV2.setTextColor(Color.parseColor("#78FA5A"));

//        Log.i("TAG",tV2.getText().toString());
//            tV2.setTextColor(Color.parseColor("#78FA5A"));

        tV3.setTextColor(Color.parseColor("#78FA5A"));

        tV1.setTextSize(20);
        tV2.setTextSize(20);
        tV3.setTextSize(20);

        ImageView imageView = new ImageView(tV1.getContext());
        Drawable drawable = context.getResources().getDrawable(R.drawable.ic_heineken);
        imageView.setImageDrawable(drawable);

        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(widthDp, heightDp);
        imageView.setLayoutParams(ll);
        tV1.setGravity(Gravity.CENTER_VERTICAL);


        LinearLayout linearLayout = new LinearLayout(tV1.getContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.addView(imageView);
        linearLayout.addView(tV1);
        linearLayout.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f));
//        linearLayout.setGravity(Gravity.CENTER|Gravity.CENTER_VERTICAL);

        tr.addView(linearLayout);
//        tr.addView(tV1);
        tr.addView(tV2);
        tr.addView(tV3);

        tableLayout.addView(tr);
        Animation animation1 = AnimationUtils.loadAnimation(tableLayout.getContext(), R.anim.slide_in_bottom);
        Animation animation2 = AnimationUtils.loadAnimation(tableLayout.getContext(), R.anim.slide_out_bottom);
        tr.startAnimation(animation2);
    }
}