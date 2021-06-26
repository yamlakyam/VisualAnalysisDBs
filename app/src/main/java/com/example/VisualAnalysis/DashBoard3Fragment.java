package com.example.VisualAnalysis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    private TableLayout tableLayout;

    public static ArrayList<Table> tables;
    private static int paddingPixel;

    static ScrollView scrollView;

    static int widthDp;
    static int heightDp;

    private ImageView imageView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflater.getContext().setTheme(R.style.darkTheme);


        View view = getLayoutInflater().inflate(R.layout.fragment_dash_board3, container, false);
        tableLayout = view.findViewById(R.id.tableLayout);

        scrollView = view.findViewById(R.id.scrolll);

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
        tables.add(new Table("Credit", "32%", "100", new TableRow(getContext()), new Date(2020 - 1900, 6 - 1, 1, 9, 10), "Monday", timeElapsed(new Date(2020 - 1900, 6 - 1, 1, 9, 10), now)));
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
        tables.add(new Table("Sthg", "5%", "250", new TableRow(getContext()), new Date(2020 - 1900, 12 - 1, 11, 10, 4), "Monday", timeElapsed(new Date(2020 - 1900, 12 - 1, 11, 10, 4), now)));


        Thread tThread = new Thread(() -> {

            for (int i = 0; i < DashBoard3Fragment.tables.size(); i++) {
                int finalI = i;
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        View tableElements= LayoutInflater.from(getContext()).inflate(R.layout.table_elements, null , false);

                        TextView textView1 = tableElements.findViewById(R.id.tvalue1);
                        TextView textView2 = tableElements.findViewById(R.id.tvalue2);
                        TextView textView3 = tableElements.findViewById(R.id.tvalue3);
                        TextView textView4 = tableElements.findViewById(R.id.tvalue4);
                        TextView textView5 = tableElements.findViewById(R.id.tvalue5);
                        TextView textView6 = tableElements.findViewById(R.id.tvalue6);

                        textView1.setText(DashBoard3Fragment.tables.get(finalI).branchName);
                        textView2.setText(DashBoard3Fragment.tables.get(finalI).percentage);
                        textView3.setText(DashBoard3Fragment.tables.get(finalI).total);

                        String date = java.text.DateFormat.getDateInstance().format(DashBoard3Fragment.tables.get(finalI).date);
//                            String date = new SimpleDateFormat("yyyy-mm-dd").format(DashBoard3Fragment.tables.get(finalI).date);

                        textView4.setText(date);
                        textView5.setText(DashBoard3Fragment.tables.get(finalI).day);
                        textView6.setText(DashBoard3Fragment.tables.get(finalI).lastSeen);

                        if (Double.parseDouble((textView2.getText().toString()).substring(0, textView2.getText().toString().length() - 1)) < 50) {
                            textView2.setTextColor(Color.parseColor("#EA6F6A"));
                        } else
                            textView2.setTextColor(Color.parseColor("#78FA5A"));

                        TableRow tableRow = tableElements.findViewById(R.id.tableRow);
                        tableLayout.addView(tableElements);

                        Animation animation1 = AnimationUtils.loadAnimation(tableLayout.getContext(), R.anim.slide_in_bottom);
                        Animation animation2 = AnimationUtils.loadAnimation(tableLayout.getContext(), R.anim.slide_out_bottom);
                        tableRow.startAnimation(animation2);

//                            DashBoard3Fragment.initTable(getContext());
                        Log.i("TAG", tables.get(finalI).branchName);
//                            requireActivity().setContentView(R.layout.fragment_dash_board3);
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

}