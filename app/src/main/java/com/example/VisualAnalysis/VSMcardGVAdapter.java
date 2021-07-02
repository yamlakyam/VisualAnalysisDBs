package com.example.VisualAnalysis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class VSMcardGVAdapter extends ArrayAdapter<Table> {
    public VSMcardGVAdapter(@NonNull Context context, ArrayList<Table> vsmCardArrayList) {
        super(context, 0, vsmCardArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listedView = convertView;

        if (listedView == null) {
            listedView = LayoutInflater.from(getContext()).inflate(R.layout.vsm_card_element, parent, false);
        }
        Table vsmCard = getItem(position);

        TextView vsmName =listedView.findViewById(R.id.vsmCardNameTxt);
        TextView vsmOutlet =listedView.findViewById(R.id.vsmCardoutletTxt);
        TextView vsmLastActive =listedView.findViewById(R.id.vsmCardlastActive);
        TextView vsmVcount =listedView.findViewById(R.id.vsmCardVcount);
        TextView vsmTsale =listedView.findViewById(R.id.vsmCardTotalSale);

        vsmName.setText(vsmCard.vsi);
        vsmOutlet.setText(String.valueOf(vsmCard.salesOutLateCount));
        vsmLastActive.setText(vsmCard.lastSeen);
        vsmVcount.setText(String.valueOf(vsmCard.vCount));
        vsmTsale.setText(String.valueOf(vsmCard.totalSalesAmountAfterTax));


        return listedView;
    }
}
