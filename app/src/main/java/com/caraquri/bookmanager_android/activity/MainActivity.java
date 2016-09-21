package com.caraquri.bookmanager_android.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.caraquri.bookmanager_android.R;
import com.caraquri.bookmanager_android.adapter.PagerAdapter;
import com.caraquri.bookmanager_android.api.DataClient;
import com.caraquri.bookmanager_android.databinding.ActivityMainBinding;
import com.caraquri.bookmanager_android.fragment.AlertDialogFragment;
import com.caraquri.bookmanager_android.widget.OnRecyclerItemClickListener;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


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


    private void initToolbar() {
        setSupportActionBar(binding.toolbar);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setDisplayShowHomeEnabled(true);
            bar.setHomeButtonEnabled(true);
        }
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

        Bundle args = new Bundle();
        AlertDialogFragment alertDialog = new AlertDialogFragment();
        if (emailStr.isEmpty()) {
            args.putString("message", getString(R.string.input_mail_address));
            alertDialog.setArguments(args);
            alertDialog.show(getSupportFragmentManager(), "dialog");
        } else if (passwordStr.isEmpty()) {
            args.putString("message", getString(R.string.input_password));
            alertDialog.setArguments(args);
            alertDialog.show(getSupportFragmentManager(), "dialog");
        } else if (passwordConStr.isEmpty()) {
            args.putString("message", getString(R.string.input_password_confirm));
            alertDialog.setArguments(args);
            alertDialog.show(getSupportFragmentManager(), "dialog");
        } else if (!(passwordStr.equals(passwordConStr))) {
            args.putString("message", getString(R.string.not_match_password));
            alertDialog.setArguments(args);
            alertDialog.show(getSupportFragmentManager(), "dialog");
        } else {
            DataClient client = new DataClient();
            Call<Void> call = client.userRegisterClient(emailStr, passwordStr);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Response<Void> response, Retrofit retrofit) {
                    Bundle args = new Bundle();
                    args.putString("message", getString(R.string.completed_register));
                    AlertDialogFragment alertDialog = new AlertDialogFragment();
                    alertDialog.setArguments(args);
                    alertDialog.show(getSupportFragmentManager(), "dialog");
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
        }


    }

}
