package com.allforcars.all4cars.Venderlogin.Activity;



import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.allforcars.all4cars.Activity.EditProfile_Activity;
import com.allforcars.all4cars.Activity.Filter_Activity;
import com.allforcars.all4cars.Activity.OutageTracker_Activity;
import com.allforcars.all4cars.Activity.Signup_Activity;
import com.allforcars.all4cars.Adapter.Filtertype_Adapter;
import com.allforcars.all4cars.Model.Catagory_Model;
import com.allforcars.all4cars.Model.Type_Model;
import com.allforcars.all4cars.R;
import com.allforcars.all4cars.classes.AppController;
import com.allforcars.all4cars.classes.CircleTransform;
import com.allforcars.all4cars.classes.Permission;
import com.allforcars.all4cars.classes.Utility;
import com.allforcars.all4cars.classes.apicall.FileUploadModel;
import com.allforcars.all4cars.classes.apicall.UpdateMultipartListener;
import com.allforcars.all4cars.classes.apicall.VolleyMultipartRequest;
import com.allforcars.all4cars.classes.apicall.onUpdateViewListener;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddProduct_Activity extends AppCompatActivity {

    Dialog dialogPicProfile;
    private static final int request_camera = 1888;
    private static final int PICK_IMAGE_REQUEST = 3;
    private File photo;
    private Uri uriFilePath;
    RelativeLayout relativelayout;
    String user_id,str_et_unit,user_email,phone_number,user_image,str_username,str_phonenumber,msg;
    com.github.siyamed.shapeimageview.CircularImageView user_imageprofile;
    TextView txt_user_email,txt_submitstud;
    EditText txt_user_name,txt_user_phone,txt_company_nm;
    String usertype,str_useremail,productid,str_company,prouduct_id,category,product_name,banner_image,price;
    EditText et_productprice,et_productname,et_unit;
    SearchableSpinner spinner_categroylist,spinner_unit;
    TextView submitstud;
    RelativeLayout img_back_edit_prof;
    String  str_proudctname,str_productprice,Categorey_id,str_spinner,category_id,Spinnercatory_name,Spinnercaterlist_id;
    ArrayAdapter adapter;
    String  str_units;
    public ArrayList<Catagory_Model> arrayList_total_class = new ArrayList<>();
    AddProduct_Activity.spinnerAdapter dAdapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        et_unit = findViewById(R.id.et_unit);
        user_imageprofile = findViewById(R.id.user_imageprile);
        relativelayout = findViewById(R.id.relativelayout);
        et_productprice=findViewById(R.id.et_productprice);
        et_productname=findViewById(R.id.et_productname);
        submitstud=findViewById(R.id.submitstud);
        img_back_edit_prof=findViewById(R.id.Addproudct_backbtn);
        spinner_categroylist=findViewById(R.id.spinner_categroylist);



        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");


        final Intent intent = getIntent();
        prouduct_id = intent.getStringExtra("prouduct_id");
        Categorey_id = intent.getStringExtra("category_id");
        /*nugut permission*/
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }

        dialogPicProfile = new Dialog(AddProduct_Activity.this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
        dialogPicProfile.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogPicProfile.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogPicProfile.setContentView(R.layout.layouy_camera_galary);


        dialogPicProfile.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        WindowManager.LayoutParams wmlp = dialogPicProfile.getWindow().getAttributes();
        wmlp.gravity = Gravity.BOTTOM;

        relativelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        submitstud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_proudctname = et_productname.getText().toString().trim();
                str_productprice= et_productprice.getText().toString().trim();
                str_spinner = spinner_categroylist.getSelectedItem().toString();
                str_et_unit= et_unit.getText().toString().trim();



                  validation_user();




            }
        });

        img_back_edit_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });







        spinner_categroylist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {


                String item=spinner_categroylist.getSelectedItem().toString();
                if(item.equals("Select Category"))
                {

                }
                else
                {

                    category_id = arrayList_total_class.get(position).getCatagory_id();


                }






            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });






        Get_Categoylist();



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE_REQUEST) {
                Uri img = data.getData();
                String sel_path = getpath(img);
                user_imageprofile.setImageURI(img);

                Glide.with(this).load(img).asBitmap().centerCrop().into(new BitmapImageViewTarget(user_imageprofile));


                //img_profile.setImageURI(img);
                // img_back.setImageURI(img);
            } else if (requestCode == request_camera) {
                try {

                    ExifInterface exif = new ExifInterface(uriFilePath.getPath());
                    int orientation = exif.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_NORMAL);
                    Matrix matrix = new Matrix();

                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            matrix.postRotate(90);
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            matrix.postRotate(180);
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            matrix.postRotate(270);
                            break;
                    }
                    String uri = uriFilePath.getPath();
                    user_imageprofile.setImageURI(uriFilePath);
                    Glide.with(this).load(uriFilePath).asBitmap().centerCrop().into(new BitmapImageViewTarget(user_imageprofile) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(AddProduct_Activity.this.getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            user_imageprofile.setImageDrawable(circularBitmapDrawable);
                        }
                    });
//            Glide.with(this).load(uriFilePath).placeholder(R.mipmap.profile_pic).into(img_profile);
//                    Glide.with(this).load(uriFilePath).placeholder(R.mipmap.ic_launcher).into(img_back);

                    photo = new File(new URI("file://" + uri.replace(" ", "%20")));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void openDialog() {
        final Dialog dialog = new Dialog(AddProduct_Activity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layouy_camera_galary);
        TextView gallary = (TextView) dialog.findViewById(R.id.galary);
        TextView camera = (TextView)dialog.findViewById(R.id.camera);

        dialog.show();

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean readExternal = Permission.checkPermissionReadExternal(AddProduct_Activity.this);
                boolean camera = Permission.checkPermissionCamera(AddProduct_Activity.this);
                if (readExternal || camera) {
                    openCamera();
                    dialog.dismiss();
                }
            }
        });

        gallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean readExternal = Permission.checkPermissionReadExternal(AddProduct_Activity.this);
                boolean camera = Permission.checkPermissionCamera(AddProduct_Activity.this);
                if (readExternal || camera) {
                    showFileChosser();
                    dialog.dismiss();
                }
            }
        });



    }

    private void openCamera() {
        PackageManager packageManager = AddProduct_Activity.this.getPackageManager();
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            File mainDirectory = new File(Environment.getExternalStorageDirectory(), "MyFolder/tmp");
            if (!mainDirectory.exists())
                mainDirectory.mkdirs();
            Calendar calendar = Calendar.getInstance();
            uriFilePath = Uri.fromFile(new File(mainDirectory, "IMG" + calendar.getTimeInMillis() + ".jpg"));
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uriFilePath);
            startActivityForResult(intent, request_camera);
        }
    }

    private void showFileChosser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private String getpath(Uri img) {
        String[] p = {MediaStore.Images.Media.DATA};
        Cursor cursor = AddProduct_Activity.this.getContentResolver().query(img, p, null, null, null);
        int col = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String file_path = cursor.getString(col);

        try {
            photo = new File(new URI("file://" + file_path.replace(" ", "%20")));
//            convert_file_string();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return cursor.getString(col);
    }


    public void validation_user()
    {


        if(str_proudctname.matches(""))
        {
            et_productname.setError("Please Enter Product Name");
            et_productname.requestFocus();
        }



        else if (str_productprice.matches(""))
        {
            et_productprice.setError("Please Enter Product Price");
            et_productprice.requestFocus();
        }

        else if (str_spinner.matches("Select Category"))
        {
            Toast toast = Toast.makeText(AddProduct_Activity.this,"Select Product Category", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        }
        else if(photo==null){
            Toast toast = Toast.makeText(AddProduct_Activity.this,"Please Select Product Image", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        }




        else
        {


               AddProdct_Api();




        }
    }



    private void Get_Categoylist() {
        final ProgressDialog pd = new ProgressDialog(AddProduct_Activity.this);
        pd.setMessage("Please Wait..");
        pd.setCancelable(false);
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        pd.show();
        String urll = Utility.Base_URL + "category_list_byvender";
        StringRequest sr = new StringRequest(Request.Method.POST, urll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {


                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray=jsonObject.getJSONArray("record");


                    for (int i= 0; i<jsonArray.length(); i++){
                        Catagory_Model categlilist = new Catagory_Model();
                        JSONObject record_info = jsonArray.getJSONObject(i);

                        categlilist.setCatagory_id(record_info.getString("category_id"));
                        categlilist.setCatagory_name(record_info.getString("category_names"));
                        arrayList_total_class.add(categlilist);
                    }

                    List<String> listheight = new ArrayList<>();
                    for (int j = 0; j < arrayList_total_class.size(); j++) {
                        listheight.add(arrayList_total_class.get(j).getCatagory_name());

                    }

                    dAdapter1 = new AddProduct_Activity.spinnerAdapter(getApplicationContext(), R.layout.customespinner, listheight);

                    dAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dAdapter1.addAll(listheight);
                    dAdapter1.add("Select Category");
                    spinner_categroylist.setAdapter(dAdapter1);
                    spinner_categroylist.setSelection(dAdapter1.getCount());

                   // spinner_categroylist.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.customespinner, listheight));



                } catch (JSONException e) {


                    //   Toast.makeText(getActivity(), ,+e Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                pd.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AppController.getInstance().getRequestQueue().cancelAll("user_Sign_up");
                pd.dismiss();
                if (error instanceof NetworkError) {
                    Toast.makeText(AddProduct_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(AddProduct_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(AddProduct_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(AddProduct_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(AddProduct_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(AddProduct_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddProduct_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        })

        {
            @Override
            protected Map<String, String> getParams()
                    throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("user_id", user_id);
                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sr);
    }



    private void AddProdct_Api() {

        final ProgressDialog pd = new ProgressDialog(AddProduct_Activity.this);
        pd.setMessage("Please Wait..");
        pd.setCancelable(false);
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        pd.show();
        String url= Utility.Base_URL+"add_product";
        VolleyMultipartRequest request = new VolleyMultipartRequest(url,
                new UpdateMultipartListener(AddProduct_Activity.this, new onUpdateViewListener() {
                    @Override
                    public void updateView(Object response, boolean isSuccess, int reqType) {
                        try {

                            if(isSuccess)
                            {
                                JSONObject jsonObject = new JSONObject(String.valueOf(response));
                                msg=jsonObject.getString("message");
                                if (jsonObject.getString("success").equalsIgnoreCase("true")) {

                                    Toast.makeText(AddProduct_Activity.this, ""+msg, Toast.LENGTH_SHORT).show();

                                     onBackPressed();

                                    pd.dismiss();
                                }
                                else {
                                    Toast.makeText(AddProduct_Activity.this, ""+msg, Toast.LENGTH_SHORT).show();

                                    pd.dismiss();
                                }
                            }
                            else

                            // Toast.makeText(Mychild_Uploadpaymentproof.this, response.toString(), Toast.LENGTH_SHORT).show();
                            {
                                Toast.makeText(AddProduct_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                                pd.dismiss();

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(AddProduct_Activity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }
                    }
                }, 1), FileUploadModel.class, "product_image", photo, getParamss());
        int socketTime = 20000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTime,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(retryPolicy);
        AppController.getInstance().addToRequestQueue(request);
    }


    private HashMap<String, String> getParamss() {

        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", user_id);
        map.put("category_id",category_id);
        map.put("product_name",str_proudctname);
        map.put("product_price",str_productprice);
        map.put("unit",str_et_unit);
        return map;

    }

    public class spinnerAdapter extends ArrayAdapter<String>
    {
        private spinnerAdapter(Context context, int textViewResourceId, List<String> smonking)
        {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount()
        {
            int count = super.getCount();
            return count > 0 ? count - 1 : count;
        }
    }


}
