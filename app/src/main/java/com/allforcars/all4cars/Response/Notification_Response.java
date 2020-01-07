package com.allforcars.all4cars.Response;


import com.allforcars.all4cars.Model.Loyality_Model;
import com.allforcars.all4cars.Model.Notification_Model;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Notification_Response
{



    public ArrayList<Notification_Model> getnotificatin()
    {
        return notification_models;
    }

    @SerializedName("record")
    private ArrayList<Notification_Model> notification_models;



   /* public List<SingleUserAdsModel> getSingleUserAdsModels() {
        return singleUserAdsModels;
    }

    @SerializedName("record")
    private List<SingleUserAdsModel> singleUserAdsModels;
*/


  /*  @SerializedName("message")
    private String message;*/
   /* @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public List<WalletModelList> getWalletModelLists()
    {
        return walletModelLists;
    }

    @SerializedName("wallet_deatils")
    private List<WalletModelList> walletModelLists;*/


 /*   public List<CategoryModel> getCategoryModelLists()
    {
        return categoryModelLists;
    }

    @SerializedName("category_details")
    private List<CategoryModel> categoryModelLists;*/

}
