package com.example.VisualAnalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {

    int negativeColor = Color.rgb(17, 15, 71);
    int positiveColor = Color.rgb(217, 245, 255);


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        dataVals.add(new BarEntry(11, -3));
        //dataVals.add(new BarEntry(5, -5));


        BarChart barChart = findViewById(R.id.barchart);
        BarDataSett barDataSet = new BarDataSett(dataVals, "dataset1");

        barDataSet.setDrawValues(false);
//        for (int i = 0; i < dataVals.size(); i++) {
//            if (dataVals.get(i).getY() < 0) {
//                barDataSet.setColors(negativeColor,positiveColor);
//            } else if(dataVals.get(i).getY()>0) {
//                barDataSet.setColors(positiveColor,negativeColor);
//            }
//
//        }

        //barChart.getAxisLeft().setDrawGridLines(false);
        barChart.setDrawGridBackground(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisRight().setDrawGridLines(false);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(dataVals.size());

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

        barChart.animateXY(1000, 1000);


    }
}