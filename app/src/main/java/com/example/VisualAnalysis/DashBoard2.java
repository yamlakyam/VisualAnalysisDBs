package com.example.VisualAnalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.Arrays;

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
        dataVals.add(new Entry(6, 3.4f));

        LineDataSet lineDataSet = new LineDataSet(dataVals, "active users");
        LineData lineData = new LineData();
        lineData.addDataSet(lineDataSet);
        lineChart.setData(lineData);

        lineChart.getAxisLeft().setDrawLabels(true);
        lineChart.getAxisRight().setDrawGridLines(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        //lineChart.getAxisLeft().setEnabled(false);
        lineChart.getAxisRight().setDrawAxisLine(false);
        lineChart.getAxisRight().setDrawLabels(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.getXAxis().setDrawAxisLine(false);
        lineChart.getXAxis().setDrawGridLines(false);
//        lineChart.getXAxis().enableAxisLineDashedLine(10f,10f,0);
//        lineChart.getAxisRight().enableAxisLineDashedLine(10f,10f,0);
//        lineChart.getAxisLeft().enableAxisLineDashedLine(10f,10f,0);

        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.setDrawGridBackground(false);
        lineChart.getAxisLeft().setDrawGridLines(false);


        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setDrawValues(false);
        lineDataSet.setColors(Color.parseColor("#5b79e7"));
        lineDataSet.setDrawCircles(false);
//        lineDataSet.enableDashedLine(10f,10f,0);


        ArrayList<String> xAxisVals = new ArrayList<>(Arrays.asList("Apr 6", "Apr 7", "Apr 8", "Apr 9", "Apr 10", "Apr 11", "Apr 12"));
        ArrayList<String> yAxisVals = new ArrayList<>(Arrays.asList("0", "10k", "20k", "30k", "40k", "50k", "60k"));
        lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisVals));
        //lineChart.getAxisLeft().setValueFormatter(new IndexAxisValueFormatter(yAxisVals));
        lineChart.getAxisLeft().setLabelCount(yAxisVals.size());

        LineChart lineChart2 = findViewById(R.id.linechart2);
        ArrayList<Entry> dataVal1 = new ArrayList<Entry>();
        dataVal1.add(new Entry(0, 10f));
        dataVal1.add(new Entry(1, 2));
        dataVal1.add(new Entry(2, 10f));
        dataVal1.add(new Entry(3, 6f));
        dataVal1.add(new Entry(4, 15f));
        dataVal1.add(new Entry(5, 4f));
        dataVal1.add(new Entry(6, 8f));

        ArrayList<Entry> dataVal2 = new ArrayList<Entry>();
        dataVal2.add(new Entry(0, 5f));
        dataVal2.add(new Entry(1, 18f));
        dataVal2.add(new Entry(2, 11f));
        dataVal2.add(new Entry(3, 14f));
        dataVal2.add(new Entry(4, 4f));
        dataVal2.add(new Entry(5, 16f));
        dataVal2.add(new Entry(6, 18f));

        LineDataSet lineDataSet1 = new LineDataSet(dataVal1, "Install");
        LineDataSet lineDataSet2 = new LineDataSet(dataVal2, "Uninstall");

        lineDataSet1.setColors(Color.parseColor("#27adb9"));
        lineDataSet2.setColors(Color.parseColor("#5473e8"));

        LineData lineData2 = new LineData();
        lineData2.addDataSet(lineDataSet1);
        lineData2.addDataSet(lineDataSet2);
        lineChart2.setData(lineData2);



    }
}