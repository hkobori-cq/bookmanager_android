package com.caraquri.bookmanager_android.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.caraquri.bookmanager_android.R;
import com.caraquri.bookmanager_android.adapter.PagerAdapter;
import com.caraquri.bookmanager_android.databinding.ActivityMainBinding;
import com.caraquri.bookmanager_android.widget.OnItemClickListener;


public class MainActivity extends AppCompatActivity implements OnItemClickListener {
    public ActivityMainBinding mainBinding;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initToolbar();
        initTabBar();
    }

    private void initTabBar(){
        FragmentManager manager = getSupportFragmentManager();
        PagerAdapter adapter = new PagerAdapter(manager);
        adapter.addCategory("書籍一覧");
        adapter.addCategory("設定");
        mainBinding.viewPaper.setAdapter(adapter);
        mainBinding.tabbar.setupWithViewPager(mainBinding.viewPaper);
    }


    private void initToolbar() {
        setSupportActionBar(mainBinding.toolbar);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setDisplayShowHomeEnabled(true);
            bar.setHomeButtonEnabled(true);
        }
        mainBinding.viewPaper.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mainBinding.toolbar.getMenu().clear();
                switch (position) {
                    case 0:
                        mainBinding.toolbar.setTitle("書籍一覧");
                        mainBinding.toolbar.inflateMenu(R.menu.main_activity_actions);
                        break;
                    case 1:
                        mainBinding.toolbar.setTitle("設定");
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
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(this, AddActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public void onItemClick(View view, String id,String name,String price,String date) {
        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("name",name);
        intent.putExtra("price",price);
        intent.putExtra("date",date);
        startActivity(intent);
    }

}
