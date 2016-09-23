package com.caraquri.bookmanager_android.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.caraquri.bookmanager_android.R;
import com.caraquri.bookmanager_android.api.DataClient;
import com.caraquri.bookmanager_android.databinding.ActivityAddBinding;
import com.caraquri.bookmanager_android.fragment.AlertDialogFragment;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class AddActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    protected ActivityAddBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add);
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(binding.toolbar);
    }

}
