package com.allforcars.all4cars.Test.Retrofit_Rxjava;


import com.allforcars.all4cars.Test.Retrofit_Rxjava.SpinnerModel.Spinnerlist;
import com.allforcars.all4cars.Test.TestLargeAPI.Model.Mainapitest;

import io.reactivex.Observable;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RequestInterface {



    @FormUrlEncoded
    @POST(API.ADD_TICKET)
    Observable<CheckStatusModel> Addsubscripiton(@Field("user_id") String user_id,
                                                 @Field("user_name") String user_name,
                                                 @Field("email") String email,
                                                 @Field("phone") String phone,
                                                 @Field("address") String address,
                                                 @Field("description") String description,
                                                 @Field("user_type") String user_type,
                                                 @Field("company_name") String company_name);

    @FormUrlEncoded
    @POST(API.Reportlists)
    Observable<CheckStatusModel> changestatus(@Field("user_id") String user_id,
                                              @Field("user_name") String user_name,
                                              @Field("mobile") String mobile,
                                              @Field("user_type") String user_type,
                                              @Field("issue") String issue);

    @FormUrlEncoded
    @POST(API.order_list)
    Observable<Responses_Model> order_list(@Field("user_type") String user_type,
                                            @Field("user_id") String user_id,
                                            @Field("status_type") String status_type);


    @GET(API.Spinner)
    Observable<Spinnerlist> spinner(@Query("user_type") String user_type);


    @Headers("Content-Type: application/json")
    @POST(API.ADD_FUND)
    Observable<CheckStatusModel> addfund_(@Body RequestBody body);

    @GET(API.Larageapi)
    Observable<Mainapitest> largeapi(@Query("api-key") String api_key);






}
