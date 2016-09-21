package com.caraquri.bookmanager_android.api;

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
    static final String BASEURL = "http://app.com";
    public Retrofit createDataClient() {
        Gson gson = new GsonBuilder()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit;
    }

    public Call<Void> bookRegisterClient(String imageUrl, String nameStr,
                                   Integer priceInt, String dateStr) {
        BookDataRegisterService service = createDataClient().create(BookDataRegisterService.class);
        Call<Void> call = service.storeBookData("sample", nameStr, priceInt, dateStr);
        return call;
    }

    public Call<Void> bookUpdateClient(String id, String imageUrl, String nameStr,
                                 Integer priceInt, String dateStr) {
        BookDataUpdateService service = createDataClient().create(BookDataUpdateService.class);
        Call<Void> call = service.storeBookData(id, imageUrl, nameStr, priceInt, dateStr);
        return call;
    }

    public Call<Integer> userLoginClient(String email, String password){
        UserLoginService service = createDataClient().create(UserLoginService.class);
        Call<Integer> call = service.storeUserData(email,password);
        return call;
    }

    public Call<Void> userRegisterClient(String email, String password){
        UserDataRegisterService service = createDataClient().create(UserDataRegisterService.class);
        Call<Void> call = service.storeUserData(email,password);
        return call;
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

    private interface UserDataRegisterService {
        @POST("account/register")
        @Headers("Accept: application/json;charset=utf-8")
        @FormUrlEncoded
        Call<Void> storeUserData(
                @Field("mail_address") String mail,
                @Field("password") String password
        );
    }

    private interface UserLoginService {
        @POST("account/login")
        @Headers("Accept: application/json;charset=utf-8")
        @FormUrlEncoded
        Call<Integer> storeUserData(
                @Field("mail_address") String mail,
                @Field("password") String password
        );
    }



}
