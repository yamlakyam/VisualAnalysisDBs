package com.example.VisualAnalysis;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

//import maes.tech.intentanim.CustomIntent;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "DeviceTypeRuntimeCheck";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        setTheme(R.style.darkTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if (getIntent().getIntExtra("fragmentNumber",0)==1) {
//            Fragment fragment = new VsmCardFragment();
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.vsmCardFragment, fragment).commit();
//        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


}