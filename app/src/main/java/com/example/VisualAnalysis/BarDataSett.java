package com.example.VisualAnalysis;

import android.graphics.Color;

import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.List;

public class BarDataSett extends BarDataSet {

    public BarDataSett(List<BarEntry> yVals, String label) {
        super(yVals, label);
    }

    @Override
    public int getColor(int index) {
        if (getEntryForIndex(index).getY()>0)
            return Color.parseColor("#d9f5ff");
        else if(getEntryForIndex(index).getY()<0)
            return Color.parseColor("#d9f5ff");
       else
            return Color.parseColor("#d9f5ff");

    }
}
