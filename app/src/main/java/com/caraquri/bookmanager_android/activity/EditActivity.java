package com.caraquri.bookmanager_android.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.caraquri.bookmanager_android.R;
import com.caraquri.bookmanager_android.api.BookDataUpdateService;
import com.caraquri.bookmanager_android.api.DataClient;
import com.caraquri.bookmanager_android.databinding.ActivityEditBinding;
import com.caraquri.bookmanager_android.util.CreateAlertView;
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
        binding.toolbar.setTitle(R.string.book_edit);
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
        inflater.inflate(R.menu.menu_book, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 保存ボタンを押したときに呼ばれるメソッド
     */
    public void registerBookData() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        EditText name = (EditText) findViewById(R.id.book_title_field);
        EditText price = (EditText) findViewById(R.id.book_price_field);
        EditText date = (EditText) findViewById(R.id.book_date_field);

        String nameStr = name.getText().toString();
        String dateStr = date.getText().toString();
        CreateAlertView alertView = new CreateAlertView();

        if (nameStr.isEmpty()) {
            alertView.createAlertView(getString(R.string.input_book_name),this);
        } else if (price.getText().toString().isEmpty()) {
            alertView.createAlertView(getString(R.string.input_book_price),this);
        } else if (dateStr.isEmpty()) {
            alertView.createAlertView(getString(R.string.select_purchase_date),this);
        } else {
            Integer priceInt = Integer.parseInt(price.getText().toString());

            DataClient client = new DataClient();
            Retrofit retrofit = client.createDataClient();

            BookDataUpdateService service = retrofit.create(BookDataUpdateService.class);
            Call<Void> call = service.storeBookData(id, getString(R.string.image_sample_url), nameStr, priceInt, dateStr);
            call.enqueue(new Callback<Void>() {
                /**
                 * API通信が成功したときに呼ばれるメソッド
                 * @param response
                 * @param retrofit
                 */
                @Override
                public void onResponse(Response<Void> response, Retrofit retrofit) {
                    Intent intent = new Intent(EditActivity.this, MainActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Throwable t) {
                }
            });
        }
    }
}
