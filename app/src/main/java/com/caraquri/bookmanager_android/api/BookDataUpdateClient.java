package com.caraquri.bookmanager_android.api;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Headers;
import retrofit.http.POST;

public interface BookDataUpdateClient {
    @POST("book/regist")
    @Headers("Accept: application/json;charset=utf-8")
    @FormUrlEncoded
    Call<Void> storeBookData(
            @Field("id") String id,
            @Field("image_url") String url,
            @Field("name") String name,
            @Field("price") Integer price,
            @Field("purchase_date") String date
    );
}
