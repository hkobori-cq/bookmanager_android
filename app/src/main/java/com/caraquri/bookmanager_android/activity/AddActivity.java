package com.caraquri.bookmanager_android.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.caraquri.bookmanager_android.R;
import com.caraquri.bookmanager_android.databinding.ActivityAddBinding;

public class AddActivity extends AppCompatActivity {
    protected ActivityAddBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add);
        initToolbar();
    }

    private void initToolbar(){
        setSupportActionBar(binding.toolbar);
        ActionBar bar = getSupportActionBar();
        if (bar != null){
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setDisplayShowHomeEnabled(true);
            bar.setDisplayShowTitleEnabled(false);
            bar.setHomeButtonEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
        return true;
    }
}
