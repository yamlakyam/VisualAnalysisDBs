package com.example.VisualAnalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ArrayList<BarEntry> dataValues() {
        ArrayList<BarEntry> dataVals = new ArrayList<BarEntry>();
        dataVals.add(new BarEntry(0, 3));
        dataVals.add(new BarEntry(1, 4));
        dataVals.add(new BarEntry(3, 6));
        dataVals.add(new BarEntry(4, 10));

        return dataVals;
    }


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BarChart barChart = findViewById(R.id.barchart);
        BarDataSet barDataSet = new BarDataSet(dataValues(),"dataset1");

        BarData barData= new BarData();
        barData.addDataSet(barDataSet);
        barChart.setData(barData);
        barChart.invalidate();

    }
}