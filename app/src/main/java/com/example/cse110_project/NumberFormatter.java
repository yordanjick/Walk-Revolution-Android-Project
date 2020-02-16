package com.example.cse110_project;

import java.util.Locale;

public class NumberFormatter {
    public static String formatDistance(double distanceInMiles){
        String str = String.format(Locale.US, "%.2f", distanceInMiles);
        if(distanceInMiles < 0) {
            str = "0.00mi";
        } else if(distanceInMiles <= 1000) {
            str += "mi";
        } else {
            distanceInMiles = distanceInMiles/1000;
            str = String.format(Locale.US, "%.2f", distanceInMiles);
            str += "kmi";
        }

        return str;
    }

    public static String formatStep(long steps) {
        String str;
        if(steps < 0) {
            str = "0";
        } else if(steps < 1000) {
            str = Long.toString(steps);
        } else {
            str = String.format(Locale.US, "%.1f", steps/1000.0);
            str += "k";
        }
        return str + "s";
    }

    public static String formatTime(long timeInSecond) {
        long hour, minute, second;
        StringBuffer str = new StringBuffer();
        if(timeInSecond < 0) str.append("00:00:00");
        else {
            hour = timeInSecond / 3600;
            if(hour < 10) str.append('0');
            str.append(hour);
            str.append(':');

            timeInSecond %= 3600;
            minute = timeInSecond / 60;
            if(minute < 10) str.append('0');
            str.append(minute);
            str.append(':');

            second = timeInSecond % 60;
            if(second < 10) str.append('0');
            str.append(second);
        }

        return str.toString();
    }
}
