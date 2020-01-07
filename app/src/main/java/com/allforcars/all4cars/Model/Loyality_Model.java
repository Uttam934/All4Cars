package com.allforcars.all4cars.Model;

public class Loyality_Model {

    private String loyalty_id;
    private String loyalty_name;
    private String loyalty_image;
    private String status;
    private String loyality_years;
    private String created_on;


    public String getcreated_on() {
        return created_on;
    }

    public void setcreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getloyality_years() {
        return loyality_years;
    }

    public void setloyality_years(String loyality_years) {
        this.loyality_years = loyality_years;
    }




    public String getloyalty_name() {
        return loyalty_name;
    }

    public void setloyalty_name(String loyalty_name) {
        this.loyalty_name = loyalty_name;
    }



    public String getloyalty_id() {
        return loyalty_id;
    }

    public void setloyalty_id(String loyalty_id) {
        this.loyalty_id = loyalty_id;
    }

    public String getloyalty_image() {
        return loyalty_image;
    }

    public void setloyalty_image(String loyalty_image) {
        this.loyalty_image = loyalty_image;
    }


    public String getstatus() {
        return status;
    }

    public void setstatus(String status) {
        this.status = status;
    }


}
