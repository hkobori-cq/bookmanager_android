package com.caraquri.bookmanager_android.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.caraquri.bookmanager_android.R;
import com.caraquri.bookmanager_android.api.DataClient;
import com.caraquri.bookmanager_android.databinding.ActivityEditBinding;
import com.caraquri.bookmanager_android.fragment.AlertDialogFragment;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class EditActivity extends AppCompatActivity {
    private static final String TAG = EditActivity.class.getSimpleName();
    protected ActivityEditBinding binding;

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
                updateBookData();
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
    public void updateBookData() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        EditText name = (EditText) findViewById(R.id.book_title_field);
        EditText price = (EditText) findViewById(R.id.book_price_field);
        EditText date = (EditText) findViewById(R.id.book_date_field);

        String nameStr = name.getText().toString();
        String dateStr = date.getText().toString();
        Bundle args = new Bundle();
        AlertDialogFragment alertDialog = new AlertDialogFragment();

        if (nameStr.isEmpty()) {
            args.putString(getString(R.string.message), getString(R.string.input_book_name));
            alertDialog.setArguments(args);
            alertDialog.show(getSupportFragmentManager(), getString(R.string.dialog));
        } else if (price.getText().toString().isEmpty()) {
            args.putString(getString(R.string.message), getString(R.string.input_book_price));
            alertDialog.setArguments(args);
            alertDialog.show(getSupportFragmentManager(),getString(R.string.dialog));
        } else if (dateStr.isEmpty()) {
            args.putString(getString(R.string.message), getString(R.string.select_purchase_date));
            alertDialog.setArguments(args);
            alertDialog.show(getSupportFragmentManager(), getString(R.string.dialog));
        } else {
            int priceInt = Integer.parseInt(price.getText().toString());

            DataClient client = new DataClient();
            Call<Void> call = client.bookUpdateClient(id, getString(R.string.sample_image), nameStr, priceInt, dateStr);
            call.enqueue(new Callback<Void>() {
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
