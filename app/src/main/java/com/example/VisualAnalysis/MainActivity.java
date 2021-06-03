package com.example.VisualAnalysis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

//import android.content.Intent;
import android.app.UiModeManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Future;

public class MainActivity extends FragmentActivity {


    public static final String TAG = "DeviceTypeRuntimeCheck";


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        UiModeManager uiModeManager = (UiModeManager) getSystemService(UI_MODE_SERVICE);
        if (uiModeManager.getCurrentModeType() == Configuration.UI_MODE_TYPE_TELEVISION) {
            Log.d(TAG, "Running on a TV Device");
        } else if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_LEANBACK)) {
            Log.d(TAG, "has leanback features");

        } else if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_TELEVISION)) {
            Log.d(TAG, "has hardware type tv");

        } else {
            Log.d(TAG, "Running on a non-TV Device");
        }


        ArrayList<String> xAxisVals = new ArrayList<>(Arrays.asList("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEPT", "OCT", "NOV", "DEC"));


        ArrayList<BarEntry> dataVals = new ArrayList<BarEntry>();
        dataVals.add(new BarEntry(0, 3));
        dataVals.add(new BarEntry(1, 4));
        dataVals.add(new BarEntry(2, 9));
        dataVals.add(new BarEntry(3, 6));
        dataVals.add(new BarEntry(4, 10));
        dataVals.add(new BarEntry(5, 12));
        dataVals.add(new BarEntry(6, 15));
        dataVals.add(new BarEntry(7, 8));
        dataVals.add(new BarEntry(8, 10));
        dataVals.add(new BarEntry(9, 9));
        dataVals.add(new BarEntry(10, 12));
        dataVals.add(new BarEntry(11, 7));
        //dataVals.add(new BarEntry(5, -5));


        BarChart barChart = findViewById(R.id.barchart);
        BarDataSett barDataSet = new BarDataSett(dataVals, "dataset1");

        barDataSet.setDrawValues(false);
        for (int i = 0; i < dataVals.size(); i++) {
            if (dataVals.get(i).getY() < 0) {
                barDataSet.setColors(Color.parseColor("#d9f5ff"));
            } else if (dataVals.get(i).getY() > 0) {
                barDataSet.setColors(Color.parseColor("#212c5d"));
            }

        }//this code sets all the dataset color based on the last condition.//has to be fixed

        //barChart.getAxisLeft().setDrawGridLines(false);
        barChart.setDrawGridBackground(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisRight().setDrawGridLines(false);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(dataVals.size());//if it doesn't show all the labels, use this

        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawAxisLine(false);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getAxisRight().setDrawAxisLine(false);


        //baChart.setDrawGridBackground(false);
        barDataSet.setBarBorderWidth(1f);


        BarData barData = new BarData();
        barData.addDataSet(barDataSet);
        barChart.setData(barData);
        barChart.invalidate();
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisVals));
        barChart.getXAxis().setLabelRotationAngle(-15);
        barChart.animateXY(1000, 1000);
        barChart.getAxisRight().setDrawLabels(false);
        barChart.getDescription().setEnabled(false);


        PieChart pieChart = findViewById(R.id.piechart);

        ArrayList<PieEntry> piedatas = new ArrayList<>();
        piedatas.add(new PieEntry((float) 690.8, "Poland"));
        piedatas.add(new PieEntry((float) 84.4, "United States"));
        piedatas.add(new PieEntry((float) 71.2, "India"));
        piedatas.add(new PieEntry((float) 437.7, "Others"));

        PieDataSet pieDataSet = new PieDataSet(piedatas, "Piedata label");
        pieDataSet.setColors(
                Color.parseColor("#e73a55"),
                Color.parseColor("#110f49"),
                Color.parseColor("#f8f8fa"),
                Color.parseColor("#d9f5ff")
        );
        pieDataSet.setDrawValues(false);


        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.setDrawSliceText(false);
        //pieChart.setHoleRadius(0);
        pieChart.setDrawHoleEnabled(false);
        //pieChart.setOutlineSpotShadowColor(Color.parseColor(""));
        pieChart.spin(5000, 90f, 360f, Easing.EaseInOutQuad);
        pieChart.getDescription().setEnabled(false);
        // pieChart.getRadius();
        //Toast.makeText(this, (int) pieChart.getRadius(),Toast.LENGTH_LONG).show();



        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);

        CircleDisplay circleDisplay = findViewById(R.id.circleDisplay);
        circleDisplay.setColor(Color.parseColor("#d68894"));
        circleDisplay.setAnimDuration(3000);
        circleDisplay.setStepSize(1f);
        circleDisplay.showValue(75f, 100f, true);
        circleDisplay.setTouchEnabled(true);

//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(MainActivity.this, DashBoard2.class);
//                startActivity(intent);
//                finish();
//            }
//        },2000);

        Button nxtbtn =findViewById(R.id.button2);
        nxtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DashBoard2.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                BarChart barChart = findViewById(R.id.barchart);
                barChart.setFocusable(true);
                barChart.requestFocus();
                return true;

            case KeyEvent.KEYCODE_DPAD_LEFT:
                findViewById(R.id.sessionscard).setFocusable(true);
        }
        return super.onKeyDown(keyCode, event);
    }
}