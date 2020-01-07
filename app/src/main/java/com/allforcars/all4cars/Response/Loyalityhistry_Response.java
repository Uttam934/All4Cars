package com.allforcars.all4cars.Response;


import com.allforcars.all4cars.Model.LoyalityHistory_Model;
import com.allforcars.all4cars.Model.Loyality_Model;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Loyalityhistry_Response
{


    @SerializedName("success")
    private String success;

    public String getMessage() {
        return message;
    }

    @SerializedName("message")
    private String message;

    public String getSuccess()
    {
        return success;
    }

    public List<LoyalityHistory_Model> getloylisthistory()
    {
        return loyalityhistry_models;
    }

    @SerializedName("record")
    private List<LoyalityHistory_Model> loyalityhistry_models;





}
