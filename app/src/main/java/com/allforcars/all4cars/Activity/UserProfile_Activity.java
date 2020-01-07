package com.allforcars.all4cars.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
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
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UserProfile_Activity extends AppCompatActivity {

    Dialog dialogPicProfile;
    private static final int request_camera = 1888;
    private static final int PICK_IMAGE_REQUEST = 3;
    private File photo;
    private Uri uriFilePath;
    RelativeLayout relativelayout;
    String user_id,user_name,user_email,phone_number,user_image,str_username,str_phonenumber,msg,created_at;
    com.github.siyamed.shapeimageview.CircularImageView user_imageprofile;
    TextView txt_submitstud;
    EditText txt_user_name,txt_user_phone,edittxt_dob,txt_user_email,edittxt_dateofsubrionn;
    ImageView img_back_edit_prof,Dateofbirht;
    String usertype,birthday_day,str_dob;
    private int mYear, mMonth, mDay;
    private Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        edittxt_dateofsubrionn = findViewById(R.id.edittxt_dateofsubrionn);
        edittxt_dob = findViewById(R.id.edittxt_dob);
        user_imageprofile = findViewById(R.id.user_imageprile);
        relativelayout = findViewById(R.id.relativelayout);
        txt_user_name = findViewById(R.id.txt_user_name);
        txt_user_email = findViewById(R.id.txt_user_email);
        txt_user_phone = findViewById(R.id.txt_user_phone);
        txt_submitstud = findViewById(R.id.txt_submitstud);
        img_back_edit_prof = findViewById(R.id.img_back_edit_prof);
        Dateofbirht = findViewById(R.id.Dateofbirht);
        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id =sharedpreference.getString("user_id","");
        usertype =sharedpreference.getString("usertype","");



        /*nugut permission*/
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }

        dialogPicProfile = new Dialog(UserProfile_Activity.this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
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

        txt_submitstud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_username = txt_user_name.getText().toString().trim();
                str_phonenumber = txt_user_phone.getText().toString().trim();
                str_dob = edittxt_dob.getText().toString().trim();
                UpdateProfile_Api();

            }
        });

        img_back_edit_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });



        Dateofbirht.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(UserProfile_Activity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth)

                            {
                                if(dayOfMonth<10 && monthOfYear<10)
                                {
                                    edittxt_dob.setText("0"+dayOfMonth + "/" +"0"+ (monthOfYear + 1) + "/" + year);

                                }
                                else if(monthOfYear<10 && dayOfMonth>10)
                                {
                                    edittxt_dob.setText(dayOfMonth + "/" +"0"+ (monthOfYear + 1) + "/" + year);
                                }
                                else if(dayOfMonth<10 && monthOfYear>10)
                                {
                                    edittxt_dob.setText("0"+dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                }
                                else
                                {
                                    edittxt_dob.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                }
                            }

                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                datePickerDialog.getDatePicker().setMaxDate(myCalendar.getTimeInMillis());


            }
        });



        Getuserinfo_api();


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
                                    RoundedBitmapDrawableFactory.create(UserProfile_Activity.this.getResources(), resource);
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
        final Dialog dialog = new Dialog(UserProfile_Activity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layouy_camera_galary);
        TextView gallary = (TextView) dialog.findViewById(R.id.galary);
        TextView camera = (TextView)dialog.findViewById(R.id.camera);
      //  TextView cancle = (TextView)dialog.findViewById(R.id.cancle);
        dialog.show();

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean readExternal = Permission.checkPermissionReadExternal(UserProfile_Activity.this);
                boolean camera = Permission.checkPermissionCamera(UserProfile_Activity.this);
                if (readExternal || camera) {
                    openCamera();
                    dialog.dismiss();
                }
            }
        });

        gallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean readExternal = Permission.checkPermissionReadExternal(UserProfile_Activity.this);
                boolean camera = Permission.checkPermissionCamera(UserProfile_Activity.this);
                if (readExternal || camera) {
                    showFileChosser();
                    dialog.dismiss();
                }
            }
        });



    }

    private void openCamera() {
        PackageManager packageManager = UserProfile_Activity.this.getPackageManager();
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
        Cursor cursor = UserProfile_Activity.this.getContentResolver().query(img, p, null, null, null);
        int col = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String file_path = cursor.getString(col);

        try {
            //photo = new File(new URI("file://" + file_path.replace(" ", "%20")));
            photo = new File(new URI("file://" +""));
//            convert_file_string();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return cursor.getString(col);
    }



    private void Getuserinfo_api() {
        final ProgressDialog pd = new ProgressDialog(UserProfile_Activity.this);
        pd.setCancelable(false);
        pd.setMessage("Please wait..");
        pd.show();
        String urll= Utility.Base_URL+"get_user_data";
        StringRequest sr = new StringRequest(Request.Method.POST, urll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getString("success").equalsIgnoreCase("true"))
                    {
                        JSONArray jsonArray=jsonObject.getJSONArray("record");

                        for(int i=0;i<jsonArray.length();i++)


                        {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            user_name = (jsonObject1.getString("name"));
                            user_email = (jsonObject1.getString("email"));
                            phone_number = (jsonObject1.getString("phone_number"));
                            user_image = (jsonObject1.getString("user_image"));
                            birthday_day = (jsonObject1.getString("birthday_day"));
                            created_at = (jsonObject1.getString("created_at"));
                            edittxt_dob.setText(birthday_day);


                            txt_user_name.setText(user_name);
                            txt_user_email.setText(user_email);
                            txt_user_phone.setText(phone_number);
                            edittxt_dateofsubrionn.setText(created_at);



                            if(user_image.equals("null"))
                            {
                                user_imageprofile.setImageDrawable(getResources().getDrawable(R.drawable.users));

                            }
                            else {

                                String url_image = Utility.Base_Image_Url + user_image;
                                Glide.with(UserProfile_Activity.this).load(url_image)
                                        .crossFade()
                                        .thumbnail(0.5f)
                                        .bitmapTransform(new CircleTransform(UserProfile_Activity.this))
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(user_imageprofile);


                            }

                        }


                    }
                    else{

                    }

                } catch (JSONException e) {


                    //Toast.makeText(Create_New_Check_Activity.this, "Please Enter Registered Email And Password", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(UserProfile_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(UserProfile_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(UserProfile_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(UserProfile_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(UserProfile_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(UserProfile_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UserProfile_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        })

        {
            @Override
            protected Map<String, String> getParams()
                    throws AuthFailureError { HashMap<String, String> map = new HashMap<>();
                map.put("user_id", user_id);
                map.put("user_type", usertype);

                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sr);
//        sr.setRetryPolicy(new DefaultRetryPolicy(0,-1,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


    private void UpdateProfile_Api() {

        final ProgressDialog pd = new ProgressDialog(UserProfile_Activity.this);
        pd.setMessage("Please Wait..");
        pd.setCancelable(false);
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        pd.show();
        String url= "http://3.6.35.180/m-api/borrowers/6";
        VolleyMultipartRequest request = new VolleyMultipartRequest(url,
                new UpdateMultipartListener(UserProfile_Activity.this, new onUpdateViewListener() {
                    @Override
                    public void updateView(Object response, boolean isSuccess, int reqType) {
                        try {





                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(UserProfile_Activity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }
                    }
                }, 1), FileUploadModel.class, "farmer_image", photo, getParamss());
        int socketTime = 20000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTime,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(retryPolicy);
        AppController.getInstance().addToRequestQueue(request);
    }

    private HashMap<String, String> getParamss() {

        HashMap<String, String> map = new HashMap<>();
        map.put("name", "fdf");
        map.put("m_no", "55");
        map.put("address","fdf");
        map.put("village","fdf");
        map.put("father_name", "fdf");
        map.put("aadhar_no", "fdf");
        map.put("pan_no","fdf");
        map.put("landholding","fdf");
        map.put("crops_grown", "fdf");
        map.put("existing_credit_facility", "552");
        map.put("total_existing_loan_outstanding_amount","552");
        map.put("total_current_emi_amount","522");
        map.put("total_monthly_expenses","522");
        map.put("additional_income_source","522");
        map.put("additional_monthly_income","522");

        return map;




    }


}
