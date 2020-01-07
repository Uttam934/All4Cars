package com.allforcars.all4cars.Test.Retrofit_Rxjava;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Krishan on 1/24/2017.
 */

public class CheckStatusModel {


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


}
