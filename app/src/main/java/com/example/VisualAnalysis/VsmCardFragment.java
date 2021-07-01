package com.example.VisualAnalysis;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ScrollView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;


public class VsmCardFragment extends Fragment {

    GridView VSMcardGridView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_vsm_card, container, false);
        VSMcardGridView = view.findViewById(R.id.vsmCardGridLayout);


        ArrayList<Table> vsmCardList = new ArrayList<>();
        vsmCardList.add(new Table("VAN1", 20, "20 mins ago", 20, 20098.67));
        vsmCardList.add(new Table("VAN2", 25, "30 mins ago", 25, 2098.87));
        vsmCardList.add(new Table("VAN3", 10, "10 mins ago", 29, 200098.68));
        vsmCardList.add(new Table("VAN4", 15, "15 mins ago", 15, 20098.57));
        vsmCardList.add(new Table("VAN5", 7, "25 mins ago", 26, 95098.68));
        vsmCardList.add(new Table("VAN6", 8, "12 mins ago", 10, 208698.97));

        VSMcardGVAdapter adapter = new VSMcardGVAdapter(requireContext(), vsmCardList);
        VSMcardGridView.setAdapter(adapter);


        return view;

    }


}
