package com.caraquri.bookmanager_android.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.caraquri.bookmanager_android.R;
import com.caraquri.bookmanager_android.adapter.PagerAdapter;
import com.caraquri.bookmanager_android.api.UserDataRegisterClient;
import com.caraquri.bookmanager_android.databinding.ActivityMainBinding;
import com.caraquri.bookmanager_android.widget.OnRecyclerItemClickListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;


public class MainActivity extends AppCompatActivity implements OnRecyclerItemClickListener {
    public ActivityMainBinding binding;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初めてアプリを開いたかどうかを判定する
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (defaultSharedPreferences.getBoolean("first_visit", true)) {
            defaultSharedPreferences.edit().putBoolean("first_visit", false).apply();
            Intent intent = new Intent(MainActivity.this, UserLoginActivity.class);
            startActivity(intent);
        } else {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
            initToolbar();
            initTabBar();
        }
    }

    private void initTabBar() {
        FragmentManager manager = getSupportFragmentManager();
        PagerAdapter adapter = new PagerAdapter(manager);
        adapter.addCategory("書籍一覧");
        adapter.addCategory("設定");
        binding.viewPaper.setAdapter(adapter);
        binding.tabbar.setupWithViewPager(binding.viewPaper);
    }


    private void initToolbar() {
        setSupportActionBar(binding.toolbar);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setDisplayShowHomeEnabled(true);
            bar.setHomeButtonEnabled(true);
        }
        binding.viewPaper.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            /**
             * ページが切り替わったときに呼ばれるメソッド
             * ToolBarのタイトル変更と、メニュー切り替えを行う
             * @param position
             */
            @Override
            public void onPageSelected(int position) {
                binding.toolbar.getMenu().clear();
                switch (position) {
                    case 0:
                        binding.toolbar.setTitle("書籍一覧");
                        binding.toolbar.inflateMenu(R.menu.menu_main);
                        break;
                    case 1:
                        binding.toolbar.setTitle("設定");
                        binding.toolbar.inflateMenu(R.menu.menu_user_settings);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(this, AddActivity.class);
                startActivity(intent);
                break;
            case R.id.action_user_settings:
                registerUserData();
                break;
        }
        return true;
    }

    /**
     * Recyclerのセルをタップしたときに必要なリスナー
     * RecyclerViewAdapterでセルをタップするとMainActivityにこのリスナーを通してデータが送られてきて、
     * Intentを用いて、EditActivityに送られる。
     *
     * @param view
     * @param bookID
     * @param bookName
     * @param bookPrice
     * @param purchaseDate
     */
    @Override
    public void onRecyclerItemClick(View view, String bookID, String bookName, String bookPrice, String purchaseDate) {
        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        intent.putExtra("id", bookID);
        intent.putExtra("name", bookName);
        intent.putExtra("price", bookPrice);
        intent.putExtra("date", purchaseDate);
        startActivity(intent);
    }

    /**
     * 設定画面で保存を押したときに呼ばれるメソッド
     */
    public void registerUserData() {
        EditText email = (EditText) findViewById(R.id.user_email_field);
        EditText password = (EditText) findViewById(R.id.user_password_field);
        EditText passwordCon = (EditText) findViewById(R.id.user_password_confirm_field);

        String emailStr = email.getText().toString();
        String passwordStr = password.getText().toString();
        String passwordConStr = passwordCon.getText().toString();

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
        } else if (passwordConStr.isEmpty()) {
            new AlertDialog.Builder(this)
                    .setTitle("パスワード確認を入力してください")
                    .setNegativeButton("ok", null)
                    .show();
        } else if (!(passwordStr.equals(passwordConStr))) {
            new AlertDialog.Builder(this)
                    .setTitle("パスワードと確認が一致しません")
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
            UserDataRegisterClient client = retrofit.create(UserDataRegisterClient.class);
            Call<Void> call = client.storeUserData(emailStr, passwordStr);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Response<Void> response, Retrofit retrofit) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("登録が完了しました")
                            .setNegativeButton("ok", null)
                            .show();
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.d(TAG, "だめ");
                }
            });
        }


    }

}
