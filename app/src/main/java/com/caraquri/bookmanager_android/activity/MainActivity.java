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
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;


import java.util.ArrayList;
import java.util.List;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.Subscription;


public class MainActivity extends Activity {
    public ActivityMainBinding mainBinding;
    protected Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initRecyclerView();
    }

    private void initRecyclerView(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://app.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        BookDataClient.BookDataService service = retrofit.create(BookDataClient.BookDataService.class);

        this.subscription = service.getBookData("0-10")
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        Log.d("","onComplete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("", "エラー : " + e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("","onNext");
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
