package com.allforcars.all4cars.Test;

import java.io.Serializable;

public class Add_Model implements Serializable {


    public String name;
    public String add;
    public String mob;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public Add_Model(String name, String add, String mob) {
        this.name  = name;
        this.add = add;
        this.mob = mob;
    }
    public  Add_Model()
    {

        return;

    }











}
