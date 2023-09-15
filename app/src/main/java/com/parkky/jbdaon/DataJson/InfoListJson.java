package com.parkky.jbdaon.DataJson;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class InfoListJson {
    @SerializedName("Result")
    public String Result;
    @SerializedName("Total_Num")
    public int Total_Num;
    @SerializedName("data")
    public ArrayList<InfoListJson2> data = new ArrayList<InfoListJson2>();
}
