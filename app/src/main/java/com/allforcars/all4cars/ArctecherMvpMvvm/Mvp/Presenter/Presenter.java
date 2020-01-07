package com.allforcars.all4cars.ArctecherMvpMvvm.Mvp.Presenter;

import android.content.Context;


import com.allforcars.all4cars.ArctecherMvpMvvm.Mvp.Api.GetDataContract;
import com.allforcars.all4cars.ArctecherMvpMvvm.Mvp.Model.Record;

import java.util.List;

public class Presenter implements GetDataContract.Presenter, GetDataContract.onGetDataListener {
    private GetDataContract.View mGetDataView;
    private Intractor mIntractor;
    public Presenter(GetDataContract.View mGetDataView){
        this.mGetDataView = mGetDataView;
        mIntractor = new Intractor(this);
    }
    @Override
    public void getDataFromURL(Context context, String url) {
        mIntractor.initRetrofitCall(context,url);
    }

    @Override
    public void onSuccess(String message, List<Record> allRecodsData) {
        mGetDataView.onGetDataSuccess(message, allRecodsData);
    }

    @Override
    public void onFailure(String message) {
        mGetDataView.onGetDataFailure(message);
    }
}
