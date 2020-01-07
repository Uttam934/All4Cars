package com.allforcars.all4cars.Test.Retrofit_Rxjava;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by agent on 12/9/16.
 */

public class Preference {
    public static final String MyPREFERENCES = "Pref_Categorieslist";
    public static final String Key_Category = "Categories";
    public static final String key_PrdctCatgry = "Product_Category_";
    public static final String key_TabCatgry = "Tab_Category_POS_";
    public static final String key_TabCatgry_Return = "Tab_Category_Return_POS_";
    public static final String key_TabCatgry_indent = "Tab_Category_Indent_";
    public static final String key_TabProducts = "Tab_Products_";
    public static final String key_Crop = "Crop";
    public static final String key_Store = "Store";
    public static final String key_Circle = "Circle";
    public static final String key_Transport = "Transport";
    public static final String key_StoreMenu = "Storemenu";
    public static final String key_ProductDiscunt = "prodctDiscount";
    public static final String key_StoreSubsidy = "StoreSubsidy";
    public static final String ATTENDANCESTATUS = "attendanceStatus";
    public static final String key_companyUnit = "unit";
    public static final String key_SubStore = "SubStore";
    public static final String key_StoreUser = "StoreUser";
    public static final String key_FmProducts = "FmProducts";



    public static void  setTabsDataToPref(Context context, JSONObject jsonObject, String Key)
    {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = settings.edit();
        try{
            JSONArray subArray = jsonObject.getJSONArray("products");
            editor.putString(Key, String.valueOf(subArray));
            editor.commit();
        }
        catch (Exception e)
        {

        }

    }


}
