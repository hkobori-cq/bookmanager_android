package com.caraquri.bookmanager_android.api;

import com.caraquri.bookmanager_android.model.BookDataModel;

import java.util.List;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Headers;
import retrofit.http.POST;
import rx.Observable;

public interface BookDataClient {
    @POST("book/get")
    @Headers("Accept: application/json")
    @FormUrlEncoded
    Observable<BookDataModel> getBookData(
            @Field("page") String param
    );
}