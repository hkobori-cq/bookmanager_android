package com.caraquri.bookmanager_android.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.caraquri.bookmanager_android.R;
import com.caraquri.bookmanager_android.databinding.ActivityEditBinding;


public class EditActivity extends AppCompatActivity {
    private static final String TAG = EditActivity.class.getSimpleName();
    public static final String BOOK_ID = "id";
    public static final String BOOK_NAME = "name";
    public static final String BOOK_PRICE = "price";
    public static final String PURCHASE_DATE = "purchase_date";
    protected ActivityEditBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit);
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(binding.toolbar);
    }
}
