package com.caraquri.bookmanager_android.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.caraquri.bookmanager_android.R;
import com.caraquri.bookmanager_android.databinding.ActivityAddBinding;

public class AddActivity extends AppCompatActivity {
    protected ActivityAddBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add);
    }
}
