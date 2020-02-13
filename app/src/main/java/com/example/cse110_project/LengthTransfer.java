package com.example.cse110_project;

public class LengthTransfer {
    public LengthTransfer(){}

    public String transfer(float length){
        String str=String.format("%.2f",length);
            if(length>=10000){
                length=length/1000;
                str=String.format("%.2f",length);
                str+="k";

            }




            return str;
    }


}
