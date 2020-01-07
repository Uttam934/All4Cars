package com.allforcars.all4cars.Model;

import com.allforcars.all4cars.Venderlogin.model.Paymenthistory_Model;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Objectclass {

    @SerializedName(value = "deuamount")
    private String deuamount;
    @SerializedName(value = "my_income")
    private String my_income;
    @SerializedName(value = "payment_received")
    private String payment_received;
    @SerializedName(value = "remaining_amount")
    private String remaining_amount;
    @SerializedName(value = "totalsales")
    private String totalsales;
    @SerializedName(value = "total_commission")
    private String total_commission;
    @SerializedName(value = "vendor_id")
    private String vendor_id;





    public String getDeuamount() {
        return deuamount;
    }

    public String getMy_income() {
        return my_income;
    }



    public String getPayment_received() {
        return payment_received;
    }

    public String getRemaining_amount() {
        return remaining_amount;
    }

    public String getTotalsales() {
        return totalsales;
    }

    public String getTotal_commission() {
        return total_commission;
    }

    public String getVendor_id() {
        return vendor_id;
    }

    public List<Paymenthistory_Model> getpaymetslip() {
        return paymenthistory_Model;
    }

    @SerializedName("payments_list")
    private List<Paymenthistory_Model> paymenthistory_Model;

}
