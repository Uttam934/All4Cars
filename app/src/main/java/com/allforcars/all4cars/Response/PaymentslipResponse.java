package com.allforcars.all4cars.Response;


import com.allforcars.all4cars.Fragment.List_Fragment;
import com.allforcars.all4cars.Model.Loyality_Model;
import com.allforcars.all4cars.Model.Objectclass;
import com.allforcars.all4cars.Venderlogin.model.Paymenthistory_Model;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaymentslipResponse
{


    @SerializedName("record")
    public Objectclass objectclasses;

    public Objectclass getObjectclasses() {
        return objectclasses;
    }



}
