package com.allforcars.all4cars.classes;

import android.graphics.Bitmap;

import com.allforcars.all4cars.Model.Feaulserher_Model;

import java.util.ArrayList;


public class Singleton {
    private static Singleton singleton=new Singleton();
    private Singleton() { }

    public static Singleton getInstance( ) {
        return singleton;
    }


    public ArrayList<Feaulserher_Model> Arraylist_feaulserher;

}
