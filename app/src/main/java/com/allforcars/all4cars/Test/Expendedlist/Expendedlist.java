package com.allforcars.all4cars.Test.Expendedlist;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import com.allforcars.all4cars.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class Expendedlist extends Activity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expended_main);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
      //  prepareListData();
      new Add_Child().execute();



        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });
    }

    /*
     * Preparing the list data
     */


    private class Add_Child extends AsyncTask<String, String, String> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(Expendedlist.this);
            pDialog.setCancelable(false);
            pDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... arg0) {

            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create(mediaType, "user_id="+"24"+"&user_type="+"2");
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url("http://itdevelopmentservices.com/all4cars/api/get_user_data")
                    .post(body)
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("postman-token", "73cca086-4a24-e89c-c90d-c6fd86ce2759")
                    .build();


            okhttp3.Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
                e.getMessage();
            }
            try {
                if (response!=null){
                    return response.body().string();
                }
                else {
                    return "";
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }
        @Override
        protected void onPostExecute(String response) {

            pDialog.dismiss();
            Log.d("check",response.toString());

            if (response.equals("")){
                Toast.makeText(getApplicationContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();

            }
            else {
                try {
                    JSONObject jsonObj = new JSONObject(response);

                    listDataHeader = new ArrayList<>();
                    listDataChild = new HashMap<>();



                    String success = jsonObj.getString("success");
                    if (success.equals("true")) {

                        JSONArray jsonArray=jsonObj.getJSONArray("record");

                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String user_name = (jsonObject1.getString("name"));
                            String user_email = (jsonObject1.getString("email"));
                            String phone_number = (jsonObject1.getString("phone_number"));
                            String user_image = (jsonObject1.getString("user_image"));

                            listDataHeader.add("Data");

                            List<String> data = new ArrayList<>();
                            data.add(user_name);
                            data.add(user_email);
                            data.add(phone_number);
                            data.add(user_image);

                            listDataChild.put(listDataHeader.get(0), data);
                            listDataHeader.add("Top 250");


                            // Adding child data
                            List<String> top250 = new ArrayList<String>();
                            top250.add("The Shawshank Redemption");
                            top250.add("The Godfather");
                            top250.add("The Godfather: Part II");
                            top250.add("Pulp Fiction");
                            top250.add("The Good, the Bad and the Ugly");
                            top250.add("The Dark Knight");
                            top250.add("12 Angry Men");


                            listDataChild.put(listDataHeader.get(1), top250);
                            // Adapter code is added here.
                            listAdapter = new ExpandableListAdapter(getApplicationContext(),listDataHeader,listDataChild);
                            expListView.setAdapter(listAdapter);

                        }



                    }else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}