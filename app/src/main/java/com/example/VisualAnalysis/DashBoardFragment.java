package com.example.VisualAnalysis;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BubbleChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.BubbleDataSet;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;



import java.util.ArrayList;
import java.util.Arrays;


public class DashBoardFragment extends Fragment {

   LineChart lineChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View rootView =inflater.inflate(R.layout.activity_dash_board2,container,false);
       lineChart =(LineChart) rootView.findViewById(R.id.linechart);
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
       lineChart.setExtraBottomOffset(15f);
       lineChart.setExtraTopOffset(15f);
       lineChart.animateX(500, Easing.EaseInCubic);


       LineChart lineChart2 = (LineChart)rootView.findViewById(R.id.linechart2);
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

       LineDataSet lineDataSet1 = new LineDataSet(dataVal1, "Customers");
       LineDataSet lineDataSet2 = new LineDataSet(dataVal2, "Visitors");

       lineDataSet1.setColors(Color.parseColor("#27adb9"));
       lineDataSet2.setColors(Color.parseColor("#5473e8"));
       lineDataSet1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
       lineDataSet2.setMode(LineDataSet.Mode.CUBIC_BEZIER);
       lineDataSet1.setDrawCircles(false);
       lineDataSet2.setDrawCircles(false);
       lineDataSet1.setDrawValues(false);
       lineDataSet2.setDrawValues(false);
       lineDataSet1.setForm(Legend.LegendForm.CIRCLE);
       lineDataSet2.setForm(Legend.LegendForm.CIRCLE);


       LineData lineData2 = new LineData();
       lineData2.addDataSet(lineDataSet1);
       lineData2.addDataSet(lineDataSet2);
       lineChart2.setData(lineData2);
       lineChart2.setDrawGridBackground(false);
       lineChart2.getAxisRight().setDrawGridLines(false);
       lineChart2.getAxisLeft().setDrawGridLines(false);
       lineChart2.getAxisRight().setDrawAxisLine(false);
       lineChart2.getAxisRight().setDrawLabels(false);
       lineChart2.getDescription().setEnabled(false);
       lineChart2.getXAxis().setDrawAxisLine(false);
       lineChart2.getXAxis().setDrawGridLines(false);
       lineChart2.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

       ArrayList<String> chart2xAxis = new ArrayList<String>(Arrays.asList("Apr 6", "Apr 7", "Apr8 ", "Apr 9", "Apr 10", "Apr 11", "Apr 12"));
       lineChart2.getXAxis().setValueFormatter(new IndexAxisValueFormatter(chart2xAxis));
       lineChart2.setExtraBottomOffset(15f);
       lineChart.getXAxis().setTextColor(Color.parseColor("#708099"));
       lineChart2.getXAxis().setTextColor(Color.parseColor("#708099"));
       lineChart.getAxisLeft().setTextColor(Color.parseColor("#708099"));
       lineChart2.getAxisLeft().setTextColor(Color.parseColor("#708099"));
       lineChart.getLegend().setTextColor(Color.parseColor("#708099"));
       lineChart2.getLegend().setTextColor(Color.parseColor("#708099"));

       lineChart2.animateY(3000, Easing.EaseInOutBack);


       ArrayList<BarEntry> dataVal3 = new ArrayList<BarEntry>();
       dataVal3.add(new BarEntry(1, 12.5f));
       dataVal3.add(new BarEntry(2, 10));
       dataVal3.add(new BarEntry(3, 7.5f));
       dataVal3.add(new BarEntry(4, 8));
       dataVal3.add(new BarEntry(5, 6.5f));

       ArrayList<BarEntry> dataVal4 = new ArrayList<BarEntry>();
       dataVal4.add(new BarEntry(1, 6f));
       dataVal4.add(new BarEntry(2, 8));
       dataVal4.add(new BarEntry(3, 5));
       dataVal4.add(new BarEntry(4, 2));
       dataVal4.add(new BarEntry(5, 3.5f));

       BarChart barChart2 = (BarChart)rootView.findViewById(R.id.barchart2);
       barChart2.getDescription().setEnabled(false);
       barChart2.setDrawGridBackground(false);
       barChart2.getXAxis().setCenterAxisLabels(true);
       barChart2.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
       barChart2.getXAxis().setDrawGridLines(false);
       barChart2.getAxisRight().setDrawGridLines(false);
       barChart2.getAxisRight().setDrawAxisLine(false);
       barChart2.getAxisRight().setDrawLabels(false);
       barChart2.getAxisLeft().setDrawGridLines(false);
       barChart2.setPinchZoom(false);
       barChart2.getXAxis().setGranularity(1f);


       BarDataSet barDataSet2 = new BarDataSet(dataVal3, "On time");
       BarDataSet barDataSet3 = new BarDataSet(dataVal4, "In full");
       barDataSet2.setForm(Legend.LegendForm.CIRCLE);
       barDataSet3.setForm(Legend.LegendForm.CIRCLE);

       BarData barData2 = new BarData(barDataSet2, barDataSet3);

       barChart2.setData(barData2);
       barChart2.setVisibleXRangeMaximum(dataVal3.size());
       barChart2.setExtraBottomOffset(15f);


       barDataSet2.setColor(Color.parseColor("#5b79e7"));
       barDataSet3.setColor(Color.parseColor("#27adb9"));
       barDataSet2.setDrawValues(false);
       barDataSet3.setDrawValues(false);

       barData2.setBarWidth(0.3f);
       barChart2.getXAxis().setAxisMaximum(0);
       barChart2.getXAxis().setAxisMaximum(0+barChart2.getBarData().getGroupWidth(0.4f,0f)*6);
       barChart2.groupBars(1, 0.4f, 0f);


       barChart2.animateXY(3000, 3000);
       barChart2.getAxisRight().setDrawLabels(false);


       BubbleChart bubbleChart = (BubbleChart)rootView.findViewById(R.id.bubblechart);

       ArrayList<BubbleEntry> bubbleEntries = new ArrayList<BubbleEntry>();
       bubbleEntries.add(new BubbleEntry(1, 1, 0.001f));
       bubbleEntries.add(new BubbleEntry(2, 2, 0.0021f));
       bubbleEntries.add(new BubbleEntry(3, 3, 0.0017f));
       bubbleEntries.add(new BubbleEntry(4, 4, 0.0008f));
       bubbleEntries.add(new BubbleEntry(5, 1, 0.0002f));
       bubbleEntries.add(new BubbleEntry(6, 3, 0.0018f));
       bubbleEntries.add(new BubbleEntry(7, 1, 0.0012f));

       BubbleDataSet bubbleDataSet = new BubbleDataSet(bubbleEntries, "Event actions");
       bubbleDataSet.setColors(Color.parseColor("#5d78df"), Color.parseColor("#768fec"), Color.parseColor("#7790ec"));
       bubbleDataSet.setForm(Legend.LegendForm.CIRCLE);
       bubbleDataSet.setDrawValues(false);
       BubbleData bubbleData = new BubbleData();
       bubbleData.addDataSet(bubbleDataSet);
       bubbleChart.setData(bubbleData);
       bubbleChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
       //bubbleChart.getAxisLeft().setDrawLabels(false);
       bubbleChart.setDrawGridBackground(false);
       //bubbleChart.getAxisLeft().setDrawAxisLine(false);
       bubbleChart.getAxisLeft().setDrawGridLines(false);
       bubbleChart.getAxisRight().setDrawLabels(false);
       bubbleChart.getAxisRight().setDrawGridLines(false);
       bubbleChart.getAxisRight().setDrawAxisLine(false);
       bubbleChart.getXAxis().setDrawGridLines(false);
       //bubbleChart.getXAxis().setAvoidFirstLastClipping(true);
       bubbleChart.getXAxis().setCenterAxisLabels(true);
       bubbleChart.getXAxis().setAxisMinimum(0.5f);
       bubbleChart.getXAxis().setAxisMaximum(7.5f);

       String[] bubbleXlabel = {"Apr 6", "Apr 7", "Apr 8", "Apr 9", "Apr 10", "Apr 11", "Apr 12"};
       String[] bubbleYlablel = {"Payment", "Scan", "Activate", "Serach"};

       bubbleChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(bubbleXlabel));
       //bubbleChart.getAxisLeft().setValueFormatter(new IndexAxisValueFormatter(bubbleYlablel));
       bubbleChart.getXAxis().setGranularity(1f);
       //bubbleChart.getXAxis().setLabelCount(10);
       //bubbleChart.setViewPortOffsets(60,10,50,60);
       bubbleChart.animateXY(3000, 3000);
       bubbleChart.getLegend().setEnabled(false);


       ProgressBar progressBar1= (ProgressBar)rootView.findViewById(R.id.progressBar1);
       ProgressBar progressBar2 = (ProgressBar)rootView.findViewById(R.id.progressBar2);
       progressBar1.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#5472e8")));
       progressBar2.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#26adb9")));

       PieChart pieChartos = (PieChart)rootView.findViewById(R.id.piechartOS);
       pieChartos.setDrawSliceText(false);
       pieChartos.setHoleRadius(80);
       pieChartos.animateX(3000, Easing.EaseInOutCirc);

       ArrayList<PieEntry> piedatas = new ArrayList<>();
       piedatas.add(new PieEntry(58,"Cash Sales Adjustment"));
       piedatas.add(new PieEntry(42, "Credit Sales Adjustment"));

       PieDataSet pieDataSetOS = new PieDataSet(piedatas,"");
       pieDataSetOS.setColors(Color.parseColor("#5472e8"),Color.parseColor("#26adb9"));
       pieDataSetOS.setDrawValues(false);

       PieData pieDataos = new PieData(pieDataSetOS);
       pieChartos.setData(pieDataos);

       HorizontalBarChart horizontalBarChart= (HorizontalBarChart)rootView.findViewById(R.id.horizontalbC);

       ArrayList<BarEntry> hbardatavals = new ArrayList<>();
       hbardatavals.add(new BarEntry(0, 10f));
       hbardatavals.add(new BarEntry(1, 20f));
       hbardatavals.add(new BarEntry(2, 30f));
       hbardatavals.add(new BarEntry(3, 40f));
       hbardatavals.add(new BarEntry(4, 50f));
       hbardatavals.add(new BarEntry(5, 60f));

       BarDataSet hbarDataSet = new BarDataSet(hbardatavals,"sample horizontal bar graph");

       hbarDataSet.setColors(Color.parseColor("#5b79e7"),
               Color.parseColor("#317892"),
               Color.parseColor("#61c2cc"),
               Color.parseColor("#61c2cc"),
               Color.parseColor("#92a4d4"),
               Color.parseColor("#abdbe3"));
       BarData hbarData = new BarData(hbarDataSet);
       hbarData.setBarWidth(0.5f);

       horizontalBarChart.setData(hbarData);
       horizontalBarChart.getXAxis().setDrawAxisLine(false);
       horizontalBarChart.getXAxis().setDrawGridLines(false);
       horizontalBarChart.getAxisRight().setDrawAxisLine(false);
       horizontalBarChart.getAxisRight().setDrawGridLines(false);
       horizontalBarChart.getAxisLeft().setDrawGridLines(false);
       horizontalBarChart.getAxisLeft().setDrawAxisLine(false);
       horizontalBarChart.getAxisLeft().setDrawLabels(false);
       horizontalBarChart.getAxisRight().setDrawLabels(false);
       //horizontalBarChart.getXAxis().setDrawLabels(false);

       horizontalBarChart.getAxisRight().setSpaceMax(0f);
       horizontalBarChart.getAxisRight().setGranularity(1f);
       horizontalBarChart.getLegend().setEnabled(false);
       horizontalBarChart.getDescription().setEnabled(false);
       horizontalBarChart.getAxisLeft().setAxisMinimum(0f);
       horizontalBarChart.getXAxis().setLabelCount(6);

       ArrayList<String> hbarxlabels = new ArrayList<>(Arrays.asList("Damaged Goods","Shortage Goods","Loaded Goods","Sold Goods","Imported Goods", "Consignment Goods"));
       horizontalBarChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(hbarxlabels));
       horizontalBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
       horizontalBarChart.animateY(3000);


        return rootView;
    }

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
   }

   @Override
   public void onActivityCreated( Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);


   }

   @Override
 public void onViewCreated(View view, Bundle savedInstanceState) {
  super.onViewCreated(view, savedInstanceState);

 }
}