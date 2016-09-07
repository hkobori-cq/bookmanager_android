package com.caraquri.bookmanager_android.activity;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.caraquri.bookmanager_android.R;
import com.caraquri.bookmanager_android.adapter.BookTitleAdapter;
import com.caraquri.bookmanager_android.api.APIListener;
import com.caraquri.bookmanager_android.api.ConnectAPI;
import com.caraquri.bookmanager_android.databinding.ActivityMainBinding;
import com.caraquri.bookmanager_android.model.BookDataModel;


import java.util.ArrayList;
import java.util.List;



public class MainActivity extends Activity implements APIListener {
    public ActivityMainBinding mainBinding;
    private MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initRecyclerView();
    }

    private void initRecyclerView(){
        (new Thread(new Runnable() {
            @Override
            public void run() {
                ConnectAPI connectAPI = new ConnectAPI("page=0-10","getBook");
                connectAPI.setAPIListener(mainActivity);
                connectAPI.execute();
            }
        })).start();
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

    @Override
    public void succeededAPIConnection(StringBuffer result){
        Log.d("ok","ok");
    }

}
