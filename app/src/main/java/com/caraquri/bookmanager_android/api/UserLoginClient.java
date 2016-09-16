package com.caraquri.bookmanager_android.api;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Headers;
import retrofit.http.POST;

public interface UserLoginClient {
    @POST("account/login")
    @Headers("Accept: application/json;charset=utf-8")
    @FormUrlEncoded
    Call<Integer> storeUserData(
            @Field("mail_address") String mail,
            @Field("password") String password
    );
}
