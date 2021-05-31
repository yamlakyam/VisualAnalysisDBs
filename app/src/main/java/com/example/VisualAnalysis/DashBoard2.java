package com.example.VisualAnalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class DashBoard2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board2);

        LineChart lineChart = findViewById(R.id.linechart);
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        dataVals.add(new Entry(0, 2));
        dataVals.add(new Entry(1, 3));
        dataVals.add(new Entry(2, 1.5f));
        dataVals.add(new Entry(3, 2));
        dataVals.add(new Entry(4, 3.5f));
        dataVals.add(new Entry(5, 1.5f));
        dataVals.add(new Entry(5, 3.4f));

        LineDataSet lineDataSet = new LineDataSet(dataVals, "active users");
        LineData lineData = new LineData();
        lineData.addDataSet(lineDataSet);
        lineChart.setData(lineData);

        lineChart.getAxisLeft().setDrawLabels(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getAxisRight().setDrawGridLines(false);
        lineChart.getAxisRight().setDrawAxisLine(false);
        lineChart.getAxisRight().setDrawLabels(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.getXAxis().setDrawAxisLine(false);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.setDrawGridBackground(false);

        //lineDataSet.setDrawCircles(true);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);


    }
}