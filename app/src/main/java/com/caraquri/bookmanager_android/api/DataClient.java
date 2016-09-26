package com.caraquri.bookmanager_android.api;

import com.caraquri.bookmanager_android.model.BookDataEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Headers;
import retrofit.http.POST;

public class DataClient {
    private static final String BASE_URL = "http://app.com";
    private static final String SAMPLE_IMAGE_URL = "sample.jpg";
    private DataRegisterService service;
    private static Retrofit retrofit;

    private Retrofit createDataClient() {
        Gson gson = new GsonBuilder()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit;
    }

    public Call<BookDataEntity> bookDataLoadClient(String pageParam) {
        if (retrofit == null) {
            service = createDataClient().create(DataRegisterService.class);
        } else {
            service = retrofit.create(DataRegisterService.class);
        }
        Call<BookDataEntity> call = service.getBookData(pageParam);
        return call;
    }

    public Call<Void> bookRegisterClient(String imageUrl, String nameStr,
                                         Integer priceInt, String dateStr) {
        service = retrofit.create(DataRegisterService.class);
        Call<Void> call = service.storeBookData(SAMPLE_IMAGE_URL, nameStr, priceInt, dateStr);
        return call;
    }

    public Call<Void> bookUpdateClient(String id, String imageUrl, String nameStr,
                                       Integer priceInt, String dateStr) {
        service = retrofit.create(DataRegisterService.class);
        Call<Void> call = service.storeBookData(id, imageUrl, nameStr, priceInt, dateStr);
        return call;
    }

    public Call<Integer> userLoginClient(String email, String password) {
        if (retrofit == null) {
            service = createDataClient().create(DataRegisterService.class);
        } else {
            service = retrofit.create(DataRegisterService.class);
        }
        Call<Integer> call = service.loginUserData(email, password);
        return call;
    }

    public Call<Void> userRegisterClient(String email, String password) {
        service = retrofit.create(DataRegisterService.class);
        Call<Void> call = service.storeUserData(email, password);
        return call;
    }

    private interface DataRegisterService {
        @POST("book/get")
        @Headers("Accept: application/json;charset=utf-8")
        @FormUrlEncoded
        Call<BookDataEntity> getBookData(
                @Field("page") String param
        );

        @POST("book/regist")
        @Headers("Accept: application/json;charset=utf-8")
        @FormUrlEncoded
        Call<Void> storeBookData(
                @Field("image_url") String url,
                @Field("name") String name,
                @Field("price") int price,
                @Field("purchase_date") String date
        );

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

        @POST("account/register")
        @Headers("Accept: application/json;charset=utf-8")
        @FormUrlEncoded
        Call<Void> storeUserData(
                @Field("mail_address") String mail,
                @Field("password") String password
        );

        @POST("account/login")
        @Headers("Accept: application/json;charset=utf-8")
        @FormUrlEncoded
        Call<Integer> loginUserData(
                @Field("mail_address") String mail,
                @Field("password") String password
        );
    }

}
