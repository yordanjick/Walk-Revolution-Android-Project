package com.example.cse110_project;

import java.util.Locale;

public class LengthTransfer {
    public static String transfer(float length){
        String str=String.format(Locale.US, "%.2f", length);
            if(length >= 10000){
                length = length/1000;
                str = String.format(Locale.US, "%.2f", length);
                str += "k";

            }

            return str;
    }
}
