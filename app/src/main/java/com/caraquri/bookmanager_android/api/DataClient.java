package com.caraquri.bookmanager_android.api;

import android.content.Context;
import android.content.Intent;

import com.caraquri.bookmanager_android.activity.MainActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Headers;
import retrofit.http.POST;

public class DataClient {
    public Retrofit createDataClient() {
        Gson gson = new GsonBuilder()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://app.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit;
    }

    public void bookRegisterClient(String imageUrl, String nameStr,
                                   Integer priceInt, String dateStr, final Context context) {
        BookDataRegisterService service = createDataClient().create(BookDataRegisterService.class);
        Call<Void> call = service.storeBookData("sample", nameStr, priceInt, dateStr);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Response<Void> response, Retrofit retrofit) {
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public void bookUpdateClient(String id, String imageUrl, String nameStr,
                                 Integer priceInt, String dateStr, final Context context) {
        BookDataUpdateService service = createDataClient().create(BookDataUpdateService.class);
        Call<Void> call = service.storeBookData(id, imageUrl, nameStr, priceInt, dateStr);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Response<Void> response, Retrofit retrofit) {
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private interface BookDataRegisterService {
        @POST("book/regist")
        @Headers("Accept: application/json;charset=utf-8")
        @FormUrlEncoded
        Call<Void> storeBookData(
                @Field("image_url") String url,
                @Field("name") String name,
                @Field("price") Integer price,
                @Field("purchase_date") String date
        );
    }

    private interface BookDataUpdateService {
        @POST("book/update")
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


}
