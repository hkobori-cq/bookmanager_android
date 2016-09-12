package com.caraquri.bookmanager_android.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;

import com.caraquri.bookmanager_android.R;
import com.caraquri.bookmanager_android.adapter.BookTitleAdapter;
import com.caraquri.bookmanager_android.api.BookDataClient;
import com.caraquri.bookmanager_android.databinding.ActivityMainBinding;
import com.caraquri.bookmanager_android.model.BookDataEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;


public class MainActivity extends AppCompatActivity {
    public ActivityMainBinding mainBinding;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initToolbar();
        initRecyclerView();
    }

    private void initRecyclerView(){

        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://app.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        BookDataClient bookDataClient = retrofit.create(BookDataClient.class);
        Call<BookDataEntity> call = bookDataClient.getBookData("0-10");
        call.enqueue(new Callback<BookDataEntity>() {
            @Override
            public void onResponse(Response<BookDataEntity> response, Retrofit retrofit) {
                BookTitleAdapter adapter = new BookTitleAdapter(response.body().getBookData());
                mainBinding.recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    private void initToolbar(){
        setSupportActionBar(mainBinding.toolbar);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setDisplayShowHomeEnabled(true);
            bar.setHomeButtonEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
