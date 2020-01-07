package com.allforcars.all4cars.classes.apicall;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allforcars.all4cars.Model.InfoWindow;
import com.allforcars.all4cars.R;
import com.allforcars.all4cars.classes.CircleTransform;
import com.allforcars.all4cars.classes.Utility;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowGasPrice implements GoogleMap.InfoWindowAdapter {
    SharedPreferences sharedPreferences;
    private Context context;
    LinearLayout layout_mapinfo;

    public CustomInfoWindowGasPrice(Context ctx) {
        context = ctx;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.customemarkerprice, null);
        layout_mapinfo = view.findViewById(R.id.layout_mapinfo);
        TextView info_title = view.findViewById(R.id.info_title);
        TextView info_pricepetorl = view.findViewById(R.id.info_pricepetorl);
        TextView txt_desigal = view.findViewById(R.id.txt_desigal);
        TextView txt_Gas = view.findViewById(R.id.txt_Gas);
        LinearLayout petrol_layout = view.findViewById(R.id.petrol_layout);
        LinearLayout desial_layout = view.findViewById(R.id.desial_layout);
        LinearLayout Gas_layout = view.findViewById(R.id.Gas_layout);




        layout_mapinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

try
{

    String str = marker.getTitle();
    // String str_one = marker.getSnippet();
    info_title.setText(marker.getTitle());




    InfoWindow infoWindowData =(InfoWindow)marker.getTag();




    String strpetrol = "";
    String strUserTitle = "";
    String strDesial = "";
    String strGas = "";

    strpetrol = infoWindowData.getstrpetrol();
    strDesial = infoWindowData.getstrDesial();
    strGas = infoWindowData.getstrGas();
    strUserTitle = marker.getTitle();

    if(strpetrol.equals("0$")|| strpetrol.equals(""))
    {
        petrol_layout.setVisibility(View.GONE);
    }
    else {
        info_pricepetorl.setText(strpetrol);
    }
    if(strDesial.equals("0$") || strDesial.equals(""))
    {
        desial_layout.setVisibility(View.GONE);
    }
    else {
        txt_desigal.setText(strDesial);
    }
    if(strGas.equals("0") || strGas.equals("") )
    {
        Gas_layout.setVisibility(View.GONE);
    }
    else {
        txt_Gas.setText(strGas);

    }

    info_title.setText(strUserTitle);







}
catch (Exception e)
{

}

        return view;
    }
}