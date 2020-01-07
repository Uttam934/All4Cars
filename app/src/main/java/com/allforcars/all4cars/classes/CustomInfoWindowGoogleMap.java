package com.allforcars.all4cars.classes;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allforcars.all4cars.Model.InfoWindow;
import com.allforcars.all4cars.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter {
    SharedPreferences sharedPreferences;
    private Context context;
    LinearLayout layout_mapinfo;

    public CustomInfoWindowGoogleMap(Context ctx) {
        context = ctx;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.map_custom_infowindow, null);
        layout_mapinfo = view.findViewById(R.id.layout_mapinfo);
        TextView info_title = view.findViewById(R.id.info_title);
        TextView info_distance = view.findViewById(R.id.info_distance);
        TextView info_phone_number = view.findViewById(R.id.info_phone_number);
        TextView info_address = view.findViewById(R.id.info_address);

        final ImageView info_image = view.findViewById(R.id.info_image);

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




    String strDistance = "";
    String strPhonenumber = "";
    String strUserTitle = "";
    String strUserIcon = "";
    String Straddress = "";
//            strUserIcon = infoWindowData.getStrUserIcon();
    strPhonenumber = infoWindowData.getStrPhoneNumber();
    strDistance = infoWindowData.getStrDistance();
    Straddress=infoWindowData.getstraddress();

    if(strPhonenumber.equals(""))
    {
        info_phone_number.setText("MobileNumber");
    }
    else
    {
        info_phone_number.setText("Mob: "+strPhonenumber);
    }

    strUserTitle = marker.getTitle();

    info_title.setText(strUserTitle);
    info_distance.setText(strDistance + " Km away");

    info_address.setText(Straddress);

    String url_image = Utility.logo_url+strUserIcon;


    Glide.with(context).load(url_image)
            .crossFade()
            .thumbnail(0.5f)
            .bitmapTransform(new CircleTransform(context))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(info_image);



}
catch (Exception e)
{

}

        return view;
    }
}