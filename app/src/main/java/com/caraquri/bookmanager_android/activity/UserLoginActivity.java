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
import com.caraquri.bookmanager_android.databinding.ActivityUserLoginBinding;
import com.caraquri.bookmanager_android.fragment.AlertDialogFragment;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

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

        Bundle args = new Bundle();
        AlertDialogFragment alertDialog = new AlertDialogFragment();

        if (emailStr.isEmpty()) {
            args.putString(getString(R.string.message), getString(R.string.input_mail_address));
            alertDialog.setArguments(args);
            alertDialog.show(getSupportFragmentManager(), getString(R.string.dialog));
        } else if (passwordStr.isEmpty()) {
            args.putString(getString(R.string.message), getString(R.string.input_password));
            alertDialog.setArguments(args);
            alertDialog.show(getSupportFragmentManager(),getString(R.string.dialog));
        } else {
            DataClient client = new DataClient();
            Call<Integer> call = client.userLoginClient(emailStr, passwordStr);
            call.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Response<Integer> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        Intent intent = new Intent(UserLoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Bundle args = new Bundle();
                        AlertDialogFragment alertDialog = new AlertDialogFragment();
                        args.putString(getString(R.string.message), getString(R.string.failed_login_message));
                        alertDialog.setArguments(args);
                        alertDialog.show(getSupportFragmentManager(), getString(R.string.dialog));
                    }
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
        }
    }
}
