package com.caraquri.bookmanager_android.activity;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.caraquri.bookmanager_android.R;
import com.caraquri.bookmanager_android.adapter.BookTitleAdapter;
import com.caraquri.bookmanager_android.api.BookDataClient;
import com.caraquri.bookmanager_android.databinding.ActivityMainBinding;
import com.caraquri.bookmanager_android.model.BookDataModel;
import com.squareup.okhttp.OkHttpClient;


import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public class MainActivity extends Activity {
    public ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initRecyclerView();
    }

    private void initRecyclerView(){
        OkHttpClient httpClient = new OkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://app.com")
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BookDataClient.BookDataService service = retrofit.create(BookDataClient.BookDataService.class);

        Call<String> call = service.getBookData("0-10");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {
                Log.v("", Boolean.toString(response.isSuccess()));
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("error",t.toString());
            }
        });
        List<BookDataModel> list = new ArrayList<>();
        list.add(new BookDataModel("item1"));
        list.add(new BookDataModel("item2"));
        list.add(new BookDataModel("item3"));
        list.add(new BookDataModel("item4"));
        list.add(new BookDataModel("item5"));
        list.add(new BookDataModel("item6"));
        BookTitleAdapter adapter = new BookTitleAdapter(list);
        mainBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainBinding.recyclerView.setAdapter(adapter);
    }

}
