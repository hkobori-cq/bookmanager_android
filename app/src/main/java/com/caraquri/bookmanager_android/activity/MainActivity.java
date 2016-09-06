package com.caraquri.bookmanager_android.activity;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.caraquri.bookmanager_android.R;
import com.caraquri.bookmanager_android.adapter.BookTitleAdapter;
import com.caraquri.bookmanager_android.databinding.ActivityMainBinding;
import com.caraquri.bookmanager_android.model.BookDataModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    public BookTitleAdapter bookTitleAdapter;
    public ActivityMainBinding mainBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initRecyclerView();
    }

    private void initRecyclerView(){
        List<BookDataModel> list = new ArrayList<>();
        list.add(new BookDataModel("item1"));
        list.add(new BookDataModel("item2"));

        BookTitleAdapter adapter = new BookTitleAdapter(list);
        mainBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainBinding.recyclerView.setAdapter(adapter);
    }
}
