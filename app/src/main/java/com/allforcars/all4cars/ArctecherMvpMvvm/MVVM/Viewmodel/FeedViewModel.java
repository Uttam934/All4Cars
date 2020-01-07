package com.allforcars.all4cars.ArctecherMvpMvvm.MVVM.Viewmodel;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.allforcars.all4cars.ArctecherMvpMvvm.MVVM.Modle.JsonResponse;
import com.allforcars.all4cars.ArctecherMvpMvvm.MVVM.Repository.FeedRepository;

public class FeedViewModel extends ViewModel {

    private MutableLiveData<JsonResponse> mutableLiveData;
    private FeedRepository feedRepository;

    public void init() {
        if (mutableLiveData != null) {
            return;
        }
        feedRepository = FeedRepository.getInstance();
        mutableLiveData = feedRepository.getFeed("24", "2", "0");

    }

    public LiveData<JsonResponse> getFeedRepository() {
        return mutableLiveData;
    }
}
