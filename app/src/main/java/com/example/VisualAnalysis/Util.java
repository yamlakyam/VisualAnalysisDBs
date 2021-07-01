package com.example.VisualAnalysis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;

public class Util {

    public static void initRow(int finalI, ArrayList<Table> tableList, Context context, TableLayout tableLayout) {
        View tableElements = LayoutInflater.from(context).inflate(R.layout.table_elements_2, null, false);

        TextView textView1 = tableElements.findViewById(R.id.t2value1);
        TextView textView2 = tableElements.findViewById(R.id.t2value2);
        TextView textView3 = tableElements.findViewById(R.id.t2value3);
        TextView textView4 = tableElements.findViewById(R.id.t2value4);
        TextView textView5 = tableElements.findViewById(R.id.t2value5);

        String orgName = tableList.get(finalI).vsi;
        String preciseOrgName;
        if (orgName.length() > 30)
            preciseOrgName = orgName.substring(0, 20) + "...";
        else
            preciseOrgName = orgName;

        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setGroupingUsed(true);

        int quantityCount = tableList.get(finalI).quantityCount;
        double totalSalesAmountAfterTax = tableList.get(finalI).totalSalesAmountAfterTax;

        textView1.setText(preciseOrgName);
        textView2.setText(String.valueOf(tableList.get(finalI).salesOutLateCount));
        textView3.setText(String.valueOf(tableList.get(finalI).skuCount));
        textView4.setText(numberFormat.format(quantityCount));
        textView5.setText(numberFormat.format(totalSalesAmountAfterTax));

        TableRow tableRow = tableElements.findViewById(R.id.VSMtableRow);
        tableLayout.addView(tableElements);

        Animation animation1 = AnimationUtils.loadAnimation(tableLayout.getContext(), R.anim.slide_in_bottom);
        Animation animation2 = AnimationUtils.loadAnimation(tableLayout.getContext(), R.anim.slide_out_bottom);
        tableRow.startAnimation(animation2);
    }

    public static void animate(View container, View child){
        Animation animation = AnimationUtils.loadAnimation(container.getContext(), R.anim.slide_out_bottom);
        child.startAnimation(animation);
    }
}
