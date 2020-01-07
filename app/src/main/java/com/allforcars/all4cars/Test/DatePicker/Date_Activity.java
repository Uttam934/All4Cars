package com.allforcars.all4cars.Test.DatePicker;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.allforcars.all4cars.R;
import com.allforcars.all4cars.Test.CustomView.CustomEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Date_Activity extends AppCompatActivity {


     ImageView Dob_btn;
    CustomEditText et_Dob;
    String dte="";
    private int mYear, mMonth, mDay, mHour, mMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);


//        et_Dob= findViewById(R.id.et_Dob);
//
//
//      //  function(et_Dob,Dob_btn);
//
//        et_Dob.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                // Get Current Date
//                final Calendar c = Calendar.getInstance();
//                mYear = c.get(Calendar.YEAR);
//                mMonth = c.get(Calendar.MONTH);
//                mDay = c.get(Calendar.DAY_OF_MONTH);
//
//
//                DatePickerDialog datePickerDialog = new DatePickerDialog(Date_Activity.this,
//                        new DatePickerDialog.OnDateSetListener() {
//
//                            @Override
//                            public void onDateSet(DatePicker view, int year,
//                                                  int monthOfYear, int dayOfMonth) {
//
//                                et_Dob.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
//
//                            }
//                        }, mYear, mMonth, mDay);
//                datePickerDialog .getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
//                datePickerDialog.show();
//
//            }
//        });




    }

    private void function(final EditText date1, ImageView ss) {

        date1.setFocusable(false);
        InputMethodManager imm = (InputMethodManager) Date_Activity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(date1.getWindowToken(), 0);


        ss.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {

                if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
                    final Calendar c = Calendar.getInstance();
                    Integer mYear = c.get(Calendar.YEAR);
                    Integer mMonth = c.get(Calendar.MONTH);
                    Integer mDay = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dpd = new DatePickerDialog(Date_Activity.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker arg0,
                                                      int year, int monthOfYear,
                                                      int dayOfMonth) {

                                    try {
                                        SimpleDateFormat df12 = new SimpleDateFormat("yyyy-MM-dd");
                                        String date = df12.format(c.getTime());
                                        date1.setText(date);
                                       /* date1.setText(year + "-"
                                        + (monthOfYear + 1) + "-" + dayOfMonth);*/
                                        /*date1.setText(dayOfMonth + "-"
                                                + (monthOfYear + 1) + "-" + year);*/


                                    } catch (Exception e) {
                                        e.getMessage();

                                    }
                                }
                            }, mYear, mMonth, mDay);
                    dpd.getDatePicker().setMinDate(new Date().getTime());
                    dpd.getDatePicker().setMaxDate(new Date().getTime());
                    dpd.show();
                }
                return false;
            }
        });

    }
}
