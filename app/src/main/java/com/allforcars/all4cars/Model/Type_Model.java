package com.allforcars.all4cars.Model;

import java.io.Serializable;

public class Type_Model  implements Serializable {

    public String type_logo;
    public String type_name;
    public String type_id;
    public String feaul_stationkm;
    private boolean isSelected;


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }



    public String gettype_logo() {
        return type_logo;
    }

    public void settype_logo(String type_logo) {
        this.type_logo = type_logo;
    }

    public String gettype_name() {
        return type_name;
    }

    public void settype_name(String type_name) {
        this.type_name = type_name;
    }


    public String gettype_id() {
        return type_id;
    }

    public void settype_id(String type_id) {
        this.type_id = type_id;
    }



}
