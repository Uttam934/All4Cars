package com.allforcars.all4cars.Venderlogin.model;

public class    Paymenthistory_Model {

    private String payment_id;
    private String vendor_id;
    private String payment_amount;
    private String remainning_amount;
    private String payment_date;
    private String status;


    public String getpayment_id() {
        return payment_id;
    }

    public void setpayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getvendor_id() {
        return vendor_id;
    }

    public void setvendor_id(String vendor_id) {
        this.vendor_id = vendor_id;
    }




    public String getpayment_amount() {
        return payment_amount;
    }

    public void setpayment_amount(String payment_amount) {
        this.payment_amount = payment_amount;
    }



    public String getremainning_amount() {
        return remainning_amount;
    }

    public void setremainning_amount(String remainning_amount) {
        this.remainning_amount = remainning_amount;
    }

    public String getpayment_date() {
        return payment_date;
    }

    public void setpayment_date(String payment_date) {
        this.payment_date = payment_date;
    }


    public String getstatus() {
        return status;
    }

    public void setstatus(String status) {
        this.status = status;
    }


}
