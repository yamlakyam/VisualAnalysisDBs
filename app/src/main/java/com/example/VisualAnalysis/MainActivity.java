package com.example.VisualAnalysis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

//import android.content.Intent;
import android.app.UiModeManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.github.anastr.speedviewlib.SpeedView;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.security.interfaces.DSAKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Future;

import maes.tech.intentanim.CustomIntent;

public class MainActivity extends FragmentActivity {


    public static final String TAG = "DeviceTypeRuntimeCheck";


    public View handleTestClick(View v) {

        Log.i("TAg", "hello ");
        return v;
    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        //overridePendingTransition(R.anim.slide_out_bottom,R.anim.slide_in_bottom);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);



    }

}