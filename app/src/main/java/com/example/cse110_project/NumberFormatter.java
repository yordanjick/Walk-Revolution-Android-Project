package com.example.cse110_project;

import java.util.Locale;

public class NumberFormatter {
    public static String formatDistance(double length){
        String str = String.format(Locale.US, "%.2f", length);
        if(length < 0) {
            str = "0.00m";
        } else if(length <= 1000) {
            str += "m";
        } else if(length >= 10000){
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
}
