package com.allforcars.all4cars.Venderlogin.Activity;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allforcars.all4cars.Activity.EditProfile_Activity;
import com.allforcars.all4cars.Activity.Login_Activity;
import com.allforcars.all4cars.R;
import com.allforcars.all4cars.classes.AppController;
import com.allforcars.all4cars.classes.CircleTransform;
import com.allforcars.all4cars.classes.Permission;
import com.allforcars.all4cars.classes.Utility;
import com.allforcars.all4cars.classes.apicall.FileUploadModel;
import com.allforcars.all4cars.classes.apicall.NewVolleyMultipartRequest;
import com.allforcars.all4cars.classes.apicall.UpdateMultipartListener;
import com.allforcars.all4cars.classes.apicall.VolleyMultipartRequest;
import com.allforcars.all4cars.classes.apicall.onUpdateViewListener;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import okhttp3.internal.Util;

public class VenderPorfileupdate_Activity extends AppCompatActivity {

    Dialog dialogPicProfile;
    private static final int request_camera = 1888;
    private static final int PICK_IMAGE_REQUEST = 3;
    private File photo, bannerimg;
    private Uri uriFilePath;
    RelativeLayout relativelayout;
    private  String user_id, user_name, user_email, phone_number, user_image, str_username, created_at,str_phonenumber, msg;
    com.github.siyamed.shapeimageview.CircularImageView user_imageprofile;
    TextView txt_user_email, txt_submitstud;
    private EditText txt_user_name, txt_user_phone, txt_company_nm,edittxt_dateofsubcrip;
    ImageView img_back_edit_prof, img_banner_main;
    String usertype, str_useremail, Compnay_name, str_company, selectimagetype,Banner_image;
    RelativeLayout banner_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vender_porfileupdate);

        img_banner_main = findViewById(R.id.img_banner_main);
        user_imageprofile = findViewById(R.id.user_imageprile);
        relativelayout = findViewById(R.id.relativelayout);
        txt_user_name = findViewById(R.id.txt_user_name);
        txt_user_email = findViewById(R.id.txt_user_email);
        txt_user_phone = findViewById(R.id.txt_user_phone);
        txt_submitstud = findViewById(R.id.txt_submitstud);
        img_back_edit_prof = findViewById(R.id.img_back_edit_prof);
        txt_company_nm = findViewById(R.id.txt_company_nm);
        banner_image = findViewById(R.id.banner_image);
        edittxt_dateofsubcrip = findViewById(R.id.edittxt_dateofsubcrip);

        SharedPreferences sharedpreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id = sharedpreference.getString("user_id", "");
        usertype = sharedpreference.getString("usertype", "");



        /*nugut permission*/
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }

        dialogPicProfile = new Dialog(VenderPorfileupdate_Activity.this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
        dialogPicProfile.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogPicProfile.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogPicProfile.setContentView(R.layout.layouy_camera_galary);


        dialogPicProfile.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        WindowManager.LayoutParams wmlp = dialogPicProfile.getWindow().getAttributes();
        wmlp.gravity = Gravity.BOTTOM;


        txt_submitstud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_username = txt_user_name.getText().toString().trim();
                str_phonenumber = txt_user_phone.getText().toString().trim();
                str_useremail = txt_user_email.getText().toString().trim();
                str_company = txt_company_nm.getText().toString().trim();

                UpdateProfile_Api();

            }
        });



        img_back_edit_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        relativelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectimagetype = "1";

                openDialog();
            }
        });

        banner_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectimagetype = "2";

                openDialog();


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

                if (selectimagetype.equals("1")) {

                    user_imageprofile.setImageURI(img);
                    Glide.with(this).load(img).asBitmap().centerCrop().into(new BitmapImageViewTarget(user_imageprofile));
                } else {

                    img_banner_main.setImageURI(img);
                    Glide.with(this).load(img).asBitmap().into(new BitmapImageViewTarget(img_banner_main));

                }


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

                    if (selectimagetype.equals("1"))

                    {
                        user_imageprofile.setImageURI(uriFilePath);
                        Glide.with(this).load(uriFilePath).asBitmap().centerCrop().into(new BitmapImageViewTarget(user_imageprofile) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(VenderPorfileupdate_Activity.this.getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                user_imageprofile.setImageDrawable(circularBitmapDrawable);
                            }
                        });
                        photo = new File(new URI("file://" + uri.replace(" ", "%20")));

                    }

                    else {

                        img_banner_main.setImageURI(uriFilePath);
                        Glide.with(this).load(uriFilePath).asBitmap().centerCrop().into(new BitmapImageViewTarget(img_banner_main) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(VenderPorfileupdate_Activity.this.getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                img_banner_main.setImageDrawable(circularBitmapDrawable);
                            }
                        });

                        bannerimg = new File(new URI("file://" + uri.replace(" ", "%20")));

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }









    private void openDialog() {
        final Dialog dialog = new Dialog(VenderPorfileupdate_Activity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layouy_camera_galary);
        TextView gallary = (TextView) dialog.findViewById(R.id.galary);
        TextView camera = (TextView) dialog.findViewById(R.id.camera);
        //  TextView cancle = (TextView)dialog.findViewById(R.id.cancle);
        dialog.show();

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean readExternal = Permission.checkPermissionReadExternal(VenderPorfileupdate_Activity.this);
                boolean camera = Permission.checkPermissionCamera(VenderPorfileupdate_Activity.this);
                if (readExternal || camera) {
                    openCamera();
                    dialog.dismiss();
                }
            }
        });

        gallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean readExternal = Permission.checkPermissionReadExternal(VenderPorfileupdate_Activity.this);
                boolean camera = Permission.checkPermissionCamera(VenderPorfileupdate_Activity.this);
                if (readExternal || camera) {
                    showFileChosser();
                    dialog.dismiss();
                }
            }
        });


    }

    private void openCamera() {
        PackageManager packageManager = VenderPorfileupdate_Activity.this.getPackageManager();
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
        Cursor cursor = VenderPorfileupdate_Activity.this.getContentResolver().query(img, p, null, null, null);
        int col = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String file_path = cursor.getString(col);

        try {

            if (selectimagetype.equals("1"))
            {
                photo = new File(new URI("file://" + file_path.replace(" ", "%20")));

            }
          else {
                bannerimg = new File(new URI("file://" + file_path.replace(" ", "%20")));
            }




        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return cursor.getString(col);
    }


    private void getDropboxIMGSize(Uri uri){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(new File(uri.getPath()).getAbsolutePath(), options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;


    }




    private void Getuserinfo_api() {
        final ProgressDialog pd = new ProgressDialog(VenderPorfileupdate_Activity.this);
        pd.setCancelable(false);
        pd.setMessage("Please wait..");
        pd.show();
        String urll = Utility.Base_URL + "get_user_data";
        StringRequest sr = new StringRequest(Request.Method.POST, urll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getString("success").equalsIgnoreCase("true")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("record");

                        for (int i = 0; i < jsonArray.length(); i++)


                        {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            user_name = (jsonObject1.getString("fld_name"));
                            user_email = (jsonObject1.getString("fld_email"));
                            phone_number = (jsonObject1.getString("phone"));
                            user_image = (jsonObject1.getString("logo"));
                            Compnay_name = (jsonObject1.getString("company_name"));
                            Banner_image = (jsonObject1.getString("image"));
                            created_at = (jsonObject1.getString("created_at"));


                            txt_user_name.setText(user_name);
                            txt_user_email.setText(user_email);
                            txt_user_phone.setText(phone_number);
                            txt_company_nm.setText(Compnay_name);
                            edittxt_dateofsubcrip.setText(created_at);


                            if (user_image.equals(null)) {
                                user_imageprofile.setImageDrawable(getResources().getDrawable(R.drawable.users));

                            } else {

                                String url_image = Utility.logo_url + user_image;
                                Glide.with(VenderPorfileupdate_Activity.this).load(url_image)
                                        .crossFade()
                                        .thumbnail(0.5f)
                                        .bitmapTransform(new CircleTransform(VenderPorfileupdate_Activity.this))
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(user_imageprofile);

                            }



                            if (Banner_image.equals(null)) {
                               // img_banner_main.setImageDrawable(getResources().getDrawable(R.drawable.users));

                            } else {

                                String url_image = Utility.Vender_banner + Banner_image;
                                Glide.with(VenderPorfileupdate_Activity.this).load(url_image)
                                        .crossFade()
//                                        .thumbnail(0.5f)
//                                        .bitmapTransform(new CircleTransform(VenderPorfileupdate_Activity.this))
//                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(img_banner_main);

                            }


                        }


                    } else {

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
                    Toast.makeText(VenderPorfileupdate_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(VenderPorfileupdate_Activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(VenderPorfileupdate_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(VenderPorfileupdate_Activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(VenderPorfileupdate_Activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(VenderPorfileupdate_Activity.this, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(VenderPorfileupdate_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                map.put("user_type", usertype);


                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sr);
//        sr.setRetryPolicy(new DefaultRetryPolicy(0,-1,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }





    public void UpdateProfile_Api() {
        final ProgressDialog pd = new ProgressDialog(VenderPorfileupdate_Activity.this);
        pd.setMessage("Please Wait..");
        pd.setCancelable(false);
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        pd.show();

        NewVolleyMultipartRequest volleyMultipartRequest = new NewVolleyMultipartRequest(Request.Method.POST, Utility.Base_URL + "edit_vendorsprofile",
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {



                                JSONObject jsonObject = new JSONObject(new String(response.data));
                                msg = jsonObject.getString("message");

                            Toast toast = Toast.makeText(VenderPorfileupdate_Activity.this,msg, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            pd.dismiss();
                             Intent ints = new Intent(VenderPorfileupdate_Activity.this,Vender_Profile_Activity.class);
                             startActivity(ints);
                             finish();

                                if (jsonObject.getString("success").equalsIgnoreCase("true")) {

                                    pd.dismiss();
                                } else {
                                    Toast.makeText(VenderPorfileupdate_Activity.this, "" + msg, Toast.LENGTH_SHORT).show();

                                    pd.dismiss();
                                }


//                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        VolleyLog.d("", "Error: " + volleyError.getMessage());
                        String message = null;
                        if (volleyError instanceof NetworkError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (volleyError instanceof ServerError) {
                            message = "The server could not be found. Please try again after some time!!";
                        } else if (volleyError instanceof AuthFailureError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (volleyError instanceof ParseError) {
                            message = "Parsing error! Please try again after some time!!";
                        } else if (volleyError instanceof NoConnectionError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (volleyError instanceof TimeoutError) {
                            message = "Connection TimeOut! Please check your internet connection.";
                        }
                        pd.dismiss();
                    }
                }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("access", "1");
                params.put("vender_id", user_id);
                params.put("company_name", str_company);
                params.put("email", str_useremail);
                params.put("phone", str_phonenumber);
                params.put("user_type", usertype);
                params.put("vender_name", str_username);
                return params;
            }


            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();

                if(photo!=null)
                {
                    String filePath = photo.getPath();
                    Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                    params.put("logo", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                }
                else {

                }

                if(bannerimg!=null)
                {
                    String filePath1 = bannerimg.getPath();
                    Bitmap bitmap1 = BitmapFactory.decodeFile(filePath1);
                    params.put("banner_image", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap1)));
                }
                else {


                }



                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.getCache().clear();


    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }



}





