package com.caraquri.bookmanager_android.api;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Headers;
import retrofit.http.POST;

public interface UserDataClient {
    @POST("account/register")
    @Headers("Accept: application/json;charset=utf-8")
    @FormUrlEncoded
    Call<Void> storeUserData(
            @Field("mail_address") String mail,
            @Field("password") String password
    );
}