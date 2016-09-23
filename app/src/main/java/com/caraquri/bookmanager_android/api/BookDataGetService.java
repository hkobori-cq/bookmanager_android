package com.caraquri.bookmanager_android.api;

import com.caraquri.bookmanager_android.model.BookDataEntity;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Headers;
import retrofit.http.POST;

public interface BookDataGetService {
    @POST("book/get")
    @Headers("Accept: application/json;charset=utf-8")
    @FormUrlEncoded
    Call<BookDataEntity> getBookData(
            @Field("page") String param
    );
}
