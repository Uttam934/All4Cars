package com.allforcars.all4cars.Response;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("e_sign_status")
    @Expose
    private String eSignStatus;

    public String getESignStatus() {
        return eSignStatus;
    }

    public void setESignStatus(String eSignStatus) {
        this.eSignStatus = eSignStatus;
    }

}