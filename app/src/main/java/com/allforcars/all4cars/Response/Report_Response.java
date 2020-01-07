package com.allforcars.all4cars.Response;


import com.allforcars.all4cars.Model.Loyality_Model;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Report_Response
{

    @SerializedName("message")
    private String message;

    @SerializedName("success")
    private String success;

    public String getMessage() {
        return message;
    }

    public String getSuccess() {
        return success;
    }

//    public List<Loyality_Model> getLoyality()
//    {
//        return notification_models;
//    }
//
//    @SerializedName("record")
//    private List<Loyality_Model> notification_models;





}
