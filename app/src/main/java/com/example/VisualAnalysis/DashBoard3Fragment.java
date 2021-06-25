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
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;


public class DashBoard3Fragment extends Fragment {

    private static TableLayout tableLayout;
    //    TableLayout tableLayout;
    public static ArrayList<Table> tables;
    private static int paddingPixel;

    static ScrollView scrollView;

    static int widthDp;
    static int heightDp;

    private static ImageView imageView;
    private static TextView textView1;
    private static TextView textView2;
    private static TextView textView3;
    private static TextView textView4;
    private static TextView textView5;
    private static TextView textView6;
    private static TableRow tableRow;
    private static LinearLayout linearLayout;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflater.getContext().setTheme(R.style.darkTheme);


        View view = inflater.inflate(R.layout.fragment_dash_board3, container, false);

        LinearLayout tableElements =(LinearLayout) getLayoutInflater().inflate(R.layout.table_elements,null,false);

        tableLayout = view.findViewById(R.id.tableLayout);
//        textView1 = view.findViewById(R.id.tvalue1);
//        textView1=(TextView)getLayoutInflater().inflate(R.id.tvalue1,null);
       textView1=tableElements.findViewById(R.id.tvalue1);
       textView2=tableElements.findViewById(R.id.tvalue2);
       textView3=tableElements.findViewById(R.id.tvalue3);
       textView4=tableElements.findViewById(R.id.tvalue4);
       textView5=tableElements.findViewById(R.id.tvalue5);
       textView6=tableElements.findViewById(R.id.tvalue6);
       tableRow=tableElements.findViewById(R.id.tableRow);
       scrollView=view.findViewById(R.id.scrolll);
       linearLayout=tableElements.findViewById(R.id.tableElementLayout);


//        linearLayout = view.findViewById(R.id.linearLayout1);






        //conversion to dp
        int paddingDp = 10;
        widthDp = 70;
        heightDp = 50;
        float density = getResources().getDisplayMetrics().density;
        paddingPixel = (int) (paddingDp * density);
        widthDp = (int) (widthDp * density);
        heightDp = (int) (heightDp * density);

        Date now = new Date();


        tables = new ArrayList<>();
        tables.add(new Table("Credit", "0%", "0", new TableRow(getContext()), new Date(2020 - 1900, 6 - 1, 1, 9, 10), "Monday", timeElapsed(new Date(2020 - 1900, 6 - 1, 1, 9, 10), now)));
        tables.add(new Table("Credit", "0%", "0", new TableRow(getContext()), new Date(2020 - 1900, 6 - 1, 1, 9, 10), "Monday", timeElapsed(new Date(2021 - 1900, 5 - 1, 1, 9, 10), now)));
        tables.add(new Table("Credit", "0%", "0", new TableRow(getContext()), new Date(2020 - 1900, 6 - 1, 1, 9, 10), "Monday", timeElapsed(new Date(2021 - 1900, 6 - 1, 1, 9, 10), now)));
        tables.add(new Table("Credit", "0%", "0", new TableRow(getContext()), new Date(2020 - 1900, 6 - 1, 1, 9, 10), "Monday", timeElapsed(new Date(2021 - 1900, 6 - 1, 25, 9, 10), now)));
        tables.add(new Table("Credit", "0%", "0", new TableRow(getContext()), new Date(2020 - 1900, 6 - 1, 1, 9, 10), "Monday", timeElapsed(new Date(2021 - 1900, 6 - 1, 25, 12, 10), now)));
        tables.add(new Table("Admin ", "56%", "2566", new TableRow(getContext()), new Date(2020 - 1900, 5 - 1, 2, 10, 4), "Monday", timeElapsed(new Date(2020 - 1900, 5 - 1, 2, 10, 4), now)));
        tables.add(new Table("Sales ", "24%", "1940", new TableRow(getContext()), new Date(2020 - 1900, 4 - 1, 9, 10, 4), "Monday", timeElapsed(new Date(2020 - 1900, 4 - 1, 9, 10, 4), now)));
        tables.add(new Table("Sthg", "5%", "250", new TableRow(getContext()), new Date(2020 - 1900, 6 - 1, 3, 10, 4), "Monday", timeElapsed(new Date(2020 - 1900, 6 - 1, 3, 10, 4), now)));
        tables.add(new Table("Credit", "0%", "0", new TableRow(getContext()), new Date(2020 - 1900, 5 - 1, 1, 10, 4), "Monday", timeElapsed(new Date(2020 - 1900, 5 - 1, 1, 10, 4), now)));
        tables.add(new Table("Admin ", "56%", "2566", new TableRow(getContext()), new Date(2020 - 1900, 3 - 1, 13, 10, 4), "Monday", timeElapsed(new Date(2020 - 1900, 3 - 1, 13, 10, 4), now)));
        tables.add(new Table("Sales ", "24%", "1940", new TableRow(getContext()), new Date(2020 - 1900, 2 - 1, 11, 10, 4), "Monday", timeElapsed(new Date(2020 - 1900, 2 - 1, 11, 10, 4), now)));
        tables.add(new Table("Sthg", "5%", "250", new TableRow(getContext()), new Date(2020 - 1900, 2 - 1, 12, 10, 4), "Monday", timeElapsed(new Date(2020 - 1900, 2 - 1, 12, 10, 4), now)));
        tables.add(new Table("Credit", "0%", "0", new TableRow(getContext()), new Date(2020 - 1900, 1 - 1, 30, 10, 4), "Monday", timeElapsed(new Date(2020 - 1900, 1 - 1, 30, 10, 4), now)));
        tables.add(new Table("Admin ", "56%", "2566", new TableRow(getContext()), new Date(2020 - 1900, 4 - 1, 29, 10, 4), "Monday", timeElapsed(new Date(2020 - 1900, 4 - 1, 29, 10, 4), now)));
        tables.add(new Table("Sales ", "24%", "1940", new TableRow(getContext()), new Date(2020 - 1900, 8 - 1, 28, 10, 4), "Monday", timeElapsed(new Date(2020 - 1900, 8 - 1, 28, 10, 4), now)));
        tables.add(new Table("Sthg", "5%", "250", new TableRow(getContext()), new Date(2020 - 1900, 6 - 1, 27, 10, 4), "Monday", timeElapsed(new Date(2020 - 1900, 6 - 1, 27, 10, 4), now)));
        tables.add(new Table("Credit", "0%", "0", new TableRow(getContext()), new Date(2020 - 1900, 7 - 1, 26, 10, 4), "Monday", timeElapsed(new Date(2020 - 1900, 7 - 1, 26, 10, 4), now)));
        tables.add(new Table("Admin ", "56%", "2566", new TableRow(getContext()), new Date(2020 - 1900, 6 - 1, 25, 10, 4), "Monday", timeElapsed(new Date(2020 - 1900, 6 - 1, 25, 10, 4), now)));
        tables.add(new Table("Sales ", "24%", "1940", new TableRow(getContext()), new Date(2020 - 1900, 5 - 1, 24, 10, 4), "Monday", timeElapsed(new Date(2020 - 1900, 5 - 1, 24, 10, 4), now)));
        tables.add(new Table("Sthg", "5%", "250", new TableRow(getContext()), new Date(2020 - 1900, 7 - 1, 23, 10, 4), "Monday", timeElapsed(new Date(2020 - 1900, 7 - 1, 23, 10, 4), now)));
        tables.add(new Table("Credit", "0%", "0", new TableRow(getContext()), new Date(2020 - 1900, 3 - 1, 22, 10, 4), "Monday", timeElapsed(new Date(2020 - 1900, 3 - 1, 22, 10, 4), now)));
        tables.add(new Table("Admin ", "56%", "2566", new TableRow(getContext()), new Date(2020 - 1900, 4 - 1, 20, 10, 4), "Monday", timeElapsed(new Date(2020 - 1900, 4 - 1, 20, 10, 4), now)));
        tables.add(new Table("Sales ", "24%", "1940", new TableRow(getContext()), new Date(2020 - 1900, 6 - 1, 21, 10, 4), "Monday", timeElapsed(new Date(2020 - 1900, 6 - 1, 21, 10, 4), now)));
        tables.add(new Table("Sthg", "5%", "250", new TableRow(getContext()), new Date(2020 - 1900, 2 - 1, 19, 10, 4), "Monday", timeElapsed(new Date(2020 - 1900, 2 - 1, 19, 10, 4), now)));
        tables.add(new Table("Credit", "0%", "0", new TableRow(getContext()), new Date(2020 - 1900, 3 - 1, 18, 10, 4), "Monday", timeElapsed(new Date(2020 - 1900, 3 - 1, 18, 10, 4), now)));
        tables.add(new Table("Admin ", "56%", "2566", new TableRow(getContext()), new Date(2020 - 1900, 7 - 1, 17, 10, 4), "Monday", timeElapsed(new Date(2020 - 1900, 7 - 1, 17, 10, 4), now)));
        tables.add(new Table("Sales ", "24%", "1940", new TableRow(getContext()), new Date(2020 - 1900, 9 - 1, 16, 10, 4), "Monday", timeElapsed(new Date(2020 - 1900, 9 - 1, 16, 10, 4), now)));
        tables.add(new Table("Sthg", "5%", "250", new TableRow(getContext()), new Date(2020 - 1900, 10 - 1, 15, 10, 4), "Monday", timeElapsed(new Date(2020 - 1900, 10 - 1, 15, 10, 4), now)));
        tables.add(new Table("Credit", "0%", "0", new TableRow(getContext()), new Date(2020 - 1900, 7 - 1, 14, 10, 4), "Monday", timeElapsed(new Date(2020 - 1900, 7 - 1, 14, 10, 4), now)));
        tables.add(new Table("Admin ", "56%", "2566", new TableRow(getContext()), new Date(2020 - 1900, 9 - 1, 13, 10, 4), "Monday", timeElapsed(new Date(2020 - 1900, 9 - 1, 13, 10, 4), now)));
        tables.add(new Table("Sales ", "24%", "1940", new TableRow(getContext()), new Date(2020 - 1900, 11 - 1, 12, 10, 4), "Monday", timeElapsed(new Date(2020 - 1900, 11 - 1, 12, 10, 4), now)));
        tables.add(new Table("Sthg", "5%", "250", new TableRow(getContext()), new Date(2020 - 1900, 12 - 1, 11, 10, 4), "Monday", timeElapsed(new Date(2020 - 1900, 12 - 1, 11, 10, 4), now)));


        Thread tThread = new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < DashBoard3Fragment.tables.size(); i++) {
                    int finalI = i;
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            TextView txtv1 = new TextView(getContext());
//                            TextView txtv2 = new TextView(getContext());
//                            TextView txtv3 = new TextView(getContext());
//                            TextView txtv4 = new TextView(getContext());
//                            TextView txtv5 = new TextView(getContext());
//                            TextView txtv6 = new TextView(getContext());

                            textView1.setText(DashBoard3Fragment.tables.get(finalI).branchName);
                            textView2.setText(DashBoard3Fragment.tables.get(finalI).percentage);
                            textView3.setText(DashBoard3Fragment.tables.get(finalI).total);

                            String date = java.text.DateFormat.getDateInstance().format(DashBoard3Fragment.tables.get(finalI).date);
//                            String date = new SimpleDateFormat("yyyy-mm-dd").format(DashBoard3Fragment.tables.get(finalI).date);

                            textView4.setText(date);
                            textView5.setText(DashBoard3Fragment.tables.get(finalI).day);
                            textView6.setText(DashBoard3Fragment.tables.get(finalI).lastSeen);
                            //            initTable(tables.get(i).tableRow, new TextView(this).setText(tables.get(i).branchName),new TextView(this).setText(tables.get(i).percentage),new TextView(this).setText(tables.get(i).total));
                            DashBoard3Fragment.initTable( getContext());
                            Log.i("TAG", tables.get(finalI).branchName);
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

            }
        });
        tThread.start();

//        View child =tableLayout.getChildAt(5);
//        scrollView.scrollTo(0,child.getTop());

        return view;
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

        if (elapsedMonths > 12) {
            return " long ago";
        } else if (elapsedMonths <= 12 && elapsedMonths > 0) {
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

    @SuppressLint("UseCompatLoadingForDrawables")
    static void initTable( Context context) {

//        tableRow.addView(linearLayout);

//        tableRow.addView(textView1);
//        tableRow.addView(textView2);
//        tableRow.addView(textView3);
//        tableRow.addView(textView4);
//        tableRow.addView(textView5);
//        tableRow.addView(textView6);
//        tableRow.removeAllViews();
//        tableRow.addView(tableLayout);


//        tr.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        tr.setBackgroundColor(Color.parseColor("#49515c"));
//        tV1.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f));
//        tV2.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f));
//        tV3.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f));
//        tV4.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f));
//        tV5.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f));
//        tV6.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f));
//        tV2.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
//        tV3.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
//        tV4.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
//        tV5.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
//        tV6.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
//        tV3.setPadding(0, 0, paddingPixel, 0);
//        tV5.setPadding(0, 0, paddingPixel, 0);
//        tV6.setPadding(0, 0, paddingPixel, 0);
//        tV1.setPadding(paddingPixel, 0, 0, 0);
//        tV1.setTextColor(Color.parseColor("#d2b566"));

        if (Double.parseDouble((textView2.getText().toString()).substring(0, textView2.getText().toString().length() - 1)) < 50) {
            textView2.setTextColor(Color.parseColor("#EA6F6A"));
        } else
            textView2.setTextColor(Color.parseColor("#78FA5A"));

//        Log.i("TAG",tV2.getText().toString());
//            tV2.setTextColor(Color.parseColor("#78FA5A"));

//        tV3.setTextColor(Color.parseColor("#78FA5A"));
//        tV4.setTextColor(Color.parseColor("#78FA5A"));
//        tV5.setTextColor(Color.parseColor("#78FA5A"));
//        tV5.setTextColor(Color.parseColor("#78FA5A"));
//        tV6.setTextColor(Color.parseColor("#78FA5A"));
//
//        tV1.setTextSize(20);
//        tV2.setTextSize(20);
//        tV3.setTextSize(20);
//        tV4.setTextSize(20);
//        tV5.setTextSize(20);
//        tV6.setTextSize(20);

        ImageView imageView = new ImageView(textView1.getContext());
        Drawable drawable = context.getResources().getDrawable(R.drawable.ic_heineken);
        imageView.setImageDrawable(drawable);

        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(widthDp, heightDp);
        imageView.setLayoutParams(ll);
//        tV1.setGravity(Gravity.CENTER_VERTICAL);

//
//        LinearLayout linearLayout = new LinearLayout(tV1.getContext());
//        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
//        linearLayout.addView(imageView);
//        linearLayout.addView(tV1);
//        linearLayout.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f));
//        linearLayout.setGravity(Gravity.CENTER|Gravity.CENTER_VERTICAL);

//        tr.addView(linearLayout);
////        tr.addView(tV1);
//        tr.addView(tV2);
//        tr.addView(tV3);
//        tr.addView(tV4);
//        tr.addView(tV5);
//        tr.addView(tV6);

//        ((ViewGroup)tableRow.getParent()).removeView(tableRow);
//        ((ViewGroup)linearLayout.getParent()).removeView(linearLayout);

        if(linearLayout.getParent()!=null)
            ((ViewGroup)linearLayout.getParent()).removeView(linearLayout);

        tableLayout.addView(linearLayout);
        Animation animation1 = AnimationUtils.loadAnimation(tableLayout.getContext(), R.anim.slide_in_bottom);
        Animation animation2 = AnimationUtils.loadAnimation(tableLayout.getContext(), R.anim.slide_out_bottom);
        linearLayout.startAnimation(animation2);
    }
}