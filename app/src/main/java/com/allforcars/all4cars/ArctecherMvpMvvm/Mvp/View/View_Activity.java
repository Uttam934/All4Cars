package com.allforcars.all4cars.ArctecherMvpMvvm.Mvp.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.allforcars.all4cars.ArctecherMvpMvvm.Mvp.Adapter.FeedAdapter;
import com.allforcars.all4cars.ArctecherMvpMvvm.Mvp.Api.GetDataContract;
import com.allforcars.all4cars.ArctecherMvpMvvm.Mvp.Model.Record;
import com.allforcars.all4cars.ArctecherMvpMvvm.Mvp.Presenter.Presenter;
import com.allforcars.all4cars.R;

import java.util.List;

public class View_Activity extends AppCompatActivity implements GetDataContract.View {

    private GetDataContract.Presenter presenter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    FeedAdapter feedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter=new Presenter(this);
        presenter.getDataFromURL(getApplicationContext(),"");
        recyclerView=findViewById(R.id.recyclerView_historyFragment);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onGetDataSuccess(String message, List<Record> list) {
        feedAdapter=new FeedAdapter(getApplicationContext(),list);
        recyclerView.setAdapter(feedAdapter);
    }

    @Override
    public void onGetDataFailure(String message) {

    }
}
