package com.caraquri.bookmanager_android.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.caraquri.bookmanager_android.activity.MainActivity;
import com.caraquri.bookmanager_android.fragment.RecyclerLayoutFragment;
import com.caraquri.bookmanager_android.fragment.UserSettingsFragment;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentPagerAdapter {

    private final List<String> mCategoryNames = new ArrayList<>();

    public void addCategory(String categoryName) {
        mCategoryNames.add(categoryName);
    }

    public PagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            default:
                return new RecyclerLayoutFragment();
            case 1:
                return new UserSettingsFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mCategoryNames.get(position);
    }
}
