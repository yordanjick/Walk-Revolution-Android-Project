package com.example.cse110_project;

import java.util.Locale;

public class NumberFormatter {
    public static String formatDistance(double length){
        String str = String.format(Locale.US, "%.2f", length);
        if(length < 0) {
            str = "0.00m";
        } else if(length <= 1000) {
            str += "m";
        } else {
            length = length/1000;
            str = String.format(Locale.US, "%.2f", length);
            str += "km";
        }

        return str;
    }

    public static String formatStep(int steps) {
        String str;
        if(steps < 0) {
            str = "0";
        } else if(steps < 1000) {
            str = Integer.toString(steps);
        } else {
            str = String.format(Locale.US, "%.1f", steps/1000.0);
            str += "k";
        }
        return str + "s";
    }

    public static String formatTime(int timeInSecond) {
        int hour, minute, second;
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
