package com.allforcars.all4cars.ArctecherMvpMvvm.MVVM.View;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.allforcars.all4cars.ArctecherMvpMvvm.MVVM.Adapter.FeedAdapter;
import com.allforcars.all4cars.ArctecherMvpMvvm.MVVM.Modle.Record;
import com.allforcars.all4cars.ArctecherMvpMvvm.MVVM.Viewmodel.FeedViewModel;
import com.allforcars.all4cars.R;

import java.util.ArrayList;
import java.util.List;

public class Mvvm_View_Activity extends AppCompatActivity {

    private static final String TAG = "Response";
    int i = 0;
    private List<Record> recordArrayList = new ArrayList<>();
    private List<Record> records = new ArrayList();
    private RecyclerView feedRecyclerView;
    private FeedAdapter feedAdapter;
    private FeedViewModel feedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        feedRecyclerView = findViewById(R.id.recyclerView_historyFragment);

        feedViewModel = ViewModelProviders.of(this).get(FeedViewModel.class);
        feedViewModel.init();

        feedViewModel.getFeedRepository().observe(this, jsonResponse -> {

            records = jsonResponse.getRecord();

            recordArrayList.addAll(records);
            feedAdapter.notifyDataSetChanged();
        });
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        if (feedAdapter == null) {
            feedAdapter = new FeedAdapter(getApplicationContext(), recordArrayList);
            feedRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            feedRecyclerView.setAdapter(feedAdapter);
            feedRecyclerView.setItemAnimator(new DefaultItemAnimator());
            feedRecyclerView.setNestedScrollingEnabled(true);
        } else {
            feedAdapter.notifyDataSetChanged();
        }
    }
}
