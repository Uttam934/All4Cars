package com.allforcars.all4cars.ArctecherMvpMvvm.MVVM.Api;



import com.allforcars.all4cars.ArctecherMvpMvvm.MVVM.Modle.JsonResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface FeedApi {

    @FormUrlEncoded
    @POST("order_list")
    Call<JsonResponse> getFeedList(@Field("user_id") String user_id,
                                   @Field("user_type") String user_type,
                                   @Field("status_type") String status_type);

}
