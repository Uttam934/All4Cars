package com.allforcars.all4cars.ArctecherMvpMvvm.MVVM.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private static Retrofit retrofit = new Retrofit.Builder().baseUrl("http://itdevelopmentservices.com/all4cars/api/").
            addConverterFactory(GsonConverterFactory.create()).build();

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
