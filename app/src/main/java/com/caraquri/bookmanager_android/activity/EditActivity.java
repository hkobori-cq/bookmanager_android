package com.caraquri.bookmanager_android.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.caraquri.bookmanager_android.R;
import com.caraquri.bookmanager_android.api.BookDataUpdateClient;
import com.caraquri.bookmanager_android.databinding.ActivityEditBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

public class EditActivity extends AppCompatActivity {
    protected ActivityEditBinding binding;
    private static final String TAG = EditActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit);
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(binding.toolbar);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setDisplayShowHomeEnabled(true);
            bar.setDisplayShowTitleEnabled(false);
            bar.setHomeButtonEnabled(true);
        }
        binding.toolbar.setTitle("書籍編集");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.register:
                registerBookData();
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_and_edit_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void registerBookData() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        EditText name = (EditText) findViewById(R.id.book_title_field);
        EditText price = (EditText) findViewById(R.id.book_price_field);
        EditText date = (EditText) findViewById(R.id.book_date_field);

        String nameStr = name.getText().toString();
        String dateStr = date.getText().toString();

        if (nameStr.isEmpty()){
            new AlertDialog.Builder(this)
                    .setTitle("書籍名を入力してください")
                    .setNegativeButton("ok",null)
                    .show();
        }else if (price.getText().toString().isEmpty()){
            new AlertDialog.Builder(this)
                    .setTitle("価格を入力してください")
                    .setNegativeButton("ok",null)
                    .show();
        } else if (dateStr.isEmpty()){
            new AlertDialog.Builder(this)
                    .setTitle("日付を選択してください")
                    .setNegativeButton("ok",null)
                    .show();
        }else {
            Integer priceInt = Integer.parseInt(price.getText().toString());
            Gson gson = new GsonBuilder()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://app.com")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            BookDataUpdateClient client = retrofit.create(BookDataUpdateClient.class);
            Call<Void> call = client.storeBookData(id, "sample", nameStr, priceInt, dateStr);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Response<Void> response, Retrofit retrofit) {
                    Log.d(TAG, "ok");
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.d(TAG, "だめ");
                }
            });
        }
    }
}
