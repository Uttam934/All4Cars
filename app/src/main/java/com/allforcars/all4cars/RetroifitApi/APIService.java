package com.allforcars.all4cars.RetroifitApi;







import com.allforcars.all4cars.Response.Getuserdata_Response;
import com.allforcars.all4cars.Response.Loyalityhistry_Response;
import com.allforcars.all4cars.Response.Notification_Response;
import com.allforcars.all4cars.Response.PaymentslipResponse;
import com.allforcars.all4cars.Response.Report_Response;
import com.allforcars.all4cars.Response.ResultResponse;
import com.allforcars.all4cars.Response.Test_Response;
import com.allforcars.all4cars.Response.Test_Responsesss;
import com.allforcars.all4cars.Response.Venderlist_Response;
import com.allforcars.all4cars.Test.modelCreateBorrowers.CreateBorrowerResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static com.allforcars.all4cars.RetroifitApi.ServiceUrlList.Getnotification;
import static com.allforcars.all4cars.RetroifitApi.ServiceUrlList.Loyatlity;
import static com.allforcars.all4cars.RetroifitApi.ServiceUrlList.Loyatlity_histoy;
import static com.allforcars.all4cars.RetroifitApi.ServiceUrlList.Loyatlitys;
import static com.allforcars.all4cars.RetroifitApi.ServiceUrlList.Notification_status;
import static com.allforcars.all4cars.RetroifitApi.ServiceUrlList.Reportlist;
import static com.allforcars.all4cars.RetroifitApi.ServiceUrlList.Venderlist;
import static com.allforcars.all4cars.RetroifitApi.ServiceUrlList.paymetnhistory_histoy;

public interface APIService
{

//    @GET(show_message_count)
//    Call<ResultResponse>getMessageCountList(@Query("news_code") String news_code);

    // @FormUrlEncoded

    @Headers({"Content-Type: application/json"})
    @GET(Loyatlity)
    Call<ResultResponse>postMessageCountList(@Query("user_id") String user_id);

    @GET(Loyatlity_histoy)
    Call<Loyalityhistry_Response>localityhistory(@Query("user_id") String user_id);

    @GET(paymetnhistory_histoy)
    Call<PaymentslipResponse>paymethistory(@Query("vendor_id") String vendor_id);

    @GET(Notification_status)
    Call<Notification_Response>notificationstaus(
            @Query("user_id") String user_id,
            @Query("user_type") String user_type
    );

    @GET(Venderlist)
    Call<Venderlist_Response>venderlist(
            @Query("latitude") String currentlatitude,
            @Query("longitude") String currentlongtide,
            @Query("category") String Catergorylist,
            @Query("rang") String seekbarValue

    );




    @FormUrlEncoded
    @POST(Reportlist)
    Call<Report_Response>Reportsend(@Field("user_id") String user_id,
                                    @Field("user_name") String user_name,
                                    @Field("mobile") String mobile,
                                    @Field("user_type") String user_type,
                                    @Field("issue") String issue
    );


    @GET(Loyatlitys)
    Call<CreateBorrowerResponse>Reportsends();

















    @Headers({"Content-Type: application/json"})
    @FormUrlEncoded
    @POST(Getnotification)
    Call<Getuserdata_Response>getrespnce(@Field("user_id") String user_id,
                                         @Field("user_type") String user_type

    );




}
