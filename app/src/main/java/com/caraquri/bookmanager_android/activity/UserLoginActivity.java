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
import com.caraquri.bookmanager_android.api.UserLoginClient;
import com.caraquri.bookmanager_android.databinding.ActivityUserLoginBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

public class UserLoginActivity extends AppCompatActivity {
    protected ActivityUserLoginBinding binding;
    private static final String TAG = UserLoginActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_login);
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(binding.toolbar);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_user_settings, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_user_settings:
                registerUserData();
                break;
        }
        return true;
    }

    /**
     * 保存ボタンを押したときに呼ばれるメソッド
     */
    public void registerUserData() {
        EditText email = (EditText) findViewById(R.id.user_login_email_field);
        EditText password = (EditText) findViewById(R.id.user_login_password_field);

        String emailStr = email.getText().toString();
        String passwordStr = password.getText().toString();

        if (emailStr.isEmpty()) {
            new AlertDialog.Builder(this)
                    .setTitle("メールアドレスを入力してください")
                    .setNegativeButton("ok", null)
                    .show();
        } else if (passwordStr.isEmpty()) {
            new AlertDialog.Builder(this)
                    .setTitle("パスワードを入力してください")
                    .setNegativeButton("ok", null)
                    .show();
        } else {
            Gson gson = new GsonBuilder()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://app.com")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            UserLoginClient client = retrofit.create(UserLoginClient.class);
            Call<Integer> call = client.storeUserData(emailStr, passwordStr);
            call.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Response<Integer> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        Intent intent = new Intent(UserLoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        new AlertDialog.Builder(UserLoginActivity.this)
                                .setTitle("ログインに失敗しました")
                                .setNegativeButton("ok", null)
                                .show();
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.d(TAG, "だめ");
                }
            });
        }
    }
}
