package com.allforcars.all4cars.classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SessionManager {



    public static String KEYRadioStatus = "check_radio_status";


    public static void SetSharedPreference(String Keyname, String Keyvalue, Context _context)
    {
        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(_context);
        SharedPreferences.Editor spedit=preferences.edit();

        spedit.putString(Keyname,Keyvalue);
        spedit.commit();

    }

    public  static  String GetSharedPreference(String Keyname, Context _context)
    {
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(_context);
        return  prefs.getString(Keyname, "");


    }


}
