package com.caraquri.bookmanager_android.api;

import com.caraquri.bookmanager_android.model.BookDataEntity;

import java.util.List;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Headers;
import retrofit.http.POST;

public interface BookDataClient {
    @POST("book/get")
    @Headers("Accept: application/json")
    @FormUrlEncoded
    Call<BookDataEntity> getBookData(
            @Field("page") String param
    );
}