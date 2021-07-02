package com.example.VisualAnalysis;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.text.NumberFormat;
import java.util.ArrayList;


public class vsmTransactionFragment extends Fragment {

    TableLayout tableLayout;
    FrameLayout frameLayout;
    ScrollView scrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vsm_transaction, container, false);

        tableLayout = view.findViewById(R.id.VSMtableLayout);
        frameLayout = view.findViewById(R.id.VSM_progressBar_frame);
        scrollView = view.findViewById(R.id.VSMscroll);


//        initRow();
        View tableElements = LayoutInflater.from(getContext()).inflate(R.layout.vsm_table_element, null, false);
        tableLayout.addView(tableElements);

        return view;
    }

    private void initRow(int finalI, ArrayList<Table> tableList) {
        View tableElements = LayoutInflater.from(getContext()).inflate(R.layout.vsm_table_element, null, false);

        TextView textView1 = tableElements.findViewById(R.id.vsmElement1);
        TextView textView2 = tableElements.findViewById(R.id.vsmElement2);
        TextView textView3 = tableElements.findViewById(R.id.vsmElement3);
        TextView textView4 = tableElements.findViewById(R.id.vsmElement4);
        TextView textView5 = tableElements.findViewById(R.id.vsmElement5);
        TextView textView6 = tableElements.findViewById(R.id.vsmElement6);
        TextView textView7 = tableElements.findViewById(R.id.vsmElement7);
        TextView textView8 = tableElements.findViewById(R.id.vsmElement8);
        TextView textView9 = tableElements.findViewById(R.id.vsmElement9);

        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setGroupingUsed(true);

        textView1.setText(String.valueOf(tableList.get(finalI).sN));
        textView2.setText(String.valueOf(tableList.get(finalI).voucherN));
        textView3.setText(String.valueOf(tableList.get(finalI).salesOutLateCount));
        textView4.setText(String.valueOf(tableList.get(finalI).TIN));
        textView5.setText(String.valueOf(tableList.get(finalI).startTime));
        textView6.setText(String.valueOf(tableList.get(finalI).itemCount));
        textView7.setText(numberFormat.format(tableList.get(finalI).subTotal));
        textView8.setText(String.valueOf(tableList.get(finalI).VAT));
        textView9.setText(numberFormat.format(tableList.get(finalI).totalSalesAmountAfterTax));

        TableRow tableRow = tableElements.findViewById(R.id.VSMtableRow);
        tableLayout.addView(tableElements);

        Util.animate(tableLayout, tableRow);
    }
}