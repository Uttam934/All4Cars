package com.allforcars.all4cars.Venderlogin.Adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.allforcars.all4cars.Activity.Mycart_Activity;
import com.allforcars.all4cars.Activity.OrderDeatil_Activity;
import com.allforcars.all4cars.Model.Histroyorder_Model;
import com.allforcars.all4cars.R;
import com.allforcars.all4cars.Venderlogin.Activity.Orderdeatil_Activity;
import com.allforcars.all4cars.Venderlogin.Activity.Orderlist_Activity;
import com.allforcars.all4cars.classes.AppController;
import com.allforcars.all4cars.classes.Utility;
import com.allforcars.all4cars.interfaces.RefreshInterface;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.allforcars.all4cars.classes.Utility.Base_URL;


public class Venderorderlist_Adapter extends RecyclerView.Adapter<Venderorderlist_Adapter.ViewHolder> {

    private Context context;
    private ArrayList<com.allforcars.all4cars.Model.Histroyorder_Model> Histroyorder_Model;
    String order_number,subamt,totaldis,totalamt,vender_id,Filter_status,str_note="";
    ArrayAdapter adapter;
    RefreshInterface refreshInterface;
    Dialog dialog_main;
    RadioButton rb_Process;
    RadioButton rb_Rejected;
    RadioButton rb_Complete;
    RadioGroup radio_gr_lan_login;
    EditText edittxt_note;
    String Payment_Type;

    public Venderorderlist_Adapter(Context context, ArrayList<com.allforcars.all4cars.Model.Histroyorder_Model> Recyclemodel,RefreshInterface refreshInterface ) {
        this.context = context;
        this.refreshInterface = refreshInterface;
        this.Histroyorder_Model = Recyclemodel;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.venderorderlist, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.itemView.setTag(Histroyorder_Model.get(position));


        holder.txt_venderorder.setText(Histroyorder_Model.get(position).getorder_number());
        holder.txt_venderordrdate.setText(Histroyorder_Model.get(position).getorderdate());
        holder.vender_paymenttype.setText(Histroyorder_Model.get(position).getpaymettype());
        holder.vender_paymenstatus.setText(Histroyorder_Model.get(position).getstatus());
        holder.vendder_txt_totlamt.setText(Histroyorder_Model.get(position).gettotoalpayamt()+"₦");


        holder.btn_changestaus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                custom_dialog_box();
                order_number=Histroyorder_Model.get(position).getorder_number();
                vender_id=Histroyorder_Model.get(position).getvender_id();
            }
        });

        if(Histroyorder_Model.get(position).gethide_btn().equals("1"))
        {
            holder.btn_changestaus.setVisibility(View.GONE);
        }
        else {
            holder.btn_changestaus.setVisibility(View.VISIBLE);
        }



        holder.btn_viewroder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                order_number=Histroyorder_Model.get(position).getorder_number();
                subamt=Histroyorder_Model.get(position).getsubtotoal()+"₦";
                totaldis=Histroyorder_Model.get(position).gettotdiscount()+"₦";
                totalamt=Histroyorder_Model.get(position).gettotoalpayamt()+"₦";

                Intent ints = new Intent(context,Orderdeatil_Activity.class);
                ints.putExtra("order_number", order_number);
                ints.putExtra("subamt", subamt);
                ints.putExtra("totaldis", totaldis);
                ints.putExtra("totalamt", totalamt);
                context.startActivity(ints);

            }
        });





    }


    @Override
    public int getItemCount() {
        return Histroyorder_Model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView vender_paymenstatus,txt_venderorder,btn_viewroder,txt_venderordrdate,vender_paymenttype,vendder_txt_totlamt,txt_totlamt;
        LinearLayout btn_changestaus;



        public ViewHolder(View itemView) {
            super(itemView);

            txt_venderorder = itemView.findViewById(R.id.txt_venderorder);
            txt_venderordrdate = itemView.findViewById(R.id.txt_venderordrdate);
            vender_paymenttype = itemView.findViewById(R.id.vender_paymenttype);
            vender_paymenstatus = itemView.findViewById(R.id.vender_paymenstatus);
            vendder_txt_totlamt = itemView.findViewById(R.id.vendder_txt_totlamt);
            btn_viewroder = itemView.findViewById(R.id.btn_viewroder);
            btn_changestaus = itemView.findViewById(R.id.btn_changestaus);




        }
    }




    public void custom_dialog_box(){
        dialog_main = new Dialog(context);
        dialog_main.setContentView(R.layout.changestatus_layout);
        dialog_main.setCancelable(false);

        final TextView txt_submit = dialog_main.findViewById(R.id.txt_submit);
         rb_Process = dialog_main.findViewById(R.id.rb_Process);
         rb_Rejected = dialog_main.findViewById(R.id.rb_Rejected);
         rb_Complete = dialog_main.findViewById(R.id.rb_Complete);
         edittxt_note = dialog_main.findViewById(R.id.edittxt_note);
         radio_gr_lan_login = dialog_main.findViewById(R.id.radio_gr_lan_login);


        rb_Process.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Payment_Type ="2";
            }
        });

        rb_Rejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Payment_Type ="3";

            }
        });
        rb_Complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Payment_Type ="4";

            }
        });




        txt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (radio_gr_lan_login.getCheckedRadioButtonId() == -1)
                {
                    Toast toast = Toast.makeText(context,"Select Order Status", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else {


                    str_note=edittxt_note.getText().toString();

                    if(Payment_Type.equals("3"))
                    {

                        if (str_note.matches("")) {
                            edittxt_note.setError("Reason for Cancel order");
                            edittxt_note.requestFocus();
                        }
                        else {

                            Editstatus();
                        }

                    }
                    else  if(Payment_Type.equals("2"))
                    {
                        Editstatus();
                    }

                    else  if(Payment_Type.equals("4"))
                    {
                        Editstatus();
                    }

                    else {


                    }


                }








            }
        });




        dialog_main.show();

        dialog_main.setCanceledOnTouchOutside(true);

    }


    public void  Editstatus(){

        String urljsonobj_group = Base_URL+"change_order_status?vender_id="+vender_id+"&order_number="+order_number+"&status="+Payment_Type+"&status_resion="+str_note;
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        JsonObjectRequest jsonObjReq_group = new JsonObjectRequest(Request.Method.GET, urljsonobj_group, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    String message = response.getString("message");
                    if (response.getString("success").equalsIgnoreCase("true")) {

                        notifyDataSetChanged();
                        refreshInterface.Refresh();

                        dialog_main.dismiss();
                        Toast toast = Toast.makeText(context,message, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();



                    }
                    else {

                        Toast toast = Toast.makeText(context,message, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                    }



                }
                catch (JSONException e) {
                    AppController.getInstance().getRequestQueue().cancelAll("survey_list");
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                AppController.getInstance().getRequestQueue().cancelAll("survey_list");
                progressDialog.dismiss();
                if (error instanceof NetworkError) {
                    Toast.makeText(context, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(context, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(context, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(context, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(context, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(context, "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq_group, "survey_list");
    }

    }