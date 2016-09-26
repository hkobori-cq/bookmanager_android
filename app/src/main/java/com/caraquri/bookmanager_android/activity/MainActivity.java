package com.caraquri.bookmanager_android.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.caraquri.bookmanager_android.R;
import com.caraquri.bookmanager_android.adapter.PagerAdapter;
import com.caraquri.bookmanager_android.databinding.ActivityMainBinding;
import com.caraquri.bookmanager_android.widget.OnRecyclerItemClickListener;

public class MainActivity extends AppCompatActivity implements OnRecyclerItemClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String FIRST_VISIT_FLAG = "first_visit_flag";
    private static final int IS_REGISTER_FRAGMENT = 0;
    private static final int IS_USER_SETTINGS_FRAGMENT = 1;
    public ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初めてアプリを開いたかどうかを判定する
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (defaultSharedPreferences.getBoolean(FIRST_VISIT_FLAG, true)) {
            defaultSharedPreferences.edit().putBoolean(FIRST_VISIT_FLAG, false).apply();
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
                    case IS_REGISTER_FRAGMENT:
                        binding.toolbar.setTitle(R.string.book_list);
                        break;
                    case IS_USER_SETTINGS_FRAGMENT:
                        binding.toolbar.setTitle(R.string.settings);
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
        intent.putExtra(EditActivity.BOOK_ID, bookID);
        intent.putExtra(EditActivity.BOOK_NAME, bookName);
        intent.putExtra(EditActivity.BOOK_PRICE, bookPrice);
        intent.putExtra(EditActivity.PURCHASE_DATE, purchaseDate);
        startActivity(intent);
    }
}
