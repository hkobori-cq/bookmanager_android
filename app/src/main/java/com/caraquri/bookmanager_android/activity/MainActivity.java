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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.caraquri.bookmanager_android.R;
import com.caraquri.bookmanager_android.adapter.PagerAdapter;
import com.caraquri.bookmanager_android.api.DataClient;
import com.caraquri.bookmanager_android.api.UserDataRegisterService;
import com.caraquri.bookmanager_android.databinding.ActivityMainBinding;
import com.caraquri.bookmanager_android.util.CreateAlertView;
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
        if (defaultSharedPreferences.getBoolean(getString(R.string.first_visit), true)) {
            defaultSharedPreferences.edit().putBoolean(getString(R.string.first_visit), false).apply();
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
        adapter.addCategory(getString(R.string.book_list));
        adapter.addCategory(getString(R.string.settings));
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
                        binding.toolbar.setTitle(R.string.book_list);
                        binding.toolbar.inflateMenu(R.menu.menu_main);
                        break;
                    case 1:
                        binding.toolbar.setTitle(R.string.settings);
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

        CreateAlertView alertView = new CreateAlertView();
        if (emailStr.isEmpty()) {
            alertView.createAlertView(getString(R.string.input_mail_address), this);
        } else if (passwordStr.isEmpty()) {
            alertView.createAlertView(getString(R.string.input_password), this);
        } else if (passwordConStr.isEmpty()) {
            alertView.createAlertView(getString(R.string.input_password_confirm), this);
        } else if (!(passwordStr.equals(passwordConStr))) {
            alertView.createAlertView(getString(R.string.not_match_password), this);
        } else {
            DataClient client = new DataClient();
            client.userRegisterClient(emailStr,passwordStr,this);
        }


    }

}
