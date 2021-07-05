package com.example.VisualAnalysis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Util {

    public static void animate(View container, View child){
        Animation animation = AnimationUtils.loadAnimation(container.getContext(), R.anim.slide_out_bottom);
        child.startAnimation(animation);
    }

    static void retryRequest(JsonArrayRequest jsonArrayRequest) {

        jsonArrayRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 180000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 180000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
    }

    static Date formatTime(String lastActive) {
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//        Date currentTime = Calendar.getInstance().getTime();

        Date parsed = null;
        try {
            parsed = input.parse(lastActive);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parsed;
    }

    static String timeElapsed(Date startDate, Date endDate) {
        long difference = endDate.getTime() - startDate.getTime();
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        long monthsInMilli = daysInMilli * 30;

        long elapsedMonths = difference / monthsInMilli;
        difference = difference % monthsInMilli;
        long elapsedDays = difference / daysInMilli;
        difference = difference % daysInMilli;
        long elapsedHours = difference / hoursInMilli;
        difference = difference % hoursInMilli;
        long elapsedMinutes = difference / minutesInMilli;
        difference = difference % minutesInMilli;

        long elapsedSeconds = difference / secondsInMilli;
        if (elapsedMonths > 7) {
            return " long ago";
        } else if (elapsedMonths > 0) {
            return elapsedMonths + " months ago";
        } else if (elapsedMonths == 0 && elapsedDays > 0) {
            return elapsedDays + " days ago";
        } else if (elapsedDays == 0 && elapsedHours > 0) {
            return elapsedHours + " hours ago";
        } else if (elapsedDays == 0 && elapsedHours == 0 & elapsedMinutes > 0) {
            return elapsedMinutes + " minutes ago";
        } else if (elapsedDays == 0 && elapsedHours == 0 & elapsedMinutes == 0 && elapsedSeconds > 0) {
            return elapsedSeconds + " seconds ago";
        } else
//            return elapsedDays + " days, " + elapsedHours + " hours, " + elapsedMinutes + " minutes, " + elapsedSeconds + " seconds ago";
            return elapsedSeconds + "";
    }
}
