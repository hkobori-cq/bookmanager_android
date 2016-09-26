package com.caraquri.bookmanager_android.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.caraquri.bookmanager_android.R;
import com.caraquri.bookmanager_android.databinding.ActivityUserLoginBinding;

public class UserLoginActivity extends AppCompatActivity {
    private static final String TAG = UserLoginActivity.class.getSimpleName();
    protected ActivityUserLoginBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_login);
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(binding.toolbar);
    }
}
