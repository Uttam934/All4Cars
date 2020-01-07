package com.allforcars.all4cars.Response;


import com.allforcars.all4cars.Model.Getuserdata_Model;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Getuserdata_Response
{



    @SerializedName("success")
    private String success;

    public String getSuccess() {
        return success;
    }

    public ArrayList<Getuserdata_Model> getUserdata()
    {
        return notification_models;
    }

    @SerializedName("record")
    private ArrayList<Getuserdata_Model> notification_models;





}
