package com.caraquri.bookmanager_android.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.caraquri.bookmanager_android.R;
import com.caraquri.bookmanager_android.adapter.BookTitleAdapter;
import com.caraquri.bookmanager_android.api.BookDataClient;
import com.caraquri.bookmanager_android.databinding.ActivityMainBinding;
import com.caraquri.bookmanager_android.model.BookDataEntity;
import com.caraquri.bookmanager_android.widget.OnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;


public class MainActivity extends AppCompatActivity implements OnItemClickListener {
    public ActivityMainBinding mainBinding;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initToolbar();
    }


    private void initToolbar() {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(this, AddActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public void onItemClick(View view, String id,String name,String price,String date) {
        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("name",name);
        intent.putExtra("price",price);
        intent.putExtra("date",date);
        startActivity(intent);
    }

}
