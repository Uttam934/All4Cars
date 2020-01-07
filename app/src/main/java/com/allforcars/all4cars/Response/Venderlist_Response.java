package com.allforcars.all4cars.Response;


import com.allforcars.all4cars.Model.Venderlist_Model;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Venderlist_Response
{



    public List<Venderlist_Model> getVenderlist()
    {
        return venderlist_Model;
    }

    @SerializedName("record")
    private List<Venderlist_Model> venderlist_Model;



}
