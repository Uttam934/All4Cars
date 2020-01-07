package com.allforcars.all4cars.Test.Bitmapcamer;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.allforcars.all4cars.R;
import com.allforcars.all4cars.Test.Bitmapcamer.Compressor_Imge.BitmapUtils;
import com.allforcars.all4cars.Test.Bitmapcamer.Compressor_Imge.Compressor;
import com.allforcars.all4cars.Test.Bitmapcamer.Compressor_Imge.FileUtil;
import com.allforcars.all4cars.Test.Retrofit_Rxjava.API;
import com.allforcars.all4cars.Test.Retrofit_Rxjava.CheckStatusModel;
import com.allforcars.all4cars.Test.Retrofit_Rxjava.Constant;
import com.allforcars.all4cars.Test.Retrofit_Rxjava.CustomProgressDialog;
import com.allforcars.all4cars.Test.Retrofit_Rxjava.RequestInterface;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Bitmapcamer_Activity extends AppCompatActivity implements View.OnClickListener {

    EditText _edtTransactionNo, _edtRemark;
    Button _btnConfirmPayment, _btnChooseFile;
    ImageView _ivChooseFile;
    CustomProgressDialog progressDialog;
    private CompositeDisposable mCompositeDisposable;

    String _photoPath;
    private Uri _imageCaptureUri;
    private View view;
    Bitmap bitmapChooseFile;
    String[] PERMISSIONS = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public static final int MY_PEQUEST_CODE = 123;
    int bankId;
    String amount, paymentMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmapcamer_);



        initialization();
    }

    public void initialization() {

        mCompositeDisposable = new CompositeDisposable();
        progressDialog = new CustomProgressDialog(this, R.drawable.custom_progress_layout);
        _edtTransactionNo = findViewById(R.id.edt_confirmPaymentActivity_transactionNo);
        _edtRemark = findViewById(R.id.edt_confirmPaymentActivity_remark);

        _ivChooseFile = findViewById(R.id.iv_confirmPaymentActivity_chooseFile);
        _btnChooseFile = findViewById(R.id.btn_confirmPaymentActivity_chooseFile);
        _btnConfirmPayment = findViewById(R.id.btn_confirmPaymentActivity_confirmPayment);

        //click listener
        _btnChooseFile.setOnClickListener(this);
        _btnConfirmPayment.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        view = v;
        switch (v.getId()) {
            case R.id.btn_confirmPaymentActivity_chooseFile:
                if (!hasPermissions(this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(this, PERMISSIONS, MY_PEQUEST_CODE);
                } else {
                    selectPhoto();
                }
                break;
            case R.id.btn_confirmPaymentActivity_confirmPayment:
                addFundNetCall();
                break;
        }
    }

    //===================call login api==============================//
    private void addFundNetCall() {
        if (checkValidation()) {
            progressDialog.show();
            progressDialog.setCancelable(false);

            RequestInterface requestInterface = new Retrofit.Builder()
                    .baseUrl(API.BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(RequestInterface.class);

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("trackid", "21");
                jsonObject.put("UID", "241");
                jsonObject.put("amount", amount);
                jsonObject.put("bankid", bankId);
                jsonObject.put("transid", _edtTransactionNo.getText().toString());
                jsonObject.put("remarks", _edtRemark.getText().toString());
                jsonObject.put("no", "");
                jsonObject.put("paymode", paymentMode);
                jsonObject.put("sponserid", "");
                jsonObject.put("image", getStringImage(bitmapChooseFile));
                jsonObject.put("filename", "temp.jpg");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
            mCompositeDisposable.add(requestInterface.addfund_(requestBody)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        }
    }

    private void handleResponse(CheckStatusModel checkStatusModel) {
        if (progressDialog.isShowing() && progressDialog != null) {
            progressDialog.dismiss();
        }
//        if (checkStatusModel.isStatus()) {
//            Alertdialog(Bitmapcamer_Activity.this, checkStatusModel.getMessage(), true);
//            // Constant.Alertdialog(Bitmapcamer_Activity.this, checkStatusModel.getMessage(), true);
//            // clearView();
//        } else {
//            Constant.Alertdialog(Bitmapcamer_Activity.this, checkStatusModel.getMessage(), false);
//        }
    }

    private void handleError(Throwable error) {
        if (progressDialog.isShowing() && progressDialog != null) {
            progressDialog.dismiss();
        }
       // Constant.toast(this, "Server Error");
    }




    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PEQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean cameraStateAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean readExternalStorageStateAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean writeExternalStorageStateAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    if (cameraStateAccepted && readExternalStorageStateAccepted && writeExternalStorageStateAccepted) {
                        selectPhoto();
                    } else {
                        Snackbar.make(view, "Permission Denied", Snackbar.LENGTH_LONG).show();
                    }
                }
        }
    }

    private boolean checkValidation() {
        final View focusview;
        String transactionNo = _edtTransactionNo.getText().toString();
        String remark = _edtRemark.getText().toString();

        if (transactionNo.equals("")) {
            _edtTransactionNo.setError("Please enter the transaction no");
            focusview = _edtTransactionNo;
            focusview.requestFocus();
            return false;
        } else if (remark.equals("")) {
            _edtRemark.setError("Please enter the remark");
            focusview = _edtRemark;
            focusview.requestFocus();
            return false;
        } else if (bitmapChooseFile == null) {
            Constant.toast(this, "please choose the file");
            return false;
        } else if (Constant.isNetworkAvailable(this)) {
            return true;
        } else {
            return false;
        }
    }

    public void clearView() {
        _edtTransactionNo.setText("");
        _edtRemark.setText("");
        bitmapChooseFile = null;
        _ivChooseFile.setImageResource(R.drawable.ic_date);
    }

    //===========code for click image and get image from gallery===============//

    private void selectPhoto() {
        final String[] items = {"Take photo", "Choose from Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    doTakePhoto();

                } else {
                    doTakeGallery();
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void doTakeGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, Constants.PICK_FROM_ALBUM);
    }

    private void doTakePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String picturePath = BitmapUtils.getTempFolderPath() + "photo_temp.jpg";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            _imageCaptureUri = FileProvider.getUriForFile(getApplicationContext(), this.getPackageName() + ".provider", new File(picturePath));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, _imageCaptureUri);
            startActivityForResult(intent, Constants.CROP_FROM_CAMERA);
        } else {
            _imageCaptureUri = Uri.fromFile(new File(picturePath));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, _imageCaptureUri);
            startActivityForResult(intent, Constants.PICK_FROM_CAMERA);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            switch (requestCode) {
                case Constants.CROP_FROM_CAMERA: {
                    if (resultCode == RESULT_OK) {
                        try {
                            File cameraFile = FileUtil.from(this, _imageCaptureUri);

                            //=========compress image code===================//
                            if (cameraFile == null) {
                                Constant.toast(this, "Please click an image!");
                            } else {
                                // Compress image in main thread using custom Compressor
                                try {
                                    File compressedCameraImage = new Compressor(this)
                                            .setMaxWidth(640)
                                            .setMaxHeight(480)
                                            .setQuality(100)
                                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
                                            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                                    Environment.DIRECTORY_PICTURES).getAbsolutePath())
                                            .compressToFile(cameraFile);

                                    _ivChooseFile.setImageBitmap(BitmapFactory.decodeFile(compressedCameraImage.getAbsolutePath()));
                                    bitmapChooseFile = BitmapFactory.decodeFile(compressedCameraImage.getAbsolutePath());


                                } catch (IOException e) {
                                    e.printStackTrace();
                                    //Constant.toast(this, e.getMessage());
                                }
                            }
                            //============================//
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
                case Constants.PICK_FROM_ALBUM:

                    if (resultCode == RESULT_OK) {
                        _imageCaptureUri = data.getData();
                        try {
                            File galleryFile = FileUtil.from(this, _imageCaptureUri);
                            //=========compress image code===================//

                            if (galleryFile == null) {
                                Constant.toast(this, "Please choose an image!");
                            } else {
                                // Compress image using RxJava in background thread
                                try {
                                    File compressedGalleryImage = new Compressor(this)
                                            .setMaxWidth(640)
                                            .setMaxHeight(480)
                                            .setQuality(100)
                                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
                                            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                                    Environment.DIRECTORY_PICTURES).getAbsolutePath())
                                            .compressToFile(galleryFile);

                                    _ivChooseFile.setImageBitmap(BitmapFactory.decodeFile(compressedGalleryImage.getAbsolutePath()));
                                    bitmapChooseFile = BitmapFactory.decodeFile(compressedGalleryImage.getAbsolutePath());

                                } catch (IOException e) {
                                    e.printStackTrace();
                                    // Constant.toast(this, e.getMessage());
                                }
                            }
                            //============================//

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        } else {

            switch (requestCode) {
                case Constants.CROP_FROM_CAMERA: {
                    if (resultCode == RESULT_OK) {
                        try {
                            File saveFile = BitmapUtils.getOutputMediaFile(this);
                            InputStream in = this.getContentResolver().openInputStream(Uri.fromFile(saveFile));
                            BitmapFactory.Options bitOpt = new BitmapFactory.Options();
                            Bitmap bitmap = BitmapFactory.decodeStream(in, null, bitOpt);
                            in.close();
                            //set The bitmap data to image View
                            _photoPath = saveFile.getAbsolutePath();

                            _ivChooseFile.setImageBitmap(bitmap);
                            bitmapChooseFile = bitmap;

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
                case Constants.PICK_FROM_ALBUM:
                    if (resultCode == RESULT_OK) {
                        _imageCaptureUri = data.getData();
                    }
                case Constants.PICK_FROM_CAMERA: {
                    try {
                        _photoPath = BitmapUtils.getRealPathFromURI(this, _imageCaptureUri);

                        Intent intent = new Intent("com.android.camera.action.CROP");
                        intent.setDataAndType(_imageCaptureUri, "image");

                        intent.putExtra("crop", true);
                        intent.putExtra("scale", true);
                        intent.putExtra("outputX", 256);
                        intent.putExtra("outputY", 256);
                        intent.putExtra("aspectX", 1);
                        intent.putExtra("aspectY", 1);

                        intent.putExtra("noFaceDetection", true);
                        //intent.putExtra("return-data", true);
                        intent.putExtra("output", Uri.fromFile(BitmapUtils.getOutputMediaFile(this)));

                        startActivityForResult(intent, Constants.CROP_FROM_CAMERA);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 60, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}
